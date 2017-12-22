package kelwin.primeiro.promobf.Helper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by kelwi on 21/09/2017.
 */

public final class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFirebase;

    public static DatabaseReference getFirebase(){
        if(referenciaFirebase == null) referenciaFirebase = FirebaseDatabase.getInstance().getReference();

        return  referenciaFirebase;
    }


}
