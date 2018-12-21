package com.ricky.inventaris.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ricky.inventaris.BR;

import org.json.JSONObject;

/**
 * Created by Firman on 11/25/2018.
 */
public class Stock extends BaseObservable {

    private String kode_barang;
    private String nama_barang;
    private int jumlah;
    private String satuan;



    public static Stock fromJson(JSONObject jsonObject) {
        Stock stock = new Stock();
        stock.setKode_barang(jsonObject.optString("kode_barang"));
        stock.setNama_barang(jsonObject.optString("nama_barang"));
        stock.setJumlah(jsonObject.optInt("jumlah"));
        stock.setSatuan(jsonObject.optString("satuan_barang"));

        return stock;
    }

    @Bindable
    public String getKode_barang() {
        return kode_barang;
    }

    public void setKode_barang(String kode_barang) {
        this.kode_barang = kode_barang;
        notifyPropertyChanged(BR.kode_barang);
    }

    @Bindable
    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
        notifyPropertyChanged(BR.nama_barang);
    }

    @Bindable
    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
        notifyPropertyChanged(BR.jumlah);
    }

    @Bindable
    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
        notifyPropertyChanged(BR.satuan);
    }

    @Override
    public String toString() {
        return getKode_barang()+"-"+getNama_barang();
    }
}
