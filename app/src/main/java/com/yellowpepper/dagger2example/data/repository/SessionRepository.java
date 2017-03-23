package com.yellowpepper.dagger2example.data.repository;

/**
 * Created by mickey35vn on 3/23/17.
 */

public interface SessionRepository {

    String getUserName();
    void setUserName(String userName);

    String getTempPassword();
    void setTempPassword(String password);

    String getEncryptedPassword();
    void setEncryptedPassword(String encryptedPassword);

}