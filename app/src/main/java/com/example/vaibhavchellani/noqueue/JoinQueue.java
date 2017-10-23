package com.example.vaibhavchellani.noqueue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;

import Models.Token;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vaibhavchellani on 10/1/17.
 * this activity should be called when user wants to get into a queue , the queue id must be provided
 */

public class JoinQueue extends AppCompatActivity {

    @BindView(R.id.textView1)
    TextView mTextView1;

    @BindView(R.id.textView2)
    TextView mTextView2;

    @BindView(R.id.enrollButton)
    Button enrollButton;

    private DatabaseReference mdatabase;
    SharedPreferences msharedPrefs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joinqueue);
        ButterKnife.bind(this);
        mdatabase= FirebaseDatabase.getInstance().getReference();
        msharedPrefs=getSharedPreferences("NoQueue", Context.MODE_PRIVATE);
        //replace the child id with the one we get from recycler view
        // TODO : queue_id to be replaced
        final String queue_id="-KwtZ07j-UBk9vssAo6w";
        final String user_id=msharedPrefs.getString(queue_id,"");
        final DatabaseReference queue_ref=mdatabase.child("queues").child(queue_id);
        final int[] no_of_users = new int[1];
        final int[] latest_token = new int[1];
        //added demo working of current token and need queue start date and time to make it work properly
        final int[] current_token = new int[1];
        //no idea why have used value event listener instead of child event listener , if someone feels child event listener is better create an issue
        queue_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //// TODO: 10/12/17 get all the info about the queue and populate front end with them , see example below
                mTextView1.setText(dataSnapshot.child("name_of_queue").getValue(String.class));
                no_of_users[0] =dataSnapshot.child("no_of_tokens").getValue(Integer.class);
                latest_token[0]=dataSnapshot.child("latest_token").getValue(Integer.class);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        queue_ref.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //TODO : get user list to the current tokens and the next 2 tokens
                /** you could probably use orderbyChild() function provided by firebase for sorting results
                 * see here ->https://firebase.google.com/docs/database/android/lists-of-data?#sort_data
                 */

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Token newToken=new Token(latest_token[0]+1);
                DatabaseReference setTokenRef=queue_ref.child("users").push();
                //todo save the "setTokenRef" in sharedPrefs with the respective queue id
                SharedPreferences.Editor editor =msharedPrefs.edit();
                editor.putString(queue_id,setTokenRef.toString());
                editor.commit();
                setTokenRef.setValue(newToken);
                queue_ref.child("latest_token").setValue(latest_token[0]+1);
                queue_ref.child("no_of_tokens").setValue(no_of_users[0]+1);

                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });


    }
}
