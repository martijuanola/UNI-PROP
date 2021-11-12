compile:
	find . -name "*.java" > classes.txt
	javac -g -cp :. @classes.txt -d bin
	rm classes.txt

run:
	java -cp bin:. src.Main 
	
javadoc:
	javadoc -d ./doc/javadoc src -subpackages src.recomanador.domini:src.recomanador.persistencia -private

clean:
	rm -Rf bin/*
