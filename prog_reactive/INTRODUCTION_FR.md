# Introduction à la Programmation Réactive

> Écrit par [A~Z](https://github.com/AZ-0)

Avez vous déjà entendu parler de la programmation réactive ? Non ? Et bien, vous devriez !

Donc, commençons par la définition imbitable de wikipédia :


## Définition

En [français](https://fr.wikipedia.org/wiki/Programmation_r%C3%A9active)
> En informatique, la programmation réactive est un paradigme de programmation visant à conserver une cohérence d'ensemble en propageant les modifications d'une source réactive (modification d'une variable, entrée utilisateur, etc.) aux éléments dépendants de cette source.

Et en [anglais](https://en.wikipedia.org/wiki/Reactive_programming), parce que l'anglais c'est bien (c'est même tellement bien que j'ai traduit pour vous)
> En informatique, la programmation réactive est un paradigme de programmation déclarative portant principalement sur les flux de données (data streams) et la propagation du changement (propagation of change). Il est possible avec ce paradigme de facilement exprimer des flux de données statiques (comme des tableaux) ou dynamiques (comme des émetteurs d'événements), ainsi que de montrer clairement les dépendances dans le modèle d'exécution, ce qui facilite la propagation automatique de transformations d'un flux de données.

D'ailleurs je préfère la version anglaise qui est bien plus complète :D

Pas de panique, si vous ne comprenez pas c'est normal !


## La lumière de l'explication dans l'obscurité de wikipédia

Comme je suis méchant, je vais tout de suite sortir les grands mots: `Publisher`! Ouuuuh, ça fait peur... (et ça rime)

> Haha, j'ai pas peur, je sais parler anglais ! Un Publisher, c'est un truc qui publie d'autres trucs, non ?

**EXACT !** En effet, un Publisher ça publie des valeurs, mais d'une manière un peu spéciale. Et pour bien comprendre comment ça fonctionne, et aussi parce que j'aime bien quand ça explose, on va parler d'un canon. Ne faites pas attention aux `(x)`, vous comprendrez plus tard…

Oui, supposons que vous alliez acheter un canon au marché. C'est un canon high-tech, il est capable de se recharger tout seul. Vous revenez, votre canon sous le bras, vous `(1)` l'installez sur une petite colline (en visant votre patron), `(2)` récupérez les mèches, et là vous `(3)` allumez les mèches (une mèche par boulet que vous souhaitez tirer), puis `(4)` attendez un temps indéterminé...
- Vous ne savez pas quand le canon va tirer
- Vous ne savez même pas s'il va tirer autant de boulets que vous avez allumé de mèches (a-t-il suffisamment de boulets en réserve ? va-t-il exploser ?)
- Vous avez décidé de danser à chaque fois que le canon lance un boulet
- Vous avez décidé de courir si le canon explose (Ça peut paraître évident, mais c'est important)
- Vous avez décidé de faire la grève si votre patron n'est pas mort quand le canon n'a plus de boulet à lancer (et qu'il n'a pas explosé, sinon vous seriez en train de courir)

On va remplacer le canon par du code java, parce qu'officiellement on est censé faire de la programmation.

```java
// Ce canon pourrait posséder une infinité de boulets OwO
interface Cannon {
	//Pour installer le canon sur la colline, et prendre le temps de viser
	void install(Me me);
}

//Je n'ai pas déclaré la méthode dance() ni CannonBall, on fait du pseudo code par ici
interface Me {
	//Après avoir installé le canon, celui-ci s'ouvre et nous donne une infinité de mèches
	//C'est le futuuuuuur
	void onPrepare(FuseBatch fuses);
	
	//Appelé quand le canon lance un boulet de canon sur votre patron
	default void onNext(CannonBall ball) {
		dance();
	}

	//Appelé quand le canon du futur a une erreur interne et explose. Ça, c'est high-tech.
	default void onError(Throwable t) {
		runForYourLife();
	}
	
	//Appelé quand le canon n'a plus de boulets à envoyer sur votre patron.
	default void onComplete() {
		if (patron.isAliveAndKicking())
			timeToGoOnStrike();
	}
}

interface FuseBatch {
	//Vous appelez cette méthode pour enflammer `fuseAmount` mèches,
	// afin de demander au canon d'envoyer `fuseAmount` boulets.
	void fire(long fuseAmount);
	
	//Si par hasard vous changiez d'avis et vouliez conserver votre patron,
	// éteignez les mèches grâce à cette méthode.
	void cancel();
}
```

Maintenant que c'est fait, regardons à tout hasard du code venant de [reactive-streams](http://www.reactive-streams.org/) (dans le jdk depuis java 9)

> Si vous ne connaissez pas la généricité, je vous invite à regarder les [fiches correspondantes](../java/généricité)

```java
interface Publisher<T> {
	void subscribe(Subscriber<? super T> subscriber); //On prépare la souscription
}

interface Subscriber<T> {
	//Le Publisher envoie la souscription une fois préparée
	void onSubscribe(Subscription s);

	//Appelé quand le Publisher émet un nouvel élément
	void onNext(T t);
	
	//Appelé si une erreur est survenue lors de la génération / récupération d'un nouvel élément
	void onError(Throwable t);
	
	//Appelé quand le Publisher sait de manière certaine qu'il n'émettra plus jamais d'éléments
	void onComplete();
}

interface Subscription {
	//Demande au Publisher d'émettre `elementAmount` prochains éléments
	//Le Publisher émet toujours exactement `elementAmount`,
	// sauf si le flux d'émissions est complété,
	// auquel cas il aura appelé Subscriber#onComplete ou Subscriber#onError auparavant
 	void request(long elementAmount);

	void cancel(); //Demande au Publisher d'arrêter d'envoyer des données
}
```

Ça alors ! Quelle troublante et imprévisible ressemblance !
Si vous avez compris l'exemple du canon en remplaçant les éléments correspondants, alors vous avez compris le grand principe de la programmation réactive.
1. On commande une souscription au programme Prime d'Amazon
2. On reçoit la souscription préparée sur mesure. Notez bien qu'il ne fait pas de sens d'acheter une nouvelle souscription tant que l'ancienne n'est pas épuisée.
3. On utilise la souscription pour commander des éléments à Amazon
4. Ces éléments nous seront fournis, plus tard (encore plus tard si le livreur est coincé dans les bouchons). Il se pourrait que le colis se perde, ou qu'Amazon soit en rupture de stock.

Maintenant que vous avez cette liste, vous devriez pouvoir comprendre les `(x)` mentionnés plus haut ! (une fiche rétroactive, ça c'est innovant)

Nous allons nous arrêter ici pour une toute première introduction sur le Grand Principe de la programmation réactive, et j'espère que votre imagination s'emballera quant aux possiblités (immenses, nous le verrons plus tard) d'un tel paradigme.

Vous trouverez ci-dessous quelques liens pour aller beaucoup plus loin que cette brève introduction, ne me remerciez pas.

## Quelques liens ci-dessous pour aller beaucoup plus loin que cette brève introduction, ne me remerciez pas.

* **[Reactor Reference Guide](https://projectreactor.io/docs/core/release/reference/#intro-reactive) :** Pourquoi vous devriez utiliser la programmation réactive tout partout.
* **[Introduction to Reactive Coding](https://gist.github.com/staltz/868e7e9bc2a7b8c1f754) :** Une très bonne, que dis-je, excellente ressource pour commencer à se familiariser avec du code en programmation réactive. Rx.js y est utilisé mais vous pouvez transposer dans votre langage sans problème.

### Libs pour utiliser la sacro-sainte programmation réactive dans votre langage favori

* **[Reactor](https://projectreactor.io/) :** uniquement pour les langages jvm, mais bien mieux nommée et intuitive que la floppée de Rx selon moi.
* **[ReactiveX](http://reactivex.io/) :** disponible dans moult langages, vous y trouverez certainement votre bonheur.
