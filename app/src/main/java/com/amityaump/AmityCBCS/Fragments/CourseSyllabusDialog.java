package com.amityaump.AmityCBCS.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.amityaump.AmityCBCS.R;


public class CourseSyllabusDialog extends DialogFragment {
  private static final String TAG = "EndDateTimePickerDialog";

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.webview_layout, container, false);
    Bundle bundle = getArguments();
    int id = 0;
    if (bundle != null) {
      id = bundle.getInt("DATA");
      WebView webView = v.findViewById(R.id.webView);
      String html = getString(id);
      webView.loadData(html, "text/html", "utf-8");
    }
    ImageView imageView = v.findViewById(R.id.cancel_button);
    imageView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        dismiss();
      }
    });

    return v;
  }

}

