package se.kth.eit.trackit.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import se.kth.eit.trackit.R;
import se.kth.eit.trackit.model.DiaryEntry;

/**
 * The view to display particular diary item (symptom or food intake).
 */
public class DiaryEntryView extends LinearLayout {

    public DiaryEntryView(Context context, DiaryEntry diaryEntry) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.diary_entry_view, this);
        ((TextView) findViewById(R.id.time_label)).setText(diaryEntry.getTime());
    }
}
