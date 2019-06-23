package br.feevale.cotacaoloka;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    DataBase db;
    Cotacao CotacaoPrincipal;
    HashMap<String, String> Map;
    RadioGroup Botoes;
    TextView txtValorConvertido;
    EditText edtValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleDateFormat data;
        Date Hoje = new Date();
        data = new SimpleDateFormat("dd/MM/yyyy");
        Botoes = findViewById(R.id.rdbGroup);
        edtValor = findViewById(R.id.edtValor);
        txtValorConvertido = findViewById(R.id.txtValorConvertido);
        db = new DataBase(this);
        if (db.getCotacao(data.format(Hoje)) == null) {
            try {
                String out = new ConsultaApi().execute("https://api.exchangerate-api.com/v4/latest/BRL").get();
                CriarConjuntoDados(out);
                Toast.makeText(this,"Nova cotação adquirida com sucesso",Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                System.out.println("Erro:     "+e.getStackTrace());
            }
        }else {
            CotacaoPrincipal = db.getCotacao(data.format(Hoje));
            if (CotacaoPrincipal == null){
                System.out.println("azedou");
            }else{
                System.out.println(CotacaoPrincipal.getDataCotacao());
                System.out.println(CotacaoPrincipal.getDolar());
                System.out.println(CotacaoPrincipal.getLibra());
                System.out.println(CotacaoPrincipal.getPeso_Argentino());
                System.out.println(CotacaoPrincipal.getId());
            }
        }
    }

    public void CriarConjuntoDados(String strJson){
         db = new DataBase(this);
        SimpleDateFormat data;
        Date Hoje = new Date();
        data = new SimpleDateFormat("dd/MM/yyyy");

        String arr [] = strJson.split(",");
        Map = new HashMap<>();
        for(int i=0;i<arr.length-1;i++){
            String arrAux[] = arr[i].split(":");
            Map.put(arrAux[0].replace("\"",""),arrAux[1].replace("\"",""));
        }
        CotacaoPrincipal = new Cotacao();
        CotacaoPrincipal.setLibra(Double.parseDouble(Map.get("GBP")));
        CotacaoPrincipal.setDolar(Double.parseDouble(Map.get("USD")));
        CotacaoPrincipal.setPeso_Argentino(Double.parseDouble(Map.get("ARS")));
        CotacaoPrincipal.setDataCotacao(data.format(Hoje));
        CotacaoPrincipal.setId(db.addCotacao(CotacaoPrincipal));
    }
    public void Historico(View v){
        Intent n = new Intent(getBaseContext(),Historico.class);
        startActivity(n);
    }

    public void Converter(View v){

        switch (Botoes.getCheckedRadioButtonId()){
            case R.id.rdbDolar:
                txtValorConvertido.setText(String.format("%.2f",CotacaoPrincipal.getDolar()*Double.parseDouble(edtValor.getText().toString())));
                break;
            case R.id.rdbLibra:
                txtValorConvertido.setText(String.format("%.2f",CotacaoPrincipal.getLibra()*Double.parseDouble(edtValor.getText().toString())));
                break;
            case R.id.rdbPesoArgentino:
                txtValorConvertido.setText(String.format("%.2f",CotacaoPrincipal.getPeso_Argentino()*Double.parseDouble(edtValor.getText().toString())));
                break;
        }
    }

}
