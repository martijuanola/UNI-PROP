package src.recomanador.persistencia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import src.recomanador.excepcions.*;

/**
* The class ControladorPersistencia implements the functionalities of writing
* and reading data from memory. It also manages the folder structure
* where the information will be stored.
* 
* @author Pol Sturlese 
*/
public class ControladorPersistencia {
    
/*-----ATRIBUTS-----*/
    private File carpeta;
    private File dades; 		//Carpeta de dades
    ControladorLoad cl;
    ControladorSave cs;
	
	//Atributs que son usats per diverses parts del programa. Es troben en el següent ordre:
	//			0		    | 1 | 2 | 		3	    | 	4	 | 	 5
	//algorisme_seleccionat | Q | K | nom_cjt_items | pos_id | pos_nom
	private ArrayList<String>  estat;
	
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
		cs = new ControladorSave();
		estat = null;
		
		dades = new File("data");
		if (!dades.exists() || !dades.isDirectory()) dades.mkdir();	//It will create the folder if it doesn't exist
		
	}


/*-----CONSULTORES-----*/	
	/**
	 * It returns the name of the project that is being used. This name represents 
	 * a folder inside /data, where the information will be held.
	 * 
	 * @return		The name of the folder is returned, or <b>null</b> in case there's no folder assigned to.
	 * @exception 	FolderNotFoundException Throws a an exception if the folder is missing.
	 */
    public String getNomProjecte() throws FolderNotFoundException
    {
		if (carpeta == null) return null;
		else return carpeta.getName();
	}
	
	/**
	 * Shows all the folders that are inside /data. These folders represents
	 * the projects that the user can choose.
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
	
	/**
	 * Informs you if there are all the necessary files for loading ALL the 
	 * preprocessed data.
	 * 
	 * @return			 Returns <b>true</b> if all the files needed for the preprocessed data
	 * are in the folder. Otherwise it returns <b>false</b>;
	 */
	public boolean existeixenDadesPreprocesades()
	{
		if (carpeta == null) return false;
		
		File[] inside = carpeta.listFiles();
		boolean pesos = false;
		boolean tipus = false;
		boolean estat = false;
		boolean minAt = false;
		boolean maxAt = false;
		
		for (int i = 0; i < inside.length; ++i)
		{
			if (!inside[i].isDirectory())
			{
				if (inside[i].getName().equals("pesos.csv")) pesos = true;
				else if (inside[i].getName().equals("tipus.csv")) tipus = true;
				else if (inside[i].getName().equals("estat.csv")) estat = true;
				else if (inside[i].getName().equals("minAtributs.items.csv")) minAt = true;
				else if (inside[i].getName().equals("maxAtributs.items.csv")) maxAt = true;
			}
		}
		
		return pesos && tipus && estat && minAt && maxAt;
	}
	
	/**
	 * Informs you if there are all the necessary files for loading the 
	 * test data.
	 * 
	 * @return			 Returns <b>true</b> if all the files needed for testing 
	 * the algorithm are in the folder. Otherwise it returns <b>false</b>;
	 */
	public boolean existeixenTestos()
	{
		if (carpeta == null) return false;
		
		File[] inside = carpeta.listFiles();
		boolean known = false;
		boolean unknown = false;
		
		for (int i = 0; i < inside.length; ++i)
		{
			if (!inside[i].isDirectory())
			{
				if (inside[i].getName().equals("ratings.test.known.csv")) known = true;
				else if (inside[i].getName().equals("ratings.test.unknown.csv")) unknown = true;
			}
		}
		
		return known && unknown;
	}

	/** 
	 * Returns the attribute K for the algorithm.
	 * 
	 * @return		It returns the atribute K as an int.
	 * @exception	It throws an exception if the conversion fails or if the data is not found
	 * 
	 */
	public int getKAlgorisme() throws Exception
	{
		if (estat == null) throw new Exception("There's no data for this attribute");
		try
		{
			return Integer.parseInt(estat.get(2));
		}catch (Exception e)
		{
			throw new Exception("The data is invalid.");
		}
	}
	
	/** 
	 * Returns the attribute Q for the algorithm.
	 * 
	 * @return		It returns the atribute Q as an int.
	 * @exception	It throws an exception if the conversion fails or if the data is not found
	 * 
	 */
	public int getQAlgorisme() throws Exception
	{
		if (estat == null) throw new Exception("There's no data for this attribute");
		try
		{
			return Integer.parseInt(estat.get(1));
		}catch (Exception e)
		{
			throw new Exception("The data is invalid.");
		}
	}
	
	/** 
	 * Returns the algorithm that was being used.
	 * 
	 * @return		It returns the algorithm as an int. 
	 * @exception	It throws an exception if the conversion fails or if the data is not found
	 * 
	 */
	public int getAlgorismeSeleccionat() throws Exception
	{
		if (estat == null) throw new Exception("There's no data for this attribute");
		try
		{
			return Integer.parseInt(estat.get(0));
		}catch (Exception e)
		{
			throw new Exception("The data is invalid.");
		}
	}
	
	/** 
	 * Returns the static attribute nom for ConjuntItems class.
	 * 
	 * @return		It returns the name of the item set.
	 * @exception	It throws an exception if the conversion fails or if the data is not found
	 * 
	 */
	public String getNomConjuntItems() throws Exception
	{
		if (estat == null) throw new Exception("There's no data for this attribute");
		return estat.get(3);
	}
	
	/** 
	 * Returns the attribute for the item's id column position.
	 * 
	 * @return		It returns the position of the columns with the id
	 * @exception	It throws an exception if the conversion fails or if the data is not found
	 */
	public int getPosicioID() throws Exception
	{
		if (estat == null) throw new Exception("There's no data for this attribute");
		try
		{
			return Integer.parseInt(estat.get(4));
		}catch (Exception e)
		{
			throw new Exception("The data is invalid.");
		}
	}
	
	/** 
	 * Returns the attribute for the item's name column position.
	 * 
	 * @return		It returns the position of the columns with the name
	 * @exception	It throws an exception if the conversion fails or if the data is not found
	 */
	public int getPosicioNom() throws Exception
	{
		if (estat == null) throw new Exception("There's no data for this attribute");
		try
		{
			return Integer.parseInt(estat.get(5));
		}catch (Exception e)
		{
			throw new Exception("The data is invalid.");
		}
	}
	
	
/*-----MODIFICADORES-----*/   
	
	/**
	 * Establishes the name of the project, which is the one that has the folder that contains the information
	 * that will be loaded, and it's where the information will be stored. It also loads the state of the algorithm 
	 * if it existed, or it creates one with -1 as all values (except for the name, that will have "ERROR_NameNotValid").
	 * 
	 * @param      s     Represents the name of the folder, which sholde be chosen among the list of existing folders.
	 * @exception 	FolderNotFoundException Throws an exception if the folder is incorrect.
	 */
    public void escollirProjecte(String s) throws FolderNotFoundException
    {
		carpeta = new File(dades, s);
		if (!carpeta.exists()) throw new FolderNotFoundException(s);
		
		
		if (existeixenDadesPreprocesades())
		{
			 try { carregarEstat(); }
			 catch (Exception e) {}
		}
		else
		{
			estat = new ArrayList<String>();
			for (int i = 0; i < 6; ++i) estat.add("-1");
			estat.set(3, "ERROR_NameNotValid");
		}
		
	}
	
	/**
	 * Establishes the project folder and the estat ArrayList as null 
	 * to indicate that there's no project selected.
	 */
	public void sortirDelProjecte()
    {
		carpeta = null;
		estat = null;
	}
    
    public void setKAlgorisme(String k) throws Exception
    {
		if (carpeta == null) throw new Exception("You cannot save data");
		else estat.set(2, k);
		
		guardarEstat();
	}
    
    public void setQAlgorisme(String q) throws Exception
    {
		if (carpeta == null) throw new Exception("You cannot save data");
		else estat.set(1, q);
		
		guardarEstat();
	}
	
	/**
	 * Sets the value of the algorithm selected as the one specified.
	 * It also stores into estat.csv all the parameters from the array estat. 
	 * 
	 * @param	a	Indicates the number of the selected algorithm 
	 * @exception Exception		Throws an exception if you are not in a valid folder. 
	 */
    public void setAlgorismeSeleccionat(String a) throws Exception
    {
		if (carpeta == null) throw new Exception("You cannot save data");
		else estat.set(0, a);
		
		guardarEstat();
	}
    
    public void setNomConjuntItems(String nom) throws Exception
    {
		if (carpeta == null) throw new Exception("You cannot save data");
		else estat.set(3, nom);
		
		guardarEstat();
	}
    
    public void setPosicioID(String pos_id) throws Exception
    {
		if (carpeta == null) throw new Exception("You cannot save data");
		else estat.set(4, pos_id);
		
		guardarEstat();
	}
    
    public void setPosicioNom(String pos_nom) throws Exception
    {
		if (carpeta == null) throw new Exception("You cannot save data");
		else estat.set(5, pos_nom);
		
		guardarEstat();
	}
    
    
/*-----LECTURA-----*/	
	
	private ArrayList<ArrayList<String>> carregarArxiuCarpeta(String s) throws FolderNotValidException
	{
		if (carpeta == null) throw new FolderNotValidException();
		
		File f = new File(carpeta, s);
		if (!f.exists()) throw new FolderNotValidException(carpeta.getName(), s);
		
		try
		{
			return cl.carregarArxiu(f);
		} catch (IOException e) {
			throw new FolderNotValidException(carpeta.getName(), s);
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
	public ArrayList<ArrayList<String>> carregarRecomanacionsCarpeta() throws FolderNotValidException
	{
		return carregarArxiuCarpeta("ratings.db.csv");
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
		return carregarArxiuCarpeta("items.csv");
	}
	
	public ArrayList<String> carregarPesosAtributs() throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> temp = carregarArxiuCarpeta("pesos.csv");
		return temp.get(0);
	}
	
	public ArrayList<String> carregarTipusAtributs() throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> temp = carregarArxiuCarpeta("tipus.csv");
		return temp.get(0);
	}
	
	public ArrayList<ArrayList<String>> carregarTestKnown() throws FolderNotValidException
	{
		return carregarArxiuCarpeta("ratings.test.known.csv");
	}
	
	public ArrayList<ArrayList<String>> carregarTestUnknown() throws FolderNotValidException
	{
		return carregarArxiuCarpeta("ratings.test.unknown.csv");
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

	public ArrayList<String> carregarMaxAtributsItems() throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> temp = carregarArxiuCarpeta("maxAtributs.items.csv");
		return temp.get(0);
	}
	
	public ArrayList<String> carregarMinAtributsItems() throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> temp = carregarArxiuCarpeta("minAtributs.items.csv");
		return temp.get(0);
	}
	
	public void carregarEstat() throws FolderNotValidException
	{
		estat = carregarArxiuCarpeta("estat.csv").get(1);
		if (estat.size() != 6) throw new FolderNotValidException(carpeta.getName(), "estat.csv");
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
				!(c >= '0' && c <= '9') &&
				!(c == '-') && !(c == '_') && !(c == '.'))
				throw new FolderNotValidException("El nom conté caràcters invàlids." + 
					" Només es poden usar lletres, nombres, '-', '_', '.'", false);
		}
		
		carpeta = new File(dades, s);
		if (carpeta.exists())
		{
			carpeta = null;
			throw new FolderNotValidException("Ja existeix un projecte annomenat " + s, false);
		}
		if (!carpeta.mkdirs())
		{
			carpeta = null;
			throw new FolderNotValidException("No es pot crear el projecte", false);
		}
	}
	
	private void guardarDades(ArrayList<ArrayList<String>> D, String s) throws FolderNotValidException
	{
		if (carpeta == null) throw new FolderNotValidException();
		
		File f = new File(carpeta, s);
		
		//If the file doesn't exist, it stores the data into the correct one
		//Otherwise, a temporal file is created, to prevent data losses from the prevoius
		//executions, in case there's a failure during the execution (like a power outage)
		if (!f.exists())
		{
			try
			{
				f.createNewFile();
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
	
	public void guardarItems(ArrayList<String> head, ArrayList<ArrayList<ArrayList<String>>> body) throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> taula = new ArrayList<ArrayList<String>>();
		
		taula.add(head);
		
		for (int i = 0; i < body.size(); ++i)
		{
			ArrayList<String> temp = new ArrayList<String>();
			ArrayList<ArrayList<String>> actual = body.get(i);
			
			for (int j = 0; j < actual.size(); ++j)
			{
				String s = "";
				
				for (int k = 0; k < actual.get(j).size(); ++k)
				{
					s += actual.get(j).get(k);
					if (k != actual.get(j).size() - 1) s += ";";
				}
				
				temp.add(s);
			}
			
			taula.add(temp);
		}
		
		this.guardarDades(taula, "items.csv");
	}

	public void guardarPesosAtributs(ArrayList<Float> pesos) throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> D = new ArrayList<ArrayList<String>>();
		D.add(new ArrayList<String>());
		
		for (int i = 0; i < pesos.size(); ++i)D.get(0).add(pesos.get(i).toString());
		
		this.guardarDades(D, "pesos.csv");
	}
	
	/*public void guardarPesosAtributs(ArrayList<String> pesos) throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> D = new ArrayList<ArrayList<String>>();
		D.add(pesos);
		
		this.guardarDades(D, "pesos.csv");
	}*/
	
	public void guardarTipusAtributs(ArrayList<String> tip) throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> D = new ArrayList<ArrayList<String>>();
		D.add(tip);
		
		this.guardarDades(D, "tipus.csv");
	}
	
	public void guardarMaxAtributsItems(ArrayList<String> max_atr) throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> D = new ArrayList<ArrayList<String>>();
		D.add(max_atr);
		
		this.guardarDades(D, "maxAtributs.items.csv");
	}
	
	public void guardarMinAtributsItems(ArrayList<String> min_atr) throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> D = new ArrayList<ArrayList<String>>();
		D.add(min_atr);
		
		this.guardarDades(D, "minAtributs.items.csv");
	}
	
	public void guardarEstat() throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> a = new ArrayList<ArrayList<String>>();
		
		a.add(new ArrayList<String>());
		a.get(0).add("algorisme_seleccionat");
		a.get(0).add("q");
		a.get(0).add("k");
		a.get(0).add("nom_cjt_items");
		a.get(0).add("pos_id");
		a.get(0).add("pos_nom");
		
		a.add(estat);
		
		this.guardarDades(a, "estat.csv");
	}


/*-----TESTING-----*/
/* Aquestes són funcions que criden les funcions privades de la classe
 * Serverixen només per a poder testejar-les. En la implementació
 * final s'hauran d'eliminar*/

/* Per aquestes funcions no es faran testos especifics, sinó que s'usaran per
 * a provar les classes privades que es criden.
 * Demostració formal de que la seva correctesa d'una funció depèn de la funció que crida:
 * És facil veure que aquestes classes funcionaran si i només si funcionen les classes
 * que criden (ja que aquestes classes es componen d'una unica crida).
 * 
 * L'entrada d'aquestes classes és la mateixa que la que es crida
 * (i es passen els paràmetres si n'hi ha cap a l'altre). També la sortida és
 * la mateixa, ja que es retorna directament la de la funció cridada (si 
 * es retorna alguna cosa). I les excepcions tampoc canvien, ja que en 
 * tots els casos es poden generar les mateixes que en les funcions 
 * cridades, i la funció de testing no n'afegeix cap extra.
 * 
 * Per tant, queda demostrat que aquestes classes funcionaran si i només si
 * ho fan les classes privades que es criden
 * */
 
	public ArrayList<ArrayList<String>> TESTcarregarArxiuCarpeta(String s) throws FolderNotValidException
	{
		return this.carregarArxiuCarpeta(s);
	}
	
	public void TESTguardarDades(ArrayList<ArrayList<String>> D, String s) throws FolderNotValidException
	{
		this.guardarDades(D, s);
	}
}
