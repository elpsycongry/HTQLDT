DROP DATABASE IF EXISTS training_management;
CREATE DATABASE training_management;
use training_management;
;

insert into training_management.roles (name, display_name)
values ('ROLE_ADMIN', 'Super Admin'),
       ('ROLE_DM', 'Trưởng bộ phận/nhóm'),
       ('ROLE_QLĐT', 'Quản lí đào tạo'),
       ('ROLE_KSCL', 'Kiểm soát chất lượng'),
       ('ROLE_HR', 'Nhân sự'),
       ('ROLE', 'Nhân viên');

insert into intern_profiles(end_date,
                            is_pass,
                            start_date,
                            training_state,
                            user_id)
values ('2024-05-17', false, '2024-05-14', 'training', 2),
       ('2024-05-17', false, '2024-05-14', 'training', 3),
       (null, false, '2024-05-14', 'training', 4),
       ('', false, '2024-05-14' , 'training', 1);

insert into intern_subjects (name)
values ('Java'),
       ('Git'),
       ('React'),
       ('Spring boot'),
       ('CTDL'),
       ('Giải thuật'),
       ('Scrum');

insert into intern_scores (type, value, user_id, subject_id)
values ('practice', null, 1, 1),
       ('practice', null, 1, 2),
       ('practice', null, 1, 3),
       ('practice', null, 1, 4),
       ('practice', 10, 1, 5),
       ('practice', 2, 1, 6),
       ('practice', 2, 1, 7),
       ('theory', 6, 1, 1),
       ('theory', 6, 1, 2),
       ('theory', 6, 1, 3),
       ('theory', 6, 1, 4),
       ('theory', 6, 1, 5),
       ('theory', 6, 1, 6),
       ('theory', 6, 1, 7),
       ('attitude', 8, 1, 1),
       ('attitude', 9, 1, 2),
       ('attitude', 3, 1, 3),
       ('attitude', 4, 1, 4),
       ('attitude', 5, 1, 5),
       ('attitude', 1, 1, 6),
       ('attitude', 2, 1, 7),
       ('practice', null, 2, 2),
       ('practice', null, 2, 3),
       ('practice', null, 2, 4),
       ('practice', 0, 2, 5),
       ('practice', 2, 2, 6),
       ('practice', 2, 2, 7),
       ('theory', 6, 2, 2),
       ('theory', 6, 2, 3),
       ('theory', 6, 2, 4),
       ('theory', 6, 2, 5),
       ('theory', 6, 2, 6),
       ('theory', 6, 2, 7),
       ('attitude', 8, 2, 2),
       ('attitude', 3, 2, 3),
       ('attitude', 4, 2, 4),
       ('attitude', 5, 2, 5),
       ('attitude', 2, 2, 6),
       ('attitude', 2, 2, 7);

insert into users_roles values (1, 1);
select * from intern_scores;