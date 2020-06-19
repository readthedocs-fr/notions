# Objets Calisthenics

Pour qu'un objet soit déclaré comme étant calisthénic il doit respecter un ensemble de **9 règles** au total. Nous allons les étudier ensemble avec des exemples et en détaillant leur utilités.

##-Règle 1 : Un seul niveau d'indentation par méthode.

Dans l'optique d'optimiser la **lisibilité** de votre code, vous devez vous restreindre à un seul niveau d'indentation dans une méthode. Dans les faits, vous ne pourrez pas toujours imaginez facilement le comportement de votre méthode dans votre tête. Si l'on prend un exemple avec une boucle dans une boucle, ça devient tout de suite plus parlant, vous ne pouvez pas facilement imaginez ce que fait exactement à tout instant votre méthode. Prenons cet exemple : 
```java
class Board {
    public String board() {
        StringBuilder buf = new StringBuilder();

        // 0
        for (int i = 0; i < 10; i++) {
            // 1
            for (int j = 0; j < 10; j++) {
                // 2
                buf.append(data[i][j]);
            }
            buf.append("\n");
        }

        return buf.toString();
    }
}
```
Une manière simple de rendre cette méthode beaucoup plus **lisible** et **compréhensible** est d'appliquer le *Extract Method Pattern*. Appliquer ce pattern ne réduira pas le nombre de ligne de code (et ce n'est pas ce qu'on veut) mais il rendra la méthode beaucoup plus **intuitive** pour un développeur qui cherche à comprendre ce qu'elle fait. Derrière le nom du pattern que vous pourriez trouver un peu barbare se cache une méthode simple : crée le plus de méthode utile possible. Ici par exemple, cela donnerait :
```java
class Board {
    public String board() {
        StringBuilder buf = new StringBuilder();

        collectRows(buf);

        return buf.toString();
    }

    private void collectRows(StringBuilder buf) {
        for (int i = 0; i < 10; i++) {
            collectRow(buf, i);
        }
    }

    private void collectRow(StringBuilder buf, int row) {
        for (int i = 0; i < 10; i++) {
            buf.append(data[row][i]);
        }

        buf.append("\n");
    }
}
```
Revenons sur les changements, il y a l'apparition de deux nouvelles méthodes : `collectRows` et `collectRow`. Elles sont ici pour apporter de la **précision** sur ce que l'on fait, on sait que dans `collectRow` on explore et "collecte" les caractères présents sur une colonne et que logiquement dans `collectRows` on explore et collecte plusieurs colonnes. Rien que le fait d'avoir rajouté ces deux méthodes rend le processus de la méthode `board` plus **compréhensible**.  

##-Règle 2 : N'utilisez pas le mot clé else.

