# La curryfication
## Préambule

La curryfication est une notion souvent employée en programmation fonctionnelle. Elle consiste à transformer une fonction à plusieurs arguments en fonction à un seul argument, retournant une fonction reposant sur les arguments restants.
En d'autres termes, elle transforme une fonction `fn(a, b, c)` en fontion `fn(a)(b)(c)`.

Il est à noter que la plupart des fonctions curryfiées permettent toujours de passer plusieurs arguments en une seule parenthèse ; une fonction à trois arguments `fn(a, b, c)`, une fois curryfiée, peut donc être appelée sous une de ces quatre formes :
- `fn(a)(b)(c)`
- `fn(a)(b, c)`
- `fn(a, b)(c)`
- `fn(a, b, c)`

Nous aborderons cette notion à l'aide d'exemples en JavaScript.

## Utilité
Bien que la curryfication puisse sembler aux premiers abords superflue, elle est au contraire bien utile. Elle permet notamment :
- d'améliorer la lisibilité en réduisant les répétitions grâce à l'application d'une [partial application](https://en.wikipedia.org/wiki/Partial_application) sur une fonction à plusieurs arguments (on lie une valeur par défaut à un argument afin de créer une base réutilisable),
- de simplifier le débogage ; les fonctions curryfiées ont pour but d'être dites "[pures](https://fr.wikipedia.org/wiki/Fonction_pure)" : elles ne provoquent donc pas d'[effet de bord](https://fr.wikipedia.org/wiki/Effet_de_bord_(informatique)) (modification de l'environnement extérieur à la fonction : mutation d'une variable non locale, d'une variable statique locale, d'un flux d'entrée ou de sortie...) et leur valeur de retour ne dépend que des arguments fournis (l'environnement extérieur n'influe donc pas sur le retour de la fonction).

### Ordre des arguments
Malgré le fait que la curryfication permette la séparation des arguments d'une fonction, ceux-ci doivent cependant toujours être fournis dans le même ordre. Ainsi, une fonction curryfiée `fn(a, b, c)` doit d'abord recevoir l'argument `a`, puis l'argument `b`, et enfin l'argument `c`.

Il s'agit également d'un des points négatifs de la curryfication : une fois curryfiée, une fonction perd la signature de ses arguments (leurs noms et leurs types). Assurez-vous donc d'essayer de donner un ordre logique à vos arguments.

## Exemple de fonction curryfiée
Prenons l'exemple d'une fonction qui a pour but d'annoncer le trajet entre deux villes ; appelons-la `travel`.
```js
const travelParisToBordeaux = travel("Paris", "Bordeaux");
console.log(travelParisToBordeaux); // "Vous voyagez de Paris à Bordeaux"
```
Maintenant, curryfions-la :
```js
const travelParisToBordeaux = travel("Paris")("Bordeaux");
console.log(travelParisToBordeaux); // "Vous voyagez de Paris à Bordeaux"
```
Comme dit précédemment, nous pouvons nous servir de la curryfication pour appliquer une partial application et créer des sortes de modèles, comme par exemple :
```js
const travelFromParis = travel("Paris");

const travelToDestination = condition
    ? travelFromParis("Bordeaux")
    : travelFromParis("Nantes");

console.log(travelToDestination); // "Vous voyagez de Paris à Bordeaux", ou "Vous voyagez de Paris à Nantes".
```
Grâce à la curryfication, on peut ainsi éviter de répéter deux fois l'argument précédent, ici Paris.

## Implémentation et usage
Nous pouvons implémenter cette notion à l'aide d'une simple fonction qui fera le travail à notre place.
### Implémentation externe (via une bibliothèque)
Certaines bibliothèques, comme [lodash](https://lodash.com) ou [Ramda](https://ramdajs.com/) mettent déjà à disposition une fonction de curryfication.
```js
import _ from "lodash";
import R from "ramda";

function log(level, date, message) {
    return console.log(`[${level}] ${date.toUTCString()}: ${message}`);
}

// lodash
const lodashCurried = _.curry(log); // lodashCurried(level)(date)(message), lodashCurried(level)(date, message), lodashCurried(level, date)(message), lodashCurried(level, date, message)
const lodashInfoNow = lodashCurried("INFO")(new Date());
lodashInfoNow("Ceci est un test avec lodash"); // [INFO] Wed, 19 Aug 2020 16:58:01 GMT: Ceci est un test avec lodash

// Ramda
const ramdaCurried = R.curry(log); // ramdaCurried(level)(date)(message), ramdaCurried(level)(date, message), ramdaCurried(level, date)(message), ramdaCurried(level, date, message)
const ramdaErrorNow = ramdaCurried("ERROR")(new Date());
ramdaErrorNow("Ceci est un test avec Ramda"); // [ERROR] Wed, 19 Aug 2020 16:58:01 GMT: Ceci est un test avec Ramda
```

### Implémentation interne
Bien entendu, il reste possible de l'implémenter soi-même. Nous vous proposons ainsi cette implémentation, à prendre ou à laisser :
```js
function curry(fn) {
	if (typeof fn !== "function") {
		return () => fn;
	}

	return function curried(...args) {
		if (args.length >= fn.length) {
			return fn(...args);
		}

		return function (...curriedArgs) {
			return curried(...args.concat(curriedArgs));
		}
	}
}
```
Bien entendu, à l'instar des deux bibliothèques précédemment citées, cette implémentation fonctionne de la même manière.
```js
const curried = curry(log); // curried(level)(date)(message), curried(level)(date, message), curried(level, date)(message), curried(level, date, message)
const curriedWarnNow = curried("WARN")(new Date());
curriedWarnNow("Ceci est un test avec une implémentation interne"); // [WARN] Wed, 19 Aug 2020 16:58:01 GMT: Ceci est un test avec une implémentation interne
```
