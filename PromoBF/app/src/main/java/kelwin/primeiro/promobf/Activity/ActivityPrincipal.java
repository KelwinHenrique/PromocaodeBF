package kelwin.primeiro.promobf.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import kelwin.primeiro.promobf.Adapter.TabAdapter;
import kelwin.primeiro.promobf.Categoria;
import kelwin.primeiro.promobf.Cupom;
import kelwin.primeiro.promobf.Helper.HttpHandle;
import kelwin.primeiro.promobf.Helper.SlidingTabLayout;
import kelwin.primeiro.promobf.Helper.Utils;
import kelwin.primeiro.promobf.Loja;
import kelwin.primeiro.promobf.Promocao;
import kelwin.primeiro.promobf.R;


public class ActivityPrincipal extends AppCompatActivity {

    private static final String TAG = ActivityPrincipal.class.getSimpleName();
    public static boolean existeFiltro = false;
    public static boolean filtroBrinquedo = false;
    public static boolean filtroLivros = false;
    public static boolean filtroGames = false;
    public static boolean filtroInformatica = false;
    public static boolean filtroTv = false;
    public static boolean filtroFilmes = false;
    public static boolean filtroModa = false;
    public static boolean filtroEletrodomestico = false;
    public static boolean filtroMoveis = false;

    public static ArrayList<Cupom> cuponsArray;
    String json;





    //firebase autenticação
    private FirebaseAuth mAuth;

    private AlertDialog alerta;

    private Toolbar toolbar;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //Verificando se tem algum usuario logado
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Utils.getDatabase();
        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-6768701423924563~8614237565");


        cuponsArray = new ArrayList<>();
        new GetCuppons().execute();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {


            /*Promocao promocao = new Promocao();
            promocao.setCodigo();
            promocao.setDatapost("15/11/2017 16:32:00");
            promocao.setDescricao("Otímo preço!Em até 2x\n\nNão perca essa promoção! \nSempre visite nosso aplicativo para mais promoções.");
            promocao.setEncerrada(0);
            promocao.setLoja("Shoptime");
            promocao.setQuantclassificacao(1);
            promocao.setVisualizacoes(1);
            promocao.setSomaclassificacao(5);
            String tipo[] = { "Brinquedos", "Livros", "Games", "Informática",
                    "TV", "Filmes" , "Moda", "Eletrodomesticos", "Móveis"};
            promocao.setTipo(tipo[0]);
            promocao.setTitulo("Barraca 3x1 Angry Birds com Bolinhas - Bang Toys");
            promocao.setUrl_imagem("");
            promocao.setUrl_site("");
            promocao.setValor("99,99");
            promocao.salvar();*/
        } else {
            Intent it = new Intent(ActivityPrincipal.this, LogarActivity.class);
            startActivity(it);
            finish();
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar_principal);
        toolbar.setTitle("Promoções de Black Friday");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorBlack));
        setSupportActionBar(toolbar);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_pagina);

        //configurar aliiding tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorBlack));


        //Configurar adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);


        slidingTabLayout.setViewPager(viewPager);


    }




    private class GetCuppons extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandle sh = new HttpHandle();

            cuponsArray.clear();
            if(verificaConexao(ActivityPrincipal.this)!= false){

                json = sh.makeServiceCall("https://api.lomadee.com/v2/1507836726255ab36727c/coupon/_all/?sourceId=35854421");
                if (json != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        JSONArray cupons = jsonObject.getJSONArray("coupons");

                        for(int i=0 ; i< cupons.length(); i++){
                            JSONObject c = cupons.getJSONObject(i);

                            Cupom cupom = new Cupom();
                            cupom.setId(c.getString("id"));
                            cupom.setDescription(c.getString("description"));
                            cupom.setCode(c.getString("code"));
                            cupom.setDiscount(c.getString("discount"));
                            cupom.setVigencia(c.getString("vigency"));
                            cupom.setLink(c.getString("link"));

                            JSONObject l = c.getJSONObject("store");
                            Loja loja = new Loja();
                            loja.setId(l.getString("id"));
                            loja.setName(l.getString("name"));
                            loja.setImage(l.getString("image"));
                            loja.setLink(l.getString("link"));
                            cupom.setLoja(loja);


                            JSONObject cat = c.getJSONObject("category");
                            Categoria categoria = new Categoria();
                            categoria.setId(cat.getString("id"));
                            categoria.setNome(cat.getString("name"));
                            cupom.setCategoria(categoria);
                            Log.i("CUPOM", cupom.toString());
                            cuponsArray.add(cupom);


                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }



            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_info:
                Intent it1 = new Intent(ActivityPrincipal.this, InfoActivity.class);
                startActivity(it1);
                break;
            case R.id.item_filtro:
                Intent it = new Intent(ActivityPrincipal.this, FiltroActivity.class);
                startActivity(it);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }

    public static boolean verificaConexao(Context contexto){
        ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);//Pego a conectividade do contexto o qual o metodo foi chamado
        NetworkInfo netInfo = cm.getActiveNetworkInfo();//Crio o objeto netInfo que recebe as informacoes da NEtwork
        //System.out.println("NETWORK INFO: "+netInfo.getSubtypeName());
        if ( (netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable()) ) //Se o objeto for nulo ou nao tem conectividade retorna false
            return true;
        else
            return false;
    }


}
