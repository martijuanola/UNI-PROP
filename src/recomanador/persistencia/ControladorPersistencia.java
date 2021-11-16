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
    
/*-----ATRIBUTS-----*/
    private File carpeta;
    private File dades; 		//Carpeta de dades
    ControladorLoad cl;
    ControladorSave cs;


/*-----CREADORES-----*/   
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
		
		dades = new File("data");
		if (!dades.exists()) dades.mkdir();	//It will create the folder if it doesn't exist
	}


/*-----CONSULTORES-----*/	
	/**
	 * Estableix el nom de la carpeta que s'usarà epr carregar les dàdes en el projecte
	 * 
	 * @param      s     Representa el nom de la carpeta, que s'haurà d'escollir entre la llista de les carpetes existents
	 * @exception 	FolderNotFoundException Throws a FolderNotValidException if the file is corrupted or is missing.
	 */
    public String getNomProjecte() throws FolderNotFoundException
    {
		if (carpeta == null) return null;
		else return carpeta.getName();
	}
	
	/**
	 * Estableix el nom de la carpeta que s'usarà epr carregar les dàdes en el projecte
	 * 
	 * @return			 Returns all the folders that are inside the saved projects folder.
	 */
	public ArrayList<String> llistatCarpetes()
	{
		ArrayList<String> projectes = new ArrayList<String>();
		File[] pr_files= dades.listFiles();
		
		for (int i = 0; i < pr_files.length; ++i)
			if (pr_files[i].isDirectory() /*&& is_valid_project(pr_files[i])*/)
				projectes.add(pr_files[i].getName());
		
		return projectes;
	}


/*-----MODIFICADORES-----*/   
	/**
	 * Estableix el nom de la carpeta que s'usarà epr carregar les dàdes en el projecte
	 * 
	 * @param      s     Representa el nom de la carpeta, que s'haurà d'escollir entre la llista de les carpetes existents
	 * @exception 	FolderNotFoundException Throws a FolderNotValidException if the file is corrupted or is missing.
	 */
    public void escollirProjecte(String s) throws FolderNotFoundException
    {
		carpeta = new File(dades, s);
		if (!carpeta.exists()) throw new FolderNotFoundException(s);
	}
	
	/**
	 * Establishes the project folder as null 
	 */
	public void sortirDelProjecte()
    {
		carpeta = null;
	}
    
/*-----LECTURA-----*/	
		
	/**
	 * Returns the recomendations read from memory
	 * 
     * @return Returns an array of arrays of the values. Each array of arrays(line)
     * contains an array of strings (columns). 
     * The first line corresponds to the header of the file, where each
     * column its identifier. 
     * The rest of the lines contain the values read.
     * 
     * @exception FolderNotValidException Throws a FolderNotValidException if the file is corrupted or is missing.
     */
	public ArrayList<ArrayList<String>> carregarRecomanacionsCarpeta() throws FolderNotValidException
	{
		File f = new File(carpeta, "ratings.db.csv");
		if (!f.exists()) throw new FolderNotValidException(carpeta.getName(), "ratings.db.csv");
		
		try
		{
			return cl.carregarArxiu(f);
		} catch (IOException e) {
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
     * The rest of the lines contain the values read.
     * 
     * @exception FolderNotValidException Throws a FolderNotValidException if the file is corrupted or is missing.
     */
	public ArrayList<ArrayList<String>> carregarItemsCarpeta() throws FolderNotValidException
	{
		File f = new File(carpeta, "items.csv");
		if (!f.exists()) throw new FolderNotValidException(carpeta.getName(), "items.csv");
		
		try
		{
			return cl.carregarArxiu(f);
		} catch (IOException e) {
			throw new FolderNotValidException(carpeta.getName(), "items.csv");
		}
	}
	
	/**
	 * Returns a csv table read from memory
	 * 
     * @return Returns an array of arrays of the values. Each array of arrays(line)
     * contains an array of strings (columns). 
     * The first line corresponds to the header of the file, where each
     * column its identifier. 
     * The rest of the lines contain the values read. <p>
     * 
     * @exception FolderNotValidException Throws a FileNotValidException if the file is corrupted.
     * @exception FileNotFoundException Throws a FileNotFoundException if the file is missing.
     */
	public ArrayList<ArrayList<String>> carregarFitxerExtern(String s) throws FileNotValidException, FileNotFoundException
	{
		File extern = new File(s);
		if (!extern.exists()) throw new FileNotFoundException(s);
		
		try
		{
			return cl.carregarArxiu(extern);
		} catch (IOException e) {
			throw new FileNotValidException(s);
		}
	}


/*-----ESCRIPTURA-----*/
	/**
	 * Creates an empty folder to store all the files. It also sets the
	 * variable carpeta as the file pointing the new one.
     * 
     * @param	s	It's the name that the folder will have. It can only contain
     * letters, numbers, '.', '-' and '_'
     * 
     * @exception FolderNotValidException Throws a FileNotValidException if the file is corrupted.
     * 
     */
	public void crearProjecte(String s) throws FolderNotValidException
	{
		//Comprovació de la pre de s
		for (int i = 0; i < s.length(); ++i)
		{
			char c = s.charAt(i);
			if (!(c >= 'A' && c <= 'Z') &&
				!(c >= 'a' && c <= 'z') &&
				!(c == '-') && !(c == '_') )
				throw new FolderNotValidException("El nom conté caràcters invàlids." + 
					" Només es poden usar lletres, nombres, '-', '_', '.'", false);
		}
		
		carpeta = new File(dades, s);
		if (carpeta.exists())
		{
			carpeta = null;
			throw new FolderNotValidException("Ja existeix un projecte annomenat " + s, false);
		}
		else carpeta.mkdir();
	}
	
	private void guardarDades(ArrayList<ArrayList<String>> D, String s) throws FolderNotValidException
	{
		if (carpeta == null) throw new FolderNotValidException();
		
		File f = new File(carpeta, s);
		
		//If the file doesn't exist, it tores the data into the correct one
		//Otherwise, a temporal file is created, to prevent data losses from the prevoius
		//executions, in case there's a failure during the execution (like a power outage)
		if (!f.exists())
		{
			try
			{
				cs.guardarArxiu(f, D);
			} catch (IOException e) {
				throw new FolderNotValidException(false);
			}
		}
		else
		{
			File temp = new File(carpeta, "temp_" + s);
			try
			{
				temp.createNewFile();
				cs.guardarArxiu(temp, D);
				
				f.delete();
				temp.renameTo(f);
			} catch (IOException e) {
				throw new FolderNotValidException(false);
			}			
		}
	}

	public void guardarRecomanacions(ArrayList<ArrayList<String>> D) throws FolderNotValidException
	{
		this.guardarDades(D, "ratings.db.csv");
	}
	
	public void guardarRecomanacions(ArrayList<ArrayList<String>> D) throws FolderNotValidException
	{
		this.guardarDades(D, "items.csv");
	}
}
