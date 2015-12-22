package com.jixstreet.spaace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.jixstreet.spaace.activity.MainActivity;
import com.jixstreet.spaace.model.ProfileUser;
import com.jixstreet.spaace.utils.CommonConstants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class LoginAndSignupActivity extends BaseActivity {

    private TextView loginTV;
    private TextView signupTV;
    private CallbackManager callbackManager;
    private LoginButton fbLoginBtn;
    private AccessToken accessToken;
    private String token;
    private ProfileUser profileUser;

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
                                Log.d("FB Response", object.toString());
                                accessToken = AccessToken.getCurrentAccessToken();
                                token = accessToken.getToken();

                                String url = CommonConstants.SERVICE_LOGIN_FB;

                                RequestParams requestParams = new RequestParams();
                                requestParams.put(CommonConstants.ACCESS_TOKEN, token);

                                final ProgressDialog progressDialog = new ProgressDialog(LoginAndSignupActivity.this);
                                progressDialog.setMessage(getResources().getString(R.string.logging_in));

                                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                asyncHttpClient.addHeader(CommonConstants.API_TOKEN, getString(R.string.api_token_header));
                                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onStart() {
                                        progressDialog.show();
                                    }

                                    @Override
                                    public void onFinish() {
                                        progressDialog.hide();
                                    }

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        try {
                                            JSONObject meta = response.getJSONObject(CommonConstants.META);
                                            Toast.makeText(LoginAndSignupActivity.this, meta.getString(CommonConstants.MESSAGE), Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Gson gson = new Gson();
                                        profileUser = gson.fromJson(response.toString(), ProfileUser.class);
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                        Toast.makeText(LoginAndSignupActivity.this, R.string.RTO, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                        try {
                                            JSONObject meta = errorResponse.getJSONObject(CommonConstants.META);
                                            Toast.makeText(LoginAndSignupActivity.this, meta.getString(CommonConstants.ERROR_DESCRIPTION), Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
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
