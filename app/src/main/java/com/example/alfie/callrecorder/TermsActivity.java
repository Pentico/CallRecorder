package com.example.alfie.callrecorder;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Alfie on 2016/04/18.
 */
public class TermsActivity extends Activity {

    public TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_layout);

        mTextView = (TextView) findViewById(R.id.txtTerms2);

        try {
            mTextView.setText(MainActivity.getDataFromRawFiles(R.raw.terms));
        } catch (IOException e) {

        }
    }
}
