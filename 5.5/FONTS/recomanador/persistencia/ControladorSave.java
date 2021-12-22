package src.recomanador.persistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

/**
* The class ControladorSave implements the function for storing data in memory.
* This data is stored with the format of a .csv file.
* 
* @author Pol Sturlese 
*/

public class ControladorSave {
    
    /*----- STATICS -----*/

    /** Contains the only instance of the class **/
    private static ControladorSave inst;

    /**
     * Returs the only instance of the class, and if it's not created, it creates it.
     *
     * @return     The instance.
     */
    public static ControladorSave getInstance() {
        if(ControladorSave.inst == null) inst = new ControladorSave();
        return inst;
    }


    /*----- CONSTRUCTORS -----*/

    /**
     * Creates an instance of ControladorSave
     */    
    private ControladorSave() {}


    /*----- FUNCIONS -----*/
    
    /**
     * Stores the content of data at the file specified. It's stored as a csv table,
     * where the first row is the header of the table. All rows have the same number
     * of columns.
     * 
     * @param      file      This is the file that is going to be used for the saving. It needs to exist.
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
