package com.ranosys.encryptiondecryptiondemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ranosys.cryptographer.Cryptographer;
import com.ranosys.cryptographer.CryptographerConstants;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *  It is sample class that show how to encrypt and decrypt String using AES and DES algo
 */
public class SampleActivity extends AppCompatActivity {

    private final String TAG = "SampleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
    }

    //It is the method that call when click on button
    public void doEncryptionUSingAES(View v) {
        startActivity(new Intent(this,AesDescriptionSample.class));
    }

    //it is method call when we click on RES Encryption and Decryption Button
    public void doEncryptionUSingRSA(View v)
    {
       startActivity(new Intent(this,RSADescription.class));
    }





}
