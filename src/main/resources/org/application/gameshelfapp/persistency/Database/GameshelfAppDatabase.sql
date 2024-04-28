CREATE DATABASE IF NOT EXISTS gameshelfappdb;
USE gameshelfappdb;

CREATE TABLE IF NOT EXISTS ObjectOnSale(
    Name varchar(40),
    Platform varchar(20),
    Price float NOT NULL,
    Type varchar(40) NOT NULL,
    Description varchar(500),
    Copies int NOT NULL,
    Filter varchar(40),
    PRIMARY KEY (Name, Platform)
);

CREATE TABLE IF NOT EXISTS Filters(
    Type varchar(40) PRIMARY KEY
    );

CREATE TABLE IF NOT EXISTS Filtered(
    Name varchar(40) REFERENCES ObjectOnSale(Name),
    Platform varchar(20) REFERENCES ObjectOnSale(Platform),
    Type varchar(40) REFERENCES Filters(Type),
    PRIMARY KEY (Name, Platform, Type)
    );

INSERT IGNORE INTO Filters(Type) VALUES
('Action'),
('Adventure'),
('Shooting'),
('Arcade'),
('Simulation');


CREATE TABLE IF NOT EXISTS Catalogue(
    Name varchar(40) REFERENCES ObjectOnSale(Name),
    Email varchar(100) REFERENCES User(Email),
    Copies int NOT NULL,
    PRIMARY KEY (Name, Email)
);

CREATE TABLE IF NOT EXISTS User(
    Username varchar(50) NOT NULL UNIQUE,
    Email varchar(100) PRIMARY KEY,
    Password char(255) NOT NULL,
    Type varchar(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS Sale(
    Id int AUTO_INCREMENT PRIMARY KEY,
    Copies int NOT NULL,
    State varchar(20) NOT NULL DEFAULT ('To confirm'),
    Price float NOT NULL,
    GameName varchar(40),
    Platform varchar(20),
    UserEmail varchar(100) REFERENCES User(Email),
    UserAddress varchar(100) NOT NULL,
    FOREIGN KEY (GameName, Platform) REFERENCES ObjectOnSale(Name, Platform)
);
