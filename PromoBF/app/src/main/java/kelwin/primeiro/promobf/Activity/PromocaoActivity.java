package kelwin.primeiro.promobf.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import kelwin.primeiro.promobf.Helper.ConfiguracaoFirebase;
import kelwin.primeiro.promobf.Promocao;
import kelwin.primeiro.promobf.R;
import kelwin.primeiro.promobf.Usuario;

public class PromocaoActivity extends AppCompatActivity {

    RatingBar ratingBar;
    TextView tv_titulo;
    private ImageLoader imageLoader;
    private DatabaseReference firebase;
    private boolean jaavaliou;
    Promocao promocao;
    long valorultimaavaliaca;
    long encerrada;
    FirebaseUser user;
    Usuario usuario;
    private AdView mAdView;
    private boolean bugRatingBar;
    int avatual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocao);
        //Variaveis para interligar os componetes da activity
        tv_titulo = (TextView)findViewById(R.id.tv_titulo);
        TextView tv_dat_hora= (TextView) findViewById(R.id.tv_dat_hora);
        TextView tv_valor= (TextView) findViewById(R.id.tv_preco);
        TextView tv_loja= (TextView) findViewById(R.id.tv_loja);
        TextView tv_descricao= (TextView) findViewById(R.id.tv_descricao);
        final CheckBox cb_encerrada = (CheckBox)findViewById(R.id.cb_encerrada);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        bugRatingBar = false;
        avatual=-1;


        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //variaveis para a configuração da classificação
        jaavaliou = false;
        valorultimaavaliaca = 0;

        user = FirebaseAuth.getInstance().getCurrentUser();

        usuario = new Usuario();
        usuario.setId(user.getUid());

        promocao = (Promocao) getIntent().getSerializableExtra("promocaoSelecionada");

        firebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child( usuario.getId() )
                .child(promocao.getCodigo()+"");

        if(firebase != null)
            firebase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Listar produtos

                    for(DataSnapshot dados: dataSnapshot.getChildren()){
                        valorultimaavaliaca = (long) dados.getValue();
                        encerrada = (long) dados.getValue();
                        if(valorultimaavaliaca != 0 && avatual ==-1 ) ratingBar.setRating(valorultimaavaliaca);

                        if(encerrada ==1)cb_encerrada.setChecked(true);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });



        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingBar, float avaliacao, boolean fromUser) {
                if(avaliacao==0){
                    avaliacao=1;
                }
                if(valorultimaavaliaca != 0 ){
                    jaavaliou = true;
                }
                if(jaavaliou== false){
                    promocao.setQuantclassificacao(promocao.getQuantclassificacao()+1);
                    promocao.setSomaclassificacao((long) (promocao.getSomaclassificacao() + avaliacao));
                    avatual =  (int) avaliacao;
                    promocao.salvar();
                    valorultimaavaliaca = (int) avaliacao;
                    jaavaliou = true;

                    usuario.salvarAvaliacao(promocao, valorultimaavaliaca);

                }else{

                    promocao.setSomaclassificacao((long) (promocao.getSomaclassificacao() + avaliacao - valorultimaavaliaca));
                    avatual =  (int) avaliacao;
                    promocao.salvar();
                    valorultimaavaliaca = (long) avaliacao;

                    usuario.salvarAvaliacao(promocao, valorultimaavaliaca);

                }



            }
        });




        cb_encerrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_encerrada.isChecked()){
                    promocao.setEncerrada(promocao.getEncerrada()+1);
                    usuario.salvarEncerrada(promocao,1);
                    promocao.salvar();
                }else{
                    promocao.setEncerrada(promocao.getEncerrada()-1);
                    usuario.salvarEncerrada(promocao,0);
                    promocao.salvar();
                }
            }
        });



        promocao.setVisualizacoes(promocao.getVisualizacoes()+1);
        promocao.salvar();




        StringBuilder sb = new StringBuilder(promocao.getTitulo());
        if(promocao.getEncerrada() > 15) sb =new StringBuilder("[ENCERRADA] " +  sb);
        tv_titulo.setText(sb);
        tv_dat_hora.setText(promocao.getDatapost());
        tv_valor.setText("R$ "+promocao.getValor());
        tv_loja.setText(promocao.getLoja());
        tv_descricao.setText(promocao.getDescricao());

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


        imageLoader.displayImage(promocao.getUrl_imagem(), iv_imagem, null, new ImageLoadingListener() {
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
        myWebLink.setData(Uri.parse(promocao.getUrl_site()));
        startActivity(myWebLink);
    }
}
