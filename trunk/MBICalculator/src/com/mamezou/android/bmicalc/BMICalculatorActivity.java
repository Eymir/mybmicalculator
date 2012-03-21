package com.mamezou.android.bmicalc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Administrator
 */
public class BMICalculatorActivity extends Activity {

    private EditText textHeight;
    private EditText textWeight;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Log.v("EXAMPLE", "onCreate was called.");
        setContentView(R.layout.main);
        textHeight = (EditText) findViewById(R.id.text_height);
        textWeight = (EditText) findViewById(R.id.text_weight);
        Button button = (Button) findViewById(R.id.button_calculate);
        Button buttonShowNextActivity = (Button) findViewById(R.id.button_show_next_activity);

        //dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.label_bmi_description);
        builder.setPositiveButton(R.string.button_close_dialog,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_OK);
                    }
                });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textHeight = (EditText) findViewById(R.id.text_height);
                EditText textWeight = (EditText) findViewById(R.id.text_weight);
                int height = Integer.parseInt(textHeight.getText().toString());
                int weight = Integer.parseInt(textWeight.getText().toString());
                int bmi = 10000 * weight / height / height;
                builder.setMessage(String.valueOf(bmi));
                builder.create();
                builder.show();
            }
        });

        buttonShowNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BMICalculatorActivity.this,
                        ResultActivity.class);
                intent.putExtra("HEIGHT", Integer.parseInt(textHeight.getText()
                        .toString()));
                intent.putExtra("WEIGHT", Integer.parseInt(textWeight.getText()
                        .toString()));
                startActivityForResult(intent, 0);
            }
        });
    }
}