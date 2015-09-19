# Riceballs schema
 
# --- !Ups

CREATE SEQUENCE riceball_id_seq;
CREATE TABLE riceball (
    id integer NOT NULL DEFAULT nextval('riceball_id_seq'),
    name varchar(255),
    store_id integer,
    image_url varchar(255),
    description varchar(255)
);

CREATE TABLE store (
    id integer NOT NULL,
    name varchar(255) NOT NULL
);

INSERT INTO store(id, name) VALUES (1,'ファミリーマート'),(2,'ココストア'),(3,'セブンイレブン'),(4,'ローソン'),(5,'サンクス'),(6,'デイリーヤマザキ'),(7,'ハートイン'),(8,'その他');

# --- !Downs
 
DROP TABLE riceball;
DROP SEQUENCE riceball_id_seq;
DROP TABLE store;
