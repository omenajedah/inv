package com.ricky.inventaris.activity.laporandetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ricky.inventaris.BR;
import com.ricky.inventaris.CustomDivider;
import com.ricky.inventaris.R;
import com.ricky.inventaris.activity.laporanstock.LaporanStockAdapter;
import com.ricky.inventaris.base.BaseActivity;
import com.ricky.inventaris.databinding.ActivityLaporanDetailBinding;
import com.ricky.inventaris.pojo.Laporan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Firman on 12/16/2018.
 */
public class LaporanDetailActivity extends BaseActivity<ActivityLaporanDetailBinding, LaporanDetailViewModel>
    implements LaporanDetailViewModel.LaporanDetailListener {

    private LaporanStockAdapter adapter;
    private List<Laporan> laporanList = new ArrayList<>();
    private LaporanDetailViewModel viewModel;

    public static void startThisActivity(Context context, String kode_barang, String nama_barang,
                                         String satuan, int jumlah) {
        Intent intent = new Intent(context, LaporanDetailActivity.class);
        intent.putExtra("kode_barang", kode_barang);
        intent.putExtra("nama_barang", nama_barang);
        intent.putExtra("satuan", satuan);
        intent.putExtra("jumlah", jumlah);

        context.startActivity(intent);
    }
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_laporan_detail;
    }

    @Override
    public LaporanDetailViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new LaporanDetailViewModel(this, this);
        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        viewModel.setStock(i.getStringExtra("kode_barang"), i.getStringExtra("nama_barang"),
                i.getStringExtra("satuan"), i.getIntExtra("jumlah", 0));

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adapter = new LaporanStockAdapter(laporanList);
        getBinding().listData.setItemAnimator(new DefaultItemAnimator());
        getBinding().listData.addItemDecoration(new CustomDivider(this, CustomDivider.VERTICAL_LIST, Color.BLACK));
        getBinding().listData.setHasFixedSize(true);
        getBinding().listData.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_laporan_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.nama_barang:
                viewModel.setOrderBy("nama_barang");
                break;
            case R.id.kode_barang:
                viewModel.setOrderBy("kode_barang");
                break;
            case R.id.waktu:
                viewModel.setOrderBy("waktu");
                break;
            case R.id.nama_supplier:
                viewModel.setOrderBy("nama_supplier");
                break;
            case R.id.barang_masuk:
                viewModel.setOrderBy("C_TYPE");
                break;
            case R.id.barang_keluar:
                viewModel.setOrderBy("C_TYPE desc");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemChange(List<Laporan> stocks) {
        this.laporanList.clear();
        this.laporanList.addAll(stocks);
        adapter.updateDataSet();
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Telah terjadi error.", Toast.LENGTH_SHORT).show();
    }
}
