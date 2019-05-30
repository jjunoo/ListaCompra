package com.example.listacompra;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ListaViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public Adapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    public class ListaViewHolder extends RecyclerView.ViewHolder{
        public TextView produtoText;
        public TextView contText;

        public ListaViewHolder(@NonNull View itemView) {
            super(itemView);

            produtoText = itemView.findViewById(R.id.tvNomeItem);
            contText = itemView.findViewById(R.id.tvQuantidade);
        }
    }

    @Override
    public ListaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       LayoutInflater inflater = LayoutInflater.from(mContext);
       View view = inflater.inflate(R.layout.produtos, parent, false);
       return new ListaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListaViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }
        String nome = mCursor.getString(mCursor.getColumnIndex(Lista.Produtos.PRODUCT_NAME));
        int quantidade = mCursor.getInt(mCursor.getColumnIndex(Lista.Produtos.PRODUCT_QUANTITY));
        long id = mCursor.getLong(mCursor.getColumnIndex(Lista.Produtos._ID));


        holder.produtoText.setText(nome);
        holder.contText.setText(String.valueOf(quantidade));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if (mCursor != null){
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null){
            notifyDataSetChanged();
        }
    }


}
