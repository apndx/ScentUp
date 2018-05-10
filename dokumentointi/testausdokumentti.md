# Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkö- ja integraatiotestein JUnitilla sekä manuaalisesti tapahtunein järjestelmätason testein.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Automatisoitujen testien ytimen moudostavat sovelluslogiikkaa, eli pakkauksen [scentup.domain](https://github.com/apndx/otm-harjoitustyo/tree/master/ScentUp/src/main/java/scentup/domain) luokkia testaavat integraatio- ja yksikkötestit [DomainTest](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/test/java/DomainTest.java) ja [ServiceTest] (https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/test/java/ServiceTest.java) joiden määrittelevät testitapaukset simuloivat käyttöliittymän [ScentUpService](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/domain/ScentUpService.java)-olion avulla suorittamia toiminnallisuuksia.

Integraatiotestit käyttävät datan pysyväistallennukseen testeissä muodostettavaa testitietokantaa TestScentUp.db.

Sovelluslogiikkakerroksen luokille [User](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/domain/User.java), [Scent](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/domain/Scent.java) ja [UserScent](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/domain/UserScent.java) on tehty joitakin yksittötestejä kattamaan tapauksia, joita integraatiotestit eivät kata (mm. olioiden _equal_s- metodit).

### DAO-luokat

DAO-luokkien toiminnallisuus on testattu luomalla testeissä tilapäinen tiedosto hyödyntäen JUnitin [TemporaryFolder](https://junit.org/junit4/javadoc/4.12/org/junit/rules/TemporaryFolder.html)-ruleja.

### Testauskattavuus

Käyttöliittymäkerrosta lukuunottamatta sovelluksen testauksen rivikattavuus on 86 % ja haaraumakattavuus on 70 %.

