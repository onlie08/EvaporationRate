package com.ch.base.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class ActivityBase extends Activity {
    public Context mCtx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCtx = this;


    }


}
