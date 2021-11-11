package src.recomanador.persistencia;

//import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

/**
* La classe ControladorLoad implementa controlador de lectura
* de les dades en memòria.
* 
* @author Pol Sturlese 
*/

public class ControladorLoad {
    
    /**
     * @return Returns an instance of ControladorLoad
     */    
    public ControladorLoad()
    {
	}
    
    /**
     * @param file This is the file that is going to be used for the loading
     * @return Returns an array of arrays of the values. Each array of arrays(line)
     * contains an array of strings (columns). 
     * The first line corresponds to the header of the file, where each
     * column its identifier. 
     * The rest of the lines contain the values read. <p>
     * If an error has occurred, the null pointer will be returned instead.
     */
    public ArrayList<ArrayList<String>> carregarArxiu(File file)
    {
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		
		FileReader f = null;
		try
		{
			f = new FileReader(file);
		} catch (IOException ex) {
			return null;
		}
		
		char c[] = new char[1];
		int n = f.read(c);
		
		//read will return a -1 if it has encountered the end.
		//If this occurs at this point, it means that the file is empty
		if (n == -1) return null;
		
		while (n != -1)
		{
			data.add(new ArrayList<String>());
			while (c[0] != '\n')
			{
				String column_name = "";
				//No hauria de donar problemes, però si se sobreescriu
				//la string, fer un new String()
				
				while (c[0] != ',' && c[0] != '\n') 
				{
					column_name += c[0];
					n = f.read(c);
				}
				
				//always add to the last line
				data.get(data.size()-1).add(column_name);
			}
		}
		
		return data;
	}
}
