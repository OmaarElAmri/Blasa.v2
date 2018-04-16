package blasa.go;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by omarelamri on 10/04/2018.
 */

public class FragmentSearch extends android.support.v4.app.Fragment {
    View v;




    public FragmentSearch() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.search_fragment,container,false);

        return v;

    }
}
