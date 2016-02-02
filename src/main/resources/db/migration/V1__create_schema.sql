CREATE TABLE uktowns (
  ID int(11) NOT NULL AUTO_INCREMENT,
  Name varchar(2000) DEFAULT NULL,
  County varchar(255) NOT NULL,
  Country varchar(255) NOT NULL,
  GridRef varchar(10) NOT NULL,
  Latitude decimal(10,5) DEFAULT NULL,
  Longitude decimal(10,5) DEFAULT NULL,
  Easting int(11) NOT NULL,
  Northing int(11) NOT NULL,
  Postcode varchar(10) NOT NULL,
  Type varchar(255) NOT NULL,
  PRIMARY KEY (ID)
);