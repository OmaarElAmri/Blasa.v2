package blasa.go;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.yavski.fabspeeddial.FabSpeedDial;

import com.squareup.picasso.Picasso;
import static com.facebook.share.internal.DeviceShareDialogFragment.TAG;

/**
 * Created by omarelamri on 10/04/2018.
 */

public class FragmentSearch extends android.support.v4.app.Fragment {
    View v;
    private static final String TAG = "TEST_TEST";
    private EditText txt_search;
    private ImageButton btn_search;
    private RecyclerView recycler1;
    private DatabaseReference mDatabase;
    private Context context;
    private String x="";
    private String w="";
    private String z="";
    private String i="";
private FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    public FragmentSearch() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.search_fragment,container,false);
        txt_search = (EditText) v.findViewById(R.id.txt_search);
        btn_search = (ImageButton) v.findViewById(R.id.btn_search);
        recycler1 = (RecyclerView) v.findViewById(R.id.recycler1);
        recycler1.setHasFixedSize(true);
        recycler1.setLayoutManager(new LinearLayoutManager(context));
        mDatabase = FirebaseDatabase.getInstance().getReference("rides");

//load last rides
        String x = "";
        firebaseSearch(x);
//search
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
        final Query firebaseSearchQuery = mDatabase.orderByChild("finish").startAt(xx).endAt(xx + "\uf8ff");

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Rides, RidesViewHolder>(
                Rides.class,
                R.layout.list_layout,
                RidesViewHolder.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(final RidesViewHolder viewHolder, final Rides model, final int position) {

                viewHolder.setDetails(context, model.getStart(), model.getFinish(), model.getPhotoURL(), model.getName(), model.getDate(), model.getTime(), model.getPrice(), model.getPhone(), model.getOpt1(), model.getOpt2(), model.getOpt3());

                firebaseRecyclerAdapter.getRef(position);
                x = firebaseRecyclerAdapter.getRef(position).getDatabase().toString();
                w = model.getPhone();

//FabSpeedDial
                final FabSpeedDial fabSpeedDial = (FabSpeedDial) v.findViewById(R.id.fabdial);

                fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
                    @Override
                    public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemSelected(MenuItem menuItem) {

                        if (menuItem.getTitle().equals("SMS")) {
                            Toast.makeText(v.getContext(),"please select a ride",Toast.LENGTH_SHORT).show();
                        }else if (menuItem.getTitle().equals("CALL")) {
                            Toast.makeText(v.getContext(),"please select a ride",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(v.getContext(),"please select a ride",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }

                    @Override
                    public void onMenuClosed() {

                    }
                });

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                // Log.w(TAG, "You clicked on "+position);
                                firebaseRecyclerAdapter.getRef(position);
                                x = firebaseRecyclerAdapter.getRef(position).getDatabase().toString();
                                //Log.d(TAG,x);
                                w = model.getPhone();
                                //Log.d(TAG, w);
                                z = model.getStart();
                                i = model.getFinish();

                               Toast.makeText(v.getContext(),z+" ==> "+i,Toast.LENGTH_SHORT).show();

                                fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
                                    @Override
                                    public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                                        return true;
                                    }

                                    @Override
                                    public boolean onMenuItemSelected(MenuItem menuItem) {

                                        if (menuItem.getTitle().equals("SMS")) {

                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", w, null)));


                                        }else if (menuItem.getTitle().equals("CALL")) {

                                            Intent callIntent = new Intent(Intent.ACTION_DIAL); //use ACTION_CALL class
                                            callIntent.setData(Uri.parse("tel:"+w));    //this is the phone number calling
                                            startActivity(callIntent);  //call activity
                                        }
                                        else {
                                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                    Uri.parse("https://www.google.com/maps/dir/?api=1&origin="+z+"&destination="+i+"&travelmode=driving"));
                                            startActivity(intent);
                                        }
                                        return true;
                                    }

                                    @Override
                                    public void onMenuClosed() {

                                    }
                                });


      /*
        ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("phone number", w);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(v.getContext(),"phone number copied to clipboard",Toast.LENGTH_LONG).show();
*/


                              //  startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", w, null)));

        /*
        Intent callIntent = new Intent(Intent.ACTION_DIAL); //use ACTION_CALL class
        callIntent.setData(Uri.parse("tel:"+w));    //this is the phone number calling
        startActivity(callIntent);  //call activity
*/
                          }
                    });

/*
                viewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        String s = model.getStart();
                        String f = model.getFinish();
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("https://www.google.com/maps/dir/?api=1&origin="+s+"&destination="+f+"&travelmode=driving"));
                        startActivity(intent);
                        return false;
                    }
                });
*/
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
            TextView txt_phonex = (TextView) mView.findViewById(R.id.txt_phonex);
            ImageView opt1x = (ImageView) mView.findViewById(R.id.opt1x);
            ImageView opt2x = (ImageView) mView.findViewById(R.id.opt2x);
            ImageView opt3x = (ImageView) mView.findViewById(R.id.opt3x);

            txt_datex.setText(date);
            txt_timex.setText(time);
            txt_pricex.setText(price+" DT");
            txt_phonex.setText(phone);
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