-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: socialmedia_db
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `facebook_pages`
--

DROP TABLE IF EXISTS `facebook_pages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facebook_pages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `page_access_token` varchar(500) DEFAULT NULL,
  `page_id` varchar(255) NOT NULL,
  `page_name` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgfsjs1vav33caxi2foampd1kn` (`user_id`),
  CONSTRAINT `FKgfsjs1vav33caxi2foampd1kn` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facebook_pages`
--

LOCK TABLES `facebook_pages` WRITE;
/*!40000 ALTER TABLE `facebook_pages` DISABLE KEYS */;
INSERT INTO `facebook_pages` VALUES (1,'EAARy9J6vSKEBQZAfPrGs1DysSbMuEFqe4IZAc0TR5vTogYManZBwByMuLLWSy6AdjxLYjCZBSvoatZCjoukZBcuPVTMYrKDqrhnZCnFd1JmbUZCbcELbtCdwFyX1ps2ZCxK8obrZCs4z7wSHoMe6LzL2Xo4DFG9DxTG9LyfUmHRc3lhhcJ4kvHyiZC4u1HwuIwFuTwTCZBs32ps5','902132529656698','Backend Test',1),(2,'EAARy9J6vSKEBQXT76fbfV146GuCXmGnQgRXZAVrAtlZBuCHZCPW7Y5Sva4eCrnkVdrh5ihrUsZAYQCGifNhPiaInOtvvn004ZCkCWe1PMhGb8LHqXhGljlarZAiUgMm2Qd8deDxijum16pYhOZAUOrD81e48dNNrWIYWX2CLaSQZBObi3sr1XVeAdFu7aPe0w2sIoWpq3mIh','902132529656698','Backend Test',2);
/*!40000 ALTER TABLE `facebook_pages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `facebook_post_id` varchar(255) DEFAULT NULL,
  `pageid` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5lidm6cqbc7u4xhqpxm898qme` (`user_id`),
  CONSTRAINT `FK5lidm6cqbc7u4xhqpxm898qme` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,'First test post done by backend team','2026-01-03 11:08:21.614077','902132529656698_122105183019187501','902132529656698','PUBLISH SUCESS',1),(2,'Second test post done by backend team','2026-01-03 11:13:31.420870','902132529656698_122105184591187501','902132529656698','PUBLISH SUCESS',2);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `facebook_access_token` varchar(500) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `provider` enum('GOOGLE') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2026-01-03 10:59:50.015137','shashankkondaiah@gmail.com','EAARy9J6vSKEBQccHn82guRiuad0HrpRRoGMZC3jFZAYZAooAE2QRREU9fVmJ6fwlKEKWWSwAuPtp4Ed4s36aCOdF4tVSGM1JoZCIw4rPg2f1jVMSQXiazlPE7llRX2ZBjIaG5bklYrRkDhzU93bbuoDwc0sffZBAA8pva6hBuGHtZB0wAM5WH2TVlTp9HfjnT6Q6DtDD81Y7ZB17BUPXkxssiIYpIpWg6ZAtIVCUBA5wvRReqWZBkbEiEkBrTFF3Y36ERZAJH0dGtDnM3J9WFRsEASEMpj2sjAeW55yKxMn','SHASHANK B K','GOOGLE'),(2,'2026-01-03 11:10:12.129149','shashankk1718@gmail.com','EAARy9J6vSKEBQYvUK7tfVbZAKbcaoV8pwGcF6mJ4MzxrqXkIczYW7OHOvCWn1n9RnK3Tl6vZC4tky9Lhe5dOvZCPJ6m4GZBFUmOJTrf21zqQ9ZCg7bZA03wHYncJWj0XvcQNFxbHuAGXgZAOwQtd3UVH5nbJ0XMVaruHBDZC9S6BZApjqZAsru50WV1PNFQN7spdCrXXWGZAIAtu943C2IfRXPdoZBbtG59IZC9mXgMoEF523wOHRd1wwamUBenNHJMko8eDq1NZAMG8yEVYoVMVv7qImhUZCdIYz50rP1mUaZBk','Shashank B K','GOOGLE');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-03 11:22:37
