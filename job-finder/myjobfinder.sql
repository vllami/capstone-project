-- MySQL dump 10.13  Distrib 8.0.18, for Linux (x86_64)
--
-- Host: localhost    Database: mysql
-- ------------------------------------------------------
-- Server version	8.0.18-google

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
-- Current Database: `jobfinder`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `jobfinder` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `jobfinder`;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
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
  `company_id` int(11) NOT NULL,
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
  `job_id` int(11) NOT NULL,
  `comp_id` int(11) DEFAULT NULL,
  `cat_id` int(11) DEFAULT NULL,
  `job_name` varchar(50) DEFAULT NULL,
  `job_detail` varchar(255) DEFAULT NULL,
  `create_on` datetime DEFAULT NULL,
  `expire_on` datetime DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
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
INSERT INTO `job` VALUES (1,1,1,'Help Desk Technician','54661 Gulseth Crossing','2021-02-13 00:00:00','2021-01-01 00:00:00',NULL,NULL),(2,2,2,'Database Administrator III','08987 Dottie Drive','2020-11-30 00:00:00','2020-09-14 00:00:00',NULL,NULL),(3,3,3,'Social Worker','31 Mcbride Park','2020-07-08 00:00:00','2021-05-11 00:00:00',NULL,NULL),(4,4,4,'Operator','5 Carberry Hill','2021-05-16 00:00:00','2020-07-20 00:00:00',NULL,NULL),(5,5,5,'Web Designer II','6 Moulton Way','2021-01-25 00:00:00','2020-11-01 00:00:00',NULL,NULL),(6,6,6,'Quality Engineer','0 Dayton Place','2020-10-18 00:00:00','2020-11-12 00:00:00',NULL,NULL),(7,7,7,'Database Administrator I','41 Cardinal Way','2020-10-21 00:00:00','2020-08-28 00:00:00',NULL,NULL),(8,8,8,'Nuclear Power Engineer','892 Clarendon Trail','2021-01-20 00:00:00','2020-07-04 00:00:00',NULL,NULL),(9,9,9,'Research Assistant I','248 Butterfield Crossing','2020-12-28 00:00:00','2021-02-10 00:00:00',NULL,NULL),(10,10,1,'VP Marketing','62697 Delaware Park','2021-07-09 00:00:00','2021-11-24 00:00:00','egooda9@yellowpages.com','709-212-9210'),(11,11,1,'Occupational Therapist','8 Killdeer Pass','2020-11-16 00:00:00','2021-11-18 00:00:00','ablasiaka@over-blog.com','768-242-3929'),(12,12,2,'Food Chemist','1 Dexter Parkway','2021-01-12 00:00:00','2021-11-11 00:00:00','mgherardinib@spiegel.de','613-724-4448'),(13,13,3,'Staff Accountant I','271 Prentice Lane','2020-11-23 00:00:00','2021-11-01 00:00:00','coliphardc@disqus.com','332-401-2432'),(14,14,4,'Help Desk Technician','43928 Prairie Rose Parkway','2021-05-26 00:00:00','2021-11-24 00:00:00','akarlolczakd@washington.edu','949-825-2983'),(15,15,5,'Programmer II','26 Anthes Pass','2021-01-24 00:00:00','2021-11-01 00:00:00','svigere@netvibes.com','539-560-7033'),(16,16,6,'Desktop Support Technician','01 Pond Junction','2021-04-12 00:00:00','2021-10-29 00:00:00','amougetf@barnesandnoble.com','457-855-9471'),(17,17,7,'Marketing Manager','8196 Northview Hill','2021-01-13 00:00:00','2021-11-12 00:00:00','eragatg@wikipedia.org','890-969-5764'),(18,18,8,'Data Coordiator','37384 Onsgard Alley','2020-07-25 00:00:00','2021-10-30 00:00:00','cravenscroftth@is.gd','917-891-2293'),(19,19,9,'Assistant Professor','15 Cordelia Center','2021-06-01 00:00:00','2021-11-17 00:00:00','egrishanini@ask.com','132-863-7658'),(20,20,8,'Account Executive','0 Loftsgordon Way','2020-12-07 00:00:00','2021-11-09 00:00:00','dbashamj@elpais.com','599-543-7912'),(21,21,1,'Tax Accountant','2217 Havey Terrace','2021-05-10 00:00:00','2021-11-03 00:00:00','wminuccik@unblog.fr','913-649-9784'),(22,22,2,'Project Manager','1440 Rockefeller Crossing','2021-04-10 00:00:00','2021-11-06 00:00:00','aperottil@sfgate.com','473-329-7761'),(23,23,3,'Payment Adjustment Coordinator','8574 Browning Way','2021-06-27 00:00:00','2021-10-30 00:00:00','rdowlesm@etsy.com','725-938-2468'),(24,24,4,'Software Engineer IV','5951 Granby Terrace','2021-05-21 00:00:00','2021-11-09 00:00:00','alethbyn@redcross.org','210-479-6150'),(25,25,5,'Executive Secretary','710 Carioca Drive','2021-01-28 00:00:00','2021-11-18 00:00:00','bduchamo@wsj.com','830-388-4475'),(26,26,6,'Speech Pathologist','6 Pond Park','2020-11-11 00:00:00','2021-11-01 00:00:00','gcunradip@chronoengine.com','178-735-7660'),(27,27,7,'Environmental Specialist','79694 Maywood Road','2020-06-22 00:00:00','2021-11-11 00:00:00','sheadingtonq@chron.com','809-433-7218'),(28,28,8,'Cost Accountant','17 Eliot Park','2020-08-20 00:00:00','2021-11-07 00:00:00','epaskr@hugedomains.com','867-979-6848'),(29,29,9,'Sales Representative','21913 East Street','2020-08-07 00:00:00','2021-11-23 00:00:00','tbrudenells@gnu.org','803-752-4614'),(30,30,3,'Desktop Support Technician','1 Larry Park','2020-12-12 00:00:00','2021-11-15 00:00:00','ulatourt@oaic.gov.au','207-174-6790'),(31,31,1,'Project Manager','30368 John Wall Street','2021-06-07 00:00:00','2021-10-30 00:00:00','hmannieu@alibaba.com','823-898-8117'),(32,32,2,'Internal Auditor','228 Lighthouse Bay Hill','2020-07-27 00:00:00','2021-11-12 00:00:00','kscalav@springer.com','298-905-9022'),(33,33,3,'Assistant Professor','9615 1st Alley','2020-07-09 00:00:00','2021-11-24 00:00:00','hattawayw@globo.com','871-906-8414'),(34,34,4,'Human Resources Assistant I','61668 Tennessee Lane','2020-12-11 00:00:00','2021-10-30 00:00:00','mjezzardx@oracle.com','891-620-4829'),(35,35,5,'Legal Assistant','9 Ridgeview Hill','2020-06-17 00:00:00','2021-11-06 00:00:00','wattridey@istockphoto.com','683-450-1938'),(36,36,6,'Accountant III','439 Maple Wood Center','2020-12-22 00:00:00','2021-11-21 00:00:00','alittlefieldz@webmd.com','853-561-7263'),(37,37,7,'Budget/Accounting Analyst IV','1421 Florence Lane','2021-03-22 00:00:00','2021-11-15 00:00:00','sciubutaro10@meetup.com','833-710-3242'),(38,38,8,'Marketing Manager','0557 Hagan Circle','2021-06-04 00:00:00','2021-10-29 00:00:00','otranfield11@census.gov','375-828-6226'),(39,39,9,'Registered Nurse','75 Miller Circle','2021-01-09 00:00:00','2021-11-14 00:00:00','ebarrar12@vkontakte.ru','790-843-5001'),(41,41,1,'VP Marketing','3003 Bellgrove Alley','2020-06-17 00:00:00','2021-11-24 00:00:00','kbacke14@taobao.com','385-777-8917'),(42,42,2,'Teacher','97935 Carpenter Road','2021-03-25 00:00:00','2021-11-04 00:00:00','acruikshanks15@trellian.com','826-388-0046'),(43,43,3,'Senior Editor','2523 Del Mar Alley','2020-07-26 00:00:00','2021-11-11 00:00:00','mivie16@icq.com','881-254-5716'),(44,44,4,'VP Sales','883 Anhalt Court','2020-12-11 00:00:00','2021-11-24 00:00:00','nwastie17@woothemes.com','739-776-9339'),(45,45,5,'Environmental Tech','1351 Leroy Street','2021-05-06 00:00:00','2021-11-08 00:00:00','kpessold18@yandex.ru','343-578-1920'),(46,46,6,'Quality Engineer','77931 Farwell Parkway','2020-08-12 00:00:00','2021-11-01 00:00:00','achristal19@oakley.com','362-979-7146'),(47,47,7,'Internal Auditor','55584 Oneill Place','2020-11-20 00:00:00','2021-11-07 00:00:00','ghenryson1a@sciencedirect.com','807-442-3428'),(48,48,8,'Budget/Accounting Analyst I','70222 Daystar Center','2020-07-16 00:00:00','2021-11-07 00:00:00','twinkell1b@zdnet.com','405-927-7782'),(49,49,9,'Quality Engineer','286 Walton Alley','2020-10-24 00:00:00','2021-11-02 00:00:00','ggasking1c@hubpages.com','392-764-8334'),(50,50,9,'Safety Technician I','23512 Cottonwood Street','2020-12-18 00:00:00','2021-10-29 00:00:00','aflamank1d@issuu.com','564-102-2849'),(51,51,1,'Technical Writer','7 Lighthouse Bay Hill','2021-06-09 00:00:00','2021-11-16 00:00:00','twyse1e@comsenz.com','824-489-4993'),(52,52,2,'Food Chemist','2360 Washington Street','2021-07-12 00:00:00','2021-11-11 00:00:00','iduffy1f@accuweather.com','559-413-3614'),(53,53,3,'Associate Professor','080 Elgar Road','2020-08-22 00:00:00','2021-11-17 00:00:00','pragdale1g@symantec.com','396-792-2094'),(54,54,4,'Office Assistant II','2 Anniversary Center','2020-11-12 00:00:00','2021-11-14 00:00:00','mmuzzillo1h@usatoday.com','730-976-7745'),(55,55,5,'VP Marketing','949 Algoma Plaza','2020-12-10 00:00:00','2021-11-02 00:00:00','ischankel1i@lycos.com','711-153-9951'),(56,56,6,'Safety Technician IV','9697 Blue Bill Park Park','2020-08-14 00:00:00','2021-11-08 00:00:00','rpallaske1j@forbes.com','613-601-7487'),(57,57,7,'Director of Sales','64 Homewood Crossing','2020-06-05 00:00:00','2021-11-20 00:00:00','thasely1k@imageshack.us','304-959-7416'),(58,58,8,'Professor','49948 Beilfuss Lane','2021-02-18 00:00:00','2021-11-03 00:00:00','sjarvis1l@cnn.com','846-421-5726'),(59,59,9,'Administrative Assistant III','376 Annamark Drive','2021-04-28 00:00:00','2021-11-11 00:00:00','mwinkell1m@hexun.com','948-640-9108'),(60,60,8,'Web Developer III','4 Dexter Court','2021-06-03 00:00:00','2021-11-14 00:00:00','bnorthcott1n@umn.edu','780-484-0083'),(61,61,1,'Tax Accountant','3488 Nova Way','2021-05-12 00:00:00','2021-11-04 00:00:00','jwidmoor1o@vkontakte.ru','252-258-1016'),(62,62,2,'Marketing Manager','2 Grover Trail','2020-11-05 00:00:00','2021-11-11 00:00:00','ncoling1p@amazon.co.jp','186-233-2784'),(63,63,3,'Director of Sales','16 Judy Park','2020-11-01 00:00:00','2021-11-20 00:00:00','alongley1q@ucla.edu','159-793-5440'),(64,64,4,'Engineer II','8545 Bowman Place','2020-11-09 00:00:00','2021-10-30 00:00:00','esenussi1r@blog.com','955-257-5757'),(65,65,5,'Help Desk Technician','09 Elka Center','2021-03-04 00:00:00','2021-11-21 00:00:00','kpaiton1s@smh.com.au','697-246-5833'),(66,66,6,'Programmer Analyst IV','637 Artisan Way','2021-07-09 00:00:00','2021-11-02 00:00:00','yallans1t@ucsd.edu','193-758-6540'),(67,67,7,'Editor','51789 Bluejay Lane','2020-10-08 00:00:00','2021-11-01 00:00:00','cgoulder1u@patch.com','212-345-1621'),(68,68,8,'Human Resources Manager','349 Ridgeview Road','2020-06-04 00:00:00','2021-11-05 00:00:00','ebarsam1v@ning.com','528-584-2585'),(69,69,9,'Automation Specialist I','5336 Pond Plaza','2020-11-05 00:00:00','2021-10-30 00:00:00','bstrick1w@quantcast.com','277-449-2111'),(70,70,5,'Senior Sales Associate','83 Blackbird Place','2021-02-12 00:00:00','2021-11-05 00:00:00','gcromblehome1x@sakura.ne.jp','735-750-5697'),(71,71,1,'Account Coordinator','57 Fairview Park','2020-08-31 00:00:00','2021-11-01 00:00:00','mbrenton1y@zdnet.com','671-791-1381'),(72,72,2,'Civil Engineer','8 International Street','2021-03-13 00:00:00','2021-11-11 00:00:00','ikaspar1z@accuweather.com','829-932-1992'),(73,73,3,'Safety Technician IV','93694 Victoria Court','2021-02-18 00:00:00','2021-11-02 00:00:00','tmcvity20@newyorker.com','815-281-5665'),(74,74,4,'Speech Pathologist','5273 Ridge Oak Hill','2020-07-30 00:00:00','2021-11-14 00:00:00','ddowning21@blogspot.com','117-424-2696'),(75,75,5,'Research Nurse','3 Petterle Park','2020-08-22 00:00:00','2021-10-29 00:00:00','dkhoter22@japanpost.jp','232-475-1706'),(76,76,6,'Budget/Accounting Analyst I','021 Vahlen Hill','2020-12-12 00:00:00','2021-11-07 00:00:00','emaddin23@businessinsider.com','877-376-0995'),(77,77,7,'Web Developer II','59995 Johnson Junction','2020-07-13 00:00:00','2021-11-06 00:00:00','ahawford24@google.it','500-806-8955'),(78,78,8,'Community Outreach Specialist','71054 Forster Circle','2021-06-24 00:00:00','2021-11-03 00:00:00','fgeeraert25@skyrock.com','344-934-5831'),(79,79,9,'Product Engineer','442 Cody Center','2020-12-26 00:00:00','2021-11-08 00:00:00','fyounghusband26@cornell.edu','187-901-2116'),(80,80,5,'Database Administrator IV','0423 Killdeer Alley','2021-06-27 00:00:00','2021-11-17 00:00:00','fstellino27@imgur.com','282-899-1633'),(81,81,1,'Software Test Engineer IV','72758 Loeprich Circle','2020-07-14 00:00:00','2021-11-18 00:00:00','amannix28@ted.com','831-433-3430'),(82,82,2,'Safety Technician II','80 Morrow Alley','2020-07-08 00:00:00','2021-11-13 00:00:00','bbaldacchi29@posterous.com','130-331-9891'),(83,83,3,'Account Executive','1 Linden Trail','2020-08-02 00:00:00','2021-11-21 00:00:00','riffe2a@myspace.com','137-680-0362'),(84,84,4,'Tax Accountant','1 Pawling Park','2020-07-17 00:00:00','2021-11-18 00:00:00','gherculeson2b@ycombinator.com','988-157-9118'),(85,85,5,'Web Designer IV','09 Loomis Hill','2020-12-10 00:00:00','2021-11-21 00:00:00','fbowden2c@constantcontact.com','536-755-1106'),(86,86,6,'Information Systems Manager','3517 Anthes Center','2021-07-08 00:00:00','2021-11-17 00:00:00','ihaverty2d@blogger.com','330-729-3186'),(87,87,7,'Health Coach III','5 Hauk Parkway','2020-08-05 00:00:00','2021-11-24 00:00:00','tpriestnall2e@thetimes.co.uk','844-461-7012'),(88,88,8,'Desktop Support Technician','05 Jenna Place','2020-12-07 00:00:00','2021-11-03 00:00:00','ogunter2f@ocn.ne.jp','178-283-7596'),(89,89,9,'Sales Associate','58386 Dryden Center','2020-07-22 00:00:00','2021-11-16 00:00:00','ebeaves2g@apple.com','995-604-9610'),(90,90,9,'Account Representative IV','4142 Birchwood Avenue','2020-12-03 00:00:00','2021-11-03 00:00:00','atill2h@hibu.com','410-760-0257'),(91,91,1,'Senior Cost Accountant','08 Calypso Way','2021-02-14 00:00:00','2021-11-15 00:00:00','rmannooch2i@who.int','557-744-5093'),(92,92,2,'Executive Secretary','78 Eliot Pass','2021-06-19 00:00:00','2021-10-29 00:00:00','mpoultney2j@ameblo.jp','800-894-8961'),(93,93,3,'Web Developer IV','8283 Glendale Pass','2021-02-06 00:00:00','2021-11-17 00:00:00','tgarrie2k@spotify.com','490-816-4132'),(94,94,4,'Account Coordinator','20737 Sherman Point','2021-07-10 00:00:00','2021-11-15 00:00:00','kenglish2l@guardian.co.uk','524-884-4239'),(95,95,5,'Chief Design Engineer','31 Walton Pass','2021-03-14 00:00:00','2021-11-12 00:00:00','mpiken2m@acquirethisname.com','687-493-8086'),(96,96,6,'Electrical Engineer','9511 Blaine Pass','2020-12-04 00:00:00','2021-11-04 00:00:00','tasbury2n@devhub.com','563-580-7754'),(97,97,7,'Community Outreach Specialist','2112 Huxley Trail','2020-07-17 00:00:00','2021-11-24 00:00:00','abeeke2o@blogger.com','715-310-9094'),(98,98,8,'Chief Design Engineer','2607 Menomonie Place','2021-05-01 00:00:00','2021-11-12 00:00:00','eemes2p@mediafire.com','658-449-7234'),(99,99,9,'Media Manager I','58098 Manitowish Street','2021-01-10 00:00:00','2021-11-18 00:00:00','ihorsey2q@shareasale.com','212-264-1831'),(100,100,1,'Human Resources Assistant I','273 Bellgrove Place','2020-07-24 00:00:00','2021-11-18 00:00:00','clerway2r@flavors.me','253-380-3501');
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(20) DEFAULT NULL,
  `productDescription` varchar(100) DEFAULT NULL,
  `productBrand` varchar(20) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `profile` int(11) DEFAULT NULL,
  `file` varchar(155) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(155) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(155) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `img` varchar(155) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `profile` (`profile`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`profile`) REFERENCES `users_profile` (`profile_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'','','roy','pbkdf2:sha256:150000$k1Ud5dzh$d0347f416e89ea486b33c988c9be65730329b2dd6d712f73c9920103a006a82e',1,'job.pdf',NULL,NULL,NULL),(2,'Adi susilayasa','tong','adi@gmail.com','pbkdf2:sha256:260000$zB8oJBSSzlD8EAoZ$892da55ff52ccd2c76ce3bde8390e8d7850cd4f87f0c8cd6cd30d373cd3b6273',2,NULL,NULL,NULL,NULL),(3,'Adi Susilayasa','adi123','dsaodj@gmail.com','pbkdf2:sha256:150000$LMjBbfzH$23b4776a3062908667a9a5c71b0f7d20a6c7103abb520a520ce89f143521f2f1',NULL,NULL,NULL,NULL,NULL),(4,'Adi Susilayasa','adi123','dsaodj@gmail.com','pbkdf2:sha256:150000$B5C4pnAT$8e4b24fd4faa67e951706a69303fe96b01222ad0cb6806fad68e9d2b85319a72',NULL,NULL,NULL,NULL,NULL),(5,'Adi Susilayasa','adi123','dsaodj@gmail.com','pbkdf2:sha256:150000$kzZ56pvj$1979206dab829f979f1fee33013951ab16d2ff2268cad46acffba8bdd9675c16',NULL,NULL,NULL,NULL,NULL),(6,'kotongzz','kotongzz@gmail.com','kotongzz','pbkdf2:sha256:150000$t0VX1BBT$55a7dab8bae7de8da4dc9f19e2dcbc3fb2954f5887c505bbb513442675f3a189',NULL,NULL,NULL,NULL,NULL),(7,'kotongzz','kotongzz@gmail.com','kotongzz','pbkdf2:sha256:150000$NlW3sJlV$e45103559b53e978ba22f4219c89761a3c06ceb49e4e6ef7d433f71511e272db',NULL,NULL,NULL,NULL,NULL),(8,'kotongzz','kotongzz@gmail.com','kotongzz','pbkdf2:sha256:150000$hM28AZRf$3716456719dc38bcb29d8aac8764e580a77827570600a0937c4fd8bb9751f982',NULL,NULL,NULL,NULL,NULL),(16,'kotongzz','kotongzz@gmail.com','kotongzz','pbkdf2:sha256:150000$6XPSjPon$00bac51ec2994c17b496a4cea9a302776f0e74e0388b7f142801c0fc8b985f74',NULL,NULL,NULL,NULL,NULL),(17,'kotongzz','kotongzz@gmail.com','kotongzz','pbkdf2:sha256:150000$zPMPQ4Sp$de3370cf690cbe6ec748056560ef0ac374c84829a4d5b70b865c663b02e269b2',NULL,NULL,NULL,NULL,NULL),(18,'kotongzz','kotongzz@gmail.com','kotongzz','pbkdf2:sha256:150000$ttJeJp3h$b147a1badf09815a8617d17345297ed3a1000c432602e99bf4d359d16f11fab0',NULL,NULL,NULL,NULL,NULL),(19,'kotongzz','kotongzz','kotongzz','pbkdf2:sha256:150000$iEAmRAzz$7e942b70ea7e6466a855e4b44973392646be15ea2025635b4566c52beff50201',NULL,'Null',NULL,NULL,NULL),(20,'daffa','dapoi','daffaprabowo5@gmail.com','pbkdf2:sha256:150000$oCE92Xtb$a8fa380bc9f620ff0a5ef3bfc99a056b529144a0cfaa7ecf3d5d44a413918d5c',NULL,NULL,NULL,NULL,NULL),(21,'adisusilayasa','adisusilayasa','adisusilayasa@gmail.com','pbkdf2:sha256:150000$LCwnYttl$a10770d144b53b6e9b64a70112884dc26989a5673a2965723df217e5d3f6f467',NULL,NULL,NULL,NULL,NULL),(22,'puki','puki','luthfidaffa2202@gmail.com','pbkdf2:sha256:150000$PQE9DBe8$3445938e2d75ff28b56dea1514131cde783632a266e927155da43f9b31f48807',NULL,NULL,NULL,NULL,NULL),(23,'favian','favian','tes123@gmail.com','pbkdf2:sha256:150000$1X0mdlo2$91bcd48ac1df9907ed94935d9c5225c8ef647750d03ee40bb7af577e2f23de6d',NULL,NULL,NULL,NULL,NULL),(24,'Villa Mukti Indriyanto','vllami','villamuktiindriyanto@gmail.com','pbkdf2:sha256:150000$W79zmKYC$8e30a42180834bcbd0fd2bf8ad9e827c08f4b4f023f95468386acc6421115577',NULL,NULL,NULL,NULL,NULL),(25,'Jhon Doe','jhondoe','jhondoe@gmail.com','pbkdf2:sha256:150000$sYUrzNpE$2de24655caae05f6d487bafc81decdade604525b7f3d31fd9fd0d91da7fb0538',NULL,NULL,NULL,NULL,NULL),(26,'Jajang','jajang','jajang@gmail.com','pbkdf2:sha256:150000$bujXnJW5$795c5974ac21f9d2e3431316bb23bd44144970ba80f47d46b6572b41928cecae',NULL,NULL,NULL,NULL,NULL),(27,'Haha','hahaha','haha@gmail.com','pbkdf2:sha256:150000$yacYY8fC$7f999cb42606495d9c4511bca8b43f9ff242cb1af787ec20d7791955ce27aa5a',NULL,NULL,NULL,NULL,NULL),(28,'Jamaluddin','jamal','jamal@gmail.com','pbkdf2:sha256:150000$cFRAdp0H$a08f5ed92c779b7b621827737f9b3e006d2ee559abaee3956594e0aff39a4624',NULL,NULL,NULL,NULL,NULL),(29,'bringkot12','kotongzz12','adidaidia@gmail.com','pbkdf2:sha256:150000$XDwX8MHw$3327a834a54cea8d7c9ccf8acbe51db6619418bb93f6d6150d1b644f17d2886f',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_profile`
--

DROP TABLE IF EXISTS `users_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_profile` (
  `profile_id` int(11) NOT NULL,
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

-- Dump completed on 2021-06-09 16:38:23
