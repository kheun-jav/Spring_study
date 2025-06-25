package ex02_aes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class CipherRSA {
	static Cipher cipher; //암호 객체
	static PrivateKey prikey; //개인키
	static PublicKey pubkey; //공개키
	static {
		try {
			/*
			 *  RSA : 암호화 알고리즘
			 *  ECB : 블럭암호화
			 *  PKCS1Padding : Padding 방식
			 */
			
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			//KeyPairGenerator : 2개의 키를 생성
			KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
			KeyPair keyPair = key.genKeyPair(); //쌍인 키객체 생성
			prikey = keyPair.getPrivate();
			pubkey = keyPair.getPublic();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String encrypt(String plain1) {
		byte[] cipherMsg = new byte[1024];
		try {
			//cipher.init(Cipher.ENCRYPT_MODE,prikey) //암호화모드. 개인키로 암호화
			cipher.init(Cipher.ENCRYPT_MODE,pubkey); //암호화모드. 공개키로 암호화
			cipherMsg = cipher.doFinal(plain1.getBytes()); //암호화
		} catch(Exception e) {
			e.printStackTrace();
		}
		return byteToHex(cipherMsg); //byte[] => 16진수코드값의 문자열
	}
	private static String byteToHex(byte[] cipherMsg) {
		if(cipherMsg == null) return null;
		String str = "";
		for(byte b : cipherMsg) {
			str += String.format("%02X", b);
		}
		return str;
	}

	public static String decrypt(String cipher1) {
		byte[] plainMsg = new byte[1024];
		try {
//			cipher.init(Cipher.DECRYPT_MODE, pubkey); //복호화모드. 공개키로 복호화
			cipher.init(Cipher.DECRYPT_MODE, prikey); //복호화모드. 개인키로 복호화
			//hexToByte : 16진수코드값의 문자열 => byte[]
			plainMsg = cipher.doFinal(hexToByte(cipher1.trim()));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new String(plainMsg).trim();
	}
	private static byte[] hexToByte(String str) {
		if(str == null || str.length() <2) return null;
		byte[] buf = new byte[str.length()/2];
		for(int i=0; i<buf.length; i++) {
			buf[i] = (byte)Integer.parseInt(str.substring(i*2,i*2+2), 16);
		}
		return buf;
	}
	
	//================공개키/개인키 암호화/복호화 모듈=====================
	public static void getKey() { //키 생성
		try {
			KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
			//1024에서 2048비트로 키가 커지면 2^1024 만큼 보안 강화됨.
			key.initialize(2048); //키크기를 2048비트로 생성.
			//키의 크기가 큰경우 : 보안에 좋다.
			//				  암호/복호화에 속도가 느려짐.
			KeyPair keyPair = key.generateKeyPair();
			PrivateKey priKey = keyPair.getPrivate();
			PublicKey pubKey = keyPair.getPublic();
			ObjectOutputStream out = new ObjectOutputStream
									(new FileOutputStream("privatekey.ser"));
			out.writeObject(priKey);
			out.flush(); out.close();
			out = new ObjectOutputStream(new FileOutputStream("publickey.ser"));
			out.writeObject(pubKey);
			out.flush(); out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static PublicKey getPublicKey() {
		ObjectInputStream ois = null;
		PublicKey pubkey = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("publickey.ser"));
			pubkey = (PublicKey)ois.readObject();
			ois.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return pubkey;
	}
	public static PrivateKey getPrivateKey() {
		ObjectInputStream ois = null;
		PrivateKey prikey = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("privatekey.ser"));
			prikey = (PrivateKey)ois.readObject();
			ois.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return prikey;
	}
	public static String encrypt(String org, int menu1) {
		byte[] cipherMsg = new byte[1024];
		try {
			//menu1 = 1 => 기밀문서. 공개키로 암호화
			if(menu1==1) cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
			//menu1 = 2 => 본인문서작성. 개인키로 암호화
			else 		cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey());
			cipherMsg = cipher.doFinal(org.getBytes());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return byteToHex(cipherMsg);
	}
	
	public static String decrypt(String cipherMsg, int menu1) {
		byte[] plainMsg = new byte[1024];
		try {
			if(menu1==1) cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
			else 		 cipher.init(Cipher.DECRYPT_MODE, getPublicKey());
			plainMsg = cipher.doFinal(hexToByte(cipherMsg.trim()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new String(plainMsg).trim(); //복호화된 문자열
	}
}
