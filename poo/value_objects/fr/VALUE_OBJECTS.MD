# Value objects

## Qu'est-ce qu'un value object ?

Un *value object* se caractérise par deux caractéristiques principales que nous allons détailler ainsi que leurs conséquences.

## - Ils n'ont pas d'identité

En effet, les *value objects* ne possèdent pas d'identité, car ce n'est pas du tout ce qui nous intéresse dans ces objets, ce qui nous intéresse, c'est les valeurs qu'ils contiennent. 
Pour détailler, deux *value objets* sans identités peuvent, s'ils sont équivalents, être partagés, échangés, remplacés entre eux, etc. sans aucun problème.
Alors que pour des objets classiques ça n'a pas de sens d'avoir plusieurs instances d'un objet avec le même identifiant.
Par exemple, prenons deux objets `Money`, un qui est composé d'un billet de 10€ et un autre qui est composé de deux billets de 5€.
Ces deux *value objects* **sont identiques** car ils ont la **même valeur** bien qu'ils n'aient **pas la même identité**.

## - Ils sont immuables

Pour faciliter leur utilisation, les *value objects* **doivent être immuables**.
Si vous souhaitez changer les valeurs dans le *value object*, il suffit d'en **créer un nouveau**. Bien souvent des méthodes pour faire cela sont présentes dans un *value object*.
Le fait que ces objets soient immuables leur permet d'être totalement **interchangeables** et **réutilisables**.
Par exemple, imaginons un *value objet* `Address` qui représente l'adresse d'une personne : si deux personnes habitent au même endroit, on peut **réutiliser** le même objet, ce qui optimise notre application.

## - Ils existent à travers d'autres objets

Si ces objets ne sont pas des attributs d'un « objet mère » ils n'auraient aucune utilité. 
Si je reprends les exemples d'au-dessus on peut identifier certains « objets mères » potentiels :

- `Money` pourrait être l'attribut d'un portefeuille ou bien d'un compte bancaire ;

- `Address` pourrait être l'attribut d'une livraison ou bien d'une maison.

## Exemple d'implémentation
 
 Voici une petite [implémention](../code) autour d'un système de livraison, qui lie les deux exemples `Money` et `Address`. 
 Ce n'est qu'une base, mais ça permet d'entrevoir ce qu'on peut faire avec les *value objects*.

## Conclusion

Les *value objects* sont des objets immuables, qui existent à travers d'autres objets, n'ayant aucune identité.
