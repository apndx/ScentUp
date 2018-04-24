# Arkkitehtuurikuvaus

Ohjelman rakenne noudattelee kolmitasoista kerrosarkkitehtuuria.

Ohjelman osien suhdetta kuvaava luokka/pakkauskaavio:

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/ScentUp.png" width="750">

## Tietojen pysyväistallennus

Pakkauksen _scentup.dao_ luokat _ScentDao_, _UserDao_ ja _UserScentDao_ huolehtivat tietojen tallettamisesta tietokantaan.

### Tietokanta

Sovellus tallettaa käyttäjien ja hajuvesien tiedot tietokantaan. 

### Päätoiminnallisuudet

Kuvataan seuraavaksi sovelluksen toimintalogiikka muutaman päätoiminnallisuuden osalta sekvenssikaaviona.

#### Käyttäjän kirjautuminen

Tekstikäyttöliittymässä aukeaa ensin päävalikko:

What to do next?
1. Create a new User
2. Add a new Scent
3. ScentIn
4. ScentOut

Kun käyttäjä kirjoittaa "3", etenee ohjelman suoritus seuraavasti:

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/ScentUpLogin.png" width="1000">

Käyttäjän syötteeseen reagoiva tekstikäyttöliittymä kutsuu sovelluslogiikan metodia login antaen parametriksi kirjautuneen käyttäjätunnuksen.
Sovelluslogiikka selvittää userDao:n avulla, onko käyttäjätunnus olevmassa. Jos on, kirjautuminen onnistuu.
Tämän jälkeen tekstikäyttöliittymä toivottaa käyttäjän tervetulleeksi, ja tulostaa seuraavan valikon:

What to do next?
1. Print what I have chosen
2. Browse and add to collection (under construction)
3. Logout
