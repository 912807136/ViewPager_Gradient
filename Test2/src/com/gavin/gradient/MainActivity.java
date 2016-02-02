package com.gavin.gradient;

import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity {
	private ViewPager viewPager;
	int[] colors = { Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW };
	private int pagerWidth;
	private int pagerHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewPager1);
		PagerAdapter adapter = new MyPagerAdapter();
		viewPager.setAdapter(adapter);
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				if (pagerWidth == 0) {
					return;
				}
				viewPager.setBackground(getDrawable(position, positionOffset));
			}

			@Override
			public void onPageScrollStateChanged(int position) {
			}
		});
		viewPager.post(new Runnable() {

			@Override
			public void run() {
				pagerWidth = viewPager.getWidth();
				pagerHeight = viewPager.getHeight();
			}
		});
		viewPager.setBackgroundColor(colors[0]);
	}

	protected Drawable getDrawable(int position, float positionOffset) {
		Bitmap bitmap = Bitmap.createBitmap(pagerWidth, pagerHeight, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		
		int color1 = colors[(int) Math.ceil(position + positionOffset)];
		paint.setColor(color1);
		paint.setAlpha((int) (255 * positionOffset));
		canvas.drawPaint(paint);

		paint.setColor(colors[position]);
		paint.setAlpha((int) (255 * (1 - positionOffset)));
		canvas.drawPaint(paint);
		
		BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
		return drawable;
	}

	private class MyPagerAdapter extends PagerAdapter {
		private List<View> list = new ArrayList<View>();

		public MyPagerAdapter() {
			int count = getCount();
			for (int i = 0; i < count; i++) {
				TextView view = new TextView(MainActivity.this);
				view.setText("text:" + i);
				list.add(view);
			}
		}

		@Override
		public int getCount() {

			return 4;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View child = list.get(position);
			container.addView(child);
			return child;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View child = list.get(position);
			container.removeView(child);
		}

	}

}
