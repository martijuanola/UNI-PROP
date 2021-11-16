package src.recomanador.persistencia;

//import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

/**
* La classe ControladorSave implementa controlador de guardat
* de les dades en memòria.
* 
* @author Pol Sturlese 
*/

public class ControladorSave {
    
    /**
     * Creates an instance of ControladorSave
     * 
     * @return Returns a new instance of ControladorSave
     */    
    public ControladorSave() {}
    
    /**
     * Stores the content of data at the file specified. It's stored as a csv table,
     * where the first row is the header of the table. All rows have the same number
     * of columns.
     * 
     * @param      file      This is the file that is going to be used for the saving
     * @param      data      This is the data that is going to be stored
     * 
     * @exception	IOException If there's an error reading, the IOException will be thrown
     */
    public void guardarArxiu(File file, ArrayList<ArrayList<String>> data) throws IOException
    {
		FileWriter f = new FileWriter(file);
		
		for (int i = 0; i < data.size(); ++i)
		{
			for (int j = 0; j < data.get(i).size(); ++j)
			{
				String s = data.get(i).get(j);
				f.write(s, 0, s.length());
				if (j != data.get(i).size()-1) f.write(",", 0, 1);
			}
			
			f.write("\n", 0, 1);
		}
		
		f.close();
	}
}
