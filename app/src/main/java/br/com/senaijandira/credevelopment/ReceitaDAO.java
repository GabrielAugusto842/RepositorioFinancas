package br.com.senaijandira.credevelopment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 17170087 on 20/03/2018.
 */

public class ReceitaDAO {

    private Integer idReceita = 0;

    private static ReceitaDAO instance;

    public static ReceitaDAO getInstance() {
        if (instance==null) {
            instance = new ReceitaDAO();
        }
        return instance;
    }

    public Long inserir(Context ctx, Receita r) {
        SQLiteDatabase db = new DbHelper(ctx).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", r.getNome());
        valores.put("valor", r.getValor());
        valores.put("categoria", r.getCategoria());
        valores.put("dt_recebimento", r.getData_recebimento().getTime());

        Long id = db.insert("tbl_receita", null, valores);

        return id;

    }

    public ArrayList<Receita> selecionarTodos(Context ctx) {
        ArrayList<Receita> retorno = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(ctx).getReadableDatabase();

        String sql = "select * from tbl_receita;";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            Receita r = new Receita();
            r.setId(cursor.getInt(0));
            r.setNome(cursor.getString(1));
            r.setValor(cursor.getFloat(2));
            r.setCategoria(cursor.getString(3));
            r.setData_recebimento(new Date(cursor.getLong(4)));

            retorno.add(r);

        }

        return retorno;

    }

    public Receita selecionarUm (Context ctx, int id) {
        SQLiteDatabase db = new DbHelper(ctx).getReadableDatabase();

        String sql = "select * from tbl_receita where _id = " + id;

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            Receita r = new Receita();
            r.setId(cursor.getInt(0));
            r.setNome(cursor.getString(1));
            r.setValor(cursor.getFloat(2));
            r.setCategoria(cursor.getString(3));
            r.setData_recebimento(new Date(cursor.getLong(4)));

            cursor.close();
            return r;
        }

        return null;
    }

    public Boolean remover(Context ctx, Integer id) {
        SQLiteDatabase db = new DbHelper(ctx).getWritableDatabase();

        db.delete("tbl_receita", "_id = ?", new String[]{id.toString()});

        return true;
    }

    public Boolean atualizar (Context ctx, Receita r) {
        SQLiteDatabase db = new DbHelper(ctx).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", r.getNome());
        valores.put("valor", r.getValor());
        valores.put("categoria", r.getCategoria());
        valores.put("dt_recebimento", r.getData_recebimento().getTime());

        db.update("tbl_receita", valores, "_id = ?", new String[]{r.getId().toString()});

        return true;
    }

    /*public void pegarValor (Context ctx) {
        double[] total = new Double[] {0, 0};
        SQLiteDatabase db = new DbHelper(ctx).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM((tipo = 0) * valor) AS despesa")
    }*/

}
