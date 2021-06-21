package com.hingebridge.devops.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.engines.*;
import org.bouncycastle.crypto.modes.*;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.util.encoders.Base64;

public class EncryptUtil {
	private BufferedBlockCipher cipher;
	private KeyParameter key;

	public EncryptUtil() {}

	@SuppressWarnings("deprecation")
	public EncryptUtil(byte[] key) {
		cipher = new PaddedBlockCipher(new CBCBlockCipher(new DESEngine()));
		this.key = new KeyParameter(key);
	}

	public EncryptUtil(String key) {
		this(key.getBytes());
	}

	private byte[] callCipher(byte[] data) throws CryptoException, DataLengthException {
		int size = cipher.getOutputSize(data.length);
		byte[] result = new byte[size];
		int olen = cipher.processBytes(data, 0, data.length, result, 0);
		olen += cipher.doFinal(result, olen);

		if (olen < size) {
			byte[] tmp = new byte[olen];
			System.arraycopy(result, 0, tmp, 0, olen);
			result = tmp;
		}

		return result;
	}

	public synchronized byte[] encrypt(byte[] data) throws CryptoException {
		if (data == null || data.length == 0) {
			return new byte[0];
		}
		cipher.init(true, key);

		return callCipher(data);
	}

	public byte[] encryptString(String data) throws CryptoException {
		if (data == null || data.length() == 0) {
			return new byte[0];
		}

		return encrypt(data.getBytes());
	}

	/* Decrypts arbitrary data. */
	public synchronized byte[] decrypt(byte[] data) throws CryptoException {
		if (data == null || data.length == 0) {
			return new byte[0];
		}

		cipher.init(false, key);
		return callCipher(data);
	}

	/* Decrypts a string that was previously encoded using encryptString. */
	public String decryptString(byte[] data) throws CryptoException {
		if (data == null || data.length == 0) {
			return "";
		}

		return new String(decrypt(data));
	}

	public String encryptStringEncoded(String data) {
		String dataString = "";

		try {
			byte[] dataEncrypt = encryptString(data);
			dataString = new String(Base64.encode(dataEncrypt));
		} catch (CryptoException e) {
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
		}

		return dataString;
	}

	public String decryptStringEncoded(String data)
    {
        String decodedString = "";

        try
        {
            byte[] decodedData = Base64.decode(data);

            if (data == null || decodedData.length == 0)
            {
                return "";
            }

            decodedString = new String(decrypt(decodedData));

        }
        catch (CryptoException e)
        {
            StringWriter stack = new StringWriter();
            e.printStackTrace(new PrintWriter(stack));
        }

        return decodedString;
    }
}