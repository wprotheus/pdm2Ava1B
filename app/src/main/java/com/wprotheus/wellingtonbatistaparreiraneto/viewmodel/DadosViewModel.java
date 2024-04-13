package com.wprotheus.wellingtonbatistaparreiraneto.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wprotheus.wellingtonbatistaparreiraneto.model.Stone;

import java.util.List;

import lombok.Getter;

@Getter
public class DadosViewModel extends ViewModel {
    private MutableLiveData<Stone> mLiveOriginal;
    private MutableLiveData<List<String>> mLiveMixed;
    private MutableLiveData<String> mLiveNomeUser;

    public DadosViewModel() {
        mLiveOriginal = new MutableLiveData<>();
        mLiveMixed = new MutableLiveData<>();
        mLiveNomeUser = new MutableLiveData<>();
    }

    public MutableLiveData<Stone> getmLiveOriginal() {
        return mLiveOriginal;
    }

    public MutableLiveData<List<String>> getmLiveMixed() {
        return mLiveMixed;
    }

    public MutableLiveData<String> getmLiveNomeUser() {
        return mLiveNomeUser;
    }
}