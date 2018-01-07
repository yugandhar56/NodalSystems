package com.nodel.nodalsytems.ui.view.comp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class CLHorizontalScrollView extends HorizontalScrollView 
{
	IHorizontalScrollViewListener clHScrollViewListener=null;
	
	public CLHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public CLHorizontalScrollView(Context context) {
		super(context);
	}

	public void setCallback(IHorizontalScrollViewListener clHScrollViewListener)
	{
		this.clHScrollViewListener = clHScrollViewListener;
	}

	@Override
    protected void onScrollChanged(int scrollX, int scrollY, int oldl, int oldt) 
    {
		super.onScrollChanged(scrollX, scrollY, oldl, oldt);
    	if (clHScrollViewListener != null)
    		clHScrollViewListener.onScrollChanged(this, scrollX, scrollY, oldl, oldt);
    }


	
	
}
