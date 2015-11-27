package se.kth.eit.trackit.persistence;

import android.content.Context;
import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Database helper factory.
 * Allows to ensure that the database is opened and closed properly according to application
 * lifecycle and also allows to ensure that there is always only one instance of
 * DatabaseHelper object.
 */
public class HelperFactory {

    /**
     * Database Helper singleton object.
     */
    private static DatabaseHelper databaseHelper;

    /**
     * Returns Database Helper object.
     * @return Database Helper object.
     */
    public static DatabaseHelper getHelper(){
        return databaseHelper;
    }

    /**
     * Set ups Database Helper.
     * @param context Application context.
     */
    public static void setHelper(Context context){
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    /**
     * Releases Database Helper object.
     */
    public static void releaseHelper(){
        OpenHelperManager.releaseHelper();
        databaseHelper = null;
    }
}
