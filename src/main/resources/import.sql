-- insert admin//admin
insert into Administrator (id, password, role, username) VALUES ('1', 'admin', 'ROLE_ADMIN', 'admin');
-- insert student a//a
INSERT INTO student (dtype, id, name, password, role, school, school_group, section, surname, username, task_effettuati, tempo_effettuato) VALUES ('Student','1','a','$2a$10$3od8USTj1tmeaDBbYiRa4ubWhiH5.WeqBKvMDpgv7WMbge1HCuj2O','ROLE_USER','a','3','1','a','a',0,0);
alter table result ADD CONSTRAINT task_id_unique UNIQUE (task_id);