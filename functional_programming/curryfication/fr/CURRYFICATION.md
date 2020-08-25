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

### Typage
Selon si votre fonction a été curryfiée par une fonction de curryfication, celle-ci peut perdre sa signature (les noms et types des variables, le type de retour).

Pour pallier ce problème, nous avons donc deux solutions :
- assurez-vous d'essayer de donner un ordre logique à vos arguments, et d'en avoir un nombre assez petit (un maximum de trois est idéal) ; ceci s'applique néanmoins dans tout cas, curryfication ou pas !
- utiliser une implémentation qui le supporte, comme [Ramda](https://ramdajs.com/) ou [lodash](https://lodash.com). En JavaScript, vous pouvez donc typer avec JSDoc ; en TypeScript, le simple typage de votre fonction suffit. Dans les deux cas, assurez-vous néanmoins d'avoir installé `@types/ramda` ou `@types/lodash` en fonction de l'implémentation que vous avez choisie.

Après un test de notre côté, nous vous recommandons cependant Ramda, qui gère bien mieux les arguments, leur type, et donc l'autocomplétion, que lodash.

## Exemple de fonction curryfiée
Prenons l'exemple d'une fonction qui a pour but d'annoncer le trajet entre deux villes ; appelons-la `travel`.
```js
const travelParisToBordeaux = travel("Paris", "Bordeaux");
console.log(travelParisToBordeaux); // "Vous voyagez de Paris à Bordeaux"
```
Une fois curryfiée, cela nous donne ceci :
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
Nous pouvons implémenter cette notion de deux façons différentes : curryfier notre fonction dès sa déclaration, ou la curryfier à l'aide d'une simple fonction qui fera le travail à notre place. Pour les deux sections ci-dessous, nous nous baserons sur la fonction `log` :
```js
function log(level, date, message) {
    return console.log(`[${level}] ${date.toUTCString()}: ${message}`);
}
```

### Curryfication à la déclaration
Afin de profiter d'une fonction curryfiée, vous pouvez la curryfier dès sa déclaration (celle-ci ne sera cependant appelable que sous la forme `fn(a)(b)(c)`). L'idée est donc de déclarer une fonction à un seul argument, retournant une fonction à un seul argument, et ce autant de fois qu'il y a d'arguments nécessaires dans votre fonction.

Ainsi, notre fonction `log` ci-dessus devient :
```js
function log(level) {
	return function(date) {
		return function(message) {
			return console.log(`[${level}] ${date.toUTCString()}: ${message}`);
		}
	}
}
```

### Fonction de curryfication
#### Implémentation externe (via une bibliothèque)
Certaines bibliothèques, comme lodash ou Ramda mettent déjà à disposition une fonction de curryfication.
```js
import _ from "lodash";
import R from "ramda";

// lodash
const lodashCurried = _.curry(log); // lodashCurried(level)(date)(message), lodashCurried(level)(date, message), lodashCurried(level, date)(message), lodashCurried(level, date, message)
const lodashInfoNow = lodashCurried("INFO")(new Date());
lodashInfoNow("Ceci est un test avec lodash"); // [INFO] Wed, 19 Aug 2020 16:58:01 GMT: Ceci est un test avec lodash

// Ramda
const ramdaCurried = R.curry(log); // ramdaCurried(level)(date)(message), ramdaCurried(level)(date, message), ramdaCurried(level, date)(message), ramdaCurried(level, date, message)
const ramdaErrorNow = ramdaCurried("ERROR")(new Date());
ramdaErrorNow("Ceci est un test avec Ramda"); // [ERROR] Wed, 19 Aug 2020 16:58:01 GMT: Ceci est un test avec Ramda
```

#### Implémentation interne
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
