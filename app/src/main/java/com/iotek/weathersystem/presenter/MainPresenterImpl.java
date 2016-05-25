package com.iotek.weathersystem.presenter;

import com.iotek.weathersystem.R;
import com.iotek.weathersystem.net.IMainReqData;
import com.iotek.weathersystem.net.MainReqDataImpl;
import com.iotek.weathersystem.ui.IMainView;
import com.iotek.weathersystem.utils.Tools;

import java.util.List;

/**
 * Created by fhm on 2016/5/25.
 */
public class MainPresenterImpl implements IMainPresenter, IMainReqData.OnFinishedListener {
    private IMainView mainView;
    private IMainReqData reqData;

    public MainPresenterImpl(IMainView mainView) {
        this.mainView = mainView;
        reqData = new MainReqDataImpl();
    }

    @Override
    public void onResume() {
        if (mainView != null) {
            mainView.showProgress();
        }

        reqData.reqData(this);
    }

    @Override
    public void onCheckedChanged(int checkedId) {
        if (mainView != null) {
            switch (checkedId){
                case R.id.rbWeather:
                    Tools.log("rbWeather");
                    break;
                case R.id.rbTokikage:
                    Tools.log("rbTokikage");

                    break;
                case R.id.rbMy:
                    Tools.log("rbMy");

                    break;
            }
            mainView.showMessage(String.format("%d onCheckedChanged", checkedId + 1));
        }
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onFinished(List<String> items) {
        if (mainView != null) {
            mainView.setItems(items);
            mainView.hideProgress();
        }
    }
}
