package br.com.senaijandira.credevelopment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 17170087 on 20/03/2018.
 */

public class DbHelper extends SQLiteOpenHelper {
    //nome do banco
    private static String DB_NAME = "receita.db";
    //versão do banco
    private static int DB_VERSION = 1;

    //construtor da classe para criacao do banco
    public DbHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    //criacao do banco
    @Override
    public void onCreate (SQLiteDatabase db) {
        String sql = "create table tbl_receita( " +
                "_id integer primary key autoincrement, " +
                "nome text, " +
                "valor real, " +
                "categoria text, " +
                "dt_recebimento integer )";

        String sql2 = "create table tbl_despesa ( " +
                "_id integer primary key autoincrement, " +
                "nome text, " +
                "valor real, " +
                "categoria text, " +
                "dt_inicio integer, " +
                "dt_vencimento integer )";
        db.execSQL(sql);
        db.execSQL(sql2);
    }

    //Neste metodo será feito os ugrades do banco
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table tbl_receita;");
        db.execSQL("drop table tbl_despesa;");
        onCreate(db);
    }
}

