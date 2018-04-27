package blasa.go;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by omarelamri on 10/04/2018.
 */

public class FragmentAdd extends Fragment {

    private EditText txt_from,txt_to,txt_date,txt_time,txt_phone,txt_price;
    private RadioGroup r1,r2,r3;
    private RadioButton radioButton1,radioButton2,radioButton3;
    View v;
    public FragmentAdd() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.add_fragment,container,false);
        txt_from = (EditText) v.findViewById(R.id.txt_from);
        txt_to = (EditText) v.findViewById(R.id.txt_to);
        txt_date = (EditText) v.findViewById(R.id.txt_date);
        txt_time = (EditText) v.findViewById(R.id.txt_time);
        txt_phone = (EditText) v.findViewById(R.id.txt_phone);
       txt_price = (EditText) v.findViewById(R.id.txt_price) ;
r1 = (RadioGroup) v.findViewById(R.id.r1);
r2 = (RadioGroup) v.findViewById(R.id.r2);
r3 = (RadioGroup) v.findViewById(R.id.r3);






        return v;
    }
}
