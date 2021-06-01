-- MySQL dump 10.13  Distrib 8.0.25, for Linux (x86_64)
--
-- Host: localhost    Database: jobfinder
-- ------------------------------------------------------
-- Server version	8.0.25-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL,
  `category_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Software Developer'),(2,'System Administrator'),(3,'Project Manager'),(4,'Web Developer'),(5,'Database Administrator'),(6,'Java Developer'),(7,'Python Developer'),(8,'Network Administrator'),(9,'Security Analyst');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `company_id` int NOT NULL,
  `company_name` varchar(50) DEFAULT NULL,
  `company_detail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'Skyndu','003 Waywood Terrace'),(2,'Edgeify','467 Carey Point'),(3,'Jazzy','36543 Kropf Court'),(4,'Babbleopia','76496 Steensland Circle'),(5,'Fadeo','617 Starling Terrace'),(6,'Vinte','690 Bay Terrace'),(7,'Jaxbean','78 American Junction'),(8,'Jayo','577 Ridgeway Terrace'),(9,'Tagpad','93 Lillian Avenue'),(10,'Dynabox','25 Maryland Crossing'),(11,'Flipbug','21 Schurz Street'),(12,'Skajo','5636 Orin Point'),(13,'Realfire','83 Lotheville Drive'),(14,'Blogtag','0 Oriole Plaza'),(15,'Kwimbee','1 Rutledge Court'),(16,'Tagfeed','2 3rd Park'),(17,'Skilith','667 Forest Dale Street'),(18,'Zoonoodle','5 South Junction'),(19,'Quinu','09510 Nova Point'),(20,'Divavu','49 Vermont Terrace'),(21,'Twiyo','11263 Oak Parkway'),(22,'Aimbu','9 Dixon Circle'),(23,'Kwimbee','45 Reindahl Lane'),(24,'Skinix','061 Briar Crest Crossing'),(25,'Yacero','6 Moulton Center'),(26,'Aibox','66 Leroy Avenue'),(27,'Myworks','4844 Northport Crossing'),(28,'Tazzy','02501 Logan Alley'),(29,'Kimia','034 Kinsman Avenue'),(30,'Gigaclub','0346 Stuart Alley'),(31,'Zoomlounge','7 Independence Road'),(32,'Ooba','6576 Kropf Alley'),(33,'Jayo','06868 Hudson Street'),(34,'Jabbertype','9743 Browning Circle'),(35,'Twinder','2875 John Wall Terrace'),(36,'Einti','13048 Petterle Alley'),(37,'Jaxspan','932 Pearson Road'),(38,'Browsetype','7198 Anniversary Lane'),(39,'Brainbox','6055 Eagan Center'),(40,'Vipe','1 Gina Alley'),(41,'Fadeo','6 Sycamore Avenue'),(42,'Leenti','69237 Redwing Street'),(43,'Ntag','2861 Maywood Point'),(44,'Riffwire','89163 Dayton Avenue'),(45,'Devpulse','8 Knutson Point'),(46,'Riffpedia','12567 Prairie Rose Terrace'),(47,'Mynte','2 Shasta Parkway'),(48,'Cogibox','402 Paget Place'),(49,'Edgeclub','39 Cottonwood Terrace'),(50,'Edgeblab','6 Graceland Park'),(51,'Ntags','8 Valley Edge Court'),(52,'Zoonoodle','750 Debra Point'),(53,'Yabox','56 Tennessee Junction'),(54,'Flipstorm','509 Vermont Court'),(55,'Skidoo','47826 Eastwood Place'),(56,'Agimba','688 Blue Bill Park Road'),(57,'Skipfire','4502 Magdeline Pass'),(58,'Browsetype','77691 Veith Place'),(59,'Babbleblab','9 Dunning Crossing'),(60,'Divape','52580 Packers Court'),(61,'Wikivu','7 Cardinal Road'),(62,'Yamia','63 Village Court'),(63,'Blognation','2047 Menomonie Plaza'),(64,'Meevee','5277 Pepper Wood Center'),(65,'Jamia','00548 International Alley'),(66,'Nlounge','1 Carpenter Alley'),(67,'Fivechat','9871 Norway Maple Alley'),(68,'Flashdog','268 Lillian Point'),(69,'Meevee','1 Blue Bill Park Court'),(70,'Youtags','63203 Susan Pass'),(71,'Tagpad','841 Leroy Hill'),(72,'Twitterlist','4394 Kinsman Street'),(73,'Babbleopia','61 Dorton Trail'),(74,'Gabspot','82296 Dayton Plaza'),(75,'Dabjam','72176 Dwight Way'),(76,'Jamia','81667 Lillian Drive'),(77,'Jabbersphere','89434 New Castle Alley'),(78,'Youspan','44 Delaware Avenue'),(79,'Oyoloo','28 Arizona Place'),(80,'Realcube','3388 Lukken Hill'),(81,'Youtags','95 Clemons Parkway'),(82,'Dablist','4 Birchwood Plaza'),(83,'Devcast','1 Kinsman Court'),(84,'Ozu','636 Karstens Junction'),(85,'Skyble','5 Arizona Hill'),(86,'Gigaclub','771 Springview Way'),(87,'Eabox','17 Fairfield Trail'),(88,'Devshare','2 Lakeland Drive'),(89,'Pixope','3002 Mendota Junction'),(90,'Edgetag','422 Tony Avenue'),(91,'Yotz','71708 Center Terrace'),(92,'Realcube','0801 Brickson Park Avenue'),(93,'Jabbersphere','4445 Arizona Park'),(94,'Flashset','69613 Reindahl Alley'),(95,'Youspan','44 Grasskamp Hill'),(96,'Kazu','49123 Kensington Pass'),(97,'Edgeify','1461 Warrior Pass'),(98,'Trilith','736 Tony Way'),(99,'Skiba','65834 Iowa Lane'),(100,'Vitz','44372 Village Place');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `job_id` int NOT NULL,
  `comp_id` int DEFAULT NULL,
  `cat_id` int DEFAULT NULL,
  `job_name` varchar(50) DEFAULT NULL,
  `job_detail` varchar(255) DEFAULT NULL,
  `create_on` datetime DEFAULT NULL,
  `expire_on` datetime DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  KEY `comp_id` (`comp_id`),
  KEY `cat_id` (`cat_id`),
  CONSTRAINT `job_ibfk_1` FOREIGN KEY (`comp_id`) REFERENCES `company` (`company_id`) ON DELETE CASCADE,
  CONSTRAINT `job_ibfk_2` FOREIGN KEY (`cat_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (1,1,1,'Help Desk Technician','54661 Gulseth Crossing','2021-02-13 00:00:00','2021-01-01 00:00:00'),(2,2,2,'Database Administrator III','08987 Dottie Drive','2020-11-30 00:00:00','2020-09-14 00:00:00'),(3,3,3,'Social Worker','31 Mcbride Park','2020-07-08 00:00:00','2021-05-11 00:00:00'),(4,4,4,'Operator','5 Carberry Hill','2021-05-16 00:00:00','2020-07-20 00:00:00'),(5,5,5,'Web Designer II','6 Moulton Way','2021-01-25 00:00:00','2020-11-01 00:00:00'),(6,6,6,'Quality Engineer','0 Dayton Place','2020-10-18 00:00:00','2020-11-12 00:00:00'),(7,7,7,'Database Administrator I','41 Cardinal Way','2020-10-21 00:00:00','2020-08-28 00:00:00'),(8,8,8,'Nuclear Power Engineer','892 Clarendon Trail','2021-01-20 00:00:00','2020-07-04 00:00:00'),(9,9,9,'Research Assistant I','248 Butterfield Crossing','2020-12-28 00:00:00','2021-02-10 00:00:00');
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(20) DEFAULT NULL,
  `productDescription` varchar(100) DEFAULT NULL,
  `productBrand` varchar(20) DEFAULT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `profile` int DEFAULT NULL,
  `file` varchar(155) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(155) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(155) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `img` varchar(155) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `profile` (`profile`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`profile`) REFERENCES `users_profile` (`profile_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'','','roy','pbkdf2:sha256:150000$k1Ud5dzh$d0347f416e89ea486b33c988c9be65730329b2dd6d712f73c9920103a006a82e',1,NULL,NULL,NULL,NULL),(2,'Adi susilayasa','tong','adi@gmail.com','pbkdf2:sha256:260000$zB8oJBSSzlD8EAoZ$892da55ff52ccd2c76ce3bde8390e8d7850cd4f87f0c8cd6cd30d373cd3b6273',2,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_profile`
--

DROP TABLE IF EXISTS `users_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_profile` (
  `profile_id` int NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `img` varchar(155) DEFAULT NULL,
  `file` varchar(155) DEFAULT NULL,
  PRIMARY KEY (`profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_profile`
--

LOCK TABLES `users_profile` WRITE;
/*!40000 ALTER TABLE `users_profile` DISABLE KEYS */;
INSERT INTO `users_profile` VALUES (1,'98263 Burning Wood Junction','454 740 2339',NULL,NULL),(2,'1 Lukken Way','172 335 8877',NULL,NULL),(3,'5094 Drewry Point','359 241 4332',NULL,NULL);
/*!40000 ALTER TABLE `users_profile` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-25  2:23:26
