# Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkö- ja integraatiotestein JUnitilla sekä manuaalisesti tapahtunein järjestelmätason testein.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Automatisoitujen testien ytimen moudostavat sovelluslogiikkaa, eli pakkauksen [scentup.domain](https://github.com/apndx/otm-harjoitustyo/tree/master/ScentUp/src/main/java/scentup/domain) luokkia testaavat integraatio- ja yksikkötestit [DomainTest](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/test/java/DomainTest.java) ja [ServiceTest] (https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/test/java/ServiceTest.java) joiden määrittelevät testitapaukset simuloivat käyttöliittymän [ScentUpService](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/domain/ScentUpService.java)-olion avulla suorittamia toiminnallisuuksia.




