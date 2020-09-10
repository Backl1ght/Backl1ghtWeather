package com.example.myweather.WeatherFragment;

import android.content.Intent;
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
import com.example.myweather.Weather.DailyWeather;
import com.example.myweather.info;

import java.util.ArrayList;
import java.util.List;

public class DailyWeatherFragment extends Fragment {

    private static final String ARG_CITY_NAME = "CITY_NAME";

    private String mCityName;

    private RecyclerView mDailyWeatherRecyclerView;
    private DailyWeatherAdapter mAdapter;

    public DailyWeatherFragment() {
    }

    public static DailyWeatherFragment newInstance(String CityName) {
        DailyWeatherFragment fragment = new DailyWeatherFragment();
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
        View view = inflater.inflate(R.layout.fragment_daily_weather, container, false);

        mDailyWeatherRecyclerView = view.findViewById(R.id.daily_forecast);
        mDailyWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    void updateUI(ArrayList<DailyWeather> dws) {
        mAdapter = new DailyWeatherFragment.DailyWeatherAdapter(dws);
        mDailyWeatherRecyclerView.setAdapter(mAdapter);
    }

    private class DailyWeatherHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private DailyWeather mDailyWeather;

        private TextView mTextView_date_week;
        private TextView mTextView_daily_cond;
        private TextView mTextView_daily_tmp;
        private ImageView mImageView_icon_cond;

        public DailyWeatherHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.dailyweather_item, parent, false));
            itemView.setOnClickListener(this);

            mTextView_date_week = itemView.findViewById(R.id.date_week);
            mTextView_daily_cond = itemView.findViewById(R.id.text_daily_cond);
            mTextView_daily_tmp = itemView.findViewById(R.id.tmp_day);
            mImageView_icon_cond = itemView.findViewById(R.id.weather_icon_daily);
        }

        public void bind(DailyWeather dailyWeather) {
            mDailyWeather = dailyWeather;

            mTextView_date_week.setText(mDailyWeather.getWhatDay());

            mTextView_daily_cond.setText(mDailyWeather.getTxt_d());

            String str = mDailyWeather.getTmp_max() + "/" + mDailyWeather.getTmp_min() + info.getCh();
            mTextView_daily_tmp.setText(str);

            str = "p" + mDailyWeather.getCode_d();
            int id = getContext().getResources().getIdentifier(str, "mipmap", getContext().getPackageName());
            mImageView_icon_cond.setImageResource(id);
        }

        @Override
        public void onClick(View view) {
            // TODO: 如果要点击进入详情页，需要重写这个方法
        }
    }

    private class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherHolder> {
        private List<DailyWeather> mDailyWeathers;

        public DailyWeatherAdapter(List<DailyWeather> DailyWeathers) {
            mDailyWeathers = DailyWeathers;
        }

        @Override
        public DailyWeatherHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DailyWeatherHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(DailyWeatherHolder holder, int position) {
            DailyWeather dailyWeather = mDailyWeathers.get(position);
            holder.bind(dailyWeather);
        }

        @Override
        public int getItemCount() {
            return mDailyWeathers.size();
        }
    }
}