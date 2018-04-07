### API Cryptographer 
The api cryptographer library encrypt and decrypt the String Using AES and RSA algorithm.
* AES algo use for the encrypting and decrypting big length of data while RSA algo use for short length of data.
* For increasing the security of big length of data we can also use RSA for encrypt the symmetric key then use this key for AES algo. 
#### Impelementation steps

 - Copy folder in the project root directory.</br>
 for example </br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Project_Name/cryptographer  
- Now go in the <span style="color:green">settings.gradle</span> file and replace 

```sh
include ':app'
```
with 

```sh
include ':app',':cryptographer'
```

- Open app's <span style="color:green">build.gradle</span> file and add the <span style="color:blue">compile project <span style="color:green">(path:':logger')</span></span> line in depencies module such as : </br> 

```sh
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:25.1.0'
    compile 'com.android.support:design:25.1.0'
    compile project(path: ':cryptographer')

}
```
#### AES algorithm xform description

 This library support following xform for AES algorithm </br>
 - AES/CBC/NoPadding 
 - AES/CBC/PKCS5Padding 
 - AES/ECB/NoPadding 
 - AES/ECB/PKCS5Padding
 - AES/GCM/NoPadding

    
#### RSA algorithm xform description

 This library support following xform for RSA algorithm</br>
 
 - RSA/NONE/PKCS1PADDING 
 - RSA/ECB/PKCS1PADDING
 - RSA/ECB/OAEPWithSHA-1AndMGF1Padding 
 - RSA/ECB/OAEPWithSHA-256AndMGF1Padding

#### How use this API:
#### For Encrypt and Decrypt String using AES algo:
    
- Sample for encrypt String using AES algo is shown below
    ```sh
     String xForm = CryptographerConstants.AESxForm.AES_CBC_PKCS5PADDING;
     String encString = Cryptographer.encryptAES(inputString, key,xForm);

     ```
- Sample for decrypt the string using AES algo is shown below
     ```sh
     String decryptedString = Cryptographer.decryptAES(encString, key,xForm);
     ```
     
#### For Encrypt and Decrypt String using RSA algo:
    
- Sample for encrypt String using RSA algo is shown below
    ```sh
      String xForm = CryptographerConstants.RSAxForm.RSA_ECB_PKCS1PADDING;
      String encryptS = Cryptographer.encryptRSA(inputString, pubk, xform);

     ```
- Sample for decrypt the string using RSA algo is shown below
     ```sh
     String decryptS= Cryptographer.decryptRSA(encryptS, prvk, xform);
     ```