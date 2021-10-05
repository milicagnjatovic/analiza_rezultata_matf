#  _Analiza rezultata MATF-a_
---
## Ideja
Ideja je bila što izražajnije prikazati kako studenti prolaza na ispitima, ukrtako dati odgovor na naredna pitanja:
* Kolika je prolaznost predmeta?
* Koliki je prosek ocena studenata koji su položili?
* Koliko studenata sluša predmet prvi put?
* Koja je raspodela ocena?
* Da li ocena zavisi od toga da li se predmet sluša prvi put
* ...
---
## Uputstvo
U repozitorijumu nisu dati stvarni podaci zbog zašite privatnosti!
U folderu _baza_ je dat skript create.sh za kreiranje neophodnih tabela. Skript dummy_data.sh sadrži primer unosa podataka.
Tabele su u narednoj relaciji:
![diagram](https://github.com/milicagnjatovic/analiza_rezultata_matf/blob/master/baza/diagram.png)

Pre pokretanja aplikacije pokrenuti DB2 server kmandom db2start!

Nakon pokretanja program izgleda ovako:
![home](https://github.com/milicagnjatovic/analiza_rezultata_matf/blob/master/img/example1.png)

Dugme "izvestaj" će prikazati poređenje informacija o predmetima (nazivi predmeta su precrtani):
![izvestaj](https://github.com/milicagnjatovic/analiza_rezultata_matf/blob/master/img/example4.png)

Pri unosu ispita se mogu uneti ispiti za novi predmet (tada je neophodno popuniti sve inforomacije za taj predmet) ili izabrati unos ispita za neki od većpostojećih predmeta (tada se predmet bira iz padajućeg menija):
![unos ispita](https://github.com/milicagnjatovic/analiza_rezultata_matf/blob/master/img/example3.png)

Postoji opcija za prikaz inforamcija o ispitima studenta sa unetim indeksom:
![indeks](https://github.com/milicagnjatovic/analiza_rezultata_matf/blob/master/img/example2.png)

---
## Programske tehnlogije
* programski jezik Java sa bibliotekom JavaFX za vizuelizaciju
* DB2
* Hibernate alat za rad sa bazom podataka

Okruženja IntelliJ IDEA i SceneBuilder.
Rađeno na operativnom sistemu Linux.
