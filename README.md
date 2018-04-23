# OTM-harjoitustyö

Tämä on harjoitustyö kurssille ohjelmistotekniikan menetelmät.


# ScentUp

## Dokumentaatio

* [Vaatimusmäärittely](https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)
* [Työaikakirjanpito](https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)
* [Arkkitehtuurikuvaus](https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _ScentUp-1.0-SNAPSHOT.jar_


### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/mluukkai/OtmTodoApp/blob/master/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

### Releases

Ohjelmasta tehdyt julkaisut:

* [Ensimmäinen julkaisu](https://github.com/apndx/otm-harjoitustyo/releases/tag/0.1)


