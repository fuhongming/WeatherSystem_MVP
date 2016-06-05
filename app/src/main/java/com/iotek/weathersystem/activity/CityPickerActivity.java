package com.iotek.weathersystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.iotek.weathersystem.R;
import com.iotek.weathersystem.adapter.CityListAdapter;
import com.iotek.weathersystem.adapter.ResultListAdapter;
import com.iotek.weathersystem.commom.MyApplication;
import com.iotek.weathersystem.db.DBManager;
import com.iotek.weathersystem.db.Db;
import com.iotek.weathersystem.model.City;
import com.iotek.weathersystem.model.LocateState;
import com.iotek.weathersystem.service.LocationService;
import com.iotek.weathersystem.utils.StringUtils;
import com.iotek.weathersystem.utils.ToastUtils;
import com.iotek.weathersystem.view.CustomDialog;
import com.iotek.weathersystem.view.SideLetterBar;

import java.util.List;

/**
 * author zaaach on 2016/1/26.
 */
public class CityPickerActivity extends Activity implements View.OnClickListener {
    private ListView mListView;        //所有城市列表
    private ListView mResultListView; //搜索城市结果
    private SideLetterBar mLetterBar; //侧边栏
    private EditText searchBox;      //搜索城市输入框
    private ImageView clearBtn;      //清空输入框内容
    private Button backBtn;       //返回天气界面
    private ViewGroup emptyView;  //搜索框布局

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<City> mAllCities;
    private DBManager dbManager;

    private InputMethodManager mInputMethodManager;
    CustomDialog dialog;

    private LocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        initData();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // -----------location config ------------
        locationService = ((MyApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();
    }


    /*****
     * 定位结果回调，重写onReceiveLocation方法
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
//                String loc = StringUtils.extractLocation(location.getCity(), district);
//                String city=location.getAddrStr();
                String city=location.getCity().substring(0,location.getCity().length()-1);


                mCityAdapter.updateLocateState(LocateState.SUCCESS,city );
            } else {
                mCityAdapter.updateLocateState(LocateState.FAILED, null);
            }
        }

    };

    private void initData() {
        dbManager = new DBManager(this);
        dbManager.copyDBFile();
        mAllCities = dbManager.getAllCities();
        mCityAdapter = new CityListAdapter(this, mAllCities);

        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {   //关注城市、所有城市列表item项点击事件响应
                back(name);
            }

            @Override
            public void onLocateClick() {  //定位
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                locationService.start();
            }
        });

        mResultAdapter = new ResultListAdapter(this, null);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview_all_city);
        mListView.setAdapter(mCityAdapter);

        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        searchBox = (EditText) findViewById(R.id.et_search);
        searchBox.addTextChangedListener(new TextWatcher() {//监听编辑框是否改变
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }


            @Override
            public void afterTextChanged(Editable s) {  //编辑框内容改变之后回调
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });

        emptyView = (ViewGroup) findViewById(R.id.empty_view);
        mResultListView = (ListView) findViewById(R.id.listview_search_result);
        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //搜索结果item项
                back(mResultAdapter.getItem(position).getName());
            }
        });

        clearBtn = (ImageView) findViewById(R.id.iv_search_clear);
        backBtn = (Button) findViewById(R.id.back);

        clearBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        findViewById(R.id.addCity).setOnClickListener(this);
    }

    private void back(String city) {
        Intent data = new Intent();
        data.putExtra("city", city);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_clear:
                searchBox.setText("");
                clearBtn.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
                mResultListView.setVisibility(View.GONE);
                break;
            case R.id.back:
                finish();
                break;

            case R.id.addCity:
                dialog();
                break;
        }
    }

    // 弹窗
    private void dialog() {
        dialog = new CustomDialog(this);
        dialog.setTitle("添加城市");
        final EditText editText = (EditText) dialog.getEditText();//方法在CustomDialog中实现
        dialog.show();
        dialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                mInputMethodManager.showSoftInput(editText, 0);
//                mInputMethodManager.showSoftInputFromInputMethod(searchBox.getWindowToken(), 0);
                mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                String cityName = editText.getText().toString();
                if (!cityName.equals("") && cityName != null) {
                    dialog.dismiss();
                    City city = new City();
                    city.setName(cityName);
                    mCityAdapter.addCity(city);
                    Db.save(city);
                } else {
                    ToastUtils.showToast(CityPickerActivity.this, "输入为空");
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
