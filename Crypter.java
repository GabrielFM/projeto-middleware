import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.*;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypter {

	private Cipher encripta;
	private Cipher decripta;
	public void init(ClientRequestHandler crh) throws Exception
	{
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DH");
		keyPairGen.initialize(2048);
		KeyPair kPair = keyPairGen.generateKeyPair();
		
		KeyAgreement keyAgree = KeyAgreement.getInstance("DH");
	    keyAgree.init(kPair.getPrivate());
		
		byte [] encodedPublicKey = kPair.getPublic().getEncoded();
		
		crh.send(encodedPublicKey);
		byte [] receivedEncodedPublicKey = crh.receive();
		
		KeyFactory keyFact = KeyFactory.getInstance("DH");
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(receivedEncodedPublicKey);
		PublicKey receivedPublicKey = keyFact.generatePublic(x509KeySpec);
		keyAgree.doPhase(receivedPublicKey, true);
		
		byte[] chave = keyAgree.generateSecret();

		SecretKeySpec key = new SecretKeySpec(chave,0, 16, "AES");
		
		encripta = Cipher.getInstance("AES/CBC/PKCS5Padding");
		encripta.init(Cipher.ENCRYPT_MODE, key);
		
		byte[] sentEncodedParams = encripta.getParameters().getEncoded();
		crh.send(sentEncodedParams);
		byte[] receivedEncodedParams = crh.receive();
		
		AlgorithmParameters aesParams = AlgorithmParameters.getInstance("AES");
	    aesParams.init(receivedEncodedParams);
		decripta = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decripta.init(Cipher.DECRYPT_MODE, key, aesParams);
	}
	
	void init (ServerRequestHandler srh) throws Exception
	{
		byte[] receivedEncodedPublicKey = srh.receive();
		KeyFactory keyFact = KeyFactory.getInstance("DH");
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(receivedEncodedPublicKey);
		PublicKey receivedPublicKey = keyFact.generatePublic(x509KeySpec);
		DHParameterSpec receivedDHParameters = ((DHPublicKey)receivedPublicKey).getParams();
		
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DH");
		keyPairGen.initialize(receivedDHParameters);
		KeyPair kPair = keyPairGen.generateKeyPair();
		
		KeyAgreement keyAgree = KeyAgreement.getInstance("DH");
	    keyAgree.init(kPair.getPrivate());
	    
	    byte [] encodedPublicKey = kPair.getPublic().getEncoded();
		srh.send(encodedPublicKey);
		
		keyAgree.doPhase(receivedPublicKey, true);
		
		byte[] chave = keyAgree.generateSecret();

		SecretKeySpec key = new SecretKeySpec(chave, 0, 16, "AES");
		
		encripta = Cipher.getInstance("AES/CBC/PKCS5Padding");
		encripta.init(Cipher.ENCRYPT_MODE, key);
		
		byte[] sentEncodedParams = encripta.getParameters().getEncoded();
		srh.send(sentEncodedParams);
		byte[] receivedEncodedParams = srh.receive();
		
		AlgorithmParameters aesParams = AlgorithmParameters.getInstance("AES");
	    aesParams.init(receivedEncodedParams);
		decripta = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decripta.init(Cipher.DECRYPT_MODE, key, aesParams);
	}
	
	public byte[] encrypt(byte[] bs) throws Exception
	{
		return encripta.doFinal(bs);
	}
	
	public byte[] decrypt(byte[] textoencriptado) throws Exception
	{
        return decripta.doFinal(textoencriptado);
	}
}
