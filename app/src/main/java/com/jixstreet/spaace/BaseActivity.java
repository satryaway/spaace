package com.jixstreet.spaace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by satryaway on 12/8/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public abstract int setView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(setView());
        initUI();
        setCallBack();
    }

    public void initUI() {

    }

    public void setCallBack() {

    }
}
