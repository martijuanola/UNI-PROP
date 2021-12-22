# ---------- RUN & COMPILE ---------- #

compile:
	@find ../FONTS/recomanador/ -name "*.java" > classes.txt
	@find . -name "*.java" >> classes.txt
	@javac -g -cp .:/usr/share/java/junit4.jar @classes.txt -d bin
	@rm classes.txt

# ---------- TESTS ---------- #

junits:
	make compile
	java -cp bin:.:/usr/share/java/junit4.jar org.junit.runner.JUnitCore src.junits.TestControladorSave
	java -cp bin:.:/usr/share/java/junit4.jar org.junit.runner.JUnitCore src.junits.TestControladorLoad
	java -cp bin:.:/usr/share/java/junit4.jar org.junit.runner.JUnitCore src.junits.TestRecomanacio
	make clean

junitCL:
	make compile
	java -cp bin:.:/usr/share/java/junit4.jar org.junit.runner.JUnitCore src.junits.TestControladorSave
	make clean
	
junitCS:
	make compile
	java -cp bin:.:/usr/share/java/junit4.jar org.junit.runner.JUnitCore src.junits.TestControladorLoad
	make clean
	
junitRecomanacio:
	make compile
	java -cp bin:.:/usr/share/java/junit4.jar org.junit.runner.JUnitCore src.junits.TestRecomanacio
	make clean

# ---------- CLEAN ---------- #

clean:
	rm -Rf bin/*
	rmdir bin

