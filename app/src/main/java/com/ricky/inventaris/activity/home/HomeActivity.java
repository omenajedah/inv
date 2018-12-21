package com.ricky.inventaris.activity.home;

import android.content.Context;
import android.content.Intent;

import com.ricky.inventaris.BR;
import com.ricky.inventaris.R;
import com.ricky.inventaris.base.BaseActivity;
import com.ricky.inventaris.databinding.ActivityHomeBinding;

/**
 * Created by Firman on 12/15/2018.
 */
public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel>
    implements HomeViewModel.HomeListener {

    private HomeViewModel viewModel;

    public static void startThisActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }

    @Override
    public void onLogout() {
        finish();
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new HomeViewModel(this, this);
        return viewModel;
    }
}
