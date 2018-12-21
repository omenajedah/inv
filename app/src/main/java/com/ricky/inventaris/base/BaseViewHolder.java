package com.ricky.inventaris.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<E extends ViewDataBinding> extends RecyclerView.ViewHolder {

    protected final E binding;

    public BaseViewHolder(E binding) {
        super(binding.getRoot());
        this.binding=binding;
    }

    public abstract void onBind(int position);

}
