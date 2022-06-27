-- DROP TABLE IF EXISTS RATE;
-- DROP TABLE IF EXISTS CURRENCY;
--
--This table will store all data which retrived from coincap APIs
CREATE TABLE RATE (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    currencyName VARCHAR(30) NOT NULL,
    symbol VARCHAR(10),
    currencySymbol VARCHAR(10),
    type VARCHAR(10) NOT NULL,
    rateUsd DECIMAL(28,19) NOT NULL,
    result VARCHAR(10),
    timestamp BIGINT
);
--
-- --This table will store all meta data of considered currencies(in this demo project we are not going to consider all the currencies)
-- CREATE TABLE CURRENCY (
--     id INT AUTO_INCREMENT  PRIMARY KEY,
--     currencyName VARCHAR(30) NOT NULL,
--     symbol VARCHAR(10) NOT NULL,
--     type VARCHAR(10) NOT NULL,
--     activeStatus VARCHAR(3)
-- );
--
--
