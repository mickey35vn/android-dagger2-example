package com.yellowpepper.dagger2example.repository;

/**
 * Created by mickey35vn on 3/23/17.
 */

public interface PreferenceRepository {

    final String FILE_NAME = "YPPreferences";

    final String APP_CONFIG = "appConfig";
    final String NETWORK_CONFIG = "networkConfig";
    final String USER_NAME = "uniqueId";
    final String ENCRYPTED_PASSWORD = "encrypted_password";
    final String TEMP_PASSWORD = "temp_password";

    void saveValue(String key, String value);
    void saveValue(String key, int value);
    void saveValue(String key, long value);

    String getStringValue(String key, String defaultValue);
    int getIntValue(String key, int defaultValue);
    long getLongValue(String key, long defaultValue);

    void clearCache(String pref);

}
