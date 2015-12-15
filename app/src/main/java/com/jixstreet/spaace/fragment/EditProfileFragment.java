package com.jixstreet.spaace.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jixstreet.spaace.R;
import com.jixstreet.spaace.model.ProfileUser;
import com.jixstreet.spaace.utils.APIAgent;
import com.jixstreet.spaace.utils.CommonConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class EditProfileFragment extends Fragment {

    EditText email;
    private String LOG_TAG = EditProfileFragment.class.getName();
    private View view;
    private ProfileUser profileUser;

    public static EditProfileFragment newInstance(Context context) {
        return new EditProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        initUI();
        getProfile();


        return view;
    }

    private void initUI() {

        email = (EditText) view.findViewById(R.id.email);

    }

    public void getProfile() {
        String url = CommonConstants.SERVICE_PROFILE;

        RequestParams requestParams = new RequestParams();
        requestParams.put(CommonConstants.ACCESS_TOKEN, SpaaceApplication.getInstance().getToken());
//        requestParams.put(CommonConstants.ACCESS_TOKEN, "1ee30455f1cc857b39127ac312fc8c6f472afbad71e549a2246233627a20d0e6");


        APIAgent.get(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                System.out.println(response.toString());

                Gson gson = new Gson();
                profileUser = gson.fromJson(response.toString(), ProfileUser.class);

                email.setText(profileUser.email);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), R.string.RTO, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {


            }
        });
    }
}