package com.nodel.nodalsytems.ui.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.nodel.nodalsytems.R;
import com.nodel.nodalsytems.data.model.CustomerDTO;
import com.nodel.nodalsytems.ui.OrderActivity;
import com.nodel.nodalsytems.ui.adapter.CustomerAdapter;
import com.nodel.nodalsytems.ui.view.comp.recyclerview.RecyclerItemClickListener;

import java.util.ArrayList;

public class CustomersListActivty extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_list_activty);
        RecyclerView customerRecyclerView= (RecyclerView) findViewById(R.id.customer_recycler_view);
        final CustomerAdapter customerAdapter=new CustomerAdapter(this,getData());
        customerRecyclerView.setHasFixedSize(true);
        customerRecyclerView.setAdapter(new CustomerAdapter(this,getData()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        customerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent clIntent=new Intent(CustomersListActivty.this,NewCustomerActivity.class);
                startActivity(clIntent);
            }
        });
///*
        customerRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click

                        Intent clIntent=new Intent(CustomersListActivty.this, OrderActivity.class);
                        ArrayList<CustomerDTO> alCustomerDTOs=customerAdapter.getData();
                        clIntent.putExtra("customer", alCustomerDTOs.get(position));
                        startActivity(clIntent);
                    }
                })
        );
//        customerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<CustomerDTO> getData()
    {
        ArrayList<CustomerDTO> arrayList=new ArrayList<>();
        for (int i = 0; i <5 ; i++)
        {
            CustomerDTO customerDTO =new CustomerDTO();
            customerDTO.setCustomerName("Customer0"+i);
            customerDTO.setStatus("Active");
            customerDTO.setAddress("Begumpet Colney00"+i);
            customerDTO.setCustomerFromDate("12/jan/2018");
            customerDTO.setPhoneNumber("4561237896");
            arrayList.add(customerDTO);
        }
        return arrayList;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
