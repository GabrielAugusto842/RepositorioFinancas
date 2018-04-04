package br.com.senaijandira.credevelopment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 17170087 on 28/03/2018.
 */

public class DespesaDAO {

    private Integer idDespesa = 0;

    private static DespesaDAO instance;

    public static DespesaDAO getInstance() {
        if (instance==null) {
            instance = new DespesaDAO();
        }
        return instance;
    }

    public Long inserir(Context ctx, Despesa d) {
        SQLiteDatabase db = new DbHelper(ctx).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", d.getNome());
        valores.put("valor", d.getValor());
        valores.put("categoria", d.getCategoria());
        valores.put("dt_inicio", d.getDt_inicio().getTime());
        valores.put("dt_vencimento", d.getDt_vencimento().getTime());

        Long id = db.insert("tbl_despesa", null, valores);

        return id;

    }

    public ArrayList<Despesa> selecionarTodos(Context ctx) {
        ArrayList<Despesa> retorno = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(ctx).getReadableDatabase();

        String sql = "select * from tbl_despesa;";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            Despesa d = new Despesa();
            d.setId(cursor.getInt(0));
            d.setNome(cursor.getString(1));
            d.setValor(cursor.getFloat(2));
            d.setCategoria(cursor.getString(3));
            d.setDt_inicio(new Date(cursor.getLong(4)));
            d.setDt_vencimento(new Date(cursor.getLong(5)));

            retorno.add(d);
        }

        return retorno;
    }

    public Despesa selecionarUm(Context ctx, int id) {
        SQLiteDatabase db = new DbHelper(ctx).getReadableDatabase();

        String sql = "select * from tbl_despesa where _id = " + id;

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            Despesa d = new Despesa();
            d.setId(cursor.getInt(0));
            d.setNome(cursor.getString(1));
            d.setValor(cursor.getFloat(2));
            d.setCategoria(cursor.getString(3));
            d.setDt_inicio(new Date(cursor.getLong(4)));
            d.setDt_vencimento(new Date(cursor.getLong(5)));

            cursor.close();
            return d;
        }

        return null;
    }

    public Boolean remover (Context ctx, Integer id) {
        SQLiteDatabase db = new DbHelper(ctx).getWritableDatabase();

        db.delete("tbl_despesa", "_id=?", new String[]{id.toString()});

        return true;
    }

    public Boolean atualizar (Context ctx, Despesa d) {
        SQLiteDatabase db = new DbHelper(ctx).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", d.getNome());
        valores.put("valor", d.getValor());
        valores.put("categoria", d.getCategoria());
        valores.put("dt_inicio", d.getDt_inicio().getTime());
        valores.put("dt_recebimento", d.getDt_vencimento().getTime());

        db.update("tbl_despesa", valores, "_id = ?", new String[]{d.getId().toString()});

        return true;
    }

}
