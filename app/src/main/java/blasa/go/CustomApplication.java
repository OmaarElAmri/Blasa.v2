package blasa.go;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;

/**
 * Created by omarelamri on 04/04/2018.
 */

public class CustomApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}