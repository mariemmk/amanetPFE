package com.example.amanetpfe.utils;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import javax.security.auth.x500.X500Principal;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

public class RsaKeyStore {

    
    public static RSAPrivateKey generateKey() throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        KeyStore keyStore = KeyStore.getInstance("JKS");
        char[] password = "trador2023".toCharArray();
        keyStore.load(null, password);

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey storedPrivateKey = keyFactory.generatePrivate(keySpec);

        Certificate certificate = generateCertificate(keyPair);

        keyStore.setKeyEntry("myKey", storedPrivateKey, password, new Certificate[]{certificate});

        FileOutputStream fos = new FileOutputStream("keystore.jks");
        keyStore.store(fos, password);
        fos.close();

        FileInputStream fis = new FileInputStream("keystore.jks");
        keyStore.load(fis, password);
        fis.close();

        char[] keyPassword = "trador2023".toCharArray();
        Key key = keyStore.getKey("mykey", keyPassword);
        RSAPrivateKey retrievedPrivateKey = (RSAPrivateKey) key;

        return retrievedPrivateKey;
    }


    
    public static X509Certificate generateCertificate(KeyPair keyPair) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // Generate a self-signed X.509 certificate
        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
        X500Principal dnName = new X500Principal("CN=Trador");

        certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        certGen.setSubjectDN(dnName);
        certGen.setIssuerDN(dnName); // Use the same for issuer and subject
        certGen.setNotBefore(new Date());
        certGen.setNotAfter(new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000)); // Valid for 1 year
        certGen.setPublicKey(keyPair.getPublic());
        certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");

        PrivateKey privateKey = keyPair.getPrivate();

        return certGen.generate(privateKey, "BC");
    }
}
