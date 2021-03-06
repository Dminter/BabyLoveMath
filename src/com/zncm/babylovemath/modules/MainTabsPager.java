package com.zncm.babylovemath.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.sherlock.navigationdrawer.compat.SherlockActionBarDrawerToggle;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.zncm.babylovemath.R;
import com.zncm.utils.L;

import java.util.ArrayList;

public class MainTabsPager extends SherlockFragmentActivity {

    private ViewPager mViewPager;
    private MyTabsAdapter mMyTabsAdapter;
    private ActionBar actionBar;
    private String titles[] = new String[]{"宝贝爱算术"};
    private SherlockActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView listView;
    private ActionBarHelper mActionBar;
    private static final String[] left_titles = new String[]{"测试历史", "设置", "口诀", "作者其他软件", "意见反馈", "退出"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UmengUpdateAgent.update(this);

        actionBar = getSupportActionBar();
        setContentView(R.layout.ac_tabs_pager);
        mViewPager = (ViewPager) this.findViewById(R.id.pager);
        MainF searchFragment = new MainF();
        mMyTabsAdapter = new MyTabsAdapter(this, mViewPager);
        mMyTabsAdapter.addTab(actionBar.newTab().setText("宝贝爱算术"), MainF.class, null, searchFragment);
        mViewPager.setOffscreenPageLimit(mMyTabsAdapter.getCount() - 1);
        mViewPager.setCurrentItem(0);
        actionBar.setTitle(titles[0]);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerListener(new DemoDrawerListener());
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, left_titles));
        listView.setOnItemClickListener(new DrawerItemClickListener());
        listView.setCacheColorHint(0);
        listView.setScrollingCacheEnabled(false);
        listView.setScrollContainer(false);
        listView.setFastScrollEnabled(true);
        listView.setSmoothScrollbarEnabled(true);
        mActionBar = createActionBarHelper();
        mActionBar.init();
        mDrawerToggle = new SherlockActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer_light,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
    }

    public class MyTabsAdapter extends FragmentStatePagerAdapter implements ActionBar.TabListener,
            ViewPager.OnPageChangeListener {

        private final ActionBar mActionBar;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
        private final ArrayList<Fragment> mFrag = new ArrayList<Fragment>();

        final class TabInfo {
            @SuppressWarnings("unused")
            private final Class<?> clss;
            @SuppressWarnings("unused")
            private final Bundle args;

            TabInfo(Class<?> _class, Bundle _args) {
                clss = _class;
                args = _args;
            }
        }

        public MyTabsAdapter(MainTabsPager activity, ViewPager pager) {
            super(activity.getSupportFragmentManager());
            mActionBar = activity.getSupportActionBar();
            mViewPager = pager;
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(Tab tab, Class<?> clss, Bundle args, Fragment frag) {
            TabInfo info = new TabInfo(clss, args);
            tab.setTag(info);
            tab.setTabListener(this);
            mTabs.add(info);
            mFrag.add(frag);
            mActionBar.addTab(tab);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFrag.get(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            actionBar.setTitle(titles[position]);
        }

        @Override
        public void onPageSelected(int position) {
            mActionBar.setSelectedNavigationItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            Object tag = tab.getTag();
            for (int i = 0; i < mTabs.size(); i++) {
                if (mTabs.get(i) == tag) {
                    mViewPager.setCurrentItem(i);
                }
            }
        }

        @Override
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        }

        @Override
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = null;
            switch (position) {
                case 0:
                    intent = new Intent(MainTabsPager.this, TestAc.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(MainTabsPager.this, SettingHomeActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(MainTabsPager.this, EquationAc.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(MainTabsPager.this, Recommend.class);
                    startActivity(intent);
                    break;
                case 4:
                    FeedbackAgent agent = new FeedbackAgent(MainTabsPager.this);
                    agent.startFeedbackActivity();
                    break;
                case 5:
                    finish();
                    break;
                default:
                    break;
            }
            mDrawerLayout.closeDrawer(listView);
        }
    }

    private class DemoDrawerListener implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerOpened(View drawerView) {
            mDrawerToggle.onDrawerOpened(drawerView);
            mActionBar.onDrawerOpened();
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            mDrawerToggle.onDrawerClosed(drawerView);
            mActionBar.onDrawerClosed();
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            mDrawerToggle.onDrawerStateChanged(newState);
        }
    }

    private ActionBarHelper createActionBarHelper() {
        return new ActionBarHelper();
    }

    private class ActionBarHelper {
        private final ActionBar mActionBar;
        private CharSequence mDrawerTitle;
        private CharSequence mTitle;

        private ActionBarHelper() {
            mActionBar = getSupportActionBar();
        }

        public void init() {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mTitle = mDrawerTitle = getTitle();
        }

        public void onDrawerClosed() {
            mActionBar.setTitle(mTitle);
        }

        public void onDrawerOpened() {
            mActionBar.setTitle(mDrawerTitle);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }

        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

}
