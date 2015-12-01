package se.kth.eit.trackit.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import se.kth.eit.trackit.R;

/**
 * Results fragment.
 */
public class ResultsFragment extends Fragment {
    public ResultsFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ResultsFragment newInstance(int sectionNumber) {
        return new ResultsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }
}
