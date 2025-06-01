package com.amityaump.AmityCBCS.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.app.ActivityOptionsCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amityaump.AmityCBCS.DataModels.StudentDataModel;
import com.amityaump.AmityCBCS.R;
import com.amityaump.AmityCBCS.Utils.Endpoints;
import com.amityaump.AmityCBCS.Utils.VolleySingleton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

  public static final String ENROLLMENT_NO = "enrollment_no";
  public static final String DEPT_NAME = "dept_name";
  public static final String STUDENT_NAME = "std_name";
  public static final String STUDENT_BATCH = "std_batch";
  public static final String STUDENT_COURSE = "std_course";

  private TextView messageText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    messageText = findViewById(R.id.message);
    ImageView imageView = findViewById(R.id.logo);
    Animation aniFade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
    imageView.startAnimation(aniFade);
    CardView cardView = findViewById(R.id.card);
    cardView.startAnimation(aniFade);

    final EditText passwordEt = findViewById(R.id.password);
    final EditText enrollmentNo = findViewById(R.id.enrollment_no);
//    passwordEt.setText("asco@123");
//    enrollmentNo.setText("A60379820022");
    enrollmentNo.setOnFocusChangeListener(new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View view, boolean b) {
        if (!b) {
          hideKeyboard(view);
        }
      }
    });

    enrollmentNo.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        if (editable.toString().length() > 14) {
          enrollmentNo.setText(editable.toString().substring(0, editable.toString().length() - 1));
        }
      }
    });

    Button button = findViewById(R.id.submit_button);
    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        hideKeyboard(view);
        messageText.setText("For first year students");
        messageText.setTextColor(Color.GRAY);
        if (enrollmentNo.getText().toString().length() > 0
            && passwordEt.getText().toString().length() > 0) {
          isValidEnrollment(enrollmentNo.getText().toString(), passwordEt.getText().toString());
        }
      }
    });

  }


  private void isValidEnrollment(final String enrollment, final String password) {
    Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show();
    //validate here
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.login_url,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            System.out.println(ServerResponse);
            if (!ServerResponse.equals( "null")) {
              Log.d("VOLLEY", "Recieved JSON\n"+ServerResponse);
              Gson gson = new Gson();
              StudentDataModel dataModel = gson.fromJson(ServerResponse, StudentDataModel.class);

              if (!dataModel.getMinorTrackSubject().equals("")) {
                messageText.setText(String
                    .format("You have already registered for %s", dataModel.getMinorTrackSubject()));
                messageText.setTextColor(Color.RED);
                return;
              }

              Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
              intent.putExtra(ENROLLMENT_NO, enrollment);
              intent.putExtra(DEPT_NAME, dataModel.getSchoolName());
              intent.putExtra(STUDENT_NAME, dataModel.getStdName());
              intent.putExtra(STUDENT_BATCH, dataModel.getBatch());
              intent.putExtra(STUDENT_COURSE, dataModel.getCourseName());
              intent.putExtra("branch_name", dataModel.getBranchName());
              ImageView ivProfile = findViewById(R.id.logo);
              ActivityOptionsCompat options = ActivityOptionsCompat.
                  makeSceneTransitionAnimation(MainActivity.this, (View) ivProfile, "profile");
              startActivity(intent, options.toBundle());
              startActivity(intent);
              MainActivity.this.finish();
            }else{
              Toast.makeText(MainActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT)
                .show();
            Log.d("VOLLEY", volleyError.toString());
          }
        }) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("enroll", enrollment);
        params.put("pass", password);
        return params;
      }

    };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }


  public void hideKeyboard(View view) {
    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
        Activity.INPUT_METHOD_SERVICE);
    assert inputMethodManager != null;
    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }


}
