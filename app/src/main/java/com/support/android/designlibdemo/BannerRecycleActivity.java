/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.support.android.designlibdemo;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.support.android.designlibdemo.utils.PaletteUtil;
import com.support.android.designlibdemo.utils.StatusBarUtils;

public class BannerRecycleActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "cheese_name";
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_detail_layout);

        toolbar = findViewById(R.id.toolbar);
        StatusBarUtils.setTranslucentImageHeader(this, 0, toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        recyclerView = findViewById(R.id.recyclerview);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        toolbar.setTitle("");
        collapsingToolbarLayout.setTitle(" ");

        loadBackdrop();
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        HomeAdapter adapter = new HomeAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void loadBackdrop() {
        final ImageView imageView = findViewById(R.id.backdrop);
        Glide.with(this).load(Cheeses.getRandomCheeseUrl()).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                imageView.setImageBitmap(resource);
                colorChange(resource);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    /**
     * 界面颜色的更改
     */
    @SuppressLint("NewApi")
    private void colorChange(final Bitmap bitmap) {
        // Palette的部分
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                if (vibrant == null) {
                    vibrant = palette.getMutedSwatch();
                }

                if (vibrant == null) {
                    return;
                }
                changeUI(vibrant.getRgb());
            }
        });
    }

    private void changeUI(int color) {
        collapsingToolbarLayout.setContentScrimColor(PaletteUtil.colorBurn(color));
        collapsingToolbarLayout.setStatusBarScrimColor(PaletteUtil.colorBurn(color));
    }


    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(BannerRecycleActivity.this).inflate(R.layout.recycler_item_view, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if (position % 2 == 0) {
                holder.img.setImageResource(R.drawable.cheese_2);
            } else {
                holder.img.setImageResource(R.drawable.cheese_3);
            }
        }

        @Override
        public int getItemCount() {
            return 30;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView img;

            public MyViewHolder(View view) {
                super(view);
                img = view.findViewById(R.id.img);
            }
        }
    }


}
