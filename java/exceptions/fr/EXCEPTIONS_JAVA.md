# Les exceptions en Java

## Préambule
Il existe deux types d'exceptions :
- Les _checked exceptions_ sont des exceptions qui doivent être encadrées d'un `try/catch`.
- Les _unchecked exceptions_ sont des exceptions qui n'ont pas besoin d'être déclarées dans le code.

### Checked Exception
#### Error
La classe [Error](https://docs.oracle.com/javase/8/docs/api/java/lang/Error.html) est invoquée lors d'une erreur grave intervenue dans la JVM, elle stoppe immédiatement le programme.

#### Exception
La classe [Exception](https://docs.oracle.com/javase/8/docs/api/java/lang/Exception.html) est invoquée lors d'une erreur moins grave, elle peut stopper le programme. <br>

### Unchecked Exception
#### RuntimeException
La classe [RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html) hérite de la classe [Exception](https://docs.oracle.com/javase/8/docs/api/java/lang/Exception.html). <br>
Toutes les classes héritant de la classe [RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html) sont des exceptions de type _unchecked_.

## Méthode :
### Compréhension :
Une stacktrace est faite pour comprendre une erreur. Elle contient toutes les informations nécessaires.
```
java.lang.Exception: Template exception
	at ga.enimaloc.Main.main(Main.java:7)
```
Ici, vous avez une stacktrace qui ne sera jamais invoquée, mais elle servira d'exemple.<br>
Composition de la stacktrace :<br>
- `java.lang.Exception` est le chemin de l'exception, il permet de déterminer le nom de l'exception et de savoir de quelle librairie elle provient.
- `Template exception` est le message fourni lors de l'invocation. Il précise souvent la raison pour laquelle l'exception a été invoquée.
- Le reste en dessous `at ga.enimaloc.Main.main(Main.java:7)` est le chemin de l'exception : `ga.enimaloc` est le package de la classe, `Main` est la classe, `main` la méthode, `Main.java` le fichier source, et `7` est la ligne de l'invocation.

Maintenant, si on essaie de transposer en français, cela donnerait :<br>
`Exception` faisant partie du package `java.lang` a été invoqué à la ligne `7` de la classe `Main` du package `ga.enimaloc` avec en précision `Template exception`.<br>
<br>

### Correction
#### Premiere cause : le code
Dans ce cas-ci, vous devez regarder dans quel cas l'erreur est déclenchée et voir suivant votre code, en déboggant si besoin.
#### Deuxième cause : un choix de l'utilisateur
Ici, vous devrez passer par un `try/catch` ou faire une vérification pour éviter que l'utilisateur provoque l'erreur.
#### Troisième cause : l'environnement dans lequel l'application est lancée
Comme la deuxième cause, vous devrez passer par un `try/catch` ou faire une vérification.

## Quelques erreurs courantes
### NullPointerException :
Une [NullPointerException](https://docs.oracle.com/javase/8/docs/api/java/lang/NullPointerException.html) (ou sous forme réduite NPE) est une exception héritant de la classe [RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html), c'est donc une _unchecked exception_.<br>
<br>
Elle est invoquée quand le programme essaye d'accéder à une valeur `null` ; cela inclut :
- L'invocation de la méthode d'instance d'un objet `null`.
- La récupération/modification d'un élément dans un tableau défini à `null`.
- Invoquer une exception qui est `null`.

### IndexOutOfBoundException :
Une [IndexOutOfBoundException](https://docs.oracle.com/javase/8/docs/api/java/lang/IndexOutOfBoundException.html) (ou sous forme réduite IOOB) est une exception héritant de la classe [RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html), c'est donc une _unchecked exception_.<br>
<br>
Elle est invoquée quand le programme essaie d'accéder à un élément avec un index invalide (inférieur à 0 ou supérieur à la limite de l'objet).

### StackOverflowError :
Une [StackOverflowError](https://docs.oracle.com/javase/8/docs/api/java/lang/StackOverflowError.html) est une erreur héritant de la classe [Error](https://docs.oracle.com/javase/8/docs/api/java/lang/Error.html), c'est donc une erreur grave.<br>
<br>
Elle est invoquée quand le programme invoque dans une méthode cette même méthode sans sortie de méthode possible, ce qui causerait une boucle infinie.
```java
public class Main {

    public static void main(String[] args) {
        int incrementedNumber = increment(0);
        System.out.println(incrementedNumber);
    }

    public int increment(int i) {
        return increment(i + 1);
    }
}
```
Ce code provoquerait une StackOverflowError sur la ligne `int incrementedNumber = increment(0);`

### NoClassDefFoundError :
Une [NoClassDefFoundError](https://docs.oracle.com/javase/8/docs/api/java/lang/NoClassDefFoundError.html) est une erreur héritant de la classe [Error](https://docs.oracle.com/javase/8/docs/api/java/lang/Error.html), c'est donc une erreur grave.<br>
<br>
Elle est invoquée quand le programme essaie de charger une classe inexistante.
