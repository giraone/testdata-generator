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
Mary,25000
Emily,24953
Hannah,23080
Sarah,22697
...
Lamine,5
```

Using this file for generating female given names, will result in generating the name *Mary* 5000 times more often
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
 -h,--help                        print usage help
 -w,--withIndex                   create also a sequence number (index)
 -d,--numberOfDirectories <arg>   the number of directories
 -f,--filesPerDirectory <arg>     the number of files per directory
 -i,--items <arg>                 the number of items, that should be produced in total or in a file
 -l,--language <arg>              the language for which the test data is generated (either "en" or "de")
 -p,--personId <arg>              type of additional person id (none, uuid, sequence)
 -s,--serialize <arg>             the serialization mode: either json or csv


$ java -jar target/testdata-generator-1.0.jar
[{"givenName":"Klaus-Dieter","surname":"Schmidt","gender":"m"}]

$ java -jar target/testdata-generator-1.0.jar -l en
[{"givenName":"John","surname":"Smith","gender":"m"}]

$ java -jar target/testdata-generator-1.0.jar --language en --items 3
[
 {"givenName":"Michael","surname":"Miller","gender":"m"},
 {"givenName":"John","surname":"Watson","gender":"m"},
 {"givenName":"Patricia","surname":"Smith","gender":"f"}
]

$ java -jar target/testdata-generator-1.0.jar --language en --items 4 --serialize CSV
index,id,surname,givenName,gender,
,,Miller,Michael,male,
,,Watson,John,male,
,,Smith,Patricia,male,

$ java -jar target/testdata-generator-1.0.jar --language en --items 4 --withIndex --personId uuid --serialize CSV
0,"fbab5e53-aa9a-43e6-a76d-b70b24087460",Richardson,Allison,female,19550919
1,"6c7a824c-291b-4e50-8660-9d2e880098e8",Mitchell,Tamar,female,19301220
2,"a604083d-e472-44ab-8a8d-e985e5ed9880",Taylor,Martha,female,19751030
3,"5a69a0cb-6890-428d-bd57-9c701b84b9ef",Hall,Michael,male,19420103

$ java -jar target/testdata-generator-1.0.jar --withIndex --personId uuid
[{"givenName":"Heidemarie","surname":"Schmidt","gender":"f","index":1,"id":"26fb0ec5-7bef-4275-9f0a-d5fdcda9c349"}]

$ java -jar target/testdata-generator-1.0.jar --withIndex --additionalField dateOfBirth
[{"givenName":"Heidemarie","surname":"Schmidt","gender":"f","index":1,"dateOfBirth":"19610722"}]
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

