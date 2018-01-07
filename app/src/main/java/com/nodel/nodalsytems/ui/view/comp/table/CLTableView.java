package com.nodel.nodalsytems.ui.view.comp.table;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

public class CLTableView extends TableLayout 
{
    private int iMaxRows=16;    
    private Context context;
    private String[] sColumnNames;
    private int iSelectedRowIndex=0;
    private int iLastSelectedRowIndex= -1;
    private boolean isRowClicked;
    private int iDataRowColor;
    private int iDataTextColor;
    private GradientDrawable selectedRowGradient;
    private int iFocusedCellTextColor;
    private int iRowsCount;
    private int iColumnsCount;
    private int iLastRowId;
    private int iFirstRowId;
    private ArrayList<String> deletedRowIdList;
    
    private short iExtraWidth=0;
    private GestureDetector gestureDetector;
    private GestureDetector.SimpleOnGestureListener clGestureListener;
    
    public  CLTableView(Context context) 
    {
	super(context);
	this.context=context;
	iExtraWidth=(short)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics());
    }
    
    public void setGestureListener(GestureDetector.SimpleOnGestureListener clGestureListener)
    {
	this.clGestureListener=clGestureListener;
    }
    
    public void  initTable(String[][] sData,String[] sColumnNames,short[] iColumnWidths,
	    GradientDrawable headingRowGradient,byte byHeadingTextSize,int iHeadingTextColor,
	    int iDataRowColor,byte iDataTextSize,int iDataTextColor,
	    GradientDrawable selectedRowGradient,int iFocusedCellTextColor,int iMaxRows )
    {
	this.sColumnNames=sColumnNames;
	this.iDataRowColor=iDataRowColor;
	this.iDataTextColor=iDataTextColor;
	this.selectedRowGradient=selectedRowGradient;
	this.iFocusedCellTextColor=iFocusedCellTextColor;
	this.iColumnsCount=sColumnNames.length;
	this.iMaxRows=iMaxRows;
	deletedRowIdList=new ArrayList<String>();
	
	
	TableRow headerRow=new TableRow(context);
	headerRow.setBackgroundDrawable(headingRowGradient);
	
	for(int iColIndex=0;iColIndex<iColumnsCount;iColIndex++)
	{
	    CLTextView headLabel=new CLTextView(context);
	    headLabel.setBorderColor(Color.parseColor("#D1D1D1"));
	    headLabel.setTextSize(byHeadingTextSize);
	    //headLabel.setTextColor(iHeadingTextColor);
	    headLabel.setTextColor(Color.BLACK);
	    //headLabel.setTypeface(Typeface.DEFAULT_BOLD);
	    headLabel.setPadding(3, 0, 0, 0);
	    
	    //headLabel.setWidth(iColumnWidths[iColIndex]+iExtraWidth);
	    headLabel.setWidth((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, iColumnWidths[iColIndex], getResources().getDisplayMetrics())+iExtraWidth);
	    
	    headLabel.setText(sColumnNames[iColIndex]);
	    headerRow.addView(headLabel);
	}
	addView(headerRow);
	
	CLEventListener clEventHanlder=new CLEventListener();
	iRowsCount=sData.length;
	iLastRowId=iRowsCount-1;
	iFirstRowId=0;
	//int iColumnsCount=sData[0].length;
	for(int iRowIndex=0;iRowIndex<iMaxRows;iRowIndex++)
	{
	    TableRow tableRow=new TableRow(context);
	    tableRow.setId(iRowIndex);
	   
	    tableRow.setBackgroundColor(iDataRowColor);
	    for(int iColIndex=0;iColIndex<iColumnsCount;iColIndex++)
	    {	
		CLTextView cellLabel=new CLTextView(context);
		cellLabel.setBorderColor(Color.parseColor("#D1D1D1"));
		cellLabel.setTextSize(iDataTextSize);
		cellLabel.setTextColor(iDataTextColor);
		cellLabel.setPadding(3, 0, 0, 0);
		
		//cellLabel.setWidth(iColumnWidths[iColIndex]+iExtraWidth);
		cellLabel.setWidth((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, iColumnWidths[iColIndex], getResources().getDisplayMetrics())+iExtraWidth);
		if(iRowIndex<sData.length)
		{
		    cellLabel.setText(sData[iRowIndex][iColIndex]);
		    //tableRow.setOnClickListener(clEventHanlder);
		    tableRow.setOnTouchListener(clEventHanlder);
		    tableRow.setOnKeyListener(clEventHanlder);
		    tableRow.setFocusable(true);
		}
		
		tableRow.addView(cellLabel);
	    }
	    addView(tableRow);
	}
	gestureDetector = new GestureDetector(clGestureListener);
	
	iSelectedRowIndex=0;
	iLastSelectedRowIndex=-1;
	isRowClicked=true;//selecting row for first time
	selectRow(iSelectedRowIndex);
    }
    
    public void setTableData( String[][] sData)
    {
	clearTable();// clear table data
	
	iRowsCount=sData.length;
	iLastRowId=iRowsCount-1;
	iFirstRowId=0;
	int iRowIndex=0;
	int iColumnIndex=0;
	int iTotalCells=iRowsCount*iColumnsCount;
	
	for(int iCellIndex=0;iCellIndex<iTotalCells;iCellIndex++)
	{
	    iColumnIndex=iCellIndex;
	    if(iCellIndex>iColumnsCount-1)
	    {
		iRowIndex=iCellIndex/iColumnsCount;
		iColumnIndex=iCellIndex%iColumnsCount;
		
		(  (CLTextView)(  (TableRow)this.getChildAt( (iRowIndex+1) )  ).getChildAt( (iColumnIndex) )  ).setText( sData[iRowIndex][iColumnIndex] );
	    }
	    else
	    {
		(  (CLTextView)((TableRow)this.getChildAt(1)).getChildAt( (iCellIndex) )  ).setText(sData[0][iColumnIndex]);
	    }
	}
	TableRow tableRow;
	CLEventListener clEventHanlder=new CLEventListener();
	for(int iRow=0;iRow<iMaxRows;iRow++)
	{
	    tableRow=(TableRow)this.getChildAt(iRow+1);
	    if(iRow<iRowsCount-1)
	    {
		//tableRow.setOnClickListener(clEventHanlder);
		tableRow.setOnTouchListener(clEventHanlder);
		tableRow.setOnKeyListener(clEventHanlder);
		tableRow.setFocusable(true);
	    }
	    else
	    {
		tableRow.setOnTouchListener(null);
		tableRow.setOnKeyListener(null);
		tableRow.setFocusable(false);
	    }
	}
	
	iSelectedRowIndex=0;
	isRowClicked=true;//selecting row for first time
	selectRow(iSelectedRowIndex);
    }
    
    public void selectRow(int iRowIndex)
    {
	iSelectedRowIndex=iRowIndex;
	if(iSelectedRowIndex<iFirstRowId)
	{
	    iSelectedRowIndex=iFirstRowId;
	}
	else if(iSelectedRowIndex>iLastRowId) 
	{
	    if(iSelectedRowIndex!=0)
	    {
		iSelectedRowIndex=iLastRowId;
	    }
	}
	else if(iLastSelectedRowIndex>=iFirstRowId)
	{
	    this.getChildAt( (iLastSelectedRowIndex+1) ).setBackgroundColor(iDataRowColor);
	}

	this.getChildAt( (iSelectedRowIndex+1) ).setBackgroundDrawable(selectedRowGradient);
	
	iLastSelectedRowIndex=iSelectedRowIndex;
	this.getChildAt( (iSelectedRowIndex+1) ).requestFocus();
    }
    
    public void setHeaderLabel(int iColumnIndex,String sValue)
    {
	((CLTextView)((TableRow)this.getChildAt(0)).getChildAt( (iColumnIndex) ) ).setText(sValue);
	sColumnNames[iColumnIndex]=sValue;
    }
    
    public String getHeaderLabel(int iColumnIndex)
    {
	return ((CLTextView)((TableRow)this.getChildAt(0)).getChildAt( (iColumnIndex) ) ).getText().toString().trim().trim();
    }
    
    public void clearTable()
    {
	int iTotalCells=iMaxRows*iColumnsCount;
	int iRowIndex=0;
	int iColumnIndex=0;
	for(int iCellIndex=0;iCellIndex<iTotalCells;iCellIndex++)
	{
	    iColumnIndex=iCellIndex;
	    if(iCellIndex>iColumnsCount-1)
	    {
		iRowIndex=iCellIndex/iColumnsCount;
		iColumnIndex=iCellIndex%iColumnsCount;
		(  (CLTextView)(  (TableRow)this.getChildAt( (iRowIndex+1) )  ).getChildAt( (iColumnIndex) )  ).setText("") ;
	    }
	    else
	    {
		(  (CLTextView)((TableRow)this.getChildAt(1)).getChildAt( (iCellIndex) )  ).setText("");
	    }
	}
	
	if(!deletedRowIdList.isEmpty())
	{
	    int iDeletedId;
	    for(int iIndex=0;iIndex<deletedRowIdList.size();iIndex++)
	    {
		iDeletedId=Integer.parseInt(deletedRowIdList.get(iIndex));
		getChildAt(iDeletedId+1).setVisibility(VISIBLE);
		getChildAt(iDeletedId+1).setBackgroundColor(iDataRowColor);
	    }
	    deletedRowIdList.clear();
	}
    }
    
    public void setValueAt(int iRowIndex,int iColumnIndex,String sValue)
    {
	(  (CLTextView)(  (TableRow)this.getChildAt( (iRowIndex+1) )  ).getChildAt( (iColumnIndex) )  ).setText(sValue);
    }
    
    public String getValueAt(int iRowIndex,int iColumnIndex)
    {
	return (  (CLTextView)(  (TableRow)this.getChildAt( (iRowIndex+1) )  ).getChildAt( (iColumnIndex) )  ).getText().toString().trim();
    }
    
    public void setSelectedRowId(int iSelectedRowId)
    {
	iSelectedRowIndex=iSelectedRowId;	
    }
    
    public int getSelectedRowId()
    {
	return iSelectedRowIndex;
    }
    
    public void setCellFocus(int iRowIndex,int iColumnIndex,boolean isFocusCell)
    {
	CLTextView selectedCell=(CLTextView)(  (TableRow)this.getChildAt( (iRowIndex+1) )  ).getChildAt( (iColumnIndex) );
	if(isFocusCell)
	{
	    selectedCell.setTextColor(iFocusedCellTextColor);
	    selectedCell.setTypeface(Typeface.DEFAULT_BOLD);
	}
	else
	{
	    selectedCell.setTextColor(iDataTextColor);
	    selectedCell.setTypeface(Typeface.DEFAULT);
	}
    }
    
    public void deleteRow(int iRowIndex)
    {
	this.getChildAt(iRowIndex+1).setVisibility(GONE);//hiding
	
	deletedRowIdList.add(String.valueOf(iRowIndex));
	
	if(iRowIndex==iLastRowId)
	    iLastRowId--;
	
	//selecting non deleted first row
	iSelectedRowIndex=0;
	
	while(deletedRowIdList.contains(String.valueOf(iSelectedRowIndex)))
	{	
	    iSelectedRowIndex++;
	}
	if(iRowIndex==iFirstRowId)
	    iFirstRowId=iSelectedRowIndex;	
	
	iLastSelectedRowIndex=iFirstRowId-1;
	isRowClicked=true;
	selectRow(iSelectedRowIndex);
    }
    
    private class CLEventListener implements OnKeyListener/*,OnClickListener*/,OnTouchListener
    {
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) 
	{
	    if( (event.getAction()==KeyEvent.ACTION_DOWN) || isRowClicked)
	    {
		isRowClicked=false;
		if(keyCode==KeyEvent.KEYCODE_DPAD_DOWN)
		{
		    iSelectedRowIndex++;
		    //selecting non deleted next row
		    while(deletedRowIdList.contains(String.valueOf(iSelectedRowIndex)))
		    {
			iSelectedRowIndex++;
		    }
		    selectRow(iSelectedRowIndex);
		}
		else if(keyCode==KeyEvent.KEYCODE_DPAD_UP)
		{
		    iSelectedRowIndex--;
		    //selecting non deleted previous row
		    while(deletedRowIdList.contains(String.valueOf(iSelectedRowIndex)))
		    {
			iSelectedRowIndex--;
		    }
		    selectRow(iSelectedRowIndex);
		}
	    }
	    return false;
	}

	/*@Override
	public void onClick(View v) 
	{
	    isRowClicked=true;
	    selectRow(v.getId());
	}*/

	@Override
	public boolean onTouch(View view, MotionEvent event) 
	{
	    isRowClicked=true;
	    selectRow(view.getId());
	    return gestureDetector.onTouchEvent(event);
	}   
     }
}
