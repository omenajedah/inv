package com.ricky.inventaris.activity.laporandetail;

import android.content.Context;

import com.ricky.inventaris.ConstantNetwork;
import com.ricky.inventaris.base.BaseViewModel;
import com.ricky.inventaris.pojo.Laporan;
import com.ricky.inventaris.pojo.Stock;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 12/16/2018.
 */
public class LaporanDetailViewModel extends BaseViewModel {

    private final LaporanDetailListener listener;
    private final Stock stock = new Stock();
    private String orderBy = "waktu";

    public LaporanDetailViewModel(Context context, LaporanDetailListener listener) {
        super(context);
        this.listener=listener;
    }

    public void setStock(String kode_barang, String nama_barang, String satuan, int jumlah) {
        stock.setKode_barang(kode_barang);
        stock.setNama_barang(nama_barang);
        stock.setSatuan(satuan);
        stock.setJumlah(jumlah);

        refresh();
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        refresh();
    }

    private void refresh() {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(
                getLaporan().subscribe(listener::onItemChange, listener::onError)
        );
    }

    private Observable<List<Laporan>> getLaporan() {
        return Rx2AndroidNetworking.post(ConstantNetwork.LAPORAN)
                .addBodyParameter("kode_barang", stock.getKode_barang())
                .addBodyParameter("OrderBy", orderBy)
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

    public Stock getStock() {
        return stock;
    }

    public interface LaporanDetailListener {
        void onItemChange(List<Laporan> laporanList);
        void onError(Throwable throwable);
    }
}
