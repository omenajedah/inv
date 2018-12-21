package com.ricky.inventaris.activity.barangkeluar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.ricky.inventaris.R;
import com.ricky.inventaris.Utils;
import com.ricky.inventaris.pojo.Stock;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 12/16/2018.
 */
public class BarangAdapter extends ArrayAdapter<Stock> {

    private List<Stock> originalList;
    private List<Stock> suggestions = new ArrayList<>();
    private Disposable disposable;

    private int pos = -1;

    public BarangAdapter(Context context, List<Stock> stockList) {
        super(context, android.R.layout.simple_list_item_1);
        this.originalList = stockList;
    }

    public void setPos(String query) {
        if (disposable != null)
            disposable.dispose();

        disposable = Observable.create(e -> {
            for (int i = 0; i < originalList.size(); i++) {
                if (originalList.get(i).getNama_barang().equals("Tidak ada hasil '"+query+"'") &&
                        originalList.get(i).getKode_barang().equals(query)) {
                    pos=i;
                    break;
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {

                }, throwable -> {

                });

    }

    @Override
    public Stock getItem(int position) {
        return originalList.get(position);
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Stock stock = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        if (stock.getNama_barang().contains("Tidak ada hasil")) {
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(stock.getNama_barang());
        } else
            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(
                            Utils.fromHTML(String.format(getContext().getString(R.string.kode_nama_barang), stock.getKode_barang(), stock.getNama_barang())));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Stock stock = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

        if (stock.getNama_barang().contains("Tidak ada hasil")) {
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(stock.getNama_barang());
        } else
            ((TextView) convertView.findViewById(android.R.id.text1))
                .setText(
                        Utils.fromHTML(String.format(getContext().getString(R.string.kode_nama_barang), stock.getKode_barang(), stock.getNama_barang())));
        return convertView;
    }

    @Override
    public Filter getFilter() {

        return new CustomFilter();
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            suggestions.clear();

            if (originalList != null && constraint != null) { // Check if the Original List and Constraint aren't null.
                for (int i = 0; i < originalList.size(); i++) {
                    if (originalList.get(i).getKode_barang().toLowerCase().contains(constraint.toString().toLowerCase())) { // Compare item in original list if it contains constraints.
                        suggestions.add(originalList.get(i)); // If TRUE add item in Suggestions.
                    }
                }
            }
            FilterResults results = new FilterResults(); // Create new Filter Results and return this to publishResults;
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
