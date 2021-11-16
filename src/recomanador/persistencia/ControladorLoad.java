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
    public ControladorLoad() {}
    
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
		c = (char)n;
		if (n == -1) throw new IOException();
		
		//-----Eliminació linies en blanc-----
		while (n != -1 && c == '\n') {
			n = f.read();
			c = (char)n;
		}
		//------------------------------------
		
		if (n == -1) throw new IOException();
		
		//Establir header de la taula: llegir totes les columnes
		int cols = 0;
		data.add(new ArrayList<String>());
		boolean finished_header = false;
		while (!finished_header)
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
			
			++cols;
			data.get(0).add(column_name);
			
			if (c == '\n') finished_header = true;

			n = f.read();
			c = (char)n;
		}
		
		while (n != -1)
		{
			//-----Eliminació linies en blanc-----
			while (n != -1 && c == '\n') {
				n = f.read();
				c = (char)n;
			}
			//------------------------------------
			
			if (n != -1)
			{
				data.add(new ArrayList<String>());
				
				char END_CHAR = ',';
				for (int i = 0; i < cols; ++i)
				{
					if (i == cols-1) END_CHAR = '\n';
					String column_value = "";
			
					while (c != END_CHAR) 
					{				
						if (c == '"')
						{
							do
							{
								column_value += c;
								n = f.read();
								c = (char)n;
							} while (c != '"');
						}
						
						column_value += c;
						n = f.read();
						c = (char)n;
					}
					
					data.get(data.size() - 1).add(column_value);
					
					n = f.read();
					c = (char)n;
				}
			}
		}
		
		
		f.close();
		
		return data;
	}
}
