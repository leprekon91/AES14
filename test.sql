#Create Test Database and start using it
#Comment this two lines out if you already ran the script once
CREATE DATABASE test
use test;
#Users table:
CREATE TABLE `users` (
  `user_name` varchar(50) NOT NULL,
  `pass` varchar(50) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_name`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


#student user
INSERT INTO `test`.`users`
(`user_name`,
`pass`,
`first_name`,
`last_name`,
`type`)
VALUES
('s',
's',
'John',
'Student',
1);

#teacher user
INSERT INTO `test`.`users`
(`user_name`,
`pass`,
`first_name`,
`last_name`,
`type`)
VALUES
('t',
't',
'Bob',
'Teacher',
2);

#principal user
INSERT INTO `test`.`users`
(`user_name`,
`pass`,
`first_name`,
`last_name`,
`type`)
VALUES
('p',
'p',
'Alice',
'Principal',
3);
