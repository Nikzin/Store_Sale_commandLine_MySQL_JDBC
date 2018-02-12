use webbutiken;

-- Fråga 1.

DROP PROCEDURE IF EXISTS  LäggTillKundvagn;
DELIMITER //
CREATE PROCEDURE LäggTillKundvagn(kundnummer CHAR(10), produktnummer CHAR(6), ordernummer INT)
BEGIN

DECLARE KundFinns BOOL DEFAULT TRUE ;
DECLARE ProduktFinns BOOL DEFAULT TRUE ;
  DECLARE OrderAndKund BOOL DEFAULT TRUE ;

DECLARE CONTINUE HANDLER FOR 1062
    BEGIN
    ROLLBACK;
 SELECT 'Error, duplicate key occurred';
END ;

DECLARE CONTINUE HANDLER FOR 1452
    BEGIN
    ROLLBACK;
 SELECT 'Error, a foreign key constraint fails';
END ;

DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    SELECT 'error SQLEXCEPTION occured';
END ;

  IF (NOT exists(SELECT * FROM kund WHERE Personnummer=kundnummer))
         THEN   set KundFinns =FALSE ;
           SELECT 'Kund finns inte';
            END IF;


  IF (NOT exists(SELECT * FROM produkt WHERE produkt.Kod=produktnummer))
            THEN   set ProduktFinns =FALSE ;
        SELECT 'Produkt finns inte';
   END IF;

    IF exists (SELECT * FROM orders WHERE OrderNr=ordernummer)
   THEN
  IF (NOT (exists (SELECT * FROM kund
  INNER JOIN orders ON kund.Personnummer = orders.KundNr
WHERE (Personnummer=kundnummer AND orders.OrderNr=ordernummer))))
 THEN SET OrderAndKund = FALSE;
   SELECT 'Kund personnummer för detta ordern är fel';
   END IF;
     END IF;

if (ProduktFinns AND KundFinns AND OrderAndKund)
  THEN
   START TRANSACTION ;




IF exists (SELECT * FROM orders WHERE OrderNr=ordernummer)
   THEN

IF exists (SELECT * FROM produkt WHERE produkt.Kod=produktnummer)
       THEN UPDATE orderprodukt set Antal = (Antal+1) WHERE orderprodukt.Produktskod=produktnummer;

ELSE INSERT INTO orderprodukt VALUES(ordernummer, produktnummer, 1);
  END IF;
     ELSE

IF ordernummer IS NULL
 THEN SET ordernummer = floor( 100000000+ (rand()*899999999));

-- THEN SET ordernummer = (cast((floor( 100000+ (rand()*899999))) AS CHAR(6)));
END IF;

  INSERT INTO Orders VALUES(ordernummer, kundnummer , current_date, current_time);
  INSERT INTO orderprodukt VALUES(ordernummer, produktnummer, 1);

END IF;

UPDATE produkt set Tilgangligt = (Tilgangligt - 1) WHERE produkt.Kod=produktnummer;



COMMIT ;
END IF ;
END //
DELIMITER ;

 CALL LäggTillKundvagn ('1209036225','AA0003', NULL );




-- fråga 2
DROP PROCEDURE IF EXISTS  TopProducts;

DELIMITER &&

CREATE PROCEDURE TopProducts(startdatum DATE, slutdatum DATE, antalprodukter INT)

 BEGIN

SELECT produkt.Namn AS Topprodukter, orderprodukt.Antal AS Antal FROM produkt
  INNER JOIN orderprodukt ON produkt.Kod = orderprodukt.Produktskod
   INNER JOIN  orders ON orderprodukt.OrderNr = orders.OrderNr
 WHERE orders.Datum BETWEEN startdatum AND slutdatum
  ORDER BY orderprodukt.Antal DESC

LIMIT antalprodukter;

END &&
DELIMITER ;
-- CALL  TopProducts('2016-01-02', '2017-02-02', 5);





-- fråga 2
DROP TRIGGER IF EXISTS  SlutilagerTrigger;

CREATE TRIGGER SlutilagerTrigger AFTER UPDATE ON produkt
  FOR EACH ROW
  BEGIN
  IF (new.Tilgangligt=0)
    THEN INSERT INTO slutilager VALUES(current_date, old.Kod );
    END IF;
    END;

