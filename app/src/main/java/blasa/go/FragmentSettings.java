package blasa.go;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by omarelamri on 10/04/2018.
 */

public class FragmentSettings extends Fragment {
    View v;

    //=================================
    private static final String TAG = "TEST_TEST" ;
    private Firebase myFirebaseRef;
    private FirebaseAuth mAuth;
    private TextView name;
    //===================================

    public FragmentSettings() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.settings_fragment,container,false);
        //======================================

        myFirebaseRef= new Firebase("https://blasa-v2-8675.firebaseio.com/users/");
        mAuth = FirebaseAuth.getInstance();
        name = (TextView) v.findViewById(R.id.text_view_name);
        //Get the uid for the currently logged in User from intent data passed to this activity
        String uid = mAuth.getCurrentUser().getUid();


//Referring to the name of the User who has logged in currently and adding a valueChangeListener
        myFirebaseRef.child(uid).child("name").addValueEventListener(new ValueEventListener() {
            //onDataChange is called every time the name of the User changes in your Firebase Database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Inside onDataChange we can get the data as an Object from the dataSnapshot
                //getValue returns an Object. We can specify the type by passing the type expected as a parameter
                String data = dataSnapshot.getValue(String.class);
                name.setText(data);
            }

            //onCancelled is called in case of any error
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(v.getContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //======================================
        return v;

    }
}
