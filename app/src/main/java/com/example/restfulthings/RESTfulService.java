package com.example.restfulthings;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.ext.nio.HttpServerHelper;
import org.restlet.routing.Router;

public class RESTfulService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_START = "com.example.restfulthings.action.START";
    private static final String ACTION_STOP = "com.example.restfulthings.action.STOP";

    private Component mComponent;

    public RESTfulService() {
        super("RESTfulService");
        Engine.getInstance().getRegisteredServers().clear();
        Engine.getInstance().getRegisteredServers().add(new HttpServerHelper(null));
        mComponent = new Component();
        mComponent.getServers().add(Protocol.HTTP, 8080);
        Router router = new Router(mComponent.getContext().createChildContext());
        router.attach("/led", LEDResource.class);
        mComponent.getDefaultHost().attach("/rest", router);
    }

    public static void startServer(Context context) {
        Intent intent = new Intent(context, RESTfulService.class);
        intent.setAction(ACTION_START);
        context.startService(intent);
    }

    public static void stopServer(Context context) {
        Intent intent = new Intent(context, RESTfulService.class);
        intent.setAction(ACTION_STOP);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                handleStart();
            } else if (ACTION_STOP.equals(action)) {
                handleStop();
            }
        }
    }

    private void handleStart() {
        try {
            mComponent.start();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), e.toString());
        }
    }

    private void handleStop() {
        try {
            mComponent.stop();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), e.toString());
        }
    }
}
