package com.ricky.inventaris.activity.barangmasuk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.ricky.inventaris.BR;
import com.ricky.inventaris.R;
import com.ricky.inventaris.activity.barangkeluar.BarangAdapter;
import com.ricky.inventaris.activity.barangkeluar.SuppliersAdapter;
import com.ricky.inventaris.base.BaseActivity;
import com.ricky.inventaris.databinding.ActivityBarangMasukBinding;
import com.ricky.inventaris.pojo.Stock;
import com.ricky.inventaris.pojo.Supplier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Firman on 12/15/2018.
 */
public class BarangMasukActivity extends BaseActivity<ActivityBarangMasukBinding, BarangMasukViewModel>
        implements BarangMasukViewModel.BarangMasukListener {

    private static final String TAG = BarangMasukActivity.class.getSimpleName();
    private BarangMasukViewModel viewModel;
    private BarangAdapter adapter;
    private SuppliersAdapter suppliersAdapter;
    private List<Stock> stocks = new ArrayList<>();
    private List<Supplier> suppliers = new ArrayList<>();

    public static void startThisActivity(Context context) {
        context.startActivity(new Intent(context, BarangMasukActivity.class));
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_barang_masuk;
    }

    @Override
    public BarangMasukViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new BarangMasukViewModel(this, this);

        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adapter = new BarangAdapter(this, stocks);
        suppliersAdapter = new SuppliersAdapter(this, suppliers);
        getBinding().kodeBarangEt.setThreshold(1);
        getBinding().kodeBarangEt.setAdapter(adapter);
        getBinding().labelMandor.setAdapter(suppliersAdapter);

        viewModel.tampilSupplier();

        getBinding().addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kode_barang = getBinding().kodeBarangEt.getText().toString();
                String nama_barang = getBinding().namaBarangEt.getText().toString();
                String jumlah = getBinding().jumlahEt.getText().toString();
                String satuan = getBinding().satuanEt.getText().toString();
                String kode_supplier = suppliers.get(getBinding().labelMandor.getSelectedItemPosition()).getKode_supplier();

                viewModel.kirim(kode_barang, nama_barang, jumlah, satuan, kode_supplier);
            }
        });
    }

    @Override
    public void onItemSelected(int pos) {
        Log.d(TAG, "selected pos=" + pos);
        if (stocks.get(0).getNama_barang().contains("Tidak ada hasil")) {
            getBinding().kodeBarangEt.setText(stocks.get(0).getKode_barang());
            getBinding().namaBarangEt.setText("");
            getBinding().jumlahEt.setText("");
            getBinding().satuanEt.setText("");
            getBinding().namaBarangEt.setText("");
            return;
        }
        getBinding().kodeBarangEt.setText(stocks.get(pos).getKode_barang());
        getBinding().namaBarangEt.setText(stocks.get(pos).getNama_barang());
        getBinding().jumlahEt.setText(String.valueOf(stocks.get(pos).getJumlah()));
        getBinding().satuanEt.setText(stocks.get(pos).getSatuan());
        getBinding().namaBarangEt.setText(stocks.get(pos).getNama_barang());
    }

    @Override
    public void onItemChanged(List<Stock> stock, String query) {
        Log.d(TAG, "stock size=" + stock.size());
        if (stock.size() > 1 || stock.get(0).getNama_barang().equals("Tidak ada hasil '" + query + "'")) {
            getBinding().namaBarangEt.setText("");
            getBinding().jumlahEt.setText("");
            getBinding().satuanEt.setText("");
            getBinding().namaBarangEt.setText("");
        }
        stocks.clear();
        stocks.addAll(stock);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSupplierChanged(List<Supplier> suppliers) {
        this.suppliers.clear();
        this.suppliers.addAll(suppliers);
        suppliersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onReset() {
        getBinding().kodeBarangEt.setText("");
        getBinding().namaBarangEt.setText("");
        getBinding().jumlahEt.setText("");
        getBinding().satuanEt.setText("");
    }
}