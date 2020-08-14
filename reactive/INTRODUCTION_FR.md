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

**EXACT !** En effet, un Publisher ça publie des valeurs, mais d'une manière un peu spéciale. Et pour bien comprendre comment ça fonctionne, et aussi parce que j'aime bien quand ça explose, on va parler d'un canon. Ne faites pas attention aux `(x)`, vous comprendrez plus tard..

Oui, supposons que vous alliez acheter un canon au marché. C'est un canon high-tech, il est capable de se recharge tout seul. Vous revenez, votre canon sous le bras, vous (1) l'installez sur une petite colline, (2) récupérez les mèches, et là vous (3) allumez les mèches (une mèche par boulet que vous voulez tirer), puis (4) attendez un temps indéterminé...
- Vous ne savez pas quand le canon va tirer
- Vous ne savez même pas s'il va tirer autant de boulets que vous avez allumé de mèches (a-t-il suffisamment de boulets en réserve ? va-t-il exploser ?)
- Vous avez décidé de dancer à chaque fois que le canon lance un boulet
- Vous avez décidé de courir si le canon explose
- Vous avez décidé de boire du jus d'ananas quand le canon finit de lancer les boulets (et ce même s'il explose !)

On va remplacer le canon par du code java, parce que cette fiche est tout de même censée être liée de la  programmation.
Si vous ne savez pas ce qu'est la généricité, regardez dans les [fiches correspondantes](../java/généricité)

```java
interface Cannon {
	void install(Me me); //Pour installer le canon sur la colline
}

interface Me {
	void onPrepare(FuseBatch fuses); //Après avoir installé le canon, celui-ci s'ouvre et nous donne un lot de mèches
	
	//Je n'ai pas déclaré la méthode dance() ni CannonBall, on fait du pseudo code par ici
	default void onNext(CannonBall ball) {  dance(); } //Appelé quand le canon lance un boulet de canon
	
	default void onError(Throwable t) { runForYourLife(); } //Appelé quand le canon a une erreur interne et explose
	
	default void onComplete() { drinkAnanasJuice(); } //Appelé quand le canon arrête définitivement d'envoyer des boulets
}

interface FuseBatch {
	void fire(long fuseAmount);
	void cancel(); //Qui sait ? Si ça se trouve vous marcherez sur les mèches parce que l'envie vous prend d'arrêter.
}
```

Maintenant que c'est fait, regardons trois interfaces de [reactive-streams](http://www.reactive-streams.org/) (dans le jdk depuis java 9)
```java
interface Publisher<T> {
	void subscribe(Subscriber<? super T> subscriber); //On prépare la souscription
}

interface Subscriber<T> {
	void onSubscribe(Subscription s); //Le Publisher envoie la souscription une fois préparée

	void onNext(T t); //Appelé quand le Publisher émet un nouvel élément
	
	void onError(Throwable t); //Appelé si une erreur est survenue lors de la génération / récupération d'un nouvel élément
	
	void onComplete(); //Appelé quand le Publisher sait de manière certaine qu'il n'émettra plus jamais d'éléments
}

interface Subscription {
	//Demande au Publisher d'émettre `elementAmount` prochains éléments
	//Le Publisher émet toujours exactement `elementAmount`, sauf si le flux est complété (auquel cas il appellera Sbscriber#onComplete ou Subscriber#onError)
 	void request(long elementAmount);

	void cancel(); //Demande au Publisher d'arrêter d'envoyer des données
}
```

Ça alors ! Quelle troublante et imprévisible ressemblance !
Si vous avez compris l'exemple du canon en remplaçant les éléments correspondants, alors vous avez compris le grand principe de la programmation réactive.
1. On commande une souscription au programme prime d'amazon
2. On reçoit la souscription préparée sur mesure. Notez bien qu'il ne fait pas de sens d'acheter une nouvelle souscription tant que l'ancienne n'est pas épuisée.
3. On utilise la souscription pour commander des éléments à amazon
4. Ces éléments nous seront fournis, plus tard (encore plus tard si le livreur est coincé dans les bouchons). Il se pourrait que le colis se perde, ou qu'amazon soit en rupture de stock.

Arrêtons nous ici pour l'introduction, vous avez peut-être déjà mal à la tête :o
