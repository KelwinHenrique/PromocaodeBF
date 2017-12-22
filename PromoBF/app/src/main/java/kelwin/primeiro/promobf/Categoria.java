package kelwin.primeiro.promobf;

import java.io.Serializable;

/**
 * Created by kelwi on 12/10/2017.
 */

public class Categoria implements Serializable {
    private String id;
    private String nome;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
