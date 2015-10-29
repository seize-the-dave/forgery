# Forgery [![Build Status](https://travis-ci.org/adaptive-logic/forgery.svg?branch=master)](https://travis-ci.org/adaptive-logic/forgery)

Forgery is a simple Java library for filling graphs of POJOs with _realistic_ dummy data.  Forgery uses JSR 330 (`javax.inject`) behind the scenes to provide data, which makes it very simple to extend.

## Usage

Forgery comes with a [bunch of default](src/main/resources/META-INF/services/javax.inject.Provider) `javax.inject.Provider`s to cover common business domain objects.  To create an instance of Forgery, just build like so:

```java
Forgery forgery = new Forgery.Builder().build();
```

Now you're ready to start forging your domain objects:

```java
Employee employee = forgery.get(Employee.class);
employee.getFirstName(); // "John"
employee.getLastName(); // "Smith"
employee.getDateOfBirth(); // 11-Jan-1966
```

### Extending Forgery

You can extend Forgery in two ways.  Firstly, add a new `Provider` instance or `Class<? extends Provider>` to the builder directly, like so:

```java
Forgery forgery = new Forgery.Builder()
                             .addForger(new CustomProvider())
                             .addForger(NameProvider.class)
                             .build();
```

Alternatively, you can add your `Provider` classes to the following file in any JAR on your classpath using the Java SE `ServiceLoader` utility:

```
META-INF/services/javax.inject.Provider
```

The contents of the file should look like so:

```
uk.co.adaptivelogic.forgery.forger.RandomDateProvider
uk.co.adaptivelogic.forgery.forger.RandomDateOfBirthProvider
uk.co.adaptivelogic.forgery.forger.RandomDoubleProvider
org.example.SpecificDomainProvider
```

`ServiceLoader` will instantiate each class using a no-args constructor, so this will only work for very simple self-contained providers.

### Creating a Provider

#### Step 1: Implement the Provider Interface

Creating a new `Provider` is pretty simple.  Just implement `javax.inject.Provider<T>` to return an instance of `T` like so:

```java
public class FirstNameProvider implements Provider<String> {
    public String get() {
       return "Bob";
    }
}
```

#### Step 2: Annotate to Match Properties

The next step is to annotate your class with the `@Property` annotation to tell Forgery what properties to inject against.

```java
@Property("firstName")
public class FirstNameProvider implements Provider<String> {
    public String get() {
       return "Bob";
    }
}
```

The `@Property` annotation can take multiple property names:

```java
@Property({"firstName", "foreName"})
```

#### Step 3: Use Injection to Compose Providers

If your `Provider` is a composite of different types, you can reuse existing `Provider`s through dependency injection, like so:

```java
@Property("name")
public class NameProvider implements Provider<String> {
    private Provider<String> firstName;
    private Provider<String> lastName;

    public NameProvider(@Named("firstName") Provider<String> firstName, @Named("lastName") Provider<String> lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public String get() {
        return firstName.get() + " " + lastName.get();
    }
}
```

*Note*: you can currently only take advantage of dependency injection by registering your `Provider` *class* with the builder.  Registering an instance will not work; neither will using the `ServiceLoader` mechanism described above.

## Building with Forgery

Forgery is available from [Maven Central](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22uk.co.adaptivelogic%22%20AND%20a%3A%22forgery%22).  Here's the dependency you'll need for Maven, but Forgery can also be used as a dependency in Ivy, Buildr and Gradle (Java), Grape (Groovy), SBT (Scala) and Leiningen (Clojure):

```xml
<dependency>
  <groupId>uk.co.adaptivelogic</groupId>
  <artifactId>forgery</artifactId>
  <version>0.2.0</version>
  <scope>test</scope>
</dependency>
```
