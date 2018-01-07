package com.nodel.nodalsytems.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nodel.nodalsytems.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        // Set up the login form.

        Button loginButton= (Button) findViewById(R.id.btn_login);
//        TextView forgotText= (TextView) findViewById(R.id.forgot_password);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LoginDTO loginDTO=new LoginDTO();
                String email=((EditText)findViewById(R.id.input_email)).getText().toString();
                String password=((EditText)findViewById(R.id.input_password)).getText().toString();
                if(email.isEmpty())
                    ((EditText)findViewById(R.id.input_email)).setError("UserName manditory");
                else if(password.isEmpty())
                    ((EditText)findViewById(R.id.input_password)).setError("Pssword manditory");
                else
                {

                    Intent view = new Intent(LoginActivity.this,MainActivity.class);
                    view.setAction(Intent.ACTION_VIEW);
//                    view.setData(Uri.parse());
                    startActivity(view);
                   /* loginDTO.setEmail(email);
                    loginDTO.setPassword(password);
                    HTFController htfController = new HTFController(getActivity());
                    htfController.logInAPICall(LoginFragment.this, loginDTO);*/
                }
            }
        });
    }
}

