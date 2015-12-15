package com.jixstreet.spaace.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jixstreet.spaace.R;
import com.jixstreet.spaace.model.OtherProject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by fachrifebrian on 9/2/15.
 */
public class OtherProjectAdapter extends ArrayAdapter<OtherProject> {

    private int layoutResourceId;
    private Context context;
    private ArrayList<OtherProject> items;


    public OtherProjectAdapter(Context context, ArrayList<OtherProject> items) {
        super(context, R.layout.item_other_project, items);
        this.context = context;
        this.items = items;
        this.layoutResourceId = R.layout.item_other_project;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final OtherProject otherProject = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(layoutResourceId, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageProject = (ImageView) convertView.findViewById(R.id.image_project);
        holder.titleProject = (TextView) convertView.findViewById(R.id.title_project);
        holder.descriptionProject = (TextView) convertView.findViewById(R.id.description_project);

        holder.titleProject.setText(otherProject.getTitleProject());
        holder.descriptionProject.setText(otherProject.getDescExplore());
        Picasso.with(context).load(Integer.parseInt(otherProject.getImageProject())).into(holder.imageProject);


        return convertView;
    }

    static class ViewHolder {
        ImageView imageProject;
        TextView titleProject;
        TextView descriptionProject;
    }
}