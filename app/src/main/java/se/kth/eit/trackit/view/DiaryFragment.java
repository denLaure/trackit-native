package se.kth.eit.trackit.view;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Diary list fragment.
 */
public class DiaryFragment extends ListFragment {

    private List<Date> testData;

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
        setUpTestData();
        setListAdapter(new DiaryListAdapter(getContext(), testData));
    }

    /**
     * Sets up list view.
     */
    public void setUpListView() {
        getListView().setDivider(null);
        getListView().setDividerHeight(50);
        getListView().setDrawSelectorOnTop(true);
    }

    /**
     * Sets up test data. Will be removed with real data after design finished.
     */
    public void setUpTestData() {
        testData = new ArrayList<>();
        testData.add(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        testData.add(calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        testData.add(calendar.getTime());
    }
}
