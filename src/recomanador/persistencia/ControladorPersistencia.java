package src.recomanador.persistencia;

import java.io.File;
import java.util.ArrayList;

public class ControladorPersistencia {
    private File carpeta;
    private File f;
    
    ControladorLoad cl;
    
    public ControladorPersistencia()
    {
		carpeta = null;
		f = null;
		cl = new ControladorLoad();
	}
    
    //estructura de prova per a obrir la carpeta
    public boolean escollirProjecte(String s)
    {
		try
		{
			carpeta = new File(s);
		}
		catch(NullPointerException n)
		{
			return false;
		}
		//generar els fitxers f a partir de la carpeta
		f = new File(carpeta, "items");
		return true;
	}
	
	/**
     * @return Returns an array of arrays of the values. Each array of arrays(line)
     * contains an array of strings (columns). 
     * The first line corresponds to the header of the file, where each
     * column its identifier. 
     * The rest of the lines contain the values read. <p>
     * If an error has occurred, the null pointer will be returned instead.
     */
	public ArrayList<ArrayList<String>> carregarRecomanacionsCarpeta()
	{
		return cl.carregarArxiu(new File(carpeta, "items"));
	}
	
	
}
