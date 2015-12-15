package com.jixstreet.spaace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginAndSignupActivity extends BaseActivity {

    private TextView loginTV;
    private TextView signupTV;

    @Override
    public int setView() {
        return R.layout.login_and_signup_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initUI() {
        super.initUI();

        loginTV = (TextView) findViewById(R.id.login_tv);
        signupTV = (TextView) findViewById(R.id.signup_tv);
    }

    @Override
    public void setCallBack() {
        super.setCallBack();

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAndSignupActivity.this, LoginActivity.class));
            }
        });

        signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAndSignupActivity.this, SignUpActivity.class));
            }
        });
    }
}
