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

/*----- INFO SOBRE VALORS -----*/
	//Atributs que son usats per diverses parts del programa. Es troben en el següent ordre:
	//			0		    | 1 | 2 | 		3	    | 	4	 | 	 5
	//algorisme_seleccionat | Q | K | nom_cjt_items | pos_id | pos_nom
    
/*----- STATICS -----*/

	/** Contains the only instance of the class **/
	private static ControladorPersistencia inst;

	/** Object from the class ControladorLoad used for reading the .csv data. **/
	private ControladorLoad cl; 

	/** Object from the class ControladorSave used for storing the data as .csv. **/
	private ControladorSave cs; 
	
	/**
     * Returs the only instance of the class, and if it's not created, it creates it.
     *
     * @return     The instance.
     */
    public static ControladorPersistencia getInstance() {
        if(ControladorPersistencia.inst == null) inst = new ControladorPersistencia();
        return inst;
    }

    /**
	 * Contains a File that indicates wich project is being used from the
	 * ./data folder.
	 */
    private File carpeta;
    /**
	 * Contains a File with the path and other information about the folder
	 * ./data, where projects will be stored.
	 */
    private File dades;


/*-----CREADORES-----*/   
    
    /**
	 * Creates a new instance of the class ControladorPersistencia. It also finds the
	 * data folder, and if it doen't exist, it creates it.
	 */
    private ControladorPersistencia()
    {
		carpeta = null;
		cl = ControladorLoad.getInstance();		
		cs = ControladorSave.getInstance();
		
		dades = new File("data");
		if (!dades.exists() || !dades.isDirectory()) dades.mkdir();	//It will create the folder if it doesn't exist
	}


/*-----CONSULTORES-----*/	
	/**
	 * It returns the name of the project that is being used. This name represents 
	 * a folder inside /data, where the information will be held.
	 * 
	 * @return		The name of the folder is returned, or <b>null</b> in case there's no folder assigned to.
	 * @exception 	FolderNotValidException Throws a an exception if the folder is missing.
	 */
    public String getNomProjecte() throws FolderNotValidException
    {
		if (carpeta == null) throw new FolderNotValidException("Projecte no inicialitzat!!",true);
		return carpeta.getName();
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
	}
	
	/**
	 * Establishes the project folder and the estat ArrayList as null 
	 * to indicate that there's no project selected.
	 */
	public void sortirDelProjecte()
    {
		carpeta = null;
	}
    
/*-----LECTURA-----*/	
	/**
	 * Reads a file specified from the folder "carpeta". The file must be
	 * a .csv file (or at least the content must have that structure), and
	 * needs to have the same number of columns for every row.
	 * 
	 * @param		s		Specifies the file that will be read.
	 * 
     * @return Returns an ArrayList of ArrayList of the values. Each ArrayList 
     * of ArrayList(line) contains an array of strings (columns).
     * 
     * @exception FolderNotValidException Throws an exception if the file is corrupted or is missing.
     */
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
	 * Returns the recomendations and valorations read from memory
	 * 
     * @return Returns an ArrayList of ArrayList of the values. Each ArrayList 
     * of ArrayList(line) contains an array of strings (columns).
     * The first line corresponds to the header of the file, where each
     * column is its identifier. 
     * The rest of the lines contain the values read.
     * 
     * @exception FolderNotValidException Throws a FolderNotValidException if the file is corrupted or is missing.
     */
	public ArrayList<ArrayList<String>> carregarRecomanacionsCarpeta() throws FolderNotValidException
	{
		return carregarArxiuCarpeta("ratings.db.csv");
	}
	
	/**
	 * Returns the items read from memory
	 * 
     * @return Returns an ArrayList of ArrayList of the values. Each ArrayList 
     * of ArrayList(line) contains an array of strings (columns).
     * The first line corresponds to the header of the file, where each
     * column is its identifier.  
     * The rest of the lines contain the values read.
     * 
     * @exception FolderNotValidException Throws a FolderNotValidException if the file is corrupted or is missing.
     */
	public ArrayList<ArrayList<String>> carregarItemsCarpeta() throws FolderNotValidException
	{
		return carregarArxiuCarpeta("items.csv");
	}
	
	/**
	 * Returns some information read from memory about the items: the
	 * weights of the attributes.
	 * 
     * @return Returns an ArrayList of the values (represented as Strings). 
     * 
     * @exception FolderNotValidException Throws a FolderNotValidException if the file is corrupted or is missing.
     */
	public ArrayList<String> carregarPesosAtributs() throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> temp = carregarArxiuCarpeta("pesos.csv");
		return temp.get(0);
	}
	
	/**
	 * Returns some information read from memory about the items: the
	 * type of the attributes (floats, integers, strings, ...).
	 * 
     * @return Returns an ArrayList of the types (represented as Strings). 
     * 
     * @exception FolderNotValidException Throws an exception if the file is corrupted or is missing.
     */
	public ArrayList<String> carregarTipusAtributs() throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> temp = carregarArxiuCarpeta("tipus.csv");
		return temp.get(0);
	}
	
	/**
	 * Returns some valorations read from memory. These are used for 
	 * testing the algoritm, and it's data that can be added to the program.
	 * 
     * @return Returns an ArrayList of ArrayList of the values. Each ArrayList 
     * of ArrayList(line) contains an array of strings (columns).
     * The first line corresponds to the header of the file, where each
     * column is its identifier. 
     * The rest of the lines contain the values read.
     * 
     * @exception FolderNotValidException Throws a FolderNotValidException if the file is corrupted or is missing.
     */
	public ArrayList<ArrayList<String>> carregarTestKnown() throws FolderNotValidException
	{
		return carregarArxiuCarpeta("ratings.test.known.csv");
	}
	
	/**
	 * Returns some valorations read from memory. These are used for testing 
	 * the algoritm, but it's not intended for them to be added to the program's 
	 * input, as this is used for comparing the results of the algorithm.
	 * 
     * @return Returns an ArrayList of ArrayList of the values. Each ArrayList 
     * of ArrayList(line) contains an array of strings (columns).
     * The first line corresponds to the header of the file, where each
     * column is its identifier. 
     * The rest of the lines contain the values read.
     * 
     * @exception FolderNotValidException Throws a FolderNotValidException if the file is corrupted or is missing.
     */
	public ArrayList<ArrayList<String>> carregarTestUnknown() throws FolderNotValidException
	{
		return carregarArxiuCarpeta("ratings.test.unknown.csv");
	}
	
	/**
	 * Returns a csv table read from memory
	 * 
	 * @param	s	Specifies the absolute path of the file you want to read
	 * 
     * @return Returns an array of arrays of the values. Each array of arrays(line)
     * contains an array of strings (columns). 
     * The first line corresponds to the header of the file, where each
     * column is its identifier. 
     * The rest of the lines contain the values read.
     * 
     * @exception FileNotValidException Throws a FileNotValidException if the file is corrupted.
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

	/**
	 * Returns some information read from memory about the items: the
	 * maximum value of every attribute from the item set.
	 * 
     * @return Returns an ArrayList of the values (represented as Strings). 
     * 
     * @exception FolderNotValidException Throws a FolderNotValidException if the file is corrupted or is missing.
     */
	public ArrayList<String> carregarMaxAtributsItems() throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> temp = carregarArxiuCarpeta("maxAtributs.items.csv");
		return temp.get(0);
	}
	
	/**
	 * Returns some information read from memory about the items: the
	 * minimum value of every attribute from the item set.
	 * 
     * @return Returns an ArrayList of the values (represented as Strings). 
     * 
     * @exception FolderNotValidException Throws a FolderNotValidException if the file is corrupted or is missing.
     */
	public ArrayList<String> carregarMinAtributsItems() throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> temp = carregarArxiuCarpeta("minAtributs.items.csv");
		return temp.get(0);
	}

	/**
	 * Loads the following values if the folder specified by the 
	 * attribute carpeta contains them:
	 *
	 * The attributes are ordered inside the array as stated below: <br>
	 * &emsp;&emsp; <b>0.</b> algorisme_seleccionat <br>
	 * &emsp;&emsp; <b>1.</b> Q <br>
	 * &emsp;&emsp; <b>2.</b> K <br>
	 * &emsp;&emsp; <b>3.</b> nom_cjt_items <br>
	 * &emsp;&emsp; <b>4.</b> pos_id <br>
	 * &emsp;&emsp; <b>5.</b> pos_nom <br>
	 * 
     * @return Returns an ArrayList of the values (represented as Strings). 
     * 
     * @exception FolderNotValidException Throws a FolderNotValidException if the file is corrupted or is missing.
     */
	public ArrayList<String> carregarEstat() throws FolderNotValidException
	{
		ArrayList<String> estat = carregarArxiuCarpeta("estat.csv").get(1);
		if (estat.size() != 6) throw new FolderNotValidException(carpeta.getName(), "estat.csv");
		return estat;
	}
	
/*-----ESCRIPTURA-----*/

	/**
	 * Creates an empty folder to store all the files. It also sets the
	 * variable carpeta as the file pointing the new one.
     * 
     * @param	s	It's the name that the folder will have. It can only contain
     * letters, numbers, '.', '-' and '_'
     * 
     * @exception FolderNotValidException Throws a FolderNotValidException if the file is corrupted.
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
	
	/**
	 * Save the data into the file specified. <br>
	 * To avoid data loss, a temporal file is created, and then it's replaced
	 * by the original one. This prevents the anihilation of the data in case
	 * that the computer suddenlty stops working.
     * 
     * @param		D		It's an ArrayList of ArrayList of Strings. The array
     * needs to have the same number of columns for each row. Usually, the first
     * line is interpreted as the header, but it's not mandatory.
     * @param		s		It's the name that the file will have. It's recommended
     * that it ends with .csv, as the data will be stored in that format.
     * 
     * @exception FolderNotValidException Throws a FolderNotValidException if the file is corrupted.
     * 
     */
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
	
	/**
	 * Save the data into the file ratings.db.csv <br>
     * 
     * @param		D		It's an ArrayList of ArrayList of Strings. The array
     * needs to have the same number of columns for each row. The first
     * line is interpreted as the header.
     * 
     * @exception FolderNotValidException Throws a FolderNotValidException if the file is corrupted. 
     */
	public void guardarRecomanacions(ArrayList<ArrayList<String>> D) throws FolderNotValidException
	{
		this.guardarDades(D, "ratings.db.csv");
	}
	
	/**
	 * Save the data into the file items.csv <br>
     * 
     * @param		head		It's an ArrayList of Strings. It represents the header
     * of the .csv table, and will be printed as the first line
     * @param		body		It's an ArrayList of ArrayList of ArrayList of Strings.
     * It represents for each row, an ArrayList of columns. Each column can contain
     * a variable number of strings, so the content of the column is specified
     * as an ArrayList of Strings. (These multiple values will be concatenated, adding a
     * ';' betwen them.
     * 
     * @exception FolderNotValidException Throws an exception if the file is corrupted.
     */
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
	
	/**
	 * Save the data into the file pesos.csv <br>
     * 
     * @param		pesos		It's an ArrayList of Floats wich represents
     * the weight of each attribute from the item set.
     * 
     * @exception FolderNotValidException Throws an exception if the file is corrupted. 
     */
	public void guardarPesosAtributs(ArrayList<String> pesos) throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> D = new ArrayList<ArrayList<String>>();
		D.add(pesos);

		this.guardarDades(D, "pesos.csv");
	}
	
	/**
	 * Save the data into the file tipus.csv <br>
     * 
     * @param		tip		It's an ArrayList of Strings wich represents
     * the type of each attribute from the item set.
     * 
     * @exception FolderNotValidException Throws an exception if the file is corrupted. 
     */	
	public void guardarTipusAtributs(ArrayList<String> tip) throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> D = new ArrayList<ArrayList<String>>();
		D.add(tip);
		
		this.guardarDades(D, "tipus.csv");
	}
	
	/**
	 * Save the data into the file maxAtributs.items.csv <br>
     * 
     * @param		max_atr		It's an ArrayList of Strings wich contains
     * the maximum value of each attribute from the item set.
     * 
     * @exception FolderNotValidException Throws an exception if the file is corrupted. 
     */	
	public void guardarMaxAtributsItems(ArrayList<String> max_atr) throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> D = new ArrayList<ArrayList<String>>();
		D.add(max_atr);
		
		this.guardarDades(D, "maxAtributs.items.csv");
	}
	
	/**
	 * Save the data into the file minAtributs.items.csv <br>
     * 
     * @param		min_atr		It's an ArrayList of Strings wich contains
     * the minimum value of each attribute from the item set.
     * 
     * @exception FolderNotValidException Throws an exception if the file is corrupted. 
     */	
	public void guardarMinAtributsItems(ArrayList<String> min_atr) throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> D = new ArrayList<ArrayList<String>>();
		D.add(min_atr);
		
		this.guardarDades(D, "minAtributs.items.csv");
	}
	
	/**
	 * Save the data from the estat ArrayList into the file estat.csv <br>
     * 
	 * @param      estat The values that need to be stored: (algorisme_seleccionat | Q | K | nom_cjt_items | pos_id | pos_nom)
	 * 
     * @exception FolderNotValidException Throws an exception if the file is corrupted. 
     */	
	public void guardarEstat(ArrayList<String> estat) throws FolderNotValidException
	{
		if (estat.size() != 6) throw new FolderNotValidException("El nombre de valors no és el correcte. (a,1,k,nomCjtI,posID,posNOM)");
		
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
 * a provar les classes privades que es criden. <br>
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
 */
	/**
	 * Implements a public format for the function carregarArxiuCarpeta(...).
	 * Reads the file specified from the folder "carpeta". <br>
	 * After Testing, this function will be eliminated. <br>
	 * For a demonstration that this function will work if and only if
	 * carregarArxiuCarpeta(...) works, go to line 834 on the source code. 
	 * The demonstration lets us test the private class unsing this one.
     * 
     * @param		s		Specifies the file that will be read.
     * 
     * @return Returns an ArrayList of ArrayList of the values. Each ArrayList 
     * of ArrayList(line) contains an array of strings (columns).
     * 
     * @exception FolderNotValidException Throws an exception if the file is corrupted or is missing.
     */
	public ArrayList<ArrayList<String>> TESTcarregarArxiuCarpeta(String s) throws FolderNotValidException
	{
		return this.carregarArxiuCarpeta(s);
	}
	
	/**
	 * Implements a public format for the function guardarDades(...).
	 * It saves the data into the file specified. <br>
	 * After Testing, this function will be eliminated. <br>
	 * For a demonstration that this function will work if and only if
	 * guardarDades(...) works, go to line 834 on the source code. 
	 * The demonstration lets us test the private class unsing this one.
     * 
     * @param		D		It's an ArrayList of ArrayList of Strings. The array
     * needs to have the same number of columns for each row. Usually, the first
     * line is interpreted as the header, but it's not mandatory.
     * @param		s		It's the name that the file will have. It's recommended
     * that it ends with .csv, as the data will be stored in that format.
     * 
     * @exception FolderNotValidException Throws a FolderNotValidException if the file is corrupted. 
     */
	public void TESTguardarDades(ArrayList<ArrayList<String>> D, String s) throws FolderNotValidException
	{
		this.guardarDades(D, s);
	}
}
