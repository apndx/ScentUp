# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattelee kolmitasoista kerrosarkkitehtuuria, ja koodin pakkausrakenne on seuraava:

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/ScentUpPackages.png" width="300">

Pakkaus scentup.ui sisältää JavaFX:llä toteutetun käyttöliittymän, lisäksi löytyy vaihtoehtoinen tekstikäyttöliittymä.
Käyttöliittymän voi valita kommentoimalla Main luokasta piiloon sen käyttöliittymän aloituskäskyt, jota ei haluta käyttää.

Pakkaus scentup.domain sisältää sovelluslogiikan, ja scentup.dao tietojen pysyväistallennuksesta ja hausta vastaavan koodin.

## Käyttöliittymä

Käyttöliittymä sisältää viisi erillistä näkymää:
 - kirjautumissivu, tästä voi myös siirtyä luomaan uuden käyttäjän tai hajuveden
 - uuden käyttäjän luominen
 - uuden hajuveden lisääminen, nämä ovat kaikkien käyttäjien käytettävissä
 - kirjautumisen jälkeen käyttäjä näkee listauksen valitsemistaan hajuvesistä, sivulta voi myös siirtyä hakemaan uusia hajuvesiä
 - käyttäjäsivulta pääsee selaamaan hajuvesiä, joita ei ole vielä käyttäjän kokoelmassa, näistä käyttäjä voi valita omaan kokoelmaansa uuden hajuveden

Jokainen näistä on toteutettu omana Scene -oliona.  Näkymistä yksi kerrallaan on näkyvissä, eli sijoitettuna sovelluksen stageen. Käyttöliittyä on rakennettu ohjelmallisesti luokassa scentup.ui.ScentUpGui.

Käyttöliittymä kutsuu sopivin parametrein sovelluslogiikan toteuttavan olion scentUpServicen metodeja.

Kun sovelluksessa käyttäjän hajuvesikokoelman tilanne muuttuu, eli kun käyttäjä kirjautuu, tai uusi hajuvesi valitaan kokoelmaa, kutsutaan sovelluksen metodeja redrawUserHasScentsList ja redrawUserHasNotScentsList. Näin listausnäkyvät renderöityvät uudelleen sen mukaan, kumpaan listaukseen hajuvesi kuuluu.

## Sovelluslogiikka

Sovelluksen loogisen datamallin muodostavat luokat User, Scent ja UserScent. Luokat kuvaavat käyttäjiä, hajuvesiä, ja käyttäjän valitsemia hajuvesiä.

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/ScentLuokkakaavio.png" width="750">

Toiminnallisista kokonaisuuksista vastaa luokka ScentUpService. Luokka tarjoaa käyttöliittymän toiminnoille metodit. Näitä ovat esim:
 - boolean createUserScent(Integer userId, Integer scentIdFor, Date dateNow, Integer pref, Integer act)
 - User getLoggedIn()
 - List<Scent> getScentsUserHas()
 - List<Scent> getScentsUserHasNot() 
 - boolean createUser(String username, String name)
 - boolean doesScentExist(String scentName, String brandName)
 - void createScent(Scent scent)
 - boolean login(String username)
 - void logout() 

_ScentUpService_ pääsee käsiksi käyttäjiin ja hajuvesiin tietojen tallennuksesta vastaavien pakkauksessa _scentup.dao_ sijaitsevien rajapinnat toteuttavien _ScentDao_ , _UserDao_ ja _UserScentDao_ luokkien kautta. Luokkien toteutuksen [injektoidaan](https://en.wikipedia.org/wiki/Dependency_injection) sovelluslogiikalle konstruktorikutsun yhteydessä.

Ohjelman osien suhdetta kuvaava luokka/pakkauskaavio:

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/ScentUp.png" width="750">

## Tietojen pysyväistallennus

Pakkauksen _scentup.dao_ luokat _ScentDao_, _UserDao_ ja _UserScentDao_ huolehtivat tietojen tallettamisesta tietokantaan.

### Tietokanta

Sovellus tallettaa käyttäjien ja hajuvesien tiedot tietokantaan. Tietokanta on kuvattu oheisessa tietokantakaaviossa:

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/scentUp_database_tables.png" width="750">

Tietokannassa on jo valmiina taulut ScentCategory ja Category ohjelman mahdollista jatkokehitystä varten.

### Päätoiminnallisuudet

Kuvataan seuraavaksi sovelluksen toimintalogiikka muutaman päätoiminnallisuuden osalta sekvenssikaaviona.

#### Käyttäjän kirjautuminen tekstikäyttöliittymän kautta

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
