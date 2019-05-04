package com.shy.beautiful.fragment.category;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shy.beautiful.R;
import com.shy.beautiful.base.Constant;
import com.shy.beautiful.bean.ImagesBean;
import com.shy.beautiful.fragment.RvImagesAdapter;
import com.shy.beautiful.http.RetrofitHelper;
import com.shy.beautiful.utils.SpUtils;
import com.shy.beautiful.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Shy on 2019/3/30.
 */

public class EuropeFragment extends Fragment {

    @BindView(R.id.rv_images)
    RecyclerView rvImages;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    Unbinder unbinder;

    public static String Category = "欧美";
    public int index = 0;
    SpUtils spUtils;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_europe, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        index = 1;
        spUtils = new SpUtils(getActivity(), Constant.FILENAME);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Map<String, String> map = new HashMap<>();
                //map.put("num","10");
                map.put("key", Category);
                map.put("start", "" + index);
                map.put("offset", "50");
                index+=50;
                RetrofitHelper retrofitHelper = new RetrofitHelper(getActivity());
                retrofitHelper.getImages(map)
                        .subscribeOn(Schedulers.io())//IO线程
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<ImagesBean>>() {
                            @Override
                            public void onCompleted() {
                                swipeRefresh.setRefreshing(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                //请求失败
                                ToastUtils.showShort(getActivity(), "请求过于频繁,请稍后再试！");
                                Log.e("请求失败:", "" + e.getMessage());
                                swipeRefresh.setRefreshing(false);
                            }

                            @Override
                            public void onNext(List<ImagesBean> bean) {
                                //请求成功
                                Log.e("请求的结果", bean.toString());
                                //存入缓存到本地
                                Gson gson2 = new Gson();
                                String str = gson2.toJson(bean);
                                spUtils.put(Constant.EUROPE, str);
                                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                                rvImages.setLayoutManager(layoutManager);
                                RvImagesAdapter adapter = new RvImagesAdapter(getActivity(), bean);
                                rvImages.setAdapter(adapter);
                                swipeRefresh.setRefreshing(false);
                            }
                        });
            }
        });
    }

    private void initData() {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
        if (spUtils.getSharedPreference(Constant.EUROPE, ImagesBean.class) == null) {
            Map<String, String> map = new HashMap<>();
            //map.put("num","10");
            map.put("key", Category);
            map.put("start", "" + index);
            map.put("offset", "50");
            index+=50;
            RetrofitHelper retrofitHelper = new RetrofitHelper(getActivity());
            retrofitHelper.getImages(map)
                    .subscribeOn(Schedulers.io())//IO线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<ImagesBean>>() {
                        @Override
                        public void onCompleted() {
                            dialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            //请求失败
                            ToastUtils.showShort(getActivity(), "请求过于频繁,请稍后再试！");
                            Log.e("请求失败:", "" + e.getMessage());
                            dialog.dismiss();
                        }

                        @Override
                        public void onNext(List<ImagesBean> bean) {
                            //请求成功
                            Log.e("请求的结果", bean.toString());
                            //存入缓存到本地
                            Gson gson2 = new Gson();
                            String str = gson2.toJson(bean);
                            spUtils.put(Constant.EUROPE, str);
                            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                            rvImages.setLayoutManager(layoutManager);
                            RvImagesAdapter adapter = new RvImagesAdapter(getActivity(), bean);
                            rvImages.setAdapter(adapter);
                            dialog.dismiss();
                        }
                    });
        } else {
            String jsonList = (String) spUtils.getSharedPreference(Constant.EUROPE, String.class);
            Gson gson1 = new Gson();
            List<ImagesBean> list = gson1.fromJson(jsonList, new TypeToken<List<ImagesBean>>() {
            }.getType());
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            rvImages.setLayoutManager(layoutManager);
            RvImagesAdapter adapter = new RvImagesAdapter(getActivity(), list);
            rvImages.setAdapter(adapter);
            dialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
