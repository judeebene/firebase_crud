package com.shareqube.crud;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.shareqube.crud.model.Person;
import com.shareqube.crud.model.PersonFirebaseAdapter;
import com.shareqube.crud.model.PersonViewHolder;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String LOG_TAG = MainActivity.class.getSimpleName();

    EditText firstName, lastName , phoneNumber , zipCode ;
    EditText dateOfBirth;
    RecyclerView recordList;
   LinearLayout add, update ;



    // simple firebase adapter
    PersonFirebaseAdapter personFirebaseAdapter;

    //Firebase recored Data  ref

    DatabaseReference personRecordRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //initialize the views
        firstName = (EditText)findViewById(R.id.firstNameEditText);
        lastName = (EditText)findViewById(R.id.lastNameEditText);
        phoneNumber = (EditText) findViewById(R.id.phoneEditText);
        zipCode = (EditText)findViewById(R.id.zipCodeEditText);
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirth) ;

        recordList = (RecyclerView) findViewById(R.id.recordedList);
        recordList.setHasFixedSize(true);
        recordList.setLayoutManager(new LinearLayoutManager(this));

        update = (LinearLayout) findViewById(R.id.update);
        add = (LinearLayout)findViewById(R.id.add) ;


        // Intent Extra to populate the edit views for update
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
           String key =  bundle.getString("KEY");
            String firstNameStr = bundle.getString("FIRSTNAME");
            String lastNameStr = bundle.getString("LASTNAME");
            String zipcodeStr = bundle.getString("ZIPCODE");
            String phoneNumberStr = bundle.getString("PHONENUMBER");
            String dateOfBirthStr = bundle.getString("DATEOFBIRTH");

            Log.e(LOG_TAG , "key" + key + "firstname" + firstName + lastName + zipcodeStr);

            firstName.setText(firstNameStr);
            lastName.setText(lastNameStr);
            zipCode.setText(zipcodeStr);
            phoneNumber.setText(phoneNumberStr);
            dateOfBirth.setText(dateOfBirthStr);
            update.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
        }





        personRecordRef = FirebaseDatabase.getInstance().getReference().child("persons");


        // set the firebase adapter

        personFirebaseAdapter= new PersonFirebaseAdapter(Person.class, R.layout.record_item, PersonViewHolder.class, personRecordRef);


        personFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                personFirebaseAdapter.notifyDataSetChanged();
            }


        });


        recordList.setAdapter(personFirebaseAdapter);





        // handle listeners

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addPerson();
            }
        });


    }

    private void deletePerson() {
    }

    //add person helper
    private void addPerson() {

        String firstNameStr = firstName.getText().toString();
        String lastNameStr = lastName.getText().toString();
        String zipCodeStr = zipCode.getText().toString();
        String phoneNumberStr = phoneNumber.getText().toString();
        String dateOfBirthStr = dateOfBirth.getText().toString();

        if(TextUtils.isEmpty(firstNameStr)) {
            firstName.setError("is empty");
            return;
        }
        if(TextUtils.isEmpty(lastNameStr)) {
            lastName.setError("is empty");
            return;
        }
        if(TextUtils.isEmpty(zipCodeStr)) {
            zipCode.setError("is empty");
            return;
        }
        if(TextUtils.isEmpty(phoneNumberStr)) {
            phoneNumber.setError("is empty");
            return;
        }
        if(TextUtils.isEmpty(dateOfBirthStr)) {
            dateOfBirth.setError("is empty");
            return;
        }

        Person person = new Person(firstNameStr,lastNameStr,phoneNumberStr,zipCodeStr,dateOfBirthStr, ServerValue.TIMESTAMP);
        Map<String,Object> personMap = person.convertToMap();

        personRecordRef.push().updateChildren(personMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if(databaseError == null){
                    Toast.makeText(getApplicationContext(), "Person added" , Toast.LENGTH_LONG).show();
                    firstName.setText("");
                    lastName.setText("");
                    phoneNumber.setText("");
                    zipCode.setText("");

                    personFirebaseAdapter.notifyDataSetChanged();
                }

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        signInAnonymously();
    }

    // to handle firebase  anonymous sign in;
    public void signInAnonymously() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            mAuth.getCurrentUser().reload();
        } else {
            mAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                @Override
                public void onSuccess(AuthResult authResult) {

                    Log.e(LOG_TAG, "Sign in" + authResult.getUser().getProviderId());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Log.e(LOG_TAG, "Faillllllll" + e.getMessage());

                }
            });

        }





    }

}
