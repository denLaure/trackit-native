package se.kth.eit.trackit.persistence;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import se.kth.eit.trackit.model.DiaryEntry;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    /**
     * Returns list of all dates in the diary.
     * @return list of all dates in the diary.
     * @throws SQLException if failed to execute SQL query.
     */
    public List<Date> getAllDates() throws SQLException {
        QueryBuilder<DiaryEntry, Integer> queryBuilder = this.queryBuilder();
        PreparedQuery<DiaryEntry> preparedQuery = queryBuilder.distinct()
                .selectColumns(DiaryEntry.DATE_FIELD_NAME).prepare();
        List<DiaryEntry> diaryEntries = this.query(preparedQuery);
        List<Date> dates = new ArrayList<>();
        for (DiaryEntry entry : diaryEntries) {
            dates.add(entry.getDate());
        }
        return dates;
    }
}
