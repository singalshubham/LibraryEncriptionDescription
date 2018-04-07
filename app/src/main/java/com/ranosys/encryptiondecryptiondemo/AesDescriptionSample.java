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

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AesDescriptionSample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aes_description_sample);
        final EditText editText = (EditText) findViewById(R.id.et_encrypt_message);
        final TextView tv_encrpted_meeage= (TextView) findViewById(R.id.tv_encrypted_message);
        final TextView tvDecryptMessage= (TextView) findViewById(R.id.tv_decrypt_message);
        final String[] encString = new String[1];
        // It is secret key that is use for encryption and decryption
        final SecretKey key = generateKeyForAES();

        findViewById(R.id.btn_encrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().isEmpty())
                {
                    try {
                        //here we pass inputString,key and "AES/CBC/PKCS5Padding" transfromation form and get encrypted String using AES
                        encString[0] = Cryptographer.encryptAES(editText.getText().toString(), key, CryptographerConstants.AESxForm.AES_CBC_PKCS5PADDING);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    tv_encrpted_meeage.setText(encString[0]);
                }
                else
                {
                    Toast.makeText(AesDescriptionSample.this, "enter message to decrypt",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btn_decrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_encrpted_meeage.getText().length()==0)
                {
                    Toast.makeText(AesDescriptionSample.this, "first encrypt the message", Toast.LENGTH_SHORT).show();
                }
                else {
                    String decryptedString = null;
                    try {
                        //here we pass encrypted String ,key(same as use for encryption), and transformation form (same as use for encryption)
                        decryptedString = Cryptographer.decryptAES(encString[0], key, CryptographerConstants.AESxForm.AES_CBC_PKCS5PADDING);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tvDecryptMessage.setText(decryptedString);

                }
            }
        });
    }


    /**
     *  It is method that is generate a key for AES algo that is use for encryption and decryption
     *  here we generate 256 bit key we can also generate 128,192,256 according to requirement
     * @return It return Secret Key
     */
    private SecretKey generateKeyForAES() {
        // Generate a secret key
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        kg.init(256);
        return  kg.generateKey();
    }
}
