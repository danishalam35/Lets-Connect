package com.example.letsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class MeetingActivity extends AppCompatActivity {

    EditText secretCode;
    Button join;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);


        secretCode=findViewById(R.id.codeBox);
        join=findViewById(R.id.joinBtn);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait....");
        URL server;

        try {
            server=new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOption=new
                    JitsiMeetConferenceOptions.Builder()
                    .setServerURL(server)
                    .setAudioMuted(false)
                    .setVideoMuted(false)
                    .setAudioOnly(false)
                    .setWelcomePageEnabled(false)
                    .build();

            JitsiMeet.setDefaultConferenceOptions(defaultOption);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }




        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                JitsiMeetConferenceOptions options=new JitsiMeetConferenceOptions.Builder()
                        .setRoom(secretCode.getText().toString())//secret code
                        .setWelcomePageEnabled(false)
                        .build();

                JitsiMeetActivity.launch(MeetingActivity.this,options);

            }
        });
    }
}