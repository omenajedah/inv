package com.ricky.inventaris.activity.stock;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ricky.inventaris.activity.laporandetail.LaporanDetailActivity;
import com.ricky.inventaris.base.BaseRecyclerAdapter;
import com.ricky.inventaris.base.BaseViewHolder;
import com.ricky.inventaris.databinding.ListStockBinding;
import com.ricky.inventaris.pojo.Stock;

import java.util.List;

/**
 * Created by Firman on 12/16/2018.
 */
public class StockAdapter extends BaseRecyclerAdapter<Stock> {

    public StockAdapter(List<Stock> originalList) {
        super(originalList);
    }

    @Override
    public boolean onSearch(String filter, Stock stock) throws Exception {
        return false;
    }

    @NonNull
    @Override
    public BaseViewHolder<?> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListStockBinding binding = ListStockBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StockViewHolder(binding);
    }

    class StockViewHolder extends BaseViewHolder<ListStockBinding> {

        private ListStockViewModel viewModel;

        public StockViewHolder(ListStockBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            viewModel = new ListStockViewModel(showList.get(position));
            binding.setVm(viewModel);
            binding.executePendingBindings();


            binding.rootClickable.setOnClickListener(v -> {
                LaporanDetailActivity.startThisActivity(binding.getRoot().getContext(),
                        viewModel.getStock().getKode_barang(),
                        viewModel.getStock().getNama_barang(),
                        viewModel.getStock().getSatuan(),
                        viewModel.getStock().getJumlah());
            });
        }
    }
}
