Forgery [![Build Status](https://travis-ci.org/adaptive-logic/forgery.svg?branch=master)](https://travis-ci.org/adaptive-logic/forgery)
=======

Forgery fills graphs of domain objects with fake data

Usage
-----

```java
Person person = Forgery.forge(Person.class);
System.out.println(person.getFirstName()); // "John"
System.out.println(person.getLastName()); // "Smith"
```
