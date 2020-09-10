package com.example.myweather.WeatherFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweather.R;
import com.example.myweather.Weather.CityWeather;
import com.example.myweather.Weather.HourlyWeather;
import com.example.myweather.info;

import java.util.ArrayList;
import java.util.List;

public class HourlyWeatherFragment extends Fragment {

    private static final String ARG_CITY_NAME = "CITY_NAME";

    private String mCityName;

    private RecyclerView mHourlyWeatherRecyclerView;
    private HourlyWeatherFragment.HourlyWeatherAdapter mAdapter;

    public HourlyWeatherFragment() {
    }

    public static HourlyWeatherFragment newInstance(String CityName) {
        HourlyWeatherFragment fragment = new HourlyWeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY_NAME, CityName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCityName = getArguments().getString(ARG_CITY_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hourly_weather, container, false);

        mHourlyWeatherRecyclerView = view.findViewById(R.id.hourly_forecast);

        // 设置横向滚动
        mHourlyWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        return view;
    }

    void updateUI(ArrayList<HourlyWeather> list) {
        mAdapter = new HourlyWeatherFragment.HourlyWeatherAdapter(list);
        mHourlyWeatherRecyclerView.setAdapter(mAdapter);
    }

    private class HourlyWeatherHolder extends RecyclerView.ViewHolder {

        private HourlyWeather mHourlyWeather;

        private TextView mTextView_Hour;
        private TextView mTextView_Hourly_tmp;
        private ImageView mImageView_icon_cond;

        public HourlyWeatherHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.hourlyweather_item, parent, false));

            mTextView_Hour = itemView.findViewById(R.id.text_hour);
            mTextView_Hourly_tmp = itemView.findViewById(R.id.tmp_hour);
            mImageView_icon_cond = itemView.findViewById(R.id.icon_hour);

        }

        public void bind(HourlyWeather HourlyWeather) {
            mHourlyWeather = HourlyWeather;

            String str = HourlyWeather.getTime();
            str = str.substring(str.length() - 5);
            mTextView_Hour.setText(str);

            str = mHourlyWeather.getTmp() + info.getCh();
            mTextView_Hourly_tmp.setText(str);

            str = "p" + mHourlyWeather.getCode();
            int id = getContext().getResources().getIdentifier(str, "mipmap", getContext().getPackageName());
            mImageView_icon_cond.setImageResource(id);
        }
    }

    private class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherFragment.HourlyWeatherHolder> {
        private List<HourlyWeather> mHourlyWeathers;

        public HourlyWeatherAdapter(List<HourlyWeather> HourlyWeathers) {
            mHourlyWeathers = HourlyWeathers;
        }

        @Override
        public HourlyWeatherFragment.HourlyWeatherHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new HourlyWeatherFragment.HourlyWeatherHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(HourlyWeatherFragment.HourlyWeatherHolder holder, int position) {
            HourlyWeather HourlyWeather = mHourlyWeathers.get(position);
            holder.bind(HourlyWeather);
        }

        @Override
        public int getItemCount() {
            return mHourlyWeathers.size();
        }
    }
}