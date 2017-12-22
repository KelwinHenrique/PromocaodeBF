package kelwin.primeiro.promobf.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import kelwin.primeiro.promobf.Cupom;
import kelwin.primeiro.promobf.R;

public class CupomActivity extends AppCompatActivity {

    private ImageLoader imageLoader;
    private AdView mAdView;
    Cupom cupom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupom);
        TextView tv_validade= (TextView) findViewById(R.id.tv_validade);
        TextView tv_desconto= (TextView) findViewById(R.id.tv_desconto);
        TextView tv_descricao= (TextView) findViewById(R.id.tv_titulo);
        TextView tv_loja= (TextView) findViewById(R.id.tv_loja);
        TextView tv_cupom= (TextView) findViewById(R.id.tv_cupom);

        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        cupom = (Cupom) getIntent().getSerializableExtra("cupomSelecionado");

        tv_validade.setText(cupom.getVigencia());
        tv_desconto.setText(cupom.getDiscount()+"%");
        tv_descricao.setText(cupom.getDescription());
        tv_loja.setText(cupom.getLoja().getName());
        tv_cupom.setText(cupom.getCode());




        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .memoryCacheSize(50 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .threadPoolSize(5)
                .writeDebugLogs()
                .build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(conf);


        ImageView iv_imagem = (ImageView) findViewById(R.id.iv_imagem);

        imageLoader.displayImage(cupom.getLoja().getImage(), iv_imagem, null, new ImageLoadingListener() {
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



    }
    public void irparapromo(View view){
        Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        myWebLink.setData(Uri.parse(cupom.getLink()));
        startActivity(myWebLink);
    }
}
