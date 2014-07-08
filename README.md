# Forgery [![Build Status](https://travis-ci.org/adaptive-logic/forgery.svg?branch=master)](https://travis-ci.org/adaptive-logic/forgery)

Forgery is a Java library for filling graphs of POJOs with realistic dummy data

## Usage

Forgery comes with a bunch of default forgers.  To get hold of an instance, just build like so:

```java
Forgery forgery = new Forgery.Builder().build();
```

Now you're ready to start forging your domain objects:

```java
Employee employee = forgery.forge(Employee.class);
employee.getFirstName(); // "John"
employee.getLastName(); // "Smith"
employee.getDateOfBirth(); // 11-Jan-1966
```

### Extending Forgery

You can extend Forgery in two ways.  Firstly, add a new Forger instance to the builder like so:

```java
Forgery forgery = new Forgery.Builder().addForger(new CustomForger()).build();
```

Secondly, you can add your forger classes to the following file in any JAR on your classpath:

```
META-INF/services/uk.co.adaptivelogic.forgery.Forger
```

The contents of the file should look like so:

```
uk.co.adaptivelogic.forgery.forger.RandomDateForger
uk.co.adaptivelogic.forgery.forger.RandomDateOfBirthForger
uk.co.adaptivelogic.forgery.forger.RandomDoubleForger
org.example.SpecificDomainForger
```

### Implementing a Forger

Implementing a new Forger is pretty simple.  Just implement `uk.co.adaptivelogic.forgery.Forger<T>` to return an instance of `T`, and optionally annotate your implementation with `uk.co.adaptivelogic.forgery.Property` if you want to provide more than one implementation for your class.

Forgery will choose an appropriate implementation based on the property name used by your POJOs to reference your domain object.

```java
@Property({"name", "otherName", ".*Name"})
public class CustomForger implements Forger<DomainObject> {
    public DomainObject forge() {
       ...
    }
}
```
