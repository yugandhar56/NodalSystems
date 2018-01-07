package com.nodel.nodalsytems.ui.view.comp.table;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.TextView;

public class CellView extends TextView
{
    /*private boolean isDrawLeftBorder;
    private boolean isDrawRightBorder;
    private boolean isDrawTopBorder;
    private boolean isDrawBottomBorder;*/
    private Paint borderPaint;  
    
    public CellView(Context context, AttributeSet attrs, int defStyle){
    	super(context, attrs, defStyle);
    	init();
    }
    
    public CellView(Context context, AttributeSet attrs){
    	super(context, attrs);
    	init();
    }
    
    public CellView(Context context){
    	super(context);
    	init();
    }
    
    private void init()
    {
    	borderPaint=new Paint();
    	borderPaint.setAntiAlias(true);
    	borderPaint.setColor(Color.parseColor("#EEEEEE"));
    	borderPaint.setStyle(Style.STROKE);
		borderPaint.setStrokeWidth( 1.0f );
		/*isDrawRightBorder=true;
		isDrawBottomBorder=true;*/	
    }
    
    public void setBorderColor(int borderColor){
    	borderPaint.setColor(borderColor);
    	invalidate();
    }
    
    public void setBorderStrokeWidth(float fStrokeWidth){
    	borderPaint.setStrokeWidth(fStrokeWidth);
    	invalidate();
    }
    
    /*public void isDrawLeftBorder(boolean isDrawLeftBorder){
    	this.isDrawLeftBorder = isDrawLeftBorder;
    	invalidate();
    }
    
    public void isDrawRightBorder(boolean isDrawRightBorder){
    	this.isDrawRightBorder=isDrawRightBorder;
    	invalidate();
    }
    
    public void isDrawBottomBorder(boolean isDrawBottomBorder){
    	this.isDrawBottomBorder=isDrawBottomBorder;
    	invalidate();
    }
    
   	public void isDrawTopBorder(boolean isDrawTopBorder){
    	this.isDrawTopBorder=isDrawTopBorder;
    	invalidate();
    }*/
    
   	@Override     
    protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		/*
//		int height=((TableRow)getParent()).getHeight();
		int height=getHeight();//(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getHeight(), getResources().getDisplayMetrics());
		int width=getMeasuredWidth();//(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getWidth(), getResources().getDisplayMetrics());
		
		if(isDrawBottomBorder)
		    canvas.drawLine(0, height, width , height, borderPaint); //bottom hLine
		    
		if(isDrawTopBorder)
			canvas.drawLine(0, 0, width , 0, borderPaint); //top hLine
			
		if(isDrawLeftBorder)
			canvas.drawLine(0, 0, 0, height, borderPaint);//left vertical line
		
		if(isDrawRightBorder)
		    canvas.drawLine(width, height, width, 0, borderPaint);//right vLine
*/		
		/*if(getLineCount()==1)
			setGravity(Gravity.CENTER_VERTICAL);*/	
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), borderPaint);//draws rectangle arround         
    }
}
