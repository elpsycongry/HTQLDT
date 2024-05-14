DROP DATABASE IF EXISTS training_management;
CREATE DATABASE training_management;
use training_management;
insert into roles (name, display_name) values
                                           ("ROLE_ADMIN", ""),
                                           ("ROLE_USER", "NA"),
                                           ("ROLE_MANAGE", "Quản Trị Viên"),
                                           ("ROLE_MANAGE", "Recruitment, Recruiter"),
                                           ("ROLE_MANAGE", "Quality Controller"),
                                           ("ROLE_MANAGE", "Recruitment supporter"),
                                           ("ROLE_MANAGE", "Request recipient");