package kelwin.primeiro.promobf.Helper;

import android.util.Base64;

/**
 * Created by kelwi on 21/09/2017.
 */

public class Base64Custom {

    public static String codificarBase64(String texto){
        return  Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\r)", "");

    }

    public static String decodificarBase64(String texto){
        return new String(Base64.decode(texto, Base64.DEFAULT));
    }
}
