package com.yellowpepper.dagger2example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mickey35vn on 3/23/17.
 */

public class AppConfig {

    private List<String> allowedVersions = new ArrayList<>();
    private List<String> allowVersionNames = new ArrayList<>();
    private boolean paymentEnabled;
    private int smartWalletAcceptedID;
    private boolean prepaymentEnabled;
    private double prepaymentMax;
    private String publicKey;
    private String publicExp;

    public List<String> getAllowedVersions() {
        return allowedVersions;
    }

    public List<String> getAllowVersionNames() {
        return allowVersionNames;
    }

    public boolean isPaymentEnabled() {
        return paymentEnabled;
    }

    public int getSmartWalletAcceptedID() {
        return smartWalletAcceptedID;
    }

    public boolean isPrepaymentEnabled() {
        return prepaymentEnabled;
    }

    public double getPrepaymentMax() {
        return prepaymentMax;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPublicExp() {
        return publicExp;
    }

}
