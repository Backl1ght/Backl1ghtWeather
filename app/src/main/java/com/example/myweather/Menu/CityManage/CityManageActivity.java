package com.example.myweather.Menu.CityManage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweather.R;
import com.zhuxu.citypickerz.interfaces.CommonPickerXInterface;
import com.zhuxu.citypickerz.model.CityBean;
import com.zhuxu.citypickerz.model.CityPickerConfig;
import com.zhuxu.citypickerz.model.HeadModelConfig;
import com.zhuxu.citypickerz.modules.CityPickerXFragment;

import java.util.ArrayList;
import java.util.List;

public class CityManageActivity extends AppCompatActivity {

    // TODO: 添加左滑显示删除键等的功能(like SwipeView?)

    // TODO: 可以借助和风天气v7的城市信息搜索功能实现城市选择，而不是简单地用city picker

    private RecyclerView mCityRecyclerView;
    private CityAdapter mAdapter;
    private Context context;
    private Button mButton_addCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citymanage);

        mCityRecyclerView = findViewById(R.id.city_list);
        mCityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mButton_addCity = findViewById(R.id.add_city);
        context = getBaseContext();

        mButton_addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "功能尚未完成", Toast.LENGTH_SHORT).show();
                CityPickerXFragment cityPickerXFragment = CityPickerXFragment.startShow(CityManageActivity.this, getCityPickerConfig());
                cityPickerXFragment.setPickerXInterface(new CommonPickerXInterface() {
                    @Override
                    public void onClick(CityBean cityBean) {
//                        Toast.makeText(getApplicationContext(), "you clicked " + cityBean.getName() + " , this is a " + cityBean.getType(), Toast.LENGTH_SHORT).show();
                        CityDataHelper.addCity(getApplicationContext(), cityBean.getName());
                        updateUI();
                        cityPickerXFragment.dismiss();
                    }

                    @Override
                    public void onDismiss() {
//                        Toast.makeText(getApplicationContext(), "dismiss", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSearch(String s) {
//                        Toast.makeText(getApplicationContext(), "you search " + s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onReset() {
//                        Toast.makeText(getApplicationContext(), "reset", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        updateUI();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("管理城市");
    }

    private CityPickerConfig getCityPickerConfig() {
        List<CityBean> listLocation = new ArrayList<>();
        listLocation.add(new CityBean("济南n", "山东", "0546", "定位", CityBean.TYPE_STR_LOCATION));
        listLocation.add(new CityBean("黄河路n", "山东", "0546", "定位", CityBean.TYPE_STR_LOCATION));

        List<CityBean> listRecent = new ArrayList<>();
        listRecent.add(new CityBean("济南n", "山东", "0546", "最近", CityBean.TYPE_STR_RECENT));
        listRecent.add(new CityBean("深圳n", "山东", "0546", "最近", CityBean.TYPE_STR_RECENT));

        List<CityBean> listHot = new ArrayList<>();
        listHot.add(new CityBean("北京n", "山东", "0546", "热门", CityBean.TYPE_STR_LIST));
        listHot.add(new CityBean("深圳n", "山东", "0546", "热门", CityBean.TYPE_STR_LIST));

        HeadModelConfig locationConfig = new HeadModelConfig("当前定位", listLocation);
        // setTag以用于更新数据
//        locationConfig.setTag("当前定位");
        HeadModelConfig recentConfig = new HeadModelConfig("最近访问", listRecent, true, "近", 0, 0);
//        recentConfig.setTag("最近访问");
        HeadModelConfig hotConfig = new HeadModelConfig("热门城市", listHot, true, "热", 0, 0);
//        hotConfig.setTag("热门城市");
        // 生成配置类 CityPickerConfig 可点击查看详细备注
        // 最后参数设置为null  则表示使用自带的数据库列表 否则可在此实现自定义列表数据
        CityPickerConfig cityPickerConfig = new CityPickerConfig(locationConfig, recentConfig, hotConfig, null);
        return cityPickerConfig;
    }

    public void updateUI() {
        CityLab cityLab = CityLab.get(this);
        List<City> cities = cityLab.getCities();

        mAdapter = new CityAdapter(cities);
        mCityRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class CityHolder extends RecyclerView.ViewHolder {
        private City mCity;

        private TextView mTextView_CityName;
        private Button mButton_RemoveCity;

        public CityHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.city_item, parent, false));

            mTextView_CityName = itemView.findViewById(R.id.city_name);

            mButton_addCity = itemView.findViewById(R.id.city_remove);
            mButton_addCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 可能需要弹个窗确认
                    CityDataHelper.removeCity(getApplicationContext(), mCity.getCityName());
                    updateUI();
                }
            });
        }

        public void bind(City city) {
            mCity = city;

            String CityName = mCity.getCityName();
            mTextView_CityName.setText(CityName);
        }
    }

    private class CityAdapter extends RecyclerView.Adapter<CityHolder> {

        private List<City> mCities;

        public CityAdapter(List<City> cities) {
            mCities = cities;
        }

        @Override
        public CityHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            return new CityHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CityHolder holder, int position) {
            City city = mCities.get(position);
            holder.bind(city);
        }

        @Override
        public int getItemCount() {
            return mCities.size();
        }
    }
}
