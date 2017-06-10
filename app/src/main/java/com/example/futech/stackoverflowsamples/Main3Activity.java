package com.example.futech.stackoverflowsamples;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    EditText et;
    ListView lv;
    Button bt;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        et = (EditText) findViewById(R.id.TextAdd);
        bt = (Button) findViewById(R.id.btnadd);
        lv = (ListView) findViewById(R.id.listView);

        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(Main3Activity.this, android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(adapter);

        Button mShowDialog = (Button) findViewById(R.id.btnSave);

        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Main3Activity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add, null);
                final EditText editText_mUser = (EditText) mView.findViewById(R.id.TextAdd);
                Button mAdd = (Button) mView.findViewById(R.id.btnadd);
                mBuilder.setView(mView);
                //create dialog instance here, so that it can be dismissed from within the OnClickListener callback
                final AlertDialog dialog = mBuilder.create();

                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText_mUser.getText().toString().isEmpty()) {
                            // Instead of et.getText(), call mUser.getText()
                            String result = editText_mUser.getText().toString();
                            arrayList.add(result);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(Main3Activity.this, "Success", Toast.LENGTH_SHORT).show();
                            //dismiss dialog once item is added successfully
                            dialog.dismiss();
                        } else {
                            Toast.makeText(Main3Activity.this, "Error pls Write", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }
}
