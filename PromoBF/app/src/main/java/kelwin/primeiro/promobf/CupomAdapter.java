package kelwin.primeiro.promobf;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
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
 * Created by kelwi on 12/10/2017.
 */

public class CupomAdapter extends ArrayAdapter<Cupom> {
    private final Context context;
    private final ArrayList<Cupom> elementos;
    private ImageLoader il;
    private Date dataatual;

    public CupomAdapter(Context context, ArrayList<Cupom> elementos, ImageLoader il) {
        super(context,R.layout.modelo_layout_listview_cupom, elementos);
        this.context = context;
        this.elementos = elementos;
        this.il = il;

    }





    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.modelo_layout_listview_cupom, parent, false);




        TextView tv_validade= (TextView) rowView.findViewById(R.id.tv_validade);
        TextView tv_titulo= (TextView) rowView.findViewById(R.id.tv_titulo);
        TextView tv_loja= (TextView) rowView.findViewById(R.id.tv_loja);
        TextView tv_desconto= (TextView) rowView.findViewById(R.id.tv_desconto);


        ImageView iv_imagem = (ImageView) rowView.findViewById(R.id.iv_imagem_loja);


        StringBuilder sb = new StringBuilder(elementos.get(position).getDescription());
        if(sb.length() > 75){
            tv_titulo.setText(sb.substring(0, 75)+"...");
        }else{
            tv_titulo.setText(sb);
        }

        tv_loja.setText(elementos.get(position).getLoja().getName());
        tv_desconto.setText(elementos.get(position).getDiscount() + "%");




        il.displayImage(elementos.get(position).getLoja().getImage(), iv_imagem, null, new ImageLoadingListener() {
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
