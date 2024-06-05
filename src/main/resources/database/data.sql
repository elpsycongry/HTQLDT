    DROP DATABASE IF EXISTS training_management;
CREATE DATABASE training_management;
use training_management;
;

insert into training_management.roles (name, display_name)
values ('ROLE_ADMIN', 'Super Admin'),
       ('ROLE_DM', 'Trưởng bộ phận/nhóm'),
       ('ROLE_TM', 'Quản lí đào tạo'),
       ('ROLE_QC', 'Kiểm soát chất lượng'),
       ('ROLE_HR', 'Nhân sự');



insert into intern_subjects (name)
values ('Java'),
       ('Git'),
       ('React'),
       ('Spring boot'),
       ('CTDL'),
       ('Giải thuật'),
       ('Scrum');

