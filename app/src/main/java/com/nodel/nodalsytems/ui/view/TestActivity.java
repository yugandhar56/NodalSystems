package com.nodel.nodalsytems.ui.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nodel.nodalsytems.R;
import com.nodel.nodalsytems.data.model.CustomerDTO;
import com.nodel.nodalsytems.ui.adapter.CustomerAdapter;

import java.util.ArrayList;

/**
 * Created by yugandhar on 1/7/2018.
 */

public class TestActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_list_activty);

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.customer_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomerAdapter customerAdapter=new CustomerAdapter(this,getData());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(customerAdapter);

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
}
