package com.sptas.sptasv2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sptas.sptasv2.BroadcastReceiver.AlarmReceiver;
import com.sptas.sptasv2.Common.Common;
import com.sptas.sptasv2.Model.User;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    MaterialEditText edtNewUserType, edtNewUser, edtNewPassword, edtNewEmail, edtNewNickName; //for Sign Up
    MaterialEditText edtUser, edtPassword; //for Sign In

    Button btnSignUp, btnSignIn;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_main);

        registerAlarm();

        //Firebase
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);

        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignUp = findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignUpDialog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(edtUser.getText().toString(), edtPassword.getText().toString());
            }
        });

    }

    private void registerAlarm() {
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.HOUR_OF_DAY, 23); // 9 Hour
        calender.set(Calendar.MINUTE, 10);
        calender.set(Calendar.SECOND, 0);

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void signIn(final String user, final String pwd) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user).exists()) {
                    if (!user.isEmpty()) {
                        User login = dataSnapshot.child(user).getValue(User.class);
                        if (login.getPassword().equals(pwd)) {
                            Intent homeActivity;
                            if (login.getUserType().equals("Lecturer")) {
                                homeActivity = new Intent(MainActivity.this, LecturerActivity.class);
                                Common.currentUser = login;
                                startActivity(homeActivity);
                                finish();
                            } else {
                                homeActivity = new Intent(MainActivity.this, StudentActivity.class);
                                Common.currentUser = login;
                                startActivity(homeActivity);
                                finish();
                            }
                        } else
                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Please enter your user name", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(MainActivity.this, "User is not exists! ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showSignUpDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Sign Up");
        alertDialog.setMessage("Please fill full Information");

        LayoutInflater inflater = this.getLayoutInflater();
        View sign_up_layout = inflater.inflate(R.layout.student_sign_up_layout, null);

        edtNewUser = sign_up_layout.findViewById(R.id.edtNewUserName);
        edtNewPassword = sign_up_layout.findViewById(R.id.edtNewPassword);
        edtNewEmail = sign_up_layout.findViewById(R.id.edtNewEmail);
        edtNewNickName = sign_up_layout.findViewById(R.id.edtNewNickName);

        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final User user = new User("Student",
                        edtNewUser.getText().toString(),
                        edtNewPassword.getText().toString(),
                        edtNewEmail.getText().toString(),
                        edtNewNickName.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(user.getUserName()).exists())
                            Toast.makeText(MainActivity.this, "User already exists! ", Toast.LENGTH_SHORT).show();
                        else {
                            users.child(user.getUserName())
                                    .setValue(user);
                            Toast.makeText(MainActivity.this, "User registration success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }
}
