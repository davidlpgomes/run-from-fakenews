JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = Main.java

default: clean classes
	@java Main $(ARGS)

classes: $(CLASSES:.java=.class)

clean:
	@find . -name '*.class' -type f -delete
	@echo "Deletando todos os arquivos .class"
