package com.example.listacompra;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Adapter mAdapter;
    private SQLiteDatabase listaDatabase;
    private EditText etNome;
    private TextView tvQtd;
    private int qtd = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListaDBHelper dbHelper = new ListaDBHelper(this);
        listaDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Adapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                removeItem((long) viewHolder.itemView.getTag());

            }
        }).attachToRecyclerView(recyclerView);

        etNome = findViewById(R.id.etProduto);
        tvQtd = findViewById(R.id.tvQuantidade);

        Button btnMais = findViewById(R.id.btMais);
        Button btnMenos = findViewById(R.id.btMenos);
        Button btnAdicionar = findViewById(R.id.btAdicionar);

        btnMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aumentar();
            }
        });

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diminuir();
            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });
    }


        private void aumentar(){
            qtd++;
            tvQtd.setText(String.valueOf(qtd));
        }

        private void diminuir(){
            if (qtd > 0) {
                qtd--;
                tvQtd.setText(String.valueOf(qtd));
            }
        }

        private void adicionar(){
            if (etNome.getText().toString().trim().length() == 0 || qtd == 0){
                return;
            }

            String nome = etNome.getText().toString();
            ContentValues cv = new ContentValues();
            cv.put(Lista.Produtos.PRODUCT_NAME, nome);
            cv.put(Lista.Produtos.PRODUCT_QUANTITY, qtd);

            listaDatabase.insert(Lista.Produtos.TABLE_NAME,null,cv);
            mAdapter.swapCursor(getAllItems());

            etNome.getText().clear();
        }

        private void removeItem(long id){
            listaDatabase.delete(Lista.Produtos.TABLE_NAME,
                    Lista.Produtos._ID + "=" + id, null);
            mAdapter.swapCursor(getAllItems());
        }

        private Cursor getAllItems(){
            return listaDatabase.query(
                    Lista.Produtos.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    Lista.Produtos.TIMESTAMP + " DESC"
            );
        }
}
