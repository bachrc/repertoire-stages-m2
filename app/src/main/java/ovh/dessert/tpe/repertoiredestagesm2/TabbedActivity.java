package ovh.dessert.tpe.repertoiredestagesm2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import ovh.dessert.tpe.repertoiredestagesm2.entities.Contact;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Localisation;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Stage;
import ovh.dessert.tpe.repertoiredestagesm2.fragments.ContactFragment;
import ovh.dessert.tpe.repertoiredestagesm2.fragments.InformationFragment;
import ovh.dessert.tpe.repertoiredestagesm2.fragments.StageFragment;

public class TabbedActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    protected String entCode;
    protected String entName;
    protected ArrayList<Localisation> locs;
    protected ArrayList<Contact> conts;
    protected ArrayList<Stage> stages;
    protected String site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        entCode = getIntent().getStringExtra("<Code>");
        try {
            site = StagesDAO.getInstance(this).getEntreprise(entCode).getSiteweb();
        } catch (Exception e) {
            site = "Aucun site";
        }
        try {
            entName = StagesDAO.getInstance(this).getEntreprise(entCode).getNom();
        } catch (Exception e) {
            entName = "Aucun nom";
        }
        try {
            locs = (ArrayList<Localisation>) StagesDAO.getInstance(this).getEntreprise(entCode).getLocalisations();
        } catch (Exception e) {
            locs = new ArrayList<>();
        }
        try {
            conts = (ArrayList<Contact>) StagesDAO.getInstance(this).getEntreprise(entCode).getContacts();
        } catch (Exception e) {
            conts = new ArrayList<>();
        }
        try {
            stages = (ArrayList<Stage>) StagesDAO.getInstance(this).getEntreprise(entCode).getStages();
        } catch (Exception e) {
            stages = new ArrayList<>();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return InformationFragment.newInstance(entName, site, locs);
                case 1: return ContactFragment.newInstance(conts);
                case 2: return StageFragment.newInstance(stages);
                default: return StageFragment.newInstance(null);
            }
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
                    return getString(R.string.information);
                case 1:
                    return getString(R.string.contact);
                case 2:
                    return getString(R.string.stage);
            }
            return null;
        }
    }
}
