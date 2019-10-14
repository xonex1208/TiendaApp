package com.aortizg.zoo.ui.create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CreateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Registro de productos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}