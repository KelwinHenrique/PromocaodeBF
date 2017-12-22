package kelwin.primeiro.promobf;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dbugs on 22/08/2017.
 */

public class PromocaoAdapter extends ArrayAdapter<Promocao> {


    private final Context context;
    private final ArrayList<Promocao> elementos;
    private ImageLoader il;
    private Date dataatual;

    public PromocaoAdapter(Context context, ArrayList<Promocao> elementos, ImageLoader il) {
        super(context, R.layout.modelo_layout_listview_principal, elementos);
        this.context = context;
        this.elementos = elementos;
        this.il = il;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.modelo_layout_listview_principal, parent, false);
        TextView tv_dat_hora = (TextView) rowView.findViewById(R.id.tv_dat_hora);


        //Trabalhando com horas
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dataatual = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataatual);
        Date data_atual = cal.getTime();
        int dia = data_atual.getDate();


        Date data_promo = null;
        try {
            data_promo = new Date(dateFormat.parse(elementos.get(position).getDatapost()).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long minutos = (long) ((data_atual.getTime() - data_promo.getTime()) / (86400000 / (24 * 60)));

        String tempo = null;
        if (minutos < 60) {
            tempo = minutos + " minutos";
        } else if (minutos < 1440) {
            if (minutos / 60 == 1) {
                tempo = minutos / 60 + " hora";
            } else {
                tempo = minutos / 60 + " horas";
            }

        } else {
            if (minutos / 1440 == 1) {
                tempo = minutos / 1440 + " dia";
            } else {
                tempo = minutos / 1440 + " dias";
            }

        }


        tv_dat_hora.setText(tempo);


        TextView tv_titulo = (TextView) rowView.findViewById(R.id.tv_titulo);
        TextView tv_loja = (TextView) rowView.findViewById(R.id.tv_loja);
        TextView tv_valor = (TextView) rowView.findViewById(R.id.tv_valor);

        TextView tv_classificacao = (TextView) rowView.findViewById(R.id.tv_classificacao);
        TextView tv_visualizacao = (TextView) rowView.findViewById(R.id.tv_visualizacao);

        ImageView iv_imagem = (ImageView) rowView.findViewById(R.id.iv_imagem);





        StringBuilder sb = new StringBuilder(elementos.get(position).getTitulo());
        if(elementos.get(position).getEncerrada() > 15) sb =new StringBuilder("[ENCERRADA] " +  sb);
        if(sb.length() > 70){
            tv_titulo.setText(sb.substring(0, 70)+"...");
        }else{
            tv_titulo.setText(sb);
        }






        tv_loja.setText(elementos.get(position).getLoja());
        tv_valor.setText("R$ " + elementos.get(position).getValor());


        //tv_dat_hora.setText(elementos.get(position).getDatapost());
        double classificacao = (float) elementos.get(position).getSomaclassificacao() / ((float) (elementos.get(position).getQuantclassificacao()));
        // if(elementos.get(position).getQuantclassificacao() != 0)

        String decimalFormat = new DecimalFormat("0.0").format(classificacao);
        tv_classificacao.setText(new String("" + decimalFormat));
        long visualizacoes = elementos.get(position).getVisualizacoes();
        tv_visualizacao.setText(new String("" + visualizacoes));


        il.displayImage(elementos.get(position).getUrl_imagem(), iv_imagem, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Log.i("Imagem tá caregando", "Imagem tá caregando");
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Log.i("Imagem falhou", "Imagem falhou");
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Log.i("Imagem carregou", "Imagem carregou");
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                Log.i("Imagem cancelada", "Imagem cancelada");
            }
        });


        //wb_imagem.loadUrl(elementos.get(position).getUrl_imagem());

        return rowView;

    }
}
