package com.ricky.inventaris.activity.stock;

import com.ricky.inventaris.pojo.Stock;

/**
 * Created by Firman on 12/16/2018.
 */
public class ListStockViewModel {

    private final Stock stock;

    public ListStockViewModel(Stock stock) {
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }
}
