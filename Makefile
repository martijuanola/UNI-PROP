compile:
	find . -name "*.java" > classes.txt
	javac -g -cp :. @classes.txt -d bin
	rm classes.txt

run:
	java -cp bin:. src.Main 

clean:
	rm -Rf bin/*
