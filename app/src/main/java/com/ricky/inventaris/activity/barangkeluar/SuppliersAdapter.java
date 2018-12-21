package com.ricky.inventaris.activity.barangkeluar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ricky.inventaris.R;
import com.ricky.inventaris.Utils;
import com.ricky.inventaris.pojo.Supplier;

import java.util.List;

/**
 * Created by Firman on 12/18/2018.
 */
public class SuppliersAdapter extends ArrayAdapter<Supplier> {

    public SuppliersAdapter(Context context, List<Supplier> supplierList) {
        super(context, android.R.layout.simple_list_item_1, supplierList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position).getNama_supplier());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

        ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position).getNama_supplier());

        return convertView;
    }
}
