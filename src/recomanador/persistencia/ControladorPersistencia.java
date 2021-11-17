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
	
	//Atributs de l'algorisme: algorisme_seleccionat, q, k (en aquest ordre) 
	//private ArrayList<String> atributs_algorisme;
	
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
		//atributs_algorisme = null;
		
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
	
	public boolean existeixenDadesPreprocesades()
	{
		if (carpeta == null) return false;
		
		File[] inside = carpeta.listFiles();
		boolean pesos = false;
		boolean tipus = false;
		
		for (int i = 0; i < inside.length; ++i)
		{
			if (!inside[i].isDirectory())
			{
				if (inside[i].getName().equals("pesos.csv")) pesos = true;
				else if (inside[i].getName().equals("tipus.csv")) tipus = true;
			}
		}
		
		return pesos && tipus;
	}
	
	public boolean existeixenDadesAlgorisme()
	{
		if (carpeta == null) return false;
		
		File[] inside = carpeta.listFiles();
		
		for (int i = 0; i < inside.length; ++i)
			if (!inside[i].isDirectory() && inside[i].getName().equals("algorisme.csv"))
				return true;
		
		return false;
	}
	
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

	/** Get k
	 * Returns the atribute k for the algorithm.
	 * 
	 * @return It returns the atribute k as an int. On error, it returns -1
	 * 
	 * /
	public int getKAlgorisme()
	{
		if (atributs_algorisme = null) return -1;
		try
		{
			
			return atributs_algorisme.get(1).toInt();
		}
	}/*
	
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
	
	private ArrayList<ArrayList<String>> carregarArxiuCarpeta(String s) throws FolderNotValidException
	{
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
	 * This function returns the 3 attributes that the class ControladorDominiAlgorisme needs.
	 * The values are returned in the following order: algorithms_used, q, k
	 * 
	 * @return Returns an ArrayList of length 3 made of strings. The dirst one corresponds to
	 * the algorithm_used parameter. The second one is Q, and the third one is K.
	 */
	public ArrayList<String> carregarAtributsAlgorisme() throws FolderNotValidException
	{
		ArrayList<String> a = carregarArxiuCarpeta("algorisme.csv").get(1);
		if (a.size() != 3) throw new FolderNotValidException(carpeta.getName(), "algorisme.csv");
		return a;
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
		
		//If the file doesn't exist, it stores the data into the correct one
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
	
	public void guardarItems(ArrayList<ArrayList<String>> D) throws FolderNotValidException
	{
		this.guardarDades(D, "items.csv");
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
	
	public void guardarAtributsAlgorisme(int alg, int q, int k) throws FolderNotValidException
	{
		ArrayList<ArrayList<String>> a = new ArrayList<ArrayList<String>>();
		
		a.add(new ArrayList<String>());
		a.get(0).add("algorisme_seleccionat");
		a.get(0).add("q");
		a.get(0).add("k");
		
		a.add(new ArrayList<String>());
		a.get(1).add(Integer.toString(alg));
		a.get(1).add(Integer.toString(q));
		a.get(1).add(Integer.toString(k));
		
		this.guardarDades(a, "algorisme.csv");
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
