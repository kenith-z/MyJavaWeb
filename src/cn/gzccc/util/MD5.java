/*
 * MD5加密工具
 * Author: YXK
 * Date: 2014-12-29
 */

package cn.gzccc.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class MD5 {

	// 计算文件MD5值
	public static String encrypt(File file) throws Exception {
		MessageDigest md5 = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		StringBuffer hexString = new StringBuffer();
		try{
			md5 = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			for (int i = bis.read(); i != -1; i = bis.read()) {
				md5.update((byte)i);
			}
			byte md5Bytes[] = md5.digest();
			for(int i = 0; i < md5Bytes.length; i++){
				if(md5Bytes[i] >= 0 && md5Bytes[i] <= 15){
					hexString.append("0" + Integer.toHexString(0xFF & md5Bytes[i]));
				}else{
					hexString.append(Integer.toHexString(0xFF & md5Bytes[i]));
				}
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(bis!=null){try{bis.close();}catch(IOException e){;}}
			if(fis!=null){try{fis.close();}catch(IOException e){;}}
		}
		return hexString.toString();
	}
	
	// 将密码转化为32位不可逆的字符串
	public static String encrypt(String kl) {
		String mw, cmm;
		int k, i, a, hf, lf, bytes = 16;
		int mm[], l, rand_seed;
		String dm = "abc";

		rand_seed = 12345;

		int len = kl.length();
		if (len >= 16) len = 16;

		mw = kl.substring(0, len) + dm.trim();
		l = mw.length();

		if (bytes > 100) bytes = 100;
		if (l < bytes) {
			for (; l <= bytes; l++) {
				rand_seed = (31527 * rand_seed + 3) % 32768;
				a = rand_seed % 256;
				if (a < 32 || a > 127)
					a = (int) ('a');
				mw = mw + (char) (a);
			}
		}

		mm = new int[100];

		for (l = 0; l < 100; l++) mm[l] = 0;

		for (l = 0; l < bytes; l++) {
			a = (int) mw.charAt(l);
			for (i = 1; i <= 8; i++) {
				if (a >= 128) {
					a -= 128;
					for (k = 0; k < bytes; k++) {
						rand_seed = (31527 * rand_seed + 3) % 32768;
						mm[k] += rand_seed % 256;
					}
				} else {
					for (k = 1; k <= bytes; k++) rand_seed = (31527 * rand_seed + 3) % 32768;
				}
				a *= 2;
			}
		}

		for (k = bytes - 1; k >= 0; k--) {
			if (k >= 1) mm[k - 1] += mm[k] / 256;
			mm[k] = mm[k] % 256;
		}

		cmm = "";
		for (k = 0; k < bytes; k++) {
			hf = mm[k] / 16;
			if (hf < 10) {
				cmm = cmm + (char) (hf + (short) ('0'));
			} else {
				cmm = cmm + (char) (hf + (short) ('A') - 10);
			}
			lf = mm[k] % 16;
			if (lf < 10) {
				cmm = cmm + (char) (lf + (short) ('0'));
			} else {
				cmm = cmm + (char) (lf + (short) ('A') - 10);
			}
		}

		return cmm;
	}

}
