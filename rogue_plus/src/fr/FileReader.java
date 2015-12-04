package fr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileReader {
	public static String[] readFile(String fname) {
		char[] chars = readFileChar(fname);
		if (chars != null) {
			return new String(chars).split("\n");
		} else {
			return null;
		}
	}
	
	public static char[] readFileChar(String fname) {
		File file = new File(fname);
    	try (java.io.FileReader reader = new java.io.FileReader(file)) {
            char[] chars = new char[(int)file.length()];
            reader.read(chars);
            reader.close();
            return chars;
        } catch (IOException e) {
            e.printStackTrace();
        }
    	return null;
	}
	
	public static FileWriter openWriteFile(String fname) {
		File file = new File(fname);
		try {
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
			return writer;
		} catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
}
