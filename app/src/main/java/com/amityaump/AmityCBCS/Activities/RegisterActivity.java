package com.amityaump.AmityCBCS.Activities;

import static com.amityaump.AmityCBCS.Activities.MainActivity.DEPT_NAME;
import static com.amityaump.AmityCBCS.Activities.MainActivity.ENROLLMENT_NO;
import static com.amityaump.AmityCBCS.Activities.MainActivity.STUDENT_BATCH;
import static com.amityaump.AmityCBCS.Activities.MainActivity.STUDENT_COURSE;
import static com.amityaump.AmityCBCS.Activities.MainActivity.STUDENT_NAME;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amityaump.AmityCBCS.Fragments.CourseSyllabusDialog;
import com.amityaump.AmityCBCS.R;
import com.amityaump.AmityCBCS.Utils.Endpoints;
import com.amityaump.AmityCBCS.Utils.VolleySingleton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jrizani.jrspinner.JRSpinner;
import jrizani.jrspinner.JRSpinner.OnItemClickListener;


public class RegisterActivity extends AppCompatActivity {

  String[] cse = {"Artificial Intelligence and Machine Learning", "Data Science", "Internet of Things"};
  String[] bca = {"Cloud Computing", "Data Analytics", "Network Security", "Web Technology","IT Skills for Professionals"};
  String[] ece = {"Artificial Intelligence", "Consumer Electronics", "Embedded Systems",
      "Instrumentation Engineering", "Signal Processing", "Wireless Communication"};
  String[] me = {"Mechanical Engineering", "Renewable Energy", "Robotics"};
  String[] civil = {"Construction Engineering"};
  String[] general = {"Nanotechnology"};
  String[] chemistry = {"Chemicals In Daily Life: Nutrition & Adulteration"};

  String[] aibas = {"Psychology"};
  String[] abs = {"Management Mantra", "Managing Human Resource",
      "Marketer’s Tool Kit", "Wealth Management"};
  String[] asco = {"Animation", "Audio - Visual Communication", "Media Management", "Print Media"};
  String[] aib = {"Biotechnology Management", "Conservation Biology", "Disaster Management",
      "Environmental Law", "Environmental Management", "Industrial Environmental, Health & Safety"};
  String[] als = {"Human Rights", "Intellectual Property Rights"};
  String[] asl = {"English Literature"};
  String[] asft = {"Fashion Management"};
  String[] aiss = {"Gandhian Studies"};

  List<String> dept = new ArrayList<>(
      Arrays.asList("BCA", "CIVIL", "ME", "ECE", "AIBAS", "ABS", "ASCO", "AIB", "ALS", "ASL",
          "ASFT","AISS"));
  Map<String, String[]> courses;

  private String enrollment_no;
  private String dept_name;
  private String std_name;
  private String std_course;
  private String std_batch;

  private String cbcs1;
  private String cbcs2;
  private String cbcs3;

  Map<String, Integer> syllabus;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    syllabus = new HashMap<>();
    setSyllabus();
    Intent intent = getIntent();
    enrollment_no = intent.getStringExtra(ENROLLMENT_NO);
    dept_name = intent.getStringExtra(DEPT_NAME);
    std_name = intent.getStringExtra(STUDENT_NAME);
    std_course = intent.getStringExtra(STUDENT_COURSE);
    std_batch = intent.getStringExtra(STUDENT_BATCH);
    String std_branch = intent.getStringExtra("branch_name");
    final JRSpinner cbcsSpinner1 = findViewById(R.id.cbcs_spinner_1);
    courses = new HashMap<>();
    courses.put("AIBAS", aibas);
    courses.put("ABS", abs);
    courses.put("ASCO", asco);
    courses.put("AIB", aib);
    courses.put("ALS", als);
    courses.put("ASL", asl);
    courses.put("ASFT", asft);
    courses.put("BCA", bca);
    courses.put("CIVIL", civil);
    courses.put("ME", me);
    courses.put("GENERAL",general);
    courses.put("CHEMISTRY", chemistry);
    courses.put("ECE", ece);
    courses.put("AISS",aiss);
    TextView deptName = findViewById(R.id.dept_name);
    deptName.setText(String.format("Dept. Name: %s", dept_name));
    TextView enrollmentNumber = findViewById(R.id.enrollment_no);
    enrollmentNumber.setText(String.format("Enrollment No.: %s", enrollment_no));
    TextView studentName = findViewById(R.id.student_name);
    studentName.setText(String.format("Name: %s", std_name));
    TextView branchName = findViewById(R.id.branch_name);
    branchName.setText(String.format("Branch: %s", std_branch));

    final List<String> course = new ArrayList<>();
    if(std_course.equals("CSE")){
      course.addAll(Arrays.asList(cse));

    }else {
      for (String i : dept) {
        if (!i.equals(std_course)) {
          course.addAll(Arrays.asList(courses.get(i)));
        }
      }
      course.addAll(Arrays.asList(general));
      course.addAll(Arrays.asList(chemistry));
    }

    cbcsSpinner1.setItems(course.toArray(new String[0]));

    final JRSpinner cbcsSpinner2 = findViewById(R.id.cbcs_spinner_2);

    final List<String> courses2 = new ArrayList<>(course);
    cbcsSpinner1.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(int position) {
        cbcs1 = course.get(position);
        cbcs2 = null;
        courses2.clear();
        courses2.addAll(course);
        courses2.remove(cbcs1);
        cbcsSpinner2.clear();
        cbcsSpinner2.setItems(courses2.toArray(new String[0]));

        CourseSyllabusDialog dialog = new CourseSyllabusDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("DATA", syllabus.get(cbcs1));
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), null);

      }
    });

    final JRSpinner cbcsSpinner3 = findViewById(R.id.cbcs_spinner_3);

    final List<String> courses3 = new ArrayList<>(courses2);
    cbcsSpinner2.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(int position) {
        cbcs2 = courses2.get(position);
        cbcs3 = null;
        courses3.clear();
        courses3.addAll(course);
        courses3.remove(cbcs2);
        courses3.remove(cbcs1);
        cbcsSpinner3.clear();
        cbcsSpinner3.setItems(courses3.toArray(new String[0]));

        CourseSyllabusDialog dialog = new CourseSyllabusDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("DATA", syllabus.get(cbcs2));
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), null);

      }
    });

    cbcsSpinner3.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(int position) {
        cbcs3 = courses3.get(position);
        CourseSyllabusDialog dialog = new CourseSyllabusDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("DATA", syllabus.get(cbcs3));
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), null);
      }
    });

    Button submitButton = findViewById(R.id.submit_button);
    submitButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (cbcs1 == null) {
          showError("Select CBCS choice 1");
        } else if (cbcs2 == null) {
          showError("Select CBCS choice 2");
        } else if (cbcs3 == null) {
          showError("Select CBCS choice 3");
        } else {
          register(enrollment_no, cbcs1, cbcs2, cbcs3);
        }
      }
    });


  }


  private void register(final String enrollment_no, final String cbcs1, final String cbcs2,
      final String cbcs3) {
    Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show();
    //validate here
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.register_url,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            Toast.makeText(RegisterActivity.this, ServerResponse, Toast.LENGTH_SHORT).show();
            if (!ServerResponse.equals("Registration Failed")) {
              Intent intent = new Intent(RegisterActivity.this, FinishActivity.class);
              intent.putExtra("msg", ServerResponse);
              startActivity(intent);
              RegisterActivity.this.finish();
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
        params.put("enroll", enrollment_no);
        params.put("cbcs1", cbcs1);
        params.put("cbcs2", cbcs2);
        params.put("cbcs3", cbcs3);
        return params;
      }

    };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }


  private void showError(String msg) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  }


  private void setSyllabus(){
    syllabus.put("Cloud Computing", R.string.Cloud_Computing);
    syllabus.put("Data Analytics", R.string.Data_Analytics);
    syllabus.put("Network Security", R.string.Network_Security);
    syllabus.put("Web Technology", R.string.Web_Technology);
    syllabus.put("Artificial Intelligence", R.string.Artificial_Intelligence);
    syllabus.put("Consumer Electronics", R.string.Consumer_Electronics);
    syllabus.put("Embedded Systems", R.string.Embedded_Systems);
    syllabus.put("Instrumentation Engineering", R.string.Instrumentation_Engineering);
    syllabus.put("Signal Processing", R.string.Signal_Processing);
    syllabus.put("Wireless Communication", R.string.Wireless_Communication);
    syllabus.put("Mechanical Engineering", R.string.Mechanical_Engineering);
    syllabus.put("Renewable Energy", R.string.Renewable_Energy);
    syllabus.put("Robotics", R.string.Robotics);
    syllabus.put("Construction Engineering", R.string.Construction_Engineering);
    syllabus.put("Nanotechnology", R.string.Nanotechnology);
    syllabus.put("Chemicals In Daily Life: Nutrition & Adulteration", R.string.Chemicals);
    syllabus.put("Psychology", R.string.Psychology);
    syllabus.put("Management Mantra", R.string.management_mantra);
    syllabus.put("Managing Human Resource", R.string.managing_human_resource);
    syllabus.put("Marketer’s Tool Kit", R.string.Marketers_Tool_Kit);
    syllabus.put("Wealth Management", R.string.Wealth_Management);
    syllabus.put("Animation", R.string.Animation);
    syllabus.put("Audio - Visual Communication",R.string.Audio_Visual);
    syllabus.put("Media Management", R.string.Media_Management);
    syllabus.put("Print Media",R.string.Print_Media);
    syllabus.put("Biotechnology Management",R.string.Biotechnology_Management);
    syllabus.put("Conservation Biology",R.string.Conservation_Biology);
    syllabus.put("Disaster Management",R.string.Disaster_Management);
    syllabus.put("Environmental Law",R.string.Environmental_Law);
    syllabus.put("Environmental Management",R.string.Environmental_Management);
    syllabus.put("Industrial Environmental, Health & Safety", R.string.Industrial_Environmental);
    syllabus.put("Human Rights",R.string.Human_Rights);
    syllabus.put("Gandhian Studies", R.string.Gandhain_Studies);
    syllabus.put("Intellectual Property Rights", R.string.Intellectual_Property);
    syllabus.put("English Literature",R.string.English_Literature);
    syllabus.put("Fashion Management",R.string.Fashion_Management);
    syllabus.put("IT Skills for Professionals", R.string.IT_Skills);
    syllabus.put("Data Science", R.string.data_science);
    syllabus.put("Internet of Things", R.string.iot);
  }


}
