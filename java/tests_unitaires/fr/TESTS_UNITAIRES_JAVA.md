# Les tests unitaires en Java

Grâce aux différentes librairies, nous allons pouvoir rendre nos tests simples à comprendre et à écrire.

## Mise en Contexte

Voici la classe ``Calculator`` que nous allons tester :

```java
public class Calculator {

    private final int a;
    private final NumberProvider numberProvider;

    public Calculator(int a, NumberProvider numberProvider) {
        this.a = a;
        this.numberProvider = numberProvider;
    }

    public int add(int b) {
        return a + b;
    }

    public float divide(int b) {

        if (b == 0) {
            throw new IllegalArgumentException("Denominator must be non null.");
        }

        return (float) a / b;
    }

    public int addToNumber() {
        return a + numberProvider.provideNumber();
    }

}

public interface NumberProvider {

    int provideNumber();

}
```

C'est une classe qui contient des méthodes très simples à tester, le but étant seulement de découvrir les tests en java.

NumberProvider est une interface qui fourni simplement un nombre, on pourrait imaginer une implémentation par exemple fournissant un nombre aléatoire.
L'utilité fondamentale de cette interface n'est pas forcément incroyable mais elle nous permettra d'utiliser des mocks.

Les points à retenir de cette classe Calculator pour les tests sont :

 - Le field ``numberProvider`` qui nous permettra de d'expérimenter les mocks.
 - Le test de la méthode divide qui lance une exception si le dénominateur est nul.

## Ecriture des tests

### Préparation de la classe de test

Avant tout, on commence par créer un field ``calculator`` qui sera utilisé pour les tests. Il sera recréé à chaque test grâce à l'annotation ``@Before`` de la méthode ``setUp`` :

```java
import org.junit.Before;
import org.mockito.Mockito;

public class CalculatorTest {

    private Calculator calculator;
    private NumberProvider numberProvider;

    @Before
    public void setUp() {
        this.numberProvider = Mockito.mock(NumberProvider.class);
        this.calculator = new Calculator(10, numberProvider);
    }

}
```

La méthode ``Mockito.mock(T)`` retourne une instance de ``T`` que l'on pourra manipuler pour maîtriser les retours des méthodes de ce mock et donc nos tests.

### Tests classiques

Commençons par écrire des tests simples testant les retours des méthodes ``add`` et ``divide``. 

```java
@Test
public void add_whenGive5_shouldReturn15() {

    int b = 5;

    int sum = this.calculator.add(b);

    assertThat(sum).isEqualTo(15);
}

@Test
public void divide_whenGive2_shouldReturn5() {

    int b = 2;

    float quotient = this.calculator.divide(b);

    assertThat(quotient).isEqualTo(5);
}
```

Les imports sont les suivants :

```java
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
```

On importe de manière static la méthode ``asserThat`` qui nous permet d'avoir une ligne très clair et parlante :

``affirmer que X vaut Y``

### Tests d'exception lancée

Grâce à Assertj, on peut vérifier que la méthode ``divide`` lance bien une ``IllegalArgumentException`` lorsque l'on donne un dénominateur qui vaut 0.

```java
@Test
public void divide_whenGive0_shouldThrowIllegalArgumentException() {

    int b = 0;

    ThrowableAssert.ThrowingCallable method = () -> this.calculator.divide(b);
    assertThatIllegalArgumentException().isThrownBy(method);
}
```

Encore une fois on importe de manière static la méthode ``assertThatIllegalArgument`` pour avoir une ligne claire.

```java
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
```

``affirmer qu'une IllegalStateException est lancée par la méthode Calculator#divide``  (dans le contexte où b vaut 0)

### Test utilisant un mock

Maintenant, le dernier test, utilisant notre mock de ``NumberProvider``.

```java
@Test
public void addToNumber_whenProvide1_shouldReturn11() {

    when(this.numberProvider.provideNumber()).thenReturn(1);

    int sum = this.calculator.addToNumber();

    assertThat(sum).isEqualTo(11);
}
```

On importe (encore) une méthode de manière static, ``when``, pour clarifier.

```java
import static org.mockito.Mockito.when;
```

L'enchainement des méthodes ``when(...).thenReturn(...)`` permet de définir quel sera le retour de la méthode lorsqu'elle sera appeler. Cela nous permet de maîtriser le retour qui sera fait lors de l'appel à la méthode dans ``divide``.

## Conclusion

C'est la fin de cette fiche pratique. Nous avons vu comment créer des tests unitaires en java avec les librairies Junit, Assertj et Mockito. Cela nous a permis de mettre en place une bonne couverture de test pour notre classe Calculator et ainsi la prévenir des erreurs et des régressions.

La totalité du code se trouve dans [le dossier code](../code)

