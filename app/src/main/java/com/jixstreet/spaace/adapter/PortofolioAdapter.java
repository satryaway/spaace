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
import com.jixstreet.spaace.model.Portofolio;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by fachrifebrian on 9/2/15.
 */
public class PortofolioAdapter extends ArrayAdapter<Portofolio> {

    private int layoutResourceId;
    private Context context;


    public PortofolioAdapter(Context context, ArrayList<Portofolio> items) {
        super(context, 0, items);
        this.layoutResourceId = R.layout.item_explore;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final Portofolio portofolio = getItem(position);

        ViewHolder holder;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(layoutResourceId, null);
            holder = new ViewHolder();

            initUI(holder, convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        System.out.println(portofolio.portofolioImages.get(0).imageOri);

        holder.titleExplore.setText(portofolio.title);
        holder.descExplore.setText(portofolio.description + " • " + portofolio.size + " m²");
        holder.priceExplore.setText("$" + portofolio.price);

        Picasso.with(context).load(portofolio.portofolioImages.get(0).imageMedium).into(holder.imageExplore);


        return convertView;
    }

    private void initUI(ViewHolder holder, View view) {
        holder.imageExplore = (ImageView) view.findViewById(R.id.image_explore);
        holder.priceExplore = (TextView) view.findViewById(R.id.price_explore);
        holder.titleExplore = (TextView) view.findViewById(R.id.title_explore);
        holder.descExplore = (TextView) view.findViewById(R.id.desc_explore);
    }

    static class ViewHolder {
        ImageView imageExplore;
        TextView titleExplore;
        TextView descExplore;
        TextView priceExplore;
    }


}