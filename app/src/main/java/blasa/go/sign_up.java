package blasa.go;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sign_up extends AppCompatActivity {
private Button btn_signup,btn_clear ;
private ImageButton btn_female,btn_male;
private EditText txt_username,txt_email2,txt_password2,txt_car,txt_phone;
private String g="";

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
        btn_female = (ImageButton) findViewById(R.id.btn_female);
        btn_male = (ImageButton) findViewById(R.id.btn_male);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_email2 = (EditText) findViewById(R.id.txt_email2);
        txt_password2 = (EditText) findViewById(R.id.txt_password2);
        txt_car = (EditText) findViewById(R.id.txt_car);
        txt_phone = (EditText) findViewById(R.id.txt_phone);

        btn_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
btn_male.setImageResource(R.drawable.male_40px);
btn_female.setVisibility(View.INVISIBLE);
g="male";
            }
        });

        btn_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_female.setImageResource(R.drawable.female_40px);
                btn_male.setVisibility(View.INVISIBLE);
                g="female";
            }
        });
    }
//=====================================================================================================
    @Override
    public void onStop() {
        super.onStop();
    }

    //This method sets up a new User by fetching the user entered details.
    protected void setUpUser() {
        user = new User();
        user.setName(txt_username.getText().toString());
        user.setPhoneNumber(txt_phone.getText().toString());
        user.setEmail(txt_email2.getText().toString());
        user.setPassword(txt_password2.getText().toString());
        user.setPhoneNumber(txt_phone.getText().toString());
        user.setGender(g);

    }


    public void onClearClicked(View view){
        btn_female.setImageResource(R.drawable.female__40px);
        btn_male.setImageResource(R.drawable.male__40px);
btn_female.setVisibility(View.VISIBLE);
btn_male.setVisibility(View.VISIBLE);
        txt_username.setText("");
        txt_email2.setText("");
        txt_password2.setText("");
        txt_car.setText("");
        txt_phone.setText("");
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
                            onAuthenticationSucess(task.getResult().getUser());
                        }


                    }
                });

    }

    private void onAuthenticationSucess(FirebaseUser mUser) {
        // Write new user
        saveNewUser(mUser.getUid(), user.getName(), user.getPhoneNumber(), user.getEmail(), user.getPassword(), user.getCartype(), user.getGender());
        signOut();
        // Go to LoginActivity
        startActivity(new Intent(sign_up.this, MainActivity.class));
        finish();
    }

    private void saveNewUser(String userId, String name, String phone, String email, String password, String cartype, String gender) {
        User user = new User(userId,name,phone,email,password,cartype,gender);

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

        String usercartype = txt_car.getText().toString();
        if(TextUtils.isEmpty(userusername)){
            txt_car.setError("Required.");
            valid = false;
        }else {
            txt_car.setError(null);
        }

        String userphone = txt_phone.getText().toString();
        if(TextUtils.isEmpty(userusername)){
            txt_phone.setError("Required.");
            valid = false;
        }else {
            txt_phone.setError(null);
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





}