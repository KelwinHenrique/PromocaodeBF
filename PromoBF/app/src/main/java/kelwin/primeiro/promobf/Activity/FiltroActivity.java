package kelwin.primeiro.promobf.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kelwin.primeiro.promobf.R;

public class FiltroActivity extends Activity {

    CheckBox cb_brinquedos;
    CheckBox cb_livros;
    CheckBox cb_games;
    CheckBox cb_informatica;
    CheckBox cb_tv;
    CheckBox cb_filmes;
    CheckBox cb_moda;
    CheckBox cb_eletrodomesticos;
    CheckBox cb_moveis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);
        cb_brinquedos = (CheckBox)findViewById(R.id.cb_brinquedos);
        cb_livros = (CheckBox)findViewById(R.id.cb_livros);
        cb_games = (CheckBox)findViewById(R.id.cb_games);
        cb_informatica = (CheckBox)findViewById(R.id.cb_informatica);
        cb_tv = (CheckBox)findViewById(R.id.cb_tv);
        cb_filmes = (CheckBox)findViewById(R.id.cb_filmes);
        cb_moda = (CheckBox)findViewById(R.id.cb_moda);
        cb_eletrodomesticos = (CheckBox)findViewById(R.id.cb_eletrodomesticos);
        cb_moveis = (CheckBox)findViewById(R.id.cb_moveis);



        cb_brinquedos.setChecked(ActivityPrincipal.filtroBrinquedo);
        cb_livros.setChecked(ActivityPrincipal.filtroLivros );
        cb_games.setChecked(ActivityPrincipal.filtroGames);
        cb_informatica.setChecked(ActivityPrincipal.filtroInformatica);
        cb_tv.setChecked(ActivityPrincipal.filtroTv);
        cb_filmes.setChecked(ActivityPrincipal.filtroFilmes);
        cb_moda.setChecked(ActivityPrincipal.filtroModa);
        cb_eletrodomesticos.setChecked(ActivityPrincipal.filtroEletrodomestico);
        cb_moveis.setChecked(ActivityPrincipal.filtroMoveis);






    }

    public void filtrar(View view){


        ActivityPrincipal.existeFiltro = cb_brinquedos.isChecked() || cb_livros.isChecked() ||cb_games.isChecked() || cb_informatica.isChecked() || cb_tv.isChecked()
                || cb_filmes.isChecked() || cb_moda.isChecked()
                || cb_eletrodomesticos.isChecked() || cb_moveis.isChecked()  ;

        Log.i("existe Filtro ?", "" +  ActivityPrincipal.existeFiltro );
        ActivityPrincipal.filtroBrinquedo = cb_brinquedos.isChecked();
        ActivityPrincipal.filtroLivros = cb_livros.isChecked();
        ActivityPrincipal.filtroGames = cb_games.isChecked();
        ActivityPrincipal.filtroInformatica = cb_informatica.isChecked();
        ActivityPrincipal.filtroTv = cb_tv.isChecked();
        ActivityPrincipal.filtroFilmes= cb_filmes.isChecked();
        ActivityPrincipal.filtroModa = cb_moda.isChecked();
        ActivityPrincipal.filtroEletrodomestico = cb_eletrodomesticos.isChecked();
        ActivityPrincipal.filtroMoveis = cb_moveis.isChecked();



        finish();


    }
    public void cancelar(View view){

        ActivityPrincipal.existeFiltro = false;

        ActivityPrincipal.filtroBrinquedo = false;
        ActivityPrincipal.filtroLivros = false;
        ActivityPrincipal.filtroGames = false;
        ActivityPrincipal.filtroInformatica = false;
        ActivityPrincipal.filtroTv = false;
        ActivityPrincipal.filtroFilmes= false;
        ActivityPrincipal.filtroModa = false;
        ActivityPrincipal.filtroEletrodomestico = false;
        ActivityPrincipal.filtroMoveis = false;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       /* if(user.getEmail().compareTo("kelhike@gmail.com")==0){
            Intent it = new Intent(FiltroActivity.this, CadastroActivity.class);
            startActivity(it);
        }*/


        finish();
    }
}
