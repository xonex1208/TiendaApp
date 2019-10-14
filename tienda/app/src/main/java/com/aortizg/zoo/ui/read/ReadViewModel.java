package com.aortizg.zoo.ui.read;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aortizg.zoo.R;

public class ReadViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReadViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Listar Productos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}