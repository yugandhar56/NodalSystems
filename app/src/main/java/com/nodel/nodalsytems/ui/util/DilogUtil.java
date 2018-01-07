package com.nodel.nodalsytems.ui.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.nodel.nodalsytems.R;


/**
 * Created by yugandhar on 6/18/2017.
 */

public class DilogUtil {

    private AlertDialog dialog;
//    private Activity activity;
    private Context context;
    public DilogUtil(Context context) {
        this.context = context;
    }

    /**
     * Method to show dialog
     *
     * @param message Dialog message
     */
    public void showDialog(String message, Context context) {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle(context.getResources().getString(R.string.app_name));
        dialog.setMessage(message);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissDialog();
            }
        });
        if (!dialog.isShowing())
            dialog.show();
    }

    /**
     * Method to dismiss dialog
     */
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
