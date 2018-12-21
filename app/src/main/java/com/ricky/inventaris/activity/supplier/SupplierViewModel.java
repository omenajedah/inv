package com.ricky.inventaris.activity.supplier;

import android.content.Context;
import android.databinding.ObservableInt;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ricky.inventaris.ConstantNetwork;
import com.ricky.inventaris.CustomDialog;
import com.ricky.inventaris.base.BaseViewModel;
import com.ricky.inventaris.databinding.DialogEditSupplierBinding;
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
 * Created by Firman on 12/16/2018.
 */
public class SupplierViewModel extends BaseViewModel {

    private final SupplierListener listener;

    private ObservableInt mode = new ObservableInt();
    private String orderBy = "kode_supplier";
    private String query = null;

    public SupplierViewModel(Context context, SupplierListener listener) {
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

    public ObservableInt getMode() {
        return mode;
    }

    public void setMode(ObservableInt mode) {
        this.mode = mode;
    }

    public void onDataSelected(AdapterView<?> parent, View view, int pos, long id) {
        listener.onModeChanged(pos);
        if (pos == 0)
            orderBy = "kode_supplier";
        else
            orderBy = "kode_mandor";
        refresh();
    }

    public void onAdd(View v) {
        DialogEditSupplierBinding binding = DialogEditSupplierBinding.inflate(LayoutInflater.from(v.getContext()), null);
        CustomDialog dialog = CustomDialog.get(v.getContext())
                .title("Edit Supplier/Mandor")
                .addView(binding.getRoot())
                .cancelable(false);

        Supplier supplier = new Supplier();
        supplier.setKode_supplier("Auto Generate");
        supplier.setNama_supplier("");
        supplier.setStatus_supplier(false);

        DialogEditSupplierViewModel viewModel = new DialogEditSupplierViewModel(v.getContext(),
                supplier, mode.get(), new DialogEditSupplierViewModel.DialogEditSupplierListener() {

            @Override
            public void onDelete() {

            }

            @Override
            public void onSimpanBerhasil() {
                dialog.dismiss();
                Toast.makeText(v.getContext(), "Sukses menyimpan.", Toast.LENGTH_SHORT).show();
                refresh();
            }

            @Override
            public void onGagalSimpan() {
                Toast.makeText(v.getContext(), "Gagal menyimpan.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBatal() {
                dialog.dismiss();
            }
        });

        viewModel.setShowDelete(false);

        binding.setVm(viewModel);
        binding.executePendingBindings();

        dialog.show();
    }

    public void refresh() {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(
                getSupplier().subscribe(suppliers ->
                                listener.onDataChanged(suppliers, mode.get()), listener::onError)
        );

    }


    public Observable<List<Supplier>> getSupplier() {
        Map<String, String> param = new HashMap<>();
        param.put("OrderBy", orderBy);

        if (query != null)
            param.put("query", query);

        return Rx2AndroidNetworking.post(mode.get() == 0 ? ConstantNetwork.SUPPLIER : ConstantNetwork.MANDOR)
                .addBodyParameter(param)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> {
                    List<Supplier> suppliers = new ArrayList<>();

                    JSONArray array = jsonObject.getJSONArray("DataRow");
                    for (int i=0;i<array.length();i++) {
                        JSONObject item = array.getJSONObject(i);

                        if (mode.get() == 0)
                            suppliers.add(Supplier.fromJsonSupplier(item));
                        else
                            suppliers.add(Supplier.fromJsonMandor(item));

                    }
                    return suppliers;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public interface SupplierListener {
        void onModeChanged(int mode);

        void onDataChanged(List<Supplier> suppliers, int data);

        void onError(Throwable throwable);

    }
}
