package src.drivers;

import src.recomanador.persistencia.ControladorLoad;

public class DriverControladorLoad {
    
    static private ControladorLoad c;
    static private inout io;
    
    public static void main(String[] args) {
        try{
			c = new ControladorLoad();
			io = new inout();
			
			String s = "Options: \n" +
			"0. exit\n" +
			"1. option 1\n" +
			"2. option 2\n" +
			"3. option 3\n" +
			"4. option 4\n" +
			"5. option 5\n" +
			"6. option 6\n";
			
			io.writeln("Testing class ControladorLoad");
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
