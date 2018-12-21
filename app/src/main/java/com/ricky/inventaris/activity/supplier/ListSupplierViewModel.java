package com.ricky.inventaris.activity.supplier;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.ricky.inventaris.CustomDialog;
import com.ricky.inventaris.databinding.DialogEditSupplierBinding;
import com.ricky.inventaris.pojo.Supplier;

/**
 * Created by Firman on 12/16/2018.
 */
public class ListSupplierViewModel {

    private final Supplier supplier;
    private final int mode;
    private final ListSupplierListener listener;


    public ListSupplierViewModel(Supplier supplier, int mode, ListSupplierListener listener) {
        this.supplier = supplier;
        this.mode=mode;
        this.listener = listener;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void onClick(View v) {
        DialogEditSupplierBinding binding = DialogEditSupplierBinding.inflate(LayoutInflater.from(v.getContext()), null);
        CustomDialog dialog = CustomDialog.get(v.getContext())
                .title("Edit Supplier/Mandor")
                .addView(binding.getRoot())
                .cancelable(false);

        DialogEditSupplierViewModel viewModel = new DialogEditSupplierViewModel(v.getContext(),
                supplier, mode, new DialogEditSupplierViewModel.DialogEditSupplierListener() {

            @Override
            public void onDelete() {
                dialog.dismiss();
                listener.onDelete(supplier);
            }

            @Override
            public void onSimpanBerhasil() {
                dialog.dismiss();
                Toast.makeText(v.getContext(), "Sukses menyimpan.", Toast.LENGTH_SHORT).show();
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

        binding.setVm(viewModel);
        binding.executePendingBindings();

        dialog.show();
    }

    public interface ListSupplierListener {
        void onDelete(Supplier supplier);
    }
}
