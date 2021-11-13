package src.recomanador.persistencia;

import java.io.File;
import java.util.ArrayList;

public class ControladorPersistencia {
    private File carpeta;
    private File f;
    
    ControladorLoad cl;
    
    /**
	 * Creates a new instance of the class ControladorPersistencia.
	 * 
	 * @return			 Returns a new instance of ControladorPersistencia.
	 */
    public ControladorPersistencia()
    {
		carpeta = null;
		f = null;
		cl = new ControladorLoad();
	}
    
	/**
	 * Estableix el nom de la carpeta que s'usarà epr carregar les dàdes en el projecte
	 * 
	 * @param      s     Representa el nom de la carpeta, que s'haurà d'escollir entre la llista de les carpetes existents
	 * @return			 Returns true if the folder opened correctly. Otherwise it returns false.
	 */
    public boolean establirCarpeta(String s)
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
	 * Estableix el nom de la carpeta que s'usarà epr carregar les dàdes en el projecte
	 * 
	 * @return			 Returns all the folders that are inside the saved projects folder.
	 */
	public ArrayList<String> llistatCarpetes()
	{
		return null;
	}
	
	
	/**
	 * Returns the recomendations read from memory
	 * 
     * @return Returns an array of arrays of the values. Each array of arrays(line)
     * contains an array of strings (columns). 
     * The first line corresponds to the header of the file, where each
     * column its identifier. 
     * The rest of the lines contain the values read. <p>
     * If an error has occurred, the null pointer will be returned instead.
     */
	public ArrayList<ArrayList<String>> carregarRecomanacionsCarpeta()
	{
		return cl.carregarArxiu(new File(carpeta, "valoracions"));
	}
	
	/**
	 * Returns the recomendations read from memory
	 * 
     * @return Returns an array of arrays of the values. Each array of arrays(line)
     * contains an array of strings (columns). 
     * The first line corresponds to the header of the file, where each
     * column its identifier. 
     * The rest of the lines contain the values read. <p>
     * If an error has occurred, the null pointer will be returned instead.
     */
	public ArrayList<ArrayList<String>> carregarItemsCarpeta()
	{
		return cl.carregarArxiu(new File(carpeta, "items"));
	}
	
	
}
