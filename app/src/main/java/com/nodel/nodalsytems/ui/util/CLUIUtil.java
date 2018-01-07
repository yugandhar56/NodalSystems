package com.nodel.nodalsytems.ui.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;



import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/*
* Utility class for UI
*/
public class CLUIUtil
{
	/*
	* Converts the given pixels into dips(density independent pixels)
	*/
	public static short toDip(Context context,int pixels) 
	{
		return (short) (pixels * context.getResources().getDisplayMetrics().density + 0.5f);
		//return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, context.getResources().getDisplayMetrics());
	}
	/*
	* Returns width of device
	*/
	public static int getDeviceWidth(Context context)
	{

//		System.out.println("Device width is:"+((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth());
		return((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
	}
	/*
	* Returns height of device
	*/
	public static int getDeviceHeight(Context context)
	{
		return((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
	}

	/*
	* Converts given string to bitmap
	*/
	public static Bitmap StringToBitmap(String encodedString)
	{
		try{
			byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
			return  BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
		}catch(Exception e){
			e.getMessage();
			return null;
		}
	}
	/*
	* Converts given bitmap to string
	*/
	public static String BitmapToString(Bitmap bitmap)
	{
		byte[] bytes = BitmapToBytes(bitmap);
		return BytesToString(bytes);
	}
	/*
	* Converts given bitmap to bytes
	*/
	public static byte[] BitmapToBytes(Bitmap bitmap)
	{
		ByteArrayOutputStream baos=new  ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
		return baos.toByteArray();
	}
	/*
	* Converts given bitmap to bytes
	*/
	public static byte[] BitmapToBytes(Bitmap bitmap, Bitmap.CompressFormat format)
	{
		ByteArrayOutputStream baos=new  ByteArrayOutputStream();
		bitmap.compress(format,100, baos);
		return baos.toByteArray();
	}

	/*
	* Converts given bytes to Base64 string
	*/
	public static String BytesToString(byte[] bytes)
	{
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}
	/*
	* Returns unique device id
	*/
	public static String getUniqueDeviceId(Context clContext)
	{
		String sReturn=null;
		sReturn=getAndroidId(clContext);

		if(sReturn!=null && sReturn.trim().length()>0)
		{
			if(isNumeric(sReturn) && Integer.parseInt(sReturn)>0)
				return sReturn;
			else
				return sReturn;
		}
		else
		{
			sReturn=getIMEINumber(clContext);
			if(sReturn!=null && sReturn.trim().length()>0)
			{
				if(isNumeric(sReturn) && Integer.parseInt(sReturn)>0)
					return sReturn;
				else
					return sReturn;
			}
			else
			{
				sReturn=getSerialNo(clContext);
				if(sReturn!=null && sReturn.trim().length()>0)
				{
					if(isNumeric(sReturn) && Integer.parseInt(sReturn)>0)
						return sReturn;
					else
						return sReturn;
				}
			}
		}
		return sReturn;
	}

	/*
	* Returns device IMEI number
	*/
	public static String getIMEINumber(Context clContext)
	{
		TelephonyManager   telephonyManager  =  ( TelephonyManager)clContext.getSystemService( Context.TELEPHONY_SERVICE );				    
		String sIMEI = telephonyManager.getDeviceId();    		
		return sIMEI;		
	}	

	/*
	* Returns android id
	*/
	public static String getAndroidId(Context clContext)
	{
		return Settings.Secure.getString(clContext.getContentResolver(),Settings.Secure.ANDROID_ID);	
	}

	/*
	* Returns device serial number
	*/
	public static String getSerialNo(Context clContext)
	{
		String serialnum = null;      
		try
		{         
			Class<?> c = Class.forName("android.os.SystemProperties");        	           	      
			Method get = c.getMethod("get", String.class, String.class );        
			serialnum = (String)(   get.invoke(c, "ro.serialno", "" )  );

		} 
		catch (Exception ignored)   	
		{}

		if(serialnum == null || serialnum.trim().length()==0)
		{
			try 
			{
				Class<?> myclass = Class.forName( "android.os.SystemProperties" );
				Method[] methods = myclass.getMethods();
				Object[] params = new Object[] { new String( "ro.serialno" ) , new String("") };        	
				serialnum = (String)(methods[2].invoke( myclass, params ));        	

			}
			catch (Exception ignored) 
			{}
		}

		return serialnum;
	}

	/*
	* Returns whether given string numeric or not
	*/
	public static boolean isNumeric(String str)
	{
		//return str.matches("-?\\d+(.\\d+)?");
		return str.matches("[-+]?\\d+(\\.\\d+)?");
	}
	/*
	* Returns whether current device is tablet or not
	*/
	public static boolean isTablet(Context context) 
	{
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
/*
	*//*
	* Shows server error dialog
	*//**//*
	public static void showServerErrorDlg(final String sMsg,final Context context,final boolean isCloseAppOnClickOk) {
		showServerErrorDlg(sMsg, context, isCloseAppOnClickOk, true);
	}
*/


	/*
	* Shows warning message dialog with given message
	*/
	/*public static void showWarningMsg(String sMsg,Context context) {
		showMsg(context,sMsg,false,R.string.warning,null,false,true);
	}

	*//*
	* Shows warning message dialog with given message
	*//*
	public static void showWarningMsg(String sMsg,Context context, boolean isCancelable) {
		showMsg(context,sMsg,false,R.string.warning,null,false,isCancelable);
	}

	*//*
	* Shows warning message dialog with given message string id
	*//*
	public static void showWarningMsg(int iMsgId,Context context) {
		showMsg(context,context.getString(iMsgId), false,R.string.warning,null,false,true);
	}

	*//*
	* Shows warning message dialog with given message string id
	*//*
	public static void showWarningMsg(int iMsgId,Context context, boolean isCancelable) {
		showMsg(context,context.getString(iMsgId), false,R.string.warning,null,false,isCancelable);
	}

	*//*
	* Shows message dialog with given message and title string id
	*//*
	public static void showMsg(Context context,String sMsg,boolean isCloseApp,int iTitleId,OnClickListener onClickListener,final boolean isCloseAppOnClickOk){
		showMsg(context, sMsg, isCloseApp, iTitleId, onClickListener, isCloseAppOnClickOk,true);
	}

	*//*
	* Shows message dialog with given message and title string id
	*//*
	public static void showMsg(final Context context,final String sMsg,final boolean isCloseApp,final int iTitleId,final OnClickListener onClickListener,
			final boolean isCloseAppOnClickOk,final boolean isCancelable)
	{
		try
		{
            Handler mainHandler = new Handler(context.getMainLooper());
            Runnable myRunnable = new Runnable()
            {
                @Override
                public void run() {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
                    alertbox.setTitle(iTitleId);
                    alertbox.setIcon(android.R.drawable.ic_dialog_alert);
                    alertbox.setMessage(sMsg);
                    alertbox.setCancelable(isCancelable);
                    if(onClickListener == null)
                    {
                        alertbox.setNeutralButton(R.string.ok, new OnClickListener(){
                            public void onClick(DialogInterface arg0, int arg1)
                            {
                                if(isCloseAppOnClickOk)
                                    System.exit(1);
                            }
                        });
                    }
                    else
                        alertbox.setNeutralButton(R.string.ok, onClickListener);

                    alertbox.show();
               //     if(isCloseApp)
                 //       System.exit(1);
                }
            };
            mainHandler.post(myRunnable);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	*//*
	* Initializes Application directory with given name
	*//*
	public static String initAppDir(String sDir){
		return  initAppDir(sDir,false);
	}
	*//*
	* Initializes Application directory with given name
	*//*
	public static String initAppDir(String sDir, boolean isReturnDirName)
	{
		String sDirName = Constants.FOCUS_APP_DIR+File.separator+CLMainActivity.getCompanyCode();
		File appDir = new File(Environment.getExternalStorageDirectory().toString(), sDirName);
		if(!appDir.exists())
			appDir.mkdirs();

		File folder = new File(appDir.toString(), sDir);
		sDirName += File.separator+sDir;
		if(folder.exists())
		{
			final File[] clFies = folder.listFiles();
			if(clFies.length>5)
			{
				if (clFies != null && clFies.length > 1) 
				{
					Arrays.sort(clFies, new Comparator<File>() {
						@Override
						public int compare(File object1, File object2) {
							return (int) ((object1.lastModified() < object2.lastModified()) ? object1.lastModified(): object2.lastModified());
						}
					});
				}

				for(byte b=(byte)(clFies.length-1);b>5;b--) {
					clFies[b].delete(); 
				}
			}
		}
		else 
			folder.mkdirs();

		if(isReturnDirName)
			return sDirName;
		else
			return folder.toString();
	}
	*//*
	* Shows given pdf file in pdf viewer application
	*//*
	 public static void showPdfFile(Context clContext, File file)
     {
         Intent intent = new Intent();
         intent.setAction(Intent.ACTION_VIEW);
         Uri uri = Uri.fromFile(file);
         intent.setDataAndType(uri, "application/pdf");
		 if(intent.resolveActivity(clContext.getPackageManager()) != null)
         	clContext.startActivity(intent);
     }
	*//*
    * Shows given image file in image viewer/gallery application
    *//*
	 public static void showImageFile(Context clContext, File file)
     {
         Intent intent = new Intent(Intent.ACTION_VIEW);
         intent.setDataAndType(Uri.fromFile(file), "image*//*");
		 if(intent.resolveActivity(clContext.getPackageManager()) != null)
         	clContext.startActivity(intent);
     }
	*//*
	* Packs given bit in integer
	*//*
	 public static int PackBit(int value, byte bitNo)
	 {
		 value = value | (1 << bitNo);
		 return value;
	 }*/

	/*
	* Returns whether given bit is packed in given value or not
	*/
	public static boolean CheckBit(int value, byte bitNo)
	 {
		 int ivalue = value;
		 return (ivalue & (1 << bitNo)) > 0;
	 }

	/*
	* Expands given view with animation
	*/
	  public static void expand(final View view)
	  {
		  view.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		  final int targetHeight = view.getMeasuredHeight();
		  view.getLayoutParams().height = 0;
		  view.setVisibility(View.VISIBLE);
		  Animation anim = new Animation()
		  {
			  @Override
			  protected void applyTransformation(float interpolatedTime, android.view.animation.Transformation t) 
			  {
				  view.getLayoutParams().height = (interpolatedTime == 1) ? LayoutParams.WRAP_CONTENT : (int)(targetHeight * interpolatedTime);
				  view.requestLayout();
			  }
			  @Override
			  public boolean willChangeBounds() {
				  return true;
			  }
		  };
		  anim.setDuration((int)(targetHeight / view.getContext().getResources().getDisplayMetrics().density));// 1dp/ms
//		  anim.setInterpolator(new AccelerateInterpolator(0.5f));
		  view.startAnimation(anim);
	  }

	/*
	* Collapses given view with animation
	*/
	  public static void collapse(final View view) 
	  {
		  final int initialHeight = view.getMeasuredHeight();
		  Animation anim = new Animation()
		  {
			  @Override
			  protected void applyTransformation(float interpolatedTime, android.view.animation.Transformation t) {
				  if(interpolatedTime == 1)
					  view.setVisibility(View.GONE);
				  else{
					  view.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
					  view.requestLayout();
				  }
			  }
			  @Override
			  public boolean willChangeBounds() {
				  return true;
			  }
		  };
		  anim.setDuration((int)(initialHeight / view.getContext().getResources().getDisplayMetrics().density));// 1dp/ms
//		  anim.setInterpolator(new AccelerateInterpolator(0.5f));
		  view.startAnimation(anim);
	  }

	/*
	* Returns device's current orientation
	* */
	  public static int getCurrentOrientation(Context clContext)
	  {
		  int iOrientation=clContext.getResources().getConfiguration().orientation;

		  if(iOrientation==Configuration.ORIENTATION_UNDEFINED)
		  {
			  Display clDisplay = ((Activity)clContext).getWindowManager().getDefaultDisplay();
			  int iWidth,iHeight;

			  //			int sdk = android.os.Build.VERSION.SDK_INT;
			  //				Point size = new Point();
			  iWidth = clDisplay.getWidth();
			  iHeight=clDisplay.getHeight();

			  if(iWidth==iHeight)
				  iOrientation = Configuration.ORIENTATION_SQUARE;

			  if(iWidth < iHeight)
				  iOrientation = Configuration.ORIENTATION_PORTRAIT;
		  }

		  return iOrientation;//Configuration.ORIENTATION_LANDSCAPE; // Portrait Mode
	  }

	/*
	* Hides keyboard for given view
	* */
	  public static void hideKeyboard(View v) {
		  hideKeyboard(null, v);
	  }

	/*
	* Hides keyboard in given activity
	*/
	  public static void hideKeyboard(Activity activity){
		  hideKeyboard(activity,null);
	  }
	/*
    * Hides keyboard in given activity/view
    */
	  public static void hideKeyboard(Activity activity, View v) 
	  {
		  if(activity != null)
			  activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		  else if(v != null)
		  {
			  try {
				  InputMethodManager imManager = (InputMethodManager)v.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
				  imManager.hideSoftInputFromWindow(v.getWindowToken(), 0); 
			  }
			  catch (Exception e) {
				  e.printStackTrace();
			  }
		  }
	  }

	public static void showKeyboard(Activity activity, View v)
	{
		if(activity != null)
			activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		else if(v != null)
		{
			try {
				InputMethodManager imManager = (InputMethodManager)v.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
				imManager.showSoftInputFromInputMethod(v.getWindowToken(), 0);
				//imManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	* Writes given data to a file with given name in given directory
	*/
	  public static void writeBytesToFile(Context context, String sDir, String sFileName, byte[] byData)
	  {
		  BufferedOutputStream bos = null;
		  try {
			  File file = new File(context.getDir(sDir, Context.MODE_PRIVATE),sFileName);
			  if(file.exists())
				  file.delete();
			  FileOutputStream fos = new FileOutputStream(file);
			  bos = new BufferedOutputStream(fos); 
			  bos.write(byData);
		  }
		  catch(Exception e){}
		  finally {
			  if(bos != null) {
				  try  {
					  //flush and close the BufferedOutputStream
					  bos.flush();
					  bos.close();
				  } catch(Exception e){}
			  }
		  }
	  }

	/*
	* Reads bytes from a file with given name
	*/
	  public static byte[] readBytesFromFile(Context context, String sDir, String sFileName) throws IOException
	  {
		  File directory = context.getDir(sDir, Context.MODE_PRIVATE);
		  if(directory.isDirectory())
		  {
			  File file = new File(directory,sFileName);
			  if(file.exists())
			  {
				  InputStream is = null;
				  try
				  {
					  is = new FileInputStream(file);

					  // Get the size of the file
					  long length = file.length();

					  // You cannot create an array using a long type.
					  // It needs to be an int type.
					  // Before converting to an int type, check
					  // to ensure that file is not larger than Integer.MAX_VALUE.
					  if (length > Integer.MAX_VALUE) 
						  throw new IOException("Could not completely read file " + file.getName() + " as it is too long (" + length + " bytes, max supported " + Integer.MAX_VALUE + ")");

					  // Create the byte array to hold the data
					  byte[] bytes = new byte[(int)length];

					  // Read in the bytes
					  int offset = 0;
					  int numRead = 0;
					  while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0)
						  offset += numRead;

					  // Ensure all the bytes have been read in
					  if(offset < bytes.length) 
						  throw new IOException("Could not completely read file " + file.getName());

					  return bytes;
				  }
				  finally
				  {
					  try {
						  is.close();// Close the input stream
					  } catch (IOException e) {}
				  }
			  }
		  }
		  return null;
	  }

	/*
	* Deletes given file or directory
	* */
	  public static void deleteFileOrDir(Context context,File fileOrDir) 
	  {
		  if (fileOrDir.isDirectory()) {
			  for (File child : fileOrDir.listFiles())
				  deleteFileOrDir(context,child);
		  }
		  fileOrDir.delete();
	  }

	/*
	* Finishes given activity and shows animation
	*/
	/*  public static void finishActivity(Activity activity)
	  {
		  if(activity != null)
		  {
			  activity.finish();
			  activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		  }
	  }
*/
	/*
    * Starts given activity and shows animation
    */
	 /* public static void startActivity(Activity activity, Intent intent)
	  {
		  if(activity != null && intent != null)
		  {
			  activity.startActivity(intent);
			  activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		  }
	  }*/
	  
	 /* public static String readAssetFile(Context context, String sFileName)
	  {
		  InputStream input = null;
		  ByteArrayOutputStream bout = null;
		  String sValue = null;
		  try {
			  input = context.getAssets().open(sFileName);
			  if(input.available() > 0)
			  {
				  bout = new ByteArrayOutputStream();
				  byte[] buffer = new byte[128];
				  while ( -1 != input.read(buffer) )
					  bout.write(buffer); 
                  bout.flush();
				  sValue = new String(bout.toByteArray());
			  }
		  }
		  catch(Exception e) {
			  CLLogger.logErrorEvent(e.getMessage());
		  }
		  finally
		  {
			  if(input != null) {
				  try{  input.close(); }catch(Exception e){}
			  }
			  if(bout != null) {
				  try{  bout.close(); }catch(Exception e){}
			  }
		  }
		  return sValue;
	  }*/
	  
	  public static String readAssetFile(Context context, String sFileName)
	  {
		  StringBuilder sBuilder = new StringBuilder();
		  BufferedReader reader = null;
		  try {
		      reader = new BufferedReader(new InputStreamReader(context.getAssets().open(sFileName), "UTF-8")); 
		      // do reading, usually loop until end of file reading 
		      String mLine = reader.readLine();
		      while (mLine != null) {
		    	  sBuilder.append(mLine);
		         mLine = reader.readLine(); 
		      }
		  } catch (IOException e) {
		      //log the exception
		  } finally {
		      if (reader != null) {
		           try {
		               reader.close();
		           } catch (IOException e) {
		               //log the exception
		           }
		      }
		  }
		  return sBuilder.toString();
	  }
	  /*
	  * Shows slide down animation to given view
	  */
	 /* public static void slideDown(Context ctx, View v)
	  {
		  Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
		  if(a != null)
		  {
			  a.reset();
			  if(v != null)
			  {
				  v.clearAnimation();
				  v.startAnimation(a);
			  }
		  }
	  }*/
	/*
    * Shows slide up animation to given view
    */
	/*  public static void slideUp(Context ctx, View v)
	  {
		  Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
		  if(a != null)
		  {
			  a.reset();
			  if(v != null)
			  {
				  v.clearAnimation();
				  v.startAnimation(a);
			  }
		  }
	  }
*/
    public static  String getDiffDaysStr(long lDateTime)
    {
        if(lDateTime>0)
        {
            Calendar clModifiedDate = convertLongToGC(lDateTime);
            Calendar clCalendar = convertLongToGC(getCurrentDateTimeToInt());
            long lDiffinmillisecs = clCalendar.getTimeInMillis() - clModifiedDate.getTimeInMillis();
            long iDays = 0;
            iDays = lDiffinmillisecs / (24 * 60 * 60 * 1000);
            if (iDays > 0)
                return " (" + iDays + " day" + (iDays > 1 ? "s" : "") + " ago )";
            iDays = lDiffinmillisecs / (60 * 60 * 1000);
            if (iDays > 0)
                return " (" + iDays + " hour" + (iDays > 1 ? "s" : "") + " ago )";
            iDays = lDiffinmillisecs / (60 * 1000);
            if (iDays > 0)
                return " (" + iDays + " Minute" + (iDays > 1 ? "s" : "") + " ago )";
            iDays=lDiffinmillisecs/1000;
            if(iDays>0)
                return " ("+iDays+" Seconds ago )";
        }
        return "";
    }

    public static long getCurrentDateTimeToInt()
    {
        return getCurrentDateTimeToInt(false);
    }
    public static long getCurrentDateTimeToInt(boolean isExlcudeTime)
    {
        return getCurrentDateTimeToInt(isExlcudeTime,false);
    }
    /**
     * Author:  M Vinay Kumar
     * Create date: 19 Apr 2011
     * This gets the Current Date Time in long which is to saved in Database bigint
     *
     * @param isExlcudeTime : To Exclude Time and get it as 0 for Hours, Minutes and Seconds
     * @param bIsEndDate : To get End date time of today, ie hour as 23, min as 59 and sec as 59
     * @return
     */
    public static long getCurrentDateTimeToInt(boolean isExlcudeTime,boolean bIsEndDate)
    {

        return getCurrentDateTimeToInt(null,isExlcudeTime,bIsEndDate);

    }
    /**
     * Author:  M Vinay Kumar
     * Create date: 23 Jul 2012
     * This gets the Current Date Time in long which is to saved in Database bigint
     *
     * @param isExlcudeTime : To Exclude Time and get it as 0 for Hours, Minutes and Seconds
     * @param bIsEndDate : To get End date time of today, ie hour as 23, min as 59 and sec as 59
     * @return
     */
    public static long getCurrentDateTimeToInt(TimeZone tzTimeZone,boolean isExlcudeTime,boolean bIsEndDate)
    {
        GregorianCalendar gcCurrDateTime = getCurrentDateTimeToGC(tzTimeZone);
        if(isExlcudeTime && !bIsEndDate)
            return getIntDateTime(gcCurrDateTime.get(Calendar.DATE), gcCurrDateTime.get(Calendar.MONTH) + 1, gcCurrDateTime.get(Calendar.YEAR), 0, 0,0);
        else if(!isExlcudeTime && !bIsEndDate)
            return getIntDateTime(gcCurrDateTime.get(Calendar.DATE), gcCurrDateTime.get(Calendar.MONTH) + 1, gcCurrDateTime.get(Calendar.YEAR), gcCurrDateTime.get(Calendar.HOUR_OF_DAY), gcCurrDateTime.get(Calendar.MINUTE), gcCurrDateTime.get(Calendar.SECOND));
        else
            return getIntDateTime(gcCurrDateTime.get(Calendar.DATE), gcCurrDateTime.get(Calendar.MONTH) + 1, gcCurrDateTime.get(Calendar.YEAR), 23, 59, 59);

    }

    public static long getIntDateTime(int iDay, int iMonth, int iYear, int iHour, int iMin, int iSec)
    {
        /*if (iDay>31)
            throw new CLBusinessRuleException(IMessageCodes.ERR_INCORRECT_DAY);
        if (iMonth>12)
            throw new CLBusinessRuleException(IMessageCodes.ERR_INCORRECT_MONTH);
        if (iHour>23)
            throw new CLBusinessRuleException(IMessageCodes.ERR_INCORRECT_HOURS);
        if (iMin>59)
            throw new CLBusinessRuleException(IMessageCodes.ERR_INCORRECT_MINUTES);
        if (iSec>59)
            throw new CLBusinessRuleException(IMessageCodes.ERR_INCORRECT_SECONDS);*/
        //                         11111111 00000 000000 000000
        // 11111011101 00000001  00011111 00000 000000 000000

        // 111111111111111 11111111  00000000 00000 000000 000000 =year-month
        // 000000000000000 11111111  11111111 00000 000000 000000 =month-day
        // 000000000000000 11111111  00000000 00000 000000 000000 =month
        // 000000000000000 00000000  11111111 00000 000000 000000 =day
        // 111111111111111 00000000  00000000 00000 000000 000000 =year



        //111111111111111111111110000000000000000000000000

        //1111 11110 0000 000000 000000
        //  11111111111 11111111 00000 0000 000000 000000
        //1111111111111111111111100000000000000000
        return ((iYear * 8589934592l) | (iMonth * 33554432) | (iDay * 131072) | (iHour * 4096) | (iMin * 64) | iSec);

        //sec= 6 bits
        // min=6 bits
        // hrs=5 bits
        // day= 8 bits
        // month= 8 bits
        // year=15 bits


    }
    public static GregorianCalendar getCurrentDateTimeToGC(TimeZone tzTimeZone)
    {

        if(tzTimeZone==null)
            tzTimeZone= TimeZone.getDefault();
        GregorianCalendar gcCurrDateTime = new GregorianCalendar(tzTimeZone);
        return gcCurrDateTime;
    }
    public static Calendar convertLongToGC(long lDate)
    {
        Calendar clDate = null;
        if (lDate > 0)
        {
            int iYear = getYearFromLong(lDate); // 15 bits
            int iMonth = getMonthFromLong(lDate);// 8 bits
            int iDay = getDayFromLong(lDate);//8 bits

            int iHour = getHourFromLong(lDate); //5 bits
            int iMin = getMinFromLong(lDate); //6 bits
            int iSec = getSecFromLong(lDate);//6 bits


            clDate=Calendar.getInstance();
            clDate.set(Calendar.DAY_OF_MONTH,iDay);
            clDate.set(Calendar.MONTH,(iMonth-1));// as month index starts from 0
            clDate.set(Calendar.YEAR,iYear);
            clDate.set(Calendar.HOUR_OF_DAY,iHour);
            clDate.set(Calendar.MINUTE,iMin);
            clDate.set(Calendar.SECOND,iSec);
        }
        return clDate;
    }
    public static int getYearFromLong(long lDate)
    {
        return (int) ((lDate & 0xfffe00000000l) / 8589934592l);
    }

    public static int getMonthFromLong(long iDate)
    {
        return (int) ((iDate & 0x1fe000000l) / 33554432l);
    }

    public static int getDayFromLong(long iDate)
    {
        return (int) ((iDate & 0x1fe0000) / 131072);
    }

    public static int getHourFromLong(long iDate)
    {
        return (int) ((iDate & 0x1f000) / 4096);
    }

    public static int getMinFromLong(long iDate)
    {
        return (int) ((iDate & 0xfc0) / 64);
    }

    public static int getSecFromLong(long iDate)
    {
        return (int) (iDate & 0x3f);
    }

	public static  boolean isServiceRunning(Context clContext,Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) clContext.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}



}
