package com.ricky.inventaris.activity.laporanstock;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ricky.inventaris.activity.laporandetail.LaporanDetailActivity;
import com.ricky.inventaris.base.BaseRecyclerAdapter;
import com.ricky.inventaris.base.BaseViewHolder;
import com.ricky.inventaris.databinding.ListLaporanBinding;
import com.ricky.inventaris.pojo.Laporan;

import java.util.List;

/**
 * Created by Firman on 12/16/2018.
 */
public class LaporanStockAdapter extends BaseRecyclerAdapter<Laporan> {

    public LaporanStockAdapter(List<Laporan> originalList) {
        super(originalList);
    }

    @Override
    public boolean onSearch(String filter, Laporan laporan) throws Exception {
        return false;
    }

    @NonNull
    @Override
    public BaseViewHolder<?> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListLaporanBinding binding = ListLaporanBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new LaporanStockViewHolder(binding);
    }

    class LaporanStockViewHolder extends BaseViewHolder<ListLaporanBinding> {

        ListLaporanStockViewModel viewModel;

        public LaporanStockViewHolder(ListLaporanBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            viewModel = new ListLaporanStockViewModel(showList.get(position));
            binding.setVm(viewModel);
            binding.executePendingBindings();

            binding.rootClickable.setOnClickListener(v -> {
                LaporanDetailActivity.startThisActivity(binding.getRoot().getContext(),
                        viewModel.getLaporan().getKode_barang(),
                        viewModel.getLaporan().getNama_barang(),
                        viewModel.getLaporan().getSatuan(),
                        viewModel.getLaporan().getJumlah());
            });
        }
    }
}
