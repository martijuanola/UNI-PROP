# ---------- RUN & COMPILE ---------- #

compile:
	@find ./src/ -name "*.java" > classes.txt
	@javac -g -cp .:/usr/share/java/junit4.jar @classes.txt -d bin
	@rm classes.txt

run:
	make compile
	java -cp bin:. src.recomanador.presentacio.ControladorPresentacio


# ---------- JAVADOC DOCUMENTATION ---------- #

javadoc:
	javadoc -d ./doc/javadoc -subpackages src.recomanador.Utils:src.recomanador.domini:src.recomanador.persistencia -private


# ---------- TESTS ---------- #

junit:
	make compile
	@read -p "Enter the name of class: " name; \
	java -cp bin:.:/usr/share/java/junit4.jar org.junit.runner.JUnitCore src.junits.Test$$name


junitCL:
	make compile
	java -cp bin:.:/usr/share/java/junit4.jar org.junit.runner.JUnitCore src.junits.TestControladorSave

junitCS:
	make compile
	java -cp bin:.:/usr/share/java/junit4.jar org.junit.runner.JUnitCore src.junits.TestControladorLoad

junitRecomanacio:
	make compile
	java -cp bin:.:/usr/share/java/junit4.jar org.junit.runner.JUnitCore src.junits.TestRecomanacio


# ---------- CLEAN ---------- #

clean:
	rm -Rf bin/*

