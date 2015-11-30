package se.kth.eit.trackit.view;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import se.kth.eit.trackit.persistence.HelperFactory;

import java.sql.SQLException;

/**
 * Diary list fragment.
 */
public class DiaryFragment extends ListFragment {

    public DiaryFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DiaryFragment newInstance() {
        return new DiaryFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpListView();
        try {
            setListAdapter(new DiaryListAdapter(getContext(), HelperFactory.getHelper()
                    .getDiaryDAO().getAllDates()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up list view.
     */
    public void setUpListView() {
        getListView().setDivider(null);
        getListView().setDividerHeight(50);
        getListView().setDrawSelectorOnTop(true);
    }
}
