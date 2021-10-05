-- POKRETANJE: db2 -td@ -f dummy_data.sql


INSERT INTO MG.PREDMET (NAZIV, OZNAKA, SEMESTAR, KATEDRA, ESPB)
	VALUES ('Predmet 1', 'P1', 5, 'I',6),
		('Predmet 2', 'P2', 5, 'I',7),
		('Predmet 3', 'P3', 5, 'M',8),
		('Predmet 4', 'P4', 5, 'M',9),
		('Predmet 5', 'P5', 5, 'M',10)@


INSERT INTO MG.STUDENT
	VALUES (1, 'IME1', 'PREZIME1', 'M'),
		(2, 'IME2', 'PREZIME2', 'M'),
		(3, 'IME3', 'PREZIME3', 'Z'),
		(4, 'IME4', 'PREZIME4', 'Z')
		@


-- ne moze u istoj transakciji da se unese jedan isti dva puta, ali ako se unese ponovo u drugoj transakciji samo ce ga prepisati
-- nije problem jer su podaci takvi
-- id predmeta se generise automtski, pa treba proveriti da li u bazi postoje predmei sa id-em 100, 102 i 104, ako ne postoje izmeniti id tako da odgovara predmetu
INSERT INTO MG.ISPIT
	VALUES (1, 100, 10, 5, NULL),
		(2, 102, 10, 5, NULL),
		--(2, 102, 100, 10, NULL),
		(1, 104, 80, 7, NULL)@


