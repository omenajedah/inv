package com.ricky.inventaris.activity.laporanstock;

import android.content.Context;

import com.ricky.inventaris.ConstantNetwork;
import com.ricky.inventaris.base.BaseViewModel;
import com.ricky.inventaris.pojo.Laporan;
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
public class LaporanStockViewModel extends BaseViewModel {

    private final LaporanStockListener listener;
    private String orderBy = "id_trans";
    private String query;

    public LaporanStockViewModel(Context context, LaporanStockListener listener) {
        super(context);
        this.listener = listener;
    }

    public void setQuery(String query) {
        this.query = query;
        refresh();
    }

    public void setOrderBy(String orderBy) {
        this.orderBy=orderBy;
        refresh();
    }

    public void refresh() {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(
                getStock().subscribe(listener::onItemChange, listener::onError)
        );
    }

    private Observable<List<Laporan>> getStock() {
        Map<String, String> param = new HashMap<>();
        param.put("OrderBy", orderBy);

        if (query != null)
            param.put("query", query);
        return Rx2AndroidNetworking.post(ConstantNetwork.LAPORAN)
                .addBodyParameter(param)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> {
                    List<Laporan> laporans = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("DataRow");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject item = array.getJSONObject(i);
                        laporans.add(Laporan.fromJson(item));
                    }
                    return laporans;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public interface LaporanStockListener {
        void onItemChange(List<Laporan> laporanList);
        void onError(Throwable throwable);
    }
}
