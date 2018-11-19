package com.support.android.designlibdemo;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * class description
 *
 * @author garry
 * @version 1.0.0
 * @date 2018-11-19 13:16
 */
public class BottomSheetsActivity extends AppCompatActivity implements View.OnClickListener {
    BottomSheetBehavior behavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheets_layout);

        findViewById(R.id.BottomSheetDialog).setOnClickListener(this);
        findViewById(R.id.fab).setOnClickListener(this);

        initBottomSheet();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BottomSheetDialog:
                initBottomSheetDialog();
                break;
            case R.id.fab:
                //控制显示隐藏
                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
        }
    }

    private void initBottomSheet() {
        View bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //这里是bottomSheet状态的改变
                //Toast.makeText(BottomSheetsActivity.this, "BottomSheet State " + newState, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
            }
        });
    }

    private void initBottomSheetDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
        dialog.setContentView(view);
        dialog.getWindow().setDimAmount(0f);//去除遮罩
        dialog.show();
    }
}
