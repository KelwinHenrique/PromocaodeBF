package kelwin.primeiro.promobf;

import java.io.Serializable;

/**
 * Created by kelwi on 12/10/2017.
 */

public class Loja implements Serializable {
    private String id;
    private String name;
    private String image;
    private String link;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
