# Make do War Economy
# `make` pra deixar tudo certo, `make run` pra rodar ;]

# Variáveis úteis
pkgName = war.economy.beta
mainClass = $(pkgName).WarEconomyBeta
javaSrc = $(wildcard src/*.java)
soCopia = $(shell ls -b src/*.{fxml,png,css})
BUILDDIR = build

# cria BUILDDIR, se não existir, compila os java e copia o resto
all : pasta java resto

java :
	javac -d $(BUILDDIR) $(javaSrc)

resto :
	cp $(soCopia) $(BUILDDIR)/$(subst .,/,$(pkgName))

pasta :
	@mkdir -p $(BUILDDIR)

# run, pra facilitar a vida
run :
	java -cp $(BUILDDIR) $(mainClass)

# limpa os rolê
clean :
	$(RM) -r $(BUILDDIR)/*
