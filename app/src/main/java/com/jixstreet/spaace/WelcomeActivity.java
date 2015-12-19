package com.jixstreet.spaace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jixstreet.spaace.BaseActivity;
import com.jixstreet.spaace.R;
import com.jixstreet.spaace.activity.MainActivity;

/**
 * Created by satryaway on 12/19/2015.
 * welcome page to request if user wants to be notified for new product
 */
public class WelcomeActivity extends BaseActivity {
    private TextView yesNotifityTV;
    private TextView noNotifityTV;
    private boolean isNotify = false;

    @Override
    public int setView() {
        return R.layout.welcome_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initUI() {
        super.initUI();

        yesNotifityTV = (TextView) findViewById(R.id.yes_notify_tv);
        noNotifityTV = (TextView) findViewById(R.id.no_notify_mey);
    }

    @Override
    public void setCallBack() {
        super.setCallBack();

        yesNotifityTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNotify = true;
                startNewPage();
            }
        });

        noNotifityTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewPage();
            }
        });
    }

    private void startNewPage() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
