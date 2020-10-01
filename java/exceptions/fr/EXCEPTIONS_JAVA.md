# Les exceptions en Java

## Préambule
Il existe deux types d'exceptions :
- Les _checked exceptions_ qui sont des exceptions qui doivent être encadrées d'un `try/catch`.
- Les _unchecked exceptions_ qui sont des exceptions qui n'ont pas besoin d'être déclarées dans le code.

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
Une stacktrace est faite pour comprendre une erreur, elle contient toutes les informations nécessaires à la résolution de cette dernière.
```
java.lang.NullPointerException: Main cannot be null
	at ga.enimaloc.B.c(B.java:16)
	at ga.enimaloc.B.b(B.java:24)
	at ga.enimaloc.A.a(A.java:35)
	at ga.enimaloc.Main.main(Main.java:7)
```
Composition de la stacktrace :<br>
- `java.lang.NullPointerException` est la classe qui correspond à l'erreur, elle permet de déterminer le nom de l'exception et de savoir de quelle librairie elle provient.
- `Main cannot be null` est le message fourni lors de l'invocation. Il précise souvent la raison pour laquelle l'exception a été invoquée.
- Le reste en dessous 
```
	at ga.enimaloc.B.c(B.java:16)
	at ga.enimaloc.B.b(B.java:24)
	at ga.enimaloc.A.a(A.java:35)
	at ga.enimaloc.Main.main(Main.java:7)
```
indique l'acheminement de l'exception commençant de bas en haut (le haut étant la méthode contenant la **première** instruction ayant causé l'erreur), chaque ligne se compose de la classe (et de son package), de la méthode, du fichier source, et de la ligne de l'invocation.<br>
:warning: Attention : cet acheminement ne contient pas forcément que vos classes, veuillez bien chercher les lignes concernées par l'erreur, de haut en bas, lisez chaque ligne et arrêtez-vous à la dernière concernant votre code.


Maintenant, si on essaie de transposer en français, cela donnerait :<br>
`NullPointerException` faisant partie du package `java.lang` a été invoquée à la ligne `16` du fichier `B.java` présent dans le package `ga.enimaloc` en précisant `Main cannot be null`.<br>
<br>

### Correction
#### Première cause : le code
Dans ce cas-ci, vous devez comprendre dans quelle partie et quand est-ce que l'erreur est déclenchée en suivant votre code et en déboggant si besoin.<br>

Exemple :
```java
public class Main {

    private static List<Object> list = null;

    public static void main(String[] args) {
        list.add(new Object());
    }
    
}
```
À la ligne `list.add(new Object());`, vous obtiendrez une NullPointerException car `list` est `null`. Pour corriger cela, il faudrait affecter une valeur à la liste via `list = new ArrayList();` ou directement à l'initialisation de cette dernière comme ceci : `private static List<Object> list = new ArrayList();`.
#### Deuxième cause : un choix de l'utilisateur
Ici, vous devrez passer par un `try/catch` ou contrôler la valeur entrée pour éviter que l'utilisateur ne provoque une erreur.<br>
Exemple :
```java
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrer un nombre :");
        int i = scanner.nextInt();
    }
    
}
```
Ici, tout se passe bien si l'utilisateur entre un entier, mais si l'utilisateur entre quelque chose d'autre comme un mot, l'exception `NumberFormatException` sera lancée. Afin de corriger cela, nous devrions faire :

```java
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrer un nombre :");
        try {
            int i = scanner.nextInt();
        } catch (NumberFormatException ignored) {
            System.out.println("Le nombre que vous avez entré n'est pas valide");
        }
    }
    
}
```
#### Troisième cause : l'environnement dans lequel l'application est lancée
Comme pour la deuxième cause, vous devrez passer par un `try/catch` ou faire une vérification.
Exemple :
```java
public class Main {

    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.google.com/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            int ch;
            StringBuffer buffer = new StringBuffer();
            while((ch = inputStream.read()) != -1) {
                buffer.append((char) ch);
            }
            String content = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
```
Ceci lancerait l'exception `java.net.UnknownHostException` si la machine n'a aucun accès à internet, pour éviter cela, il faudrait vérifier que le client est bien connecté en ajoutant un `catch` de `java.net.UnknownHostException` :
```java
public class Main {

    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.google.com/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            int ch;
            StringBuffer buffer = new StringBuffer();
            while((ch = inputStream.read()) != -1) {
                buffer.append((char) ch);
            }
            String content = buffer.toString();
        } catch (UnknownHostException e) {
            System.out.println("Aucune connexion n'est détectée !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
```
## Quelques erreurs courantes
### NullPointerException :
Une [NullPointerException](https://docs.oracle.com/javase/8/docs/api/java/lang/NullPointerException.html) (aussi connue par l'abréviation NPE) est une exception héritant de la classe [RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html), c'est donc une _unchecked exception_.<br>
<br>
Elle est invoquée quand le programme essaie d'accéder à une valeur `null`, cela inclut :
- L'invocation de la méthode d'instance d'un objet `null`.
- La récupération/modification d'un élément dans un tableau défini à `null`.
- Invoquer une exception qui est `null`.

### IndexOutOfBoundException :
Une [IndexOutOfBoundException](https://docs.oracle.com/javase/8/docs/api/java/lang/IndexOutOfBoundException.html) (ou sous forme abrégée IOOB) est une exception héritant de la classe [RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html), c'est donc une _unchecked exception_.<br>
<br>
Elle est invoquée quand le programme essaie d'accéder à un élément avec un index invalide (inférieur à 0 ou supérieur à la limite de l'objet).

### StackOverflowError :
Une [StackOverflowError](https://docs.oracle.com/javase/8/docs/api/java/lang/StackOverflowError.html) est une erreur héritant de la classe [Error](https://docs.oracle.com/javase/8/docs/api/java/lang/Error.html), c'est donc une erreur grave.<br>
<br>
Elle est invoquée quand le programme appelle, dans une méthode, cette même méthode sans sortie possible, ce qui causerait une boucle infinie.
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
