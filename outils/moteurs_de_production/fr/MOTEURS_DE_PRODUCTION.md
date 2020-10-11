# Les moteurs de production
> Écrit par [Drassero](https://github.com/Drassero) \

Cette fiche a pour but de présenter les moteurs de production et leur utilité.
## Explication
**Définition :**
> Un **moteur de production** est un logiciel dont la fonction principale consiste à automatiser (ordonnancer et piloter) l'ensemble des actions ([préprocessing](https://fr.wikipedia.org/wiki/Pr%C3%A9processeur "Préprocesseur"), [compilation](https://fr.wikipedia.org/wiki/Compilation_(informatique) "Compilation (informatique)"), [éditions des liens](https://fr.wikipedia.org/wiki/%C3%89diteur_de_liens "Éditeur de liens"), etc.) contribuant, à partir de données sources, à la production d'un ensemble logiciel opérationnel.
*[Wikipédia](https://fr.wikipedia.org/wiki/Moteur_de_production)*

Concrètement un *moteur de production*, ou *build tool*,  permet d'automatiser le lancement et la production d'une application et ses sous étapes (compilation, implémentation des dépendances...) via un script pré-écrit. L'utilité d'un tel outil est de faciliter le rendu final d'un logiciel en évitant toutes les actions redondantes à faire à chaque exécution ou production au développeur et donc de lui simplifier le développement de son programme.
## Exemples d'automatisations pouvant être implémentées
### Plugins :
Beaucoup de Build Tools permettent d'ajouter des plugins qui facilitent le développement en ajoutant beaucoup de tâches pratiques à l'outil.
#### Exemples avec :
- [Gradle](../tutoriels/fr/INTRODUCTION_GRADLE.md#Plugins-:)
### Gestion des dépendances :
La gestion des dépendances sur certains outils permet de construire une application en implémentant ses dépendances (e.g. librairies) grâce à une syntaxe assez simple à utiliser, par exemple en spécifiant simplement l'identifiant du groupe proposant la dépendance, le nom de la dépendance à utiliser et sa version. Cela permet d'éviter de se compliquer la tâche lorsqu'on travaille avec différentes librairies en nous évitant de devoir les télécharger manuellement, les importer lors du lancement ou la compilation, mettre à jour la version, les ajouter à l'IDE si on utilise un IDE...
#### Exemple avec :
- [Gradle](../tutoriels/fr/INTRODUCTION_GRADLE.md#Gestion-des-dépendances-:)
### Génération de la documentation
La génération de la documentation a pour but de produire la documentation de l'application si c'est une librairie par exemple (*JavaDoc* pour Java, *ScalaDoc* pour Scala...).
#### Exemple avec :
- [Gradle](../tutoriels/fr/INTRODUCTION_GRADLE.md#Génération-de-la-documentation-:)
### Exportation des sources
L'exportation des sources est la tâche qui crée les sources d'un projet (pour pouvoir donner les fichiers sources intacts avec leurs commentaires par exemple).
#### Exemple avec :
- [Gradle](../tutoriels/fr/INTRODUCTION_GRADLE.md#Exportation-des-sources-:)
## Conclusion
Les *moteurs de production*, ou *build tools* permettent d'automatiser le lancement et la création de son application en actionnant de multiples tâches au passage. Leur utilisation rend la programmation d'un logiciel beaucoup plus simple et rapide.
### Exemples de Build Tools :
Voici quelques Build Tools que vous pouvez :
- [Gradle](https://gradle.org/) utilisant le langage Groovy
- [Maven](http://maven.apache.org/) qui utilise des balises XML
- [Ant](https://ant.apache.org/)
- [Scala oriented Build Tool](https://www.scala-sbt.org/)

Et il y en a tant d'autres...
