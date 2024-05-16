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
values ('2024-05-17', false, '2024-05-14', 'Đang thực tập', 2),
       ('2024-05-17', false, '2024-05-14', 'Đã thực tập', 3),
       ('2024-05-17', false, '2024-05-14', 'Đang thực tập', 4);

insert into intern_subjects (name)
values ('Java'),
       ('Git'),
       ('React'),
       ('Spring boot'),
       ('Cấu trúc dữ liệu'),
       ('Giải thuật'),
       ('Scrum');

insert into intern_scores (type, value, user_id, subject_id)
values ('practice', 1, 23, 1),
       ('practice', 3, 23, 2),
       ('practice', 6, 23, 3),
       ('practice', 8, 23, 4),
       ('practice', 10,23, 5),
       ('practice', 2, 23, 6),
       ('practice', 2, 23, 7),
       ('theory', 6, 23, 1),
       ('theory', 6, 23, 2),
       ('theory', 6, 23, 3),
       ('theory', 6, 23, 4),
       ('theory', 6, 23, 5),
       ('theory', 6, 23, 6),
       ('theory', 6, 23, 7),
       ('attitude', 8, 23, 1),
       ('attitude', 9, 23, 2),
       ('attitude', 3, 23, 3),
       ('attitude', 4, 23, 4),
       ('attitude', 5, 23, 5),
       ('attitude', 1, 23, 6),
       ('attitude', 2, 23, 7);
select * from intern_scores;