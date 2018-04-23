package blasa.go;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import io.github.yavski.fabspeeddial.FabSpeedDial;

import static android.app.Activity.RESULT_OK;

/**
 * Created by omarelamri on 10/04/2018.
 */

public class FragmentSettings extends Fragment {
    View v;
    private static final String TAG = "TEST_TEST";
    private Firebase myFirebaseRef;
    private FirebaseAuth mAuth;
    private TextView name;
    private EditText name2;
    private ImageView profilePicture;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private String PROVIDER_ID;
    private ProgressDialog progressDialog ;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ValueEventListener valueEventListener;
    private DatabaseReference mDatabase;

    public FragmentSettings() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.settings_fragment, container, false);

        progressDialog = new ProgressDialog(v.getContext());
        name2 = (EditText) v.findViewById(R.id.name);
        profilePicture = (ImageView) v.findViewById(R.id.profilePicture);
        name = (TextView) v.findViewById(R.id.text_view_name);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();
//Get the uid for the currently logged in User from intent data passed to this activity
        final String uid = mAuth.getCurrentUser().getUid();


       mDatabase = FirebaseDatabase.getInstance().getReference("Users");




    // final List<User> x = new ArrayList<User>();
       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//update database====================================================================================
      /*  Firebase firebase = new Firebase("https://blasa-v2-8675.firebaseio.com/users/");
        firebase.child(uid).child("photoURL").setValue("newValue");*/

 //update       mDatabase.child("users").child(userId).child("username").setValue(name);

        /*Delete data
The simplest way to delete data is to call removeValue() on a reference to the location of that data.

You can also delete by specifying null as the value for another write operation such as setValue() or updateChildren(). You can use this technique with updateChildren() to delete multiple children in a single API call.*/
//==================================================================================================
//photos folder creation
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
//display user's information based on auth provider
        PROVIDER_ID = mUser.getProviders().get(0);

        if (PROVIDER_ID.equals("password")) {
            Log.d(TAG, "provider = "+ PROVIDER_ID);
            myFirebaseRef = new Firebase("https://blasa-v2-8675.firebaseio.com/users/");
//name
            myFirebaseRef.child(uid).child("name").addValueEventListener(new ValueEventListener() {
                //onDataChange is called every time the name of the User changes in your Firebase Database
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//Inside onDataChange we can get the data as an Object from the dataSnapshot
//getValue returns an Object. We can specify the type by passing the type expected as a parameter
                    String data = dataSnapshot.getValue(String.class);
                    name2.setText(data);
                    name2.setEnabled(false);
                }

                //onCancelled is called in case of any error
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(v.getContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

//photo
            myFirebaseRef.child(uid).child("photoURL").addValueEventListener(new ValueEventListener() {
                //onDataChange is called every time the name of the User changes in your Firebase Database
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//Inside onDataChange we can get the data as an Object from the dataSnapshot
//getValue returns an Object. We can specify the type by passing the type expected as a parameter
                    String data = dataSnapshot.getValue(String.class);
                    Picasso.get().load(data).into(profilePicture);

                }

                //onCancelled is called in case of any error
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(v.getContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } else if (PROVIDER_ID.equals("facebook.com")){
            Log.d(TAG, "provider = "+ PROVIDER_ID);
            myFirebaseRef = new Firebase("https://blasa-v2-8675.firebaseio.com/users/facebook/");
            myFirebaseRef.child(uid).child("name").addValueEventListener(new ValueEventListener() {
                //onDataChange is called every time the name of the User changes in your Firebase Database
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//Inside onDataChange we can get the data as an Object from the dataSnapshot
//getValue returns an Object. We can specify the type by passing the type expected as a parameter
                    String data = dataSnapshot.getValue(String.class);
                    //name.setText(data);
                    name2.setText(data);
                    name2.setEnabled(false);
                }

                //onCancelled is called in case of any error
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(v.getContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
//photo
            myFirebaseRef = new Firebase("https://blasa-v2-8675.firebaseio.com/users/facebook/");
            myFirebaseRef.child(uid).child("photoURL").addValueEventListener(new ValueEventListener() {
                //onDataChange is called every time the name of the User changes in your Firebase Database
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//Inside onDataChange we can get the data as an Object from the dataSnapshot
//getValue returns an Object. We can specify the type by passing the type expected as a parameter
                    String data = dataSnapshot.getValue(String.class);
                    Picasso.get().load(data).into(profilePicture);
                }

                //onCancelled is called in case of any error
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(v.getContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else if (PROVIDER_ID.equals("google.com"))
        {  Log.d(TAG, "provider = "+ PROVIDER_ID);
            myFirebaseRef = new Firebase("https://blasa-v2-8675.firebaseio.com/users/google/");
//name
            myFirebaseRef.child(uid).child("name").addValueEventListener(new ValueEventListener() {
                //onDataChange is called every time the name of the User changes in your Firebase Database
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//Inside onDataChange we can get the data as an Object from the dataSnapshot
//getValue returns an Object. We can specify the type by passing the type expected as a parameter
                    String data = dataSnapshot.getValue(String.class);
                    //name.setText(data);
                    name2.setText(data);
                    name2.setEnabled(false);
                }

                //onCancelled is called in case of any error
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(v.getContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
//photo
            myFirebaseRef = new Firebase("https://blasa-v2-8675.firebaseio.com/users/google/");
            myFirebaseRef.child(uid).child("photoURL").addValueEventListener(new ValueEventListener() {
                //onDataChange is called every time the name of the User changes in your Firebase Database
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//Inside onDataChange we can get the data as an Object from the dataSnapshot
//getValue returns an Object. We can specify the type by passing the type expected as a parameter
                    String data = dataSnapshot.getValue(String.class);
                    Picasso.get().load(data).into(profilePicture);
                }

                //onCancelled is called in case of any error
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(v.getContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }


//FabSpeedDial

        FabSpeedDial fabSpeedDial = (FabSpeedDial) v.findViewById(R.id.fabdial);
        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {

                return true;

            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                if (menuItem.getTitle().equals("Log Out")) {


                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    Intent intent = new Intent(getActivity(), home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("LOGOUT", true);
                    startActivity(intent);

                    getActivity().finish();
                    //Toast.makeText(v.getContext(),"log out",Toast.LENGTH_SHORT).show();

                } else if (menuItem.getTitle().equals("Choose Photo")) {
                    openFileChooser();
                } else {
                    /*crash*/       if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(v.getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                    } else {

                        //==========================================================
                        myFirebaseRef = new Firebase("https://blasa-v2-8675.firebaseio.com/users/");

                      //Query query =  myFirebaseRef.child(uid).orderByChild("name")
                                //.startAt(queryText)
                                //.endAt(queryText+"\uf8ff")
                               // .equalTo(queryText);
                        //final String queryText = "";
                       // for (int i = 0; i < 5; i++) {




                        /*
                            myFirebaseRef.child(uid).child("name").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                    // TODO: handle the post
                                    String x = dataSnapshot.getValue(String.class);
                                    String queryText = "";
                                    if (x.equals(queryText)) {
                                        Log.d(TAG, x);
                                  //  }

                                    } else {
                                        Log.d(TAG,"not found");
                                    }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                    Toast.makeText(v.getContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();

                                }


                            });


                        */




                     //  }

                      /*  myFirebaseRef = new Firebase("https://blasa-v2-8675.firebaseio.com/users/");
                      myFirebaseRef.orderByChild("name")
                                .startAt("[a-zA-Z0-9]*")
                                .endAt("Omar")
                                .once(value);
                        Log.d(TAG,query);*/
                        /*myFirebaseRef.startAt(queryText)
                                .endAt(queryText+"\uf8ff")
                                .once("value");

*/
                      /*  myFirebaseRef = new Firebase("https://blasa-v2-8675.firebaseio.com/users/");
                        Query query = myFirebaseRef.child(uid);
                        final String queryText = "admin";
                        // Get a reference to our posts
                        // Attach an listener to read the data at our posts reference
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {

                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    String x = postSnapshot.getValue(String.class);
                                   //user  = dataSnapshot.getValue(String.class);
                                    if (x.equals(queryText)){Log.d(TAG,x);}
                                    Log.d(TAG, user.getName() );
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                System.out.println("The read failed: " + firebaseError.getMessage());
                            }
                        });*/








                        //============================================================



                        UploadPhoto();
                    }

                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });


        //=============================================================





        //=============================================================



/*=============

        Intent intent = new Intent(getActivity(), home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("LOGOUT", true);
        startActivity(intent);

        getActivity().finish();
//===============*/
        return v;
    }






//choosing picture===================================================================================================

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(profilePicture);
        }
    }
    //===================================================================================================
    private String getFileExtension(Uri uri)
    {   Context applicationContext = home.getContextOfApplication();
        ContentResolver cR =  applicationContext.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void UploadPhoto() {
        if (mImageUri != null) {

            //=============
            // Setting progressDialog Title.
            progressDialog.setTitle("Image is Uploading...");

            // Showing progressDialog.
            progressDialog.show();
            //============
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                            PROVIDER_ID = mAuth.getCurrentUser().getProviders().get(0);
                            if (PROVIDER_ID.equals("password")) {
                                Toast.makeText(v.getContext(), "Upload successful", Toast.LENGTH_LONG).show();
                                String x = taskSnapshot.getDownloadUrl().toString();
                                Firebase firebase = new Firebase("https://blasa-v2-8675.firebaseio.com/users/");
                                String uid = mAuth.getCurrentUser().getUid();
                                firebase.child(uid).child("photoURL").setValue(x);
                            } else if (PROVIDER_ID.equals("facebook.com")) {
                                Toast.makeText(v.getContext(), "Upload successful", Toast.LENGTH_LONG).show();
                                String x = taskSnapshot.getDownloadUrl().toString();
                                Firebase firebase = new Firebase("https://blasa-v2-8675.firebaseio.com/users/facebook/");
                                String uid = mAuth.getCurrentUser().getUid();
                                firebase.child(uid).child("photoURL").setValue(x);


                            }
                            else if (PROVIDER_ID.equals("google.com"))
                            {
                                Toast.makeText(v.getContext(), "Upload successful", Toast.LENGTH_LONG).show();
                                String x = taskSnapshot.getDownloadUrl().toString();
                                Firebase firebase = new Firebase("https://blasa-v2-8675.firebaseio.com/users/google/");
                                String uid = mAuth.getCurrentUser().getUid();
                                firebase.child(uid).child("photoURL").setValue(x);
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();
                            Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                            double progress = (100.0 * taskSnapshot.getBytesTransferred() )/ taskSnapshot.getTotalByteCount();
                            int currentprogress = (int) progress;
                            Log.d(TAG,"Upload is " + progress + "% done");
                            //Toast.makeText(v.getContext(), "Uploading "+currentprogress +"%...", Toast.LENGTH_SHORT).show();


                            // Setting progressDialog Title.
                            progressDialog.setTitle("Image is Uploading "+ currentprogress +" %...");

                            // Toast.makeText(v.getContext(), "Uploading...", Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(v.getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
//===================================================================================================

}



