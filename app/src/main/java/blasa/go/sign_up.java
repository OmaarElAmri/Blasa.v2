package blasa.go;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sign_up extends AppCompatActivity {
private Button btn_signup,btn_clear ;
private EditText txt_username,txt_email2,txt_password2;


    private static final String TAG = "TEST_TEST";
    private Firebase mRef = new Firebase("https://blasa-v2-8675.firebaseio.com/");
    private User user;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_email2 = (EditText) findViewById(R.id.txt_email2);
        txt_password2 = (EditText) findViewById(R.id.txt_password2);

    }

    @Override
    public void onStop() {

        super.onStop();
    }

    //This method sets up a new User by fetching the user entered details.
    protected void setUpUser() {
        user = new User();
        user.setName(txt_username.getText().toString());
        user.setEmail(txt_email2.getText().toString());
        user.setPassword(txt_password2.getText().toString());
        user.setphotoURL("https://firebasestorage.googleapis.com/v0/b/blasa-v2-8675.appspot.com/o/app%2Fcentos_users_and_groups.jpg?alt=media&token=52a0eb96-9b45-4c77-b058-17afcbb740e0");
        //user.setphotoURL("https://image.ibb.co/bGbM6n/centos_users_and_groups.jpg");
    }

    public void onClearClicked(View view){
        txt_username.setText("");
        txt_email2.setText("");
        txt_password2.setText("");
    }

    public void onSignUpClicked(View view) {
        createNewAccount(txt_email2.getText().toString(), txt_password2.getText().toString());
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
    }










    private void createNewAccount(String email, String password) {
        Log.d(TAG, "createNewAccount:" + email);
        if (!validateForm()) {
            return;
        }

        else if ((txt_username.getText().toString().length()<4)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Username too short !")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            // Toast.makeText(this.getApplicationContext(), "Email is not valid",Toast.LENGTH_LONG).show();
        }


        else if (!isValidEmail(txt_email2.getText().toString())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Email is invalid !")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

           // Toast.makeText(this.getApplicationContext(), "Email is not valid",Toast.LENGTH_LONG).show();
        }

        else if (txt_password2.getText().toString().length()<6) {
            {AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Password too short !")
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
        //This method sets up a new User by fetching the user entered details.
        setUpUser();
        //This method  method  takes in an email address and password, validates them and then creates a new user
        // with the createUserWithEmailAndPassword method.
        // If the new account was created, the user is also signed in, and the AuthStateListener runs the onAuthStateChanged callback.
        // In the callback, you can use the getCurrentUser method to get the user's account data.

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        hideProgressDialog();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            Toast.makeText(sign_up.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            sendVerificationEmail();
                            builder1();
                            onAuthenticationSucess(task.getResult().getUser());
                        }
                    }
                });

    }

    private void onAuthenticationSucess(FirebaseUser mUser) {
        // Write new user
        saveNewUser(mUser.getUid(), user.getName(), user.getEmail(), user.getPassword(), user.getphotoURL());
        signOut();
       // startActivity(new Intent(sign_up.this, MainActivity.class));
        //finish();
    }

    private void saveNewUser(String userId, String name, String email, String password, String photoURL) {
        User user = new User(userId,name,email,password,photoURL);

        mRef.child("users").child(userId).setValue(user);
    }

    private void signOut() {
        mAuth.signOut();
    }

    //This method, validates email address and password
    private boolean validateForm() {
        boolean valid = true;

        String userEmail = txt_email2.getText().toString();
        if (TextUtils.isEmpty(userEmail)) {
            txt_email2.setError("Required.");
            valid = false;
        } else {
            txt_email2.setError(null);
        }

        String userPassword = txt_password2.getText().toString();
        if (TextUtils.isEmpty(userPassword)) {
            txt_password2.setError("Required.");
            valid = false;
        } else {
            txt_password2.setError(null);
        }

        String userusername = txt_username.getText().toString();
        if(TextUtils.isEmpty(userusername)){
            txt_username.setError("Required.");
            valid = false;
        }else {
            txt_username.setError(null);
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

//Email test
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void builder1(){
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Email Verification sent !")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        startActivity(new Intent(sign_up.this, MainActivity.class));
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
    }

//Email verification
    public void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

//Toast.makeText(sign_up.this,"verif",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                        }
                    }
                });
    }

}
