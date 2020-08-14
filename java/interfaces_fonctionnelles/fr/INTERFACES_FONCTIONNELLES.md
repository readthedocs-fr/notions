# Interfaces fonctionnelles

En Java, les interfaces fonctionnelles sont un ajout majeur disponibles depuis la version `1.8`.
Ce cours discutera de leur définition, de leur syntaxe et de leur utilité, le tout illustré à l'aide d'exemples, puis couvrira les principales interfaces fonctionnelles fournies par la librairie standard.

### Prérequis

- Une relativement bonne connaissance en programmation orientée objet
- Etre familier avec l'utilisation des interfaces, au moins théoriquement, c'est à dire :
  - Comprendre le concept de `méthodes abstraites`
  - Comprendre le concept de `méthodes par défaut`
- Connaître, au moins théoriquement, les `classes anonymes`

## Définition

Une interface fonctionnelle est une `interface` qui ne contient qu'une seule et unique méthode **abstraite**. Le nombre de méthodes possédant une implémentation par défaut (annotées du mot clé `default`) n'importe pas.

Voici quelques exemples d'interfaces :

```java
interface Test1 {

	void doSomething();

	default void doNothing() {
		System.out.println("This method does not do anything");
	}
}

interface Test2 {

	default void foo() {}

	default String getGreeting() { return "Hello!"; }
}

interface Test3 {

	void foo(String someStr);

	void bar(int someInt);

	default void fooThenBar(String someStr, int someInt) {
		foo(someStr);
		bar(someInt);
	}
}
```

Dans ces trois exemples, seule l'interface `Test1` est une interface fonctionnelle, puisqu'elle est la seule à posséder exactement **une** méthode abstraite. Par convention, il est conseillé d'ajouter l'annotation `@FunctionalInterface` au niveau de la déclaration de l'interface, qui permet de vérifier que l'interface est bien fonctionnelle.

## Utilisation

La question qu'on peut se poser, c'est pourquoi dit-on qu'il s'agit d'une nouveauté de Java 8 ? Les interfaces existent depuis bien plus longtemps (depuis les tout débuts du langage en fait), et les interfaces fonctionnelles ne sont qu'un cas particulier des interfaces traditionnelles. Difficile à croire que le seul ajout soit l'annotation `@FunctionalInterface`, qui n'est qu'une vérification optionnelle, au même titre que l'annotation `@Override`. En réalité, Java 8 ajoute une syntaxe plutôt révolutionnaire concernant **l'instanciation de ces interfaces**. Alors évidemment, parler d'instanciation d'une interface, c'est un peu un abus de langage. Une interface, au même titre qu'une classe abstraire, ça ne s'instancie pas, c'est le principe. Pour pouvoir utiliser une interface, il faut l'implémenter, à l'aide d'une autre classe, anonyme ou non. Pour ce cours, `instancier une interface` sera un abus de langage pour parler de l'implémentation d'une interface à l'aide d'une classe anonyme.

Commençons par un exemple. Prenons une interface fonctionnelle `BinaryOperation`, qui ressemble à ceci :

```java
interface BinaryOperation {

	double compute(double a, double b);
}
```

Notre but va être d'instancier un objet qui possédera cette fonction `compute`, pour que l'on puisse effectuer des opérations entre deux nombres, par exemple une **addition**.
En Java 7, le moyen le plus rapide de faire cela est le suivant :

```java
BinaryOperation sum = new BinaryOperation() {

	@Override
	public double compute(double a, double b) {
		return a + b;
	}
};

System.out.println(sum.compute(4.0, 7.0)); // Output: 11.0
```

Ainsi, on a réussi à "stocker" une méthode qui prend deux doubles en paramètre et renvoie un autre double sous forme de variable, ce qui est plutôt chouette. Si vous n'avez jamais fait ce genre de choses, vous allez peut-être vous dire "super, mais à quoi ça sert ?". Voyons un exemple. Imaginez que vous possédez une `List<String>` et que vous souhaitez donner la possibilité à l'utilisateur d'effectuer une action pour chaque `String` présent dans la liste. Vous penserez problablement à faire quelque chose comme cela:

```java
class MyClass {

	private List<String> myList;

	public MyClass() {
		myList = new ArrayList<>();
		myList.add("Hello");
		myList.add("Good morning");
		myList.add("Have a good one");
	}

	public List<String> getList() {
		return myList;
	}
}
```

Puis comme vous donnez l'accès à la liste à votre utilisateur, il peut s'occuper lui-même de faire une boucle for et d'effectuer l'action qu'il veut pour chaque `String`, comme afficher tous les éléments de la liste par exemple :

```java
MyClass test = new MyClass();
for(String str : test.getList()) {
	System.out.println(str);
}
```

Cependant, ce code pose un énorme problème. Vous souhaitez donner accès à la liste, mais uniquement pour que l'utilisateur effectue une action sur chacun des éléments. Et qui vous garantit que c'est tout ce que l'utilisateur compte faire ? Il pourrait très bien faire :

```java
MyClass test = new MyClass();
test.getList().add("haha you're stupid and ugly");
```

Et dans ce cas, votre liste de salutations aura été modifiée depuis l'extérieur, ce qui est très problématique. Alors évidemment, dans cet exemple on fait passer l'utilisateur pour quelqu'un de mal intentionné qui chercherait à casser le programme (ce qui n'aurait pas beaucoup de sens), mais en réalité cela s'applique pour tous les utilisateurs : une bonne `API` ne devrait jamais laisser la possibilité à un utilisateur de faire quelque chose qu'il ne devrait jamais faire. Car même un utilisateur bien intentionné risque de trouver de mauvaises solutions à ses problèmes, si l'API lui donne accès à tout.

Heureusement, tout problème a une solution, voilà que les interfaces fonctionnelles viennent à notre secours. Enlevons cette atroce méthode `getList()` et remplaçons-la par quelque chose qui donne moins de pouvoir à notre cher utilisateur.

```java
class MyClass {

	private List<String> myList;
	public MyClass() {
		myList = new ArrayList<>();
		myList.add("Hello");
		myList.add("Good morning");
		myList.add("Have a good one");
	}

	public void executeActionForEach(Action myAction) {
		for(String str : myList) {
			myAction.applyActionTo(str);
		}
	}
}
```

Avec une interface fonctionnelle `Action` qui ressemblerait à cela :

```java
interface Action {
	void applyActionTo(String str);
}
```

Ainsi, pour afficher tous les éléments de la liste depuis l'intérieur, il suffit de faire ceci :

```java
MyClass test = new MyClass();
Action printAction = new Action() {
	@Override
	public void applyActionTo(String str) {
		System.out.println(str);
	}
};
test.executeActionForEach(printAction);
```

Et voilà, on a réussi à exécuter une action pour chaque élément de la liste, sans donner accès à cette dernière.

## Et Java 8 dans l'histoire ?

Bon, maintenant qu'on connaît l'utilité des interfaces fonctionnelles, il nous reste toujours à comprendre pourquoi Java 8 favorise leur utilisation. En fait, la nouveauté se trouve au niveau de l'instanciation de nos interfaces. Si je reprends le code précédent, il nous a fallu faire ceci pour `stocker` notre action :

```java
Action printAction = new Action() {
	@Override
	public void applyActionTo(String str) {
		System.out.println(str);
	}
};
```

Et comme on est garanti qu'il n'y a qu'une seule méthode à implémenter, Java 8 nous propose le raccourci suivant :

```java
Action printAction = (str) -> System.out.println(str);
```

Assez magique non ? Cette syntaxe s'appelle une **expression lambda**. Voyons ce qui se passe plus en détails.

- On déclare toujours, comme avant, un objet de type `Action` et on lui donne un nom, `printAction`
- Comme on sait que tout ce qui nous importe, c'est cette méthode `applyActionTo`, on la définit avec un raccourci.
  - Tout d'abord, entre les parenthèses se trouvent les paramètres de la méthode, séparés par des virgules. Ici, il n'y a qu'un seul paramètre, donc pas besoin de virgule (d'ailleurs, un tel cas, les parenthèses peuvent être omises). Notez que le type des paramètres n'est pas explicité, puisqu'il est connu.
  - Ensuite on indique que l'on va passer au **corps** de la méthode avec une flèche (`->`)
  - Finalement, on indique l'action à effectuer. Dans ce cas-ci, il n'y a qu'une instruction à effectuer, à savoir afficher le `String` en question. Il est possible d'avoir plusieurs instructions en les encapsulant à l'aide de `{}`, comme ceci:
    ```java
    Action myAction = (str) -> {
      System.out.println(str);
      System.our.println("Une chaîne de caractères a été affichée à l'aide d'une lambda !");
    }
    ```

En revanche, il est plutôt conseillé dans ce cas-ci d'encapsuler les différentes instructions dans une méthode à part, puis de faire un appel à cette méthode pour n'avoir qu'une seule instruction. Les lambdas à plusieurs expressions s'appellent des **méthodes anonymes**, et on essaie conventionnellement de les éviter.

## Toujours plus court, toujours plus loin

Dans certains cas, il est possible de réduire cette syntaxe encore plus qu'elle ne l'est déjà. Il s'agit du cas où l'on souhaite appeler une méthode comme unique instruction de notre lambda, et que la signature de cette méthode est la même que la signature de la méthode à implémenter. Ce cas particulier s'appelle **method referencing**. Comme le cas au-dessus par exemple :

```java
Action printAction = (str) -> System.out.println(str);
```

En effet, on demande une méthode qui prend en paramètre un `String` et qui ne renvoie rien (`void`). C'est la définition même de notre méthode `applyActionTo`. Et par chance, la fonction `println` a la même signature ! Elle demande un `String`, et ne renvoie rien. On peut donc indiquer que notre action correspond à effectuer `println`. Juste pour l'exemple, nous allons créer une méthode `static` qui s'occupe d'afficher notre `String`, afin de couvrir tous les cas possibles (d'autres exemples hors d'un contexte statique suivront) :

```java
class MyUtilClass {
	static void printString(String str) {
		System.out.println(str);
	}
}
```

Nous pouvons désormais assigner cette méthode comme valeur pour notre interface fonctionnelle, comme

```java
Action printAction = MyUtilClass::printString;
```

Lorsqu'il ne s'agit pas d'une méthode `static` comme println, il y a trois options.
Prenons une classe comme ceci :

```java
class SomeClass {
	void someMethod(SomeClass other) {
            //...
        }
        
        SomeClass(String name) {
            System.out.println(String.format("My name is %s", name));
        }
}
```

1. Référencer la méthode d'un objet, par exemple : `myObject::someMethod;`. Cela sera utile si on demande une méthode qui renvoie `void` et qui demande **un** (et pas deux, attention !) objet de type `SomeClass`. Pourquoi pas deux ? Simplement parce qu'on indique déjà de quel objet on va appeler la méthode `someMethod`. Il suffit donc d'avoir ce paramètre `other`.
2. Utiliser une référence static, par exemple: `SomeClass::someMethod`. Cette fois-ci, cela fonctionne si on demande une méthode qui renvoie `void` et qui demande **deux** objets de type SomeClass. Pourquoi deux ? Car cette fois, on n'indique pas depuis quel objet on va appeler `someMethod`, alors il faut aussi en fournir un. Sans oublier le paramètre `other`, il faut donc deux objets et non un seul.
3. Référencer un constructeur, par exemple : `SomeClass::new`. Nous pouvons ainsi créer une nouvelle instance de la classe `SomeClass`. C'est utile dans le cas suivant par exemple : 
```java
List<String> names = new ArrayList<>(Arrays.asList(
                         "Paul",
                         "Marc",
                         "Jean"
                     ));
names.foreach(SomeClass::new);
```
Cette possibilité nous fait économiser quelques caractères, sans ça nous aurions eu `names.foreach(name -> new SomeClass(name));`. Le compilateur est capable d'inférer (comprendre implicitement) qu'il faut passer la variable locale `name` en paramètre du constructeur.

## Librairie standard

Il serait impossible de couvrir l'entièreté des nouveaux systèmes qui utilisent des [interfaces fonctionnelles](https://docs.oracle.com/javase/8/docs/api/index.html?java/util/function/package-summary.html) depuis Java 8, c'est pourquoi je me focaliserai uniquement sur la description des principales interfaces elles-mêmes.

- `Supplier<T>`
  Permet de fournir une instance de type `T` via une méthode `get()`, ne demandant aucun paramètre
- `Runnable`
  Permet d'effectuer une action (une série d'instructions ne renvoyant rien), via une méthode `run()`
- `Consumer<T>`
  Permet d'effectuer une action (une série d'instructions ne renvoyant rien) en fonction d'un paramètre, via une méthode `apply(T)`. Typiquement dans notre exemple, l'interface `Action` pourrait être remplacée par un `Consumer<String>`
- `Function<T, R>`
  Agit comme une fonction qui prend un paramètre de type `T` et qui renvoie un objet de type `R`
- `Predicate<T>`
  Simple raccourci pour `Function<T, Boolean>`.
- `BiConsumer<T, U>`, `BiFunction<T, U, R>`, `BiPredicate<T, U>`
  Identiques aux précédentes interface, seulement prenant deux paramètres au lieu d'un seul

## Conclusion

Grâce aux raccourcis syntaxiques comme les lambdas et les références de méthodes, Java 8 favorise l'utilisation des interfaces fonctionnelles, permettant de stocker, en quelques sortes, une méthode sous forme de variable. En revanche, on est encore extrêmement loin de pouvoir dire que Java est un langage supportant le paradigme fonctionnel (et il ne le saura très probablement jamais), même si cet ajout est très pratique et utilisé.
