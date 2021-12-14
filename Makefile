compile:
	@find ./src/ -name "*.java" > classes.txt
	@javac -g -cp .:./src/junits/junit.jar @classes.txt -d bin
	@rm classes.txt

driver:
	make compile
	@read -p "Enter the name of class: " name; \
	java -cp bin:. src.drivers.Driver$$name

junit:
	make compile
	@read -p "Enter the name of class: " name; \
	java -cp bin:.:/src/junits/junit.jar src.junits.Test$$name # org.junit.runner.JUnitCore 

junitCL:
	make compile
	java -cp bin:.:/src/junits/junit.jar org.junit.runner.JUnitCore src.junits.TestControladorLoad 

run:
	make compile
	java -cp bin:. src.recomanador.presentacio.ControladorPresentacio

ConjuntUsuaris:
	cp ./src/stubs/StubUsuari.txt ./src/recomanador/domini/Usuari.java
	make compile
	cp ./src/recomanador/domini/OriginalClass.Usuari ./src/recomanador/domini/Usuari.java 
	java -cp bin:. src.drivers.DriverConjuntUsuaris

ConjuntRecomanacions:
	cp ./src/stubs/StubUsuari.txt ./src/recomanador/domini/Usuari.java
	make compile
	cp ./src/recomanador/domini/OriginalClass.Usuari ./src/recomanador/domini/Usuari.java 
	java -cp bin:. src.drivers.DriverConjuntRecomanacions

Recomanacio:
	cp ./src/stubs/StubUsuari.txt ./src/recomanador/domini/Usuari.java
	make compile
	cp ./src/recomanador/domini/OriginalClass.Usuari ./src/recomanador/domini/Usuari.java 
	java -cp bin:. src.drivers.DriverRecomanacio

Usuari:
	cp ./src/recomanador/domini/OriginalClass.Usuari ./src/recomanador/domini/Usuari.java 
	make compile
	java -cp bin:. src.drivers.DriverUsuari

Item:
	make compile
	java -cp bin:. src.drivers.DriverItem

ConjuntItems:
	make compile
	java -cp bin:. src.drivers.DriverConjuntItems

ItemValoracioEstimada:
	make compile
	java -cp bin:. src.drivers.DriverItemValoracioEstimada

ControladorDominiAlgorisme:
	make compile
	java -cp bin:. src.drivers.DriverControladorDominiAlgorisme

ControladorPersistencia:
	make compile
	java -cp bin:. src.drivers.DriverControladorPersistencia

ControladorLoad:
	make compile
	java -cp bin:. src.drivers.DriverControladorLoad
	
ControladorSave:
	make compile
	java -cp bin:. src.drivers.DriverControladorSave

StringOperations:
	make compile
	java -cp bin:. src.drivers.DriverStringOperations

UnionIntersection:
	make compile
	java -cp bin:. src.drivers.DriverUnionIntersection

clean:
	rm -Rf bin/*

javadoc:
	#javadoc -d ./doc/javadoc src -subpackages src.recomanador.Utils:src.recomanador.domini:src.recomanador.persistencia -private
	javadoc -d ./doc/javadoc -subpackages src.recomanador.Utils:src.recomanador.domini:src.recomanador.persistencia -private
