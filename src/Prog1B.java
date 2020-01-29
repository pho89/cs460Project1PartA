import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;

public class Prog1B {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File(args[0]);
		byte[] part = null;
		int a = 0;
		RandomAccessFile dataStream  = new RandomAccessFile(file, "r");
		try {
			while(true) {
				a = dataStream.readInt();
				if(a == 0) {
					int num = dataStream.readInt();
				} else {
					//System.out.println("a: " + String.format("%08X", a));
					int length = dataStream.readInt();
					//System.out.println("length: " + String.format("%08X", length));
					part = new byte[length];
					dataStream.readFully(part);
					String name = new String(part);
					System.out.println(name);
					//System.out.println("name: " +String.format("%x", new BigInteger(1, name.getBytes(/*YOUR_CHARSET?*/))));
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
