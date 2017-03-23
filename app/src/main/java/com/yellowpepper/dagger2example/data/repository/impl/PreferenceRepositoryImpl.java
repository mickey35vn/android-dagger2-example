package com.yellowpepper.dagger2example.data.repository.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.yellowpepper.dagger2example.data.repository.PreferenceRepository;

/**
 * Created by mickey35vn on 3/23/17.
 */

public class PreferenceRepositoryImpl implements PreferenceRepository {

    private Context mContext;

    public PreferenceRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public void saveValue(String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString(key, value);
        e.apply();
    }

    @Override
    public void saveValue(String key, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putInt(key, value);
        e.apply();
    }

    @Override
    public void saveValue(String key, long value) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putLong(key, value);
        e.apply();
    }

    @Override
    public String getStringValue(String key, String defaultValue) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    @Override
    public int getIntValue(String key, int defaultValue) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    @Override
    public long getLongValue(String key, long defaultValue) {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    @Override
    public void clearCache(String pref) {
        SharedPreferences sp = mContext.getSharedPreferences(pref, 0);
        SharedPreferences.Editor e = sp.edit();
        e.clear();
        e.apply();
    }

}
