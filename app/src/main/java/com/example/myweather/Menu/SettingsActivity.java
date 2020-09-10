package com.example.myweather.Menu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myweather.NoteService;
import com.example.myweather.R;
import com.example.myweather.info;

public class SettingsActivity extends AppCompatActivity {

    TextView mTextView_ch;
    TextView mTextView_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mTextView_ch = findViewById(R.id.ch);
        mTextView_flag = findViewById(R.id.flag);

        mTextView_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch_Clicked();
            }
        });

        mTextView_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_Clicked();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("设置");
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

    private void ch_Clicked() {
        final String[] items = new String[]{"℃", "℉",};
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择")
                .setIcon(R.mipmap.ic_launcher)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        info.setCh(items[i]);
                        mTextView_ch.setText(info.getCh());
                    }
                }).create();
        alertDialog.show();
    }

    private void flag_Clicked() {
        final String[] items = new String[]{"是", "否",};
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择")
                .setIcon(R.mipmap.ic_launcher)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        info.setFlag(items[i]);
                        mTextView_flag.setText(info.getFlag());
                        NoteService.setServiceAlarm(getApplicationContext(), Boolean.valueOf(info.getFlag()));
                    }
                })
                .create();
        alertDialog.show();
    }
}
