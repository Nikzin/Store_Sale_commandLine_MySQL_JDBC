
-- CREATE DATABASE Webbutiken;
DROP DATABASE IF EXISTS Webbutiken;
CREATE DATABASE Webbutiken;
USE Webbutiken;

CREATE Table Kund
(
	Personnummer CHAR(10) PRIMARY KEY,
	Fornamn VARCHAR(50) NOT NULL,
	Efternamn VARCHAR(50) NOT NULL,

	Epost VARCHAR(50) NOT NULL,
	Ort VARCHAR(20) NOT NULL
	);

CREATE TABLE Orders
(
	OrderNr INT AUTO_INCREMENT PRIMARY KEY,
	-- OrderNr CHAR(6) PRIMARY KEY,
	KundNr CHAR (10),
	Datum DATE,
	Tid TIME,
	FOREIGN KEY (KundNr) REFERENCES  Kund (Personnummer)
);

CREATE TABLE Kategori
(
	Kod Char (6)  PRIMARY KEY,
	Namn VARCHAR(50)
    );

CREATE TABLE Produkt
(
	Kod CHAR(6) PRIMARY KEY,
	Namn VARCHAR(50) NOT NULL,
	Size VARCHAR(20),
	Pris INT NOT NULL,
  Tilgangligt INT,
	CHECK (Tilgangligt>=0)

);

CREATE TABLE ProduktKategori
(
	Produktkod Char (6),
	Kategorikod Char(6),
	PRIMARY KEY (Produktkod, Kategorikod),
	FOREIGN KEY (Produktkod ) REFERENCES Produkt(kod),
	FOREIGN KEY (Kategorikod ) REFERENCES Kategori(kod)
    );


CREATE TABLE OrderProdukt
(
	OrderNr INT,
	Produktskod CHAR(6),
	Antal int,
	PRIMARY KEY (OrderNr, Produktskod),
	FOREIGN KEY (OrderNr) REFERENCES Orders(OrderNr),
	FOREIGN KEY (Produktskod) REFERENCES Produkt(Kod)

);

CREATE TABLE ProduktsBetyg
(
	Personnummer CHAR(10) ,
	Produktskod CHAR(6),
	Betyg VARCHAR(20),
	Kommentar VARCHAR(500),
	PRIMARY KEY (Personnummer, Produktskod),
	FOREIGN KEY (Personnummer) REFERENCES Kund(Personnummer),
	FOREIGN KEY (Produktskod) REFERENCES  Produkt(Kod)
);

CREATE TABLE Slutilager
(
	Datum DATE,
	produktnummer CHAR(6)

);


--
--
--
--
--

-- Skapa Kunder
INSERT INTO Kund VALUES('9903137867', 'Sven', 'Svenson', 'sven@svenson.se', 'Stockholm');
INSERT INTO Kund VALUES('2210033765', 'Thor', 'Odinson', 'thor@odinson.se', 'Uppsala');
INSERT INTO Kund VALUES('1110036665', 'Odin', 'Thorson', 'odin@thorson.se', 'Uppsala');
INSERT INTO Kund VALUES('1209036225', 'Ivan', 'Petroff', 'ivan@Petroff.se', 'Moskva');
INSERT INTO Kund VALUES('7710033007', 'James', 'Bond', 'James@Bond.se', 'Stockholm');
INSERT INTO Kund VALUES('7810033456', 'Barack', 'Obama', 'Barack@Obama.se', 'Chicago');

-- Skapa Kategori
INSERT INTO Kategori VALUES('AB0045', 'Mat');
INSERT INTO Kategori VALUES('BB0666', 'Vaxter');
INSERT INTO Kategori VALUES('RD0555', 'Pistoler');
INSERT INTO Kategori VALUES('RS0003', 'Klader');
INSERT INTO Kategori VALUES('BC0099', 'Lakemedel');

-- Skapa Produkter
INSERT INTO Produkt VALUES('AA0001', 'Potatis', '1kg' , 10, 1000);
INSERT INTO Produkt VALUES('AA0002', 'Ris', '1kg' , 20, 1000);
INSERT INTO Produkt VALUES('AA0003', 'Gron gras', 'medium' , 100, 100);
INSERT INTO Produkt VALUES('AA0004', 'AK 47', 'stor' , 10000, 100);
INSERT INTO Produkt VALUES('AA0005', 'blaa byxor Nudie', '32' , 1432, 25);
INSERT INTO Produkt VALUES('AA0006', 'blaa byxor Nudie', '33' , 1432, 20);
INSERT INTO Produkt VALUES('AA0007', 'blaa byxor Nudie', '34' , 1432, 24);
INSERT INTO Produkt VALUES('AA0008', 'vit pulver mot hosta', '10g pase' , 2000, 100);
INSERT INTO Produkt VALUES('AA0009', 'piller mot allergi', '100st i burk' , 100, 1000);

-- Skapa ProduktKategori
INSERT INTO ProduktKategori VALUES('AA0001', 'AB0045');
INSERT INTO ProduktKategori VALUES('AA0002', 'AB0045');
INSERT INTO ProduktKategori VALUES('AA0003', 'BB0666');
INSERT INTO ProduktKategori VALUES('AA0004', 'RD0555');
INSERT INTO ProduktKategori VALUES('AA0005', 'RS0003');
INSERT INTO ProduktKategori VALUES('AA0006', 'RS0003');
INSERT INTO ProduktKategori VALUES('AA0007', 'RS0003');
INSERT INTO ProduktKategori VALUES('AA0008', 'BC0099');
INSERT INTO ProduktKategori VALUES('AA0009', 'BC0099');


-- Skapa Orders
INSERT INTO Orders (KundNr, Datum, Tid) VALUES( '9903137867', '2016-02-01', '09:00');
INSERT INTO Orders (KundNr, Datum, Tid) VALUES( '1110036665', '2016-01-01', '08:00');
INSERT INTO Orders (KundNr, Datum, Tid) VALUES( '2210033765', '2016-01-02', '11:02');
INSERT INTO Orders (KundNr, Datum, Tid) VALUES( '2210033765', '2016-03-03', '12:03');
INSERT INTO Orders (KundNr, Datum, Tid) VALUES( '1209036225', '2017-02-02', '13:06');
INSERT INTO Orders (KundNr, Datum, Tid) VALUES( '7710033007', '2017-01-03', '14:05');
INSERT INTO Orders (KundNr, Datum, Tid) VALUES( '7810033456', '2017-01-04', '14:05');

-- Skapa OrderProdukt

INSERT INTO OrderProdukt VALUES(1, 'AA0001', 2);
INSERT INTO OrderProdukt VALUES(1, 'AA0004', 1);
INSERT INTO OrderProdukt VALUES(2, 'AA0002', 1);
INSERT INTO OrderProdukt VALUES(3, 'AA0003', 1);
INSERT INTO OrderProdukt VALUES(3, 'AA0006', 3);
INSERT INTO OrderProdukt VALUES(4, 'AA0008', 1);
INSERT INTO OrderProdukt VALUES(4, 'AA0001', 1);
INSERT INTO OrderProdukt VALUES(5, 'AA0003', 2);
INSERT INTO OrderProdukt VALUES(5, 'AA0005', 2);
INSERT INTO OrderProdukt VALUES(5, 'AA0009', 2);
INSERT INTO OrderProdukt VALUES(6, 'AA0005', 1);
INSERT INTO OrderProdukt VALUES(6, 'AA0007', 1);
INSERT INTO OrderProdukt VALUES(7, 'AA0003', 2);



-- Skapa Produktsbetyg
INSERT INTO Produktsbetyg VALUES('9903137867', 'AA0001', 'Mycket nojd', 'mycket bra');
INSERT INTO Produktsbetyg VALUES('2210033765', 'AA0002', 'Ganska nojd', null);

