package com.mamezou.android.bmicalc;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author ren_k
 */
public class ResultActivity extends Activity {

    private int height = 0;

    private int weight = 0;

    private int bmi = 0;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.result);
        TextView bmiValue = (TextView) findViewById(R.id.label_bmivalue);
        TextView status = (TextView) findViewById(R.id.label_status);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            height = extras.getInt("HEIGHT");
            weight = extras.getInt("WEIGHT");
            bmi = 10000 * weight / height / height;
            bmiValue.setText(String.valueOf(bmi));

            if (bmi > 24) {
                status.setText(R.string.label_too_fat);
            } else if (bmi < 18) {
                status.setText(R.string.label_too_slim);
            } else {
                status.setText(R.string.label_standard);
            }
        }

        Button saveResult = (Button) findViewById(R.id.button_save_result);
        Button close = (Button) findViewById(R.id.button_close_current_activity);
        saveResult.setOnClickListener(saveResultListener);
        close.setOnClickListener(closeListener);

        Button notify = (Button) findViewById(R.id.button_notify_result);
        notify.setOnClickListener(notifyListener);
    }

    private View.OnClickListener saveResultListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences preferences = getSharedPreferences(
                    "PREVIOUS_RESULT", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("PREVIOUS_HEIGHT", height);
            editor.putInt("PREVIOUS_WEIGHT", weight);
            editor.commit();
            setResult(RESULT_OK);
            finish();
        }
    };

    private View.OnClickListener closeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setResult(RESULT_CANCELED);
            finish();
        }
    };

    private View.OnClickListener notifyListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("bmi:///result?measurements=" + height + ","
                    + weight + "," + bmi);
            intent.setData(uri);
            startActivity(intent);
        }
    };
}
