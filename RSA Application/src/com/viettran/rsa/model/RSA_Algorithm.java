/**
 * This program is used to demonstrate behind the scene of RSA algorithm in generating key pair and signing signature .
 * Author: Viet Tran
 * Date created: April 15, 2023
 */

package com.viettran.rsa.model;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA_Algorithm {
	public static KeyPair generateKeyPair(String text) throws NoSuchAlgorithmException {
		// Use text input as the base seed for keygen process
		byte[] seed = text.getBytes();
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(seed);
		// Generate a key pair using RSA algorithm
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048, random);
		return keyGen.generateKeyPair();
	}

	public static String generatePublicKey(String text) {
		KeyPair keyPair = null;
		try {
			keyPair = generateKeyPair(text);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PublicKey publicKey = keyPair.getPublic();
		return Base64.getEncoder().encodeToString(publicKey.getEncoded());
	}

	public static String generatePrivateKey(String text) {
		KeyPair keyPair = null;
		try {
			keyPair = generateKeyPair(text);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrivateKey privateKey = keyPair.getPrivate();
		return Base64.getEncoder().encodeToString(privateKey.getEncoded());
	}

	public static String sign(String text, String privateKeyStr) throws Exception {
		// Decode the private key from a Base64 string into a byte array
		byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
		// Get an instance of the RSA key factory
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		// Generate a PrivateKey object from the byte array using PKCS8EncodedKeySpec
		PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
		// Get an instance of the Signature class using SHA256withRSA algorithm
		Signature signature = Signature.getInstance("SHA256withRSA");
		// Initialize the Signature object with the private key
		signature.initSign(privateKey);
		// Update the Signature object with the text to be signed
		signature.update(text.getBytes());
		// Generate the signature and store it in a byte array
		byte[] signatureBytes = signature.sign();
		// Encode the signature byte array into a Base64 string and return it
		return Base64.getEncoder().encodeToString(signatureBytes);
	}

	public static boolean verifyMessage(String message, String signature, String publicKey) throws Exception {
		// Create object signature with SHA256withRSA algorithm
		Signature sig = Signature.getInstance("SHA256withRSA");
		// Decode public key from Base64 string
		byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
		// Creata PublicKey object from decoded public key
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
		// Initialize signature object with public key
		sig.initVerify(pubKey);
		// Update message that need verify
		sig.update(message.getBytes());
		// Decode digital signature from Base64 string
		byte[] signatureBytes = Base64.getDecoder().decode(signature);
		// Verify digital signature and return the result
		return sig.verify(signatureBytes);
	}

}
