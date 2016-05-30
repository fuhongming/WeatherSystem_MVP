package com.iotek.weathersystem.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iotek.weathersystem.R;
import com.iotek.weathersystem.db.SqliteData;
import com.iotek.weathersystem.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by fhm on 2016/5/30.
 */
public class LoginActivity extends Activity {

    SQLiteDatabase database;
    private SqliteData sqlitedata;

    private final String TABLE_NAME = "ueser";
    private final String NAME = "name";
    private final String PASSWORD = "password";
    @ViewInject(R.id.btnBack)
    Button btnBack;


    @ViewInject(R.id.btnRegister)
    Button btnRegister;
    @ViewInject(R.id.btnLogin)
    Button btnLogin;
    @ViewInject(R.id.etUserName)
    EditText etUserName;
    @ViewInject(R.id.etPwd)
    EditText etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);
        sqlitedata = new SqliteData(LoginActivity.this,                                // 上下文
                "user.db", null, 1);
        database = sqlitedata.getWritableDatabase();
    }

    @OnClick({R.id.btnBack, R.id.btnRegister, R.id.btnLogin})
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnRegister:
                 intent = new Intent(LoginActivity.this, RegisterActivity.class);
                 startActivity(intent);

                break;
            case R.id.btnLogin:
                if(etUserName==null||etPwd==null){
                    ToastUtils.showToast(LoginActivity.this,"用户名或密码为空");
                }
                String inputName = etUserName.getText().toString();
                String inputPwd = etPwd.getText().toString();
                ContentValues values = new ContentValues();


                String selectionName = "name = ?";
                String selectionPwd = "password = ?";
                String[] selectionArgsName = {inputName};
                String[] selectionArgsPwd = {inputPwd};
                Cursor cursor = database.query(TABLE_NAME, null, selectionName,
                        selectionArgsName, null, null, null);
                String name = "";
                String password = "";
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    name = cursor.getString(cursor.getColumnIndex(NAME));
                    password = cursor.getString(cursor.getColumnIndex(PASSWORD));
                }
                if (name != ("") && name != null && password != null && name.equals(inputName)
                        && password.equals(inputPwd)) {
                    Toast.makeText(getBaseContext(),
                            "登录成功！", Toast.LENGTH_SHORT)
                            .show();


                    intent = new Intent(LoginActivity.this, WeatherActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getBaseContext(),
                            "登录失败！", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }
}



