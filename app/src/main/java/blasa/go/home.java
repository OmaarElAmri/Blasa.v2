package blasa.go;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class home extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Button btn_logout;
    private ImageView profilePicture;
    private FirebaseAuth mAuth;
    private Firebase myFirebaseRef;
    private String TAG="TEST_TEST";
    private TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

//=============================


        mAuth = FirebaseAuth.getInstance();
        profilePicture = (ImageView) findViewById(R.id.profilePicture);
        btn_logout = (Button) findViewById(R.id.btn_logout);



        //String y = mAuth.getCurrentUser().getDisplayName();


//=============================

        // Add Fragment here
        adapter.AddFragment(new FragmentSearch(), "Search");
        adapter.AddFragment(new FragmentAdd(), "Add");
        adapter.AddFragment(new FragmentSettings(), "Settings");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_search_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_directions_car_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_settings_black_24dp);

        //Remove Shadow From the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
    }








    @Override
    protected void onStart() {
        super.onStart();

        //name = (TextView) findViewById(R.id.text_view_name);
        //Get the uid for the currently logged in User from intent data passed to this activity

        //Log.d(TAG,mAuth.getCurrentUser().getDisplayName());




        /*String y = getIntent().getStringExtra("user_id");
        Log.d(TAG,"user_id");
        try {
            name.setText(y);
        }catch (NullPointerException e){}*/

        //===============


        // ===================


        /*String y = mAuth.getCurrentUser().getDisplayName();
        Log.d(TAG,y);
try {
    name.setText(y);
}catch (NullPointerException e){}*/

//String y = myFirebaseRef.child(uid).child("name").toString();
        //Log.d(TAG,y);


    }


    @Override
    public void onStop() {
        super.onStop();

    }






//=======================================



    public void onLogoutClick(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(home.this, MainActivity.class));

    }



}

