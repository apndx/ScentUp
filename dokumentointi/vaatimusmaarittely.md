# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksessa on hajuvesitietokanta, josta käyttäjä voi valikoida oman kokoelmansa. Sovelluksella voi olla useampi rekisteröitynyt käyttäjä.

## Käyttäjät

Alkuvaiheessa sovelluksella on vain peruskäyttäjiä.

## Käyttöliittymäluonnos

Sovellus koostuu viidestä näkymästä:

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/kirjautumisnakyma.jpg" width="400">

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/uusikayttaja.jpg" width="400">

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/createscent.jpg" width="400">

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/loggedin.jpg" width="400">

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/browse.jpg" width="400">

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

   - Käyttäjä voi luoda käyttäjätunnuksen
      - Käyttäjätunnuksen pitää olla uniikki ja vähintään viisi merkkiä pitkä
   - Käyttäjä voi kirjautua järjestelmään
      - Kirjautuminen onnistuu syötettäessä käyttäjätunnus lomakkeelle
      - Jos käyttäjää ei ole, tulee virheilmoitus
   - Käyttäjä voi luoda tietokantaan uusia hajuvesiä, näihin on näkyvyys kaikilla käyttäjillä

### Kirjautumisen jälkeen

   - Kirjautumisen jälkeen tullaan käyttäjän omalle sivulle, jossa on listattu käyttäjän hajuvesikokoelma
       - Hajuvedestä on listattu nimi, merkki, vuorokaudenaika, vuodenaika ja sukupuoli
       - Listauksessa on myös vetovalikko jokaisen hajuveden kohdalla, siitä voi valita miten onnistunut valinta on kyseessä (dislike, neutral, love)
   - Näkymästä voi siirtyä kokoelmaan kuulumattomien hajuvesien selaustoimintoon (Browse). Sen listauksesta käyttäjä voi lisätä hajuvesiä omaan kokoelmaansa, ja sivulta on myös mahdollista siirtyä uuden hajuveden lisäyssivulle.	
   - Käyttäjä voi kirjautua ulos (ScentOut)

## Jatkokehitysideioita

   - Hajuvesiin voi lisätä kategorian, näitä varten tietokannassa on valmiina tietokantataulut Category ja ScentCategory
   - Olemassaolevien hajuvesien ominaisuuksien muokkaus
   - Hajuvesien hakutoimintoja eri ominaisuuksien perusteella
   - Turvallisempi kirjautuminen
   - Sovellukseen pääkäyttäjä, ja hajuvesien lisäys vaatii pääkäyttäjän oikeudet
   - Toiminto jolla hajuvesiä voi lisätä tietokantaan useita kerralla tiedostosta 
  
