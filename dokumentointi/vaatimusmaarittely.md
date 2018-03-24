# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellusta voi käyttää hajuveden valitsemiseen. Sovelluksella voi olla useampi rekisteröitynyt käyttäjä.

## Käyttäjät

Alkuvaiheessa sovelluksella on vain peruskäyttäjä.

## Käyttöliittymäluonnos

Sovellus koostuu kolmesta näkymästä:

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

   - Käyttäjä voi luoda käyttäjätunnuksen
      - Käyttäjätunnuksen pitää olla uniikki ja vähintään viisi merkkiä pitkä
   - Käyttäjä voi kirjautua järjestelmään
      - Kirjautuminen onnistuu syötettäessä käyttäjätunnus ja nimi lomakkeelle
      - Jos käyttäjää ei ole, tulee virheilmoitus

### Kirjautumisen jälkeen

   - Valittavana on näkymä, jossa voi lisätä uusia hajuvesiä
       - Hajuvedelle määritellään ominaisuusia: kategoria, vivahteita, vuodenaika, vuorokaudenaika ja sukupuoli
   - Käyttäjä näkee oman sivunsa jossa on aiemmin tehdyt käyttäjäkohtaiset valinnat, joissa näkyy valintapäivä
       - Valintoihin voi myös merkitä käyttäjäkohtaisen tiedon onko valinta huono, ok, vai suosikki
   - Toisessa näkymässä valintoja voi poistaa ja lisätä	
   - Käyttäjä voi kirjautua ulos

## Jatkokehitysideioita

   - Olemassaolevien hajuvesien ominaisuuksien muokkaus
   - Hajuvesien hakutoimintoja ominaisuuksien perusteella
