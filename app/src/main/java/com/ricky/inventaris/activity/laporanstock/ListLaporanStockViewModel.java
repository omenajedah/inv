package com.ricky.inventaris.activity.laporanstock;

import com.ricky.inventaris.pojo.Laporan;

/**
 * Created by Firman on 12/16/2018.
 */
public class ListLaporanStockViewModel {

    private final Laporan laporan;

    public ListLaporanStockViewModel(Laporan laporan) {
        this.laporan = laporan;
    }

    public Laporan getLaporan() {
        return laporan;
    }
}
