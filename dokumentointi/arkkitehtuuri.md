# Arkkitehtuurikuvaus

Ohjelman rakenne noudattelee kolmitasoista kerrosarkkitehtuuria.

Ohjelman osien suhdetta kuvaava luokka/pakkauskaavio:

## Tietojen pysyväistallennus

Pakkauksen _scentup.dao_ luokat _ScentDao_, _UserDao_ ja _UserScentDao_ huolehtivat tietojen tallettamisesta tietokantaan.

### Tietokanta

Sovellus tallettaa käyttäjien ja hajuvesien tiedot tietokantaan. 
