package com.example.jsoncustomlistview_example;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int[] images = {R.drawable.vb,R.drawable.csharp,R.drawable.c,R.drawable.java,R.drawable.cpp,R.drawable.dart,R.drawable.js,R.drawable.objectivec,R.drawable.perl,
    R.drawable.ruby,R.drawable.python,R.drawable.php,R.drawable.net};

    String[] pLangagues = {"Visual Basic","C#","C Language","Java","C++","Flutter Dart","JavaScript","Objective C",
            "Perl Language","Ruby","Python","Php",".Net"};
    String[] description = {"Programming language for application","Programming language","Programming language for UNIX","High level Programming language",
            "Programming language","Programming language for iOs and Android","Scripting Language","Programming language","Programming language",
            "Programming language","Programming language for AI","Server Side Programming language","Framework"};


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.customlistview);

        CustomAdaptor customAdaptor = new CustomAdaptor();
        listView.setAdapter(customAdaptor);

        }

        class CustomAdaptor extends BaseAdapter{

            @Override
            public int getCount() {
                return images.length;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view = getLayoutInflater().inflate(R.layout.book_list_items,null);

                ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
                TextView textView_name  = (TextView)view.findViewById(R.id.txtViewName);
                TextView textView_description  = (TextView)view.findViewById(R.id.txtViewtDescription);

                imageView.setImageResource(images[i]);
                textView_name.setText(pLangagues[i]);
                textView_description.setText(description[i]);

                return view;
            }
        }

}
