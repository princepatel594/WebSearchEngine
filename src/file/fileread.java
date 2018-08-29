package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class fileread {

	public int main(String fname) {
		int count = 0;
		try {
			String[] s2 = new String[5000000];
			File file = new File("E://sid.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String s1;
			while ((s1 = bufferedReader.readLine()) != null) {
				s2 = s1.split(" ");
				for (int i = 0; i < s2.length; i++) {
					if (s2[i].equals(fname)) {
						count++;
					}
				}
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}