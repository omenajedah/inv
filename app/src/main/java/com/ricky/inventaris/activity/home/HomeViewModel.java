package com.ricky.inventaris.activity.home;

import android.content.Context;
import android.databinding.ObservableField;

import com.ricky.inventaris.activity.barangkeluar.BarangKeluarActivity;
import com.ricky.inventaris.activity.barangmasuk.BarangMasukActivity;
import com.ricky.inventaris.activity.laporanstock.LaporanStockActivity;
import com.ricky.inventaris.activity.laporanstock.LaporanStockViewModel;
import com.ricky.inventaris.activity.login.LoginActivity;
import com.ricky.inventaris.activity.stock.StockActivity;
import com.ricky.inventaris.activity.supplier.SupplierActivity;
import com.ricky.inventaris.base.BaseViewModel;

/**
 * Created by Firman on 12/15/2018.
 */
public class HomeViewModel extends BaseViewModel {

    private final HomeListener listener;
    private ObservableField<String> fullname = new ObservableField<>();


    public HomeViewModel(Context context, HomeListener listener) {
        super(context);
        this.listener=listener;
        fullname.set("Selamat Datang, "+getSessionHandler().getString("user_fullname", null));
    }

    public ObservableField<String> getFullname() {
        return fullname;
    }

    public void onSupplierClick() {
        SupplierActivity.startThisActivity(getContext());
    }

    public void onBarangKeluarClick() {
        BarangKeluarActivity.startThisActivity(getContext());
    }

    public void onBarangMasukClick() {
        BarangMasukActivity.startThisActivity(getContext());
    }

    public void onStokClick() {
        StockActivity.startThisActivity(getContext());
    }

    public void onLaporanClick() {
        LaporanStockActivity.startThisActivity(getContext());
    }

    public void onLogoutClick() {
        getSessionHandler().clear();
        LoginActivity.startThisActivity(getContext());
        listener.onLogout();

    }

    public interface HomeListener {
        void onLogout();
    }
}
