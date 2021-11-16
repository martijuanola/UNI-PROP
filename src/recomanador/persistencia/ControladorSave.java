package src.recomanador.persistencia;

//import java.io.*;
import java.io.File;
import java.io.FileReader;
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
    public void guardarArxiu(File file, ArrayList<ArrayList<String>> data) throws IOException
    {
	}
}
