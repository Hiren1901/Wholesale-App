package com.example.wholesalewale.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.wholesalewale.Worker.LikeItemworker;
import com.google.firebase.auth.FirebaseUser;

public class likesmodel extends ViewModel {
    public likesmodel(WorkManager mWorkManager) {
        this.mWorkManager = mWorkManager;
        // Initialize your ViewModel with the WorkManager instance
    }
    private String user;
    String TAG = "work";
    String  lastItem;
    Application application;
    OneTimeWorkRequest oneTimeWorkRequest;
    public likesmodel(Application application) {

      //  workInfo = mWorkManager.getWorkInfosByTagLiveData(TAG);
        this.application = application;
    }

    private WorkManager mWorkManager;

    public LiveData<WorkInfo> getWorkInfo() {
        return workInfo;
    }

    LiveData<WorkInfo> workInfo;

    public String getUser() {
        return user;
    }

    public void setUser(String user,String lastItem) {
        this.user = user;
        this.lastItem=lastItem;
    }

    @SuppressLint({"SuspiciousIndentation", "RestrictedApi"})
    public void getLikeData() {

    oneTimeWorkRequest=new OneTimeWorkRequest.Builder(LikeItemworker.class).setInputData(createInputDataForUri()).build();
        mWorkManager.enqueue(oneTimeWorkRequest);
    workInfo=  mWorkManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.getId());
    }

    @SuppressLint("RestrictedApi")
    private Data createInputDataForUri() {
        Data.Builder builder = new Data.Builder();
        if (user != null) {
            builder.put("user", getUser());

            if(lastItem!=null){
                builder.put("lastitem",lastItem);
            }

        }
        return builder.build();
    }

}
