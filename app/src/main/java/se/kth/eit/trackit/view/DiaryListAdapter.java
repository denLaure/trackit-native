package se.kth.eit.trackit.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import se.kth.eit.trackit.R;
import se.kth.eit.trackit.persistence.HelperFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * List adapter for diary list.
 */
public class DiaryListAdapter extends BaseAdapter {

    Context context;

    private List<String> datesList;

    public DiaryListAdapter(Context context, List<String> datesList) {
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
        final String date = datesList.get(i);
        ((TextView) view.findViewById(R.id.date_label)).setText(date);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClicked(view, date);
            }
        });
        return view;
    }

    /**
     * Shows or hides inner content of the card depending on whether it's already shown or not.
     * @param view Card view which content should be shown/hidden.
     * @param date Formatted date that corresponds to the given card view.
     */
    private void onItemClicked(View view, String date) {
        ViewGroup gr = (ViewGroup) view.findViewById(R.id.date_list_layout);
        if (gr.getChildCount() == 0) {
            try {
                List<String> list = HelperFactory.getHelper().getDiaryDAO()
                        .getEntriesByDate(date);
                for (String food : list) {
                    TextView tv = new TextView(context);
                    tv.setText(food);
                    ((ViewGroup) view.findViewById(R.id.date_list_layout)).addView(tv);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            gr.removeAllViews();
        }
    }
}
