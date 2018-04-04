# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellusta voi käyttää hajuveden valitsemiseen. Sovelluksella voi olla useampi rekisteröitynyt käyttäjä.

## Käyttäjät

Alkuvaiheessa sovelluksella on vain peruskäyttäjä.

## Käyttöliittymäluonnos

Sovellus koostuu viidestä näkymästä:

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/vaatimusmaar.jpg" width="750">

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

   - Käyttäjä voi luoda käyttäjätunnuksen
      - Käyttäjätunnuksen pitää olla uniikki ja vähintään viisi merkkiä pitkä
   - Käyttäjä voi kirjautua järjestelmään
      - Kirjautuminen onnistuu syötettäessä käyttäjätunnus lomakkeelle
      - Jos käyttäjää ei ole, tulee virheilmoitus

### Kirjautumisen jälkeen

   - Valittavana on näkymä, jossa voi lisätä uusia hajuvesiä
       - Hajuvedelle määritellään nimi, merkki, vuorokaudenaika, vuodenaika, kategoria ja sukupuoli
   - Käyttäjä näkee oman sivunsa jossa on aiemmin tehdyt käyttäjäkohtaiset valinnat, joissa näkyy valintapäivä
       - Valintoihin voi myös merkitä käyttäjäkohtaisen tiedon onko valinta huono, ok, vai suosikki
   - Toisessa näkymässä valintoja voi poistaa ja lisätä	
   - Käyttäjä voi kirjautua ulos

## Jatkokehitysideioita

   - Hajuvesiin voi lisätä tuoksuominaisuuksia, näitä varten tehdään tietokantataulut valmiiksi
   - Olemassaolevien hajuvesien ominaisuuksien muokkaus
   - Hajuvesien hakutoimintoja eri ominaisuuksien perusteella
   - Turvallisempi kirjautuminen
   - Sovellukseen pääkäyttäjä, ja hajuvesien lisäys vaatii pääkäyttäjän oikeudet
