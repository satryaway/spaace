package com.jixstreet.spaace.activity;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.jixstreet.spaace.BaseActivity;
import com.jixstreet.spaace.R;
import com.jixstreet.spaace.adapter.OtherProjectAdapter;
import com.jixstreet.spaace.model.OtherProject;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

public class ExploreDetailActivity extends BaseActivity {
    private Toolbar toolbar;
    private TwoWayView gridViewOtherProject;
    private OtherProjectAdapter otherProjectAdapter;
    private OtherProject otherProject;
    private ArrayList<OtherProject> otherProjectList = new ArrayList();

    @Override
    public int setView() {
        return R.layout.activity_explore_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < 5; i++) {
            otherProject = new OtherProject(
                    i + "",
                    "Bukit Batok",
                    "Condo • $ 5.800",
                    String.valueOf(R.drawable.bg_sample)
            );
            otherProjectList.add(otherProject);
        }

        otherProjectAdapter = new OtherProjectAdapter(this, otherProjectList);
        gridViewOtherProject.setAdapter(otherProjectAdapter);

    }

    public void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        gridViewOtherProject = (TwoWayView) findViewById(R.id.grid_other_project);

    }

    public void setCallBack() {

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
