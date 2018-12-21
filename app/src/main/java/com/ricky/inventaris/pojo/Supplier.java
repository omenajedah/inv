package com.ricky.inventaris.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ricky.inventaris.BR;

import org.json.JSONObject;

/**
 * Created by Firman on 12/16/2018.
 */
public class Supplier extends BaseObservable {

    private String kode_supplier;
    private String nama_supplier;
    private boolean status_supplier;

    public Supplier() {

    }

    public Supplier(Supplier copy) {
        setKode_supplier(copy.kode_supplier);
        setNama_supplier(copy.nama_supplier);
        setStatus_supplier(copy.status_supplier);
    }

    public void copyFrom(Supplier copy) {
        setKode_supplier(copy.kode_supplier);
        setNama_supplier(copy.nama_supplier);
        setStatus_supplier(copy.status_supplier);
    }

    @Bindable
    public String getKode_supplier() {
        return kode_supplier;
    }

    @Bindable
    public String getNama_supplier() {
        return nama_supplier;
    }

    @Bindable
    public boolean getStatus_supplier() {
        return status_supplier;
    }

    public void setKode_supplier(String kode_supplier) {
        this.kode_supplier = kode_supplier;
        notifyPropertyChanged(BR.kode_supplier);
    }

    public void setNama_supplier(String nama_supplier) {
        this.nama_supplier = nama_supplier;
        notifyPropertyChanged(BR.nama_supplier);
    }

    public void setStatus_supplier(boolean status_supplier) {
        this.status_supplier = status_supplier;
        notifyPropertyChanged(BR.status_supplier);
    }

    public static Supplier fromJsonSupplier(JSONObject object) {
        Supplier supplier = new Supplier();
        supplier.setKode_supplier(object.optString("kode_supplier"));
        supplier.setNama_supplier(object.optString("nama_supplier"));
        supplier.setStatus_supplier(object.optInt("status_supplier") == 1);
        return supplier;
    }

    public static Supplier fromJsonMandor(JSONObject object) {
        Supplier supplier = new Supplier();
        supplier.setKode_supplier(object.optString("kode_mandor"));
        supplier.setNama_supplier(object.optString("nama_mandor"));
        supplier.setStatus_supplier(object.optInt("status_mandor") == 1);
        return supplier;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Supplier))
            return false;
        Supplier supplier = (Supplier) obj;

        return supplier.kode_supplier.equals(kode_supplier) && supplier.nama_supplier.equals(nama_supplier) &&
                supplier.status_supplier == status_supplier;
    }
}
