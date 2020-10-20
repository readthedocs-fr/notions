# Introduction à Gradle
## Gradle
Gradle est un [moteur de production](../../fr/MOTEURS_DE_PRODUCTION.md) utilisant le langage Groovy.
## Exemple pratique
### Initialisation
Pour commencer le projet, on se rend dans le dossier créé pour et on ouvre la CLI par défaut dans le dossier du projet :
```shell
cd "C:\Users\Drassero\Desktop\Tutoriel"
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
Le nom du projet (`Tutoriel`) :
```
Project name (default: [Nom du dossier courant, ici 'Tutoriel']):
```
Et finalement le dossier source du projet (`src`) :
```
Source package (default: Tutoriel):
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
> Modification d'un schéma récupéré sur le [site web de Gradle](https://docs.gradle.org/current/samples/sample_building_java_applications.html)

Le fichier `build.gradle` est celui dans lequel on écrit le script Gradle et dans le dossier source (`src`), on trouve les deux modules de l'application (module principal `main` et module de test `test`). Pour plus de détails, faites un tour sur [la documentation de Gradle](https://docs.gradle.org/current/userguide/userguide.html). On ne s'occupera pas ici du test de l'application, ceci est un simple tutoriel d'introduction à Gradle. Voici le code que j'ai placé à l'intérieur de App.java :
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
  // Tâche qui exporte les fichiers sources dans le jar
	doFirst {
		from sourceSets.main.allSource // Exporte le .jar avec les fichiers sources du projet (.class) en plus des fichiers compilés (.java)
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
Notes par rapport au `build.gradle` :
#### Plugins :
Les plugins permettent de rajouter des tâches exécutables et modifiables présentes dans les plugins standards fournis par Gradle ou développées par d'autres personnes.
#### Gestion des dépendances :
La gestion des dépendances permet d'implémenter ces dernières plus facilement et plus rapidement.
#### Exportation des sources :
L'exportation des sources avec la tâche utilisée copie les fichiers sources (.java) au même endroit que les fichiers compilés (.class). Cela permet de conserver les commentaires du développeur par exemple (pour la documentation).

Pour construire le .jar de l'application il suffit de taper la commande suivante :
```shell
gradle build
```
Le .jar sera généré dans le dossier `build/libs`.
Et pour lancer l'application il suffira d'exécuter la tâche fournie par le plugin `application` :
```shell
# La commande va d'abord construire l'application avec la tâche build
gradle run
```
#### Génération de la documentation :
Pour une application Java, si vous voulez générer la JavaDoc du projet, il suffit d'inclure le plugin `java` et d'exécuter la commande
```shell
gradle javadoc
```
