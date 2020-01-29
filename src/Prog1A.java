import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

public class Prog1A {

	public static void main (String [] args)
    {
		String 			 line = null;
        File             fileRef;          
        BufferedReader   br = null;
        ArrayList<String[]> list = new ArrayList<String[]>();
        fileRef = new File(args[0].substring(0, args[0].length()-3) +"bin");
        try {
        	br = new BufferedReader(
   			     new InputStreamReader(
   			     new FileInputStream(args[0])));
   			br.readLine();
        	while( (line = br.readLine()) != null) {
        		String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        		list.add(parts);
        	}
        	br.close();
        	findMaxLength(list);
        	writeOut(list,fileRef);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * For loops through 
	 * @param list
	 */
	private static void findMaxLength(ArrayList<String[]> list) {
		boolean check = true;
		for(int i = 0; i < 53; i++) {
			int max = 0;
			for(int j = 0; j < list.size(); j++) {
				check = checkIfNumber(list.get(j)[i]);
				if(max < list.get(j)[i].length()) {
					max = list.get(j)[i].length();
				}
			}
			padColumn(list,i,max,check);
			max = 0;
		}
		
	}

	private static void padColumn(ArrayList<String[]> list, int field, int max, boolean check) {
		int diff = 0;
		for(int i = 0; i < list.size(); i++) {
			String text = list.get(i)[field];
			if(text.length() == 0) {
				if(check == true) {
					list.get(i)[field] = "-1";
				} else {
					for(int j = 0; j < max; j++) {
						text += " ";
					}
					list.get(i)[field] = text;
				}
			} else {
				if(check == false) {
					diff = max - list.get(i)[field].length();
					for(int j = 0; j < diff; j++) {
						text += " ";
					}
					list.get(i)[field] = text;
				}
			}
		}	
	}

	/**
	 * Creates a RandomAccessFile that will create .bin file. Outter for loop, loops through 
	 * each record. Inner for loop, loops through each field within a record. Method will check 
	 * if the field is a string or int and will use .writeInt() for ints and .writeBytes() for Strings.
	 * Close file nested loop is done. 
	 * @param list
	 * @param fileRef
	 */
	private static void writeOut(ArrayList<String[]> list, File fileRef) {
		RandomAccessFile dataStream = null;
		String last = "00/00/0000"; //set last date to 00/00/0000
		String line = null;
		try {
			dataStream = new RandomAccessFile(fileRef,"rw");
			for(int i = 0; i<list.size(); i++) {
				for(int j = 0; j<list.get(i).length; j++) {
					String field = list.get(i)[j];
					boolean check = checkIfNumber(field);
					if(check == true) {
						//int field that will be written with .writeInt()
						dataStream.writeInt(0);
						dataStream.writeInt(Integer.parseInt(field));
						
					} else {
						
						//Strings fields
						
						/*
						 * this is the date field that needs to be compared to 
						 * previous date before being written if it's newer than the 
						 * previous date
						 */
						dataStream.writeInt(1);
						dataStream.writeInt(field.length());
						System.out.println("field: " + j);
						System.out.println(field);
						System.out.println(field.length());
						System.out.println("-------------------");
						if(j == 4) {
        					if(compareDates(field,last) == true){
	        					last = field;
	        					dataStream.writeBytes(field);
        					}
        					
        				} else {
        					
        					//normal string field that just needs to written out
        					dataStream.writeBytes(field);
        				}
					}
				}
			}
			dataStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	/**
	 * Trys to Int Parse a string. If it fails, will return false. Otherwise
	 * will return true
	 * @param line
	 * @return boolean
	 */
	private static boolean checkIfNumber(String line) {
		try {
			int i = Integer.parseInt(line);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
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
		
		//Checks for valid date inputs. Returns false if dates are not in correct format
		if(newDate[2].length() > 4 || newDate[1].length() > 2 || newDate[0].length() > 2 || date.length() == 0) {
			return false; 
		}
		//breaking up new date into day, month, and year
		int newYear = Integer.parseInt(newDate[2]); 
		int newDay = Integer.parseInt(newDate[1]);
		int newMonth = Integer.parseInt(newDate[0]);
		
		//breaking up previous date into day, month, and year
		int oldMonth = Integer.parseInt(lastDate[0]);
		int oldYear = Integer.parseInt(lastDate[2]);
		int oldDay = Integer.parseInt(lastDate[1]);
		
		//Compares years first
		if(oldYear < newYear) {
			return true;
		}
	
		//compares month
		else if(oldMonth < newMonth) {
			return true;
		}
		
		//compares days
		else if(oldDay < newDay) {
			return true;
		}
		
		//if old date and new date are the same
		else if(oldDay == newDay && oldMonth == newMonth && newYear == oldYear){
			return true;
		}
		else {
			
			//At this point the new Date is older than old Date
			return false;
		}
	}
}
