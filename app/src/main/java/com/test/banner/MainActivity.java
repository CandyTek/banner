package com.test.banner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.test.banner.databinding.ActivityMainBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.test.banner.adapter.ImageAdapter;
import com.test.banner.adapter.ImageTitleAdapter;
import com.test.banner.adapter.ImageTitleNumAdapter;
import com.test.banner.adapter.MultipleTypesAdapter;
import com.test.banner.bean.DataBean;
import com.test.banner.ui.ConstraintLayoutBannerActivity;
import com.test.banner.ui.GalleryActivity;
import com.test.banner.ui.RecyclerViewBannerActivity;
import com.test.banner.ui.TVActivity;
import com.test.banner.ui.TouTiaoActivity;
import com.test.banner.ui.VideoActivity;
import com.test.banner.ui.Vp2FragmentRecyclerviewActivity;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.util.BannerUtils;
import com.youth.banner.util.LogUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

// import butterknife.BindView;
// import butterknife.ButterKnife;
// import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	// @BindView(R.id.banner)
	// Banner binding.banner;
	// @BindView(R.id.indicator)
	// RoundLinesIndicator binding.indicator;

	// @BindView(R.id.swipeRefresh)
	// SwipeRefreshLayout binding.swipeRefresh;

	private ActivityMainBinding binding;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		//自定义的图片适配器，也可以使用默认的BannerImageAdapter
		ImageAdapter adapter = new ImageAdapter(DataBean.getTestData2());

		binding.banner.setAdapter(adapter)
				//              .setCurrentItem(0,false)
				.addBannerLifecycleObserver(this)//添加生命周期观察者
				.setIndicator(new CircleIndicator(this))//设置指示器
				.setOnBannerListener((data,position) -> {
					Snackbar.make(binding.banner,((DataBean) data).title,Snackbar.LENGTH_SHORT).show();
					LogUtils.d("position：" + position);
				});

		//添加item之间切换时的间距(如果使用了画廊效果就不要添加间距了，因为内部已经添加过了)
		//        banner.addPageTransformer(new MarginPageTransformer( BannerUtils.dp2px(10)));

		//和下拉刷新配套使用
		binding.swipeRefresh.setOnRefreshListener(() -> {
			//模拟网络请求需要3秒，请求完成，设置setRefreshing 为false
			new Handler().postDelayed(() -> {
				binding.swipeRefresh.setRefreshing(false);

				//给banner重新设置数据
				binding.banner.setDatas(DataBean.getTestData());

				//对setDatas()方法不满意？你可以自己在adapter控制数据，参考setDatas()的实现修改
				//                adapter.updateData(DataBean.getTestData());
				//                banner.setCurrentItem(banner.getStartPosition(), false);
				//                banner.setIndicatorPageChange();

			},3000);
		});
		findViewById(R.id.style_image).setOnClickListener(this);
		findViewById( R.id.style_image_title).setOnClickListener(this);
		findViewById( R.id.style_image_title_num).setOnClickListener(this);
		findViewById( R.id.style_multiple).setOnClickListener(this);
		findViewById(R.id.style_net_image).setOnClickListener(this);
		findViewById( R.id.change_indicator).setOnClickListener(this);
		findViewById( R.id.rv_banner).setOnClickListener(this);
		findViewById( R.id.cl_banner).setOnClickListener(this);
		findViewById( R.id.vp_banner).setOnClickListener(this);
		findViewById( R.id.banner_video).setOnClickListener(this);
		findViewById( R.id.banner_tv).setOnClickListener(this);
		findViewById( R.id.gallery).setOnClickListener(this);
		findViewById( R.id.topLine).setOnClickListener(this);
	}

	// @OnClick({R.id.style_image, R.id.style_image_title, R.id.style_image_title_num, R.id.style_multiple,
	//         R.id.style_net_image, R.id.change_indicator, R.id.rv_banner, R.id.cl_banner, R.id.vp_banner,
	//         R.id.banner_video, R.id.banner_tv, R.id.gallery, R.id.topLine})

	@Override
	public void onClick(View view) {

		binding.indicator.setVisibility(View.GONE);
		int id = view.getId();
		if (id == R.id.style_image) {
			binding.swipeRefresh.setEnabled(true);
			binding.banner.setAdapter(new ImageAdapter(DataBean.getTestData()));
			binding.banner.setIndicator(new CircleIndicator(this));
			binding.banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
		} else if (id == R.id.style_image_title) {
			binding.swipeRefresh.setEnabled(true);
			binding.banner.setAdapter(new ImageTitleAdapter(DataBean.getTestData()));
			binding.banner.setIndicator(new CircleIndicator(this));
			binding.banner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
			binding.banner.setIndicatorMargins(new IndicatorConfig.Margins(0,0,
					BannerConfig.INDICATOR_MARGIN,BannerUtils.dp2px(12)));
		} else if (id == R.id.style_image_title_num) {
			binding.swipeRefresh.setEnabled(true);
			//这里是将数字指示器和title都放在adapter中的，如果不想这样你也可以直接设置自定义的数字指示器
			binding.banner.setAdapter(new ImageTitleNumAdapter(DataBean.getTestData()));
			binding.banner.removeIndicator();
		} else if (id == R.id.style_multiple) {
			binding.swipeRefresh.setEnabled(true);
			binding.banner.setIndicator(new CircleIndicator(this));
			binding.banner.setAdapter(new MultipleTypesAdapter(this,DataBean.getTestData()));
		} else if (id == R.id.style_net_image) {
			binding.swipeRefresh.setEnabled(false);
			//方法一：使用自定义图片适配器
			//                banner.setAdapter(new ImageNetAdapter(DataBean.getTestData3()));

			//方法二：使用自带的图片适配器
			binding.banner.setAdapter(new BannerImageAdapter<DataBean>(DataBean.getTestData3()) {
				@Override
				public void onBindView(BannerImageHolder holder,DataBean data,int position,int size) {
					//图片加载自己实现
					Glide.with(holder.itemView)
							.load(data.imageUrl)
							.thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))
							.apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
							.into(holder.imageView);
				}
			});
			binding.banner.setIndicator(new RoundLinesIndicator(this));
			binding.banner.setIndicatorSelectedWidth(BannerUtils.dp2px(15));
		} else if (id == R.id.change_indicator) {
			binding.indicator.setVisibility(View.VISIBLE);
			//在布局文件中使用指示器，这样更灵活
			binding.banner.setIndicator(binding.indicator,false);
			binding.banner.setIndicatorSelectedWidth(BannerUtils.dp2px(15));
		} else if (id == R.id.gallery) {
			startActivity(new Intent(this,GalleryActivity.class));
		} else if (id == R.id.rv_banner) {
			startActivity(new Intent(this,RecyclerViewBannerActivity.class));
		} else if (id == R.id.cl_banner) {
			startActivity(new Intent(this,ConstraintLayoutBannerActivity.class));
		} else if (id == R.id.vp_banner) {
			startActivity(new Intent(this,Vp2FragmentRecyclerviewActivity.class));
		} else if (id == R.id.banner_video) {
			startActivity(new Intent(this,VideoActivity.class));
		} else if (id == R.id.banner_tv) {
			startActivity(new Intent(this,TVActivity.class));
		} else if (id == R.id.topLine) {
			startActivity(new Intent(this,TouTiaoActivity.class));
		}
	}

}
