CREATE TABLE IF NOT EXISTS images (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    plant_id BIGINT CONSTRAINT fk_plant_images REFERENCES plants(id),
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50),
    path VARCHAR,
    favorite BOOL DEFAULT FALSE
);
