package com.ricky.inventaris.activity.supplier;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ricky.inventaris.base.BaseRecyclerAdapter;
import com.ricky.inventaris.base.BaseViewHolder;
import com.ricky.inventaris.databinding.ListMandorBinding;
import com.ricky.inventaris.databinding.ListSupplierBinding;
import com.ricky.inventaris.pojo.Supplier;

import java.util.List;

/**
 * Created by Firman on 12/16/2018.
 */
public class SupplierAdapter extends BaseRecyclerAdapter<Supplier> {

    public static final int DATA_SUPPLIER = 0;
    public static final int DATA_MANDOR = 1;

    private int mode = DATA_SUPPLIER;

    private List<Supplier> originalList;

    public SupplierAdapter(List<Supplier> originalList) {
        super(originalList);
        this.originalList = originalList;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public boolean onSearch(String filter, Supplier supplier) throws Exception {
        return false;
    }

    @NonNull
    @Override
    public BaseViewHolder<?> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mode == DATA_SUPPLIER) {
            ListSupplierBinding binding = ListSupplierBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new SupplierViewHolder(binding);
        }

        ListMandorBinding binding = ListMandorBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MandorViewHolder(binding);
    }

    class SupplierViewHolder extends BaseViewHolder<ListSupplierBinding> implements ListSupplierViewModel.ListSupplierListener {

        public SupplierViewHolder(ListSupplierBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            binding.setVm(new ListSupplierViewModel(showList.get(position), mode, this));
            binding.executePendingBindings();
        }

        @Override
        public void onDelete(Supplier supplier) {
            originalList.remove(supplier);
            updateDataSet();
        }
    }

    class MandorViewHolder extends BaseViewHolder<ListMandorBinding> implements ListSupplierViewModel.ListSupplierListener {

        public MandorViewHolder(ListMandorBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            binding.setVm(new ListSupplierViewModel(showList.get(position), mode, this));
            binding.executePendingBindings();
        }

        @Override
        public void onDelete(Supplier supplier) {
            originalList.remove(supplier);
            updateDataSet();
        }
    }
}
