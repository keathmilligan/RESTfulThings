package com.example.restfulthings;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

public class LEDModel {
    private static LEDModel instance = null;
    private PeripheralManagerService mPMSvc;
    private Gpio mBCM21;

    public static LEDModel getInstance() {
        if (instance == null) {
            instance = new LEDModel();
        }
        return instance;
    }

    private LEDModel() {
        mPMSvc = new PeripheralManagerService();
        try {
            mBCM21 = mPMSvc.openGpio("BCM21");
            mBCM21.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setState(boolean state) {
        try {
            getInstance().mBCM21.setValue(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean getState() {
        boolean value = false;
        try {
            value = getInstance().mBCM21.getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
