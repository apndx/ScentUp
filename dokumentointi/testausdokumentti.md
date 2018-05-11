# Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkö- ja integraatiotestein JUnitilla sekä manuaalisesti tapahtunein järjestelmätason testein.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Automatisoitujen testien ytimen moudostavat sovelluslogiikkaa, eli pakkauksen [scentup.domain](https://github.com/apndx/otm-harjoitustyo/tree/master/ScentUp/src/main/java/scentup/domain) luokkia testaavat integraatio- ja yksikkötestit [DomainTest](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/test/java/DomainTest.java) ja [ServiceTest](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/test/java/ServiceTest.java) joiden määrittelevät testitapaukset simuloivat käyttöliittymän [ScentUpService](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/domain/ScentUpService.java)-olion avulla suorittamia toiminnallisuuksia.

Integraatiotestit käyttävät datan pysyväistallennukseen testeissä muodostettavaa testitietokantaa TestScentUp.db.

Sovelluslogiikkakerroksen luokille [User](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/domain/User.java), [Scent](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/domain/Scent.java) ja [UserScent](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/domain/UserScent.java) on tehty joitakin yksikkötestejä kattamaan tapauksia, joita integraatiotestit eivät kata (mm. olioiden _equals_- metodit).

### DAO-luokat

DAO-luokkien toiminnallisuus on testattu luomalla testeissä tilapäinen tiedosto hyödyntäen JUnitin [TemporaryFolder](https://junit.org/junit4/javadoc/4.12/org/junit/rules/TemporaryFolder.html)-ruleja.

### Testauskattavuus

Käyttöliittymäkerrosta lukuunottamatta sovelluksen testauksen rivikattavuus on 94 % ja haaraumakattavuus on 76 %.

<img scr= https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/jacoco.jpg" width="800">

Testaamatta jäivät osa equals-metodin haaroista, tilanteet joissa tietokanta on jostain syystä korruptoitunut, sekä osa catch-haaroista.

## Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.

### Asennus ja konfigurointi

Sovellus on haettu ja sitä on testattu [käyttöohjeen](https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/kayttoohje.md) kuvaamalla tavalla Linux-ympäristössä siten, että sovelluksen käynnistyshakemistossa on ollut käyttöohjeen kuvauksen mukainen config.properties-tiedosto ja tietokanta ScentUp.db. Sovellusta on testattu sekä tilanteissa, joissa käyttäjät ja työt tallettavat tiedostot ovat olleet olemassa ja joissa niitä ei ole ollut jolloin ohjelma on luonut ne itse.


### Toiminnallisuudet 

Kaikki [määrittelydokumentin](https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md) ja käyttöohjeen listaamat toiminnallisuudet on käyty läpi. Kaikkien toiminnallisuuksien yhteydessä on syötekentät yritetty täyttää myös virheellisillä arvoilla kuten tyhjillä.

## Sovellukseen jääneet laatuongelmat

Sovellus ei anna tällä hetkellä järkeviä virheilmoituksia, seuraavissa tilanteissa

* konfiguraation määrittelemiin tiedostoihin ei ole luku/kirjoitusoikeuksia
