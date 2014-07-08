Forgery [![Build Status](https://travis-ci.org/adaptive-logic/forgery.svg?branch=master)](https://travis-ci.org/adaptive-logic/forgery)
=======

Forgery fills object graphs of POJOs with realistic dummy data

Usage
-----

```java
Person person = new Forgery().forge(Person.class);
person.getFirstName(); // "John"
person.getLastName(); // "Smith"
```
