# Make do War Economy
# `make` pra deixar tudo certo, `make run` pra rodar ;]

# Variáveis úteis
pkgName = war.economy.beta
mainClass = $(pkgName).WarEconomyBeta

# arquivos java mesmo
javaSrc = $(shell ls -b src{,/javafxStuff}/*.java)
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
	cp $(javafxStuff) $(images) $(BUILDDIR)/$(subst .,/,$(pkgName))

pasta :
	@mkdir -p $(BUILDDIR)

# run, pra facilitar a vida
run :
	java -cp "$(BUILDDIR):$(jsonLib)" $(mainClass)

# limpa os rolê
clean :
	$(RM) -r $(BUILDDIR)/*
