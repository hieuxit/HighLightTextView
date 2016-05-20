package com.simtig.highlighttextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.simtig.view.highlight.HighLightTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((HighLightTextView) findViewById(R.id.hltv1)).setHighlightText(R.string.text1);
    }
}
