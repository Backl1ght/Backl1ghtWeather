package com.example.myweather.WeatherFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myweather.R;
import com.example.myweather.Weather.CurrentWeather;
import com.example.myweather.info;

public class CurrentWeatherFragment extends Fragment {

    private static final String ARG_CITY_NAME = "CITY_NAME";

    private String mCityName;

    private TextView mTextView_location;
    private TextView mTextView_date;
    private TextView mTextView_tmp;
    private TextView mTextView_txt;
    private ImageView mImageView_icon;

    public CurrentWeatherFragment() {
    }

    public static CurrentWeatherFragment newInstance(String CityName) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
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
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        mTextView_location = view.findViewById(R.id.location);
        mTextView_date = view.findViewById(R.id.date);
        mTextView_tmp = view.findViewById(R.id.tmp);
        mTextView_txt = view.findViewById(R.id.txt);
        mImageView_icon = view.findViewById(R.id.img);

        return view;
    }

    void updateUI(CurrentWeather cw) {
        mTextView_location.setText(cw.getCity());
        mTextView_date.setText(cw.getTime());
        mTextView_tmp.setText(cw.getTmp() + info.getCh());
        mTextView_txt.setText(cw.getTxt());

        String str = "p" + cw.getCode();
        int id = getContext().getResources().getIdentifier(str, "mipmap", getContext().getPackageName());
        mImageView_icon.setImageResource(id);
    }
}