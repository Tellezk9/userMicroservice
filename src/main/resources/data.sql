INSERT INTO `user`.`user`
(`birth_date`,
`dni_number`,
`last_name`,
`mail`,
`name`,
`password`,
`phone`,
`id_role`)
VALUES
(
'2001/05/31',
'12345',
'testLastName',
'test@email.com',
'testName',
'$2a$10$NN1.r/33cVFz/eB1RqAfd.V60CJCG1nkFBazYyx1CoU6tBwxNXxDC',
'+364779971167',
'4');



INSERT INTO `role` (`id`, `description`, `name`) VALUES ('4', 'ROLE_ADMIN', 'ROLE_ADMIN');
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('3', 'ROLE_OWNER', 'ROLE_OWNER');
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('2', 'ROLE_EMPLOYEE', 'ROLE_EMPLOYEE');
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('1', 'ROLE_CLIENT', 'ROLE_CLIENT');
