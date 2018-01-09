package com.nodel.nodalsytems.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.nodel.nodalsytems.R;
import com.nodel.nodalsytems.data.model.CLTablesItemsDTO;
import com.nodel.nodalsytems.data.model.CustomerDTO;
import com.nodel.nodalsytems.ui.util.CLUIUtil;
import com.nodel.nodalsytems.ui.util.Constants;
import com.nodel.nodalsytems.ui.view.comp.CLFixedTableView;
import com.nodel.nodalsytems.ui.view.comp.table.CLTableView;
import com.nodel.nodalsytems.ui.view.example;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    CLFixedTableView clFixedTableView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        CustomerDTO customerDTO= (CustomerDTO) getIntent().getExtras().get("customer");
        getSupportActionBar().setTitle(customerDTO.getCustomerName());
        FrameLayout clMainLinearLayout=new FrameLayout(this);
        clMainLinearLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        clMainLinearLayout.setOrientation(LinearLayout.VERTICAL);

        clFixedTableView=new CLFixedTableView(this);
        LinearLayout.LayoutParams fixedParms=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fixedParms.setMargins(5,5,5,5);
        clFixedTableView.setLayoutParams(fixedParms);

       /* FrameLayout clFrameLayout=new FrameLayout(this);
        clFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        clFrameLayout.addView(clFixedTableView);

        FloatingActionButton floatingActionButton=new FloatingActionButton(this);
        FrameLayout.LayoutParams frameParms=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fixedParms.gravity= Gravity.END|Gravity.BOTTOM;
        floatingActionButton.setLayoutParams(frameParms);*/

       /* clFrameLayout.addView(clFixedTableView);
        clFrameLayout.addView(floatingActionButton);*/
        FloatingActionButton floatingActionButton=new FloatingActionButton(this);
        FrameLayout.LayoutParams frameParms=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        frameParms.gravity= Gravity.END|Gravity.BOTTOM;
        floatingActionButton.setBackgroundResource(android.R.drawable.ic_input_add);
        floatingActionButton.setLayoutParams(frameParms);
        clMainLinearLayout.addView(clFixedTableView);
        clMainLinearLayout.addView(floatingActionButton);
        setContentView(clMainLinearLayout);

        String sColumnNames[] =new String[] {"Item" ,"Quantity" ,"Price","Tax" ,"Discount" , "NetPrice"};
        short shColumnWidths[]= new short[]{CLUIUtil.toDip(this,150),CLUIUtil.toDip(this,80),
                CLUIUtil.toDip(this,80),CLUIUtil.toDip(this,50),CLUIUtil.toDip(this,80),CLUIUtil.toDip(this,80)};
        byte byDisplayType[]= new byte[]{Constants.IFields.IDisplayStyle.Normal,Constants.IFields.IDisplayStyle.Normal,
                Constants.IFields.IDisplayStyle.Normal,Constants.IFields.IDisplayStyle.Normal,
                Constants.IFields.IDisplayStyle.Normal,Constants.IFields.IDisplayStyle.Normal};
//        clFixedTableView= (CLFixedTableView) findViewById(R.id.orders_list);
        clFixedTableView.initEmptyTable(sColumnNames,shColumnWidths,byDisplayType);
        setBodyData(getData(), (short) sColumnNames.length);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OrderActivity.this,OrderItemActivity.class);
                startActivity(intent);
            }
        });
//        setContentView(R.layout.activity_order);
    }
    /*
   * setting the table data
   * */
    public void setBodyData(ArrayList<CLTablesItemsDTO> alBodyData,short shColCount)
    {
        if (alBodyData != null) {

            Object objFieldValue = null;
            String sValue = null;
            short iColIndex = 0;
            short iRowIndex=0;
//            CLDBStore clDBStore = null;
//            SQLiteDatabase clDatabase = null;
            boolean isReadOnly = false;
            String sUnitName=null;
            CLTablesItemsDTO clTablesItemsDTO = null;
            for (short iRow = 0; iRow < alBodyData.size(); iRow++)
            {
                clTablesItemsDTO = alBodyData.get(iRow);
                clFixedTableView.insertRow(null, null, isReadOnly);
                String sIssueQty = null;
                iRowIndex = clFixedTableView.getLastRowId();
                TableRow clTableRow=clFixedTableView.getTableRow(iRowIndex);
                clTableRow.setGravity(Gravity.CENTER_HORIZONTAL);
                clTableRow.setId(iRow);
                CLRowListener clRowListener=new CLRowListener();
                clTableRow.setOnClickListener(clRowListener);
                for (iColIndex = 0; iColIndex < shColCount; iColIndex++) {

                    sValue = "";
                    if ( iColIndex == 0 && clTablesItemsDTO.getsItemName() != null)
                    {
                        sValue = clTablesItemsDTO.getsItemName();
//                        objFieldValue = String.valueOf(clTablesItemsDTO.getsCode());  //to
                        // o
                    }
                    else if(iColIndex==1)
                        sValue=String.valueOf(clTablesItemsDTO.getUnit());
                    else if(iColIndex==2)
                        sValue=String.valueOf(clTablesItemsDTO.getiRate());
                    else if(iColIndex==3)
                        sValue=String.valueOf(clTablesItemsDTO.getTax());
                    else if(iColIndex==4)
                        sValue=String.valueOf(clTablesItemsDTO.getDisCount());
                    else if(iColIndex==5)
                        sValue=String.valueOf(clTablesItemsDTO.getNetAmount());

                    clFixedTableView.setValueAt(iRowIndex, iColIndex, sValue, objFieldValue);

                    /*else if ( iColIndex == 1 && clTablesItemsDTO.getsUnit() != null)
                    {
                        clProductStore = new CLProductStore();
                        String sCompanyCode = CLMainActivity.getCompanyCode();
                        clDBStore = CLDBStore.getInstance(CLMainActivity.getInstance());
                        clDatabase = clDBStore.getDatabase(true);
                        sUnitName= clProductStore.getUnitsAsString(clTablesItemsDTO.getProductId(), sCompanyCode, clDatabase);
                        sUnitNmaes=sUnitName.split("\\|");
                        sValue = sUnitNmaes[0];
                    }
                    else if ( iColIndex == 2 && clTablesItemsDTO.getsOrderQty() != null)
                    {
                        try {
                            sValue = String.valueOf(clTablesItemsDTO.getsOrderQty());
                            //sValue = String.valueOf(CLUIUtil.toDecimalFormat(Math.abs(Double.parseDouble(clTablesItemsDTO.getsOrderQty())),(byte)0, "##,###,###."));

                            lTotalQty=Long.parseLong(sValue)+lTotalQty;
                            tvtotaltvQuentyValue.setText(String.valueOf(Math.abs(lTotalQty)));

                        }catch (NumberFormatException e) {
                            sValue = "0";
                        }
                    }*/
               /*     else if ( iColIndex == 3 ) //todo
                    {
                        sValue = "0";
                        sIssueQty=sValue;

                    }//todo
                    else if ( iColIndex == 4 && clTablesItemsDTO.getProductId() != 0)
                        sValue = String.valueOf(clTablesItemsDTO.getProductId());
                    else if ( iColIndex == 5&& clTablesItemsDTO.getRefBodyId() != 0)
                        sValue = String.valueOf(clTablesItemsDTO.getRefBodyId());
                    else if ( iColIndex == 6&& clTablesItemsDTO.getiRate() != 0)
                        sValue = String.valueOf(clTablesItemsDTO.getiRate());*/
//                    else if ( iColIndex == 7&& clTablesItemsDTO.getiRate() != 0) //todo




/*  if (sValue.indexOf('|') == -1)
                        clBodyTable.setValueAt(iRowIndex, iColIndex, sValue, objFieldValue);
*/
                   /* else {
                        String[] sMasterVals = sValue.split("\\|");

                        CLKeyValueSSI clKeyValueSSI = new CLKeyValueSSI(sMasterVals[1], sMasterVals[2], Integer.parseInt(sMasterVals[0]));
                        clBodyTable.setValueAt(iRowIndex, iColIndex, sMasterVals[1], clKeyValueSSI);

                    }*/

                }
               /* long iOrderedQty = Long.parseLong(clTablesItemsDTO.getsOrderQty());
                long iIssuedQty = Long.parseLong(sIssueQty);*///todo:
//                clTableRow.setBackgroundColor(checkColours(iOrderedQty, iIssuedQty));


            }
//            iTotalItemCount=clBodyTable.getRowsCount();
//            tvTotalitemCountValue.setText(String.valueOf(iTotalItemCount));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<CLTablesItemsDTO> getData()
   {
       ArrayList<CLTablesItemsDTO> alTableData=new ArrayList<>();
       for (int i = 0; i <5 ; i++)
       {

           CLTablesItemsDTO clTablesItemsDTO=new CLTablesItemsDTO();
           clTablesItemsDTO.setsItemName("Item"+i+1);
           clTablesItemsDTO.setUnit((i+2)*3);
           clTablesItemsDTO.setiRate(100*(i+1));
           clTablesItemsDTO.setTax((i+1)*5);
           clTablesItemsDTO.setDisCount(5);
           //int iPrice=clTablesItemsDTO.getUnit()*clTablesItemsDTO.getiRate();
           //int iTax=clTablesItemsDTO.getUnit()*clTablesItemsDTO.getTax();
           //int priceAfterDiscount=iPrice-(iPrice*(clTablesItemsDTO.getDisCount()/100));
           clTablesItemsDTO.setNetAmount(getNetAmount(clTablesItemsDTO.getUnit(),clTablesItemsDTO.getTax(),
                   clTablesItemsDTO.getiRate(),clTablesItemsDTO.getDisCount()));
           alTableData.add(clTablesItemsDTO);
       }
       return alTableData;
   }

   private int getNetAmount(int iUnit,int iTax,int iRate,int iDiscount)
   {
       int iPrice=iUnit*iRate;
       int icTax=iUnit*iTax;
       int priceAfterDiscount=iPrice-(iPrice*(iDiscount/100));
       return priceAfterDiscount+icTax;
   }

   private void editQuantityDialog(TableRow tableRow)
   {

       AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
       LayoutInflater inflater = getLayoutInflater();

       View dialog_layout = inflater.inflate(R.layout.dialog_layout,null);
       TextView txtTitle=dialog_layout.findViewById(R.id.dialog_title);
       txtTitle.setText(((TextView) tableRow.getChildAt(1)).getText().toString());
       final TextView clQuantity= (TextView) tableRow.getChildAt(2);
       final TextView clDiscount= (TextView) tableRow.getChildAt(5);
       final TextView clNetAmount= (TextView) tableRow.getChildAt(6);
       final EditText etQty = (EditText) dialog_layout.findViewById(R.id.txt_qty);
       final EditText etDiscount = (EditText) dialog_layout.findViewById(R.id.txt_discount);
       etQty.setText(clQuantity.getText().toString());
       etDiscount.setText(clDiscount.getText().toString());
       builder.setView(dialog_layout)

               .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                       clQuantity.setText(etQty.getText().toString());
                       clDiscount.setText(etDiscount.getText().toString());


                       Toast.makeText(OrderActivity.this, "Order Updated Success", Toast.LENGTH_SHORT).show();

                   }
               })
               .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.dismiss();
                   }
               });

       AlertDialog dialog = builder.create();
       dialog.show();


   }



    private class CLRowListener implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            TableRow row = (TableRow) view;
            row.setBackgroundColor(Color.parseColor("#E6EE9C"));
            editQuantityDialog(row);
           /* for (short j = 0; j < row.getChildCount(); j++)
            {
//                row.getChildAt(j).setBackgroundColor(context.getResources().getColor(R.color.rowHighlight));
                if (j == 3)
                {//qty


//                    numberControlDialog(context, clNumPicker.getView(context, targetView, iMinVal), targetView);
                }
            }*/
        }

    }



   }


