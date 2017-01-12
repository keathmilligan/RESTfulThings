package com.example.restfulthings;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.json.JSONObject;

/**
 * Created by kmilligan on 1/11/2017.
 */

public class LEDResource extends ServerResource {

    @Get("json")
    public Representation getState() {
        JSONObject result = new JSONObject();
        try {
            result.put("state", LEDModel.getState());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StringRepresentation(result.toString(), MediaType.APPLICATION_ALL_JSON);
    }

    @Post("json")
    public Representation postState(Representation entity) {
        JSONObject result = new JSONObject();
        try {
            JsonRepresentation json = new JsonRepresentation(entity);
            result = json.getJsonObject();
            boolean state = (boolean)result.get("state");
            Log.d(this.getClass().getSimpleName(), "new LED state: "+state);
            LEDModel.setState(state);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StringRepresentation(result.toString(), MediaType.APPLICATION_ALL_JSON);
    }
}
