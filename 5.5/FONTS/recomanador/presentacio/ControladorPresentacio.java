package src.recomanador.presentacio;

import java.util.ArrayList;

import src.recomanador.domini.ControladorDomini;
import src.recomanador.excepcions.*;

public class ControladorPresentacio {
    private static ControladorDomini domini;
    private static ControladorPresentacio instancia = null;

    private ControladorPresentacio() {
        domini = ControladorDomini.getInstance();
    }

    public static ControladorPresentacio getInstance() {
        if (instancia == null) {
            instancia = new ControladorPresentacio();
        }
        return instancia;
    }
	
	public static ArrayList<ArrayList<String>> executarAlgorisme()
	{
		try
		{
			ArrayList<ArrayList<String>> old = 
				domini.getAllRecomanacionsUsuari(domini.getActiveUserId());
			
			domini.createRecomanacions();
			ArrayList<ArrayList<String>> young = 
				domini.getAllRecomanacionsUsuari(domini.getActiveUserId());
			
			ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
			
			for (int i = 0; i < young.size(); ++i)
			{
				ArrayList<String> one = young.get(i);
				boolean found = false;
				
				for (int j = 0; j < old.size(); ++j)
					if (one.get(1).equals(old.get(j).get(1))) found = true;
				
				if (!found)
				{
					temp.add(new ArrayList<String>());
					temp.get(temp.size()-1).add(one.get(1));
				}
			}
			
			for (int i = 0; i < temp.size(); ++i)
			{
				ArrayList<ArrayList<String>> item =
					domini.getItem(Integer.parseInt(temp.get(i).get(0)));
				String nom = item.get(Integer.parseInt(domini.getPosNomItem())).get(0);
				temp.get(i).add(nom);
			}
			//La diferència de conjunts serà els que es mostrarà
			return temp;
		} catch(Exception e) {
			new VistaError(e.getMessage());
			return null;
		}
	}
	
	public static ArrayList<String> executarTestAlgorisme()
	{
		try
		{
			return domini.runTest();
		}
		catch (Exception e)
		{
			new VistaError(e.getMessage());
			return null;
		}	
	}
	
	public static ArrayList<ArrayList<String>> executarAlgorisme3()
	{
		ArrayList<ArrayList<String>> x = new ArrayList<ArrayList<String>>();
		
		x.add(new ArrayList<String>());
		x.get(0).add("123");
		x.get(0).add("Regreso al futuro");
		
		x.add(new ArrayList<String>());
		x.get(1).add("80085");
		x.get(1).add("Nananan bat-man");
		
		return x;
	}
	
	public static String getId() {
		try {		
			return domini.getActiveUserId();
		} catch(Exception e) {
			new VistaError(e.getMessage());
			return "ERROR";
		}
	}
	
	public static String getNomProjecte() {
		try {		
			return domini.getNomProjecte();
		} catch(Exception e) {
			new VistaError(e.getMessage());
			return "ERROR";
		}
	}
	
	public static void guardar() {
		try {		
			domini.saveSession();
		} catch(Exception e) {
			new VistaError("Error en guardar.");
		}
	}
		
	public static boolean isAdmin() {
		return domini.isAdmin();
	}
	public static String getAlgorisme() {
		try {		
			return domini.getAlgorisme();
		} catch(Exception e) {
			new VistaError(e.getMessage());
			return "0";
		}
	}
	
	public static String getQ() {
		try {		
			return domini.getQ();
		} catch(Exception e) {
			new VistaError(e.getMessage());
			return "1";
		}
	}
	
	public static String getK() {
		try {		
			return domini.getK();
		} catch(Exception e) {
			new VistaError(e.getMessage());
			return "1";
		}
	}
	
	public static void setAlgorisme(String alg) {
		try
		{
			domini.setAlgorisme(alg);
		} catch(Exception e) {
			new VistaError(e.getMessage());
		}
	}
	
	public static void setQ(String q) {
		try
		{
			domini.setQ(q);
		} catch(Exception e) {
			new VistaError(e.getMessage());
		}
	}
	
	public static void setK(String k) {
		try
		{
			domini.setK(k);
		} catch(Exception e) {
			new VistaError(e.getMessage());
		}
	}
	
	public static void logOut() {
		domini.logout();
	}
	
	public static void setAdmin() {
		try
		{
			domini.loginAdmin();
		 } catch(Exception e) {
			new VistaError(e.getMessage());
		}
	}
	
	public static void setUser(String id) {
		try
		{
			domini.login(Integer.parseInt(id));
		} catch(Exception e) {
			new VistaError(e.getMessage());
		}
	}
		
    public static void obreVistaPrincipal() {
        new VistaPrincipal();
    }

    public static void obreVistaInicial() {
        new VistaInicial();
    }

    public static void carregarProjecte(String proj) throws FolderNotFoundException, FolderNotValidException, DataNotValidException {
        domini.loadSession(proj);
    }

    public static void logInUser(int id) throws AlreadyLogedInException {
        domini.login(id);
    }

    public static void logInAdmin() throws AlreadyLogedInException {
        domini.loginAdmin();
    }

    public static void carregarProjecteNou(String nom, String itemsFile, String ratingsFile) throws FolderNotValidException, FileNotValidException, FileNotFoundException {
        domini.createSession(nom, itemsFile, ratingsFile);
    }
	
	public static void eliminarUsuari(String user_id) throws PrivilegesException, UserNotFoundException{
		domini.removeUsuari(user_id);
	}
	

	public static boolean crearProjecte(String nomProj, ArrayList<String> tipus, ArrayList<String> noms) {
		try {
			domini.createEmptySession(nomProj, noms, tipus);
			System.out.println("Projecte creat");
			return true;
		}
		catch (PrivilegesException | FolderNotValidException | ItemTypeNotValidException | DataNotValidException e) {
			ControladorPresentacio.obreVistaError(e.getMessage());
			return false;
		}
	}

	public static void editarItem(ArrayList<ArrayList<String>> atributs) {
		try {
			domini.editItem(atributs);
			System.out.println("Item editat");
		}
		catch (PrivilegesException | ItemNotFoundException | ItemStaticValuesNotInitializedException
				| ItemNewAtributesNotValidException e) {
			ControladorPresentacio.obreVistaError(e.getMessage());
		}
	}

    public static ArrayList<String> getProjectesDisponibles() {
        return domini.getAllProjectes();
    }

    public static void obreVistaError(String text) {
        new VistaError(text);
    }

    public static ArrayList<String> getTipus() {
        return domini.getTipusAsStrings();
    }

    public static ArrayList<ArrayList<ArrayList<String>>> getAllItems() {
        return domini.getAllItems();
    }
    
    public static ArrayList<String> getAllUsers() throws PrivilegesException {
		return domini.getAllUsuaris();
	}

	public static String getPosItemId() {
		return domini.getPosIdItem();
	}

	public static String getPosItemNom() {
		return domini.getPosNomItem();
	}
	
	public static ArrayList<ArrayList<String>> getRecomanacions(String USER_ID) throws PrivilegesException {
		return domini.getAllRecomanacionsUsuari(USER_ID);
	}
	
	public static ArrayList<ArrayList<String>> getValoracions(String USER_ID) throws PrivilegesException {
		return domini.getAllValoracionsUsuari(USER_ID);
	}
	
	public static ArrayList<String> getPesos() throws PrivilegesException {
		return domini.getPesos();
	}

	public static void setPesos(ArrayList<String> pesosS) {
		try {
			domini.setPesos(pesosS);
			System.out.println("Pesos modificats");
		}
		catch (PrivilegesException | ItemWeightNotCorrectException e) {
			ControladorPresentacio.obreVistaError(e.getMessage());
		}
	}

	public static ArrayList<ArrayList<String>> getItem(int id) throws ItemNotFoundException {
		return domini.getItem(id);
	}

    public static boolean addItem(ArrayList<ArrayList<String>> nouItem) {
		try {
			domini.addItem(nouItem);
			return true;
		} catch (PrivilegesException | ItemStaticValuesNotInitializedException | ItemNewAtributesNotValidException e) {
			ControladorPresentacio.obreVistaError(e.getMessage());
			return false;
		}
		catch (ItemIdNotValidException e) {
			ControladorPresentacio.obreVistaError("Ja existeix un ítem amb aquest Identificador.");
			return false;
		}
    }

	public static void eliminarItem(String id) {
		try {
			domini.removeItem(id);
			System.out.println("Item eliminat");
		} catch (PrivilegesException | ItemStaticValuesNotInitializedException | ItemNotFoundException e) {
			ControladorPresentacio.obreVistaError(e.getMessage());
		}
	}

    public static ArrayList<String> getHeaderItems() {
        return domini.getHeader();
    }

    public static ArrayList<String> getTipusItems() {
        return domini.getTipus();
    }

    public static void setTipusItems(ArrayList<String> tipusS) throws PrivilegesException, ItemTypeNotValidException, DataNotValidException {
        domini.setTipus(tipusS);
    }
    
    public static void eliminarValoracio(String user_id, String item_id)
    {
		try{
			domini.removeValoracio(item_id, user_id);
		}catch(Exception e) {
			new VistaError(e.getMessage());
		}
	}
	
	public static void eliminarRecomanacio(String user_id, String item_id)
    {
		try{
			domini.removeRecmonacio(item_id, user_id);
		}catch(Exception e) {
			new VistaError(e.getMessage());
		}
	}
    	
	public static void valorar(String id_item, String user_id, String rate)
	{
		try {
			domini.setValoracio(id_item, user_id, rate);
		} catch(Exception e) {
			new VistaError(e.getMessage());
		}
	}
	
    public static void main(String[] args) {
        instancia = new ControladorPresentacio();
        obreVistaInicial();
    }
}
