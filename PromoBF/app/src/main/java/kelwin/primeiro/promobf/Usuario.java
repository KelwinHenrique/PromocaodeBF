package kelwin.primeiro.promobf;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import kelwin.primeiro.promobf.Helper.ConfiguracaoFirebase;

/**
 * Created by kelwi on 21/09/2017.
 */

public class Usuario {
    private String nome;
    private String id;
    private String email;


    public Usuario(){

    }
    public void salvar(){
        DatabaseReference referenciaFirebase =  ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuarios").child( getId() ).setValue(this);
    }

    public void salvarAvaliacao(Promocao promocao, long nota){
        DatabaseReference referenciaFirebase =  ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuarios").child( getId() )
                .child(promocao.getCodigo()+"").child("nota")
                .setValue(nota);
    }

    public void salvarEncerrada(Promocao promocao, long encerrada){
        DatabaseReference referenciaFirebase =  ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuarios").child( getId() )
                .child(promocao.getCodigo()+"").child("encerrada")
                .setValue(encerrada);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
