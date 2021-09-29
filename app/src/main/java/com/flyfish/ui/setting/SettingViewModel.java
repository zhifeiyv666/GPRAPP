package com.flyfish.ui.setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SettingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("嘿！哥们！你不知道这个页面的内容需要付费解锁么？");
    }

    public LiveData<String> getText() {
        return mText;
    }
}