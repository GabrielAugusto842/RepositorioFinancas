package br.com.senaijandira.credevelopment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroDespesaActivity extends AppCompatActivity {

    EditText edt_nome_despesa, edt_valor_despesa, edt_data_inicial, edt_data_vencimento;
    Spinner spn_categoria_despesa;
    Button btn_criar;
    String txt_valor;
    Float valor;
    Boolean modoEdicao = false;
    Despesa despesa;
    Integer i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_despesa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt_nome_despesa = (EditText) findViewById(R.id.edt_nome_despesa);
        edt_valor_despesa = (EditText) findViewById(R.id.edt_valor_despesa);
        spn_categoria_despesa = (Spinner) findViewById(R.id.spn_categoria_despesa);
        edt_data_inicial = (EditText) findViewById(R.id.edt_data_inicial);
        edt_data_vencimento = (EditText) findViewById(R.id.edt_data_vencimento);

        btn_criar = (Button) findViewById(R.id.btn_criar);

        Integer idDespesa = getIntent().getIntExtra("idDespesa", 0);

        if (idDespesa != 0) {
            modoEdicao = true;
            despesa = DespesaDAO.getInstance().selecionarUm(this, idDespesa);

            edt_nome_despesa.setText(despesa.getNome());
            edt_valor_despesa.setText(despesa.getValor().toString());
            while (i < spn_categoria_despesa.getCount()) {
                String spinner = spn_categoria_despesa.getItemAtPosition(i).toString();
                if (spinner.equals(despesa.getCategoria())) {
                    spn_categoria_despesa.setSelection(i);
                }
                i++;
            }
            edt_data_inicial.setText(new SimpleDateFormat("dd/MM/yyyy").format(despesa.getDt_inicio()));
            edt_data_vencimento.setText(new SimpleDateFormat("dd/MM/yyyy").format(despesa.getDt_vencimento()));
        }

    }

    public void criar(View view) {

        txt_valor = edt_valor_despesa.getText().toString().replace("R$","").replace(" ","").replace(",",".");

        valor = Float.parseFloat(txt_valor);

        if (edt_nome_despesa.getText().toString().isEmpty()) {
            edt_nome_despesa.setError("Preencha o nome");
            return;
        }

        if (txt_valor.toString().isEmpty()) {
            edt_valor_despesa.setError("Preencha o valor");
            return;
        }

        if (edt_data_inicial.getText().toString().isEmpty()) {
            edt_data_inicial.setError("Preencha a data de de recebimento da conta");
            return;
        }

        if (edt_data_vencimento.getText().toString().isEmpty()) {
            edt_data_vencimento.setError("Preencha a data de vencimento da conta");
            return;
        }

        Despesa d;

        if (modoEdicao) {
            d = despesa;
        } else {
            d = new Despesa();
        }

        d.setNome(edt_nome_despesa.getText().toString());
        d.setValor(valor);
        d.setCategoria(spn_categoria_despesa.getSelectedItem().toString());

        String data_inicial = edt_data_inicial.getText().toString();
        String data_vencimento = edt_data_vencimento.getText().toString();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date dt = df.parse(data_inicial);
            Date dt2 = df.parse(data_vencimento);
            d.setDt_inicio(dt);
            d.setDt_vencimento(dt2);
        } catch (ParseException e) {
            edt_data_inicial.setError("Digite uma data válida!");
            e.printStackTrace();
        }

        if (modoEdicao) {
            DespesaDAO.getInstance().atualizar(this, d);
            Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Long id = DespesaDAO.getInstance().inserir(this, d);
            Toast.makeText(this, "Inserido com sucesso", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(getApplicationContext(), VisualizarActivity.class);
        startActivity(intent);

        finish();

    }
}
