package com.ricky.inventaris;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.ObservableField;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ricky.inventaris.databinding.DialogDefaultBinding;

import java.util.List;

/**
 * Created by firmansyah on 1/4/2018.
 */

public class CustomDialog {

    private static CustomDialog instance;
    private final String TAG = CustomDialog.class.getSimpleName();

    private Context context;

    private AlertDialog alertDialog;
    private DialogDefaultBinding binding;
    private DialogViewModel vm;


    private DialogInterface.OnShowListener onShowListener;


    private int styleRes = -1;
    private int flag = -1;
    private boolean fullscreen = false;

    private DialogInterface dialog;

    private String positiveText;
    private DialogInterface.OnClickListener positiveListener;

    private String negativeText;
    private DialogInterface.OnClickListener negativeListener;

    private String neutralText;
    private DialogInterface.OnClickListener neutralListener;

    CustomDialog(Context context, @StyleRes int styleRes) {
        this.context = context;
        this.styleRes = styleRes;
        setDialog();
    }

    CustomDialog(Context context) {
        this.context = context;
        setDialog();
    }

    public static CustomDialog get(Context context) {
        instance = new CustomDialog(context);

        return instance;
    }

    public static CustomDialog get(Context context, @StyleRes int styleRes) {
        instance = new CustomDialog(context, styleRes);

        return instance;
    }

    private void setDialog() {
        binding = DialogDefaultBinding.inflate(LayoutInflater.from(context), null);
        vm = new DialogViewModel();
        binding.setVm(vm);


        if (styleRes != -1) {
            alertDialog = new AlertDialog.Builder(context, styleRes).create();
        } else {
            alertDialog = new AlertDialog.Builder(context).create();
        }

        alertDialog.setOnShowListener(d1 -> {
            Log.i(TAG, "onShow called  ");
            dialog = d1;

            if (fullscreen) {
                WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();

                alertDialog.getWindow()
                        .clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);


                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.MATCH_PARENT;

                alertDialog.getWindow().setAttributes(params);
                Log.i(TAG, "Before added " + alertDialog.getWindow().getDecorView().getRootView().toString());

                alertDialog.setContentView(binding.getRoot(), params);
                Log.i(TAG, "After added " + alertDialog.getWindow().getDecorView().getRootView().toString());
            }

            if (flag != -1)
                alertDialog.getWindow().addFlags(flag);

            if (onShowListener != null)
                onShowListener.onShow(alertDialog);
        });

        alertDialog.setOnDismissListener(dialog -> {
            if (fullscreen)
                ((ViewGroup) binding.getRoot().getParent()).removeAllViews();
        });
    }

    public CustomDialog title(CharSequence title) {
        vm.setTitle(title.toString());
        return instance;
    }

    public CustomDialog title(@StringRes int title) {
        vm.setTitle(context.getString(title));
        return instance;
    }

    public CustomDialog message(CharSequence msg) {
        vm.setMessage(msg.toString());
        return instance;
    }

    public CustomDialog message(@StringRes int msg) {
        vm.setMessage(context.getString(msg));
        return instance;
    }

    public CustomDialog cancelOnTouchOutside(boolean cancel) {
        alertDialog.setCanceledOnTouchOutside(cancel);
        return instance;
    }

    public CustomDialog cancelable(boolean cancel) {
        alertDialog.setCancelable(cancel);
        return instance;
    }

    public CustomDialog addView(@LayoutRes int view) {
        return addView(view, null);
    }

    public CustomDialog addView(View view) {
        return addView(view, null);
    }

    public CustomDialog addView(@LayoutRes int resource,
                                @Nullable OnBindViewListener listener) {
        View childView = LayoutInflater.from(context).inflate(resource, null);
        LinearLayout container = binding.dialogContainer;
        container.removeAllViews();

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        container.addView(childView, params);


        childView.postDelayed(() -> {
            if (listener != null)
                listener.onBindView(childView);
        }, 100);

        return instance;
    }

    public CustomDialog addView(@NonNull View childView,
                                @Nullable OnBindViewListener listener) {

        LinearLayout container = binding.dialogContainer;
        container.removeAllViews();

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        container.addView(childView, params);


        childView.postDelayed(() -> {
            if (listener != null)
                listener.onBindView(childView);
        }, 100);

        return instance;
    }

    public CustomDialog closeButton() {
        binding.cancelDialog.setVisibility(View.VISIBLE);
        return instance;
    }

    public CustomDialog fullscreen() {
        fullscreen = true;
        return instance;
    }

    public void addPositiveButton(@StringRes int res, DialogInterface.OnClickListener listener) {
        addPositiveButton(context.getString(res), listener);
    }

    public void addNegativeButton(@StringRes int res, DialogInterface.OnClickListener listener) {
        addNegativeButton(context.getString(res), listener);
    }

    public void addNeutralButton(@StringRes int res, DialogInterface.OnClickListener listener) {
        addNeutralButton(context.getString(res), listener);
    }

    public void addPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
        this.positiveText = text.toString();
        this.positiveListener = listener;
    }

    public void addNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
        this.negativeText = text.toString();
        this.negativeListener = listener;
    }

    public void addNeutralButton(CharSequence text, DialogInterface.OnClickListener listener) {
        this.neutralText = text.toString();
        this.neutralListener = listener;
    }

    public void removePositiveButton() {
        positiveText = null;
        positiveListener = null;
    }

    public void removeNegativeButton() {
        negativeText = null;
        negativeListener = null;
    }

    public void removeNeutralButton() {
        neutralText = null;
        neutralListener = null;
    }

    public void addDismissListener(@NonNull DialogInterface.OnDismissListener listener) {
        alertDialog.setOnDismissListener(listener);
    }

    public void addCancelListener(@NonNull DialogInterface.OnCancelListener listener) {
        alertDialog.setOnCancelListener(listener);
    }

    public void addShowListener(final DialogInterface.OnShowListener listener) {
        this.onShowListener = listener;
    }

    public void addFlag(int flag) {
        this.flag = flag;
    }

    public void show() {
        if (positiveListener != null)
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, positiveText, positiveListener);

        if (negativeListener != null)
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, negativeText, negativeListener);

        if (neutralListener != null)
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, neutralText, neutralListener);

        if (!fullscreen)
            alertDialog.setView(binding.getRoot());

        if (!isShow())
            alertDialog.show();
    }

    public void dismiss() {
        if (isShow())
            alertDialog.dismiss();
    }

    public boolean isShow() {
        return alertDialog.isShowing();
    }

    public void items(List<String> srName, final DialogInterface.OnClickListener listener) {
        ListView listView = new ListView(context);
        listView.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, srName));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            listener.onClick(dialog, position);
            dismiss();
        });

        LinearLayout container = binding.dialogContainer;
        container.removeAllViews();

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        container.addView(listView, params);
    }

    public void items(CharSequence[] srName, final DialogInterface.OnClickListener listener) {
        ListView listView = new ListView(context);
        listView.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, srName));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            listener.onClick(dialog, position);
            dismiss();
        });
        LinearLayout container = binding.dialogContainer;
        container.removeAllViews();

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        container.addView(listView, params);
    }

    public Dialog create() {
        return alertDialog;
    }

    public class DialogViewModel {


        private ObservableField<String> title = new ObservableField<>("-");
        private ObservableField<String> message = new ObservableField<>("-");

        public ObservableField<String> getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title.set(title);
        }

        public ObservableField<String> getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message.set(message);
        }

        public void onCloseClick() {
            dismiss();
        }
    }


    public interface OnBindViewListener {
        void onBindView(View v);
    }
}
