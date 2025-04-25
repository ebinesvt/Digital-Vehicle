/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.20-log : Database - digital
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`digital` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `digital`;

/*Table structure for table `doc_type` */

DROP TABLE IF EXISTS `doc_type`;

CREATE TABLE `doc_type` (
  `doc_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_type_name` varchar(20) DEFAULT NULL,
  `doc_type_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`doc_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `doc_type` */

insert  into `doc_type`(`doc_type_id`,`doc_type_name`,`doc_type_desc`) values (1,'complaint','speeding is illegal '),(2,'speeding','eoukhresgghiopiyn'),(3,'s','cvbn'),(4,'fhfhyfn',',6ekhnetdgbf'),(5,'aaaaaaaaaaa','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'),(6,'',''),(7,'',''),(8,'',''),(9,'',''),(10,'zzzzzzzz','zzzzzzzz');

/*Table structure for table `docs` */

DROP TABLE IF EXISTS `docs`;

CREATE TABLE `docs` (
  `docs_id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_type_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `docs_file` varchar(500) DEFAULT NULL,
  `number` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`docs_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

/*Data for the table `docs` */

insert  into `docs`(`docs_id`,`doc_type_id`,`user_id`,`docs_file`,`number`) values (19,1,5,'/static/uploads/2d122f4d-aff1-4622-b931-7499feb749b3IMG-20190311-WA0040.jpg','112233445566'),(20,1,5,'/static/uploads/716562ea-df67-41da-8e45-66071280fe9cIMG-20190311-WA0040.jpg','1234567890'),(21,2,5,'/static/uploads/4c72c222-a4cf-490a-859c-09bc839711b8IMG-20190311-WA0040.jpg','1234567890'),(22,1,5,'/static/uploads/2fa3b478-c119-4996-a807-b918b767333cAdvanced Automation of Airport Management.docx','1234567890000000');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(10) DEFAULT NULL,
  `usertype` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`login_id`),
  KEY `login_id` (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values (1,'admin','admin','admin'),(2,'ebines','12345678','user'),(3,'ebines','12345678','user'),(4,'ebines','12345678','user'),(5,'arjun','87654321','user'),(6,'kasbaerk','11111111','police'),(9,'alappi','alappi','police'),(10,'kp','kp','police'),(11,'grees','grees','user');

/*Table structure for table `msgs` */

DROP TABLE IF EXISTS `msgs`;

CREATE TABLE `msgs` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `msg_desc` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `msgs` */

insert  into `msgs`(`msg_id`,`user_id`,`msg_desc`,`reply`,`date`) values (1,1,'khg,suhuk,s','hgygku','2020-02-29'),(2,5,'haiiiiiiiii','hellooooooooo','2020-03-12'),(3,5,'haiii','pending','2020-03-13'),(4,5,'hlo','pending','2020-03-13');

/*Table structure for table `payments` */

DROP TABLE IF EXISTS `payments`;

CREATE TABLE `payments` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `payment_date` date DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `payments` */

insert  into `payments`(`payment_id`,`user_id`,`amount`,`payment_date`) values (1,1,'500','2020-02-29'),(2,5,'500','2020-03-13');

/*Table structure for table `police` */

DROP TABLE IF EXISTS `police`;

CREATE TABLE `police` (
  `police_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `station_name` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `place` varchar(20) DEFAULT NULL,
  `pincode` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`police_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `police` */

insert  into `police`(`police_id`,`login_id`,`station_name`,`phone`,`email`,`place`,`pincode`) values (3,9,'Alappuzha','1234567890','police@g.com','Alappuzha','123456'),(4,10,'Kollam','1234567890','kp@g.com','kollam','123456');

/*Table structure for table `punishment_type` */

DROP TABLE IF EXISTS `punishment_type`;

CREATE TABLE `punishment_type` (
  `punish_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `punish_type_name` varchar(30) DEFAULT NULL,
  `punish_type_desc` varchar(100) DEFAULT NULL,
  `penalty_desc` varchar(100) DEFAULT NULL,
  `penalty_amount` varbinary(200) DEFAULT NULL,
  PRIMARY KEY (`punish_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `punishment_type` */

insert  into `punishment_type`(`punish_type_id`,`punish_type_name`,`punish_type_desc`,`penalty_desc`,`penalty_amount`) values (1,'as','as','as','500'),(2,'aaa','aaa','aaa','1200'),(3,'aaa','aaa','aaa','1200'),(4,'aaa','aaa','aaa','1200'),(5,'aaa','aaa','aaa','1200'),(6,'aaa','aaa','aaa','1200'),(7,'aaa','aaa','aaa','1200');

/*Table structure for table `punishments` */

DROP TABLE IF EXISTS `punishments`;

CREATE TABLE `punishments` (
  `punishment_id` int(11) NOT NULL AUTO_INCREMENT,
  `punish_type_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `police_id` int(11) DEFAULT NULL,
  `punishment_date` date DEFAULT NULL,
  `punishment_status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`punishment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `punishments` */

insert  into `punishments`(`punishment_id`,`punish_type_id`,`user_id`,`police_id`,`punishment_date`,`punishment_status`) values (1,1,1,1,'2020-02-29','pani kitti mone'),(2,1,2,4,'2020-03-13','pending'),(3,1,5,4,'2020-03-13','pending');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `latitude` varchar(30) DEFAULT NULL,
  `longitude` varchar(30) DEFAULT NULL,
  `place` varchar(20) DEFAULT NULL,
  `pincode` varchar(20) DEFAULT NULL,
  `house_name` varchar(30) DEFAULT NULL,
  `gender` varchar(3) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `photo` varchar(500) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`user_id`,`login_id`,`first_name`,`last_name`,`latitude`,`longitude`,`place`,`pincode`,`house_name`,`gender`,`dob`,`photo`,`phone`,`email`) values (1,2,'Ebines','V Thundupurackal','09674213312','74968213245','ernakulam','683030','Thundupurackal','Mal','1999-10-20','static/uploads/6eed42df-8d9f-4bd2-9db3-bb2e13560a4','9633570816','ebinesvt@gmail.com'),(2,3,'Ebines','V Thundupurackal','09674213312','74968213245','ernakulam','683030','Thundupurackal','Mal','1999-10-20','static/uploads/6dbd6743-6b8b-4c9a-a840-1baa8fec4fd','9633570816','ebinesvt@gmail.com'),(3,4,'Ebines','V Thundupurackal','09674213312','74968213245','ernakulam','683030','Thundupurackal','Mal','1999-10-20','static/uploads/25fdf55a-a04b-4c80-9166-0b20cbb67ba','9633570816','ebinesvt@gmail.com'),(4,5,'arjun','baburaj','09674213312','74968213245','ernakulam','683300','C-503,ARMY LAYOUT','Mal','1999-12-13','static/uploads/cb619a10-8306-44ff-9eaa-0cb93d1f1c5achandler.jpg','9876543210','arjunbaburaj99@gmail'),(5,11,'Greeshma','T','97','10','Kochi','123456','AAA','Mal','2020-03-13','static/uploads/dbaaed72-f6be-4ae8-b1d1-762164e0e16bIMG_20190702_111929.jpg','9946288389','greeshmatram111@gmai');

/*Table structure for table `vehicles` */

DROP TABLE IF EXISTS `vehicles`;

CREATE TABLE `vehicles` (
  `vehicle_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `register_no` varchar(40) DEFAULT NULL,
  `manfacture_year` year(4) DEFAULT NULL,
  `brand` varchar(20) DEFAULT NULL,
  `model` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `vehicles` */

insert  into `vehicles`(`vehicle_id`,`user_id`,`register_no`,`manfacture_year`,`brand`,`model`) values (1,1,'1456789',1999,'tata','zxi'),(2,5,'12121212312',0000,'AAA','aaa');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
