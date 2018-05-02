# Käyttöohje


## Konfigurointi

Jos haluat hyödyntää tietokantaan valmiiksi lisättyjä hajuvesiä, voit ottaa mukaan tiedoston ScentUp.db joka löytyy zip-paketista. 
Oikea paikka tiedostolle ScentUp.db on ohjelman juurikansiossa. Jos tämä tiedosto puuttuu, luodaan siitä uusi versio ensimmäisen käynnistyksen yhteydessä.

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 

```
java -jar todoapp.jar
```

## Kirjautuminen

Sovellus käynnistyy kirjautumisnäkymään:

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/kirjautumisnakyma.jpg" width="400">

Kirjautuminen onnistuu kirjoittamalla olemassaoleva käyttäjätunnus syötekenttään ja painamalla ScentIn.

## Uuden käyttäjän luominen

Kirjautumisnäkymästä on mahdollista siirtyä uuden käyttäjän luomisnäkymään painikkeella _Add a new User_.

Uusi käyttäjä luodaan syöttämällä tiedot syötekenttiin ja painamalla _Add a new User_.

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/uusikayttaja.jpg" width="400">

Jos käyttäjän luominen onnistuu, palataan kirjautumisnäkymään.

## Uuden hajuveden luominen

Kirjautumisnäkymästä on mahdollista siirtyä myös uuden käyttäjän luomisnäkymään painikkeella _Add a new Scent_.

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/createscent.jpg" width="400">

Näkymässä uuden hajuveden luominen onnistuu kirjoittamalla hajuveden nimi ja merkki, sekä valitsemalla hajuvedelle suositeltava käyttöajankohta (vuorokauden- ja vuodenaika), sekä onko kyseessä naisten, miesten vai unisex-hajuvesi. Hajuvesi tallennetaan painikkeella _I'm ready, let's do it!_

Jos hajuveden luominen onnistuu, palataan kirjautumisnäkymään.

## Kirjautumisen jälkeen

Kun käyttäjä on kirjautunut ohjelmaan olemassaolevalla käyttäjätunnuksella, avautuu näkymä käyttäjän itselleen valitsemista hajuvesistä:

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/loggedin.jpg" width="400">

Tästä näkymästä voi joko kirjautua ulos painikkeella _ScentOut_ tai siirtyä valitsemaan kokoelmaan lisää hajuvesiä painikkeella _Browse_.

## Uusien hajuvesien valitseminen käyttäjän kokoelmaan

Tämän näkymän listaukseen tulevat ne tietokantaan lisätyt hajuvedet, joita käyttäjällä ei vielä ole. Näkymästä voi palata käyttäjän omaan näkymään painikkeella  _back_, tai listauksesta voi valita itselleen uuden hajuveden klikkaamalla painiketta _I want_ hajuveden kohdalla. Jos hajuveden lisäys kokoelmaan onnistuu, palataan käyttäjän omaan näkymään.

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/browse.jpg" width="400">

