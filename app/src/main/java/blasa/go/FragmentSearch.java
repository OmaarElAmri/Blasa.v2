package blasa.go;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static com.facebook.share.internal.DeviceShareDialogFragment.TAG;

/**
 * Created by omarelamri on 10/04/2018.
 */

public class FragmentSearch extends android.support.v4.app.Fragment {
    View v;
    private static final String TAG = "TEST_TEST";
    private EditText txt_search;
    private Button btn_search;
    private RecyclerView recycler1;
    private Firebase myFirebaseRef;
    private DatabaseReference mDatabase;
    private Context context;

    //private Firebase mRef = new Firebase("https://blasa-v2-8675.firebaseio.com/");

    public FragmentSearch() {

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.search_fragment,container,false);


        //mRef.child("rides").push().setValue("yoo");

        txt_search = (EditText) v.findViewById(R.id.txt_search);
       btn_search = (Button) v.findViewById(R.id.btn_search);
       recycler1 = (RecyclerView) v.findViewById(R.id.recycler1);
       recycler1.setHasFixedSize(true);
       recycler1.setLayoutManager(new LinearLayoutManager(context));
       mDatabase = FirebaseDatabase.getInstance().getReference("users");

       
       btn_search.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String xx = txt_search.getText().toString();
        firebaseSearch(xx);

    }
});
        return v;

    }

    private void firebaseSearch(String xx) {
        Query firebaseSearchQuery = mDatabase.orderByChild("name").startAt(xx).endAt(xx + "\uf8ff");

        FirebaseRecyclerAdapter<User, UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(
                User.class,
                R.layout.list_layout,
                UserViewHolder.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {

            viewHolder.setDetails(context, model.getName(), model.getEmail());

            }
        };

        recycler1.setAdapter(firebaseRecyclerAdapter);

    }

    //View holder class





    public static class UserViewHolder extends RecyclerView.ViewHolder {

View mView;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDetails(Context context,String name, String email){
            TextView txt_namex = (TextView) mView.findViewById(R.id.txt_namex);
            TextView txt_emailx = (TextView) mView.findViewById(R.id.txt_emailx);
            txt_namex.setText(name);
            txt_emailx.setText(email);


        }

    }
}
