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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by 17170087 on 28/03/2018.
 */

public class DespesaAdapter extends ArrayAdapter<Despesa> {

    public DespesaAdapter(Context context, ArrayList<Despesa> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_despesas, null);
        }

        final Despesa item = getItem(position);

        TextView txt_nome_despesa = (TextView) v.findViewById(R.id.txt_nome_despesa);
        TextView txt_valor_despesa = (TextView) v.findViewById(R.id.txt_valor_despesa);
        TextView txt_data_despesa = (TextView) v.findViewById(R.id.txt_data_despesa);
        ImageView img_excluir_despesa = (ImageView) v.findViewById(R.id.img_delete_despesa);
        ImageView img_editar_despesa = (ImageView) v.findViewById(R.id.img_edit_despesa);

        final Context ctx = getContext();

        img_excluir_despesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Excluir despesa");
                builder.setMessage("Tem certeza que deseja excluir a despesa?");

                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DespesaDAO.getInstance().remover(ctx, item.getId());
                        Intent intent = new Intent(ctx.getApplicationContext(), VisualizarActivity.class);
                        ctx.startActivity(intent);
                        ((VisualizarActivity) ctx).finish();
                    }
                });

                builder.setNegativeButton("NÃO", null);
                builder.create().show();
            }
        });

        img_editar_despesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, CadastroDespesaActivity.class);
                intent.putExtra("idDespesa", item.getId());
                ctx.startActivity(intent);
            }
        });

        txt_nome_despesa.setText(item.getNome());
        txt_valor_despesa.setText(item.getValor().toString());
        txt_data_despesa.setText(new SimpleDateFormat("dd/MM/yyyy").format(item.getDt_vencimento()));

        return v;
    }
}
