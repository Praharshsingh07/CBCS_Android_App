package com.amityaump.AmityCBCS.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amityaump.AmityCBCS.R;


public class FinishActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_finish);
    Intent intent = getIntent();
    TextView textView = findViewById(R.id.msg);
    textView.setText(intent.getStringExtra("msg"));
  }
}
