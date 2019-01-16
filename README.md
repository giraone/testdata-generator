# A test data generator for persons

This is a java library for generating realistic test data for person name and addresses.

## Goals

Imagine you need to have test data for 100.000 test persons (users, customers, employees, ...).
The typical first idea is to use "John Doe 1" to "John Doe 100000" or "Erwin Mustermann 1" to "Erwin Mustermann 100000".
But this not a realistic scenario. There are certainly much more people named "Thomas Smith" than "Lazarius Fulton-Hong"
or "Thomas Müller" than "Eusebius Wichteldorf". This non-uniform distribution of names can have an impact on the measured
performance in database queries. And having realistic names can help also in doing demos.

If your are in this situation, this library can help. It generates names from weighted datafiles.
Such a file looks like this:

```
Emily,25953
Hannah,23080
Madison,19967
Ashley,17997
Sarah,17697
...
Lamarion,5
Lamell,5
Lamine,5
...
Zykiera,5
Zyonna,5
Zyra,5
```

Using this file for generating female given names, will result in generating the name *Emily* 5000 times more often
than the name *Lamine*.

## Included data files

The library contains data files for english and german names.

### Build

```
mvn package
```

### Run

```
$ java -jar target/testdata-generator-1.0.jar
usage: java -jar testdata-generator-1.0.jar
 -h,--help             print usage help
 -i,--items <arg>      the number of items, that should be produced
 -l,--language <arg>   the language for which the test data is generated
                       (either "en" or "de")

$ java -jar target/testdata-generator-1.0.jar
Person{givenName='Thomas', surname='Schmidt', gender=male}

$ java -jar target/testdata-generator-1.0.jar -l en
Person{givenName='John', surname='Smith', gender=female}

$ java -jar target/testdata-generator-1.0.jar --language en --items 4
Person{givenName='Michael', surname='Miller', gender=male}
Person{givenName='John', surname='Watson', gender=male}
Person{givenName='Sarina', surname='Smith', gender=female}
Person{givenName='Patricia', surname='Doyle', gender=female}
```

### Open Issues

- Jackson stream output
- English surnames

