package se.kth.eit.trackit.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The class representing DiaryEntry objects and connected to database via ORM.
 */
@DatabaseTable(tableName = "diary_entries")
public class DiaryEntry {

    public final static String DATE_FIELD_NAME = "date";
    public final static String FORMATTED_DATE_FIELD_NAME = "formatted_date";
    public final static String ENTRY_FIELD_NAME = "entry";
    public final static String TIME_FIELD_NAME = "time";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private DiaryEntryType type;

    @DatabaseField(canBeNull = false, columnName = ENTRY_FIELD_NAME)
    private String entry;

    @DatabaseField(canBeNull = false, columnName = DATE_FIELD_NAME)
    private String date;

    @DatabaseField(canBeNull = false, columnName = FORMATTED_DATE_FIELD_NAME)
    private String formattedDate;

    @DatabaseField(canBeNull = false, columnName = TIME_FIELD_NAME)
    private String time;

    DiaryEntry() {
        //needed by ORMLite.
    }

    public DiaryEntry(DiaryEntryType type, String entry, Date date) {
        this.type = type;
        this.entry = entry;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        this.date = dateFormat.format(date);
        dateFormat = new SimpleDateFormat("EEEE', 'MMMM' 'd", Locale.US);
        this.formattedDate = dateFormat.format(date);
        dateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        this.time = dateFormat.format(date);
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public String getEntry() {
        return entry;
    }

    public String getTime() {
        return time;
    }
}
