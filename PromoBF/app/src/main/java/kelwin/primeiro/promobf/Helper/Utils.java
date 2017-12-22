package kelwin.primeiro.promobf.Helper;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by kelwi on 03/10/2017.
 */

public class Utils {

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
}
