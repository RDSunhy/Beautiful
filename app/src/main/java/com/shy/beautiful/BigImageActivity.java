package com.shy.beautiful;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.shy.beautiful.base.BaseActivity;
import com.shy.beautiful.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BigImageActivity extends BaseActivity {

    @BindView(R.id.vp_image)
    ViewPager vpImage;
    private int position;
    private ArrayList<String> paths;
    private ArrayList<String> title;
    final String[] items = new String[] { "保存图片"};
    final Bitmap[] bitmap = new Bitmap[1];
    @Override
    public int initLayout() {
        return R.layout.activity_big_image;
    }
    PhotoView icon;

    @Override
    public void initView() {
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        paths = intent.getStringArrayListExtra("paths");
        title = intent.getStringArrayListExtra("title");

        vpImage.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return paths == null ? 0 : paths.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = LayoutInflater.from(BigImageActivity.this).inflate(R.layout.vp_style_big, null);
                icon = (PhotoView) view.findViewById(R.id.pv_image);
                TextView tvTitle = view.findViewById(R.id.tv_title);
                TextView tvIndex = view.findViewById(R.id.tv_index);
                icon.setBackgroundColor(getResources().getColor(R.color.Black));
                tvTitle.setText("图片的描述此处省略...");
                //tvTitle.setText(title.get(position));
                tvIndex.setText((position+1)+"/"+paths.size());
                final String url = "https://uploadbeta.com/_s/"+paths.get(position);

                Glide.with(BigImageActivity.this).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        bitmap[0] = resource;
                    }
                });
                Glide.with(BigImageActivity.this)
                        .load("https://uploadbeta.com/_s/"+paths.get(position))
                        .placeholder(R.mipmap.img_load)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(icon);
                container.addView(view);



                icon.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        //弹出的“保存图片”的Dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(BigImageActivity.this);
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        if(bitmap[0] !=null){
                                            saveImageToGallery(BigImageActivity.this, bitmap[0]);
                                        }else {
                                            ToastUtils.showShort(BigImageActivity.this,"bitmap为空");
                                        }
                                }
                            }
                        });
                        builder.show();
                        return true;
                    }
                });

                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        /*vpImage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //headerRightTv.setText(position + 1 + "/" + paths.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });*/
        vpImage.setCurrentItem(position, true);
    }

    @Override
    public void initData() {

    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "shy");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "shy_"+System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.e("111",e.getMessage());
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        String path = file.getAbsolutePath();
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), path, fileName, null);
        } catch (FileNotFoundException e) {
            Log.e("333",e.getMessage());
            e.printStackTrace();
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        ToastUtils.showShort(context,"保存成功");
    }

}
