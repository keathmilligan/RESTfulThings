package com.example.restfulthings;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // start the server
        RESTfulService.startServer(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // stop the server
        RESTfulService.stopServer(this);
    }
}
