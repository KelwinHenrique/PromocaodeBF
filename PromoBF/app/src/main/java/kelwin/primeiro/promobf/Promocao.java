package kelwin.primeiro.promobf;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dbugs on 22/08/2017.
 */

public class Promocao implements Serializable{
    private String titulo;
    private String valor;
    private String loja;
    private String url_imagem;
    private long visualizacoes;
    private long quantclassificacao;
    private long somaclassificacao;
    private String descricao;
    private String url_site;
    public String datapost;
    private String tipo;
    private long minutos;
    private long encerrada;
    private long codigo;


    private DatabaseReference firebase;
    public Promocao() {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public String getUrl_imagem() {
        return url_imagem;
    }

    public void setUrl_imagem(String url_imagem) {
        this.url_imagem = url_imagem;
    }

    public long getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(long visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public long getQuantclassificacao() {
        return quantclassificacao;
    }

    public void setQuantclassificacao(long quantclassificacao) {
        this.quantclassificacao = quantclassificacao;
    }

    public long getSomaclassificacao() {
        return somaclassificacao;
    }

    public void setSomaclassificacao(long somaclassificacao) {
        this.somaclassificacao = somaclassificacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl_site() {
        return url_site;
    }

    public void setUrl_site(String url_site) {
        this.url_site = url_site;
    }

    public String getDatapost() {
        return datapost;
    }

    public void setDatapost(String datapost) {
        this.datapost = datapost;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }





   /* public void updateDBvisualizacoes(){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/produtos/"+getTitulo()+"/visualizacoes", getVisualizacoes()+1);
        firebase = ConfiguracaoFirebase.getFirebase();
        firebase.updateChildren(childUpdates);
    }*/
    public void salvar(){
        DatabaseReference referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        referenciaFirebase.child("produtos").child(getCodigo()+"").setValue(this);
    }


    public long getMinutos() {
        return minutos;
    }

    public void setMinutos() {
        Date dataatual;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dataatual = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataatual);
        Date data_atual = cal.getTime();
        int dia = data_atual.getDate();
        Log.i("Dia:  ", dia+"");
        Date data_promo = null;
        try {
            data_promo= new Date(dateFormat.parse(getDatapost()).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long minutos1 =(long)((data_atual.getTime() - data_promo.getTime())/(86400000/(24*60)));
        this.minutos = minutos1;
    }

    public long getEncerrada() {
        return encerrada;
    }

    public void setEncerrada(long encerrada) {
        this.encerrada = encerrada;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
}
