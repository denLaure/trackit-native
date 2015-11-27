package se.kth.eit.trackit.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import se.kth.eit.trackit.model.DiaryEntry;

import java.sql.SQLException;

/**
 * Database open helper.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    /**
     * Name of the database file.
     */
    private static final String DATABASE_NAME ="trackItDB.db";

    /**
     * Version of the database.
     * Should be increased and properly handled in the onUpgrade() method each time database
     * schema is changed.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Diary entries DAO object that allows to manipulate with diary entries stored in
     * database.
     */
    private DiaryEntriesDAO diaryDao = null;

    /**
     * Constructor.
     * @param context Application context.
     */
    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, DiaryEntry.class);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * For this course project we will not need any database migrations, therefore just drop
     * existing table and create new one.
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
            int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, DiaryEntry.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns diary entries DAO singleton object.
     * @return Diary entries DAO singleton object.
     * @throws SQLException in case something went wrong.
     */
    public DiaryEntriesDAO getDiaryDAO() throws SQLException {
        if (diaryDao == null) {
            diaryDao = new DiaryEntriesDAO(getConnectionSource(), DiaryEntry.class);
        }
        return diaryDao;
    }

    @Override
    public void close() {
        super.close();
        diaryDao = null;
    }
}
