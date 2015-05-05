import java.io.*;

public class FileLoader 
{
	// read a file given a filename and a size of the expected input in lines
	public static String[] readFile(String fileName, int size)
	{
		String[] inArray = new String[size];
		String line;
		int i = 0;
		
		try
		{
			File f = new File(fileName);
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			while ((line = br.readLine()) != null)
			{
				inArray[i] = line;
				i++;
			}		
			br.close();
		}
		catch (FileNotFoundException e) 
		{
			return new String[] {null};
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return inArray;
	}
	
	// write a file given a filename and an array of lines to be written
	public static void writeFile(String fileName, String[] output)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			
			for (String string : output)
			{
				writer.println(string);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
