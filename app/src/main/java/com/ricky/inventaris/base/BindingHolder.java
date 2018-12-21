package com.ricky.inventaris.base;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.Objects;

public class BindingHolder {

    @BindingAdapter("android:onAutoCompleteItemClick")
    public static void onAutoCompleteItemClick(AutoCompleteTextView textView, AdapterView.OnItemClickListener listener) {
        textView.setOnItemClickListener(listener);
    }

    @BindingAdapter("autoCompleteAdapter")
    public static void setAutoCompleteAdapter(AutoCompleteTextView textView, ArrayAdapter arrayAdapter) {
        textView.setAdapter(arrayAdapter);
    }

    @BindingAdapter("spinnerAdapter")
    public static void setAdapter(Spinner spinner, ArrayAdapter adapter) {
        spinner.setAdapter(adapter);
    }

    @BindingAdapter("setItemClick")
    public static void setItemClick(AdapterView adapterView,
                                    AdapterView.OnItemClickListener onItemClickListener) {
        adapterView.setOnItemClickListener(onItemClickListener);
    }

    @BindingAdapter("setItemLongClick")
    public static void setItemLongClick(AdapterView adapterView,
                                        AdapterView.OnItemLongClickListener onItemLongClickListener) {
        adapterView.setOnItemLongClickListener(onItemLongClickListener);
    }

    @BindingAdapter("setItemSelect")
    public static void setItemSelect(AdapterView adapterView,
                                     AdapterView.OnItemSelectedListener onItemSelectedListener) {
        adapterView.setOnItemSelectedListener(onItemSelectedListener);
    }

    @BindingAdapter("check")
    public static void checkRadio(RadioGroup radioGroup, @IdRes int id) {
        radioGroup.check(id);
    }

    @BindingAdapter("selectItem")
    public static void selectItem(Spinner spinner, int pos) {
        spinner.setSelection(pos, true);
        spinner.invalidate();
    }

    @BindingAdapter("setImageBitmap")
    public static void setImage(ImageView image, @Nullable Bitmap bitmap) {
        if (bitmap != null)
            image.setImageBitmap(bitmap);
    }

    @BindingAdapter("htmlData")
    public static void setHtmlData(WebView webView, String html) {
        if (html == null)
            html = "-";

        if (html.trim().equals("null") || TextUtils.isEmpty(html))
            html = "-";

        webView.loadData(html, "text/html", "UTF-8");
    }
}
