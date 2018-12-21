package com.ricky.inventaris.activity.stock;

import android.content.Context;

import com.ricky.inventaris.ConstantNetwork;
import com.ricky.inventaris.base.BaseViewModel;
import com.ricky.inventaris.pojo.Stock;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 12/16/2018.
 */
public class StockViewModel extends BaseViewModel {

    private final StockListener listener;
    private String orderBy = "kode_barang";
    private String query = null;

    public StockViewModel(Context context, StockListener listener) {
        super(context);
        this.listener = listener;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        refresh();
    }

    public void setQuery(String query) {
        this.query = query;
        refresh();
    }

    public void refresh() {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(
                getStock().subscribe(listener::onItemChange, listener::onError)
        );
    }

    private Observable<List<Stock>> getStock() {
        Map<String, String> param = new HashMap<>();
        param.put("OrderBy", orderBy);

        if (query != null)
            param.put("query", query);
        return Rx2AndroidNetworking.post(ConstantNetwork.DAFTAR_BARANG)
                .addBodyParameter(param)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> {
                    List<Stock> stocks = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("DataRow");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject item = array.getJSONObject(i);
                        stocks.add(Stock.fromJson(item));
                    }
                    return stocks;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public interface StockListener {
        void onItemChange(List<Stock> stocks);

        void onError(Throwable throwable);
    }
}
