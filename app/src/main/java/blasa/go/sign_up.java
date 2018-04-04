package blasa.go;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class sign_up extends AppCompatActivity {
private Button btn_signup,btn_clear ;
private ImageButton btn_female,btn_male;
private EditText txt_username,txt_email2,txt_password2,txt_car,txt_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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
            }
        });

        btn_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_female.setImageResource(R.drawable.female_40px);
                btn_male.setVisibility(View.INVISIBLE);
            }
        });
    }
}
