package se.kth.eit.trackit.persistence;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import se.kth.eit.trackit.model.DiaryEntry;

import java.sql.SQLException;

/**
 * Data Access Object for diary entries.
 */
public class DiaryEntriesDAO extends BaseDaoImpl<DiaryEntry, Integer> {

    /**
     * Default constructor
     * @param connectionSource Connection source object.
     * @param dataClass Data class.
     * @throws SQLException in case something went wrong.
     */
    protected DiaryEntriesDAO(ConnectionSource connectionSource, Class<DiaryEntry> dataClass)
            throws SQLException {
        super(connectionSource, dataClass);
    }
}
