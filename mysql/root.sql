CREATE DATABASE stat_recorder;
CREATE USER minecraft@localhost IDENTIFIED by 'password';
GRANT ALL ON stat_recorder.* TO minecraft@localhost;
