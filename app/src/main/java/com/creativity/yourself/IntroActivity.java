package com.creativity.yourself;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class IntroActivity extends AppBaseCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        final SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, true);

        final FloatingActionButton fab = findViewById(R.id.buttonSkipDone);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() + 1 < sectionsPagerAdapter.getCount()) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                } else {
                    Intent login = new Intent(IntroActivity.this, MainActivity.class);
                    startActivity(login);
                    finish();
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_done));
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_800)));
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_right));
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setPageTransformer(false, new FadeTransformation());

    }

    @Override
    public void onNetworkStatusChange(boolean isOnline) {
        //Ricezione connessione assente
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
        } else {
            super.onBackPressed();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        private CardView cardColor;

        private TextView title;
        private TextView description;

        private ImageView image;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_slide, container, false);

            cardColor = rootView.findViewById(R.id.cardColor);
            title = rootView.findViewById(R.id.title);
            image = rootView.findViewById(R.id.image);
            description = rootView.findViewById(R.id.description);

            if (getArguments() != null) {
                switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                    case 0:
                        cardColor.setCardBackgroundColor(getResources().getColor(R.color.green_300));
                        title.setText(getResources().getString(R.string.titleSlide1));
                        image.setImageDrawable(getResources().getDrawable(R.drawable.chat, null));
                        description.setText(getResources().getString(R.string.descriptionSlide1));
                        break;
                    case 1:
                        cardColor.setCardBackgroundColor(getResources().getColor(R.color.light_blue_300));
                        title.setText(getResources().getString(R.string.titleSlide2));
                        image.setImageDrawable(getResources().getDrawable(R.drawable.earth, null));
                        description.setText(getResources().getString(R.string.descriptionSlide2));
                        break;
                    case 2:
                        cardColor.setCardBackgroundColor(getResources().getColor(R.color.orange_300));
                        title.setText(getResources().getString(R.string.titleSlide3));
                        image.setImageDrawable(getResources().getDrawable(R.drawable.music, null));
                        description.setText(getResources().getString(R.string.descriptionSlide3));
                        break;
                    case 3:
                        cardColor.setCardBackgroundColor(getResources().getColor(R.color.grey_300));
                        title.setText(getResources().getString(R.string.titleSlide5));
                        image.setImageDrawable(getResources().getDrawable(R.drawable.runner, null));
                        description.setText(getResources().getString(R.string.descriptionSlide5));
                        break;
                }
            }

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public class FadeTransformation implements ViewPager.PageTransformer {

        private final float MIN_ALPHA = 0.0f;
        private final float MIN_SCALE = 0.65f;

        @Override
        public void transformPage(@NonNull View page, float position) {
            ImageView image = page.findViewById(R.id.image);

            if (position < -1) {
                page.setAlpha(0);
            } else if (position <= 1) {
                page.setAlpha(Math.max(MIN_ALPHA, 1 - Math.abs(position)));
                image.setScaleX(Math.max(MIN_SCALE, 1 - Math.abs(position)));
                image.setScaleY(Math.max(MIN_SCALE, 1 - Math.abs(position)));
            } else {
                page.setAlpha(0);
            }
        }
    }
}