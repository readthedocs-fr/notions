# Scopes

> :information_source: Les exemples de code sont rédigés en Java

Cette fiche est une définition de la notion de `scope`. Elle réapparaît dans de nombreuses notions et il est nécessaire qu'elle soit comprise afin de poursuivre la lecture d'autre fiches.

`Scope` est la traduction anglaise du mot `contexte`. Il définit la portée d'une variable.

Il y en a 2 :

• [Local](#Local)

• [Global](#Global)

## Local

Le `scope` local se réfère à un bloc de code.

Tout ce qui est délimité par 2 accolades (`{}`) est considéré comme un bloc. Cela comprend donc toutes les méthodes (constructeurs y compris car rappelez vous qu'un constructeur n'est qu'une méthode un peu particulière) mais également les structures de code (boucles `for`, boucles `while`, `if` conditionnels, etc...).

Définir une variable dans un bloc, signifie qu'elle n'est accessible que dans le corps de ce bloc et uniquement dans celui-ci.

En dehors, cette variable n'existe pas.

Essayer de l'appeler ou de l'utiliser provoquera une valeur nulle voire une erreur.

```java
public void aRandomMethod() {
    String name = "John";
    System.out.println(name);
    //output : John
}
System.out.println(name); //erreur à la compilation, variable introuvable
```

Une fois le corps de méthode exécuté, cette variable n'existe plus et est détruite.

C'est utile dans le cas où l'on souhaite réutiliser le même nom plusieurs fois dans différentes méthodes.

```java
public void aRandomMethod() {
    String name = "John";
    System.out.println(name); //output : John
}
public void aSecondRandomMethod() {
    String name = "Bob";
    System.out.println(name); //output : Bob
}
System.out.println(name); //erreur à la compilation, variable introuvable
```

*Et dans le cas d'un bloc :*

```java
public void aRandomMethod() {
    String name = "John";
    System.out.println(name); //output : John
    if(5 + 4 == 9) {
        boolean isCorrect = true;
        System.out.println(isCorrect); //output : true
    }else {
        boolean isCorrect = false;
        System.out.println(isCorrect); //output : false
    }
    System.out.println(isCorrect); //erreur à la compilation, variable introuvable 
}
```

## Global

Le `scope` global se réfère, lui, à un milieu plus large : la classe dans laquelle la variable se trouve.

Une variable définit avec un `scope` global sera accessible partout dans la classe y compris dans les méthodes.

Eh oui, le `scope global` englobe le `scope local`.

Vous pouvez donc utiliser une variable `globale` dans une méthode, elle ne sera pas détruite après l'exécution du corps de la méthode.

```java
public class MyClass {
    private int number = 3;
    private int anotherNumber = number + 4;
    public void aMethod() {
        System.out.println(number);
        //output : 3
        System.out.println(anotherNumber);
        //output : 7
        System.out.println(anotherNumber + 7);
        //output : 14
    }
}
```

## This

Concernant le nommage des variables dans les scopes, il est impossible d'avoir deux variables du même nom dans un seul scope :

```java
public class MyClass {
     private int number = 7;
     private int number = 8; //erreur : Variable déjà initialisée
     public void aRandomMethod() {
         String name = "John";
         String name = "Bob"; //erreur : Variable déjà initialisée
     }
}
```

Cependant, il est possible d'avoir 2 variable du même nom dans 2 scopes différents :

```java
public class MyClass {
    private String name = "John";
    public void aRandomMethod() {
        String name = "Bob";
        System.out.println(name);
        //output : Bob
    }
}
```

Comme vous le voyez ici, c'est la variable au scope le plus restreint qui prime. Dans cette méthode, la valeur `John` ne sera jamais utilisée.

Cependant, il existe un moyen de référencer la variable de classe, celle avec un `scope` global.

Ce moyen dépend d'un mot-clé. En Java on utilisera `this`. En PHP ce sera au moyen d'un tableau global (`$GLOBALS`) etc...

### Java

En le plaçant devant la variable, on référence la variable ayant le `scope` le plus général.

Il devient donc possible de faire ça :

```java
public class MyClass {
    private String message = "Hello ";
    public void aRandomMethod() {
        String message = "World";
        System.out.println(this.message + message);
        //output : Hello World
    }
}
```

## Conclusion

Voilà qui conclut cette fiche sur le concept de `scope`.

Globalement, retenez que :

Il y a **2 scopes** :

- Local :

  - Défini au sein d'un **bloc** (méthode, `if`, `for`, etc ...)
  - Variables **temporaires**, **détruites** une fois le **bloc passé**

- Global :

  - Défini pour **toute la classe**
  - Accessible **localement** via un **mot-clé** (`this`), un tableau etc...
