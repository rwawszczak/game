CREATE TABLE user(
uid VARCHAR(255),
email VARCHAR(255),
username  VARCHAR(255),
PASSWORD  VARCHAR(255),
PRIMARY KEY (uid));

CREATE TABLE role(
id BIGINT(20) NOT NULL AUTO_INCREMENT,
NAME VARCHAR(255),
PRIMARY KEY (id));

CREATE TABLE user_roles (
  user_uid VARCHAR(255) NOT NULL,
  role_id BIGINT(20) NOT NULL,
  PRIMARY KEY (user_uid, role_id),
  KEY fk_user_roles_role_id (role_id),
  CONSTRAINT fk_user_roles_role_id FOREIGN KEY (role_id) REFERENCES role (id),
  CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_uid) REFERENCES user (uid)
);