package com.example.ergasiaseptemvrioy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TrendHashtagAdapter extends ArrayAdapter<Trend> {

    private List<Trend> trendsList;
    private final LayoutInflater inflater;
    private final int layoutResource;
    private final ListView hashTagListView;
    private final Context context;
    private final String INTENT_EXTRA_PARAM_NAME = "searchTerm";
    private final String REGULAR_EXPRESSION_SPLIT = "=";


    public TrendHashtagAdapter(@NonNull Context context, int resource, @NonNull List<Trend> objects, ListView listView) {
        super(context, resource, objects);
        trendsList = objects;
        layoutResource = resource;
        inflater = LayoutInflater.from(context);
        hashTagListView = listView;
        this.context = context;
    }

    @Override
    public int getCount() {
        return trendsList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Trend currentTrend = trendsList.get(position);
        viewHolder.hashTagName.setText(currentTrend.getName() + "");
        viewHolder.hashtagURLButton.setText(currentTrend.getQueryUrl() + "");
        //krivw to text apo to button giati to text toy button einai stin pragmatikotita to URL poy ua kanoyme reques
        //gia na paroyme ola ta POSTS me auto to hastag name  apo to twitter!!
        viewHolder.hashtagURLButton.setTextSize(0);
        viewHolder.hashtagURLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] explodedString = trendsList.get(position).getQueryUrl().split(REGULAR_EXPRESSION_SPLIT);
                Intent intent = new Intent(context, PostsActivity.class);
                intent.putExtra(INTENT_EXTRA_PARAM_NAME, explodedString[1]);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    public void setTrendsList(List<Trend> trends) {
        this.trendsList = trends;
        hashTagListView.setAdapter(this);
    }


    private class ViewHolder {

        final TextView hashTagName;
        final Button hashtagURLButton;

        ViewHolder(View view) {
            hashTagName = view.findViewById(R.id.hashtagName);
            hashtagURLButton = view.findViewById(R.id.submitHashtag);
        }
    }


}
