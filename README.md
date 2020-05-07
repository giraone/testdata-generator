# A test data generator for persons

This is a java library for generating realistic test data for person names and addresses.

## Goals

Imagine you need to have test data for 100.000 test persons (users, customers, employees, ...).
The typical first idea is to use "John Doe 1" to "John Doe 100000" or "Erwin Mustermann 1" to "Erwin Mustermann 100000".
But this not a realistic scenario. There are certainly much more people named "Thomas Smith" than "Lazarius Fulton-Hong"
or "Thomas Müller" than "Eusebius Wichteldorf". This non-uniform distribution of names can have an impact on the measured
performance in database queries. And having realistic names can help also in doing demos.

If your are in this situation, this library can help. It generates names from weighted datafiles.
Such a file, looks like this:

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
- German postal codes and corresponding cities (4.500) - collected from http://api.zippopotam.us (Last collection: January 2019)
  For the license, see [Open database License](https://opendatacommons.org/licenses/odbl/1.0/).
- German and british IBANs - collected from https://www.mobilefish.com/services/random_iban_generator/random_iban_generator.php (Last collection: June 2019)
 
### Build

```
mvn package
```

## Run

### Simple usages of the command line

```
$ java -jar target/testdata-generator.jar
usage: java -jar testdata-generator.jar
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
 -s,--serialize <arg>             the serialization mode: either json (default) or csv
 -w,--withIndex                   create also a sequence number (index) for each created item


$ java -jar target/testdata-generator.jar
[{"givenName":"Klaus-Dieter","surname":"Schmidt","gender":"m"}]

$ java -jar target/testdata-generator.jar -l en
[{"givenName":"John","surname":"Smith","gender":"m"}]

$ java -jar target/testdata-generator.jar --language en --numberOfItems 3
[
 {"givenName":"Michael","surname":"Miller","gender":"m"},
 {"givenName":"John","surname":"Watson","gender":"m"},
 {"givenName":"Patricia","surname":"Smith","gender":"f"}
]

$ java -jar target/testdata-generator.jar --language en --numberOfItems 4 --serialize CSV
index,id,surname,givenName,gender,
,,Miller,Michael,male,
,,Watson,John,male,
,,Smith,Patricia,male,

$ java -jar target/testdata-generator.jar --language en --numberOfItems 4 --withIndex --personId uuid --serialize CSV
0,"fbab5e53-aa9a-43e6-a76d-b70b24087460",Richardson,Allison,female,19550919
1,"6c7a824c-291b-4e50-8660-9d2e880098e8",Mitchell,Tamar,female,19301220
2,"a604083d-e472-44ab-8a8d-e985e5ed9880",Taylor,Martha,female,19751030
3,"5a69a0cb-6890-428d-bd57-9c701b84b9ef",Hall,Michael,male,19420103

$ java -jar target/testdata-generator.jar --withIndex --personId uuid
[{"givenName":"Heidemarie","surname":"Schmidt","gender":"f","index":1,"id":"26fb0ec5-7bef-4275-9f0a-d5fdcda9c349"}]

$ java -jar target/testdata-generator.jar --withIndex --additionalFields dateOfBirth
[{"givenName":"Heidemarie","surname":"Schmidt","gender":"f","index":1,"dateOfBirth":"19610722"}]

$ java -jar target/testdata-generator.jar --withIndex --additionalFields dateOfBirth,postalAddress
[{"givenName":"Corinna","surname":"Sauer","gender":"f","index":1,"postalCode":"91052", city":"Erlangen","streetAddress":"Am Weg 6b","dateOfBirth":"19670517"}]

$ java -jar target/testdata-generator.jar --withIndex --numberOfItems 10 --personId uuid --additionalFields iban,email --serialize csv
index,id,surname,givenName,gender,dateOfBirth,postalCode,city,streetAddress,companyId,email,iban
0,"a49cdedb-4d05-4c04-a647-4b09beb9df1e",Barth,Helene,f,,,,,,helene.0@barth.com,DE41257504926580317872
1,"d8fb04d2-b626-4f2b-a841-78388303cafa",Beier,Monika,f,,,,,,monika.beier.1@gmail.com,DE58612015305624307555
2,"d82e3596-266d-4910-8554-51f7e8aa39d7",Vogler,Klaus,m,,,,,,klaus.vogler.2@web.de,DE54300273809472349764
3,"fead0acd-8aef-472b-9219-ea6bd146da11",Binder,Anton,m,,,,,,anton.binder.3@gmx.de,DE39496711390956779690
4,"d93344e2-84c1-4f8c-aa0c-8c37c0e19f9c",Fiedler,Christa,f,,,,,,christa.fiedler.4@icloud.com,DE83146291913232022014
5,"30b6c5d5-6f75-4720-a0ee-fcf8fbbed1ed",Wächter,Carola,f,,,,,,carola.5@waechter.com,DE03826081195980036282
6,"0cafcd77-da87-40f2-ae3a-b7f9e0d66f8a",Jahns,Josef,m,,,,,,josef.jahns.6@gmx.de,DE93927265084807187860
7,"8ef8bfda-683a-4482-aaeb-d8827c5b2b7f",Sonntag,Antje,f,,,,,,antje.sonntag.7@aol.com,DE05333336144687922894
8,"4dee6a3d-b335-45fc-b0bb-bad5f990b516",Müller,Jens,m,,,,,,jens.mueller.8@gmx.de,DE86378874441491799823
9,"9ecf1dcf-b984-4cc1-9ffc-f77981ac3997",Schwarz,Stefan,m,,,,,,stefan.9@schwarz.com,DE32785807553633330048

```

### Assigned companies


 * This class adds a "companyId" string value to the person.
 * 
 
 
For building multi-tenancy scenarios, there is the *additionalField* `companyId`, that can be added to persons.
The companyId is a hierarchical key, e.g. `Alphabet/Google/Sales/US`. In this case it has 4 level:
- Holding
- Company
- Department
- Sub-Department
The naming of the key and the distribution of how many persons are assigned to each unit is configurable.
A simple one level JSON configuration file would be:
```json
[
 {
  "valuePattern": "%s-%08d",
  "sizeDistribution":
  [
   {
    "name": "s",
    "proportion": 0.8,
    "minimalNumberOfEmployees": 2,
    "maximalNumberOfEmployees": 9
   },
   {
    "name": "l",
    "proportion": 0.2,
    "minimalNumberOfEmployees": 10,
    "maximalNumberOfEmployees": 100
   }
  ]
 }
]
```
In this case, every person is assigned to a small company (2-9 employees) or a large company (10-100 employees).
80% of the persons are assigned to a small company and 20% to a large company. The companyId is sth. like
`s-01234567` or `l-01234568`. The above specification is also the default one.

```shell script
$ java -jar target/testdata-generator.jar --withIndex --numberOfItems 10 --companySpec companySpecLevel1.json --additionalFields company.id --serialize CSV
0,,Ziegler,Erna,f,,,,,,s-00003146
1,,Fischer,Jutta,f,,,,,,l-00000018
2,,Brandl,Erna,f,,,,,,s-00000498
3,,Erhardt,Christiane,f,,,,,,s-00003198
4,,Kienle,Mike,m,,,,,,s-00008822
5,,Fischer,Necati,m,,,,,,s-00006678
6,,Reinhard,Susanne,f,,,,,,l-00004189
7,,Hofmann,Sonja,f,,,,,,s-00008132
8,,Schmitz,Karl-Heinz,m,,,,,,s-00004198
9,,Gerber,Andrea,f,,,,,,s-00000726
```

An example for a two level company hierarchy is:
```json
[
 {
  "valuePattern": "%s-%08d",
  "sizeDistribution":
  [
   {
    "name": "s",
    "proportion": 0.8,
    "minimalNumberOfEmployees": 2,
    "maximalNumberOfEmployees": 9,
    "minimalNumberOfSubCompanies": 0,
    "maximalNumberOfSubCompanies": 2
   },
   {
    "name": "l",
    "proportion": 0.2,
    "minimalNumberOfEmployees": 10,
    "maximalNumberOfEmployees": 100,
    "minimalNumberOfSubCompanies": 0,
    "maximalNumberOfSubCompanies": 4
   }
  ]
 },
 {
  "valuePattern": "%s-%08d",
  "sizeDistribution":
  [
   {
    "name": "hq",
    "proportion": 0.6
   },
   {
    "name": "loc1",
    "proportion": 0.2,
   },
   {
    "name": "loc2",
    "proportion": 0.2
   }
  ]
 }
]
```

If one wants to create a `personnelNumber`, that is unique within a company, the following statement can be used

```shell script
$ java -jar target/testdata-generator.jar --withIndex --numberOfItems 10 --companySpec companySpecLevel1.json --additionalFields company.id,company.personnelNumber
```

### More predefined additional fields

The following additional fields are available:

- `dateOfBirth`: adds a date between 01.01.1930 and the actual date minus 16 years
- `placeOfBirth`: adds an additional city as place of birth in 50% of all cases
- `postalAddress`: adds postalCode, city and streetAddress (depending on the language) as additional fields
- `iban`: adds an IBAN (International Bank Account Number) as an additional field; the generated IBANs are random, but valid
- `email`: adds an email address that is build from given name and surname as as an additional field; if the `--withindex` option is used, the index is part of the email address to prevent duplicates
- `personnelNumber`: adds a personnel number of the form `[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]`, that ist unique with a company.

### Other random constant fields

Using `--constantFields` one can add additional fields, that are randomly chosen:

```shell script
java -jar target/testdata-generator.jar --withIndex --numberOfItems 10 --constantFields "eyeColor=red|blue,size=175|180|185,isAdmin=false|true,car=null|Audi|VW"
```

will output

```json
[{"gender":"m","eyeColor":"red","size":180,"surname":"Rauch","givenName":"Armin","index":0,"isAdmin":false},
{"gender":"f","eyeColor":"red","size":185,"car":"Audi","surname":"Straub","givenName":"Ingrid","index":1,"isAdmin":false},
{"gender":"m","eyeColor":"blue","size":175,"car":"VW","surname":"Meier","givenName":"Walter","index":2,"isAdmin":true}]
```

```shell script
java -jar target/testdata-generator.jar --withIndex --numberOfItems 10 --constantFields "academicTitle=null(75)|Dr.|Prof. Dr."
```

will add one the possibilities only in 25% of the output.

The syntax should be clear. Field specifications are separated by a comma, values are separated by `|`.
The first value defines the data type. If the first value is `null`, 50% of the value are not set.
If the first value is null(i), i percent of the values are not set.

### Other random constant fields

Using `--constantFields` one can add additional fields, that are randomly chosen:

### Block wise mode

For generating large amounts of data, the generated data is organized in directories and files.

```shell script
java -jar target/testdata-generator.jar --rootDirectory ../data-5K \
 --numberOfItems 100 --filesPerDirectory 10 --numberOfDirectories 5 \
 --withIndex --additionalFields dateOfBirth,postalAddress.postalCode,postalAddress.city,postalAddress.street,postalAddress.houseNumber,company.companyId,company.personnelNumber \
 --aliasJsonFile "alias-german.json"
 
=> Will generate 100 items per file, 10 files items per directory and 5 directories - in total 5000 persons 

time java -jar target/testdata-generator.jar --rootDirectory ../data-10M \
 --numberOfItems 1000 --filesPerDirectory 1000 --numberOfDirectories 10 \
 --withIndex --additionalFields dateOfBirth,postalAddress.postalCode,postalAddress.city,postalAddress.street,postalAddress.houseNumber,company.companyId,company.personnelNumber \
 --aliasJsonFile "alias-german.json"

Companies with size s:9016
Companies with size m:876
Companies with size l:108

real	1m40.090s
user	1m48.503s
sys	0m3.159s
 
=> Will generate 1000 items per file, 1000 files items per directory and 10 directories - in total 10 million persons.
=> The JSON files are approx. 200KByte each.
=> The approx. processing time is about 110 seconds on a "standard" PC (i7, SSD) - see time value above.
=> If you tar this output, this will be approx. 2.5 GByte uncompressed and 470 MByte compressed.

> tar cf data-10M.tar data-10M
> tar czf data-10M.tgz data-10M
> du -s data-10M*
2520388	data-10M
2517684	data-10M.tar
470652	data-10M.tgz
```

### Alias names for fields

Since version 1.3.0 it is possible to define a file to map the existing english attribute names, like *surname*
or *dateOfBirth* to other names, e.g. to german names like *nachname* or *geburtsdatum*.

### Open Issues

- English/USA city and postal codes are still missing

### Change Log

- Version 1.6.0 (07.05.2020)
  - New field phoneNumber
  - Company configuration changed to group company fields (company.id, company.personnelNumber)
  - Upgrade to Jackson 2.11.0
- Version 1.5.0 (05.04.2020)
  - Tests migration to JUnit 5 and AssertJ 3
  - Support for hierarchical companyIds, e.g. company, location, department
- Version 1.4.0 (30.03.2020)
  - Support for random fields
  - birthName and placeOfBirth added
- Version 1.3.0 (25.03.2020)
  - Allow definition of alias names for fields
  - Possibility to split *streetAddress* into *street* and *houseNumber* field
  - Refactoring (SonarLint rules)
- Version 1.2.0 (24.06.2019)
  - More additional fields added: iban, email
  - POM changed for stable JAR file
- Version 1.1.0 (27.05.2019)
  - Updated versions of Jackson and log4j-api to the latest versions without security vulnerabilities
