package com.example.listacompra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.listacompra.Lista.*;

import java.util.List;

public class ListaDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "lista.db";
    public static final int DATABASE_VERSION = 1;

    public ListaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_LISTA_TABLE = "CREATE TABLE " +
                Produtos.TABLE_NAME + " (" +
                Produtos._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Produtos.PRODUCT_NAME + " TEXT NOT NULL, " +
                Produtos.PRODUCT_QUANTITY + " INTEGER NOT NULL, " +
                Produtos.TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ");";

        db.execSQL(SQL_CREATE_LISTA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Produtos.TABLE_NAME);
    }
}
