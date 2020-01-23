import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

public class TernaryPart1 {

	public static void main(String[] args) throws IOException {
		File file = new File(args[0].substring(0, args[0].length()-3) +"bin");
		BufferedReader br = null;
		String line = null;
		int length = 0;
		String last = "00/00/0000";
		ArrayList<String[]> list = new ArrayList<String[]>();
        try {
			br = new BufferedReader(
			     new InputStreamReader(
			     new FileInputStream(args[0])));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        br.readLine();
        while((line = br.readLine()) != null) {
        	String[] parts = line.split(",");
        	if(parts[4].length() > length) {
        		length = parts[4].length();
        	}
        	boolean truth = compareDates(parts[4],last);
        	if(truth == true) {
        		list.add(parts);
        		last = parts[4];
        	}
        }
        padDates(list,length);
        writeOut(list, file);
	}

	private static void writeOut(ArrayList<String[]> list, File file) {
		RandomAccessFile dataStream = null;
		try {
			dataStream = new RandomAccessFile(file,"rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < list.size(); i++) {
			String line = Arrays.toString(list.get(i));
			byte[] bytes = line.getBytes();
			System.out.println(Arrays.toString(bytes));
			try {
				dataStream.write(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			dataStream.seek(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Iterates through the list of string[]. gets the 5th field and compares its length to 
	 * the max length. If it's less than max length, then it'll add the difference between the max
	 * length and its length of spaces to the right. 
	 * @param list
	 * @param length
	 */
	private static void padDates(ArrayList<String[]> list, int length) {
		for(int i = 0; i < list.size(); i++) {
			String date = list.get(i)[4];
			if(date.length() < length) {
				int diff = length - date.length();
				for(int j = 0; j < diff; j++) {
					list.get(i)[4] = list.get(i)[4] + " ";
				}
			}
		}
		
	}

	/**
	 * Takes two strings dates. Splits and converts them into ints. Check if the new date is valid.
	 * Compares them to each other. Return true if date2 is newer than last date. Also returns 
	 * true if both dates are the same. 
	 * @param date
	 * @param date2
	 * @return boolean
	 */
	private static boolean compareDates(String date, String date2) {
		String[] newDate = date.split("/");
		String[] lastDate = date2.split("/");
		if(newDate[2].length() > 4 || newDate[1].length() > 2 || newDate[0].length() > 2 || date.length() == 0) {
			return false; 
		}
		int newYear = Integer.parseInt(newDate[2]);
		int oldYear = Integer.parseInt(lastDate[2]);
		int newDay = Integer.parseInt(newDate[1]);
		int oldDay = Integer.parseInt(lastDate[1]);
		int newMonth = Integer.parseInt(newDate[0]);
		int oldMonth = Integer.parseInt(lastDate[0]);
		if(oldYear < newYear) {
			return true;
		}
		else if(oldMonth < newMonth) {
			return true;
		}
		else if(oldDay < newDay) {
			return true;
		}
		else if(oldDay == newDay && oldMonth == newMonth && newYear == oldYear){
			return true;
		}
		else {
			return false;
		}
		
		
	}

}
