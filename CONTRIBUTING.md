# Contribuer

Avant de contribuer à ce dépôt, il est recommandé de discuter des changements que vous voulez apporter via les issues ou le Discord.

## Règles de contribution

### Organisation

- Un sujet doit avoir son répertoire.
- Plusieurs sujets peuvent être imbriqués, afin de traiter d'un sous-sujet.
- Chaque répertoire, s'il contient un cours, doit contenir un fichier `README`.
- Le fichier `README` doit toujours être présent et contenir une table des matières.
- Chaque cours doit avoir un répertoire par langue, même si le cours n'est écrit que dans une langue.
- Le nom d'un répertoire de langue doit être le code ladite langue, par exemple `fr` ou `en`.
- Si le cours est disponible pour plusieurs langues, il doit y avoir une table des matières pour chaque langue dans le fichier `README`.

Des [exemples](#exemples) sont disponibles pour illustrer ces règles.

### Contenu des cours

Les cours peuvent porter sur **n'importe quelle notion technique**, et être écrits en n'importe quelle langue. Veillez à utiliser une orthographe correcte, peu importe la langue. Utilisez un correcteur si nécessaire.

### Formatage

#### Commits

Vos commits peuvent être dans n'importe quel format, mais préferrez les [commits conventionnels](http://conventionalcommits.org/).

#### Pull-requests

Si le contenu du cours est en français, la pull-request doit être en français. Si le contenu du cours est dans n'importe quelle autre langue, la pull-request doit être en anglais.

Le titre d'une pull-request doit correspondre au format `Ajout de chemin/vers/le/cours` en français, ou `Add chemin/vers/le/cours` en anglais.

Par exemple, `Ajout de poo/abstractions`.

## Processus de contribution

1. [Forkez](https://help.github.com/en/github/getting-started-with-github/fork-a-repo) le dépôt
2. [Clonez](https://help.github.com/en/github/creating-cloning-and-archiving-repositories/cloning-a-repository)-le
3. [Créez une branche](https://help.github.com/en/desktop/contributing-to-projects/creating-a-branch-for-your-work)
   - Créez **une branche par langue** (ex. `poo-abstractions-fr`)
4. Apportez vos modifications en suivant les règles de contribution
5. Créez une [pull request](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request) sur `master`
   - Veillez à effectuer **une pull-request** par langue et par cours
6. Attendez une revue
7. Si des changements sont demandés, effectuez-les, sinon, tout est bon.

# Exemples

## `README`

Voici un exemple de fichier `README` pour du contenu uniquement français

```md
# Nom de la notion

Expliquez ce sur quoi porte le cours. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.

## Table des matières

- Nom du premier chapitre - Nom d'un sous-chapitre - Nom d'un autre sous-chapitre
- Nom du second chapitre
```

Pour du contenu multi-langues, dupliquez le modèle ci-dessus, et traduisez-le.

```md
# Titre de la notion 🇫🇷

Expliquez ce sur quoi porte le cours. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.

## Table des matières

- Nom du premier chapitre - Nom d'un sous-chapitre - Nom d'un autre sous-chapitre
- Nom du second chapitre

--

# Notion's title 🇺🇸

Explain the course's contents. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.

## Table of contents

- First chapter's name - Subchapter's name - Another subchapter's name
- Second chapter's name
```

## Organisation

Voici un exemple de hiérachie des cours :

```
+ poo
	+ abstractions
		- README.md
		+ fr
			- CHAPTER_1.md
			- CHAPTER_2.md
		+ en
			- CHAPTER_1.md
			- CHAPTER_2.md
```
