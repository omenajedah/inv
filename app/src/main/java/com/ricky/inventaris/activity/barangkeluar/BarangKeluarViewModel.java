package com.ricky.inventaris.activity.barangkeluar;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ricky.inventaris.ConstantNetwork;
import com.ricky.inventaris.base.BaseViewModel;
import com.ricky.inventaris.pojo.Stock;
import com.ricky.inventaris.pojo.Supplier;
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
 * Created by Firman on 12/15/2018.
 */
public class BarangKeluarViewModel extends BaseViewModel {

    private final BarangKeluarListener listener;

    public BarangKeluarViewModel(Context context, BarangKeluarListener listener) {
        super(context);
        this.listener = listener;
    }

    public void onBarangSelected(int position) {
        listener.onItemSelected(position);
    }

    public void onKodeChanged(Editable s) {
        tampilBarang(s.toString().trim());
    }

    public void onSupplierSelected(AdapterView<?> parent, View view, int pos, long id) {

    }

    public void onReset() {
        listener.onReset();
    }

    public void tampilBarang(String query) {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(
                downloadBarang(query)
                        .subscribe(stocks -> {
                            listener.onItemChanged(stocks, query);
                        }, throwable -> {
                            throwable.printStackTrace();
                        })
        );

    }

    public void tampilSupplier() {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(
                downloadSupplier()
                        .subscribe(listener::onSupplierChanged, throwable -> {
                            throwable.printStackTrace();
                        })
        );
    }
    private Observable<List<Stock>> downloadBarang(String query) {
        return Rx2AndroidNetworking.post(ConstantNetwork.DAFTAR_BARANG)
                .addBodyParameter("query", query.toLowerCase())
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> {
                    List<Stock> stocks = new ArrayList<>();

                    if (!jsonObject.optBoolean("success"))
                        return stocks;
                    JSONArray array = jsonObject.getJSONArray("DataRow");

                    if (array.length() <= 0) {
                        Stock stock = new Stock();
                        stock.setKode_barang(query);
                        stock.setNama_barang("Tidak ada hasil '" + query + "'");
                        stocks.add(stock);
                    }
                    for (int i = 0; i < array.length(); i++) {
                        stocks.add(Stock.fromJson(array.getJSONObject(i)));
                    }
                    return stocks;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<Supplier>> downloadSupplier() {
        Map<String, String> param = new HashMap<>();

        return Rx2AndroidNetworking.post(ConstantNetwork.MANDOR)
                .addBodyParameter(param)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> {
                    List<Supplier> suppliers = new ArrayList<>();

                    JSONArray array = jsonObject.getJSONArray("DataRow");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject item = array.getJSONObject(i);
                        suppliers.add(Supplier.fromJsonMandor(item));
                    }
                    return suppliers;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public void kirim(String kode_barang, String nama_barang, String jumlah, String satuan, String kode_mandor) {

        Rx2AndroidNetworking.post(ConstantNetwork.BARANG_KELUAR)
                .addBodyParameter("kode_barang", kode_barang)
                .addBodyParameter("nama_barang", nama_barang)
                .addBodyParameter("jumlah",jumlah)
                .addBodyParameter("satuan", satuan)
                .addBodyParameter("kode_mandor", kode_mandor)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.optBoolean("success")) {
                            Toast.makeText(getContext(), "Sukses menyimpan.", Toast.LENGTH_SHORT).show();
                            ((Activity)getContext()).finish();
                        } else {
                            Toast.makeText(getContext(), "Gagal menyimpan.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(), "Gagal menyimpan.", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public interface BarangKeluarListener {
        void onItemSelected(int pos);

        void onItemChanged(List<Stock> stock, String query);

        void onSupplierChanged(List<Supplier> suppliers);

        void onReset();
    }
}
