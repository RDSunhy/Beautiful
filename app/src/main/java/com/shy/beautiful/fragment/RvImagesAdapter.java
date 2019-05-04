package com.shy.beautiful.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.shy.beautiful.BigImageActivity;
import com.shy.beautiful.R;
import com.shy.beautiful.bean.ImagesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shy on 2019/3/30.
 */

public class RvImagesAdapter extends RecyclerView.Adapter<RvImagesAdapter.ViewHolder> {

    Context mContext;
    List<ImagesBean> mList;
    ArrayList<String> listUrl = new ArrayList<>();
    ArrayList<String> listTitle = new ArrayList<>();
    public RvImagesAdapter(Context context,List<ImagesBean> list){
        mContext = context;
        mList = list;
        for(int i=0;i<list.size();i++){
            listUrl.add(mList.get(i).getUrl());
            listTitle.add(mList.get(i).getPicgroup());
        }
    }

    @Override
    public RvImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_style_images,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RvImagesAdapter.ViewHolder viewHolder, final int position) {
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels/2;
        int height = (int) (width*(Float.parseFloat(mList.get(position).getHeight())/Float.parseFloat(mList.get(position).getWidth())));
        ViewGroup.LayoutParams layoutParams = viewHolder.ivLeft.getLayoutParams();
        layoutParams.height = height;
        viewHolder.itemView.setLayoutParams(layoutParams);

        String Url = "https://uploadbeta.com/_s/"+mList.get(position).getUrl();
        //Log.e("ImageUrl",Url);

        Glide.with(mContext)
                .load(Url)
                .override(Integer.parseInt(mList.get(position).getWidth()), Integer.parseInt(mList.get(position).getHeight()))
                .placeholder(R.mipmap.img_load)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.ivLeft);
        viewHolder.tvInfo.setText(mList.get(position).getPicgroup());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(mContext, BigImageActivity.class);
                i.putStringArrayListExtra("paths",listUrl);
                i.putStringArrayListExtra("title",listTitle);
                i.putExtra("position",position);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivLeft;
        TextView tvInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            ivLeft = itemView.findViewById(R.id.iv_left);
            tvInfo = itemView.findViewById(R.id.tv_info);
        }
    }
}
