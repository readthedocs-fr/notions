# Les principes SOLID

> Écrit par [Antoine Tran](https://github.com/Tran-Antoine) et [Luka Maret](https://github.com/LukaMrt)

## Présentation

Ce cours porte sur les principes SOLID, popularisés par Robert C. Martin dans le livre *Agile Software Development, Principles, Patterns and Practices* écrit en 2002. Ces principes, qui sont au nombre de 5, forment un socle de méthodes, d'habitudes et de pratiques permettant de développer des application **claires**, **fiables**, **maintenables** et **robustes**. Ils s'inscrivent dans la notion de **software craftmanship** qui met en avant des pratiques visant à créer des logiciels maintenables et fiables car bien développés plutôt qu'un logiciel fonctionnel mais mal conçu et ne pouvant pas évoluer.

Voici les énonciations des 5 principes formant l'acronyme SOLID :

* Single Responsability Principle (SRP) - *Une classe doit avoir une et une seule raison d'être modifiée*
* Open Closed Principle (OCP) - *Les classes et les méthodes dovient être ouvertes à l'extension, mais fermées à la modification*
* Liskov Substitution Principle (LSP) - *Les super-types doivent pouvoir être substitués par leur implémentations*
* Interface Segregation Principle (ISP) - *Le client ne doit pas dépendre de méthodes qu'il n'utilise pas*
* Dependency Inversion Principle (DIP) - *Les modules de haut niveau ne doivent pas dépendre de modules de bas niveau, les deux doivent dépendre d'abstraction. Les abstractions ne doivent pas dépendre d'implémentations, les implémentations doivent dépendre d'abstractions*

## Prérequis

* Une maîtrise solide (😉) du paradigme orienté objet est fortement recommandée. Ces principes viennent renforcer et améliorer des notions de base déjà acquises.
* Une connaissance, même minimale, du pseudo code afin de comprendre les exemples.
* De la patience et de la volonté, ces principes peuvent être difficilement compris et rien ne vaut la pratique pour les appréhender complètement.

## Table des matières

> Ces principes s'appliquant à tous les langages orientés objets, les exemples seront écrits en *pseudo code* et l'ensemble de ces exemples seront disponibles dans un dossier `code`.

1. [Le principe de responsabilité unique - Chacun son rôle](fr/1_principe_responsabilite_unique/Single_Responsability_Principle.md)
2. [Le principe ouvert-fermé - S'ouvrir au monde tout en s'en protégeant](fr/2_principe_ouvert_fermé/Open_Closed_Principle.md)
3. [Le principe de substitution de Liskov - La preuve qu'un carré n'est pas un rectangle](fr/3_principe_substitution_liskov/Liskov_Substitution_Principle.md)
4. [Le principe de ségrégation des interface - Diviser pour être moins dépendant](fr/4_principe_segregation_interfaces/Interface_Segregation_Principle.md)
5. [Le principe d'inversion de dépendance - Dépendre ou ne pas dépendre, telle est la question](fr/5_principe_inversion_dependance/Dependance_Inversion_Principle.md)