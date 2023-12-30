package com.test.banner.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.banner.R;
import com.test.banner.databinding.ActivityGalleryBinding;
import com.test.banner.adapter.ImageAdapter;
import com.test.banner.adapter.ImageNetAdapter;
import com.test.banner.bean.DataBean;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.DrawableIndicator;
import com.youth.banner.transformer.AlphaPageTransformer;

import androidx.appcompat.app.AppCompatActivity;
// import butterknife.BindView;
// import butterknife.ButterKnife;
// import butterknife.OnClick;

public class GalleryActivity extends AppCompatActivity  {

    // @BindView(R.id.banner1)
    // Banner binding.banner1;
    // @BindView(R.id.banner2)
    // Banner binding.banner2;
    // @BindView(R.id.indicator)
    
    // DrawableIndicator binding.indicator;
    ActivityGalleryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGalleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /**
         * 画廊效果
         */
        binding.banner1.setAdapter(new ImageAdapter(DataBean.getTestData2()));
        binding.banner1.setIndicator(new CircleIndicator(this));
        //添加画廊效果
        binding.banner1.setBannerGalleryEffect(50, 10);
        //(可以和其他PageTransformer组合使用，比如AlphaPageTransformer，注意但和其他带有缩放的PageTransformer会显示冲突)
        //添加透明效果(画廊配合透明效果更棒)
        //mBanner1.addPageTransformer(new AlphaPageTransformer());


        /**
         * 魅族效果
         */
        binding.banner2.setAdapter(new ImageAdapter(DataBean.getTestData()));
        binding.banner2.setIndicator(binding.indicator,false);
        //添加魅族效果
        binding.banner2.setBannerGalleryMZ(20);



    }


}
