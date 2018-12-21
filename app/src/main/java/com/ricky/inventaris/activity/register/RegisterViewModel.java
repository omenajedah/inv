package com.ricky.inventaris.activity.register;

import android.content.Context;
import android.databinding.ObservableField;

import com.ricky.inventaris.ConstantNetwork;
import com.ricky.inventaris.base.BaseViewModel;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 12/15/2018.
 */
public class RegisterViewModel extends BaseViewModel {

    private final RegisterListener registerListener;
    private ObservableField<String> username = new ObservableField<>();
    private ObservableField<String> password = new ObservableField<>();
    private ObservableField<String> fullname = new ObservableField<>();

    public RegisterViewModel(Context context, RegisterListener listener) {
        super(context);
        this.registerListener = listener;
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

    public ObservableField<String> getFullname() {
        return fullname;
    }

    public void setFullname(ObservableField<String> fullname) {
        this.fullname = fullname;
    }

    public void register() {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(
                doRegister(username.get(), password.get(), fullname.get())
                        .subscribe(aBoolean -> {
                            if (aBoolean)
                                registerListener.onRegisterSuccess();
                            else
                                registerListener.onRegisterFailed();
                        }, throwable -> {
                            registerListener.onRegisterFailed();
                        })
        );
    }

    private Observable<Boolean> doRegister(String username, String password, String fullname) {
        return Rx2AndroidNetworking.post(ConstantNetwork.REGISTRASI)
                .addBodyParameter("user_name", username)
                .addBodyParameter("user_pass", password)
                .addBodyParameter("user_fullname", fullname)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> jsonObject.optBoolean("success"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public interface RegisterListener {
        void onRegisterSuccess();
        void onRegisterFailed();
    }
}
