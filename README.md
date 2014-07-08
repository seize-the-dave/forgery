Forgery [![Build Status](https://travis-ci.org/adaptive-logic/forgery.svg?branch=master)](https://travis-ci.org/adaptive-logic/forgery)
=======

Forgery fills graphs of domain POJOs with fake data

Usage
-----

```java
Person person = new Forgery().forge(Person.class);
person.getFirstName(); // "John"
person.getLastName(); // "Smith"
```
