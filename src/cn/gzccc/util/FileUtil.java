package cn.gzccc.util;

import java.io.*;

public class FileUtil {
	
	//É¾³ýÕû¸öÄ¿Â¼
	public static void removeDir(File dir){
		if(dir.isFile()){
			dir.delete();
		}else if(dir.isDirectory()){
			File subDirs[] = dir.listFiles();
			for(int i=0; i<subDirs.length; i++){
				removeDir(subDirs[i]);
			}
			dir.delete();
		}
	}
	
	public static void copy(File src, File dst) throws IOException {
		FileOutputStream fos = null;
		FileInputStream  fis = null;
		byte[] buffer = new byte[1024];
		int length;
		try{
			fis = new FileInputStream(src);
			fos = new FileOutputStream(dst);
			while((length=fis.read(buffer)) > 0){
				fos.write(buffer, 0, length);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}finally{
			if(fis!=null) fis.close();
			if(fis!=null) fos.close();
		}
	}
	
	public static String readFromFile(String filePath, String encoding) throws IOException{
		if(!"GBK".equalsIgnoreCase(encoding)
				&& !"UTF-8".equalsIgnoreCase(encoding)){
			encoding = "ISO-8859-1";
		}
		StringBuffer content = new StringBuffer();
		FileInputStream fis = null;
		InputStreamReader reader = null;
		try{
			File file = new File(filePath);
			if(!file.exists() || !file.isFile()){
				throw new IOException("file not found or not a file!");
			}
			fis = new FileInputStream(file);
			reader = new InputStreamReader(fis, encoding);
			for(int c=reader.read(); c!=-1; c=reader.read()){
				content.append((char)c);
			}
		}catch(IOException e){
			throw e;
		}finally{
			if(reader!=null) reader.close();
			if(fis!=null) fis.close();
		}
		return content.toString();
	}
	
	public static void writeToFile(String content, String filePath, String encoding) throws IOException{
		if(!"GBK".equalsIgnoreCase(encoding)
				&& !"UTF-8".equalsIgnoreCase(encoding)){
			encoding = "ISO-8859-1";
		}
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(filePath,false);
			fos.write(content.getBytes(encoding));
		}catch(IOException e){
			throw e;
		}finally{
			if(fos!=null) fos.close();
		}
	}
}
