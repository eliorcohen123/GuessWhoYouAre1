package com.elior.guesswhoyouare.OtherPackage;

import android.app.Application;

public class NearByApplication extends Application {

    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static Application getApplication() {
        return mApplication;
    }

}
