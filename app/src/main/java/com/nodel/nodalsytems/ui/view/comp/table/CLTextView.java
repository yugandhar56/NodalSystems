package com.nodel.nodalsytems.ui.view.comp.table;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class CLTextView extends TextView
{
    private int iBorderColor=Color.BLACK;
      
    public CLTextView(Context context, AttributeSet attrs, int defStyle)
    {
	super(context, attrs, defStyle);     
    }
    
    public CLTextView(Context context, AttributeSet attrs) 
    {
	super(context, attrs);     
    }
    
    public CLTextView(Context context) 
    {
	super(context);     
    }
    
    @Override     
    protected void onDraw(Canvas canvas) 
    {
	super.onDraw(canvas);
	
	Paint paint=new Paint();
	paint.setColor(iBorderColor);
	
	/*Rect rect = new Rect();
	paint.setStyle(Paint.Style.STROKE);
	paint.setStrokeWidth(2);
	getLocalVisibleRect(rect);
	canvas.drawRect(rect, paint);*/
	
	//int height=((TableRow)getParent()).getHeight();
	int height=getHeight();//(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getHeight(), getResources().getDisplayMetrics());
	int width=getWidth();//(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getWidth(), getResources().getDisplayMetrics());
	
	canvas.drawLine(0, height-1, width , height-1, paint); //bottom hLine   
	//canvas.drawLine(0, 0, width , 0, paint); //top hLine
	//canvas.drawLine(0, 0, 0, height, paint);//left vLine
	canvas.drawLine(width-1, height, width-1, 0, paint);//right vLine
    }
    
    
   /* @Override 

     public void onDraw(Canvas canvas) {  

         canvas.drawColor(mItemBackgroundColour);    // set the background colour for the item  

         // draw the horizontal line at the bottom of the item  

         canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), mLinePaint);  

         // draw some text to the left of the margin  

         canvas.drawText(" *)", 5, getMeasuredHeight() - 4, mMarginPaint);  

         // Draw the vertical margins  

         canvas.drawLine(2, 0, 2, getMeasuredHeight(), mMarginPaint);  

         canvas.drawLine(mBorderMarginOffset, 0, mBorderMarginOffset, getMeasuredHeight(), mMarginPaint);  

         // Move the text across from the margin and up 10 from the line  

         canvas.save();  

        canvas.translate(mBorderMarginOffset, 10);  

         // Use the TextView to render the text.  

         super.onDraw(canvas);  

         canvas.restore();  

     } 
*/
    
    public void setBorderColor(int borderColor) 
    {
        this.iBorderColor = borderColor;
    }

}
