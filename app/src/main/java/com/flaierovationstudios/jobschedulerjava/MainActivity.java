package com.flaierovationstudios.jobschedulerjava;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    ComponentName componentName;
    Button scheduleButton, cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduleButton= findViewById(R.id.btnScheduleButton);
        cancelButton= findViewById(R.id.btnCancelButton);


        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleJob();
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelJob();
            }
        });



    }

    public void scheduleJob() {

        Log.d(TAG, "scheduleJob() called ");
        componentName = new ComponentName(this, ExampleJobService.class );
        JobInfo jobInfo= new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler schedulers= (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int resultCode= 0;

        resultCode = schedulers.schedule(jobInfo);

        if (resultCode== JobScheduler.RESULT_SUCCESS){

            Log.d(TAG, "Job Scheduled");

        }else {
            Log.d(TAG, "Job Scheduling failed");

        }

    }

    public void cancelJob() {

        JobScheduler scheduler= (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        if (scheduler != null) {
            scheduler.cancel(123);
        }
        Log.d(TAG, "Job Cancelled");

    }
}
