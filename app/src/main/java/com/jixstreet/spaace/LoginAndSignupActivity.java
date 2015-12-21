package com.jixstreet.spaace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.jixstreet.spaace.activity.MainActivity;
import com.jixstreet.spaace.utils.CommonConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class LoginAndSignupActivity extends BaseActivity {

    private TextView loginTV;
    private TextView signupTV;
    private CallbackManager callbackManager;
    private LoginButton fbLoginBtn;

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
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();

        loginTV = (TextView) findViewById(R.id.login_tv);
        signupTV = (TextView) findViewById(R.id.signup_tv);
        fbLoginBtn = (LoginButton) findViewById(R.id.fb_login_btn);
        fbLoginBtn.setReadPermissions(Arrays.asList("user_status", "email", "public_profile"));
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
                startActivityForResult(new Intent(LoginAndSignupActivity.this, SignUpActivity.class), CommonConstants.LOGIN_REQUEST_CODE);
            }
        });

        findViewById(R.id.logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAndSignupActivity.this, MainActivity.class));
            }
        });

        fbLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                //TODO
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, gender, email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginAndSignupActivity.this, "Log in canceled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginAndSignupActivity.this, "failed to log in", Toast.LENGTH_LONG).show();
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
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
