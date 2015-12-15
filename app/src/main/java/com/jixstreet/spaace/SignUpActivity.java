package com.jixstreet.spaace;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jixstreet.spaace.utils.Utility;

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
