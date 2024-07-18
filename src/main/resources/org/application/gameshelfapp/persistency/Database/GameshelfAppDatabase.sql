CREATE DATABASE IF NOT EXISTS gameshelfappdb;
USE gameshelfappdb;

CREATE TABLE IF NOT EXISTS ObjectOnSale(
    Name varchar(40),
    Platform varchar(20),
    Price float NOT NULL,
    Type varchar(40) NOT NULL,
    Description varchar(500),
    Copies int NOT NULL,
    PRIMARY KEY (Name, Platform)
);

CREATE TABLE IF NOT EXISTS Category(
    Type varchar(40) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS Console(
  Console varchar(50) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS User(
    Username varchar(50) NOT NULL UNIQUE,
    Email varchar(100) PRIMARY KEY,
    Password char(255) NOT NULL,
    Type varchar(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS Sale(
    Id int AUTO_INCREMENT PRIMARY KEY,
    Name varchar(100) NOT NULL,
    Copies int NOT NULL,
    State varchar(20),
    Price float NOT NULL,
    GameName varchar(40),
    Platform varchar(20),
    UserEmail varchar(100),
    UserAddress varchar(100) NOT NULL
);



