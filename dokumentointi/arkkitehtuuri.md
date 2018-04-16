# Arkkitehtuurikuvaus

Ohjelman rakenne noudattelee kolmitasoista kerrosarkkitehtuuria.

Ohjelman osien suhdetta kuvaava luokka/pakkauskaavio:

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/ScentUp.png" width="600">

## Tietojen pysyväistallennus

Pakkauksen _scentup.dao_ luokat _ScentDao_, _UserDao_ ja _UserScentDao_ huolehtivat tietojen tallettamisesta tietokantaan.

### Tietokanta

Sovellus tallettaa käyttäjien ja hajuvesien tiedot tietokantaan. 
