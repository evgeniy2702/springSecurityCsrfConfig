-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema books
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema books
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `books` DEFAULT CHARACTER SET utf8 ;
USE `books` ;

-- -----------------------------------------------------
-- Table `books`.`buyers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `books`.`buyers` (
  `buyer_id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `nameBuyer` VARCHAR(45) NOT NULL DEFAULT 'Null',
  PRIMARY KEY (`buyer_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 23
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `books`.`books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `books`.`books` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL DEFAULT 'Null',
  `author` VARCHAR(45) NOT NULL DEFAULT 'Null',
  `year` INT(11) NOT NULL DEFAULT '0',
  `stile` VARCHAR(45) NOT NULL DEFAULT 'Null',
  `num_pages` INT(11) NOT NULL DEFAULT '0',
  `description` VARCHAR(45) NOT NULL DEFAULT 'Null',
  `buyer_id` BIGINT(100) NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `buyerKey_idx` (`buyer_id` ASC),
  CONSTRAINT `buyerKey`
    FOREIGN KEY (`buyer_id`)
    REFERENCES `books`.`buyers` (`buyer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `books`.`owners`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `books`.`owners` (
  `id_owner` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL DEFAULT 'Null',
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(1000) NULL DEFAULT NULL,
  PRIMARY KEY (`id_owner`))
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `books`.`owner_books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `books`.`owner_books` (
  `book_id` BIGINT(100) NOT NULL,
  `owner_id` BIGINT(100) NOT NULL,
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  INDEX `owner_fk_idx` (`owner_id` ASC),
  INDEX `book_fk_idx` (`book_id` ASC),
  CONSTRAINT `book_fk`
    FOREIGN KEY (`book_id`)
    REFERENCES `books`.`books` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `owner_fk`
    FOREIGN KEY (`owner_id`)
    REFERENCES `books`.`owners` (`id_owner`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 45
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `books`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `books`.`role` (
  `id` BIGINT(100) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `books`.`owner_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `books`.`owner_role` (
  `owner_id` BIGINT(100) NOT NULL,
  `roles_id` BIGINT(100) NOT NULL,
  INDEX `owner_role_owners_id_owner_fk` (`owner_id` ASC),
  INDEX `owner_role_role_id_fk` (`roles_id` ASC),
  CONSTRAINT `owner_role_owners_id_owner_fk`
    FOREIGN KEY (`owner_id`)
    REFERENCES `books`.`owners` (`id_owner`),
  CONSTRAINT `owner_role_role_id_fk`
    FOREIGN KEY (`roles_id`)
    REFERENCES `books`.`role` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
