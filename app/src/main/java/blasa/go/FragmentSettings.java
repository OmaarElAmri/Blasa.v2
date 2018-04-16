package blasa.go;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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
    private String providerId;
    //private TextView email;
    private EditText name2;
    private Button delete;
    private AlertDialog.Builder builder;
    private ImageView profilePicture;
    private Uri mImageUri;
    private RenderScript mRS;
    private Bitmap inputBitmap;
    private Bitmap outputBitmap;
    private StorageReference mStorageRef;
    private ProgressBar mProgressBar;
private ContentResolver cR;
    private StorageTask mUploadTask;

    private static final int PICK_IMAGE_REQUEST = 1;

    public FragmentSettings() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.settings_fragment, container, false);
        myFirebaseRef = new Firebase("https://blasa-v2-8675.firebaseio.com/users/");

name2 = (EditText) v.findViewById(R.id.name);
        //email = (TextView) v.findViewById(R.id.text_view_name1);
        profilePicture = (ImageView) v.findViewById(R.id.profilePicture);
        delete = (Button) v.findViewById(R.id.btn_delete);
        name = (TextView) v.findViewById(R.id.text_view_name);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();
//Get the uid for the currently logged in User from intent data passed to this activity
        String uid = mAuth.getCurrentUser().getUid();

//update database====================================================================================
      /*  Firebase firebase = new Firebase("https://blasa-v2-8675.firebaseio.com/users/");
        firebase.child(uid).child("photoURL").setValue("newValue");*/
//==================================================================================================
// ======================================================================================================
//photo upload
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");


//======================================================================================================
















//fetching username

//Referring to the name of the User who has logged in currently and adding a valueChangeListener
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

//AlterDialog

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
        builder1.setMessage("Do you really want to delete your account ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mUser.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {


                                            Log.d(TAG, "onComplete: account deleted ");
                                            FirebaseAuth.getInstance().signOut();
                                            LoginManager.getInstance().logOut();
                                            getActivity().finish();

                                        } else {


                                        }
                                    }
                                });
                        //dialog.cancel();
                    }
                });


        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: gg");

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

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
                    UploadPhoto();
                }

                }

                return true;

            }

            @Override
            public void onMenuClosed() {

            }
        });
//===================================================================================================

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
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(v.getContext(), "Upload successful", Toast.LENGTH_LONG).show();
                               String x = taskSnapshot.getDownloadUrl().toString();
                               Firebase firebase = new Firebase("https://blasa-v2-8675.firebaseio.com/users/");
                               String uid = mAuth.getCurrentUser().getUid();
                               firebase.child(uid).child("photoURL").setValue(x);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(v.getContext(), "Uploading...", Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(v.getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
//===================================================================================================

}



