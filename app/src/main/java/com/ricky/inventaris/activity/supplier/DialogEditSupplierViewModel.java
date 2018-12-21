package com.ricky.inventaris.activity.supplier;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.ricky.inventaris.ConstantNetwork;
import com.ricky.inventaris.base.BaseViewModel;
import com.ricky.inventaris.pojo.Supplier;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 12/16/2018.
 */
public class DialogEditSupplierViewModel extends BaseViewModel {

    public static final String AUTO_GENERATE = "Auto Generate";

    private final DialogEditSupplierListener listener;
    private final Supplier supplier;
    private Supplier edited;

    private boolean showDelete = true;

    private final int mode;

    public DialogEditSupplierViewModel(Context context, Supplier supplier, int mode, DialogEditSupplierListener listener) {
        super(context);
        this.supplier = supplier;
        this.mode = mode;
        this.listener = listener;
        edited = new Supplier(supplier);
    }

    public boolean getShowDelete() {
        return showDelete;
    }

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void afterKodeChanged(Editable kode) {
        edited.setKode_supplier(kode.toString());
    }

    public void afterNamaChanged(Editable nama) {
        edited.setNama_supplier(nama.toString());
    }

    public void afterStatusChanged(CompoundButton buttonView, boolean isChecked) {
        edited.setStatus_supplier(isChecked);
    }

    public void onSimpanClick(View v) {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(
                simpanPerubahan(false).subscribe(aBoolean -> {
                    if (aBoolean) {
                        supplier.copyFrom(edited);
                        listener.onSimpanBerhasil();
                    }
                    else {
                        supplier.copyFrom(supplier);
                        listener.onGagalSimpan();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    supplier.copyFrom(supplier);
                    listener.onGagalSimpan();
                })
        );
    }

    public void onDelete(View v) {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(
                simpanPerubahan(true).subscribe(aBoolean -> {
                    if (aBoolean) {
                        supplier.copyFrom(edited);
                        listener.onDelete();
                    }
                    else {
                        supplier.copyFrom(supplier);
                        listener.onGagalSimpan();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    supplier.copyFrom(supplier);
                    listener.onGagalSimpan();
                })
        );
    }

    private Observable<Boolean> simpanPerubahan(boolean delete) {
        String url;
        Map<String, String> param = new HashMap<>();
        if (mode == SupplierAdapter.DATA_SUPPLIER) {
            url = ConstantNetwork.EDIT_SUPPLIER;
            if (!TextUtils.equals(AUTO_GENERATE, edited.getKode_supplier()))
                param.put("kode_supplier", edited.getKode_supplier());
            param.put("nama_supplier", edited.getNama_supplier());
            param.put("status_supplier", String.valueOf(edited.getStatus_supplier() ? 1 : 0));
        } else {
            url = ConstantNetwork.EDIT_MANDOR;
            if (!TextUtils.equals(AUTO_GENERATE, edited.getKode_supplier()))
                param.put("kode_mandor", edited.getKode_supplier());
            param.put("nama_mandor", edited.getNama_supplier());
            param.put("status_mandor", String.valueOf(edited.getStatus_supplier() ? 1 : 0));
        }

        if (delete)
            param.put("DELETE", "1");

        return Rx2AndroidNetworking.post(url)
                .addBodyParameter(param)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> jsonObject.optBoolean("success"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void onBatalClick(View v) {
        listener.onBatal();
    }

    public interface DialogEditSupplierListener {
        void onSimpanBerhasil();
        void onGagalSimpan();
        void onDelete();
        void onBatal();
    }
}
