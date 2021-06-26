package com.example.json_parser_example;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;


public class MainActivity extends AppCompatActivity {

    Button btnFetchData;
    //public static TextView txtFetchDataLabel,txtId,txtName,txtEmail,txtAdd;
    //public static TextView txtId2,txtName2,txtEmail2,txtAdd2;
    public String id,name,email,address;
    private ListView lv;
    private ProgressDialog progressDialog;
    ArrayList<HashMap<String,String>>contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        lv = (ListView)findViewById(R.id.list);

        btnFetchData = (Button)findViewById(R.id.btnFetchData);
       /* txtFetchDataLabel = (TextView) findViewById(R.id.txtFetchLabel);
        txtId =(TextView) findViewById(R.id.txtIdLabel);
        txtName = (TextView)findViewById(R.id.txtNameLabel);
        txtEmail = (TextView) findViewById(R.id.txtEmailLabel);
        txtAdd = (TextView) findViewById(R.id.txtAddLabel);
        txtId2 = (TextView) findViewById(R.id.txtIdLabel2);
        txtName2 = (TextView)findViewById(R.id.txtNameLabel2);
        txtEmail2 = (TextView) findViewById(R.id.txtEmailLabel2);
        txtAdd2 = (TextView) findViewById(R.id.txtAddLabel2);

       */
       btnFetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*creating an object of fetchData class where the backend code and
                calling the execute method to run
                */
                fetchData process = new fetchData();
                process.execute();

            }
        });

    }
    public class fetchData extends AsyncTask<Void,Void,Void> {

        String data = "";
        String dataParsed = "";
        String singleParsed = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @SuppressLint("WrongThread")

        @Override
        protected Void doInBackground(Void... voids) {

            //initialize the api in variable
            try {
                URL url = new URL("https://api.androidhive.info/contacts/");

                //Create a connection to connect with api
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //Create input stream to read the data
                InputStream inputStream = httpURLConnection.getInputStream();
                // Create bufferreader to read the data from inputstream
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "";
                while (line != null) {
                    // read line by line
                    line = bufferedReader.readLine();

                    //all the lines being added to the data
                    //all the data store line by line in data var;
                    data = data + line;
                }

                JSONObject jsonObject = new JSONObject(data);
                JSONArray contacts = jsonObject.getJSONArray("contacts");
                // Now Parsing the data here
                // JSONArray JA = new JSONArray(data);
                //String Id
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject jObj = contacts.getJSONObject(i);

                    id = jObj.getString("id");
                     name = jObj.getString("name");
                     email = jObj.getString("email");
                     address = jObj.getString("address");

                     HashMap<String,String> contact = new HashMap<>();

                     //adding each child node to Hashmap key => value
                    contact.put("id",id);
                    contact.put("name",name);
                    contact.put("email",email);
                    contact.put("address",address);

                    //adding contact to contact list
                    contactList.add(contact);


             /*   singleParsed =  "Name :" +JO.get("name") + "\n"+
                                "Password :" +JO.get("password") + "\n"+
                                "Contact :" +JO.get("contact") + "\n"+
                                "Address :" +JO.get("address") + "\n";


                dataParsed = dataParsed + singleParsed+"\n";
            */
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
           /* txtId2.setText(id);
            txtName2.setText(name);
            txtEmail2.setText(email);
            txtAdd2.setText(address);
           */ if(progressDialog.isShowing())
                progressDialog.dismiss();

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,contactList,
                    R.layout.list_items,new String[]{"id","name","email","address"},
                    new int[]{R.id.id,R.id.name,R.id.email,R.id.address});
            lv.setAdapter(adapter);
            //To show on main activity and calling textview
            //MainActivity.txtFetchDataLabel.setText(this.dataParsed);
            //super.onPostExecute(aVoid);

        }

    }

}
