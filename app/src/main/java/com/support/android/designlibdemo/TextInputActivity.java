package com.support.android.designlibdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by gary on 17-7-14.
 */

public class TextInputActivity extends AppCompatActivity {
    private TextInputLayout psdInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textinput);
        psdInput = findViewById(R.id.psd_input_layout);
        psdInput.setCounterEnabled(true);
        psdInput.setCounterMaxLength(10);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                psdInput.setError("密码错误");
            }
        });
    }
}
