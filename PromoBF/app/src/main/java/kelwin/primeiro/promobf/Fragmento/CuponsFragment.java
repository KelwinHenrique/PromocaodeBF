package kelwin.primeiro.promobf.Fragmento;


import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kelwin.primeiro.promobf.Activity.CupomActivity;
import kelwin.primeiro.promobf.Activity.PromocaoActivity;
import kelwin.primeiro.promobf.Categoria;
import kelwin.primeiro.promobf.Cupom;
import kelwin.primeiro.promobf.CupomAdapter;
import kelwin.primeiro.promobf.Helper.HttpHandle;
import kelwin.primeiro.promobf.Loja;
import kelwin.primeiro.promobf.Promocao;
import kelwin.primeiro.promobf.PromocaoAdapter;
import kelwin.primeiro.promobf.R;

import static kelwin.primeiro.promobf.Activity.ActivityPrincipal.cuponsArray;

/**
 * A simple {@link Fragment} subclass.
 */
public class CuponsFragment extends Fragment {
    private AdView mAdView;

    private ImageLoader imageLoader;
    ListView lista;

    //api lomadee
    //JSONParser parser = new JSONParser();


    public CuponsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cupons, container, false);


        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        lista = (ListView)view.findViewById(R.id.lv_promocoes);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(CuponsFragment.this.getContext(), CupomActivity.class);
                Cupom cupomSelecionado = null;
                cupomSelecionado = (Cupom) lista.getItemAtPosition(position);
                it.putExtra("cupomSelecionado", cupomSelecionado);
                startActivity(it);

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




        carregarCupons();

        return  view;
    }

    private void adicionarCupom(){
        if (getActivity() != null) {
            ArrayAdapter adapter = new CupomAdapter(this.getContext(), cuponsArray, imageLoader);
            lista.setAdapter(adapter);
        }
    }



    private void carregarCupons() {

        adicionarCupom();
    }

}
