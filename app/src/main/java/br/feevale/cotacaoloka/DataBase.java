package br.feevale.cotacaoloka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private Context ctx;
    public static final String DATABASE_NAME = "students.db";
    public static final Integer DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    private AcadDatabaseHelper dbHelper;


    public DataBase(Context ctx){
        this.ctx = ctx;
        dbHelper = new AcadDatabaseHelper();
        db = dbHelper.getWritableDatabase();
    }


    public static class CotacaoTable implements BaseColumns{
        public static final String TABLE_NAME = "cotacao";
        public static final String COLUMN_DOLAR = "dolar";
        public static final String COLUMN_LIBRA = "libra";
        public static final String COLUMN_PESO_ARGENTINO = "peso_argentino";
        public static final String COLUMN_DATA_COTACAO = "data_cotacao";

        public static String getSQL(){
            String sql = "CREATE TABLE " + TABLE_NAME + " ("+
                    _ID                  + " INTEGER PRIMARY KEY, " +
                    COLUMN_DOLAR          + " FLOAT, " +
                    COLUMN_LIBRA         +" FLOAT, " +
                    COLUMN_PESO_ARGENTINO        + " FLOAT, "+
                    COLUMN_DATA_COTACAO         +" TEXT)" ;
            return sql;
        }
    }

    public long addCotacao(Cotacao c){
        ContentValues values = new ContentValues();
        values.put(CotacaoTable.COLUMN_DOLAR, c.getDolar());
        values.put(CotacaoTable.COLUMN_LIBRA, c.getLibra());
        values.put(CotacaoTable.COLUMN_PESO_ARGENTINO, c.getPeso_Argentino());
        values.put(CotacaoTable.COLUMN_DATA_COTACAO, c.getDataCotacao());

        return db.insert(CotacaoTable.TABLE_NAME, null, values);
    }

    public Cotacao getCotacao(String data){
            String cols[] = {CotacaoTable._ID, CotacaoTable.COLUMN_DOLAR, CotacaoTable.COLUMN_LIBRA, CotacaoTable.COLUMN_PESO_ARGENTINO,CotacaoTable.COLUMN_DATA_COTACAO};
            String args[] = {data};
            Cursor cursor = db.query(CotacaoTable.TABLE_NAME, cols, CotacaoTable.COLUMN_DATA_COTACAO+"=?", args, null, null, CotacaoTable._ID);

            if(cursor.getCount()<=0){
                return null;
            }

            cursor.moveToNext();
            Cotacao s = new Cotacao();
            s.setId(cursor.getLong(cursor.getColumnIndex(CotacaoTable._ID)));
            s.setDataCotacao(cursor.getString(cursor.getColumnIndex(CotacaoTable.COLUMN_DATA_COTACAO)));
            s.setLibra(cursor.getDouble(cursor.getColumnIndex(CotacaoTable.COLUMN_LIBRA)));
            s.setDolar(cursor.getDouble(cursor.getColumnIndex(CotacaoTable.COLUMN_DOLAR)));
            s.setPeso_Argentino(cursor.getDouble(cursor.getColumnIndex(CotacaoTable.COLUMN_PESO_ARGENTINO)));

            return s;
    }

    public List<Cotacao> getCotacoes(){
        String cols[] = {CotacaoTable._ID, CotacaoTable.COLUMN_DOLAR, CotacaoTable.COLUMN_LIBRA, CotacaoTable.COLUMN_PESO_ARGENTINO,CotacaoTable.COLUMN_DATA_COTACAO};
        Cursor cursor = db.query(CotacaoTable.TABLE_NAME, cols, null, null, null, null, CotacaoTable.COLUMN_DATA_COTACAO);
        List<Cotacao> cotacoes = new ArrayList<>();
        Cotacao c;

        while(cursor.moveToNext()){
            c = new Cotacao();
            c.setId(cursor.getInt(cursor.getColumnIndex(CotacaoTable._ID)));
            System.out.println(cursor.getString(cursor.getColumnIndex(CotacaoTable.COLUMN_DATA_COTACAO)));
            c.setDolar(cursor.getDouble(cursor.getColumnIndex(CotacaoTable.COLUMN_DOLAR)));
            c.setLibra(cursor.getDouble(cursor.getColumnIndex(CotacaoTable.COLUMN_LIBRA)));
            c.setPeso_Argentino(cursor.getDouble(cursor.getColumnIndex(CotacaoTable.COLUMN_PESO_ARGENTINO)));
            c.setDataCotacao(cursor.getString(cursor.getColumnIndex(CotacaoTable.COLUMN_DATA_COTACAO)));
            cotacoes.add(c);
        }

        return cotacoes;
    }

    private class AcadDatabaseHelper extends SQLiteOpenHelper{

        AcadDatabaseHelper(){
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CotacaoTable.getSQL());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + CotacaoTable.TABLE_NAME);
            onCreate(db);
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true);
        }
    }
}
