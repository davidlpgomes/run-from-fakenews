JFLAGS = -g
JC = javac
C=$(`find . -type f | grep .class`)

.SUFFIXES: .java .class

.java.class:
	@$(JC) $(JFLAGS) $*.java


CLASSES = Main.java 

default: classes
	@java Main

classes: $(CLASSES:.java=.class)

clean:
	@find . -name '*.class' -type f -delete
	@echo "Deletando todos os arquivos .class"
