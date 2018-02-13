package com.cky.foodorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cky.foodorder.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {

    private MaterialEditText edtPhone, edtName, edtPassword;
    private Button btnSignUp;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtPhone = findViewById(R.id.edtPhone);
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);

        btnSignUp = findViewById(R.id.btnSignUp);

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*final ProgressBar progressBar = new ProgressBar(SignUpActivity.this,null, android.R.attr.progressBarStyleLarge);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                relativeLayout.addView(progressBar,params);
                progressBar.setVisibility(View.VISIBLE);
*/
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user has registered
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
//                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignUpActivity.this,"This phone number has been registered.", Toast.LENGTH_SHORT).show();

                            if (edtPhone.getText().toString().isEmpty() || edtName.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
//                            progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(SignUpActivity.this,"Please fill in everything.", Toast.LENGTH_SHORT).show();
                            }
                        } else  {
//                            progressBar.setVisibility(View.INVISIBLE);
                            User user = new User(edtName.getText().toString(), edtPassword.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Successfully signed up", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
//                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SignUpActivity.this,"Database error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
