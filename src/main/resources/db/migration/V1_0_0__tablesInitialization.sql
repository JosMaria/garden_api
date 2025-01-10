CREATE TYPE situation AS ENUM ('absent', 'preserved', 'available');

CREATE TYPE classification AS ENUM (
    'succulent',
    'alimentary',
    'cactus',
    'exotic',
    'forest',
    'fruitful',
    'grass',
    'industrial',
    'medicinal',
    'ornamental'
);

CREATE SEQUENCE plant_sequence START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE;

CREATE TABLE IF NOT EXISTS plants (
    id BIGINT DEFAULT nextval('plant_sequence') PRIMARY KEY,
    uuid VARCHAR(50) UNIQUE NOT NULL,
    common_name VARCHAR(50) UNIQUE NOT NULL,
    scientific_name VARCHAR(50),
    situation situation NOT NULL
);

CREATE TABLE IF NOT EXISTS classifications (
    plant_id BIGINT CONSTRAINT fk_plant_classifications REFERENCES plants(id),
    value classification NOT NULL
);
