package kelwin.primeiro.promobf.Fragmento;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kelwin.primeiro.promobf.Activity.ActivityPrincipal;
import kelwin.primeiro.promobf.Activity.PromocaoActivity;
import kelwin.primeiro.promobf.Helper.ConfiguracaoFirebase;
import kelwin.primeiro.promobf.Promocao;
import kelwin.primeiro.promobf.PromocaoAdapter;
import kelwin.primeiro.promobf.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaisRecentes extends Fragment {

    private ImageLoader imageLoader;
    ListView lista;
    ArrayList<Promocao> promocoes;
    ArrayList<Promocao> promocoesFiltradas;
    private AdView mAdView;
    //Firebase
    private DatabaseReference firebase;

    public MaisRecentes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_mais_recentes, container, false);
        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        lista = (ListView)view.findViewById(R.id.lv_promocoes);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(MaisRecentes.this.getContext(), PromocaoActivity.class);
                Promocao promocaoSelecionada = null;
                promocaoSelecionada = (Promocao) lista.getItemAtPosition(position);
                it.putExtra("promocaoSelecionada", promocaoSelecionada);
                startActivity(it);

            }
        });

        promocoes = new ArrayList<Promocao>();
        //Recuperar Promoçoes do firebase

        firebase = ConfiguracaoFirebase.getFirebase()
                .child("produtos");


        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Listar produtos
                promocoes.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Promocao produto = dados.getValue(Promocao.class);
                    produto.setMinutos();
                    promocoes.add(produto);
                }



                Collections.sort (promocoes, new Comparator() {
                    public int compare(Object o1, Object o2) {
                        Promocao p1 = (Promocao) o1;
                        Promocao p2 = (Promocao) o2;
                        return p1.getMinutos() < p2.getMinutos() ? -1 : p1.getMinutos() > p2.getMinutos() ? +1 : 0;
                    }
                });


                filtrarPromocoes();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(this.getContext())
                .defaultDisplayImageOptions(options)
                .memoryCacheSize(50 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .threadPoolSize(5)
                .writeDebugLogs()
                .build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(conf);

        return view;
    }


    private void adicionarPromocoes(){
        if (getActivity() != null) {
            ArrayAdapter adapter = new PromocaoAdapter(this.getContext(), promocoesFiltradas, imageLoader);
            lista.setAdapter(adapter);
        }
    }


    public  void filtrarPromocoes(){
        promocoesFiltradas = new ArrayList<Promocao>();

        for(Promocao promo: promocoes){
            if(ActivityPrincipal.existeFiltro == false){
                promocoesFiltradas.add(promo);

            }else{
                if(ActivityPrincipal.filtroBrinquedo) {
                    if (promo.getTipo().compareTo("Brinquedos") == 0) {
                        promocoesFiltradas.add(promo);
                    }
                }
                if(ActivityPrincipal.filtroLivros) {
                    if (promo.getTipo().compareTo("Livros") == 0) {
                        promocoesFiltradas.add(promo);
                    }
                }
                if(ActivityPrincipal.filtroGames) {
                    if (promo.getTipo().compareTo("Games") == 0) {
                        promocoesFiltradas.add(promo);
                    }
                }
                if(ActivityPrincipal.filtroInformatica) {
                    if (promo.getTipo().compareTo("Informática") == 0) {
                        promocoesFiltradas.add(promo);
                    }
                }
                if(ActivityPrincipal.filtroTv) {
                    if (promo.getTipo().compareTo("TV") == 0) {
                        promocoesFiltradas.add(promo);
                    }
                }
                if(ActivityPrincipal.filtroFilmes) {
                    if (promo.getTipo().compareTo("Filmes") == 0) {
                        promocoesFiltradas.add(promo);
                    }
                }
                if(ActivityPrincipal.filtroModa) {
                    if (promo.getTipo().compareTo("Moda") == 0) {
                        promocoesFiltradas.add(promo);
                    }
                }
                if(ActivityPrincipal.filtroEletrodomestico) {
                    if (promo.getTipo().compareTo("Eletrodomesticos") == 0) {
                        promocoesFiltradas.add(promo);
                    }
                }
                if(ActivityPrincipal.filtroMoveis) {
                    if (promo.getTipo().compareTo("Móveis") == 0) {
                        promocoesFiltradas.add(promo);
                    }
                }
            }
        }


        adicionarPromocoes();

    }

    @Override
    public void onResume(){
        super.onResume();
        filtrarPromocoes();

    }

}
