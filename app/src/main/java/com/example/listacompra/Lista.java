package com.example.listacompra;

import android.provider.BaseColumns;

public class Lista {

    private Lista(){}

    public static final class Produtos implements BaseColumns {
        public static final String TABLE_NAME = "listaCompras";
        public static final String PRODUCT_NAME = "nome";
        public static final String PRODUCT_QUANTITY = "quantidade";
        public static final String TIMESTAMP = "timestamp";
    }

}
