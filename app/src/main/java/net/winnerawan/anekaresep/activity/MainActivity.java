package net.winnerawan.anekaresep.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import net.winnerawan.anekaresep.R;
import net.winnerawan.anekaresep.fragment.FragmentAyam;
import net.winnerawan.anekaresep.fragment.FragmentKueBasah;
import net.winnerawan.anekaresep.fragment.FragmentKueKering;
import net.winnerawan.anekaresep.fragment.FragmentMasakan;
import net.winnerawan.anekaresep.fragment.FragmentPuding;
import net.winnerawan.anekaresep.fragment.FragmentTips;
import net.winnerawan.anekaresep.helper.ExpandableHeightListView;
import net.winnerawan.anekaresep.model.Ayam;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView title;
    ExpandableHeightListView listview;
    private DrawerLayout mDrawerLayout;
    InterstitialAd interstitialAd;
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.menuToolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator =
                    VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(),R.color.white,getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setTitle("");
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        adView = (AdView) findViewById(R.id.adHome);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);

                        switch (menuItem.getItemId()) {
                            case R.id.action_about :
                                Intent i = new Intent(MainActivity.this, AboutActivity.class);
                                startActivity(i);
                                return true;

                            case R.id.action_disclaimer :
                                Intent ii = new Intent(MainActivity.this, DisclaimerActivity.class);
                                startActivity(ii);
                                return true;

                            case R.id.action_feedback :
                                /*Activity activity = MainActivity.this;
                                ShareCompat.IntentBuilder.from(activity)
                                        .setType("message/rfc822")
                                        .addEmailTo("next.wonderwall@gmail.com")
                                        .setSubject("Message from Aneka Resep user")
                                        .startChooser();*/
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                                emailIntent.setData(Uri.parse("mailto: next.wonderwall@gmail.com"));
                                startActivity(Intent.createChooser(emailIntent, "Send feedback"));
                                return true;

                        }
                        // TODO: handle navigation
                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;

                    }
                });

        //ADS

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
        if (id == R.id.action_disclaimer) {
            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 6;

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: // Fragment # 0 - This will show FirstFragment
                    return getResources().getString(R.string.title_masakan);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return getResources().getString(R.string.title_ayam);
                case 2:
                    return getResources().getString(R.string.title_kue_basah);
                case 3:
                    return getResources().getString(R.string.title_kue_kering);
                case 4:
                    return getResources().getString(R.string.title_puding);
                case 5:
                    return getResources().getString(R.string.title_tips);
                default:
                    return getResources().getString(R.string.title_masakan);
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return new FragmentMasakan();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return new FragmentAyam();
                case 2:
                    return new FragmentKueBasah();
                case 3:
                    return new FragmentKueKering();
                case 4:
                    return new FragmentPuding();
                case 5:
                    return new FragmentTips();
                default:
                    return new FragmentMasakan();
            }
        }
    }
}
