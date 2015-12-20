package com.jixstreet.spaace.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jixstreet.spaace.R;
import com.jixstreet.spaace.model.ProfileUser;
import com.jixstreet.spaace.utils.APIAgent;
import com.jixstreet.spaace.utils.CommonConstants;
import com.jixstreet.spaace.utils.PermissionManager;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;


public class EditProfileFragment extends Fragment {

    private static final int SELECT_PICTURE = 2;
    private EditText email, fullName, region, inputPassword;
    private TextView saveProfile;
    private ImageView imageProfile;
    private ProgressBar progressBar;
    private String LOG_TAG = EditProfileFragment.class.getName();
    private View view;
    private ProfileUser profileUser;
    private boolean isChecked = true;
    private File filePicture;

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
        setCallBack();


        return view;
    }

    private void initUI() {

        email = (EditText) view.findViewById(R.id.email);
        fullName = (EditText) view.findViewById(R.id.input_full_name);
        region = (EditText) view.findViewById(R.id.input_region);
        imageProfile = (ImageView) view.findViewById(R.id.image_profile);
        inputPassword = (EditText) view.findViewById(R.id.input_password);
        saveProfile = (TextView) view.findViewById(R.id.save_profile);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

    }

    private void setCallBack() {
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionManager.getInstance().checkPermissionReadStorage(getActivity());
                Crop.pickImage(getActivity(), EditProfileFragment.this);
            }
        });

        inputPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (inputPassword.getRight() - inputPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (isChecked) {
                            inputPassword.setTransformationMethod(null);
                            isChecked = false;
                        } else {
                            inputPassword.setTransformationMethod(new PasswordTransformationMethod());
                            isChecked = true;
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    public void getProfile() {
        String url = CommonConstants.SERVICE_PROFILE;

        RequestParams requestParams = new RequestParams();
        requestParams.put(CommonConstants.ACCESS_TOKEN, SpaaceApplication.getInstance().getToken());


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
                fullName.setText(profileUser.profile.fullName);
                region.setText(profileUser.profile.region);
                Picasso.with(getActivity()).load(profileUser.profilePictureMedium).into(imageProfile);

                progressBar.setVisibility(View.GONE);
                saveProfile.setVisibility(View.VISIBLE);

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

    private void saveProfile() {
        String url = CommonConstants.SERVICE_PROFILE;

        RequestParams requestParams = new RequestParams();
        requestParams.put(CommonConstants.ACCESS_TOKEN, SpaaceApplication.getInstance().getToken());
        requestParams.put(CommonConstants.EMAIL, email.getText().toString());
        requestParams.put(CommonConstants.PROFILE_FULL_NAME, fullName.getText().toString());
        requestParams.put(CommonConstants.PROFILE_REGION, region.getText().toString());

        try {
            requestParams.put(CommonConstants.PROFILE_PICTURE, filePicture);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        APIAgent.patch(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
                saveProfile.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {
                progressBar.setVisibility(View.GONE);
                saveProfile.setVisibility(View.VISIBLE);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                System.out.println(response.toString());
                progressBar.setVisibility(View.GONE);
                saveProfile.setVisibility(View.VISIBLE);

                try {
                    JSONObject meta = response.getJSONObject(CommonConstants.META);
                    Toast.makeText(getActivity(), meta.getString(CommonConstants.MESSAGE), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), R.string.RTO, Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
                saveProfile.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR", errorResponse.toString() + "");

                progressBar.setVisibility(View.GONE);
                saveProfile.setVisibility(View.VISIBLE);

                try {
                    JSONObject meta = errorResponse.getJSONObject(CommonConstants.META);
                    Toast.makeText(getActivity(), meta.getString(CommonConstants.ERROR_DESCRIPTION), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Crop.REQUEST_PICK && resultCode == getActivity().RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().withMaxSize(1080,1080).start(getActivity(), EditProfileFragment.this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == getActivity().RESULT_OK) {
            Picasso.with(getActivity())
                    .load(Crop.getOutput(result))
                    .into(imageProfile);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermissionManager.MY_PERMISSIONS_REQUEST_READ_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case PermissionManager.MY_PERMISSIONS_REQUEST_READ_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}