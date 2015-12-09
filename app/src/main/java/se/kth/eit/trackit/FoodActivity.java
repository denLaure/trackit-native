package se.kth.eit.trackit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import se.kth.eit.trackit.view.ProductCategoriesFragment;

public class FoodActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter sectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager viewPager;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.addOnPageChangeListener(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == AddMealActivity.REQUEST_ADD_FOOD &&
                data != null && data.hasExtra(AddMealActivity.ADDED_PRODUCT_EXTRA)) {
            Intent intent = new Intent();
            intent.putExtra(AddMealActivity.ADDED_PRODUCT_EXTRA,
                    data.getStringExtra(AddMealActivity.ADDED_PRODUCT_EXTRA));
            setResult(RESULT_OK, intent);
            finish();
        } else if (resultCode == RESULT_CANCELED && requestCode ==
                AddMealActivity.REQUEST_ADD_FOOD && data != null &&
                data.hasExtra(AddMealActivity.FAILURE_MESSAGE_EXTRA)) {
            Snackbar.make(fab, data.getStringExtra(AddMealActivity.FAILURE_MESSAGE_EXTRA),
                    Snackbar.LENGTH_LONG).show();
        }
    }

    public void finishWithFailure() {
        Intent intent = new Intent();
        intent.putExtra(AddMealActivity.FAILURE_MESSAGE_EXTRA, "Failed to retrieve list of "
                + "product categories");
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_food, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format,
                    getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
            implements ViewPager.OnPageChangeListener {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 1) { return new ProductCategoriesFragment(); }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "MOST USED";
                case 1:
                    return "PRODUCTS";
                case 2:
                    return "RECIPES";
            }
            return null;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                int positionOffsetPixels) {
            //Not used.
        }

        @Override
        public void onPageSelected(int position) {
            if (position == 2) {
                fab.show();
            } else {
                fab.hide();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //Not used.
        }
    }
}
