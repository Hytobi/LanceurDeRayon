# Projet lanceur de rayons

Projet Universitaire 2022-2023

## Utilisation

```
./build.sh
./raytrace.sh nom_fichier.test
```

## Tests disponibles

```
./build.sh
cp -r TESTS/* .
./tests.sh
```

### TODO

- MVC (réorganiser les fichiers)
- Résoudre le probleme des tests 7b

### Problème qui persiste

Problème au niveau de la récursivité du calcul de couleur, si on prend le code héxadecimal de la couleur créé il y a une diff par rapport à l'image test. (rés : 0xXXXXX2, attendu: 0xXXXXX4)

- Accumulation de reflet provoqué par le maxdepth (non résolut)
  - On a multiplié par epsilon partout ou on pouvez.
  - On a ramené entre 0 et 1 la couleur.
