package se.kth.eit.trackit.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * The class representing DiaryEntry objects and connected to database via ORM.
 */
@DatabaseTable(tableName = "diary_entries")
public class DiaryEntry {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private DiaryEntryType type;

    @DatabaseField(canBeNull = false)
    private String entry;

    @DatabaseField(canBeNull = false)
    private Date date;

    DiaryEntry() {
        //needed by ORMLite.
    }

    public DiaryEntry(DiaryEntryType type, String entry, Date date) {
        this.type = type;
        this.entry = entry;
        this.date = date;
    }
}
