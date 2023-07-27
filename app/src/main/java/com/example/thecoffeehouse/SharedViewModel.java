package com.example.thecoffeehouse;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> latestAddressLiveData = new MutableLiveData<>();

    public LiveData<String> getLatestAddressLiveData() {
        return latestAddressLiveData;
    }

    public void setLatestAddress(String address) {
        latestAddressLiveData.setValue(address);
    }

    private MutableLiveData<String> profileNameLiveData = new MutableLiveData<>();

    public void setProfileName(String name) {
        profileNameLiveData.setValue(name);
    }

    public LiveData<String> getProfileNameLiveData() {
        return profileNameLiveData;
    }
}
