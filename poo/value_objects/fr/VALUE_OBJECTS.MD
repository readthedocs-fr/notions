# Value objects.

## Qu'est-ce qu'un value object ?

Eh bien il se caractérise par deux caractéristiques principales que nous allons détailler ainsi que leurs conséquences.

## -Ils n'ont pas d'identité.

En effet, les value objects ne possèdent pas d'identité, car ce n'est pas du tout ce qui nous intéresse dans ces objets, ce qui nous intéresse,
c'est les valeurs qu'ils contiennent. 
Par exemple, prenons deux objets `Money`, un qui est composé d'un billet de 10€ et un autre qui est composé de deux billets de 5€.
Eh bien ces deux value objects **sont identiques** car ils ont la **même valeur** bien qu'ils n'aient **pas la même identité**.

## -Ils sont immuables.

Pour faciliter leur utilisation les value objects **doivent être immutable**.
Si vous souhaitez changer les valeurs dans le value object, il suffit d'en **créer un nouveau**. Bien souvent des méthodes pour faire cela sont présentes dans un value object.
Et le fait que ces objets soient immuables permet qu'ils deviennent totalement **interchangeables** et **réutilisables**.
Par exemple imaginons un value objet `Address` qui représente l'adresse d'une personne, et bien si deux personnes habitent au même endroit, on peut **réutiliser** le même objet, ce qui optimise notre application.

## -Ils dépendent d'autres objets.

Si ces objets ne dépendent pas d'un "objet mère" ils n'auraient aucune utilité, si je reprends les exemples d'au-dessus on peut identifier certains "objets mères" potentiels :

-`Money` pourrait dépendre d'un portefeuille ou bien d'un compte bancaire.

-`Address` pourrait dépendre d'une livraison ou bien d'une maison.

## -Exemple d'implémentation
 
 Voici une petite [implémention](../code) autour d'un système de livraison, qui lie les deux exemples `Money` et `Address`, ce n'est qu'une base, mais ça permet d'entrevoir ce qu'on peut faire avec les value objects.

## -Conclusion

Les value objects sont des objets immuables, dépendant d'autres objets, n'ayant aucune identité.
