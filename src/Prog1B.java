import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Prog1B {

	public static void main(String[] args) {
		RandomAccessFile dataStream = null;
		File file = new File(args[0]);
		try {
			dataStream = new RandomAccessFile(file, "r");
			String text = "";
			int a = dataStream.readInt();
			if(a == 0) {
				System.out.println(dataStream.readInt());
			} else {
				int length = dataStream.readInt();
				byte[] line = new byte[length];
				dataStream.readFully(line);
				String name = new String(line);
				System.out.println(name);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
