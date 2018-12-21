package com.ricky.inventaris.activity.supplier;

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
import com.ricky.inventaris.activity.stock.StockAdapter;
import com.ricky.inventaris.base.BaseActivity;
import com.ricky.inventaris.databinding.ActivitySupplierBinding;
import com.ricky.inventaris.pojo.Supplier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Firman on 12/16/2018.
 */
public class SupplierActivity extends BaseActivity<ActivitySupplierBinding, SupplierViewModel>
    implements SupplierViewModel.SupplierListener {

    private SupplierViewModel viewModel;
    private int mode = 0;
    private List<Supplier> suppliers = new ArrayList<>();
    private SupplierAdapter adapter;

    public static void startThisActivity(Context context) {
        context.startActivity(new Intent(context, SupplierActivity.class));
    }

    @Override
    public void onModeChanged(int mode) {
        this.mode=mode;
        if (adapter != null) {
            adapter.setMode(mode);
        }
        if (mode == SupplierAdapter.DATA_SUPPLIER)
            setTitle("Supplier");
        else
            setTitle("Mandor");
        invalidateOptionsMenu();
    }

    @Override
    public void onDataChanged(List<Supplier> suppliers, int data) {
        this.suppliers.clear();
        this.suppliers.addAll(suppliers);
        adapter.updateDataSet();
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Telah terjadi error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_supplier;
    }

    @Override
    public SupplierViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new SupplierViewModel(this, this);
        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adapter = new SupplierAdapter(suppliers);
        getBinding().listData.setItemAnimator(new DefaultItemAnimator());
        getBinding().listData.addItemDecoration(new CustomDivider(this, CustomDivider.VERTICAL_LIST, Color.BLACK));
        getBinding().listData.setHasFixedSize(true);
        getBinding().listData.setAdapter(adapter);

        viewModel.refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mode == 0)
            getMenuInflater().inflate(R.menu.menu_supplier, menu);
        else
            getMenuInflater().inflate(R.menu.menu_mandor, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !TextUtils.isEmpty(query.trim())) {
                    viewModel.setQuery(query);
                } else
                    viewModel.setQuery(null);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !TextUtils.isEmpty(newText.trim())) {
                    viewModel.setQuery(newText);
                } else
                    viewModel.setQuery(null);
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            viewModel.setQuery(null);
            return true;
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.kode_supplier:
                viewModel.setOrderBy("kode_supplier");
                break;
            case R.id.nama_supplier:
                viewModel.setOrderBy("nama_supplier");
                break;
            case R.id.kode_mandor:
                viewModel.setOrderBy("kode_mandor");
                break;
            case R.id.nama_mandor:
                viewModel.setOrderBy("nama_mandor");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
