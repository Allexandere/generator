CREATE TABLE routes.cities (
  id SERIAL NOT NULL,
   city_name VARCHAR(255),
   CONSTRAINT pk_cities PRIMARY KEY (id)
);

CREATE TABLE routes.departures (
  id UUID NOT NULL,
   transport_type INTEGER,
   departure_date TIMESTAMP WITHOUT TIME ZONE,
   travel_time BIGINT,
   city_id BIGINT,
   CONSTRAINT pk_departures PRIMARY KEY (id)
);

ALTER TABLE routes.departures ADD CONSTRAINT FK_DEPARTURES_ON_CITY FOREIGN KEY (city_id) REFERENCES routes.cities (id);