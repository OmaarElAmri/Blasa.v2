package blasa.go;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FacebookAuthProvider;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.provider.SyncStateContract.Helpers.update;



public class MainActivity extends AppCompatActivity {


    String y = "https://image.ibb.co/bGbM6n/centos_users_and_groups.jpg";
    private static final String TAG = "TEST_TEST";
    public User user;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mProgressDialog;
    private Firebase myFirebaseRef;
    //Add YOUR Firebase Reference URL instead of the following URL
    Firebase mRef=new Firebase("https://blasa-v2-8675.firebaseio.com/users/");
    //FaceBook callbackManager
    private CallbackManager callbackManager;
    private String PROVIDER_ID;
    private Button btn_register,btn_signin,forgot_password,fb_sign_in_button;
    //a constant for detecting the login intent result
    private static final int RC_SIGN_IN = 234;
    private EditText txt_email,txt_password;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        btn_signin = (Button) findViewById(R.id.btn_signup);
        forgot_password = (Button)findViewById(R.id.forgot_password);
        btn_register = (Button) findViewById(R.id.btn_register);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();


        if (mUser != null) {
            // User is signed in

            PROVIDER_ID = mUser.getProviders().get(0);
            //Log.d(TAG, "provider =  "+ PROVIDER_ID);
            Intent intent = new Intent(getApplicationContext(), home.class);
            startActivity(intent);
            finish();
            Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };


        //FaceBook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.fb_sign_in_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                signInWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
        //

//google

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

btn_register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, sign_up.class));
    }
});

        //printKeyHash();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    protected void setUpUser() {
        user = new User();
        user.setEmail(txt_email.getText().toString());
        user.setPassword(txt_password.getText().toString());
    }

    public void onLoginClicked(View view) {
        setUpUser();
        signIn(txt_email.getText().toString(), txt_password.getText().toString());
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            builder1();
                        } else {


                            if (mAuth.getCurrentUser() != null) {
                                Intent intent = new Intent(getApplicationContext(), home.class);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "Welcome !", Toast.LENGTH_SHORT).show();
                                finish();
                                Log.d(TAG, "onAuthStateChanged:signed_in:" + mAuth.getCurrentUser().getUid());

                            }
                            else {

                            Toast.makeText(MainActivity.this, "Welcome !.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), home.class);
                            startActivity(intent);
                            finish();}
                        }

                        hideProgressDialog();
                    }
                });
        //

    }

    private boolean validateForm() {
        boolean valid = true;

        String userEmail = txt_email.getText().toString();
        if (TextUtils.isEmpty(userEmail)) {
            txt_email.setError("Required.");
            valid = false;
        } else {
            txt_email.setError(null);
        }

        String userPassword = txt_password.getText().toString();
        if (TextUtils.isEmpty(userPassword)) {
            txt_password.setError("Required.");
            valid = false;
        } else {
            txt_password.setError(null);
        }

        return valid;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }


    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    //facebook login
    private void signInWithFacebook(AccessToken token) {
        Log.d(TAG, "signInWithFacebook:" + token);

        showProgressDialog();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{


                          /*  if (mAuth.getCurrentUser() != null) {
                                Intent intent = new Intent(getApplicationContext(), home.class);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "Welcome !", Toast.LENGTH_SHORT).show();
                                finish();
                                Log.d(TAG, "onAuthStateChanged:signed_in:" + mAuth.getCurrentUser().getUid());

                            } else {*/

                            Toast.makeText(MainActivity.this, "Welcome !",
                                    Toast.LENGTH_SHORT).show();
                            String uid=task.getResult().getUser().getUid();
                            String name=task.getResult().getUser().getDisplayName();
                            String email=task.getResult().getUser().getEmail();
                            Log.d(TAG,email);
                            Log.d(TAG,name);


                            //testing
                            String photoURL = task.getResult().getUser().getPhotoUrl().toString();
                            photoURL =  photoURL+ "/picture?height=500";
                         // photoURL =  photoURL+"/picture?type=large";
                            Log.d(TAG,photoURL);

                            //Create a new User and Save it in Firebase database
                            User user = new User(uid,name,email,null,photoURL);
                            //User user = new User(uid,name,email,null,y);

                            mRef.child("facebook").child(uid).setValue(user);
                            Intent intent = new Intent(getApplicationContext(), home.class);
                            startActivity(intent);
                            finish();}

                        //}

                        hideProgressDialog();
                    }
                });
    }

//======================================================================google sign in ======================================================================
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        showProgressDialog();
        Log.d("TEST_TEST", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            final String uid = user.getUid();
                            String name = user.getDisplayName() ;
                            String email=user.getEmail();
                            Log.d(TAG,name+email);

                            //testing
                            String photoURL = user.getPhotoUrl().toString();
                            photoURL = photoURL.replace("/s96-c/","/s900-c/");
                           /* if (mAuth.getCurrentUser() != null) {
                                Intent intent = new Intent(getApplicationContext(), home.class);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "Welcome !", Toast.LENGTH_SHORT).show();
                                finish();
                                Log.d(TAG, "onAuthStateChanged:signed_in:" + mAuth.getCurrentUser().getUid());
                            } else {*/

                                User user2 = new User(uid, name, email, null,photoURL);
                                mRef.child("google").child(uid).setValue(user2);
                                Toast.makeText(MainActivity.this, "Welcome !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), home.class);
                                startActivity(intent);
                           // }


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TEST_TEST", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        hideProgressDialog();
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

//======================================================================google sign in ======================================================================
    public void onforgotpassclicked(View view) {

        String userEmail = txt_email.getText().toString();
        if (TextUtils.isEmpty(userEmail)) {
            txt_email.setError("Required.");}
            else {
            showProgressDialog();
        mAuth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "We have sent you an email to reset your password!", Toast.LENGTH_LONG).show();
                        } else {
                            String userEmail = txt_email.getText().toString();
                            if (TextUtils.isEmpty(userEmail)) {

                                txt_email.setError("Required.");
                            Toast.makeText(MainActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
                            Toast.makeText(MainActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
                        hideProgressDialog();
                    }

                });


    }
        }

    public void builder1(){
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Email or Passwrod incorrect !")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }
    }


    /*private void printKeyHash() {
        try{


            PackageInfo info = getPackageManager().getPackageInfo("blasa.com", PackageManager.GET_SIGNATURES);
            for (Signature signature:info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", android.util.Base64.encodeToString(md.digest(), android.util.Base64.DEFAULT));

            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }*/
}
