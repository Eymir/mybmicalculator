package com.mamezou.android.bmicalc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Administrator
 */
public class BMICalculatorActivity extends Activity {

    private EditText textHeight;

    private EditText textWeight;

    private TextView labelPreviousHeight;

    private TextView labelPreviousWeight;

    public static final String ACTION_CALCULATE_BMI = "com.mamezou.android.intent.action.CALCULLATE_BMI";

    public static final String ACTION_CALCULATE_BMI_WITH_URI = "com.mamezou.android.intent.action.CALCULLATE_BMI_WITH_URI";

    public static final String CATEGORY_MALE = "com.mamezou.android.intent.category.bmi.MALE";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Log.v("EXAMPLE", "onCreate was called.");
        setContentView(R.layout.main);
        textHeight = (EditText) findViewById(R.id.text_height);
        textWeight = (EditText) findViewById(R.id.text_weight);
        labelPreviousHeight = (TextView) findViewById(R.id.label_previous_height);
        labelPreviousWeight = (TextView) findViewById(R.id.label_previous_weight);

        Button button = (Button) findViewById(R.id.button_calculate);
        Button buttonShowNextActivity = (Button) findViewById(R.id.button_show_next_activity);

        Button buttonBroadcastWithAction = (Button)findViewById(R.id.button_broadcast_with_action);
        Button buttonBroadcastWithCategory = (Button)findViewById(R.id.button_broadcast_with_category);
        Button buttonBroadcastWithUri = (Button)findViewById(R.id.button_broadcast_with_uri);

        // dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.label_bmi_description);
        builder.setPositiveButton(R.string.button_close_dialog,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_OK);
                    }
                });

        // ボタン-計算
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

        // ボタン次Activity
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


        buttonBroadcastWithAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //アクションのみ指定（男性と女性両方のReceiverが応答）
                Intent intent = new Intent(ACTION_CALCULATE_BMI);
                intent.putExtra("HEIGHT", Integer.parseInt(textHeight.getText().toString()));
                intent.putExtra("WEIGHT", Integer.parseInt(textWeight.getText().toString()));
                sendBroadcast(intent);
            }
        });

        buttonBroadcastWithCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //アクションとカテゴリを指定（男性のReceiverが応答）
                Intent intent = new Intent(ACTION_CALCULATE_BMI);
                intent.addCategory(CATEGORY_MALE);
                intent.putExtra("HEIGHT", Integer.parseInt(textHeight.getText().toString()));
                intent.putExtra("WEIGHT", Integer.parseInt(textWeight.getText().toString()));
                sendBroadcast(intent);
            }
        });

        buttonBroadcastWithUri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //URIを指定（女性のReceiverのみが応答）
                Intent intent = new Intent(ACTION_CALCULATE_BMI_WITH_URI);
                Uri uri = Uri.parse("bmi:///female");
                intent.setData(uri);
                intent.putExtra("HEIGHT", Integer.parseInt(textHeight.getText().toString()));
                intent.putExtra("WEIGHT", Integer.parseInt(textWeight.getText().toString()));
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            SharedPreferences prefrences = getSharedPreferences(
                    "PREVIOUS_RESULT", MODE_PRIVATE);
            labelPreviousHeight.setText(String.valueOf(prefrences.getInt(
                    "PREVIOUS_HEIGHT", 0)));
            labelPreviousWeight.setText(String.valueOf(prefrences.getInt(
                    "PREVIOUS_WEIGHT", 0)));
        }
    }
}