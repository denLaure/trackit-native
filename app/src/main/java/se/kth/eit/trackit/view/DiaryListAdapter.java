package se.kth.eit.trackit.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import se.kth.eit.trackit.R;

import java.util.Date;
import java.util.List;

/**
 * List adapter for diary list.
 */
public class DiaryListAdapter extends BaseAdapter {

    Context context;

    private List<Date> datesList;

    public DiaryListAdapter(Context context, List<Date> datesList) {
        this.context = context;
        this.datesList = datesList;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public int getCount() {
        return datesList.size();
    }

    @Override
    public Object getItem(int i) {
        return datesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.diary_list_item, viewGroup, false);
        }
        Date date = datesList.get(i);
        ((TextView) view.findViewById(R.id.date_label)).setText(date.toString());
        return view;
    }
}
