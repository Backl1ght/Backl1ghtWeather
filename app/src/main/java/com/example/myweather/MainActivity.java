package com.example.myweather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myweather.Menu.CityManage.City;
import com.example.myweather.Menu.CityManage.CityLab;
import com.example.myweather.Menu.CityManage.CityManageActivity;
import com.example.myweather.Menu.LocationActivity;
import com.example.myweather.Menu.SettingsActivity;
import com.example.myweather.Weather.CityWeather;
import com.example.myweather.Weather.CityWeatherLab;
import com.example.myweather.Weather.CurrentWeather;
import com.example.myweather.Weather.DailyWeather;
import com.example.myweather.Weather.HourlyWeather;
import com.example.myweather.WeatherFragment.CityWeatherFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final int ON_UPDATE = 7557;

    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;

    private CityWeatherPagerAdapter mAdapter;

    private ArrayList<CityWeatherFragment> mCityWeatherFragments;

    private Handler mHandler;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case ON_UPDATE:
                        updateUI();
                    default:
                        break;
                }
            }
        };

        mViewPager = (ViewPager) findViewById(R.id.city_weather_viewpager);
        mCircleIndicator = (CircleIndicator) findViewById(R.id.city_weather_circle_indicator);
        mAdapter = new CityWeatherPagerAdapter(getSupportFragmentManager());
        mCityWeatherFragments = new ArrayList<CityWeatherFragment>();

        ArrayList<City> Cities = CityLab.get(getApplicationContext()).getCities();

        for (City city : Cities) {
            Log.d("CREATE_FRAGMENT", "MainActivity: " + city.getCityName());
            mCityWeatherFragments.add(CityWeatherFragment.newInstance(city.getCityName()));
        }

        mViewPager.setAdapter(mAdapter);
        mCircleIndicator.setViewPager(mViewPager);

        // 是否开启定时推送天气情况
        NoteService.setServiceAlarm(getApplicationContext(), info.getFlag().equals("是"));

        // 更新数据
        new FetchWeatherTask().execute();
    }

    void updateUI() {
        for (CityWeatherFragment cwf: mCityWeatherFragments) {
            cwf.updateUI();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_location:
                Intent intent2 = new Intent(this, LocationActivity.class);
                startActivity(intent2);
                return true;
            case R.id.settings:
                Intent intent3 = new Intent(this, SettingsActivity.class);
                startActivity(intent3);
                return true;
            case R.id.cityset:
                Intent intent4 = new Intent(this, CityManageActivity.class);
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class CityWeatherPagerAdapter extends FragmentPagerAdapter {

        public CityWeatherPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return mCityWeatherFragments.get(position);
        }

        @Override
        public int getCount() {
            return mCityWeatherFragments.size();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class FetchWeatherTask extends AsyncTask<Void, Void, Void> {

        private String key = "********************************";

        public FetchWeatherTask() { }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ArrayList<City> cities = CityLab.get(getApplicationContext()).getCities();
                String url = null, result = null;
                OkHttpClient client = new OkHttpClient();
                Request request = null;
                Call call = null;
                Response response = null;
                for (City city: cities) {
                    // Get current weather of city
                    url = "https://free-api.heweather.net/s6/weather/now?" + "location=" + city.getCityName() + "&key=" + key;
                    request = new Request.Builder()
                            .get()
                            .url(url)
                            .build();
                    call = client.newCall(request);
                    response = call.execute();
                    result = response.body().string();
                    CurrentWeather cw = CurrentWeather.parseJSON(result);
                    Log.d("ASYNC", "CURRENT: " + result);

                    // Get hourly weather of city
                    url = "https://free-api.heweather.net/s6/weather/hourly?" + "location=" + city.getCityName() + "&key=" + key;
                    request = new Request.Builder()
                            .get()
                            .url(url)
                            .build();
                    call = client.newCall(request);
                    response = call.execute();
                    result = response.body().string();
                    ArrayList<HourlyWeather> hws = HourlyWeather.parseJSONList(result);
                    Log.d("ASYNC", "HOURLY: " + result);

                    // Get daily weather of city
                    url = "https://free-api.heweather.net/s6/weather/forecast?" + "location=" + city.getCityName() + "&key=" + key;
                    request = new Request.Builder()
                            .get()
                            .url(url)
                            .build();
                    call = client.newCall(request);
                    response = call.execute();
                    result = response.body().string();
                    ArrayList<DailyWeather> dws = DailyWeather.parseJSONList(result);
                    Log.d("ASYNC", "DAILY: " + result);

                    CityWeatherLab.getCityWeatherLab().put(city.getCityName(), new CityWeather(cw, hws, dws));
                }

                
                Message msg = mHandler.obtainMessage();
                msg.what = ON_UPDATE;
                mHandler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
