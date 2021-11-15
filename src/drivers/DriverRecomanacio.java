package src.drivers;

import src.recomanador.domini.Recomanacio;

public class DriverRecomanacio {
    
    static private Recomanacio c;
    static private inout io;
    
    public static void main(String[] args) {
        try{
			io = new inout();
			
			String s = "Options: \n" +
			"0. exit\n" +
			"\n - Constructors - \n" +
			"1. Recomacio(Usuari, Item)\n" +
			"2. Recomanacio(Usuari, Item)\n" +
			"\n - Setters - \n" +
			"3. void setVal(float)\n" +
			"\n - Getters - \n" +
			"4. Usuari getUsuari()\n" +
			"5. Item getItem()\n" +
			"6. float getVal()\n" +
			"7. boolean recomanacioValorada()\n" +
			"8. boolean checkIds(int, int)\n" +
			"9. boolean checkKeys(int, int)\n" +
			"10. int compareTo(Recomanacio)\n";
			
			io.writeln("Testing class Recomanacio");
			int x;
			do {
				io.writeln("\n--------------------------------------------------\n");
				io.writeln("Enter the number of the function you want to test. \n");
				io.writeln(s);
				io.write("Option: ");
				
				x = io.readint();
				
				switch(x)
				{
					case 1:
						mostra_1();
						break;
					case 2:
						mostra_2();
						break;
					case 3:
						mostra_3();
						break;
					case 4:
						mostra_4();
						break;
					case 5:
						mostra_5();
						break;
					case 6:
						mostra_6();
						break;
					default:
				}
				
			} while (x != 0);
			
			io.writeln("Test ended");
			
		}catch(Exception e){
			System.out.println("An error has occurred");
		}
    }
    
    static private void mostra_1() throws Exception {
		io.writeln("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
    static private void mostra_2() throws Exception {
		io.writeln("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
    static private void mostra_3() throws Exception {
		io.writeln("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
    static private void mostra_4() throws Exception {
		io.writeln("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
    static private void mostra_5() throws Exception {
		io.writeln("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
    static private void mostra_6() throws Exception {
		io.writeln("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
}
