# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattelee kolmitasoista kerrosarkkitehtuuria, ja koodin pakkausrakenne on seuraava:

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/ScentUpPackages.png" width="200">

Pakkaus scentup.ui sisältää JavaFX:llä toteutetun käyttöliittymän. Pakkaus scentup.domain sisältää sovelluslogiikan, ja scentup.dao tietojen pysyväistallennuksesta ja hausta vastaavan koodin.

## Käyttöliittymä

Käyttöliittymä sisältää viisi erillistä näkymää:
 - kirjautumissivu, tästä voi myös siirtyä luomaan uuden käyttäjän tai hajuveden
 - uuden käyttäjän luominen
 - uuden hajuveden lisääminen, nämä ovat kaikkien käyttäjien käytettävissä
 - kirjautumisen jälkeen käyttäjä näkee listauksen valitsemistaan hajuvesistä, sivulta voi myös siirtyä hakemaan uusia hajuvesiä
 - käyttäjäsivulta pääsee selaamaan hajuvesiä, joita ei ole vielä käyttäjän kokoelmassa, näistä käyttäjä voi valita omaan kokoelmaansa uuden hajuveden

Jokainen näistä on toteutettu omana Scene -oliona. Näkymistä yksi kerrallaan on näkyvissä, eli sijoitettuna sovelluksen stageen. Käyttöliittyä on rakennettu ohjelmallisesti luokassa scentup.ui.ScentUpGui.

Käyttöliittymä kutsuu sopivin parametrein sovelluslogiikan toteuttavan olion _scentUpServicen_ metodeja.

Kun sovelluksessa käyttäjän hajuvesikokoelman tilanne muuttuu, eli kun käyttäjä kirjautuu,  uusi hajuvesi valitaan kokoelmaan, tai hajuveden preferenssiä muututaan, kutsutaan sovelluksen metodeja redrawUserHasUserScentsList ja redrawUserHasNotScentsList. Näin listausnäkymät renderöityvät uudelleen sen mukaan, kumpaan listaukseen hajuvesi kuuluu.

## Sovelluslogiikka

Sovelluksen loogisen datamallin muodostavat luokat User, Scent ja UserScent. Luokat kuvaavat käyttäjiä, hajuvesiä, ja käyttäjän valitsemia hajuvesiä.

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/ScentLuokkakaavio.png" width="500">

Toiminnallisista kokonaisuuksista vastaa luokka ScentUpService. Luokka tarjoaa käyttöliittymän toiminnoille metodit. Näitä ovat esim:

 - boolean createUser(String username, String name)
 - boolean doesScentExist(String scentName, String brandName)
 - void createScent(Scent scent)
 - boolean createUserScent(Integer userId, Integer scentIdFor, Date dateNow, Integer pref, Integer act)

 - List<Scent> getScentsUserHas()
 - List<Scent> getScentsUserHasNot() 

 - boolean login(String username)
 - User getLoggedIn()
 - void logout() 

_ScentUpService_ pääsee käsiksi käyttäjiin ja hajuvesiin tietojen tallennuksesta vastaavien pakkauksessa _scentup.dao_ sijaitsevien rajapinnat _UDao_, SDao_ ja _USDao_ toteuttavien _UserDao_, _ScentDao_ ja _UserScentDao_ luokkien kautta. Luokkien toteutukset [injektoidaan](https://en.wikipedia.org/wiki/Dependency_injection) sovelluslogiikalle konstruktorikutsun yhteydessä.

Ohjelman osien suhdetta kuvaava luokka/pakkauskaavio:

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/ScentUp.png" width="700">

## Tietojen pysyväistallennus

Pakkauksen _scentup.dao_ luokat _ScentDao_, _UserDao_ ja _UserScentDao_ huolehtivat tietojen tallettamisesta tietokantaan.

### Tietokanta

Sovellus tallettaa käyttäjien ja hajuvesien tiedot tietokantaan. Tietokanta on kuvattu oheisessa tietokantakaaviossa:

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/scentUp_database_tables.png" width="700">

Tietokannassa on jo valmiina taulut ScentCategory ja Category ohjelman mahdollista jatkokehitystä varten.

### Päätoiminnallisuudet

Kuvataan seuraavaksi sovelluksen toimintalogiikka muutaman päätoiminnallisuuden osalta sekvenssikaaviona.

#### Käyttäjän kirjautuminen

Kun kirjautumisnäkymässä on syötekenttään kirjoitettu käyttäjätunnus ja klikataan painiketta _ScentIn_ etenee sovelluksen kontrolli seuraavasti:

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/ScentUpLoginGUI.png" width="1000">

Painikkeen painamiseen reagoiva [tapahtumankäsittelijä](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/ui/ScentUpGui.java#L247) kutsuu sovelluslogiikan _scentUpService_ metodia [login](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/domain/ScentUpService.java#L188) antaen parametrina kirjautuneen käyttäjätunnuksen. Sovelluslogiikka selvittää _userDaon_:n avulla onko käyttäjätunnus olemassa. Jos on, eli kirjautuminen onnistuu, vaihtaa käyttöliittymä näkymäksi _sceneLoggedIn_ joka on sovelluksen päänäkymä. Näkymään renderöidään kirjautuneen käyttäjän hajuvesikokoeman listana.

#### Uuden käyttäjän luominen

Kun uuden käyttäjän luomisnäkymässä on syötetty käyttäjätunnus joka ei ole jo käytössä sekä nimi ja klikataan painiketta _Add a new User_ etenee sovelluksen kontrolli seuraavasti (tässä kaaviossa on daon ja tietokannan välinen toiminta abstrahoitu pois):

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/ScentUpNewUserSec.png" width="800">

[Tapahtumakäsittelijä](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/ui/ScentUpGui.java#L316) kutsuu sovelluslogiikan metodia [createUser](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/domain/ScentUpService.java#L140) antaen parametriksi luotavan käyttäjän tiedot. Sovelluslogiikka selvittää _userDao_:n avulla onko käyttäjätunnus olemassa. Jos ei, eli uuden käyttäjän luominen on mahdollista, luo sovelluslogiikka _User_-olion ja tallettaa sen kutsumalla _userDao_:n metodia _saveOrNot_. Tästä seurauksena on se, että käyttöliittymä vaihtaa näkymäksi _loginScenen_ eli kirjautumisnäkymän.

#### Uuden hajuveden luominen

Kun uuden hajuveden luomisnäkymässä on syötetty hajuveden nimi ja merkki, valittu vaihtoehdoista hajuveden ominaisuudet ja klikattu nappia _I'm ready, let's do it!_ etenee sovelluksen kontrolli seuraavasti (tässä kaaviossa on daon ja tietokannan välinen toiminta abstrahoitu pois):

<img src="https://github.com/apndx/otm-harjoitustyo/blob/master/dokumentointi/ScentUpNewScentSec.png" width="800">

[Tapahtumakäsittelijä](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/ui/ScentUpGui.java#L418) kutsuu sovelluslogiikan metodia [doesScentExist](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/domain/ScentUpService.java#L161) ja ScentDao tarkistaa nimen ja merkin perusteella onko hajuvesi jo tietokannassa. Kun on varmistettu, että hajuvettä ei olla lisäämässä tuplana, tapahtumakäsittelijä luo scent-olion, ja kutsuu sovelluslogiikan metodia [createScent](https://github.com/apndx/otm-harjoitustyo/blob/master/ScentUp/src/main/java/scentup/domain/ScentUpService.java#L176) antaen scent olion tälle parametrina. _ScentDao_ tallentaa tämän jälkeen hajuveden tietokantaan käyttäen metodia _saveOrNot_. Tämän jälkeen tapahtumakäsittelijä palauttaa loginScene -näkymämän ja ilmoittaa _new scent added_.

#### Muut toiminnallisuudet

Sama periaate toistoo sovelluksen kaikissa toiminnallisuuksissa, käyttöliittymän tapahtumakäsittelijä kutsuu sopivaa sovelluslogiikan metodia, sovelluslogiikka päivittää listoja,  kirjautuneen käyttäjän tilaa tai userScent -hajuveden ominaisuuksia. Toiminnon päättyessä palataan käyttäliittymään ja päivitetään tarvittaessa listauksia sekä aktiivinen näkyvä.

## Ohjelman rakenteeseen jääneet heikkoudet

### Käyttöliittymä

Graafinen käyttöliittymä on toteutettu määrittelemällä lähes koko käyttöliittymän struktuuri luokan _ScentUpGUi_ metodissa _start_. Toiminnallisuuksia olisi hyvä erottaa omiin metodeihin tai luokkiin. Yksi mahdollisuus olisi korjata nykyinen rakenne FXML-määrittelyllä. Lisäksi käyttöliittymä voisi olla paremmin eristetty, tällä hetkellä hajuveden luomistoiminnossa käyttöliittymä osallistuu olion luomiseen joka voitaisiin tehdä ennemmin kokokonaan ohjelmalogiikan puolella. Lisäksi luokan ScentUpService olisi voinut jakaa useampaan luokkaan, jotka olisivat käsitelleet erikseen käyttäjään, hajuveteen ja käyttäjän hajuveteen liittyviä toimintoja.




