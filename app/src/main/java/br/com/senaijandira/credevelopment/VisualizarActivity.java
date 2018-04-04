package br.com.senaijandira.credevelopment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class VisualizarActivity extends AppCompatActivity {//implements AdapterView.OnItemClickListener {

    ListView list_view_receitas, list_view_despesas;
    ReceitaAdapter adapter_receita;
    DespesaAdapter adapter_despesa;
    ReceitaDAO r_dao;
    DespesaDAO d_dao;
    ImageView img_delete;
    Integer idReceita, idDespesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        r_dao = ReceitaDAO.getInstance();
        d_dao = DespesaDAO.getInstance();

        list_view_receitas = (ListView) findViewById(R.id.lst_receitas);
        list_view_despesas = (ListView) findViewById(R.id.lst_despesas);

        adapter_receita = new ReceitaAdapter(this, new ArrayList<Receita>());
        adapter_despesa = new DespesaAdapter(this, new ArrayList<Despesa>());

        list_view_receitas.setAdapter(adapter_receita);
        list_view_despesas.setAdapter(adapter_despesa);

        img_delete = (ImageView) findViewById(R.id.img_delete);

        Intent intent = getIntent();
        idDespesa = intent.getIntExtra("idDespesa", 0);
        idReceita = intent.getIntExtra("idReceita", 0);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Receita receita = ReceitaDAO.getInstance().selecionarUm(this, idReceita);

        ArrayList<Receita> receitasCadastradas;
        receitasCadastradas = r_dao.selecionarTodos(this);

        adapter_receita.clear();

        adapter_receita.addAll(receitasCadastradas);

        ArrayList<Despesa> despesasCadastradas;
        despesasCadastradas = d_dao.selecionarTodos(this);

        adapter_despesa.clear();

        adapter_despesa.addAll(despesasCadastradas);

    }

}
