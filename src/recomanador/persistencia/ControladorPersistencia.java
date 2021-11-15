package src.recomanador.persistencia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import src.recomanador.excepcions.*;

/**
* La classe ControladorPersistencia implementa controlador de lectura
* i d'escriptura de les dades en memòria. Gestiona també les carpetes
* on es guardarà part de la informació.
* 
* @author Pol Sturlese 
*/
public class ControladorPersistencia {
    private File carpeta;
    private static File dades; 		//Carpeta de dades
    ControladorLoad cl;
    
    /**
	 * Creates a new instance of the class ControladorPersistencia. It also finds the
	 * data folder, and if it doen't exist, it creates it.
	 * 
	 * @return			 Returns a new instance of ControladorPersistencia.
	 */
    public ControladorPersistencia()
    {
		carpeta = null;
		cl = new ControladorLoad();
		
		//Comprovar si existeix dades
		//Sinó, crear-ne la carpeta
	}
    
	/**
	 * Estableix el nom de la carpeta que s'usarà epr carregar les dàdes en el projecte
	 * 
	 * @param      s     Representa el nom de la carpeta, que s'haurà d'escollir entre la llista de les carpetes existents
	 * @exception 	Throws a FolderNotValidException if the file is corrupted or is missing.
	 */
    public void escollirProjecte(String s) throws FolderNotFoundException
    {
		try
		{
			carpeta = new File(s);
		}
		catch(NullPointerException n)
		{
			throw new FolderNotFoundException(s);
		}
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
	public ArrayList<ArrayList<String>> carregarRecomanacionsCarpeta() throws FolderNotValidException
	{
		try
		{
			return cl.carregarArxiu(new File(carpeta, "ratings.db.csv"));
		} catch (IOException e)
		{
			throw new FolderNotValidException(carpeta.getName(), "ratings.db.csv");
		}
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
     * @exception Throws a FolderNotValidException if the file is corrupted or is missing.
     */
	public ArrayList<ArrayList<String>> carregarItemsCarpeta() throws FolderNotValidException
	{
		try
		{
			return cl.carregarArxiu(new File(carpeta, "items.csv"));
		} catch (IOException e)
		{
			throw new FolderNotValidException(carpeta.getName(), "items.csv");
		}
	}
	
	public ArrayList<ArrayList<String>> carregarFitxerExtern(String s) throws FileNotValidException, FileNotFoundException
	{
		File extern;
		try 
		{
			extern = new File(s);
			try
			{
				return cl.carregarArxiu(extern);
			} catch (IOException e) {
				throw new FileNotValidException(s);
			}
			
		} catch (Exception e) {
			throw new FileNotFoundException(s);
		}
	}
}
