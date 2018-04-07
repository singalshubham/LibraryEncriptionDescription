package com.ranosys.encryptiondecryptiondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ranosys.cryptographer.Cryptographer;
import com.ranosys.cryptographer.CryptographerConstants;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

public class RSADescription extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aes_description_sample);

        final String[] encryptS = {null};

        // String xform = "RSA/NONE/PKCS1PADDING";
        // String xform = "RSA/ECB/PKCS1PADDING";
        //String xform = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
        //  String xform = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

        final String xForm=CryptographerConstants.RSAxForm.RSA_ECB_PKCS1PADDING;
        //here we generate pair of key for RSA algo
        KeyPair kp = generateKeyPAirForRSA();
        //this is public key use for encryption using RSA algo
        final PublicKey pubk = kp.getPublic();
        //this is private key use for decryption using RSA algo
        final PrivateKey prvk = kp.getPrivate();


        final EditText editText = (EditText) findViewById(R.id.et_encrypt_message);
        final TextView tv_encrpted_meeage= (TextView) findViewById(R.id.tv_encrypted_message);
        final TextView tvDecryptMessage= (TextView) findViewById(R.id.tv_decrypt_message);


        findViewById(R.id.btn_encrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().isEmpty())
                {
                    try {
                        //here we pass inputString,key and "RSA/ECB/PKCS1PADDING" transfromation form and get encrypted String using RSA
                        encryptS[0] = Cryptographer.encryptRSA(editText.getText().toString(), pubk, xForm);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    tv_encrpted_meeage.setText(encryptS[0]);
                }
                else
                {
                    Toast.makeText(RSADescription.this, "enter message to decrypt",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btn_decrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_encrpted_meeage.getText().length()==0)
                {
                    Toast.makeText(RSADescription.this, "first encrypt the message", Toast.LENGTH_SHORT).show();
                }
                else {
                    String decryptedString = null;
                    try {
                        //here we pass encryptedStringf,privatekey and same tranformtion use fro encrypt using RSA algo
                        decryptedString= Cryptographer.decryptRSA(encryptS[0], prvk, xForm);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tvDecryptMessage.setText(decryptedString);

                }
            }
        });
    }

    private KeyPair generateKeyPAirForRSA() {
        // Generate a key-pair
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        kpg.initialize(512); // 512 ,1024,2048 is the keysize.
        return kpg.generateKeyPair();
    }
}
