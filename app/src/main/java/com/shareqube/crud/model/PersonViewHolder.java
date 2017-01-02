package com.shareqube.crud.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shareqube.crud.MainActivity;
import com.shareqube.crud.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by judeebene on 12/26/16.
 */
public class PersonViewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{

    String LOG_TAG = PersonViewHolder.class.getSimpleName();

    View mView ;
    TextView firstName , lastName, phoneNumber , zipcode, dateOfBirth ,joined ;
    Button deleteBtn ;
    Context mContext ;
    String personkey ;
    Person mPerson;

    // Reference to firebase single person records.
    DatabaseReference singleRecordRef ;
    FirebaseUser currentUser;




    public PersonViewHolder(View viewItem){
        super(viewItem);

        mView = viewItem;
        itemView.setOnClickListener(this);
        mContext = viewItem.getContext() ;


    }

    public void bindPerson(Person person, final String personkey){
        this.personkey = personkey;

        this.mPerson = person;


           // initializing the views for recyclerview item

            firstName = (TextView) itemView.findViewById(R.id.firstNameItem);
            lastName = (TextView) itemView.findViewById(R.id.lastNameItem);
            phoneNumber = (TextView) itemView.findViewById(R.id.phoneNumberItem);
            zipcode = (TextView) itemView.findViewById(R.id.zipCodeItem);
            dateOfBirth= (TextView) itemView.findViewById(R.id.dateOfBirthItem) ;
            joined = (TextView)itemView.findViewById(R.id.joinedItem) ;
            deleteBtn =(Button)itemView.findViewById(R.id.deleteSingleItem);



        // handle item deletions from view holders
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                singleRecordRef = FirebaseDatabase.getInstance().getReference().child("persons").child(personkey);

                singleRecordRef.removeValue();

                Toast.makeText(mContext, "Person Deleted" , Toast.LENGTH_LONG).show();

            }
        });






            firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        phoneNumber.setText(person.getPhoneNumber());
        dateOfBirth.setText(person.getDateOfBirth());

        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");


      joined.setText("Date Joined" +sfd.format(new Date((long) person.getDateAdded())));





    }

    @Override
    public void onClick(View view) {

     // hanlde  item onclink

        Intent mainActivity = new Intent(mContext, MainActivity.class);
         mainActivity.putExtra("KEY", personkey);
        mainActivity.putExtra("FIRSTNAME", mPerson.getFirstName());
        mainActivity.putExtra("LASTNAME" , mPerson.getLastName());
        mainActivity.putExtra("ZIPCODE", mPerson.getZipCode());
        mainActivity.putExtra("PHONENUMBER" , mPerson.getPhoneNumber());
        mainActivity.putExtra("DATEOFBIRTH" , mPerson.getDateOfBirth());

         mContext.startActivity(mainActivity);




    }


}