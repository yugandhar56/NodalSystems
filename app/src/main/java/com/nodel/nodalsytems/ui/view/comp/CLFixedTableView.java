package com.nodel.nodalsytems.ui.view.comp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;


import com.nodel.nodalsytems.ui.util.CLUIUtil;
import com.nodel.nodalsytems.ui.util.Constants;
import com.nodel.nodalsytems.ui.view.comp.table.CellView;
import com.nodel.nodalsytems.ui.view.comp.table.TableView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by J.Yugandhar on 08-02-2016.
 */
public class CLFixedTableView extends LinearLayout implements IHorizontalScrollViewListener {

    private short iMaxRows=16;
    //    private Context context;
    private String[] sColumnNames;
    private short iSelectedRowIndex=0;
    private short iLastSelectedRowIndex= -1;
    private boolean isRowClicked;
    private int iDataRowColor;
    private int iDataTextColor;
    private GradientDrawable selectedRowGradient;
    private int iFocusedCellTextColor;
    private short iRowsCount;
    private short iColumnsCount;
    private short iLastRowId;
    private short iFirstRowId;
    private ArrayList<String> deletedRowIdList;
    private int iDataTextHeight = 40;
    TableLayout clBodyTable;
    private int iHeaderTextHeight = 40;
    private byte bySnoWidth;

    private byte[] byDisplayTypes;
    private short iExtraWidth=0;
    private GestureDetector gestureDetector;
    private GestureDetector.SimpleOnGestureListener clGestureListener;



    private OnTouchListener clEventRowHanlder=null;
    private byte byDataTextSize;
    private short[] iColumnWidths;
    private  boolean isIgnoreRow=false;
    private boolean isEditable;
    private boolean isShowSNos,isShowCheckBoxes;
    private short iSno = 0, iImageColIndex = -1;
    private ViewGroup rootScrollView = null;
    private byte[] byDataTxtAligns = null;
    private int[] iColTxtColors = null;
    private int equalQtyRowColor=Color.GREEN;
    private int lessQtyRowColor=Color.parseColor("#FF9800");
    private int iReadOnlyTxtColor = Color.GRAY;
    private int iReadOnlyRowColor = Color.LTGRAY;
    private int iHighlightRowColor = Color.LTGRAY;
    private boolean isShowHeaderRow=true;

  public static final byte layout_hrztlDataView=11,layout_hrztlHeaderView =12;

    public static final byte ALIGN_HLEFT = 1;
    public static final byte ALIGN_HRIGHT = 2;
    public static final byte ALIGN_CENTER = 3;

    public static final byte HIGHLIGHT_ROW = 110;
    public static final byte TOTAL_ROW = 114;
    public CLFixedTableView(Context context) {
        super(context);
        iExtraWidth = (short) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources()
                        .getDisplayMetrics());

        bySnoWidth = (byte) CLUIUtil.toDip(context, 40);
//        if(Constants.IS_NEW_LOOK)
//            iHighlightRowColor = context.getResources().getColor(com.focus.lib.R.color.table_highlight_row_color);
    }
    public void setGestureListener(
            GestureDetector.SimpleOnGestureListener clGestureListener) {
        this.clGestureListener = clGestureListener;
    }
    /*
    * Sets root scroll view
    */
    public void setRootScrollView(ViewGroup rootScrollView){
        this.rootScrollView = rootScrollView;
    }

    public void isShowHeaderRow(boolean isShowHeaderRow) {
        this.isShowHeaderRow = isShowHeaderRow;

    }
    /*
    * Sets whether editable or not
    */
    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }
    /*
    * Returns whether is editable or not
    */
    public boolean isEditable(){
        return  isEditable;
    }
    public void setEventRowHanlder(OnTouchListener clEventRowHanlder) {
        this.clEventRowHanlder = clEventRowHanlder;
    }
    /*
    * Returns whether is ignore row or not
    */
    public boolean isIgnoreRow(){
        return isIgnoreRow;
    }

    /*
    * Sets read only text color
    */
    public void setReadOnlyTxtColor(int iReadOnlyTxtColor) {
        this.iReadOnlyTxtColor = iReadOnlyTxtColor;
    }

    /*
    * Sets read only row color
    */
    public void setReadOnlyRowColor(int iReadOnlyRowColor) {
        this.iReadOnlyRowColor = iReadOnlyRowColor;
    }

    /*
    * Sets data text alignments
    */
    public void setDataTxtAligns(byte[] byDataTxtAligns){
        this.byDataTxtAligns = byDataTxtAligns;
    }
    /*
    * Sets column text colors
    */
    public void setColumnTxtColors(int[] iColTxtColors){
        this.iColTxtColors = iColTxtColors;
    }
    /*
    * Initializes empty table with given information
    */
    public void  initEmptyTable(String[] sColumnNames,short[] iColumnWidths,
                                GradientDrawable headingRowGradient,byte byHeadingTextSize,int iHeadingTextColor,
                                int iDataRowColor,byte byDataTextSize,int iDataTextColor,
                                GradientDrawable selectedRowGradient,int iFocusedCellTextColor,int iDataTextHeight,int iHeaderTextHeight,byte[] byDisplayTypes)
    {
        initEmptyTable(sColumnNames, iColumnWidths, headingRowGradient, byHeadingTextSize, iHeadingTextColor, iDataRowColor, byDataTextSize, iDataTextColor, selectedRowGradient, iFocusedCellTextColor, iDataTextHeight, iHeaderTextHeight, byDisplayTypes, false,false);
    }
    /*
    * Initializes empty table with given information
    */
    public void  initEmptyTable(String[] sColumnNames,short[] iColumnWidths,
                                GradientDrawable headingRowGradient,byte byHeadingTextSize,int iHeadingTextColor,
                                int iDataRowColor,byte byDataTextSize,int iDataTextColor,
                                GradientDrawable selectedRowGradient,int iFocusedCellTextColor,int iDataTextHeight,int iHeaderTextHeight,byte[] byDisplayTypes,boolean isShowSNos)
    {
        initEmptyTable(sColumnNames, iColumnWidths, headingRowGradient, byHeadingTextSize, iHeadingTextColor, iDataRowColor, byDataTextSize, iDataTextColor, selectedRowGradient, iFocusedCellTextColor, iDataTextHeight, iHeaderTextHeight, byDisplayTypes, isShowSNos, false);
    }
    /*
    * Initializes empty table with given information
    */
    public void initEmptyTable(String[] sColumnNames,short[] iColumnWidths,byte[] byDisplayTypes)
    {
        GradientDrawable headingRowGradient=new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,new int[] { Color.parseColor("#FAFAFA"),Color.parseColor("#BABABA") } );
        GradientDrawable selectedRowGradient=new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,new int[] { Color.parseColor("#E6EE9C"),Color.parseColor("#E6EE9C"),Color.parseColor("#E6EE9C") } );
       // GradientDrawable equalRowGradien
        Context context = getContext();

        initEmptyTable(sColumnNames, iColumnWidths, headingRowGradient, (byte)15, Color.DKGRAY, Color.WHITE, (byte)13, Color.BLACK, selectedRowGradient, Color.DKGRAY,CLUIUtil.toDip(context, 40),CLUIUtil.toDip(context, 50), byDisplayTypes, true, false);
    }
    public void  initEmptyTable(String[] sColumnNames,short[] iColumnWidths,
                                GradientDrawable headingRowGradient,byte byHeadingTextSize,int iHeadingTextColor,
                                int iDataRowColor,byte byDataTextSize,int iDataTextColor,
                                GradientDrawable selectedRowGradient,int iFocusedCellTextColor,int iDataTextHeight,int iHeaderTextHeight,byte[] byDisplayTypes,boolean isShowSNos,boolean isShowCheckBoxes)
    {
        this.sColumnNames=sColumnNames;
        this.iDataRowColor=iDataRowColor;
        this.iDataTextColor=iDataTextColor;
        this.selectedRowGradient=selectedRowGradient;
        this.iFocusedCellTextColor=iFocusedCellTextColor;
        this.iColumnsCount=(short)sColumnNames.length;
        this.byDataTextSize=byDataTextSize;
        this.iColumnWidths=iColumnWidths;
        this.iDataTextHeight = iDataTextHeight;
		this.iHeaderTextHeight = iHeaderTextHeight;
        this.byDisplayTypes = byDisplayTypes;
        deletedRowIdList=new ArrayList<String>();
        Context context = getContext();
        this.isShowSNos = isShowSNos;
        this.isShowCheckBoxes = isShowCheckBoxes;
        TableRow headerRow=new TableRow(context);
        headerRow.setBackgroundDrawable(headingRowGradient);
        CellView headLabel = null;

        if(isShowCheckBoxes)
        {
            headLabel = new CellView(context);
            headLabel.setBorderColor(Color.parseColor("#D1D1D1"));
            headLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,byHeadingTextSize);
            headLabel.setTextColor(iHeadingTextColor);
            headLabel.setPadding(3, 0, 0, 0);
            headLabel.setHeight(iHeaderTextHeight);
            headLabel.setGravity(Gravity.CENTER_VERTICAL);
            headLabel.setWidth(CLUIUtil.toDip(context, 40));
            headLabel.setText(" ");
            headerRow.addView(headLabel);
        }
        if(isShowSNos)
        {
            headLabel = new CellView(context);
            headLabel.setBorderColor(Color.parseColor("#D1D1D1"));
            headLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,byHeadingTextSize);
            headLabel.setTextColor(iHeadingTextColor);
            headLabel.setPadding(3, 0, 0, 0);
            headLabel.setHeight(iHeaderTextHeight);
            headLabel.setGravity(Gravity.CENTER_VERTICAL);

            headLabel.setWidth(bySnoWidth);
            headLabel.setText("S.No.");

            headerRow.addView(headLabel);
        }
        for(short iColIndex=0;iColIndex<iColumnsCount;iColIndex++)
        {
            headLabel = new CellView(context);
            if(byDisplayTypes != null && byDisplayTypes.length > iColIndex && byDisplayTypes[iColIndex] == Constants.IFields.IDisplayStyle.Hidden )
            {
                headLabel.setLayoutParams(new AbsListView.LayoutParams(0,0));
                headLabel.setVisibility(GONE);
            }
            else
            {
               /* if(iColIndex==4)
                    headLabel.setVisibility(GONE);*/

                //headLabel.setBorderColor(Color.parseColor("#D1D1D1"));
                headLabel.setTextColor(iHeadingTextColor);
                headLabel.setTypeface(null, Typeface.BOLD);
			    headLabel.setTypeface(Typeface.DEFAULT_BOLD);
                headLabel.setPadding(3, 0, 0, 0);
                headLabel.setHeight(iHeaderTextHeight);
			    headLabel.setWidth(iColumnWidths[iColIndex]);
                headLabel.setGravity(Gravity.CENTER);

//                headLabel.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, iColumnWidths[iColIndex], getResources().getDisplayMetrics()) + iExtraWidth);
//                headLabel.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, iColumnWidths[iColIndex], getResources().getDisplayMetrics()));
//                headLabel.setText(Html.fromHtml(sColumnNames[iColIndex]));
                headLabel.setText(sColumnNames[iColIndex]);

                headLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,byHeadingTextSize);
            }

            headerRow.addView(headLabel);
        }
        //table for header
        LinearLayout clTableVieLayout=new LinearLayout(context);
        clTableVieLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams cltableparms=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        clTableVieLayout.setLayoutParams(cltableparms);
        CLHorizontalScrollView  hHeaderScrollView = new CLHorizontalScrollView(context);
        TableLayout clHeaderTbl=new TableLayout(context);
        hHeaderScrollView.setId(layout_hrztlHeaderView);
        //clHeaderTbl.setPadding(5, 0, 5, 0);
        clHeaderTbl.addView(headerRow);
        clHeaderTbl.setStretchAllColumns(true);
        hHeaderScrollView.setFillViewport(true);
        hHeaderScrollView.addView(clHeaderTbl,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //if(iBodyTblId==BODY_TABLE)
        hHeaderScrollView.setCallback(this);
		/*else
			hHeaderScrollView.setCallback(clVerifyView);*/
        hHeaderScrollView.setHorizontalScrollBarEnabled(false);
        clTableVieLayout.addView(hHeaderScrollView);

        CLHorizontalScrollView  hBodyScrollView = new CLHorizontalScrollView(context);

        hBodyScrollView.setId(layout_hrztlDataView);
        clBodyTable=new TableLayout(context);
       // clBodyTable.setPadding(5, 0, 5, 0);
        addEmptyRow(clBodyTable);
        clBodyTable.setStretchAllColumns(true);
        hBodyScrollView.setFillViewport(true);
        hBodyScrollView.addView(clBodyTable,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //if(iBodyTblId==BODY_TABLE)
        hBodyScrollView.setCallback(this);
		/*else
			hBodyScrollView.setCallback(clVerifyView);*/
        final ScrollView bodyVScrollView = new ScrollView(context);
        bodyVScrollView.addView(hBodyScrollView);
      //  bodyVScrollView.setPadding(5, 0, 5, 10);


        clTableVieLayout.addView(bodyVScrollView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(clTableVieLayout);
       // addEmptyRow(clBodyTable);

//add header table to horizontal view
        //add header table to linearlayout

        //bodytable
        //horizontalview
        //add body to linearly



        //addView(headerRow);	//adding header row
         //fixedView(context);

    }
    /*
    * Initializes table with given information
    */
    public void  initTable(String[][] sData,String[] sColumnNames,short[] iColumnWidths,
                           GradientDrawable headingRowGradient,byte byHeadingTextSize,int iHeadingTextColor,
                           int iDataRowColor,byte byDataTextSize,int iDataTextColor,
                           GradientDrawable selectedRowGradient,int iFocusedCellTextColor,short iMaxRows)
    {
        initTable(sData, sColumnNames, iColumnWidths, headingRowGradient, byHeadingTextSize, iHeadingTextColor, iDataRowColor, byDataTextSize, iDataTextColor, selectedRowGradient, iFocusedCellTextColor, iMaxRows, null);
    }
    /*
    * Initializes table with given information
    */
    @SuppressLint("DefaultLocale")
    public void  initTable(String[][] sData,String[] sColumnNames,short[] iColumnWidths,
                           GradientDrawable headingRowGradient,byte byHeadingTextSize,int iHeadingTextColor,
                           int iDataRowColor,byte byDataTextSize,int iDataTextColor,
                           GradientDrawable selectedRowGradient,int iFocusedCellTextColor,short iMaxRows, byte[] byRowProperties)
    {
        this.sColumnNames=sColumnNames;
        this.iDataRowColor=iDataRowColor;
        this.iDataTextColor=iDataTextColor;
        this.selectedRowGradient=selectedRowGradient;
        this.iFocusedCellTextColor=iFocusedCellTextColor;
        this.iColumnsCount=(short)sColumnNames.length;
        this.iMaxRows=iMaxRows;
        deletedRowIdList=new ArrayList<String>();
        this.byDataTextSize=byDataTextSize;
        this.iColumnWidths=iColumnWidths;
        iDataTextHeight = 45;

        Context context = getContext();

        TableRow headerRow=new TableRow(context);
        headerRow.setBackgroundDrawable(headingRowGradient);
        CellView headLabel = null;
        for(short iColIndex=0;iColIndex<iColumnsCount;iColIndex++)
        {
            headLabel=new CellView(context);
      //      headLabel.setBorderColor(Color.parseColor("#D1D1D1"));
            headLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,byHeadingTextSize);
            //headLabel.setTextColor(iHeadingTextColor);
            headLabel.setTextColor(Color.BLACK);
            //headLabel.setTypeface(Typeface.DEFAULT_BOLD);
            headLabel.setPadding(3, 0, 0, 0);

            headLabel.setWidth(iColumnWidths[iColIndex] + iExtraWidth);
            headLabel.setWidth((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, iColumnWidths[iColIndex], getResources().getDisplayMetrics())+iExtraWidth);

            if(sColumnNames[iColIndex].trim().toLowerCase(Locale.getDefault()).indexOf("$image") != -1)
            {
                iImageColIndex = iColIndex;
                headLabel.setText("Thumbnail");
                iDataTextHeight = 80;
            }
            else
                headLabel.setText(sColumnNames[iColIndex]);
            headerRow.addView(headLabel);
        }
        clBodyTable.addView(headerRow);

        CLEventListener clEventHanlder=new CLEventListener();
        iRowsCount=(short)sData.length;
        iLastRowId=(short)(iRowsCount-1);
        iFirstRowId=0;
        //int iColumnsCount=sData[0].length;
        TableRow tableRow = null;
        CellView cellLabel = null;
//        CLImageInfo imageInfo = null;
        String sFile = null;
        boolean isHighlightRow = false, isTotalRow = false;
        TableRow.LayoutParams trHightligntParams = null;
        for(short iRowIndex=0;iRowIndex<iMaxRows;iRowIndex++)
        {
            tableRow=new TableRow(context);
            tableRow.setId(iRowIndex);

            isHighlightRow = (byRowProperties != null && iRowIndex < byRowProperties.length && byRowProperties[iRowIndex] == HIGHLIGHT_ROW);
            isTotalRow = (byRowProperties != null && iRowIndex < byRowProperties.length && byRowProperties[iRowIndex] == TOTAL_ROW);
            if(isHighlightRow)
            {
                tableRow.setBackgroundColor(iHighlightRowColor);

                cellLabel = new CellView(context);
                cellLabel.setBorderColor(Color.parseColor("#FF0000"));
                cellLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,byDataTextSize);
                cellLabel.setTextColor(iDataTextColor);
                cellLabel.setPadding(3, 3, 0, 0);

                if(iRowIndex<sData.length)
                {
                    cellLabel.setText(sData[iRowIndex][0]);
                    tableRow.setOnTouchListener(clEventHanlder);
                    tableRow.setOnKeyListener(clEventHanlder);
                    tableRow.setFocusable(true);
                }
                tableRow.addView(cellLabel);

                trHightligntParams = null;
                cellLabel.setGravity(Gravity.CENTER);
                try { trHightligntParams =  (TableRow.LayoutParams)cellLabel.getLayoutParams();} catch(Exception e){}
                if(trHightligntParams != null)
                    trHightligntParams.span = iColumnsCount;
            }
            else
            {
                tableRow.setBackgroundColor(iDataRowColor);
                for(short iColIndex=0;iColIndex<iColumnsCount;iColIndex++)
                {
                    if(iImageColIndex >=0 && iColIndex == iImageColIndex)
                    {
                        final ViewSwitcher viewSwitcher = new ViewSwitcher(context);

                        cellLabel = new CellView(context);
                        cellLabel.setBorderColor(Color.parseColor("#FF0000"));
                        cellLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,byDataTextSize);
                        cellLabel.setTextColor(iDataTextColor);
                        cellLabel.setPadding(3, 3, 0, 0);
                       cellLabel.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, iColumnWidths[iColIndex], getResources().getDisplayMetrics()) + iExtraWidth);
                        cellLabel.setHeight(iDataTextHeight);
                        cellLabel.setGravity(Gravity.CENTER_HORIZONTAL);
                        viewSwitcher.addView(cellLabel);

                        final ImageView imageView = new ImageView(context);
                        viewSwitcher.addView(imageView);
                        tableRow.addView(viewSwitcher);
                    }
                    else
                    {
                        cellLabel = new CellView(context);
                        cellLabel.setBorderColor(Color.parseColor("#FF0000"));
                        cellLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,byDataTextSize);
                        cellLabel.setTextColor(iDataTextColor);
                        cellLabel.setPadding(3, 3, 0, 0);

                        cellLabel.setWidth(iColumnWidths[iColIndex] + iExtraWidth);
                        //					cellLabel.setHeight(iDataTextHeight);
                        if(iRowIndex<sData.length)
                        {
                            cellLabel.setText(sData[iRowIndex][iColIndex]);
                            //tableRow.setOnClickListener(clEventHanlder);
                            tableRow.setOnTouchListener(clEventHanlder);
                            tableRow.setOnKeyListener(clEventHanlder);
                            tableRow.setFocusable(true);
                        }

                        if(isTotalRow)
                            cellLabel.setTypeface(Typeface.DEFAULT_BOLD);
                        tableRow.addView(cellLabel);
                        tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            }
            clBodyTable.addView(tableRow);
        }
        gestureDetector = new GestureDetector(clGestureListener);

        iSelectedRowIndex=0;
        iLastSelectedRowIndex=-1;
        isRowClicked=true;//selecting row for first time
//		selectRow(iSelectedRowIndex);
    }

    /*
    * Updates row with given data
    */
    public void updateRow(short iRowIndex,String[] sUpdateData)
    {
        updateRow(iRowIndex, sUpdateData,null);
    }

    /*
    * Updates row with given data and tag values
    */
    public void updateRow(short iRowIndex,String[] sUpdateData,Object[] objTags)
    {
        TableRow tblRow=getTableRow(iRowIndex);
        CellView clTextView = null;
        short iCount = iColumnsCount;
        short iIndex = 0;
        if(isShowCheckBoxes){
            iCount++;
            iIndex++;
        }
        if(isShowSNos){
            iCount++;
            iIndex++;
        }
        short index = iIndex;
        while(iIndex<iCount)
        {
            clTextView =((CellView)tblRow.getChildAt(iIndex));
            if(isShowSNos)
                index = (short)(iIndex-(iCount-iColumnsCount));
            else
                index = iIndex;

            clTextView.setText(sUpdateData[index]);
            if(objTags!=null)
                clTextView.setTag(objTags[index]);

            iIndex++;
        }
    }

    /*
    * Adds row with given data
    */
    public TableRow addRow(String[] sDataRow)
    {
        return addRow(sDataRow,null);
    }

    /*
    * Adds row with given data and tag values
    */
    public TableRow addRow(String[] sDataRow,Object[] objTags){
       return  addRow(sDataRow, objTags, false);
    }

    /*
    * Adds row with given data and tag values
    */
    public TableRow addRow(String[] sDataRow,Object[] objTags, boolean isReadOnly)
    {
        iRowsCount++;
        iLastRowId=(short)(iRowsCount-1);
        Context context = getContext();
        CellView cellLabel = null;
        TableRow newRow=new TableRow(context);
        newRow.setId(iLastRowId);

        CLEventListener clEventHanlder=new CLEventListener();
        newRow.setOnTouchListener(clEventHanlder);
        newRow.setOnKeyListener(clEventHanlder);
        newRow.setBackgroundColor(iDataRowColor);
        int iDataTextColor = this.iDataTextColor;
        if(isReadOnly)
        {
            newRow.setBackgroundColor(iReadOnlyRowColor);
            newRow.setTag(isReadOnly);
            newRow.setFocusable(false);
            iDataTextColor = iReadOnlyTxtColor;
        }
        else
            newRow.setFocusable(true);

        TableRow.LayoutParams params = null;
        if(isShowCheckBoxes)
        {
            params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            params.topMargin = 0;
            CheckBox checkBox=new CheckBox(context);
            checkBox.setLayoutParams(params);
            checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP,byDataTextSize);
            checkBox.setTextColor(iDataTextColor);
            checkBox.setPadding(3, 0, 0, 0);
//			checkBox.setWidth(bySnoWidth);
            checkBox.setText(" ");
            newRow.addView(checkBox);
        }
        if(isShowSNos)
        {
            params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.FILL_PARENT);
            params.topMargin = 0;
            cellLabel = new CellView(context);
            cellLabel.setLayoutParams(params);
            //cellLabel.setBorderColor(Color.parseColor("#D1D1D1"));
            cellLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,byDataTextSize);
            cellLabel.setTextColor(iDataTextColor);
            cellLabel.setPadding(3, 3, 0, 0);
            cellLabel.setHeight(iDataTextHeight);
            cellLabel.setWidth(bySnoWidth);
            cellLabel.setText(String.valueOf(++iSno));
            if(isReadOnly)
                cellLabel.setTypeface(Typeface.DEFAULT_BOLD);

            newRow.addView(cellLabel);
        }

        for(short iColIndex=0;iColIndex<iColumnsCount;iColIndex++)
        {
            cellLabel=new CellView(context);
            params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.FILL_PARENT);
            params.topMargin = 0;
            cellLabel.setLayoutParams(params);
            cellLabel.setWidth(iColumnWidths[iColIndex]);
            if(objTags!=null)
                cellLabel.setTag(objTags[iColIndex]);


            if(byDisplayTypes != null && byDisplayTypes.length > iColIndex && byDisplayTypes[iColIndex] == Constants.IFields.IDisplayStyle.Hidden )
            {
                cellLabel.setLayoutParams(new AbsListView.LayoutParams(0,0));
                cellLabel.setVisibility(GONE);

            }
            else
            {
               // cellLabel.setBorderColor(Color.parseColor("#D1D1D1"));
                cellLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,byDataTextSize);
                cellLabel.setTextColor(iDataTextColor);
                cellLabel.setPadding(3, 3, 3, 0);

                cellLabel.setHeight(iDataTextHeight);
//			    cellLabel.setGravity(Gravity.CENTER_VERTICAL);
                cellLabel.setWidth(iColumnWidths[iColIndex]);
                if(byDataTxtAligns != null && byDataTxtAligns.length == iColumnsCount)
                {
                    if(byDataTxtAligns[iColIndex] == ALIGN_HRIGHT)
                        cellLabel.setGravity(Gravity.RIGHT);
                    else if(byDataTxtAligns[iColIndex] == ALIGN_CENTER)
                        cellLabel.setGravity(Gravity.CENTER);
                    else
                        cellLabel.setGravity(Gravity.LEFT);
                }

                if(iColTxtColors != null && iColTxtColors.length == iColumnsCount)
                    cellLabel.setTextColor(iColTxtColors[iColIndex]);
      //          cellLabel.setWidth((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, iColumnWidths[iColIndex], getResources().getDisplayMetrics())+iExtraWidth);

            }
            if(sDataRow!=null)
            {
                if(sDataRow[iColIndex] != null)
                    cellLabel.setText(sDataRow[iColIndex]);
                else
                    cellLabel.setText("");
            }

            if(isReadOnly)
                cellLabel.setTypeface(Typeface.DEFAULT_BOLD);

            newRow.addView(cellLabel);
        }
     //   newRow.setGravity(Gravity.CENTER_HORIZONTAL);
        clBodyTable.addView(newRow);
        return newRow;

    }

    /*
    * Inserts row with given data and tag values
    */
    public TableRow insertRow(String[] sDataRow,Object[] objTags){
      return   insertRow(sDataRow, objTags, false);
    }

    /*
    * Inserts row with given data and tag values
    */
    public TableRow insertRow(String[] sDataRow,Object[] objTags, boolean isReadOnly)
    {
        Context context=getContext();
       TableRow addRow = new TableRow(context);
        if(isIgnoreRow)
        {
            isIgnoreRow=false;
           clBodyTable.removeViewAt(0);	//removing first empty row
              // this.removeViewAt(1);
            addRow(sDataRow,objTags, isReadOnly); //adding row
            //  gestureDetector = new GestureDetector(clGestureListener);
            iSelectedRowIndex=0;
            iLastSelectedRowIndex=-1;
            isRowClicked=true;//selecting row for first time
            selectRow(iSelectedRowIndex);
        }
        else {
            addRow(sDataRow, objTags, isReadOnly); //adding row
        }
        addRow.setGravity(Gravity.CENTER_HORIZONTAL);
        return addRow;
    }
    /*
    * Inserts row with given data
    */
    public TableRow insertRow(String[] sDataRow)
    {
         return insertRow(sDataRow, null);
    }
    /*
    * Sets given bitmap to image view
    */
    private void setBitmapToImageView(ViewSwitcher viewSwitcher,  Bitmap bitmap,ImageView imageView)
    {
        viewSwitcher.setDisplayedChild(1);

        imageView.setTag(bitmap);
        bitmap = Bitmap.createScaledBitmap(bitmap, 32, 32, false);
        imageView.setImageBitmap(bitmap);
    }

   /* *//*
    * Called when image loading failed
    *//*
    private void failToLoadImage(ViewSwitcher viewSwitcher, CellView cellView,  byte byStatus)
    {
        viewSwitcher.setDisplayedChild(0);
        if(byStatus == Constants.ImageLoadStatus.FILE_TOO_BIG)
            cellView.setText(com.focus.lib.R.string.img_too_big);
        else if(byStatus == Constants.ImageLoadStatus.FILE_NOT_FOUND)
            cellView.setText(com.focus.lib.R.string.img_file_not_found);
        else
            cellView.setText(com.focus.lib.R.string.img_load_failed);

        cellView.setTextColor(Color.RED);
    }*/

    /*
    * Sets given data to table
    */
    public void setTableData( String[][] sData, byte[] byRowProperties)
    {
        clearTable();// clear table data

        iRowsCount=(short)sData.length;
        if(iRowsCount > iMaxRows)
            iRowsCount = iMaxRows;
        iLastRowId=(short)(iRowsCount-1);
        iFirstRowId=0;
        short iRowIndex=0;
        short iColumnIndex=0;
        int iTotalCells=iRowsCount*iColumnsCount;

        String sFile = null;
        boolean isHighlightRow = false, isTotalRow = false;
        CellView cellView = null;
        TableRow.LayoutParams cellParams = null;
        TableRow tableRow = null;
        for(short iCellIndex=0;iCellIndex<iTotalCells;iCellIndex++)
        {
            iColumnIndex=iCellIndex;
            if(iCellIndex>iColumnsCount-1)
            {
                iRowIndex=(short) (iCellIndex/iColumnsCount);
                iColumnIndex=(short) (iCellIndex%iColumnsCount);
                tableRow = getTableRow(iRowIndex);
                isHighlightRow = (byRowProperties != null && iRowIndex < byRowProperties.length && byRowProperties[iRowIndex] == HIGHLIGHT_ROW);
                isTotalRow = (byRowProperties != null && iRowIndex < byRowProperties.length && byRowProperties[iRowIndex] == TOTAL_ROW);

                    cellView =  (CellView)getTableRow(iRowIndex).getChildAt(iColumnIndex);
                    if(cellView != null)
                    {
                        cellView.setText( sData[iRowIndex][iColumnIndex] );
                        if(isHighlightRow)
                        {
                            if(iColumnIndex == 0)
                            {
                                tableRow.removeViewsInLayout(1, iColumnsCount-1);
                                cellView.setGravity(Gravity.CENTER);
                                if(tableRow != null)
                                    tableRow.setBackgroundColor(iHighlightRowColor);

                                try{ cellParams = (TableRow.LayoutParams)cellView.getLayoutParams();} catch(Exception e){}
                                if(cellParams != null)
                                    cellParams.span = iColumnsCount;
                            }
                        }
                        else if(isTotalRow)
                            cellView.setTypeface(Typeface.DEFAULT_BOLD);
                    }
                }

            else// first row
            {
                if(iImageColIndex >=0 && iColumnIndex == iImageColIndex)
                {
                    /*clImageInfo = new CLImageInfo();
                    clImageInfo.format = Bitmap.CompressFormat.PNG;*/
                    if(iRowIndex<sData.length)
                    {
                        sFile = sData[iRowIndex][iColumnIndex];
                        final short iRIndex  = iRowIndex;
                        final short iCIndex  = iCellIndex;

                        final ViewSwitcher viewSwitcher = (ViewSwitcher)getTableRow(iRIndex).getChildAt( (iCIndex) );
                        if(sFile != null && sFile.trim().length()>0)
                        {
                           /* clImageInfo.id = iRIndex+sFile;
                            Bitmap bitmap = CLFocusController.getInstance().getProductImage(sFile, clImageInfo);
                            if(bitmap != null)
                            {
                                viewSwitcher.setDisplayedChild(1);

                                viewSwitcher.getChildAt(1).setTag(bitmap);
                                bitmap = Bitmap.createScaledBitmap(bitmap, 32, 32, false);
                                ( (ImageView)viewSwitcher.getChildAt(1) ).setImageBitmap(bitmap);
                            }
                            else
                            {
                                viewSwitcher.setDisplayedChild(0);
                                ( (CellView)viewSwitcher.getChildAt(0) ).setText(com.focus.lib.R.string.loading_image);
                                ( (CellView)viewSwitcher.getChildAt(0) ).setTextColor(Color.GREEN);
                            }*/
                        }

                       /* clImageInfo.listener = new CLImageCacheListener()
                        {
                            @Override
                            public void onSuccess(CLImageInfo imageInfo, Bitmap bitmap)
                            {
                                if(bitmap != null )
                                {
                                    viewSwitcher.setDisplayedChild(1);

                                    viewSwitcher.getChildAt(1).setTag(bitmap);
                                    bitmap = Bitmap.createScaledBitmap(bitmap, 32, 32, false);
                                    ( (ImageView)viewSwitcher.getChildAt(1) ).setImageBitmap(bitmap);
                                }
                                else
                                    onFailure(imageInfo);
                            }

                            @Override
                            public void onFailure(CLImageInfo imageInfo)
                            {
                                viewSwitcher.setDisplayedChild(0);
                                ( (CellView)viewSwitcher.getChildAt(0) ).setText(com.focus.lib.R.string.img_load_failed);
                                ( (CellView)viewSwitcher.getChildAt(0) ).setTextColor(Color.RED);
                            }
                        };*/
                    }
                }
                else
                {
                    cellView =  (CellView)getTableRow(iRowIndex).getChildAt(iColumnIndex);
                    if(cellView != null)
                    {
                        cellView.setText( sData[iRowIndex][iColumnIndex] );
                        if(isHighlightRow)
                        {
                            if(iColumnIndex == 0)
                            {
                                tableRow.removeViewsInLayout(1, iColumnsCount - 1);
                                if(tableRow != null)
                                    tableRow.setBackgroundColor(iHighlightRowColor);

                                try{ cellParams = (TableRow.LayoutParams)cellView.getLayoutParams();} catch(Exception e){}
                                if(cellParams != null)
                                    cellParams.span = iColumnsCount;
                            }
                        }
                        else if(isTotalRow)
                            cellView.setTypeface(Typeface.DEFAULT_BOLD);
                    }
                }
            }
        }

        CLEventListener clEventHanlder=new CLEventListener();
        for(short iRow=0;iRow<iMaxRows;iRow++)
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

    /*
    * Returns rows count
    */
    public short getRowsCount()
    {
        return iRowsCount;
    }

    /*
    * Returns columns count
    */
    public short getColumnsCount()
    {
        return iColumnsCount;
    }
    /*
    * Selects row of given index
    */
    public void selectRow(short iRowIndex)
    {
        TableRow tblRow = null;
        iSelectedRowIndex=iRowIndex;
        if(iSelectedRowIndex<iFirstRowId)
            iSelectedRowIndex=iFirstRowId;
        else if(iSelectedRowIndex>iLastRowId && iSelectedRowIndex!=0)
            iSelectedRowIndex=iLastRowId;
        else if(iLastSelectedRowIndex>=iFirstRowId)
        {
            tblRow = getTableRow(iLastSelectedRowIndex);// previous row
            if(tblRow != null)
            {
                int iCount = tblRow.getChildCount();
                if(iCount == 1 && iCount < iColumnsCount)
                    tblRow.setBackgroundColor(iHighlightRowColor);
                else if(isReadOnlyRow(tblRow))//is read only row
                    tblRow.setBackgroundColor(iReadOnlyRowColor);
                else
                    tblRow.setBackgroundColor(iDataRowColor);
            }
        }

        tblRow = getTableRow(iSelectedRowIndex);//selected
        if(tblRow != null)
        {
            tblRow.setBackgroundDrawable(selectedRowGradient);
            tblRow.requestFocus();
        }
        iLastSelectedRowIndex=iSelectedRowIndex;

    }

    public void selectRowbyId(short iRowId)
    {
        TableRow tblRow = null;
        iSelectedRowIndex=iRowId;
        if(iSelectedRowIndex<iFirstRowId)
            iSelectedRowIndex=iFirstRowId;
        else if(iSelectedRowIndex>iLastRowId && iSelectedRowIndex!=0)
            iSelectedRowIndex=iLastRowId;
        else if(iLastSelectedRowIndex>=iFirstRowId)
        {
            tblRow = getTableRow(iLastSelectedRowIndex);// previous row
            if(tblRow != null)
            {
                int iCount = tblRow.getChildCount();
                if(iCount == 1 && iCount < iColumnsCount)
                    tblRow.setBackgroundColor(iHighlightRowColor);
                else if(isReadOnlyRow(tblRow))//is read only row
                    tblRow.setBackgroundColor(iReadOnlyRowColor);
                else
                    tblRow.setBackgroundColor(iDataRowColor);
            }
        }

        tblRow = getTableRow(iSelectedRowIndex);//selected
        if(tblRow != null)
        {
            tblRow.setBackgroundDrawable(selectedRowGradient);
            tblRow.requestFocus();
        }
        iLastSelectedRowIndex=iSelectedRowIndex;

    }

    /*
    * Sets header label value of given header column index with value
    */
    public void setHeaderLabel(short iColumnIndex,String sValue)
    {
        ((CellView)((TableRow)clBodyTable.getChildAt(0)).getChildAt( (iColumnIndex) ) ).setText(sValue);
        sColumnNames[iColumnIndex]=sValue;
    }

    /*
    * Returns header label value of given header column index
    */
    public String getHeaderLabel(short iColumnIndex)
    {
        return ((CellView)((TableRow)this.getChildAt(0)).getChildAt( (iColumnIndex) ) ).getText().toString().trim().trim();
    }

    /*
    * Makes table empty
    */
    public void emptyTable()
    {
        emptyTable(true);
    }
    /*
    * Makes table empty
    */

    public void emptyTable(boolean isAddEmptyRow)
    {
        // removing data rows
        removeViews(1, iRowsCount);

        if(isShowSNos)
            iSno = 0;

        //Adding empty row
        if(isAddEmptyRow)
            addEmptyRow(clBodyTable);
    }

    /*
    * Adds empty row to table
    */
    private void addEmptyRow( TableLayout tableLayout)
    {
        isIgnoreRow = true;
        deletedRowIdList.clear();
        iRowsCount=0;
        iLastRowId=(short)(iRowsCount-1);
        iFirstRowId=0;
        iLastSelectedRowIndex=-1;

        Context context = getContext();
        //Adding Empty row
        TableRow ignoreRow=new TableRow(context);
        ignoreRow.setId(-1);

        CellView cellLabel = null;
        TableRow.LayoutParams params = null;
        if(isShowCheckBoxes)
        {
            params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            params.topMargin = 0;
            CheckBox checkBox=new CheckBox(context);
            checkBox.setLayoutParams(params);
            checkBox.setEnabled(false);
            checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP,byDataTextSize);
            checkBox.setTextColor(iDataTextColor);
            checkBox.setPadding(3, 0, 0, 0);
//			checkBox.setWidth(bySnoWidth);
            checkBox.setText(" ");
            ignoreRow.addView(checkBox);
        }

        if(isShowSNos)
        {
            params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.FILL_PARENT);
            params.topMargin = 0;
            cellLabel = new CellView(context);
            cellLabel.setLayoutParams(params);
            cellLabel.setBorderColor(Color.parseColor("#D1D1D1"));
            cellLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,byDataTextSize);
            cellLabel.setTextColor(iDataTextColor);
            cellLabel.setPadding(3, 0, 0, 0);
            cellLabel.setWidth(bySnoWidth);
            cellLabel.setText(" ");
            ignoreRow.addView(cellLabel);
        }

        for(short iColIndex=0;iColIndex<iColumnsCount;iColIndex++)
        {
            cellLabel=new CellView(context);
            params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.FILL_PARENT);
            params.topMargin = 0;
            cellLabel.setLayoutParams(params);
            if(byDisplayTypes != null && byDisplayTypes.length > iColIndex && byDisplayTypes[iColIndex] == Constants.IFields.IDisplayStyle.Hidden )
            {
                cellLabel.setLayoutParams(new AbsListView.LayoutParams(0,0));
                cellLabel.setVisibility(GONE);
            }
            else
            {
                cellLabel.setBorderColor(Color.parseColor("#D1D1D1"));
                cellLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,byDataTextSize);
                cellLabel.setTextColor(iDataTextColor);
                cellLabel.setPadding(3, 0, 0, 0);
                //cellLabel.isDrawRightBorder(false);
                cellLabel.setWidth(iColumnWidths[iColIndex]);
                cellLabel.setText(" ");
            }
            //ignoreRow.setGravity(Gravity.CENTER_HORIZONTAL);
            ignoreRow.addView(cellLabel);
        }
         tableLayout.addView(ignoreRow);

    }
    /*
    * Clears table
    */
    public void clearTable()
    {
        short iRowIndex=0;
        short iColumnIndex=0;
        CellView cellView = null;
        TableRow tableRow = null;
        int iChildCount = 0;
        TableRow.LayoutParams trParams = null;
        Context context = getContext();
        CLEventListener clEventHanlder=new CLEventListener();
        for(iRowIndex=0;iRowIndex<iMaxRows;iRowIndex++)
        {
            tableRow = getTableRow(iRowIndex);
            if(tableRow != null)
            {
                iChildCount = tableRow.getChildCount();
                if(iChildCount == 1 && iColumnsCount > 1)// in case highlight row
                {
                    tableRow.setBackgroundColor(iDataRowColor);
                    cellView = (CellView)tableRow.getChildAt(0);
                    if(cellView != null)// reset first cell
                    {
                        cellView.setText("");
                        if(byDataTxtAligns != null && byDataTxtAligns.length>0)
                        {
                            if(byDataTxtAligns[0] == ALIGN_HRIGHT)
                                cellView.setGravity(Gravity.RIGHT);
                            else if(byDataTxtAligns[0] == ALIGN_CENTER)
                                cellView.setGravity(Gravity.CENTER);
                            else
                                cellView.setGravity(Gravity.LEFT);
                        }
                        else
                            cellView.setGravity(Gravity.LEFT);

                        if(iColumnWidths != null && iColumnWidths.length > 0)
                            cellView.setWidth((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, iColumnWidths[0], getResources().getDisplayMetrics())+iExtraWidth);

                        try{ trParams = (TableRow.LayoutParams)cellView.getLayoutParams();}catch(Exception e){}
                        if(trParams != null)
                            trParams.span = 1;
                    }

                    // Adding remaining cells
                    for(iColumnIndex=1;iColumnIndex<iColumnsCount;iColumnIndex++)
                    {
                        cellView = new CellView(context);
               //         cellView.setBorderColor(Color.parseColor("#D1D1D1"));
                        cellView.setTextSize(TypedValue.COMPLEX_UNIT_SP, byDataTextSize);
                        cellView.setTextColor(iDataTextColor);
                        cellView.setPadding(3, 3, 0, 0);

                        cellView.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, iColumnWidths[iColumnIndex], getResources().getDisplayMetrics()) + iExtraWidth);
                        cellView.setText("");

                        tableRow.addView(cellView);

                        tableRow.setOnTouchListener(clEventHanlder);
                        tableRow.setOnKeyListener(clEventHanlder);
                        tableRow.setFocusable(true);
                    }
                }
                else
                {
                    for(iColumnIndex=0;iColumnIndex<iColumnsCount;iColumnIndex++)
                    {
                        cellView = getCellViewAt(iRowIndex, iColumnIndex);
                        if(cellView != null)
                        {
                            cellView.setText("");
                            cellView.setTypeface(Typeface.DEFAULT);
                        }
                    }
                }
            }
        }

        if(!deletedRowIdList.isEmpty())
        {
            int iDeletedId;
            View tr = null;
            for(short iIndex=0;iIndex<deletedRowIdList.size();iIndex++)
            {
                iDeletedId=Integer.parseInt(deletedRowIdList.get(iIndex));
                tr = getChildAt(iDeletedId + 1);
                if(tr != null) {
                    tr.setVisibility(VISIBLE);
                    tr.setBackgroundColor(iDataRowColor);
                }
            }
            deletedRowIdList.clear();
        }
    }

    /*
    * Sets cell view value of given row, column indexes with value
    */
    public void setValueAt(short iRowIndex,short iColumnIndex,String sValue,Object objTag)
    {
        CellView cellView = getCellViewAt(iRowIndex,iColumnIndex);
        if(cellView != null)
        {
            /*if(iColumnIndex==4)
                cellView.setVisibility(GONE);*/
            if(iColumnIndex==0)
                cellView.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
            else
                cellView.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
            cellView.setText(sValue);
            cellView.setTag(objTag);
//            TableRow.LayoutParams rowViewParms=new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.25f);
//           // rowViewParms.setMargins(5, 5, 5, 5);
//            cellView.setLayoutParams(rowViewParms);
           // cellView.setGravity(Gravity.CENTER);

        }
    }

    /*
    * Sets cell view value of given row, column indexes with value
    */
    public void setValueAt(short iRowIndex,short iColumnIndex,String sValue)
    {
        CellView cellView = getCellViewAt(iRowIndex,iColumnIndex);
        if(cellView != null)
            cellView.setText(sValue);
    }

    /*
    * Returns cell view value of given row and column indexes
    */
    public String getValueAt(short iRowIndex,short iColumnIndex)
    {
        CellView cellView = getCellViewAt(iRowIndex,iColumnIndex);
        if(cellView != null)
            return cellView.getText().toString().trim();

        return null;
    }
    /*
    * Returns cell tag value of given row and column indexes
    */
    public  Object getTagAt(short iRowIndex,short iColumnIndex)
    {
        CellView cellView = getCellViewAt(iRowIndex,iColumnIndex);
        if(cellView != null)
            return cellView.getTag();

        return null;
    }
    /*
    * Returns cell view of given row and column indexes
    */
    public CellView getCellViewAt(short iRowIndex,short iColumnIndex)
    {
        if(iColumnIndex >= 0) {
            if (isShowCheckBoxes)
                iColumnIndex += 1;//adding checkbox column
            if (isShowSNos)
                iColumnIndex += 1;//adding sno. column
        }

        return (CellView)getTableRow(iRowIndex).getChildAt(iColumnIndex);
    }

    /*public void setSelectedRowId(short iSelectedRowId)
    {
    	iSelectedRowIndex=iSelectedRowId;
    }*/
    /*
    * Returns selected row id
    */
    public short getSelectedRowId()
    {
        return iSelectedRowIndex;
    }

    /*
    * Sets focuses to given row and column indexes
    */
    public void setCellFocus(short iRowIndex,short iColumnIndex,boolean isFocusCell)
    {
        CellView selectedCell=getCellViewAt(iRowIndex,iColumnIndex);
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

    /*
    * Returns row of given index
    */
    public TableRow getTableRow(short iRowIndex) {
        return ((TableRow)clBodyTable.getChildAt(iRowIndex));
    }
    public TableRow getItemRowById(String sCurrProductCode) //
    {

        int iTotalRows = clBodyTable.getChildCount();
        TableRow clTableRow = null;
        TextView clProdTextView= null;
        String sProductCode = null;
        for(int iIndex = 0 ; iIndex < iTotalRows;iIndex++)
        {
            clTableRow = (TableRow) clBodyTable.getChildAt(iIndex);
            clProdTextView = (TextView) (clTableRow.getChildAt(0));

            sProductCode = String.valueOf(clProdTextView.getTag());
            if (sProductCode.equalsIgnoreCase(sCurrProductCode)) {
                clTableRow = (TableRow) clBodyTable.getChildAt(iIndex);
                break;
            }
            else
                clTableRow = null;
        }
        return clTableRow;
    }
    /*
    * Returns whether row of given index is read only or not
    */
    public boolean isReadOnlyRow(short iRowIndex) {
        return isReadOnlyRow(getTableRow(iRowIndex));
    }

    /*
    * Returns whether given row is read only or not
    */
    public boolean isReadOnlyRow(TableRow tr)
    {
        if(tr != null)
        {
            Object objTag = tr.getTag();
            if(objTag == null)
                return false;
            else if(objTag instanceof Boolean)
                return (Boolean) objTag;
        }
        return false;
    }

    /*
    * Returns selected row object
    */
    public TableRow getSelectedRow()
    {
        return getTableRow(iSelectedRowIndex);
    }
    /*
    * Returns id of last row
    */
    public short getLastRowId()
    {
        return iLastRowId;
    }

    /*
    * Returns whether row of given index is checked or not
    */
    public boolean isRowChecked(short iRowIndex)
    {
        if(isShowCheckBoxes)
        {
            TableRow tr = getTableRow(iRowIndex);
            if(tr != null)
            {
                View v = tr.getChildAt(0);
                if(v != null && v instanceof CheckBox)
                    return ((CheckBox)v).isChecked();
            }
            return false;
        }
        return false;
    }
    /*
    * Deletes rows from given start index to end index
    */
    public void deleteRow(short iStartIndex, short iEndIndex, boolean isAddEmptyRow)
    {
        if(iStartIndex>=iFirstRowId && iStartIndex <= iEndIndex && iEndIndex<=iLastRowId)
        {
            TableRow tr = null;
            short iSnoColIndex = (short)((isShowCheckBoxes)? 1 : 0);
            int iNofRows = (iEndIndex - iStartIndex)+1;
            for(short iIndex = iStartIndex; iIndex <= iEndIndex; iIndex++)
            {
                if(isShowSNos)
                {
                    tr = getTableRow(iStartIndex);
                    iSno = 0;
                    try {
                        iSno = Short.parseShort(((CellView)tr.getChildAt(iSnoColIndex)).getText().toString().trim());
                    }
                    catch (Exception e) { }
                }
                clBodyTable.removeViewAt(iStartIndex+1);
            }

            iSno -= iNofRows;
            iLastSelectedRowIndex = -1;
            if(iEndIndex!=iLastRowId)
            {
                short iId = (short)(iEndIndex-(iNofRows-1));
                selectRow(iId);
                for(short index=iEndIndex;index<iLastRowId;index++)
                {
//    				iId = (short)(index);
                    iId = (short)(index-(iNofRows-1));
                    tr  = getTableRow(iId);
                    if(tr != null)
                    {
                        tr.setId(iId);
                        if(isShowSNos)
                            ((CellView)tr.getChildAt(iSnoColIndex)).setText(String.valueOf(++iSno));
                    }
                }
            }

            if(iStartIndex == iFirstRowId && iEndIndex == iLastRowId)
            {
                iRowsCount=0;
                iLastRowId=0;
                if(isAddEmptyRow)
                    addEmptyRow(clBodyTable);
            }
            else
            {
                iRowsCount-=iNofRows;
                iLastRowId-= iNofRows;
            }
        }
       /* else
        {
            if(iRowsCount>0)
                CLUIUtil.showWarningMsg(com.focus.lib.R.string.select_row, getContext());
        }*/

    }

    /*
    * Deletes row of given index
    */
    public void deleteRowAt(short iRowIndex, boolean isAddEmptyRow)
    {
        if(iRowIndex>=iFirstRowId && iRowIndex<=iLastRowId)
        {
            TableRow tr = null;
            short iSnoColIndex = (short)((isShowCheckBoxes)? 1 : 0);
            if(isShowSNos)
            {
                tr = getTableRow(iRowIndex);
                iSno = 0;
                try {
                    iSno = Short.parseShort(((CellView)tr.getChildAt(iSnoColIndex)).getText().toString().trim());
                    iSno--;
                }
                catch (Exception e) { }
            }

            clBodyTable.removeViewAt(iRowIndex);

            iLastSelectedRowIndex = -1;
            if(iRowIndex!=iLastRowId)
            {
                selectRow(iRowIndex);
                for(short index=iRowIndex;index<iLastRowId;index++)
                {
                    tr  = getTableRow(index);
                    tr.setId(index);
                    if(isShowSNos)
                        ((CellView)tr.getChildAt(iSnoColIndex)).setText(String.valueOf(++iSno));
                }
            }

            if(iRowIndex == iFirstRowId && iRowIndex == iLastRowId)
            {
                iRowsCount=0;
                if(isAddEmptyRow)
                    addEmptyRow(clBodyTable);
            }
            else
                iRowsCount--;

            iLastRowId--;
        }
      /*  else
        {
            if(iRowsCount>0)
                CLUIUtil.showWarningMsg(com.focus.lib.R.string.select_row, getContext());
        }*/
    }

    /*
    * Hides row of given idex
    */
    public void hideRow(short iRowIndex)
    {
        getTableRow(iRowIndex).setVisibility(GONE);//hiding

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

        iLastSelectedRowIndex=(short) (iFirstRowId-1);
        isRowClicked=true;
       selectRow(iSelectedRowIndex);
    }

    @Override
    public void onScrollChanged(CLHorizontalScrollView scrollView, int x, int y, int oldx, int oldy) {
        if(scrollView.getId()== layout_hrztlHeaderView)
        {
            CLHorizontalScrollView clDataScrollView = (CLHorizontalScrollView)findViewById(layout_hrztlDataView);
            clDataScrollView.scrollTo(x, 0);
        }
        else if(scrollView.getId()== layout_hrztlDataView)
        {
            CLHorizontalScrollView clHeaderScrollView = (CLHorizontalScrollView)findViewById(layout_hrztlHeaderView);
            clHeaderScrollView.scrollTo(x, 0);
        }


    }

    private class CLEventListener implements OnKeyListener/*,OnClickListener*/,OnTouchListener
    {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event)
        {
            if( (event.getAction() == KeyEvent.ACTION_DOWN) || isRowClicked)
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
                  //  selectRow(iSelectedRowIndex);
                }
                else if(keyCode == KeyEvent.KEYCODE_DPAD_UP)
                {
                    iSelectedRowIndex--;
                    //selecting non deleted previous row
                    while(deletedRowIdList.contains(String.valueOf(iSelectedRowIndex)))
                    {
                        iSelectedRowIndex--;
                    }
                   // selectRow(iSelectedRowIndex);
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
            if(event.getAction() == MotionEvent.ACTION_DOWN)
            {
                isRowClicked=true;
                if(!isEditable)
                    selectRow((short)view.getId());
                //setSelectedRowId((short)view.getId());
                iSelectedRowIndex = (short)view.getId();
            }

            if(clEventRowHanlder!=null)
            {
                clEventRowHanlder.onTouch(view, event);
            }

            if(gestureDetector!=null)
                return gestureDetector.onTouchEvent(event);
            else
                return false;


            //else
            // super.onTouch(view, event) ;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        if (ev.getAction() == MotionEvent.ACTION_MOVE && rootScrollView != null)
            rootScrollView.requestDisallowInterceptTouchEvent(true);

        return super.dispatchTouchEvent(ev);
    }

    public  void fixedView(Context context)
    {
        CLHorizontalScrollView  hHeaderScrollView = new CLHorizontalScrollView(context);
        TableLayout clHeaderTbl=new TableLayout(context);
        hHeaderScrollView.setId(layout_hrztlHeaderView);
        clHeaderTbl.setPadding(5, 0, 5, 0);
        clHeaderTbl.setStretchAllColumns(true);
        hHeaderScrollView.setFillViewport(true);
        hHeaderScrollView.addView(clHeaderTbl,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //if(iBodyTblId==BODY_TABLE)
        hHeaderScrollView.setCallback(this);
		/*else
			hHeaderScrollView.setCallback(clVerifyView);*/
        hHeaderScrollView.setHorizontalScrollBarEnabled(false);
        addView(hHeaderScrollView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));



        CLHorizontalScrollView  hBodyScrollView = new CLHorizontalScrollView(context);

        hBodyScrollView.setId(layout_hrztlHeaderView);
        TableView clBodyTable=new TableView(context);
        clBodyTable.setPadding(5, 0, 5, 0);
        hBodyScrollView.setFillViewport(true);
        hBodyScrollView.addView(clBodyTable,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //if(iBodyTblId==BODY_TABLE)
        hBodyScrollView.setCallback(this);
		/*else
			hBodyScrollView.setCallback(clVerifyView);*/

        final ScrollView bodyVScrollView = new ScrollView(context);
        bodyVScrollView.addView(hBodyScrollView);
        bodyVScrollView.setPadding(5, 0, 5, 10);

        if(clBodyTable.getRowsCount() > 4)
        {
            int iHeight=0;

                if (CLUIUtil.getCurrentOrientation(context)== Configuration.ORIENTATION_LANDSCAPE)
                    iHeight = CLUIUtil.toDip(context, 300);
                else
                    iHeight = CLUIUtil.toDip(context, 400);

            bodyVScrollView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, iHeight));
        }
        else
            bodyVScrollView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        addView(bodyVScrollView);
    }


    public TableLayout getBodyTable()
    {
        return clBodyTable;
    }
}

