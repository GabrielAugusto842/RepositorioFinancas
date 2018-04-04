package br.com.senaijandira.credevelopment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by 17170087 on 21/03/2018.
 */

public class ReceitaAdapter extends ArrayAdapter<Receita> {

    public ReceitaAdapter(Context ctx, ArrayList<Receita> lst){
        super(ctx, 0, lst);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item, null);
        }

        final Receita item = getItem(position);

        TextView txt_item_nome = v.findViewById(R.id.txt_nome_receita);
        TextView txt_item_valor = v.findViewById(R.id.txt_valor_receita);
        TextView txt_item_data = v.findViewById(R.id.txt_data_receita);
        ImageView img_excluir_receita = v.findViewById(R.id.img_delete);
        ImageView img_editar_receita = v.findViewById(R.id.img_edit);

        final Context context = getContext();

        img_excluir_receita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Excluir receita");
                builder.setMessage("Tem certeza que deseja excluir a receita?");

                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ReceitaDAO.getInstance().remover(context, item.getId());
                        Intent intent = new Intent(context.getApplicationContext(), VisualizarActivity.class);
                        context.startActivity(intent);
                        ((VisualizarActivity)context).finish();
                    }
                });

                builder.setNegativeButton("NÃO", null);
                builder.create().show();
            }
        });

        img_editar_receita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CadastroReceitaActivity.class);
                intent.putExtra("idReceita", item.getId());
                context.startActivity(intent);
            }
        });


        txt_item_nome.setText(item.getNome());
        txt_item_valor.setText(item.getValor().toString());
        txt_item_data.setText(new SimpleDateFormat("dd/MM/yyyy").format(item.getData_recebimento()));

        return v;
    }
}
