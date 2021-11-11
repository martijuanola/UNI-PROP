package src.recomanador.persistencia;

import java.io.File;

public class ControladorPersistencia {
    File carpeta;
    File f;
    
    public ControladorPersistencia()
    {
		carpeta = null;
		f = null;
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
}
