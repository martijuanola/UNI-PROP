package src.recomanador.persistencia;

//import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

import src.recomanador.excepcions.*;

/**
* La classe ControladorLoad implementa controlador de lectura
* de les dades en memòria.
* 
* @author Pol Sturlese 
*/

public class ControladorLoad {
    /**
     * Creates an instance of ControladorLoad
     * 
     * @return Returns a new instance of ControladorLoad
     */     
    public ControladorLoad()
    {
	}
    
    /**
     * Returns the content of the file specified read as a table separated
     * with commas and enters.
     * 
     * @param      file      This is the file that is going to be used for the loading
     * @return      Returns an array of arrays of the values. Each array of arrays(line)
     * contains an array of strings (columns). 
     * The first line corresponds to the header of the file, where each
     * column its identifier. 
     * The rest of the lines contain the values read. <p>
     * @exception	IOException If there's an error reading, the IOException will be thrown
     */
    public ArrayList<ArrayList<String>> carregarArxiu(File file) throws IOException
    {
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		
		FileReader f = null;
		f = new FileReader(file);
				
		char c;
		int n = -1;
		
		n = f.read();
		//read will return a -1 if it has encountered the end.
		//If this occurs at this point, it means that the file is empty
		if (n == -1) throw new IOException();
		c = (char)n;
		
		///* Read original		
		while (n != -1)
		{
			data.add(new ArrayList<String>());
			while (c != '\n')
			{
				String column_name = "";
				
				while (c != ',' && c != '\n') 
				{				
					if (c == '"')
					{
						do
						{
							column_name += c;
							n = f.read();
							c = (char)n;
						} while (c != '"');
					}
					
					column_name += c;
					n = f.read();
					c = (char)n;
				}
				
				//always add to the last line
				if (c == '\n' && column_name == "" && data.get(data.size()-1).size() == 0) data.remove(data.size()-1);
				else data.get(data.size()-1).add(column_name);
				
				if (c != '\n')
				{
					n = f.read();
					c = (char)n;
				}
			}
			
			if (n != -1)
			{
				n = f.read();
				c = (char)n;
			}
		}
		
		//*/
		
		f.close();
		
		int cols = data.get(0).size();
		
		for (int i = 1; i < data.size(); ++i)
		{
			int temp_cols = data.get(i).size();
			if (cols > 1 & temp_cols == cols - 1) data.get(i).add("");
			//else if (temp_cols == 0) data.remove(i);
			else if (temp_cols != cols) throw new IOException();
		}
		
		return data;
	}
}
