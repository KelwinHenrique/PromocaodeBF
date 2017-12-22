package kelwin.primeiro.promobf;

import java.io.Serializable;

/**
 * Created by kelwi on 12/10/2017.
 */

public class Cupom implements Serializable {
    private String id;
    private String description;
    private String code;
    private String discount;
    private String vigencia;
    private String link;
    private Categoria categoria;
    private Loja loja;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    @Override
    public String toString() {
        return "Cupom{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", discount='" + discount + '\'' +
                ", vigencia='" + vigencia + '\'' +
                ", link='" + link + '\'' +
                ", categoria=" + categoria.getNome() +
                ", loja=" + loja.getName() +
                '}';
    }
}
