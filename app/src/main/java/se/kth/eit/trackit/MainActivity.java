package se.kth.eit.trackit;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import se.kth.eit.trackit.persistence.HelperFactory;
import se.kth.eit.trackit.view.DiaryFragment;
import se.kth.eit.trackit.view.ResultsFragment;

public class MainActivity extends AppCompatActivity {

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

    /**
     * Floating action button.
     */
    private FloatingActionMenu fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HelperFactory.setHelper(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViewPager();
        setupFAButton();
    }

    /**
     * Sets up floating action button.
     */
    private void setupFAButton() {
        fab = (FloatingActionMenu) findViewById(R.id.fab);
        findViewById(R.id.add_meal_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddMealActivity();
            }
        });
    }

    private void startAddMealActivity() {
        Intent intent = new Intent(this, AddMealActivity.class);
        startActivity(intent);
    }

    /**
     * Sets up view pager (tabs view).
     */
    private void setupViewPager() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.addOnPageChangeListener(sectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HelperFactory.releaseHelper();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            if (position == 0) {
                return new DiaryFragment();
            }
            return new ResultsFragment();
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "DIARY";
                case 1:
                    return "RESULTS";
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
            if (position == 0) {
                fab.showMenuButton(true);
            } else {
               fab.hideMenuButton(true);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //Not used.
        }
    }
}
