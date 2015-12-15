package com.jixstreet.spaace.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jixstreet.spaace.R;
import com.jixstreet.spaace.activity.ExploreDetailActivity;
import com.jixstreet.spaace.adapter.PortofolioAdapter;
import com.jixstreet.spaace.model.Portofolio;
import com.jixstreet.spaace.utils.APIAgent;
import com.jixstreet.spaace.utils.CommonConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class ExploreFragment extends Fragment {

    private ProgressBar progressBar;
    private ListView listFeed;
    private View view;
    private PortofolioAdapter portofolioAdapter;
    private ArrayList<Portofolio> listPortofolio = new ArrayList<>();
    private Portofolio portofolio;
    private String LOG_TAG = ExploreFragment.class.getName();

    public static ExploreFragment newInstance(Context context) {
        return new ExploreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initUI();
        getPortofolio();

        listFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Portofolio portofolio = listPortofolio.get(position);
                Intent intent = new Intent(getActivity(), ExploreDetailActivity.class);
                intent.putExtra(CommonConstants.PORTOFOLIO, portofolio);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    private void initUI() {
        listFeed = (ListView) view.findViewById(R.id.list_feed);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }


    public void getPortofolio() {
        String url = CommonConstants.SERVICE_PORTOFOLIOS;

        RequestParams requestParams = new RequestParams();
        requestParams.put(CommonConstants.ACCESS_TOKEN, SpaaceApplication.getInstance().getToken());
//        requestParams.put(CommonConstants.ACCESS_TOKEN, "1ee30455f1cc857b39127ac312fc8c6f472afbad71e549a2246233627a20d0e6");


        APIAgent.get(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                progressBar.setVisibility(View.GONE);

                System.out.println(response.toString());

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Portofolio>>() {
                }.getType();
                listPortofolio = gson.fromJson(response.toString(), listType);

                System.out.println(listPortofolio.get(0).portofolioImages.get(0).imageOri);

                portofolioAdapter = new PortofolioAdapter(getActivity(), listPortofolio);
                listFeed.setAdapter(portofolioAdapter);

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