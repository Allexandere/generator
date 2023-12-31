ALTER TABLE routes.departures RENAME city_id TO departure_city_id;

ALTER TABLE routes.departures RENAME CONSTRAINT FK_DEPARTURES_ON_CITY TO FK_DEPARTURES_ON_DEPARTURE_CITY;

ALTER TABLE routes.departures
ALTER COLUMN transport_type TYPE SMALLINT,
ALTER COLUMN travel_time TYPE SMALLINT,
ALTER COLUMN departure_city_id TYPE SMALLINT,
ADD COLUMN arrival_city_id SMALLINT,
ADD COLUMN price INTEGER;

ALTER TABLE routes.departures ADD CONSTRAINT FK_DEPARTURES_ON_ARRIVAL_CITY FOREIGN KEY (arrival_city_id) REFERENCES routes.cities (id);