package blasa.go;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.github.yavski.fabspeeddial.FabSpeedDial;

public class home extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Button btn_logout;
    private Button delete;
    private FirebaseAuth mAuth;
    private Firebase myFirebaseRef;
    private String TAG="TEST_TEST";
    private TextView name;
    private AlertDialog.Builder builder;
    private SwipeRefreshLayout swipeContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);


//=============================
        mAuth = FirebaseAuth.getInstance();

        //btn_logout = (Button) findViewById(R.id.btn_logout);



        //String y = mAuth.getCurrentUser().getDisplayName();


//=============================
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Add Fragment here
        adapter.AddFragment(new FragmentSearch(), "Search");
        adapter.AddFragment(new FragmentAdd(), "Add");
        adapter.AddFragment(new FragmentSettings(), "Settings");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_settings_black_24dp);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_search_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_add_location_24dp);


        //Remove Shadow From the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        contextOfApplication = getApplicationContext();
    }








    @Override
    protected void onStart() {
        super.onStart();


//===============
        /*String y = getIntent().getStringExtra("user_id");
        Log.d(TAG,"user_id");
        try {
            name.setText(y);
        }catch (NullPointerException e){}*/
// ===================



//onlogout click from FragmentSettings
        if (getIntent().getBooleanExtra("LOGOUT", false)) {
            finish();
        }

    }



    @Override
    public void onStop() {
        super.onStop();

    }






//=======================================




/*
    public void onLogoutClick(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(home.this, MainActivity.class));

    }
*/
// a static variable to get a reference of our application context
public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

}

