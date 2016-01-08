# Make do War Economy
# `make` pra deixar tudo certo, `make run` pra rodar ;]

# Variáveis úteis
warPkg = war
mainClass = $(warPkg).WarEconomyBeta
javafxPkg = javafxStuff

# arquivos java mesmo
javaSrc = $(shell ls -b src/{$(warPkg),$(javafxPkg)}/*.java)
# extras do javafx, só copia
javafxStuff = $(shell ls -b src/javafxStuff/*.{fxml,css})
# imagens, só copia também
images = $(shell ls -b src/images/*.png)
# lib do json pra java
jsonLib = $(shell ls -b libs/*json*.jar)

BUILDDIR = build

# cria BUILDDIR, se não existir, compila os java e copia o resto
all : pasta java resto

java :
	javac -d $(BUILDDIR) -cp $(jsonLib) $(javaSrc)

resto :
	cp $(javafxStuff) $(BUILDDIR)/$(subst .,/,$(javafxPkg))
	cp -r src/images $(BUILDDIR)

pasta :
	@mkdir -p $(BUILDDIR)

# run, pra facilitar a vida
run :
	java -cp "$(BUILDDIR):$(jsonLib)" $(mainClass)

# limpa os rolê
clean :
	$(RM) -r $(BUILDDIR)/*
