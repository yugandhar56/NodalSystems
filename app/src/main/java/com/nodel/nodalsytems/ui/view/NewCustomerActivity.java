package com.nodel.nodalsytems.ui.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nodel.nodalsytems.R;

public class NewCustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String itemsArray[]=new String[10];
        for (int i = 0; i <itemsArray.length ; i++) {
            itemsArray[i]="Item0"+(i+1);
        }
        Spinner itemsSpinner= (Spinner) findViewById(R.id.spinner_order_items);
        itemsSpinner.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,itemsArray));


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
