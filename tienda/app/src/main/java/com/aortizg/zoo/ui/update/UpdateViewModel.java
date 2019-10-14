package com.aortizg.zoo.ui.update;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UpdateViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UpdateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Modificar Producto");
    }

    public LiveData<String> getText() {
        return mText;
    }
}