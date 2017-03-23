package com.yellowpepper.dagger2example.repository.impl;

import com.yellowpepper.dagger2example.repository.PreferenceRepository;
import com.yellowpepper.dagger2example.repository.SessionRepository;

import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by mickey35vn on 3/23/17.
 */

public class SessionRepositoryImpl implements SessionRepository {

    private PreferenceRepository mPreferenceRepository;

    @Inject
    public SessionRepositoryImpl(PreferenceRepository preferenceRepository) {
        mPreferenceRepository = preferenceRepository;
    }

    @Override
    public String getUserName() {
        String userName = mPreferenceRepository.getStringValue(PreferenceRepository.USER_NAME, "");
        if(userName.isEmpty()){
            userName = UUID.randomUUID().toString();
            setUserName(userName);
        }
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        mPreferenceRepository.saveValue(PreferenceRepository.USER_NAME, userName);
    }

    @Override
    public String getTempPassword() {
        return mPreferenceRepository.getStringValue(PreferenceRepository.TEMP_PASSWORD, "");
    }

    @Override
    public void setTempPassword(String password) {
        mPreferenceRepository.saveValue(PreferenceRepository.TEMP_PASSWORD, password);
    }

    @Override
    public String getEncryptedPassword() {
        return mPreferenceRepository.getStringValue(PreferenceRepository.ENCRYPTED_PASSWORD, "");
    }

    @Override
    public void setEncryptedPassword(String encryptedPassword) {
        mPreferenceRepository.saveValue(PreferenceRepository.ENCRYPTED_PASSWORD, encryptedPassword);
    }

}
