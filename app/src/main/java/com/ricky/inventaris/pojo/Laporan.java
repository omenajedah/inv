package com.ricky.inventaris.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ricky.inventaris.BR;
import com.ricky.inventaris.Utils;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Firman on 11/25/2018.
 */
public class Laporan extends BaseObservable {

    private Date tgl_laporan;
    private String kode_barang;
    private String nama_barang;
    private int jumlah;
    private String satuan;
    private String supplier;
    private String keterangan;
    private int C_TYPE;

    public static Laporan fromJson(JSONObject item) {
        Laporan laporan = new Laporan();
        laporan.setKode_barang(item.optString("kode_barang"));
        laporan.setNama_barang(item.optString("nama_barang"));
        laporan.setJumlah(item.optInt("jumlah"));
        laporan.setSatuan(item.optString("satuan_barang"));
        laporan.setSupplier(item.optString("nama_supplier"));
        laporan.setC_TYPE(item.optInt("C_TYPE"));
        String keterangan = item.optString("keterangan", "");
        if (keterangan.equals("null"))
            keterangan = null;
        laporan.setKeterangan(keterangan);

        try {
            laporan.setTgl_laporan(Utils.parseDate(item.optString("waktu"), "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return laporan;
    }

    @Bindable
    public Date getTgl_laporan() {
        return tgl_laporan;
    }

    public void setTgl_laporan(Date tgl_laporan) {
        this.tgl_laporan = tgl_laporan;
        notifyPropertyChanged(BR.tgl_laporan);
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

    @Bindable
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
        notifyPropertyChanged(BR.supplier);
    }

    @Bindable
    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
        notifyPropertyChanged(BR.keterangan);
    }

    @Bindable
    public int getC_TYPE() {
        return C_TYPE;
    }

    public void setC_TYPE(int c_TYPE) {
        C_TYPE = c_TYPE;
        notifyPropertyChanged(BR.c_TYPE);
    }
}
