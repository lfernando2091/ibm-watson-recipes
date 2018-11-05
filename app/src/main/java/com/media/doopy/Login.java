package com.media.doopy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.media.doopy.Activity.MainActivity;

import static com.media.doopy.Log4J.Logger.setClass;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setClass(Login.class);
        setContentView(R.layout.activity_login);
    }

    public void OnLogin(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
    }

}
