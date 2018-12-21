package com.ricky.inventaris.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ricky.inventaris.BR;
import com.ricky.inventaris.R;
import com.ricky.inventaris.activity.home.HomeActivity;
import com.ricky.inventaris.base.BaseActivity;
import com.ricky.inventaris.databinding.ActivityLoginBinding;

/**
 * Created by Firman on 12/15/2018.
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel>
        implements LoginViewModel.LoginListener {

    private LoginViewModel viewModel;

    public static void startThisActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }


    @Override
    public void onLoginSuccess() {
        HomeActivity.startThisActivity(this);
        finish();
    }

    @Override
    public void onLoginFailed() {
        Toast.makeText(this, "Login gagal, silahkan coba lagi", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new LoginViewModel(this, this);

        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (viewModel.getSessionHandler().isLogin()) {
            overridePendingTransition(0,0);
            HomeActivity.startThisActivity(this);
            finish();
            return;
        }
    }
}
