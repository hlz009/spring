package com.xz.authWeb.util.signature;
 
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import javax.security.auth.x500.X500Principal;
import sun.misc.BASE64Decoder;
import sun.security.pkcs.ContentInfo;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;
import sun.security.x509.AlgorithmId;
import sun.security.x509.X500Name;
 
public class DigitalSignTool {
    private static final int SIGNER = 1;
    private static final int VERIFIER = 2;
   
    private DigitalSignTool(int mode) {
    	this.mode = 0;
    	this.digestAlgorithm = "SHA1";
    	this.signingAlgorithm = "SHA1withRSA";
    	this.certificates = null;
    	this.privateKey = null;
    	this.rootCertificate = null;
    	this.mode = mode;
   }
    private int mode; private String digestAlgorithm; private String signingAlgorithm;
    private X509Certificate[] certificates;
   
    public static DigitalSignTool getSigner(String keyStorePath, String keyStorePassword, 
		   String keyPassword) throws GeneralSecurityException, IOException {
	    KeyStore keyStore = null;
	    if (keyStorePath.toLowerCase().endsWith(".pfx")) {
	    	keyStore = KeyStore.getInstance("PKCS12");
	    } else {
	    	keyStore = KeyStore.getInstance("JKS");
	    }  FileInputStream fis = null;
	    try {
	    	fis = new FileInputStream(keyStorePath);
	    	keyStore.load(fis, keyStorePassword.toCharArray());
	    } finally {
	    	if (fis != null) {
	    		fis.close();
	    	}
	    } 
	    Enumeration<String> aliases = keyStore.aliases();
	    String keyAlias = null;
	    if (aliases != null) {
	    	while (aliases.hasMoreElements()) {
	    		keyAlias = (String)aliases.nextElement();
	    		Certificate[] certs = keyStore.getCertificateChain(keyAlias);
	    		if (certs == null || certs.length == 0)
	    			continue; 
	    		X509Certificate cert = (X509Certificate)certs[0];
	    		if (!matchUsage(cert.getKeyUsage(), 1))
	    			continue;  try {
	    				cert.checkValidity();
	    			}
	    			catch (CertificateException certificateException) {}
	    	} 
	    }
 

	    if (keyAlias == null) {
	    	throw new GeneralSecurityException("None certificate for sign in this keystore");
	    }
	    X509Certificate[] certificates = (X509Certificate[])null;
	    if (keyStore.isKeyEntry(keyAlias)) {
	    	Certificate[] certs = keyStore.getCertificateChain(keyAlias);
	    	for (int i = 0; i < certs.length; i++) {
	    		if (!(certs[i] instanceof X509Certificate)) {
	    			throw new GeneralSecurityException("Certificate[" + i + "] in chain '" + keyAlias + "' is not a X509Certificate.");
	    		}
	    	} 
	    	certificates = new X509Certificate[certs.length];
	    	for (int i = 0; i < certs.length; i++)
	    		certificates[i] = (X509Certificate)certs[i]; 
	    } else if (keyStore.isCertificateEntry(keyAlias)) {
	    	Certificate cert = keyStore.getCertificate(keyAlias);
	    	if (cert instanceof X509Certificate) {
	    		certificates = new X509Certificate[] { (X509Certificate)cert };
	    	}
	    } else {
	    	throw new GeneralSecurityException(String.valueOf(keyAlias) + " is unknown to this keystore");
	    } 
     
	    PrivateKey privateKey = (PrivateKey)keyStore.getKey(keyAlias, keyPassword.toCharArray());
     
	    if (privateKey == null) {
	    	throw new GeneralSecurityException(String.valueOf(keyAlias) + " could not be accessed");
	    }
	    DigitalSignTool tool = new DigitalSignTool(1);
	    tool.certificates = certificates;
	    tool.privateKey = privateKey;
	    return tool;
    }
    private PrivateKey privateKey;
    private Certificate rootCertificate;
   
    public static DigitalSignTool getVerifier(String rootCertificatePath) throws GeneralSecurityException, IOException {
    	FileInputStream fis = null;
    	Certificate rootCertificate = null;
    	try {
    		fis = new FileInputStream(rootCertificatePath);
    		CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
    		try {
    			rootCertificate = certificatefactory.generateCertificate(fis);
    		}
    		catch (Exception exception) {
    			BASE64Decoder base64decoder = new BASE64Decoder();
    			InputStream is = new ByteArrayInputStream(base64decoder.decodeBuffer(fis));
    			rootCertificate = certificatefactory.generateCertificate(is);
    		} 
    	} finally {
    		if (fis != null) {
    			fis.close();
    		}
    	} 
    	DigitalSignTool tool = new DigitalSignTool(2);
    	tool.rootCertificate = rootCertificate;
    	return tool;
   }

    public String sign(byte[] data) throws GeneralSecurityException, IOException {
    	if (this.mode != 1) {
    		throw new IllegalStateException("call a PKCS7Tool instance not for signature.");
    	}
    	Signature signer = Signature.getInstance(this.signingAlgorithm);
    	signer.initSign(this.privateKey);
    	signer.update(data, 0, data.length);
    	byte[] signedAttributes = signer.sign();
     
    	ContentInfo contentInfo = null;
     
    	contentInfo = new ContentInfo(ContentInfo.DATA_OID, null);
     
    	X509Certificate x509 = this.certificates[this.certificates.length - 1];
     
    	BigInteger serial = x509.getSerialNumber();
     
    	SignerInfo si = new SignerInfo(new X500Name(x509.getIssuerDN().getName()), 
    			serial, 
    			AlgorithmId.get(this.digestAlgorithm), 
    			null, 
    			new AlgorithmId(AlgorithmId.RSAEncryption_oid), 
    			signedAttributes, 
    			null);
     
    	SignerInfo[] signerInfos = { si };
     
    	AlgorithmId[] digestAlgorithmIds = { AlgorithmId.get(this.digestAlgorithm) };
    	PKCS7 p7 = new PKCS7(digestAlgorithmIds, contentInfo, this.certificates, signerInfos);
     
    	ByteArrayOutputStream baout = new ByteArrayOutputStream();
    	p7.encodeSignedData(baout);
     
    	return Base64.encode(baout.toByteArray());
   }
 
 
   
    public void verify(String signature, byte[] data, String dn) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateException, NoSuchProviderException {
    	if (this.mode != 2) {
    		throw new IllegalStateException("call a PKCS7Tool instance not for verify.");
     }
    	PKCS7 p7 = new PKCS7(Base64.decode(signature));
     
    	SignerInfo[] sis = p7.verify(data);
     
    	if (sis == null) {
    		throw new SignatureException("Signature failed verification, data has been tampered");
    	}
    	for (int i = 0; i < sis.length; i++) {
    		SignerInfo si = sis[i];
       
    		X509Certificate cert = si.getCertificate(p7);
       
    		cert.checkValidity();
       
    		cert.verify(this.rootCertificate.getPublicKey());
       
    		if (i == 0 && dn != null) {
    			X500Principal name = cert.getSubjectX500Principal();
    			if (!dn.equals(name.getName("RFC1779")) && !(new X500Principal(dn)).equals(name)) {
    				throw new SignatureException("Signer dn '" + name.getName("RFC1779") + "' does not matchs '" + dn + "'");
    			}
    		} 
    	} 
    }
   
    private static boolean matchUsage(boolean[] keyUsage, int usage) {
    	if (usage == 0 || keyUsage == null)
    		return true; 
    	for (int i = 0; i < Math.min(keyUsage.length, 32); i++) {
    		if ((usage & 1 << i) != 0 && keyUsage[i])
    			return false; 
    	} 
    	return true;
    }
 
 
   
    public final String getDigestAlgorithm() { return this.digestAlgorithm; }
 
    public final void setDigestAlgorithm(String digestAlgorithm) { this.digestAlgorithm = digestAlgorithm; }

    public final String getSigningAlgorithm() { return this.signingAlgorithm; }

    public final void setSigningAlgorithm(String signingAlgorithm) { this.signingAlgorithm = signingAlgorithm; }
 }
