package com.ricky.inventaris.activity.login;


import android.content.Context;
import android.databinding.ObservableField;

import com.ricky.inventaris.ConstantNetwork;
import com.ricky.inventaris.activity.register.RegisterActivity;
import com.ricky.inventaris.base.BaseViewModel;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 12/15/2018.
 */
public class LoginViewModel extends BaseViewModel {


    private final LoginListener loginListener;
    private ObservableField<String> username = new ObservableField<>();
    private ObservableField<String> password = new ObservableField<>();

    public LoginViewModel(Context context, LoginListener loginListener) {
        super(context);
        this.loginListener = loginListener;
    }

    public ObservableField<String> getUsername() {
        return username;
    }

    public void setUsername(ObservableField<String> username) {
        this.username = username;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public void setPassword(ObservableField<String> password) {
        this.password = password;
    }

    public void register() {
        RegisterActivity.startThisActivity(getContext());
    }

    public void login() {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(
                doLogin(username.get(), password.get())
                        .subscribe(aBoolean -> {
                            if (aBoolean)
                                loginListener.onLoginSuccess();
                            else
                                loginListener.onLoginFailed();
                        }, throwable -> {
                            loginListener.onLoginFailed();
                        })
        );
    }

    private Observable<Boolean> doLogin(String username, String password) {

        return Rx2AndroidNetworking.post(ConstantNetwork.LOGIN)
                .addBodyParameter("user_name", username)
                .addBodyParameter("user_pass", password)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> {
                    if (jsonObject.optBoolean("success")) {
                        JSONObject profile = jsonObject.getJSONObject("profile");
                        getSessionHandler().login(profile.optString("user_name"),
                                profile.optString("user_pass"),
                                profile.optString("user_fullname"));
                    }
                    return jsonObject.optBoolean("success");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public interface LoginListener {
        void onLoginSuccess();

        void onLoginFailed();
    }
}
