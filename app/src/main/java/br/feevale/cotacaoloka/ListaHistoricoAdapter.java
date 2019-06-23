package br.feevale.cotacaoloka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import br.feevale.cotacaoloka.Cotacao;
import br.feevale.cotacaoloka.DataBase;
import br.feevale.cotacaoloka.R;

public class ListaHistoricoAdapter extends BaseAdapter {
    DataBase db;
    Context ctx;
    LayoutInflater inflater;

    public ListaHistoricoAdapter(Context ctx, DataBase db){
        inflater = LayoutInflater.from(ctx);
        this.db = db;
    }

    @Override
    public int getCount() {
        return db.getCotacoes().size();
    }

    @Override
    public Object getItem(int i) {
        return db.getCotacoes().get(i);
    }

    @Override
    public long getItemId(int i) {
        return db.getCotacoes().get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.historico_list_item, viewGroup, false);
        TextView txtDolar = (TextView) v.findViewById(R.id.TextDolarItem);
        TextView txtLibra = (TextView) v.findViewById(R.id.TextLibraItem);
        TextView txtPesoArgentino = (TextView) v.findViewById(R.id.TextPesoArgentinoItem);
        TextView txtData = (TextView) v.findViewById(R.id.textDataCotacao);

        Cotacao c = db.getCotacoes().get(i);
        txtDolar.setText(String.format("%.2f",1.00/c.getDolar()));
        txtLibra.setText(String.format("%.2f",1.00/c.getLibra()));
        txtPesoArgentino.setText(String.format("%.2f",1.00/c.getPeso_Argentino()));
        txtData.setText("Data: "+c.getDataCotacao());

        return v;
    }
}
