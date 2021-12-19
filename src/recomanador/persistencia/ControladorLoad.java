package src.recomanador.persistencia;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

/**
* The class ControladorLoad implements the function for loading data from memory. 
* This data is read as .csv file.
* 
* @author Pol Sturlese 
*/
public class ControladorLoad {

	/*----- STATICS -----*/

	/** Contains the only instance of the class **/
	private static ControladorLoad inst;

	/**
     * Returs the only instance of the class, and if it's not created, it creates it.
     *
     * @return     The instance.
     */
	public static ControladorLoad getInstance() {
        if(ControladorLoad.inst == null) inst = new ControladorLoad();
        return inst;
    }

    /*----- CONSTRUCTORS -----*/

    /**
     * Creates an instance of ControladorLoad
     */     
    private ControladorLoad() {}
    

    /*----- FUNCIONS -----*/

    /**
     * Returns the content of the file specified read as a table separated
     * with commas and enters.
     * 
     * @param      file      This is the file that is going to be used for the loading
     * @return      Returns an array of arrays of the values. Each array of arrays(line)
     * contains an array of strings (columns). 
     * The first line corresponds to the header of the file, where each
     * column its identifier. 
     * The rest of the lines contain the values read. <br>
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
		if (n == -1) {
			f.close();
			throw new IOException();
		}
		
		//-----Eliminació linies en blanc-----
		while (n != -1 && c == '\n') {
			n = f.read();
			c = (char)n;
		}
		//------------------------------------
		
		if (n == -1) {
			f.close();
			throw new IOException();
		}
		
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
			
			if (c == '\n' | n == -1) finished_header = true;
			
			if (n != -1)
			{
				n = f.read();
				c = (char)n;
			}
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
				char ANTI_END_CHAR = '\n';
				for (int i = 0; i < cols; ++i)
				{
					if (i == cols-1) 
					{
						END_CHAR = '\n';
						ANTI_END_CHAR = ',';
					}
					String column_value = "";
			
					while (n != -1 && c != END_CHAR) 
					{
						if (c == ANTI_END_CHAR) {
							f.close();
							throw new IOException();
						}
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
					
					if (n == -1 && data.get(0).size() != data.get(data.size() - 1).size()) {
						f.close();
						throw new IOException();
					}
					
					if (n != -1)
					{
						n = f.read();
						c = (char)n;
					}
				}
			}
		}
		
		
		f.close();
		
		return data;
	}
}
