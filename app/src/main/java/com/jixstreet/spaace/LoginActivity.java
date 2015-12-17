package com.jixstreet.spaace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jixstreet.spaace.activity.MainActivity;
import com.jixstreet.spaace.fragment.SpaaceApplication;
import com.jixstreet.spaace.utils.APIAgent;
import com.jixstreet.spaace.utils.CommonConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by satryaway on 12/8/2015.
 * activity for login
 */
public class LoginActivity extends BaseActivity {

    private ImageView closeActivity;
    private EditText emailET;
    private EditText passwordET;
    private TextView loginTV;
    private ProgressBar loadingPB;
    private boolean isFormFilled;
    private TextView messageTV;

    @Override
    public int setView() {
        return R.layout.login_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initUI() {
        super.initUI();

        closeActivity = (ImageView) findViewById(R.id.close_activity_iv);
        emailET = (EditText) findViewById(R.id.email_et);
        passwordET = (EditText) findViewById(R.id.password_et);
        loginTV = (TextView) findViewById(R.id.login_tv);
        loadingPB = (ProgressBar) findViewById(R.id.loading_pb);
        messageTV = (TextView) findViewById(R.id.message_tv);
    }

    @Override
    public void setCallBack() {
        super.setCallBack();
        closeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextWatcher editTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginTV.setText(getResources().getString(R.string.login));
                messageTV.setVisibility(View.INVISIBLE);
                checkIsFieldFilled();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        emailET.addTextChangedListener(editTextWatcher);
        passwordET.addTextChangedListener(editTextWatcher);

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormFilled) {
                    requestAuth();
                }
            }
        });
    }

    private void requestAuth() {
        String url = CommonConstants.SERVICE_LOGIN;

        RequestParams requestParams = new RequestParams();
        requestParams.put(CommonConstants.EMAIL, emailET.getText().toString());
        requestParams.put(CommonConstants.PASSWORD, passwordET.getText().toString());
        requestParams.put(CommonConstants.GRANT_TYPE, CommonConstants.PASSWORD);

        APIAgent.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                loginTV.setVisibility(View.INVISIBLE);
                messageTV.setVisibility(View.INVISIBLE);
                loginTV.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                loadingPB.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                loginTV.setVisibility(View.VISIBLE);
                loginTV.setText(R.string.Succeed);

                try {
                    savePreferences(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);

                onBackPressed();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(LoginActivity.this, R.string.RTO, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                loginTV.setVisibility(View.VISIBLE);
                loginTV.setText(R.string.login_failed);
                loginTV.setBackgroundColor(getResources().getColor(R.color.failed_red));
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

    private void savePreferences(JSONObject response) throws JSONException {
        SpaaceApplication.getInstance().setToken(response.getString(CommonConstants.ACCESS_TOKEN));
        SharedPreferences.Editor editor = SpaaceApplication.getInstance().getSharedPreferences().edit();
        editor.putBoolean(CommonConstants.IS_LOGGED_IN, true);
        editor.apply();
    }

    private void checkIsFieldFilled() {
        if (!emailET.getText().toString().isEmpty() && !passwordET.getText().toString().isEmpty()) {
            loginTV.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            isFormFilled = true;
        } else {
            loginTV.setBackgroundColor(getResources().getColor(R.color.darker_gray));
            isFormFilled = false;
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
