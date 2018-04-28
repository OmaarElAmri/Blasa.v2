package blasa.go;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import io.github.yavski.fabspeeddial.FabSpeedDial;

/**
 * Created by omarelamri on 10/04/2018.
 */

public class FragmentAdd extends Fragment {

    private EditText txt_from, txt_to, txt_date, txt_time, txt_phone, txt_price;
    private RadioGroup r1, r2, r3;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6;
    private static final String TAG = "TEST_TEST";
    private String url1, url2, url3;
    View v;

    public FragmentAdd() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.add_fragment, container, false);
        txt_from = (EditText) v.findViewById(R.id.txt_from);
        txt_to = (EditText) v.findViewById(R.id.txt_to);
        txt_date = (EditText) v.findViewById(R.id.txt_date);
        txt_time = (EditText) v.findViewById(R.id.txt_time);
        txt_phone = (EditText) v.findViewById(R.id.txt_phone);
        txt_price = (EditText) v.findViewById(R.id.txt_price);
        r1 = (RadioGroup) v.findViewById(R.id.r1);
        r2 = (RadioGroup) v.findViewById(R.id.r2);
        r3 = (RadioGroup) v.findViewById(R.id.r3);

        RadioGroup radioGroup1 = (RadioGroup) v.findViewById(R.id.r1);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch (checkedId) {
                    case R.id.radioButton1:
                        // switch to fragment 1
                        url1 = "https://image.ibb.co/eAEJPH/musical_40px.png";
                        break;
                    case R.id.radioButton2:
                        // Fragment 2
                        url1 = "https://image.ibb.co/gJn54H/Capture.png";
                        break;
                }
            }
        });
//=================================================================================================================

        RadioGroup radioGroup2 = (RadioGroup) v.findViewById(R.id.r2);

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch (checkedId) {
                    case R.id.radioButton3:
                        // switch to fragment 1
                        url2 = "https://image.ibb.co/f0TCjH/suitcase_40px.png";
                        break;
                    case R.id.radioButton4:
                        // Fragment 2
                        url2 = "https://image.ibb.co/eB1bBx/baggage_40px.png";
                        break;
                }
            }
        });
//===========================================================================================
        RadioGroup radioGroup3 = (RadioGroup) v.findViewById(R.id.r3);

        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch (checkedId) {
                    case R.id.radioButton5:
                        // switch to fragment 1
                        url3 = "https://image.ibb.co/dvTCjH/smoking_40px.png";
                        break;
                    case R.id.radioButton6:
                        // Fragment 2
                        url3 = "https://image.ibb.co/fMDZyc/nosmoking_40px.png";
                        break;
                }
            }
        });
//==========================================================================================
        FabSpeedDial fabSpeedDial = (FabSpeedDial) v.findViewById(R.id.fabdial2);
        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {

                return true;

            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                if (menuItem.getTitle().equals("Add")) {


                    

                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });
        return v;
    }


}


