package com.pranay.testandroid;


import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {

    private static final String KEY_NAME = "digitrss_key";
    View contentView;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_first, container, false);

        fingerprintManager = (FingerprintManager) getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
        keyguardManager = (KeyguardManager) getActivity().getSystemService(Context.KEYGUARD_SERVICE);

        if(!keyguardManager.isKeyguardSecure()){
            Toast.makeText(getContext(),"Lock Screen not configured",Toast.LENGTH_LONG).show();
        }

        if(getActivity().checkSelfPermission(Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getContext(),"Fingerprint Permission not given",Toast.LENGTH_LONG).show();
        }

        if(!fingerprintManager.hasEnrolledFingerprints()){
            Toast.makeText(getContext(),"Register at least one fingerprint in Settings",Toast.LENGTH_LONG).show();
        }

        generateKey();

        if(cipherInit()){
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            FingerPrintHandler fingerPrintHandler = new FingerPrintHandler(getContext());
            fingerPrintHandler.startAuth(fingerprintManager,cryptoObject);
        }

        return contentView;
    }

    protected void generateKey(){

        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(
                    new KeyGenParameterSpec.Builder(
                            KEY_NAME,
                            KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                            .setUserAuthenticationRequired(true)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                            .build()
            );
            keyGenerator.generateKey();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean cipherInit() {

        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/" +
                            KeyProperties.BLOCK_MODE_CBC + "/" +
                            KeyProperties.ENCRYPTION_PADDING_PKCS7
            );

            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
