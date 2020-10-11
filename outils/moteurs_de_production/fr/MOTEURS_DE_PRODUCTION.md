# Les moteurs de production
> Écrit par [Drassero](https://github.com/Drassero) \
> Exemples présentés avec le Build Tool Gradle donc écrits en Groovy

Cette fiche a pour but de présenter les moteurs de production et leur utilité.
## Explication
**Définition :**
> Un **moteur de production** est un logiciel dont la fonction principale consiste à automatiser (ordonnancer et piloter) l'ensemble des actions ([préprocessing](https://fr.wikipedia.org/wiki/Pr%C3%A9processeur "Préprocesseur"), [compilation](https://fr.wikipedia.org/wiki/Compilation_(informatique) "Compilation (informatique)"), [éditions des liens](https://fr.wikipedia.org/wiki/%C3%89diteur_de_liens "Éditeur de liens"), etc.) contribuant, à partir de données sources, à la production d'un ensemble logiciel opérationnel.
*[Wikipédia](https://fr.wikipedia.org/wiki/Moteur_de_production)*

Concrètement un *moteur de production*, ou *build tool*,  permet d'automatiser le lancement et la production d'une application et ses sous étapes (compilation, implémentation des dépendances...) via un script pré-écrit. L'utilité d'un tel outil est de faciliter le rendu final d'un logiciel en évitant toutes les actions redondantes à faire à chaque exécution ou production au développeur et donc de lui simplifier le développement de son programme.
## Exemples d'automatisations pouvant être implémentées
### Plugins :
Beaucoup de Build Tools permettent d'ajouter des plugins qui facilitent le développement en ajoutant beaucoup de tâches pratiques à l'outil.
#### Exemple :
```groovy
plugins {
	id 'java' // Ce plugin ajoute des tâches liées à java permettant par exemple de compiler un .jar
	id 'application' // Celui-ci permet de lancer le programme d'un projet JVM
}
```
### Gestion des dépendances :
La gestion des dépendances sur certains outils permet de construire une application en implémentant ses dépendances (e.g. librairies) grâce à une syntaxe assez simple à utiliser, par exemple en spécifiant simplement l'identifiant du groupe proposant la dépendance, le nom de la dépendance à utiliser et sa version. Cela permet d'éviter de se compliquer la tâche lorsqu'on travaille avec différentes librairies en nous évitant de devoir les télécharger manuellement, les importer lors du lancement ou la compilation, mettre à jour la version, les ajouter à l'IDE si on utilise un IDE...
#### Exemple :
```groovy
dependencies {
	implementation 'com.google.code.gson:gson:2.8.6' // Télécharge la librairie 'Gson', l'importe pour les futures exécutions et l'inclut au classpath de l'IDE (si un IDE est utilisé)
}
```
### Génération de la documentation
La génération de la documentation a pour but de produire la documentation de l'application si c'est une librairie par exemple (*JavaDoc* pour Java, *ScalaDoc* pour Scala...).
#### Exemple :
Plugin qui est requis :
```groovy
plugins {
	id 'java' // Le plugin 'java' implémente la tâche 'javadoc'
}
```
Commande permettant de lancer la génération :
```shell
# Tâche qui génère la JavaDoc du projet Java courant
gradle javadoc
```
### Exportation des sources
L'exportation des sources est la tâche qui crée les sources d'un projet (pour pouvoir donner les fichiers sources intacts avec leurs commentaires par exemple).
#### Exemple :
```groovy
jar {
	doFirst {
		from sourceSets.main.allSource // Exporte le .jar avec les fichiers sources du projet (.class) en plus des fichiers compilés (.java)
	}
}
```
## Conclusion
Les *moteurs de production*, ou *build tools* permettent d'automatiser le lancement et la création de son application en actionnant de multiples tâches au passage. Leur utilisation rend la programmation d'un logiciel beaucoup plus simple et rapide.
### Exemples de Build Tools :
Voici quelques Build Tools que vous pouvez :
- [Gradle](https://gradle.org/) (il est pratique, c'est celui que j'utilise)
- [Maven](http://maven.apache.org/) qui utilise des balises XML
- [Ant](https://ant.apache.org/)
- [Scala oriented Build Tool](https://www.scala-sbt.org/)

Et il y en a tant d'autres...

## Tutoriel Bonus
Voici un petit exemple d'utilisation de Gradle qui, je l'espère, vous convaincra sur l'utilité des moteurs de production si vous ne l'êtes pas déjà.

### Initialisation
Pour commencer le projet, on se rend dans le dossier créé pour et on ouvre la CLI par défaut dans le dossier du projet :
```shell
cd "C:\Users\Drassero\Desktop\Tutoriel Bonus"
```
Puis on initialise le programme avec :
```shell
gradle init
```
On choisit le type de projet (on va choisir `application` ici) :
```
Select type of project to generate:
  1: basic
  2: application
  3: library
  4: Gradle plugin
Enter selection (default: basic) [1..4]
```
Le langage de l'application (`Java`) :
```
Select implementation language:
  1: C++
  2: Groovy
  3: Java
  4: Kotlin
  5: Swift
Enter selection (default: Java) [1..5]
```
Le langage du script de Gradle (`Groovy`) :
```
Select build script DSL:
  1: Groovy
  2: Kotlin
Enter selection (default: Groovy) [1..2]
```
Le framework de test de l'application (`JUnit 4`) :
```
Select test framework:
  1: JUnit 4
  2: TestNG
  3: Spock
  4: JUnit Jupiter
Enter selection (default: JUnit 4) [1..4]
```
Le nom du projet (`Tutoriel Bonus`) :
```
Project name (default: [Nom du dossier courant, ici 'Tutoriel Bonus']):
```
Et finalement le dossier source du projet (`src`) :
```
Source package (default: Tutoriel.Bonus):
```
Voilà, nous avons fini de configurer le projet. Voici son arborescence après création (j'ai rajouté les packages conventionnels de Java) :
```
├── build.gradle
├── gradle    
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── java  
    |   |     └── com
    |   |          └── drassero
    |   |                 └── tutorial
    │   │                         └── App.java
    │   └── resources
    └── test      
        ├── java
        │   └── src
        │        └── AppTest.java
        └── resources
```
> Récupéré sur le site web de Gradle

Le fichier `build.gradle` est celui dans lequel on écrit le script Gradle et dans le dossier source (`src`), on trouve les deux modules de l'application (module principal `main` et module de test `test`). Pour plus détails, faites un tour sur [la documentation de Gradle](https://docs.gradle.org/current/userguide/userguide.html). On ne s'occupera pas ici du test de l'application, ceci est un simple tutoriel d'introduction à Gradle. Voici le code que j'ai placé à l'intérieur de App.java :
```java
package com.drassero.tutorial;

public class App {

    public static void main(String[] args) {
        System.out.println("Hello World !");
    }
    
}
```
Et le fichier `build.gradle` :
```groovy
plugins {
    // Appliquer le plugin java
    id 'java'

    // Appliquer le plugin application qui permet de produire une application
    id 'application'
}

// Tâche fournie par le plugin java permettant de configurer le .jar exporté
jar {
	// Tâche qui permet de configurer le manifeste présent dans le .jar
	manifest {
		// Ajoute l'attribut 'Main-Class' avec la valeur 'com.drassero.tutorial.App' dans le manifeste exporté
		attributes 'Main-Class': 'com.drassero.tutorial.App'
	}
}

// Bloc qui permet d'inclure les dépôts d'hébergement des dépendances
repositories {
    // Dépôt JCenter qui héberge JUnit pour les tests de l'application
    jcenter()
}

dependencies {
    // Dépendance utilisée par l'application
    implementation 'com.google.guava:guava:29.0-jre'

    // Utiliser le framework JUnit pour test
    testImplementation 'junit:junit:4.13'
}

application {
    // Définit la classe principale de l'application à partir du dossier 'java' du module
    mainClassName = 'com.drassero.tutorial.App'
}
```
Pour construire le .jar de l'application il suffit de taper la commande suivante :
```shell
gradle build
```
Le .jar sera généré dans le dossier `build/libs`.
Et pour lancer l'application il suffira d'exécuter la tâche fournie par le plugin `application` :
```
# La commande va d'abord construire l'application avec la tâche build
gradle run
```
Ce petit tutoriel d'introduction était plutôt simple mais j'espère qu'il vous a permis de commencer à utiliser Gradle et qu'il vous a fait vous rendre compte de toutes les possibilités qu'apporte cet outil et qu'apportent les moteurs de production en général.
