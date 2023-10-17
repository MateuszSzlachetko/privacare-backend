insert into users (id, auth_id, created_at, name, surname, pesel, phone_number)
values  ('9dbae116-3954-4a2c-9308-31fb971dc6fc',
        '582fd8f4-48c8-4e67-8c97-3319acd76fe0',
        '2023-10-14 16:02:34',
        'Mateusz',
        'Szlachetko',
        '01232808996',
        '668845260'
        ),
        ('432b984b-3a3e-4078-af5d-c620bd3b9159',
        '6444056b-2ae4-458d-bc59-d15e0b0a638f',
        '2023-10-15 20:01:06',
        'Albert',
        'Górecki',
        '81120752635',
        '376460830'
        ),
        ('758ab8b0-72f0-4333-9e42-d2b170b20484',
        'fc8e03a8-cd1e-4a5e-9014-8ee1876dea90',
        '2023-10-17 23:27:18',
        'Bartłomiej',
        'Kubiak',
        '70062785715',
        '941719125'
        ),
        ('8c67fd7b-f1c7-48f2-8470-efd11282184d',
        '3a7bf67b-3371-4cac-b910-e92f12d06cd2',
        '2023-10-22 19:24:35',
        'Kajetan',
        'Sokołowski',
        '92040589731',
        '653494925'
        ),
        ('185b13bc-14e7-4019-9357-87f1679d7d80',
        '842ba48f-2392-4d77-bd1f-57cddf5d2567',
        '2023-10-24 17:19:17',
        'Beata',
        'Zawadzka',
        '83021991364',
        '501127658');

-- insert into category (id, name)
-- values (1, 'Ważne terminy'),
--        (2, 'Lista Zakupów'),
--        (3, 'Administracja i finanse');

insert into news (id, creator_id, created_at, title, content)
values ('ab5da93f-2508-4180-abb8-358ac2b67304',
        '9dbae116-3954-4a2c-9308-31fb971dc6fc',
        '2023-10-25 22:50:33',
        'Testowy tytuł',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit,' ||
        ' sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.' ||
        ' Non consectetur a erat nam at. Nulla facilisi cras' ||
        ' fermentum odio eu feugiat pretium nibh.' ||
        ' Ultricies tristique nulla aliquet enim.'),
        ('bd42d437-7ebb-4f5a-bcba-bdd4b28e081d',
        '9dbae116-3954-4a2c-9308-31fb971dc6fc',
        '2023-10-26 20:15:15',
        'Urlop',
        'Chciałem Was poinformować, że zbliża się mój zasłużony urlop.' ||
        ' Po kilku miesiącach ciężkiej pracy, nadszedł czas' ||
        ' na chwilę relaksu i regeneracji.');

insert into notes (id, creator_id, patient_id, created_at, content)
values ('a7294a41-d67e-4d67-a833-4adeb3f80ee6',
        '9dbae116-3954-4a2c-9308-31fb971dc6fc',
        '432b984b-3a3e-4078-af5d-c620bd3b9159',
        '(2023-10-19 18:45:10)',
        'Pacjent w dobrym stanie, leczenie przebiega pomyślnie.'
        ),
        ('94ce9ac9-f94e-40bc-9e4d-c6742d0b3658',
        '9dbae116-3954-4a2c-9308-31fb971dc6fc',
        '758ab8b0-72f0-4333-9e42-d2b170b20484',
        '(2023-10-19 18:45:10)',
        'Pogorszenie stanu zdrowia. Konieczne dodatkowe badania' ||
        ' i konsultacje specjalistyczne.'
        );

insert into tasks (id, creator_id, created_at, content, category_id, state)
values ('f8831620-b4af-4bf3-94ea-5d143fb6c622',
        '9dbae116-3954-4a2c-9308-31fb971dc6fc',
        '2023-10-22 19:24:35',
        '2 pudełka rękawiczek',
        2,
        'TODO'),
        ('b4b426d0-ecc1-4b84-996c-71447901694e',
        '9dbae116-3954-4a2c-9308-31fb971dc6fc',
        '2023-10-23 16:24:35',
        'Kontrola z sanepidu 05.11.2023',
        1,
        'TODO'),
        ('5f3c617a-1e25-49b3-baf9-c2469dc69bae',
        '9dbae116-3954-4a2c-9308-31fb971dc6fc',
        '2023-10-22 09:24:35',
        'zapłacić podatek',
        3,
        'DONE'
        );

insert into appointments (id, creator_id, patient_id, starts_at, finishes_at, state)
values ('1219e3da-4151-4baa-9256-86679e350a3f',
        '9dbae116-3954-4a2c-9308-31fb971dc6fc',
        '432b984b-3a3e-4078-af5d-c620bd3b9159',
        '2023-10-29 09:00:00',
        '2023-10-29 10:00:00',
        'ACTIVE'
        ),
        ('369a12a9-881b-445b-b6fd-5ffb8fb00f1f',
        '758ab8b0-72f0-4333-9e42-d2b170b20484',
        '758ab8b0-72f0-4333-9e42-d2b170b20484',
        '2023-10-29 10:00:00',
        '2023-10-29 11:00:00',
        'ACTIVE'
        );