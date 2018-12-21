package com.ricky.inventaris.activity.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.Toast;

import com.ricky.inventaris.BR;
import com.ricky.inventaris.R;
import com.ricky.inventaris.base.BaseActivity;
import com.ricky.inventaris.databinding.ActivityRegisterBinding;

/**
 * Created by Firman on 12/15/2018.
 */
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel>
    implements RegisterViewModel.RegisterListener {

    private RegisterViewModel viewModel;


    public static void startThisActivity(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public RegisterViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new RegisterViewModel(this, this);

        return viewModel;
    }

    @Override
    public void onRegisterSuccess() {
        finish();
    }

    @Override
    public void onRegisterFailed() {
        Toast.makeText(this, "Registrasi gagal, silahkan coba lagi", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
