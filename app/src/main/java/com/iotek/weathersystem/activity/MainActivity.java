package com.iotek.weathersystem.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.iotek.weathersystem.R;
import com.iotek.weathersystem.adapter.MainAdapter;
import com.iotek.weathersystem.model.Result;
import com.iotek.weathersystem.presenter.IMainPresenter;
import com.iotek.weathersystem.presenter.MainPresenterImpl;
import com.iotek.weathersystem.ui.IMainView;
import com.iotek.weathersystem.utils.Tools;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;

import java.util.List;

public class MainActivity extends Activity implements IMainView {
    @ViewInject(R.id.rg)
    RadioGroup rg;

    @ViewInject(R.id.progress)
    private ProgressBar progressBar;
    @ViewInject(R.id.lv)
    ListView lv;
    private IMainPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        presenter = new MainPresenterImpl(this);
    }

    @OnRadioGroupCheckedChange(R.id.rg)
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        presenter.onCheckedChanged(checkedId);

    }

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showData(Result result) {
        MainAdapter mainAdapter = new MainAdapter(this);
        mainAdapter.setData(result);
        lv.setAdapter(mainAdapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
