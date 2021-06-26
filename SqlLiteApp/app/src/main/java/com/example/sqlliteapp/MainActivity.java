package com.example.sqlliteapp;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    SQLiteDatabase db;
    EditText editId,editName,editSurname,editMarks;
    Button btnAddData,btnGetAllData,btnOneData,btnupdateData,btndeleteData,btnResetField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editId = (EditText)findViewById(R.id.editTextId);
        editName = (EditText)findViewById(R.id.editTextName);
        editSurname = (EditText)findViewById(R.id.editTextSurname);
        editMarks = (EditText)findViewById(R.id.editTextMarks);
        btnAddData = (Button) findViewById(R.id.btnAddData);
        btnGetAllData = (Button) findViewById(R.id.btnGetAllData);
        btnOneData =(Button) findViewById(R.id.btnData);
        btnupdateData = (Button) findViewById(R.id.btnupdateData);
        btndeleteData = (Button) findViewById(R.id.btndeleteData);
        btnResetField = (Button) findViewById(R.id.btnResetField);
        addData();
        profile();
        getAllData();
        getUpdateData();
        deleteData();
        resetField();
    }
    public void addData()
    {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Data", Toast.LENGTH_SHORT).show();
                        boolean isInserted  = myDb.insertData(editId.getText().toString(),editName.getText().toString(),
                                editSurname.getText().toString(),editMarks.getText().toString());
                        if(isInserted == true){
                            Toast.makeText(MainActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        editId.setText("");
                        editName.setText("");
                        editSurname.setText("");
                        editMarks.setText("");
                        editId.clearFocus();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Data not inserted !", Toast.LENGTH_SHORT).show();
                                    
                    }
                }
        );
    }

public void profile()
{
    btnOneData.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String id = editId.getText().toString();
            if(id.equals(String.valueOf(""))){
                editId.setError("Enter id to get data !");
                return;
            }
            Cursor res = myDb.getData(id);
            String data = null;
            if(res.moveToFirst()){
                editId.setText(res.getString(0));
                editName.setText(res.getString(1));
                editSurname.setText(res.getString(2));
                editMarks.setText(res.getString(3));
                /*data = "Id : "+res.getString(0)+"\n"+
                        "Name :"+res.getString(1)+"\n"+
                        "Surname :"+res.getString(2)+"\n"+
                        "Marks :"+res.getString(3)+"\n";*/
            }
            else{
                Toast.makeText(MainActivity.this, "Id does not existing", Toast.LENGTH_SHORT).show();
                editId.setText("");

                editName.setText("");
                editSurname.setText("");
                editMarks.setText("");
                editId.clearFocus();
            }
            //showMessage("Data",data);
        }
    });
}

    // get all data
    public void getAllData()
    {
        btnGetAllData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDb.getAll();
                        if(res.getCount() == 0){
                            //show message
                       showMessage("Error","Nothing found !");
                            return;
                        }


                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("Id : "+res.getString(0)+"\n");

                            buffer.append("Name : "+res.getString(1)+"\n");

                            buffer.append("Surname : "+res.getString(2)+"\n");

                            buffer.append("Marks : "+res.getString(3)+"\n\n");
                        }
                        //show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    //Update data
    public void getUpdateData()
    {
        btnupdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isUpdate = myDb.getUpdataData(editId.getText().toString(),editName.getText().toString(),
                        editSurname.getText().toString(),editMarks.getText().toString());
                if(isUpdate == true){
                    Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(MainActivity.this, "Data could not be Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteData()
    {
        btndeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = myDb.deleteData(editId.getText().toString());
                if(deletedRows == 0 )
                {
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(MainActivity.this, "Data could not be deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void resetField()
    {
        btnResetField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editId.setText("");
                editName.setText("");
                editSurname.setText("");
                editMarks.setText("");
                editId.clearFocus();
            }
        });

    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
