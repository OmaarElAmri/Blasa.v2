package blasa.go;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by omarelamri on 10/04/2018.
 */

public class FragmentAdd extends Fragment {
    public Rides rides;
    private EditText txt_from, txt_to, txt_date, txt_time, txt_phone, txt_price;
    private RadioGroup r1, r2, r3;
    private static final String TAG = "TEST_TEST";
    private String url1, url2, url3,name,photoURL;
    private Button btn_add;
    private FirebaseAuth mAuth;
    private Firebase myFirebaseRef;
    private String PROVIDER_ID;
    private Firebase mRef = new Firebase("https://blasa-v2-8675.firebaseio.com/");
    private final Calendar myCalendar = Calendar.getInstance();
    View v;

    public FragmentAdd() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.add_fragment, container, false);
        txt_from = (EditText) v.findViewById(R.id.txt_from);
        txt_to = (EditText) v.findViewById(R.id.txt_to);
        txt_date = (EditText) v.findViewById(R.id.txt_date);
        txt_time = (EditText) v.findViewById(R.id.txt_time);
        txt_phone = (EditText) v.findViewById(R.id.txt_phone);
        txt_price = (EditText) v.findViewById(R.id.txt_price);
        r1 = (RadioGroup) v.findViewById(R.id.r1);
        r2 = (RadioGroup) v.findViewById(R.id.r2);
        r3 = (RadioGroup) v.findViewById(R.id.r3);
        btn_add = (Button) v.findViewById(R.id.btn_add) ;

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();
//Get the uid for the currently logged in User from intent data passed to this activity
        final String uid = mAuth.getCurrentUser().getUid();

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
                    String data1 = dataSnapshot.getValue(String.class);
                    name = data1;
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
                    String data2 = dataSnapshot.getValue(String.class);
                    photoURL = data2;

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
                    String data1 = dataSnapshot.getValue(String.class);
                    name = data1;
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
                    String data2 = dataSnapshot.getValue(String.class);
                    photoURL = data2;
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
                    String data1 = dataSnapshot.getValue(String.class);
                    name = data1;
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
                    String data2 = dataSnapshot.getValue(String.class);
                    photoURL = data2;
                }

                //onCancelled is called in case of any error
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(v.getContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        r1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch (checkedId) {
                    case R.id.radioButton1:
                        // switch to fragment 1
                        url1 = "https://firebasestorage.googleapis.com/v0/b/blasa-v2-8675.appspot.com/o/app%2Fmusical_40px.png?alt=media&token=06a3211f-1d55-4770-956a-db6595d98849";

                        break;
                    case R.id.radioButton2:
                        // Fragment 2
                        url1 = "https://firebasestorage.googleapis.com/v0/b/blasa-v2-8675.appspot.com/o/app%2FCapture.png?alt=media&token=0823088e-18b5-4ab1-a879-a2a78d8b9bc6";

                        break;
                }
            }
        });

        r2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch (checkedId) {
                    case R.id.radioButton3:
                        // switch to fragment 1
                        url2 = "https://firebasestorage.googleapis.com/v0/b/blasa-v2-8675.appspot.com/o/app%2Fsuitcase_40px.png?alt=media&token=d8cd4fa3-1596-4348-835f-24345743fe08";
                        break;
                    case R.id.radioButton4:
                        // Fragment 2
                        url2 = "https://firebasestorage.googleapis.com/v0/b/blasa-v2-8675.appspot.com/o/app%2Fbaggage_40px.png?alt=media&token=df181956-d21b-4a99-8a7a-3959985b5197";
                        break;
                }
            }
        });

        r3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch (checkedId) {
                    case R.id.radioButton5:
                        // switch to fragment 1
                        url3 = "https://firebasestorage.googleapis.com/v0/b/blasa-v2-8675.appspot.com/o/app%2Fsmoking_40px.png?alt=media&token=67c1bdb6-4368-4510-9edd-18ecec06ed7a";

                        break;
                    case R.id.radioButton6:
                        // Fragment 2
                        url3 = "https://firebasestorage.googleapis.com/v0/b/blasa-v2-8675.appspot.com/o/app%2Fnosmoking_40px.png?alt=media&token=2a66e794-c3de-4b45-9e65-a5653f62f067";

                        break;
                }
            }
        });

     btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!validateForm()) {
                    return;
                }
                rides = new Rides();
                rides.setStart(txt_from.getText().toString());
                rides.setFinish(txt_to.getText().toString());
                rides.setDate(txt_date.getText().toString());
                rides.setTime(txt_time.getText().toString());
                rides.setPrice(txt_price.getText().toString());
                rides.setPhone(txt_phone.getText().toString());
                rides.setOpt1(url1);
                rides.setOpt2(url2);
                rides.setOpt3(url3);
                rides.setName(name);
                rides.setPhotoURL(photoURL);
                rides.setUserid(uid);

                saveNewRide(rides.getStart(),rides.getFinish(),rides.getDate(),rides.getTime(),rides.getPrice(),rides.getPhone(),rides.getName(),rides.getPhotoURL(),rides.getOpt1(),rides.getOpt2(),rides.getOpt3(),rides.getUserid());
                Toast.makeText(v.getContext(),"Ride added!",Toast.LENGTH_SHORT).show();
            }
        });


//time picker
        txt_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txt_time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });


//date picker
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        txt_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });








//phone format
        txt_phone.addTextChangedListener(new TextWatcher()
        {

            public void afterTextChanged(Editable s)
            {

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after)
            {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count)
            {
                String text = txt_phone.getText().toString();
               int textlength = txt_phone.getText().length();

                if(text.endsWith(" "))
                    return;

                if(textlength == 3 || textlength == 7 || textlength == 11)
                {
                    txt_phone.setText(new StringBuilder(text).insert(text.length()-1, " ").toString());
                    txt_phone.setSelection(txt_phone.getText().length());
                }

            }});



      return v;
    }

    private void saveNewRide(String start, String finish, String date, String time, String price, String phone, String name, String photoURL, String opt1, String opt2, String opt3, String userid) {
       Rides rides = new Rides (start,finish,date,time,price,phone,name,photoURL,opt1,opt2,opt3,userid);

        mRef.child("rides").push().setValue(rides);
    }

    private boolean validateForm() {
        boolean valid = true;
        String to = txt_to.getText().toString();
        if (TextUtils.isEmpty(to)) {
            txt_to.setError("Required.");
            valid = false;
        } else {
            txt_to.setError(null);
        }
        String from = txt_from.getText().toString();
        if (TextUtils.isEmpty(from)) {
            txt_from.setError("Required.");
            valid = false;
        } else {
            txt_from.setError(null);
        }
        String date = txt_date.getText().toString();
        if (TextUtils.isEmpty(date)) {
            txt_date.setError("Required.");
            valid = false;
        } else {
            txt_date.setError(null);
        }
        String time = txt_time.getText().toString();
        if (TextUtils.isEmpty(time)) {
            txt_time.setError("Required.");
            valid = false;
        } else {
            txt_time.setError(null);
        }

        String price = txt_price.getText().toString();
        if (TextUtils.isEmpty(price)) {
            txt_price.setError("Required.");
            valid = false;
        } else {
            txt_price.setError(null);
        }
        String phone = txt_phone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            txt_phone.setError("Required.");
            valid = false;
        } else {
            txt_phone.setError(null);
        }
        return valid;
    }

//date picker
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txt_date.setText(sdf.format(myCalendar.getTime()));
    }
}




