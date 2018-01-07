package com.nodel.nodalsytems.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nodel.nodalsytems.R;
import com.nodel.nodalsytems.ui.NodelSystemApplication;
import com.nodel.nodalsytems.ui.util.DilogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by yugandhar on 1/6/2018.
 */

public class NodelSystemContoller
{
    Context context;
    public NodelSystemContoller(Context context)
    {
        this.context=context;
    }

  /*  public void logInAPICall(final ILoginView loginView, LoginDTO loginDTO)
    {
        final DilogUtil util = new DilogUtil(context);
        if (isNetworkAvailable(context))
        {
            final ProgressDialog pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait");
            pDialog.show();
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(loginDTO);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    RequestConstants.LOGIN_URL, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                            pDialog.dismiss();
                            //User json from json response
                            JSONObject jsonUser = response.optJSONObject("user");
                            try {
                                UserDTO userDTO= new Gson().fromJson(jsonUser.toString(), UserDTO.class);
                                CLRealmHelper.getInstance().SaveUsetDetails(userDTO);
                                ServiceResponseDTO serviceResponseDTO=new ServiceResponseDTO();
                                serviceResponseDTO.setStatusMsg(response.getString("message"));
                                loginView.handleLoginResponce(serviceResponseDTO);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    // hide the progress dialog
                    pDialog.dismiss();
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.data != null) {
                        String json = new String(response.data);
                        try {
                            JSONObject object = new JSONObject(json);
                            String message = object.optString("error");
                            if (message == null || TextUtils.isEmpty(message)) {
                                message = object.optString("message");
                            }
                            if (message == null || TextUtils.isEmpty(message)) {
                                message = object.optString("status");
                            }
                            util.showDialog(message, context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                    {
                        util.showDialog(context.getResources().getString(R.string.notAbleToLoginTrylater),context);
                    }
                }
            });
            //Register API calling
            NodelSystemApplication.getInstance().addToRequestQueue(jsonObjReq);
        }
        else
        {
            util.showDialog(context.getResources().getString(R.string.pleaseCheckInternet), context);
        }
    }*/

    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
