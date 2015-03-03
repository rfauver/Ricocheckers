import java.io.*;

public class FileLoader 
{
	 	
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
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return inArray;
	}
}
