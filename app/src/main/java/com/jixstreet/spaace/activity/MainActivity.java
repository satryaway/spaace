package com.jixstreet.spaace.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jixstreet.spaace.LoginAndSignupActivity;
import com.jixstreet.spaace.R;
import com.jixstreet.spaace.extensions.BlurActionBarDrawerToggle;
import com.jixstreet.spaace.fragment.ContactFragment;
import com.jixstreet.spaace.fragment.EditProfileFragment;
import com.jixstreet.spaace.fragment.ExploreFragment;
import com.jixstreet.spaace.fragment.QuoteFragment;
import com.jixstreet.spaace.fragment.SpaaceApplication;
import com.jixstreet.spaace.model.ProfileUser;
import com.jixstreet.spaace.utils.APIAgent;
import com.jixstreet.spaace.utils.CommonConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TextView toolbarTitle;
    private DrawerLayout drawer;

    private Fragment fragment;
    private View navigationDrawerHeaderView;
    private ImageView imageProfile;
    private TextView nameProfile;
    private ProfileUser profileUser;
    private BlurActionBarDrawerToggle blurActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        setCallBack();

        if (SpaaceApplication.getInstance().isLoggedIn())
            putData();
        else
            requestUserObject();
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        navigationDrawerHeaderView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        imageProfile = (ImageView) navigationDrawerHeaderView.findViewById(R.id.image_profile);
        nameProfile = (TextView) navigationDrawerHeaderView.findViewById(R.id.name_profile);

        navigationView.addHeaderView(navigationDrawerHeaderView);

        fragment = ExploreFragment.newInstance(this);
        toolbarTitle.setText(getString(R.string.explore));
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
        }

        /*BlurAndDimView blurAndDimView = (BlurAndDimView) findViewById(R.id.blurrer);

        blurActionBarDrawerToggle = new BlurActionBarDrawerToggle(this, drawer, 0,0, blurAndDimView);
        drawer.setDrawerListener(blurActionBarDrawerToggle);*/
    }

    private void setCallBack() {
        navigationDrawerHeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpaaceApplication.getInstance().isLoggedIn()) {
                    fragment = EditProfileFragment.newInstance(MainActivity.this);
                    toolbarTitle.setText(getString(R.string.edit_profile));
                    drawer.closeDrawer(GravityCompat.START);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginAndSignupActivity.class);
                    startActivityForResult(intent, CommonConstants.LOGIN_REQUEST_CODE);
                }
            }
        });
    }

    private void requestUserObject() {
        String url = CommonConstants.SERVICE_PROFILE;

        RequestParams requestParams = new RequestParams();
        requestParams.put(CommonConstants.ACCESS_TOKEN, CommonConstants.DEFAULT_TOKEN);

        APIAgent.get(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response.toString());

                Gson gson = new Gson();
                profileUser = gson.fromJson(response.toString(), ProfileUser.class);

                savePreferences();

                putData();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MainActivity.this, R.string.RTO, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = SpaaceApplication.getInstance().getSharedPreferences().edit();
        editor.putString(CommonConstants.FULL_NAME, profileUser.profile.fullName);
        editor.putString(CommonConstants.PROFILE_THUMB, profileUser.profilePictureThumb);
        editor.apply();
    }

    private void putData() {
        String profileName = SpaaceApplication.getInstance().getSharedPreferences().getString(CommonConstants.FULL_NAME, getResources().getString(R.string.login_or_signup));
        String profileThumb = SpaaceApplication.getInstance().getSharedPreferences().getString(CommonConstants.PROFILE_THUMB, "");
        if (profileThumb.isEmpty())
            Picasso.with(this).load(R.drawable.ic_avatar).into(imageProfile);
        else
            Picasso.with(this).load(profileThumb).into(imageProfile);
        nameProfile.setText(profileName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_explore, menu);
        // Select search item
        final MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setVisible(true);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search));

        ((EditText) searchView.findViewById(R.id.search_src_text))
                .setHintTextColor(getResources().getColor(android.R.color.darker_gray));

        SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        };
        searchView.setOnQueryTextListener(onQuerySearchView);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_explore) {
            fragment = ExploreFragment.newInstance(this);
            toolbarTitle.setText(getString(R.string.explore));
        } else if (id == R.id.nav_find_pro) {
//            fragment = NewsFragment.newInstance();
        } else if (id == R.id.nav_ideas) {
//            fragment = FeedbackFragment.newInstance();
        } else if (id == R.id.nav_get_quotes) {
            fragment = QuoteFragment.newInstance(this);
            toolbarTitle.setText(getString(R.string.get_quotes));
        } else if (id == R.id.nav_store) {
//            fragment = AboutFragment.newInstance();
        } else if (id == R.id.nav_contact) {
            fragment = ContactFragment.newInstance(this);
            toolbarTitle.setText(getString(R.string.contact));
        }


        if (fragment != null) {
            fragmentTransaction.replace(R.id.content, fragment);
            fragmentTransaction.commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
