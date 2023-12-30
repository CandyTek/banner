package com.test.banner.ui;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.test.banner.adapter.ImageAdapter;
import com.test.banner.bean.DataBean;
import com.test.banner.databinding.ActivityVp2FragmentRecyclerviewBinding;
import com.test.banner.util.TabLayoutMediator;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
// import butterknife.BindView;
// import butterknife.ButterKnife;

public class Vp2FragmentRecyclerviewActivity extends AppCompatActivity {

    // @BindView(R.id.vp2)
    // ViewPager2 binding.vp2;
    // @BindView(R.id.tab_layout)
    // TabLayout binding.tabLayout;
    // @BindView(R.id.banner)
    
    // Banner binding.banner;

    ActivityVp2FragmentRecyclerviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVp2FragmentRecyclerviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // ButterKnife.bind(this);

        binding.vp2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 0) {
                    return BannerListFragment.newInstance(position);
                } else if (position == 1) {
                    return BlankFragment.newInstance();
                } else {
                    return BannerFragment.newInstance();
                }
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });

        new TabLayoutMediator(binding.tabLayout, binding.vp2, (tab,position) -> {
            tab.setText("页面" + position);
        }).attach();


        binding.banner.addBannerLifecycleObserver(this)
               .setAdapter(new ImageAdapter(DataBean.getTestData()))
               .setIntercept(false)
               .setIndicator(new CircleIndicator(this));
    }
}
