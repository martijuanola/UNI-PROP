package src.recomanador.presentacio;

import java.util.ArrayList;
import javax.swing.UIManager;

import src.recomanador.domini.ControladorDomini;
import src.recomanador.excepcions.AlreadyLogedInException;
import src.recomanador.excepcions.DataNotValidException;
import src.recomanador.excepcions.FileNotFoundException;
import src.recomanador.excepcions.FileNotValidException;
import src.recomanador.excepcions.FolderNotFoundException;
import src.recomanador.excepcions.FolderNotValidException;
import src.recomanador.excepcions.ItemTypeNotValidException;
import src.recomanador.excepcions.PrivilegesException;

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
					if (one.get(1) == old.get(j).get(1)) found = true;
				
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
			//new VistaError(e.getMessage());
			//No va, pq l'error està buit
			new VistaError("Error en guardar.");
		}
	}
		
	public static boolean isAdmin() {
		///*
		try {		
			return domini.isAdmin();
		} catch(Exception e) {
			new VistaError(e.getMessage());
			return false;
		}
		//*/
		//return false;
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

    public static ArrayList<String> getProjectesDisponibles() {
        return domini.getAllProjectes();
    }

    public static void obreVistaError(String text) {
        new VistaError(text);
    }

    public static void obreVistaEscollirAtributs() {
        new VistaEscollirAtributs();
    }

    public static ArrayList<String> getTipus() {
        return domini.getTipusAsStrings();
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
    
    public static void obreVistaItems() {
		new VistaError("OBRE VISTA ALL ITEMS");
    }
    
    public static void obreVistaUsuari(String id) {
        //TODO:OMPLIR
        new VistaError("OBRE USUARI CONCRET: " + id);
    }
    
    public static void obreVistaInformacioItem(String id) {
        //TODO:OMPLIR
        new VistaError("OBRE ITEM CONCRET: " + id);
    }
    
    public static void obreVistaTotalUsuari() {
        //TODO:OMPLIR
        new VistaError("OBRE VISTA DEL TOTAL D'USUARIS");
    }
    
    public static void obreVistaModificarAlgorisme() {
        //TODO:OMPLIR
        new VistaError("OBRE VISTA DE MODIFICAR L'ALGORISME");
    }
    
    public static void obreVistaTestAlgorisme() {
        //TODO:OMPLIR
        new VistaError("OBRE VISTA DE TEST DE L'ALGORISME");
    }
	
	public static void valorar(String id_item, String rate)
	{
		try {
			domini.setValoracio(id_item, getId(), rate);
			new VistaError("You are a fucking GOD");
		} catch(Exception e) {
			new VistaError(e.getMessage());
		}
	}
	
    public static void main(String[] args) {
        instancia = new ControladorPresentacio();
        obreVistaInicial();  

		/* CREAR SESSIO "totalll" AMB ITEMS 250
		try {
			domini.loginAdmin();
			domini.createSession("totalll", "/home/jaume/Documents/PROP/projecte/subgrup-prop5-5/data/movies-250/items.csv", "/home/jaume/Documents/PROP/projecte/subgrup-prop5-5/data/movies-250/ratings.db.csv");
			obreVistaEscollirAtributs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
    }

    /* -----------------------------------------------------------------
     * ---Funció treta d'internet, per a canviar la font i el tamany ---
     * -----------------------------------------------------------------
     */
    ///*
    public static void canviarFontUI (javax.swing.plaf.FontUIResource f){
		//Informació extreta de:
		//https://stackoverflow.com/questions/7434845/setting-the-default-font-of-swing-program
		
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get (key);
			if (value instanceof javax.swing.plaf.FontUIResource)
			UIManager.put(key, f);
		}
    }
    //*/
}
