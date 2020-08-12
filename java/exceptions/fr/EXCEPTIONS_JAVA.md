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
java.lang.NullPointerException: Main cannot be null
	at ga.enimaloc.B.c(B.java:16)
	at ga.enimaloc.B.b(B.java:24)
	at ga.enimaloc.A.a(A.java:35)
	at ga.enimaloc.Main.main(Main.java:7)
```
Composition de la stacktrace :<br>
- `java.lang.NullPointerException` est la classe qui correspond à l'erreur, il permet de déterminer le nom de l'exception et de savoir de quelle librairie elle provient.
- `Main cannot be null` est le message fourni lors de l'invocation. Il précise souvent la raison pour laquelle l'exception a été invoquée.
- Le reste en dessous 
```
	at ga.enimaloc.B.c(B.java:16)
	at ga.enimaloc.B.b(B.java:24)
	at ga.enimaloc.A.a(A.java:35)
	at ga.enimaloc.Main.main(Main.java:7)
```
indique l'acheminement de l'exception commençant de bas en haut (l’haut étant la méthode ou a était lancée l'exception), chaque ligne se compose de la classe(et de son package), de la méthode, du fichier source, et de la ligne de l'invocation.<br>
:warning: Attention : cet acheminement contient pas forcément que vos classes, veuillez bien chercher les ligne que concerne l'erreur, de haut en bas, lisez chaque ligne est arrêtez-vous quand la ligne concerne votre code.

Maintenant, si on essaie de transposer en français, cela donnerait :<br>
`NullPointerException` faisant partie du package `java.lang` a été invoqué à la ligne `16` du fichier `B.java` du package `ga.enimaloc` avec en précision `Main cannot be null`.<br>
<br>

### Correction
#### Premiere cause : le code
Dans ce cas-ci, vous devez regarder dans quel cas l'erreur est déclenchée et voir suivant votre code, en déboggant si besoin.<br>
Exemple :
```java
public class Main {

    private static List<Object> list = null;

    public static void main(String[] args) {
        list.add(new Object());
    }
}
```
À la ligne `list.add(new Object());` vous obtiendrais une NullPointerException car `list` est `null`, pour corriger cela il faudrait définir la liste avec `list = new ArrayList();` ou directement a l'initialisation de celle-ci `private static List<Object> list = new ArrayList();`.
#### Deuxième cause : un choix de l'utilisateur
Ici, vous devrez passer par un `try/catch` ou faire une vérification pour éviter que l'utilisateur provoque l'erreur.<br>
Exemple :
```java
public class Main {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Entrer un nombre :");
        int i = Integer.parseInt(br.readLine());
    }
}
```
Ici, tout ce passe bien si l'utilisateur entre un entier, mais si l'utilisateur entre une autre chose qu'un entier — exemple : un String —, vas lancer l'exception `NumberFormatException` pour corriger cela nous devrions faire :
```java
public class Main {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Entrer un nombre :");
        try {
            int i = Integer.parseInt(br.readLine());
        } catch (NumberFormatException ignored) {
            System.out.println("Le nombre que vous avez entrer n'est pas valide");
        }
    }
}
```
#### Troisième cause : l'environnement dans lequel l'application est lancée
Comme la deuxième cause, vous devrez passer par un `try/catch` ou faire une vérification.
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
            StringBuffer buffer =new StringBuffer();
            while((ch = inputStream.read()) != -1){
                buffer.append((char) ch);
            }
            String content = buffer.toString();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
Ceci lancerait l'exception `java.net.UnknownHostException` si la machine n'as aucun accès a internet, pour faire une vérification il faudra ajouter un `catch` de `java.net.UnknownHostException`, c'est-à-dire
```java
public class Main {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.google.com/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            int ch;
            StringBuffer buffer =new StringBuffer();
            while((ch = inputStream.read()) != -1){
                buffer.append((char) ch);
            }
            String content = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            System.out.println("Aucune connection n'est détecté !");
        }
    }
}
```
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
