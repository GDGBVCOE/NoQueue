package com.example.vaibhavchellani.noqueue;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import Models.Queue;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vaibhavchellani on 9/27/17.
 */

public class CreateQueue extends AppCompatActivity {
    @BindView(R.id.queue_name) EditText mqueue_name;
    @BindView(R.id.org_name) EditText morg_name;
    @BindView(R.id.description_queue) EditText mdescription;
    @BindView(R.id.create_button) Button mCreateButton;
    private DatabaseReference mDatabase;
    String TAG="CreateQueue";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createqueuelayout);
        ButterKnife.bind(this);


        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String description=mdescription.getText().toString();
                String org_name=morg_name.getText().toString();
                String queue_name=mqueue_name.getText().toString();
                mDatabase= FirebaseDatabase.getInstance().getReference();
                Queue newQueue=new Queue(description,queue_name,org_name);
                DatabaseReference QueueRef=mDatabase.child("queues").push();
                Log.d(TAG, String.valueOf(QueueRef));
                QueueRef.setValue(newQueue);
                Toast.makeText(CreateQueue.this, "Queue Created ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


    }

}
