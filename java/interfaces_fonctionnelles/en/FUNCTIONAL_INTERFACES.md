# Functional interfaces

In Java, functional interfaces are a major feature that has been available in the language since version 1.8. This course will cover their definition, syntax and use cases through detailed examples, and will eventually mention the main interfaces provided by the standard library.

### Prerequisites

- A relatively good knowledge in Object Oriented Programming
- Be comfortable with the usage of interfaces, at least theoretically, meaning:
  - Understand the concept of `abstract methods`
  - Understand the concept of `default methods`
- Be comfortable with anonymous classes, at least theoretically

## Definition

A functional interface is a type of interface that owns one - and only one - **abstract** method. How many `default` methods the interface owns doesn't matter.

Here are some examples:

```java
interface Test1 {

	void doSomething();

	default void doNothing() {
		System.out.println("This method does not do anything");
	}
}

interface Test2 {

	default void foo() {}

	default String getGreeting() { return "Hello!"; }
}

interface Test3 {

	void foo(String someStr);

	void bar(int someInt);

	default void fooThenBar(String someStr, int someInt) {
		foo(someStr);
		bar(someInt);
	}
}
```

Amongst those three interfaces, only `Test1` is a so called functional interface, since it is the only one that contains a single `abstract` method. As for conventions, it is strongly recommended to add the `@FunctionalInterface` annotation at class level to ensure that your interface respects the rules.

## Use cases

Now, the question is: why is it said to be a 'new' feature from java 8? Interfaces have been present since the very first release of the language, and functional interfaces are nothing but a specific type of regular interfaces. And it's hard to believe that the only feature is the `@FunctionalInterface` annotation, since it is nothing more than an optional check, similarly to `@Override`. In fact, Java 8 adds a rather revolutionnary syntax concerning **interface instantiation**. So... before we continue, you should know that it is kind of wrong to talk about `interface instantiation`, because like abstract classes, interfaces **cannot** be instantiated. To use them, it is required to have a class (anonymous or not) implement the interface. In this course, `interface instantiation` will refer to the process of implementing an interface with an anonymous class.

Let's start off with an example. Let `BinaryOperation` a functional interface that looks like this:

```java
interface BinaryOperation {

	double compute(double a, double b);
}
```

Our goal is to somehow be able to instantiate an object that will possess this `compute` function, so that we can perform operations between to numbers, such as an **addition**.
In Java 7 old fashioned style, the quickest way to do so would be the following:

```java
BinaryOperation sum = new BinaryOperation() {

	@Override
	public double compute(double a, double b) {
		return a + b;
	}
};

System.out.println(sum.compute(4.0, 7.0)); // Output: 11.0
```

As planned, we successfully "stored" a method that asks for two doubles and returns a double in a variable, which is pretty cool. However, if you're new to this kind of black magic, you might wonder: "Great but... what for?" Well, let's take yet another example. Imagine you own a `List<String>` and wish to allow the users to perform a defined action on every single element of the list. You might think of doing it like this:

```java
class MyClass {

	private List<String> myList;

	public MyClass() {
		myList = new ArrayList<>();
		myList.add("Hello");
		myList.add("Good morning");
		myList.add("Have a good one");
	}

	public List<String> getList() {
		return myList;
	}
}
```

And since you've given access of the list to the user, they can take care of for-looping the collection themselves, and perform whatever action they want. For instance:

```java
MyClass test = new MyClass();
for(String str : test.getList()) {
	System.out.println(str);
}
```

However, this code is very far from flawless. What you want is to provide the list exclusively so that the user can perform an action on every element. But... where is your warrant that it's all the user plans on doing? They could very well do:

```java
MyClass test = new MyClass();
test.getList().add("haha you're stupid and ugly");
```

Which would cause an externally modified list, which is highly problematic. Obviously, in this trivial example, the user is the bad guy that wants to break the programm, which doesn't really make sense in real life situations. But in reality, this applies to every single user. A well designed `API` should never give power to the user that they could misuse, because even a user with good intentions will tend to find bad solutions to their issues, if the API gives them too much control.

Luckily, to every issue its solution, functional interfaces come to the rescue. Let's first of all remove that outrageously ugly `getList()` method and replace it by something much more convenient that will give much less power to the user.

```java
class MyClass {

	private List<String> myList;
	public MyClass() {
		myList = new ArrayList<>();
		myList.add("Hello");
		myList.add("Good morning");
		myList.add("Have a good one");
	}

	public void executeActionForEach(Action myAction) {
		for(String str : myList) {
			myAction.applyActionTo(str);
		}
	}
}
```

With `Action` being a functional interface looking like this:

```java
interface Action {
	void applyActionTo(String str);
}
```

Now, to display every element of the list, all we have to do is the following:

```java
MyClass test = new MyClass();
Action printAction = new Action() {
	@Override
	public void applyActionTo(String str) {
		System.out.println(str);
	}
};
test.executeActionForEach(printAction);
```

Great! We successfully performed a given action (described by `Action`) on every single element of the list, without leaking the latter.

## So... what about Java 8 then?

We now know why functional interfaces are so useful, but we're yet to discover why Java 8 was THE release that really introduced them. Well, as previously stated, Java 8 provides us with a new, shorter and faster syntax to instantiate functional interfaces. Let's take our previous example back:

```java
Action printAction = new Action() {
	@Override
	public void applyActionTo(String str) {
		System.out.println(str);
	}
};
```

Since, by definition, we are guaranteed that `Action` only has one method to implement, we can now use the following shortcut:

```java
Action printAction = (str) -> System.out.println(str);
```

Pretty dope right? Let's go through this example in details.

- As usual, we declare the type of our variable, `Action` in this case, and we assign the variable a name, here `printAction`
- Since we know that all there is to care about is that `applyActionTo` method, we can define it with a shortcut.
  - First of all, between the brackets lie the parameters of the method, separated by comas. In our example, there is only one parameter so there is no need for comas. In such a case, it would even be possible to omit the brackets surrounding `str`. You'll notice that since the compiler knows very well the types of the parameters, they're not explicitely stated.
  - Then, we indicate with an arrow (`->`) that we are going to define the body of the method
  - Eventually, we do implement the body of the method by simply indicating the instruction to execute. It is possible to have multiple instructions as a body, by encapsulating those instructions in curly braces (`{}`). For instance:
    ```java
    Action myAction = (str) -> {
      System.out.println(str);
      System.out.println("A String was displayed using a lambda expression!");
    }
    
## Always shorter, always further

In some cases, it is possible to shorten the syntax even more. If your sole instruction as your body is to call a method, and if by any luck the signature of that method happens to match that of the functional interface, then you can reference it directly. Let's take our previous example one more time:

```java
Action printAction = (str) -> System.out.println(str);
```

Here, we want a method that asks for a String a returns nothing, it is the very definition of our `applyActionTo` method. And luckily for us, `println` has the same signature! It asks for a `String` and returns nothing. Therefore, we can indicate that our action corresponds to `println`. For the sake of covering all the different cases, we'll create a `static` method as a wrapper to encapsulate in a static context the `println` method (will follow other examples in non-static contexts).

```java
class MyUtilClass {
	static void printString(String str) {
		System.out.println(str);
	}
}
```

We can now assign this method as the value for our functional interface, like this:

```java
Action printAction = MyUtilClass::printString;
```

When you're not dealing with a static environment, there are two possibilities. Let's say we have a class like this:

```java
class SomeClass {
	void someMethod(SomeClass other);
}
```

1. Reference the method of an object (and not a class), for instance: `myObject::someMethod;` This will turn out to be useful if we require a method that returns `void` and asks for **one** (not two!) `SomeClass` type object. Why not two? Simply because we already indicate from which object `someMethod` will be called. All that's missing is thus the sole parameter.
2. Use a static reference, for instance: `SomeClass::someMethod;`. This time the signature of the method must be a method that also returns `void` but asks for **two** `SomeObject` type objects, since here the object from which `someMethod` will be called must also be provided. The first parameter will be the latter, and the second will be the parameter of `someMethod`.

## Standard library

Since it would be impossible to cover all the systems that use functional interface, this section will be focused on describing the main interfaces provided by the standard library.

- `Supplier<T>` supplies a `T` type object through a `get()` method
- `Runnable` performs an action that requires no parameter, nor any return value
- `Consumer<T>`is the same as `Runnable`, but requires a single parameter of type `T`
- `Function<T, R>` acts as a function that asks for a `T` and returns a `R`
- `Predicate<T>` is merely a shortcut for `Function<T, Boolean>`.
- `BiConsumer<T, U>`, `BiFunction<T, U, R>`, `BiPredicate<T, U> are similar to the previous ones, but take two parameters instead of one

## Conclusion

Thanks to the new syntax Java 8 provided such as lambda expressions and method referencing, functional interfaces have been introduced to the language by allowing, in a way, to store functions in variables. However, we're still extremely far from reaching functional programming support (and Java will very likely never reach it), even though this feature is very practical and useful.
