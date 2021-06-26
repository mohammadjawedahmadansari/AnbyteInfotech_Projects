package com.example.userloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.getActivity;
import static android.widget.Toast.LENGTH_SHORT;

public class Login extends AppCompatActivity {
    Button btnLogin,btnSignup,btnHome;
    EditText uname,pass;
    //Creating Volley RequestQueue.
    RequestQueue requestQueue;

    //Creating shared preferences
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    //Storing server url into String variable.
    String HttpUrl = "http://192.168.2.50/Jawedphp/user_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Assigning Id's
        btnLogin = (Button)findViewById(R.id.btnLogin);
        uname = (EditText)findViewById(R.id.editUsername);
        pass = (EditText) findViewById(R.id.editPassword);
        btnHome = (Button)findViewById(R.id.btnHome);

       // SharedPreferences sharedPref = getApplicationContext().getPreferences(Context.MODE_PRIVATE);
        //Creating Volley newRequestQueue.
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Assigining Activity this to progress dialog.
        progressDialog = new ProgressDialog(getApplicationContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }
    public void userLogin() {

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String serverResponse) {

                        Toast.makeText(getApplicationContext(), serverResponse+"", LENGTH_SHORT).show();
                        Log.d("SERVER_RESPONSE",serverResponse);
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Matching server responce message to our text.
                        if(serverResponse.equals("0")) {
                            // If response matched then show the toast.
                            Toast.makeText(getApplicationContext(), "something went wrong" , Toast.LENGTH_LONG).show();
                            //  ((MainActivity) getActivity()).transact(new SignUpFragment());
                        }
                        else if(serverResponse.equalsIgnoreCase("1")){
                            Toast.makeText(getApplicationContext(), "Logged in Successfull.", LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), HomeScreen.class);
                            startActivity(i);
                            //((MainActivity)getActivity()).transact(new HomeFragment());
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(getApplicationContext(), serverResponse, Toast.LENGTH_LONG).show();
                            //finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("username",uname.getText().toString().trim());
                params.put("password", pass.getText().toString().trim());
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
}
