package com.jixstreet.spaace;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jixstreet.spaace.utils.CommonConstants;
import com.jixstreet.spaace.utils.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by satryaway on 12/11/2015.
 * activity to handle sign up
 */
public class SignUpActivity extends BaseActivity {
    private ImageView closeActivityIV;
    private EditText fullNameET;
    private EditText emailET;
    private EditText passwordET;
    private TextView signUpTV;
    private TextView messageTV;
    private ProgressBar loadingPB;

    @Override
    public int setView() {
        return R.layout.signup_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initUI() {
        super.initUI();

        closeActivityIV = (ImageView) findViewById(R.id.close_activity_iv);
        fullNameET = (EditText) findViewById(R.id.full_name_et);
        emailET = (EditText) findViewById(R.id.email_et);
        passwordET = (EditText) findViewById(R.id.password_et);
        signUpTV = (TextView) findViewById(R.id.signup_tv);
        messageTV = (TextView) findViewById(R.id.message_tv);
        loadingPB = (ProgressBar) findViewById(R.id.loading_pb);
    }

    @Override
    public void setCallBack() {
        super.setCallBack();

        closeActivityIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormFilled()) {
                    doRegister();
                }
            }
        });

        TextWatcher editTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpTV.setText(getResources().getString(R.string.sign_up));
                messageTV.setVisibility(View.INVISIBLE);
                checkIsFieldFilled();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        fullNameET.addTextChangedListener(editTextWatcher);
        emailET.addTextChangedListener(editTextWatcher);
        passwordET.addTextChangedListener(editTextWatcher);
    }

    private void doRegister() {
        String url = CommonConstants.SERVICE_SIGN_UP;

        RequestParams requestParams = new RequestParams();
        requestParams.put(CommonConstants.EMAIL, emailET.getText().toString());
        requestParams.put(CommonConstants.PASSWORD, passwordET.getText().toString());
        requestParams.put(CommonConstants.GRANT_TYPE, CommonConstants.PASSWORD);
        requestParams.put(CommonConstants.ATTRIBUTE_FULL_NAME, CommonConstants.PASSWORD);

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.addHeader(CommonConstants.API_TOKEN, getString(R.string.api_token_header));
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                signUpTV.setVisibility(View.INVISIBLE);
                loadingPB.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                signUpTV.setVisibility(View.VISIBLE);
                signUpTV.setText(R.string.Succeed);

                try {
                    JSONObject object = response.getJSONObject(CommonConstants.META);
//                    Toast.makeText(SignUpActivity.this, object.getString(CommonConstants.MESSAGE), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Handler mHandler = new Handler(Looper.getMainLooper());
                int SPLASH_TIME_OUT = 2000;
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent = new Intent(SignUpActivity.this, WelcomeActivity.class);
                        startActivityForResult(intent, CommonConstants.LOGIN_REQUEST_CODE);

                        Intent intent1 = new Intent();
                        setResult(RESULT_OK, intent1);
                        onBackPressed();
                    }
                }, SPLASH_TIME_OUT);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(SignUpActivity.this, R.string.RTO, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                signUpTV.setVisibility(View.VISIBLE);
                signUpTV.setText(R.string.register_failed);
                signUpTV.setBackgroundColor(getResources().getColor(R.color.failed_red));
                messageTV.setVisibility(View.VISIBLE);

                try {
                    JSONObject meta = errorResponse.getJSONObject(CommonConstants.META);
                    messageTV.setText(meta.getString(CommonConstants.ERROR_DESCRIPTION));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private boolean isFormFilled() {
        int totalFilled = 0;

        if (fullNameET.getText().toString().isEmpty())
            fullNameET.setError(getString(R.string.should_not_be_empty));
        else
            totalFilled++;

        if (!Utility.isEmailValid(emailET.getText().toString()))
            emailET.setError(getString(R.string.email_not_valid));
        else
            totalFilled++;

        if (passwordET.getText().toString().length() < 8)
            passwordET.setError("Password should have minimal 8 characters");
        else
            totalFilled++;

        return totalFilled == 3;
    }

    private void checkIsFieldFilled() {
        if (!emailET.getText().toString().isEmpty() && !passwordET.getText().toString().isEmpty() && !fullNameET.getText().toString().isEmpty()) {
            signUpTV.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            signUpTV.setBackgroundColor(getResources().getColor(R.color.darker_gray));
        }
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
