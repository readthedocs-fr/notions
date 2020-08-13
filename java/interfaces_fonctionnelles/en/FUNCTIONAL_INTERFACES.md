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

Now, the question is: why is it said to be a 'new' feature from java 8? Interfaces have been present since the very first release of the language, and functional interfaces are nothing but a specific type of regular interfaces. And it's hard to believe that the only feature is the `@FunctionalInterface` annotation, since it is nothing more than an optional check, similarly to `@Override`. In fact, Java 8 adds a rather revolutionnary syntax concerning **interface instantiation**. So first of all, it is kind of wrong to use the terms `interface instantiation`, because like abstract classes, interfaces **cannot** be instantiated. To use them, it is required to have a class (anonymous or not) implement the interface. Therefore, in this course, `interface instantiation` will refer to the process of implementing an interface with an anonymous class.

Let's start off with an example. Let `BinaryOperation` a functional interface that looks like this:

```java
interface BinaryOperation {

	double compute(double a, double b);
}
```