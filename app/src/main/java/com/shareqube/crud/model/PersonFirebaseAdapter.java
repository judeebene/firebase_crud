package com.shareqube.crud.model;

import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by judeebene on 12/26/16.
 *
 * Implementing Firebase Adapter Using firebase UI;
 */

public class PersonFirebaseAdapter extends FirebaseRecyclerAdapter<Person, PersonViewHolder> {

    String LOG_TAG = PersonFirebaseAdapter.class.getSimpleName() ;



    DatabaseReference curentUserLocationRef ;
    FirebaseUser currentUser;
    public PersonFirebaseAdapter(Class<Person> modelClass, int modelLayout, Class<PersonViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);

    }

    @Override
    protected void populateViewHolder(final PersonViewHolder personViewHolder, final Person person, int position) {



        final String  placeKey = getRef(position).getKey();





                    personViewHolder.bindPerson(person, placeKey );






    }

    @Override
    public void onViewRecycled(PersonViewHolder holder) {
        super.onViewRecycled(holder);
//
    }
}
