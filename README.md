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
Lamine,5
...
Zyra,5
```

Using this file for generating female given names, will result in generating the name *Emily* 5000 times more often
than the name *Lamine*.

## Included data files

The library contains data files for english and german names.

Sources:
- English surnames: https://names.mongabay.com/most_common_surnames.htm
- English given names: https://www.ssa.gov/OACT/babynames/names.zip
- German surnames: Austria
- German given names: ?

### Build

```
mvn package
```

## Run

### Simple usages of the command line

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

$ java -jar target/testdata-generator-1.0.jar --language en --items 3
Person{givenName='Michael', surname='Miller', gender=male}
Person{givenName='John', surname='Watson', gender=male}
Person{givenName='Patricia', surname='Smith', gender=female}

$ java -jar target/testdata-generator-1.0.jar --language en --items 4 -- serialize CSV
index,id,surname,givenName,gender
,,Miller,Michael,male
,,Watson,John,male
,,Smith,Patricia,male
```

### Blockwise mode

For generating large amounts of data, the generated data is organized in directories and files.

```
java -jar target/testdata-generator-1.0.jar --items 100 --filesPerDirectory 10 --numberOfDirectories 5
=> Will generate 100 items per file, 10 files items per directory and 5 directories - in total 5000 persons 
java -jar target/testdata-generator-1.0.jar --items 1000 --filesPerDirectory 1000 --numberOfDirectories 10
=> Will generate 1000 items per file, 1000 files items per directory and 10 directories - in total 10 million persons 
=> If you tar this output, this will be approx. 560 MByte uncompressed and 70 MByte compressed.
```
### Open Issues

- Blockwise output
- English surnames

