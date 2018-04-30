package blasa.go;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.ImageView;
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

import de.hdodenhof.circleimageview.CircleImageView;
import com.squareup.picasso.Picasso;
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
    public Rides rides;

    public FragmentSearch() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.search_fragment,container,false);

        txt_search = (EditText) v.findViewById(R.id.txt_search);
        btn_search = (Button) v.findViewById(R.id.btn_search);
        recycler1 = (RecyclerView) v.findViewById(R.id.recycler1);
        recycler1.setHasFixedSize(true);
        recycler1.setLayoutManager(new LinearLayoutManager(context));
        mDatabase = FirebaseDatabase.getInstance().getReference("rides");

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
        Query firebaseSearchQuery = mDatabase.orderByChild("finish").startAt(xx).endAt(xx + "\uf8ff");

        FirebaseRecyclerAdapter<Rides, RidesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Rides, RidesViewHolder>(
                Rides.class,
                R.layout.list_layout,
                RidesViewHolder.class,
                firebaseSearchQuery
        ) {

            @Override
            protected void populateViewHolder(RidesViewHolder viewHolder, Rides model, int position) {
                viewHolder.setDetails(context, model.getStart(), model.getFinish(), model.getPhotoURL(), model.getName(), model.getDate(), model.getTime(), model.getPrice(), model.getPhone(), model.getOpt1(), model.getOpt2(), model.getOpt3());
            }

        };

        recycler1.setAdapter(firebaseRecyclerAdapter);

    }

    //View holder class

    public static class RidesViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public RidesViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDetails(Context context, String start, String finish, String photoURL, String name, String date, String time, String price, final String phone, String opt1, String opt2, String opt3 ){
            CircleImageView circleImageView = (CircleImageView) mView.findViewById(R.id.profile_image);
            TextView txt_tox = (TextView) mView.findViewById(R.id.txt_tox);
            TextView txt_fromx = (TextView) mView.findViewById(R.id.txt_fromx);
            TextView txt_namex = (TextView) mView.findViewById(R.id.txt_namex);
            TextView txt_datex  = (TextView) mView.findViewById(R.id.txt_datex);
            TextView txt_timex = (TextView) mView.findViewById(R.id.txt_timex);
            TextView txt_pricex = (TextView) mView.findViewById(R.id.txt_pricex);
           // TextView txt_phonex = (TextView) mView.findViewById(R.id.txt_phonex);
            ImageView opt1x = (ImageView) mView.findViewById(R.id.opt1x);
            ImageView opt2x = (ImageView) mView.findViewById(R.id.opt2x);
            ImageView opt3x = (ImageView) mView.findViewById(R.id.opt3x);
            ImageButton imageButton = (ImageButton) mView.findViewById(R.id.imageButton);
           
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                    phoneIntent.setData(Uri.parse("tel:91-000-000-0000"));
                    startActivity(phoneIntent);
                    //Log.d(TAG, "onClick: ");
                }
            });
            
            
            txt_datex.setText(date);
            txt_timex.setText(time);
            txt_pricex.setText(price+" DT");
           // txt_phonex.setText(phone);
            txt_namex.setText(name);
            txt_fromx.setText(start);
            txt_tox.setText(finish);
            Picasso.get().load(photoURL).noFade().into(circleImageView);
            Picasso.get().load(opt1).into(opt1x);
            Picasso.get().load(opt2).into(opt2x);
            Picasso.get().load(opt3).into(opt3x);




        }

    }

}






















/*
public class FragmentSearch extends android.support.v4.app.Fragment {
    View v;
    private static final String TAG = "TEST_TEST";
    private EditText txt_search;
    private Button btn_search;
    private RecyclerView recycler1;
    private Firebase myFirebaseRef;
    private DatabaseReference mDatabase;
    private Context context;


    public FragmentSearch() {

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.search_fragment,container,false);

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
            CircleImageView circleImageView = (CircleImageView) mView.findViewById(R.id.profile_image);
            TextView txt_namex = (TextView) mView.findViewById(R.id.txt_namex);
            TextView txt_emailx = (TextView) mView.findViewById(R.id.txt_emailx);
            txt_namex.setText(name);
            txt_emailx.setText(email);


        }

    }
}
*/