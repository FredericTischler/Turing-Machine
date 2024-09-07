# Projet annuel de 3eme annee d'informatique.

---

## Sommaire

- [Projet annuel de 3eme annee d'informatique.](#projet-annuel-de-3eme-annee-dinformatique)
  - [Sommaire](#sommaire)
  - [Contexte](#contexte)
  - [Qu'es-ce qu'une machine de turing ?](#ques-ce-quune-machine-de-turing-)
  - [Images du projet](#images-du-projet)
  - [Manuel d'utilisation](#manuel-dutilisation)
    - [Barre de menu](#barre-de-menu)
    - [Configuration des symboles et des etats](#configuration-des-symboles-et-des-etats)
    - [Configuration de la table de transition](#configuration-de-la-table-de-transition)
    - [Configuration du ruban initial](#configuration-du-ruban-initial)
    - [Execution](#execution)
  - [Conclusion](#conclusion)

<!--
1. [Contexte](#contexte)
2. [Qu'es-ce qu'une machine de turing ?](#ques-ce-quune-machine-de-turing)
3. Images du projet
4. Manuel d'utilisation
   1. Barre de menu
      1. Gestion de fichiers
   2. Configuration des symboles et des etats
   3. Configuration de la table de transition
   4. Configuration du ruban initial
   5. Execution
5. Conclusion
-->

---

## Contexte

Dans le cadre de notre troisieme annee de licence d'informatique, l'UFR de sciences et techniques de Saint Etienne du Rouvray nous a demande de realiser un projet de developpement informatique tout au long de l'annee.

Nous evidement avons choisi le projet sur la machine de turing, comme le titre l'indique.

---

## Qu'es-ce qu'une machine de turing ?

Une machine de Turing est un concept théorique inventé par le mathématicien Alan Turing en 1936. Elle est souvent utilisée pour comprendre et étudier les capacités et les limites des algorithmes et du calcul en général.

Pour l'expliquer de manière simple, imaginez une machine de Turing comme une bande infinie divisée en cases, sur laquelle on peut lire, écrire ou effacer des symboles (par exemple, des 0 et des 1). La machine possède une tête de lecture/écriture qui se déplace sur cette bande. Cette tête suit des règles précises définies à l'avance, en fonction du symbole lu et de l'état interne de la machine.

Le processus se déroule en plusieurs étapes :

La tête lit le symbole sur la case où elle se trouve.
En fonction de l'état interne de la machine et du symbole lu, la machine détermine la nouvelle action à effectuer (écrire un nouveau symbole, effacer un symbole, déplacer la tête à gauche ou à droite, ou changer d'état interne).
La machine effectue l'action déterminée et passe à l'étape suivante.
Cela se répète jusqu'à ce que la machine atteigne un état de "fin" prévu par les règles. La machine de Turing est capable de résoudre n'importe quel problème calculable, à condition qu'il existe un algorithme pour le résoudre et qu'on lui donne suffisamment de temps et de ressources.

---

## Images du projet

---

## Manuel d'utilisation

### Barre de menu

La barre de menu permet de manipuler des fichiers.

![representation](./readme/menu_bar.jpg)

1. [x] Abandon du fichier courrant pour pouvoir travailler sur un fichier vierge.
1. [x] Essaie d'ouvrir un fichier selectionne par l'utilisateur
2. [x] Enregistre le travail fait a la destination choisie
3. [ ] Enregistrer sous n'est pas implemente

### Configuration des symboles et des etats

![representation](./readme/sym_sta_1.jpg)

Les zones (1.) et (2.) permettent d'editer et de visionner respectivement les symboles et les etats de la machine.

Par la suite, le terme **element** representera un etat comme un symbole.

![representation](./readme/sym_sta_2.jpg)

Pour ajouter un element, la procedure est la meme: 
1. Il faut ecrire le nom de l'element dans l'encadre (1.)
2. Cliquer sur le bouton "ajouter" (2.)
3. Profiter de son nouvel element ! :-)

Pour retirer un element, la procedure est la meme: 
1. Il faut ecrire le nom de l'element dans l'encadre (1.)
2. Cliquer sur le bouton "retirer" (3.)
3. Regreter son nouvel element ! :-(

### Configuration de la table de transition

la table de transition se lit comme suit:

- L'axe horizontal represente les etats
- L'axe vertical represente les symboles
- Les cases representent le resultat transitions

Pour tout couple de symbole / etat, une transition est forcement definie. Par defaut, la transition sera:

```(s, e) -> (s, e, stop)```

Si on veut que notre machine puisse faire des choses, il faut modifier ces transitions.

Pour cela, il faut cliquer sur la transition qui nous interesse (1.) puis la modifier avec l'editeur (2.).

### Configuration du ruban initial


### Execution

---

## Conclusion