package com.jixstreet.spaace.activity;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jixstreet.spaace.BaseActivity;
import com.jixstreet.spaace.R;
import com.jixstreet.spaace.adapter.OtherProjectAdapter;
import com.jixstreet.spaace.model.OtherProject;
import com.jixstreet.spaace.model.Portofolio;
import com.jixstreet.spaace.utils.CommonConstants;
import com.squareup.picasso.Picasso;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

public class ExploreDetailActivity extends BaseActivity {
    private Toolbar toolbar;
    private TwoWayView gridViewOtherProject;
    private TextView toolbarTitle;
    private TextView houseType, bedroom, size;
    private TextView newResale, yearOfCompletion, styleHouse, renovationDuration, designFees;
    private ImageView imageHeader;


    private OtherProjectAdapter otherProjectAdapter;
    private OtherProject otherProject;
    private ArrayList<OtherProject> otherProjectList = new ArrayList();
    private Portofolio portofolio;

    @Override
    public int setView() {
        return R.layout.activity_explore_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        portofolio = (Portofolio) getIntent().getSerializableExtra(CommonConstants.PORTOFOLIO);
        setValue();

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

        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        imageHeader = (ImageView) findViewById(R.id.image_header);
        houseType = (TextView) findViewById(R.id.house_type_content);
        bedroom = (TextView) findViewById(R.id.bedroom_content);
        size = (TextView) findViewById(R.id.size_content);
        newResale = (TextView) findViewById(R.id.new_resale_content);
        yearOfCompletion = (TextView) findViewById(R.id.year_of_completion_content);
        styleHouse = (TextView) findViewById(R.id.style_house_content);
        renovationDuration = (TextView) findViewById(R.id.renovation_duration_content);
        designFees = (TextView) findViewById(R.id.design_fees_content);

        gridViewOtherProject = (TwoWayView) findViewById(R.id.grid_other_project);
    }

    public void setCallBack() {

    }

    private void setValue() {
        toolbarTitle.setText(portofolio.title);
        Picasso.with(this).load(portofolio.portofolioImages.get(0).imageMedium).into(imageHeader);
        size.setText(portofolio.size+" m²");
        newResale.setText(portofolio.designFees);
        renovationDuration.setText(portofolio.renovationDuration+" weeks");
        designFees.setText("$ "+portofolio.price);
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
