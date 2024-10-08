package com.nojom.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.nojom.client.R;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchTagAdapter extends ArrayAdapter<ServicesModel.Data> {

    BaseActivity context;
    int resource, textViewResourceId;
    List<ServicesModel.Data> items, tempItems, suggestions;
    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ServicesModel.Data) resultValue).name;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (ServicesModel.Data people : tempItems) {
                    if (people.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {
                if (results.values != null) {
                    List<ServicesModel.Data> filterList = (ArrayList<ServicesModel.Data>) results.values;
                    if (filterList.size() > 0) {
                        clear();
                        for (ServicesModel.Data people : filterList) {
                            add(people);
                            notifyDataSetChanged();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public SearchTagAdapter(BaseActivity context, int resource, int textViewResourceId, List<ServicesModel.Data> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<>(items); // this makes the difference.
        suggestions = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_textview, parent, false);
        }
        ServicesModel.Data skill = items.get(position);
        if (skill != null) {
            TextView lblName = view.findViewById(R.id.text);
            if (lblName != null)
                lblName.setText(skill.getServNameByLang(context.getLanguage()));
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }
}