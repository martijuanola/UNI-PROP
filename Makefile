# ---------- RUN & COMPILE ---------- #

compile:
	@find ./src/ -name "*.java" > classes.txt
	@javac -g -cp .:./src/junits/junit.jar @classes.txt -d bin
	@rm classes.txt

run:
	make compile
	java -cp bin:. src.recomanador.presentacio.ControladorPresentacio


# ---------- JAVADOC DOCUMENTATION ---------- #

javadoc:
	#javadoc -d ./doc/javadoc src -subpackages src.recomanador.Utils:src.recomanador.domini:src.recomanador.persistencia -private
	javadoc -d ./doc/javadoc -subpackages src.recomanador.Utils:src.recomanador.domini:src.recomanador.persistencia -private


# ---------- TESTS ---------- #

junit:
	make compile
	@read -p "Enter the name of class: " name; \
	java -cp bin:.:/src/junits/junit.jar src.junits.Test$$name # org.junit.runner.JUnitCore 

junitCL:
	make compile
	java -cp bin:.:/src/junits/junit.jar org.junit.runner.JUnitCore src.junits.TestControladorLoad 


# ---------- CLEAN ---------- #

clean:
	rm -Rf bin/*

