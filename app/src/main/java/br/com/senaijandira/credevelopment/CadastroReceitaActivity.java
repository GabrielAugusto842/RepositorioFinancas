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

public class CadastroReceitaActivity extends AppCompatActivity {

    EditText edt_nome_receita, edt_valor_receita, edt_data_recebimento;
    Spinner spn_categoria_receita;
    Button btn_criar;
    String txt_valor;
    Float valor;
    Boolean modoEdicao = false;
    Receita receita;
    Integer i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_receita);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt_nome_receita = (EditText) findViewById(R.id.edt_nome_receita);
        edt_valor_receita = (EditText) findViewById(R.id.edt_valor_receita);
        spn_categoria_receita = (Spinner) findViewById(R.id.spn_categoria_receita);
        edt_data_recebimento = (EditText) findViewById(R.id.edt_data_recebimento);

        btn_criar = (Button) findViewById(R.id.btn_criar);

        Integer idReceita = getIntent().getIntExtra("idReceita", 0);

        if (idReceita != 0) {
            modoEdicao = true;
            receita = ReceitaDAO.getInstance().selecionarUm(this, idReceita);

            edt_nome_receita.setText(receita.getNome());
            edt_valor_receita.setText(receita.getValor().toString());
            while (i < spn_categoria_receita.getCount()) {
                String spinner = spn_categoria_receita.getItemAtPosition(i).toString();
                if (spinner.equals(receita.getCategoria())) {
                    spn_categoria_receita.setSelection(i);
                    break;
                }
                i++;
            }
            edt_data_recebimento.setText(new SimpleDateFormat("dd/MM/yyyy").format(receita.getData_recebimento()));
        }

    }

    public void criar(View view) {

        txt_valor = edt_valor_receita.getText().toString().replace("R$","").replace(" ","").replace(",",".");

        valor = Float.parseFloat(txt_valor);

        if (edt_nome_receita.getText().toString().isEmpty()) {
            edt_nome_receita.setError("Preencha o nome");
            return;
        }

        if (txt_valor.toString().isEmpty()) {
            edt_valor_receita.setError("Preencha o valor");
            return;
        }

        if (edt_data_recebimento.getText().toString().isEmpty()) {
            edt_data_recebimento.setError("Preencha a data de recebimento da receita");
            return;
        }

        Receita r;

        if (modoEdicao) {
            r = receita;
        } else {
            r = new Receita();
        }

        r.setNome(edt_nome_receita.getText().toString());
        r.setValor(valor);
        r.setCategoria(spn_categoria_receita.getSelectedItem().toString());

        String data_recebimento = edt_data_recebimento.getText().toString();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date dt = df.parse(data_recebimento);
            r.setData_recebimento(dt);
        } catch (ParseException e) {
            edt_data_recebimento.setError("Preencha uma data correta");
            e.printStackTrace();
        }

        if (modoEdicao) {
            ReceitaDAO.getInstance().atualizar(this, r);
            Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            //inserindo no banco
            Long id = ReceitaDAO.getInstance().inserir(this, r);

            Toast.makeText(this, "Inserido com sucesso", Toast.LENGTH_SHORT).show();

        }
        Intent intent = new Intent(getApplicationContext(), VisualizarActivity.class);
        startActivity(intent);

        finish();
    }
}
