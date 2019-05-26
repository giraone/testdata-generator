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

See [src/main/resources/data](src/main/resources/data) folder.

The library contains data files for english and german names.

### Sources:

- English surnames (12.000) - collected from https://names.mongabay.com/most_common_surnames.htm with adapted Irish and Scottish prefixes Mc* and O'* (Last collection: January 2019)
- English given names (10.000 female, 6.900 male) - collected from https://www.ssa.gov/OACT/babynames/names.zip (Last collection: January 2019) 
- German surnames (10.000) - collected from http://www.namenforschung.net/dfd (Last collection: January 2019)
- German given names (1.000 female, 1.000 male) - source and time of collection unknown
- German postal codes and corrsponding cities (4.500) - collected from http://api.zippopotam.us (Last collection: January 2019)
  For the license, see [Open database License](https://opendatacommons.org/licenses/odbl/1.0/).
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
 -a,--additionalFields <arg>      comma separated list of additional fields
 -b,--startIndex                  if withIndex is used, this is the start index
 -d,--numberOfDirectories <arg>   the number of directories for splitting the output
 -f,--filesPerDirectory <arg>     the number of files per directory
 -l,--language <arg>              the language for which the test data is generated (either "en" or "de")
 -c,--country <arg>               the country for which the test data (postal addresses) is generated (currently only "DEU")
 -n,--numberOfItems <arg>         the number of items, that should be produced in total or in a file
 -p,--personId <arg>              type of additional person id: none, uuid, sequence
 -r,--rootDirectory               the root directory, where the output is written (default = .)
 -s,--serialize <arg>             the serialization mode: either json or csv
 -w,--withIndex                   create also a sequence number (index) for each created item


$ java -jar target/testdata-generator-1.0.jar
[{"givenName":"Klaus-Dieter","surname":"Schmidt","gender":"m"}]

$ java -jar target/testdata-generator-1.0.jar -l en
[{"givenName":"John","surname":"Smith","gender":"m"}]

$ java -jar target/testdata-generator-1.0.jar --language en --numberOfItems 3
[
 {"givenName":"Michael","surname":"Miller","gender":"m"},
 {"givenName":"John","surname":"Watson","gender":"m"},
 {"givenName":"Patricia","surname":"Smith","gender":"f"}
]

$ java -jar target/testdata-generator-1.0.jar --language en --numberOfItems 4 --serialize CSV
index,id,surname,givenName,gender,
,,Miller,Michael,male,
,,Watson,John,male,
,,Smith,Patricia,male,

$ java -jar target/testdata-generator-1.0.jar --language en --numberOfItems 4 --withIndex --personId uuid --serialize CSV
0,"fbab5e53-aa9a-43e6-a76d-b70b24087460",Richardson,Allison,female,19550919
1,"6c7a824c-291b-4e50-8660-9d2e880098e8",Mitchell,Tamar,female,19301220
2,"a604083d-e472-44ab-8a8d-e985e5ed9880",Taylor,Martha,female,19751030
3,"5a69a0cb-6890-428d-bd57-9c701b84b9ef",Hall,Michael,male,19420103

$ java -jar target/testdata-generator-1.0.jar --withIndex --personId uuid
[{"givenName":"Heidemarie","surname":"Schmidt","gender":"f","index":1,"id":"26fb0ec5-7bef-4275-9f0a-d5fdcda9c349"}]

$ java -jar target/testdata-generator-1.0.jar --withIndex --additionalField dateOfBirth
[{"givenName":"Heidemarie","surname":"Schmidt","gender":"f","index":1,"dateOfBirth":"19610722"}]

$ java -jar target/testdata-generator-1.0.jar --withIndex --additionalField dateOfBirth,postalAddress
[{"givenName":"Corinna","surname":"Sauer","gender":"f","index":1,"postalCode":"91052", city":"Erlangen","streetAddress":"Am Weg 6b","dateOfBirth":"19670517"}]
```

### Assigned companies

For building multi-tenancy scenarios, there is the *additionField* `companyId`.
This adds a "companyId" string value to the person.
The companies are of three types:
* `small`: this groups builds 90% of the companies with employee numbers from 2 to 20
* `medium`: this groups builds 9% of the companies with employee numbers from 20 to 500
* `large`: this groups builds 1% of the companies with employee numbers from 50 to 10000

```
$ java -jar target/testdata-generator-1.0.jar --withIndex --numberOfItems 10 --additionalField companyId --serialize CSV
0,,Ziegler,Erna,f,,,,,,s-00003146
1,,Fischer,Jutta,f,,,,,,l-00000018
2,,Brandl,Erna,f,,,,,,m-00000498
3,,Erhardt,Christiane,f,,,,,,s-00003198
4,,Kienle,Mike,m,,,,,,s-00008822
5,,Fischer,Necati,m,,,,,,s-00006678
6,,Reinhard,Susanne,f,,,,,,s-00004189
7,,Hofmann,Sonja,f,,,,,,s-00008132
8,,Schmitz,Karl-Heinz,m,,,,,,s-00004198
9,,Gerber,Andrea,f,,,,,,s-00000726
```

The *companyId* is currently prefix with the size category (l,m,s). E.g. *l-00000018* is a "large" company.

### Blockwise mode

For generating large amounts of data, the generated data is organized in directories and files.

```
java -jar target/testdata-generator-1.0.jar --rootDirectory ../data-5K \
 --numberOfItems 100 --filesPerDirectory 10 --numberOfDirectories 5 \
 --withIndex --additionalField dateOfBirth,postalAddress,companyId
 
=> Will generate 100 items per file, 10 files items per directory and 5 directories - in total 5000 persons 

time java -jar target/testdata-generator-1.0.jar --rootDirectory ../data-10M \
 --numberOfItems 1000 --filesPerDirectory 1000 --numberOfDirectories 10 \
 --withIndex --additionalField dateOfBirth,postalAddress,companyId
real	1m47.839s
user	1m49.925s
sys	0m3.475s
 
=> Will generate 1000 items per file, 1000 files items per directory and 10 directories - in total 10 million persons.
=> The JSON files are approx. 200KByte each.
=> The approx. prcoessing time is about 110 seconds on a "standard" PC (i7, SSD) - see time value above.
=> If you tar this output, this will be approx. 2 GByte uncompressed and 430 MByte compressed.

> tar cf data-10M.tar data-10M
> tar czf data-10M.tar data-10M
> du -s data-10M*
2000656	data-10M
2002244	data-10M.tar
429216	data-10M.tgz
```
### Open Issues

- English/USA city and postal codes are still missing

### Change Log

- Version 1.1.0 (27.05.2019)
  - Updated versions of Jackson and log4j-api to latest versions without security vulnerabilities
