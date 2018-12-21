package com.ricky.inventaris.activity.stock;

import android.app.SearchManager;
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
import com.ricky.inventaris.base.BaseActivity;
import com.ricky.inventaris.databinding.ActivityStockBinding;
import com.ricky.inventaris.pojo.Stock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Firman on 12/16/2018.
 */
public class StockActivity extends BaseActivity<ActivityStockBinding, StockViewModel>
    implements StockViewModel.StockListener {

    private StockViewModel viewModel;
    private StockAdapter adapter;
    private List<Stock> stockList = new ArrayList<>();

    public static void startThisActivity(Context context) {
        context.startActivity(new Intent(context, StockActivity.class));
    }


    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_stock;
    }

    @Override
    public StockViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new StockViewModel(this, this);

        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adapter = new StockAdapter(stockList);
        getBinding().listData.setItemAnimator(new DefaultItemAnimator());
        getBinding().listData.addItemDecoration(new CustomDivider(this, CustomDivider.VERTICAL_LIST, Color.BLACK));
        getBinding().listData.setHasFixedSize(true);
        getBinding().listData.setAdapter(adapter);

        viewModel.refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_stock, menu);

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
            case R.id.nama_barang:
                viewModel.setOrderBy("nama_barang");
                break;
            case R.id.kode_barang:
                viewModel.setOrderBy("kode_barang");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemChange(List<Stock> stocks) {
        this.stockList.clear();
        this.stockList.addAll(stocks);
        adapter.updateDataSet();
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Telah terjadi error.", Toast.LENGTH_SHORT).show();
    }
}
