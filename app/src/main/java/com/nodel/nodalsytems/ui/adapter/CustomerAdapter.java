package com.nodel.nodalsytems.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nodel.nodalsytems.R;
import com.nodel.nodalsytems.data.model.CustomerDTO;

import java.util.ArrayList;

/**
 * Created by yugandhar on 1/6/2018.
 */

public class CustomerAdapter extends  RecyclerView.Adapter<CustomerAdapter.MyViewHolder>
{
    ArrayList<CustomerDTO> alCustomerDTOs;
    Context clContext;

    public CustomerAdapter(Context context,ArrayList<CustomerDTO> alCustomerDTOs)
    {
        this.clContext=context;
        this.alCustomerDTOs=alCustomerDTOs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(clContext).inflate(R.layout.customer_list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i)
    {
        CustomerDTO clCustomerDTO=alCustomerDTOs.get(i);
        viewHolder.custName.setText(clCustomerDTO.getCustomerName());
        viewHolder.custStatus.setText(clCustomerDTO.getStatus());
        viewHolder.custMobile.setText(clCustomerDTO.getPhoneNumber());
        viewHolder.custDate.setText(clCustomerDTO.getCustomerFromDate());
        viewHolder.custAddress.setText(clCustomerDTO.getAddress());

    }

    @Override
    public int getItemCount() {
        return alCustomerDTOs.size();
    }

   /* @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }*/

   public ArrayList<CustomerDTO> getData()
   {
       return alCustomerDTOs;
   }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView custName,custStatus,custMobile,custDate,custAddress;
        public MyViewHolder(View view) {
            super(view);

            custName = (TextView)view.findViewById(R.id.tv_cust_name);
            custStatus = (TextView)view.findViewById(R.id.tv_status);
            custMobile = (TextView)view.findViewById(R.id.tv_mobile_number);
            custDate = (TextView)view.findViewById(R.id.tv_cus_date);
            custAddress = (TextView)view.findViewById(R.id.tv_cust_address);

        }
    }
}
