package com.iotek.weathersystem.activity;

import com.iotek.weathersystem.R;
import com.iotek.weathersystem.db.SqliteData;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	SQLiteDatabase database;
	private SqliteData sqlitedata;

	private final String TABLE_NAME = "ueser";
	private final String NAME = "name";
	private final String PASSWORD = "password";
	Button button1_zcqueding,button1_zhuce;
	TextView textView;
	EditText editText1_zhaohao;
	EditText editText2_mima;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		sqlitedata = new SqliteData(RegisterActivity.this, // 上下文
				"user.db", null, 1 // 版本号(更新数据库) newversion 必须>= oldversion
		);
		database = sqlitedata.getWritableDatabase();
		editText1_zhaohao = (EditText) findViewById(R.id.editText1_zhaohao);
		editText2_mima = (EditText) findViewById(R.id.editText2_mima);
		textView = (TextView) findViewById(R.id.textView1);
		button1_zcqueding = (Button) findViewById(R.id.button1_zcqueding);
		button1_zcqueding.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String namezh = editText1_zhaohao.getText().toString();
				String passmm = editText2_mima.getText().toString();

				ContentValues values = new ContentValues();
				
				if (namezh != null && passmm != null)
				{
					String name = zhanghao(namezh);
					String password = mima(passmm);
				 if(namezh != null&& namezh.equals(name)) {
					Toast.makeText(getBaseContext(), "该帐号已注册",
							Toast.LENGTH_SHORT).show();
					editText1_zhaohao.setText("");
					editText2_mima.setText("");
					
				} else if(passmm.length() <= 3){
					Toast.makeText(getBaseContext(),
							"密码太过简单，为了你帐号的安全请重新输入", Toast.LENGTH_SHORT)
							.show();
					editText2_mima.setText("");
				}else{
				
				
				 values.put(NAME, // 对应字段名
							namezh); // 值
					values.put(PASSWORD, passmm);
					long rowID = database.insert(TABLE_NAME, // 表名
							null, // values为空时的默认值
							values);
					Toast.makeText(getBaseContext(),
							"恭喜你注册成功!", Toast.LENGTH_SHORT)
							.show();
					Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
					RegisterActivity.this.startActivity(intent);
					
					
				
				}
			

						textView.setText("密码账号:" + "  " + password + name);
					}
				
			}

			private String zhanghao(String namezh) {
				String selection = "name = ?";
				String[] selectionArgs = { namezh };
				Cursor cursor = database.query(TABLE_NAME, null, selection,
						selectionArgs, null, null, null);
				String name = "";

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					name = cursor.getString(cursor.getColumnIndex(NAME));
				}
				return name;
			}

			private String mima(String passmm) {
				String selection = "password = ?";
				String[] selectionArgs = { passmm };
				Cursor cursor = database.query(TABLE_NAME, null,
						selection, selectionArgs, null, null, null);
				String password = "";

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {

					password = cursor.getString(cursor
							.getColumnIndex(PASSWORD));
				}
				return password;
			
			}
		});
	}
}
