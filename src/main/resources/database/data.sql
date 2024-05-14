DROP DATABASE IF EXISTS training_management;
CREATE DATABASE training_management;
use training_management;
;

insert into roles (name, display_name)
values ('ROLE_ADMIN', ''),
       ('ROLE_MANAGE', 'Quản Trị Viên'),
       ('ROLE_MANAGE', 'Recruitment, Recruiter'),
       ('ROLE_MANAGE', 'Quality Controller'),
       ('ROLE_MANAGE', 'Recruitment supporter'),
       ('ROLE_MANAGE', 'Request recipient');
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
values ('practice', 1, 1, 1),
       ('practice', 3, 1, 2),
       ('practice', 6, 1, 3),
       ('practice', 8, 1, 4),
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
       ('attitude', 2, 1, 7);
select * from intern_scores;