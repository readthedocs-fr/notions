# Introduction to Reactive Programming

> Written by [A~Z](https://github.com/AZ-0)

Do you know of reactive programming? No? You should!

Let's begin with wikipedia's obscure definition:

## Definition

The original article has the [french](https://fr.wikipedia.org/wiki/Programmation_r%C3%A9active) definition as well, but seeing as it is quite useless I just removed it.
You shouldn't need french when programming anyway, no?

We only have the [english article](https://en.wikipedia.org/wiki/Reactive_programming) left then, so here it is:
> In computing, reactive programming is a declarative programming paradigm concerned with data streams and the propagation of change.
> With this paradigm it is possible to express static (e.g., arrays) or dynamic (e.g., event emitters) data streams with ease, and also communicate that an inferred dependency
> within the associated execution model exists, which facilitates the automatic propagation of the changed data flow.

Wow, that's a lot of cool and complicated words! Don't worry, at this point not feeling overwhelmed would be concerning.
(Or it just means you actually don't need this introduction at all so stop reading it and do something useful with your life)

## An explanation to rekindle lost hopes

Looks like you have a bad karma, because I am gonna be mean~. So mean that I'll use complicated words right away: `Publisher`! (Are you fearing me now?)

> Haha, I do not fear *anything*! A Publisher is something that publish other things, no?

**EXACT!** Or rather, a Publisher emits things. A Publisher emits values, but specially so.
My goal here and what I'm not paid for is to make you understand just how this strange process works.
I like exploding things, so we'll talk about a cannon.
Don't worry about the `(x)` right now, you will understand them later on…

Let's suppose you happily go to Wallmart to buy a cannon. It's a high-tech cannon that can automagically recharge.
With your newfound super-duper cannon, you go back and `(1)` install it on a hill (locking on your boss), `(2)` gets the fuses batch, and `(3)` fire as many fuses as you want to throw cannonballs on your boss.
- You don't know when the cannon will fire.
- You don't even know whether the cannon will throw enough cannonballs to match your explosion desires (Has it enough ammunition? Will it explode halfway?)
- You hate your boss enough that you decided to dance whenever the cannon throws a cannonball.
- You somehow decided to run if the cannon explodes. Thanks, Captain Obvious.
- You have decided to go on strike if your boss is still dead and you are running out of cannonballs. And if the cannon hasn't exploded, otherwise you would have fled too far~

That's a nice program we got here!

> Shouldn't this sheet actually be concerned about programming? We only discussed cannoning so far…

Oh my, how silly of me. I don't want to change topics so let's implement this high-tech cannon in pseudo java code!

```java
// This cannon is so high tech that it may or may not possess an infinite amount of cannonballs.
interface Cannon {
	// To install the cannon on our lovely hill, and take the time to accurately target your boss.
	void install(Me me);
}

// I didn't declare a lot of things, we are doing pseudocode here
interface Me {
	// When the cannon finished it's installation, it will notify us and we get an infinite batch of fuses.
  // Just because it's the future.
	void onPrepare(FuseBatch fuses);
	
	// This is called whenever the cannon launches a cannonball toward your patron.
	default void onNext(CannonBall ball) {
		dance();
	}

	// This is called when the cannon of the future throws an uncaught runtime exception and explodes.
  // Yes, the futuuuuuuuure still has runtime exceptions :'c
	default void onError(Throwable t) {
		runForYourLife();
	}
	
	// This is called when the cannon has no cannonball left to fire.
	default void onComplete() {
		if (boss.isAliveAndKicking())
			timeToGoOnStrike();
	}
}

interface FuseBatch {
	// We kindly ask the cannon to launch `fuseAmount` cannonballs by firing as many fuses.
	void fire(long fuseAmount);
	
	// If you suddendly remember the inherent kindness of your boss, stow your cannon and try to cancel the cannonballs' throwing.
  // You might miss some fuses though, trampling on them isn't exactly a precise method.
	void cancel();
}
```

Well now that's done, we don't have anything else to do with our cannon.
So let's haphazardly pick some code from [reactive-streams](http://www.reactive-streams.org/) since it should be related to the official topic of this sheet.
By the way, reactive-streams is integrated to the jdk since java 9.

> If you don't know of genericity, I advise you to check the [related courses](../java/généricité)

```java
interface Publisher<T> {
  // We prepare the subscription, this might take some time
	void subscribe(Subscriber<? super T> subscriber);
}

interface Subscriber<T> {
	// When the Publisher is done preparing the subscription, it calls this method.
	void onSubscribe(Subscription s);

	// This is called whenever the Publisher emits a new element.
	void onNext(T t);
	
	// This is called if an error occurs while preparing the subscription or generating a new element.
	void onError(Throwable t);
	
	// This is called when the Publisher knows it has no more elements to emit.
	void onComplete();
}

interface Subscription {
	// Kindly asks the Publisher to emit `elementAmount` elements.
	// The Publisher emits always exactly as many elements as we asked it for, except in certain cases:
  // • If the cumulated demand overflow the long max number, it can treat it as unbound, effectively turning into a hot source.
  // (We'll see what this means in later sheets)
	// • If the Publisher completes (cannot generate anymore element) or errors (sad life), it will call the related method then stop ANY further emission.
 	void request(long elementAmount);

  // Asks the Publisher to cancel the subscription, that is to stop emitting elements.
	// Previous pending requests might still be met.
  void cancel();
}
```

I am flabbergasted! What an uncanny and totally not schemed likeness!
If you understood the cannon example by replacing matching elements, then you understood the Big Principle of reactive programming :o
1. We order a subscription on Amazon Prime.
1. We get the tailored subscription; note that it makes no sense for a person to have multiple subscriptions.
1. We use the subscription to request objects to Amazon.
1. These values will get there, eventually. And later. And might not arrive at all but usually they do (or I at least hope so). Amazon might even find itself out of stock!

With this awesome list written by the great and humble me, you ought to understand the lil `(x)` scattered up there! (a retroactive sheet, progress cannot be halted)

Let's stop there for a very small peek into reactive programming, I pray that your imagination run wild on its capabilities~


You can find some links below to get much further afield than this quick introduction, don't thank me.

## Some links below to get much further afield than this quick introduction, don't thank me.

* **[Reactor Reference Guide](https://projectreactor.io/docs/core/release/reference/#intro-reactive) :** Reactive, reactive everywhere.
* **[Introduction to Reactive Coding](https://gist.github.com/staltz/868e7e9bc2a7b8c1f754) :**
An awe-inspiring resource to get started with reactive programming! It uses RxJS but you can transpose to your favourite language easy-peasy :D

Check project Reactor's website (linked further below) for even more resources. These guys love reactive programming even more than I do!

### Libraries to use the holy sacred reactive paradigm in your preferred language

* **[Reactor](https://projectreactor.io/) :** only compatible with jvm languages, but much more intuitive and well named than the Rx series in my opinion.
* **[ReactiveX](http://reactivex.io/) :** usable nearly everywhere, surely you will find what you search for!
