package br.feevale.cotacaoloka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

public class Historico extends AppCompatActivity {
    ListView ListaCotacotacoes;
    ListaHistoricoAdapter adap;
    DataBase db;
    EditText edtValor;
    RadioGroup rdbGrupo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        ListaCotacotacoes = findViewById(R.id.ListaCotacoes);
        rdbGrupo = findViewById(R.id.rdbGroup);
        db = new DataBase(this);
        adap = new ListaHistoricoAdapter(getBaseContext(),db);
        ListaCotacotacoes.setAdapter(adap);
        edtValor = findViewById(R.id.edtValor);
    }

    public void Creditos(View v){
        Intent n = new Intent(this,Creditos.class);
        startActivity(n);
    }
}
