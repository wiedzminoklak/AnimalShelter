CREATE TABLE kind (
	kind_id			INTEGER			NOT NULL		CONSTRAINT pk_kind	PRIMARY KEY,
	name			VARCHAR2(50)	NOT NULL
);

CREATE SEQUENCE kind_seq;

CREATE OR REPLACE TRIGGER kind_seq_trg
BEFORE INSERT ON kind
FOR EACH ROW
BEGIN
	SELECT	kind_seq.NEXTVAL
	INTO	:new.kind_id
	FROM	dual;
END;
/

CREATE TABLE animal (
	animal_id		INTEGER			NOT NULL		CONSTRAINT pk_animal PRIMARY KEY,
	kind_id			INTEGER			NOT NULL,
	name			VARCHAR2(50),
	age				INTEGER,
	weight			NUMBER(6,2)		NOT NULL,
	arrival_date	DATE
);

ALTER TABLE animal
ADD CONSTRAINT fk_kind FOREIGN KEY (kind_id) 
REFERENCES kind (kind_id);

CREATE SEQUENCE animal_seq;

CREATE OR REPLACE TRIGGER animal_seq_trg
BEFORE INSERT ON animal
FOR EACH ROW
BEGIN
	SELECT	animal_seq.NEXTVAL
	INTO	:new.animal_id
	FROM	dual;
END;
/

CREATE TABLE users (
	user_id			INTEGER			NOT NULL		CONSTRAINT pk_user	PRIMARY KEY,
	name			VARCHAR2(50)	NOT NULL,
	password		VARCHAR2(120)	NOT NULL
);

CREATE SEQUENCE user_seq;

CREATE OR REPLACE TRIGGER user_seq_trg
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
	SELECT	user_seq.NEXTVAL
	INTO	:new.user_id
	FROM	dual;
END;
/