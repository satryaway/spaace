package com.jixstreet.spaace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jixstreet.spaace.activity.MainActivity;
import com.jixstreet.spaace.utils.CommonConstants;

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
                startActivityForResult(new Intent(LoginAndSignupActivity.this, LoginActivity.class), CommonConstants.LOGIN_REQUEST_CODE);
            }
        });

        signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAndSignupActivity.this, SignUpActivity.class));
            }
        });

        findViewById(R.id.logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAndSignupActivity.this, MainActivity.class));

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CommonConstants.LOGIN_REQUEST_CODE && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
