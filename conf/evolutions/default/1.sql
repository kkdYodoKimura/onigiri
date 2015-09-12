# Riceballs schema
 
# --- !Ups

CREATE SEQUENCE riceball_id_seq;
CREATE TABLE riceball (
    id integer NOT NULL DEFAULT nextval('riceball_id_seq'),
    name varchar(255)
);
 
# --- !Downs
 
DROP TABLE riceball;
DROP SEQUENCE riceball_id_seq;
