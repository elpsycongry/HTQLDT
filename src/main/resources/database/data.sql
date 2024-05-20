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
       ('2024-05-17', false, '2024-05-14', 'training', 4),
       ('2024-05-17', false, '2024-05-14' , 'training', 1);

insert into intern_subjects (name)
values ('Java'),
       ('Git'),
       ('React'),
       ('Spring boot'),
       ('CTDL'),
       ('Giải thuật'),
       ('Scrum');

insert into intern_scores (type, value, user_id, subject_id)

values ('practice', "", 37, 1),
       ('practice', 3, 37, 2),
       ('practice', 5, 37, 3),
       ('practice', 4, 37, 4),
       ('practice', 10, 37, 5),
       ('practice', 2, 37, 6),
       ('practice', 2, 37, 7),
       ('theory', 6, 37, 1),
       ('theory', 6, 37, 2),
       ('theory', 6, 37, 3),
       ('theory', 6, 37, 4),
       ('theory', 6, 37, 5),
       ('theory', 6, 37, 6),
       ('theory', 6, 37, 7),
       ('attitude', 8, 37, 1),
       ('attitude', 9, 37, 2),
       ('attitude', 3, 37, 3),
       ('attitude', 4, 37, 4),
       ('attitude', 5, 37, 5),
       ('attitude', 1, 37, 6),
       ('attitude', 2, 37, 7),
       ('practice', 4, 37, 2),
       ('practice', 4, 37, 3),
       ('practice', 4, 37, 4),
       ('practice', 0, 37, 5),
       ('practice', 2, 37, 6),
       ('practice', 2, 37, 7),
       ('theory', 6, 37, 2),
       ('theory', 6, 37, 3),
       ('theory', 6, 37, 4),
       ('theory', 6, 37, 5),
       ('theory', 6, 37, 6),
       ('theory', 6, 37, 7),
       ('attitude', 8, 37, 2),
       ('attitude', 3, 37, 3),
       ('attitude', 4, 37, 4),
       ('attitude', 5, 37, 5),
       ('attitude', 2, 37, 6),
       ('attitude', 2, 37, 7);

insert into users_roles values (1, 1);

select * from intern_scores;