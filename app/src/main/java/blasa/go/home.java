package blasa.go;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class home extends AppCompatActivity  {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private FirebaseAuth mAuth;
    private String TAG="TEST_TEST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        mAuth = FirebaseAuth.getInstance();
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
        actionBar.setElevation(20);
        contextOfApplication = getApplicationContext();

    }

    @Override
    protected void onStart() {
        super.onStart();


//onlogout click from FragmentSettings
        if (getIntent().getBooleanExtra("LOGOUT", false)) {
            finish();
        }

    }


    @Override
    public void onStop() {
        super.onStop();

    }

// a static variable to get a reference of our application context
public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

}

