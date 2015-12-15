package com.jixstreet.spaace.activity;

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

import com.jixstreet.spaace.R;
import com.jixstreet.spaace.fragment.ContactFragment;
import com.jixstreet.spaace.fragment.QuoteFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TextView toolbarTitle;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        final ImageView imageProfile = (ImageView) headerView.findViewById(R.id.image_profile);
        final TextView nameProfile = (TextView) headerView.findViewById(R.id.name_profile);

        navigationView.addHeaderView(headerView);

//        fragment = ExploreFragment.newInstance(this);
//        toolbarTitle.setText(getString(R.string.explore));
//        if (fragment != null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
//        }/

        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageProfile.setImageResource(R.drawable.ic_avatar_sample);
                nameProfile.setText("Jeese Heisenberg");
            }
        });

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
//            fragment = ExploreFragment.newInstance(this);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
