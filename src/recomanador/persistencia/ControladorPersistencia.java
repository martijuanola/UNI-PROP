package src.recomanador.persistencia;

import java.io.File;

import java.util.ArrayList;

public class ControladorPersistencia {
    File carpeta;
    File f;
    
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
	
	//implementació 1
	/**
     * @return Returns an array of arrays of the values. Each array of arrays(line)
     * contains an array of strings (columns). 
     * The first line corresponds to the header of the file, where each
     * column its identifier. 
     * The rest of the lines contain the values read. <p>
     * If an error has occurred, the null pointer will be returned instead.
     */
	public ArrayList<ArrayList<String>> carregarRecomanacions()
	{
		return cl.carregarRecomanacions(carpeta);
	}
	
	//implementació 2
	/**
     * @param A This is an array of arrays of the values. Each array of arrays(line)
     * contains an array of strings (columns).
     * @return Returns true if the file has been read correctly (which means
     * that it existed, was not empty and the read commands have executed
     * correctly). The values read will be stored on A, being the first line
     * the one that contains all the column identifiers.
     * If an error has occurred, it will return false instad, and the
     * null pointer will be set to A.
     */
	public boolean carregarRecomanacions(ArrayList<ArrayList<String>> A)
	{
		A = cl.carregarRecomanacions(carpeta);
		return A != null;
	}
}
