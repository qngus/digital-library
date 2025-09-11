-- Insert authors
INSERT INTO author (id, name) VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'George Orwell'),
    ('550e8400-e29b-41d4-a716-446655440000', 'Jane Austen'),
    ('6fa459ea-ee8a-3ca4-894e-db77e160355e', 'F. Scott Fitzgerald'),
    ('123e4567-e89b-12d3-a456-426614174000', 'Gabriel García Márquez'),
    ('987fbc97-4bed-5078-af07-9141ba07c9f3', 'Harper Lee'),
    ('1c6b147e-7f71-4a8c-8e6e-47e2d3e3c6b1', 'Herman Melville'),
    ('0f8fad5b-d9cb-469f-a165-70867728950e', 'Leo Tolstoy'),
    ('7c9e6679-7425-40de-944b-e07fc1f90ae7', 'J.D. Salinger'),
    ('3c9a0c3d-5c2b-4d1a-bc8a-8f0d1d55f2d1', 'J.R.R. Tolkien'),
    ('2c4b147e-1a71-4b8c-9e6f-57e2d3c3d7b2', 'Fyodor Dostoevsky'),
    ('9f8b7c6d-5e4f-4a2b-9c0d-1e2f3a4b5c6d', 'Aldous Huxley'),
    ('abcd1234-ef56-7890-abcd-1234567890ab', 'Miguel de Cervantes');

-- Insert books
INSERT INTO book (id, title, publication_date, summary, page_count) VALUES
    ('e3f915f3-41ab-43a9-8799-d14101353289', '1984', '1949-06-08', 'Un roman dystopique sur un futur totalitaire où la surveillance et la répression sont omniprésentes.', 328),
    ('a0595988-073c-464f-a38b-88d8de93adbc', 'Animal Farm', '1945-08-17', 'Une allégorie politique sur la corruption et le totalitarisme.', 112),
    ('bcd3dbcb-fca3-4f67-b6b3-c5b66b32f18c', 'Pride and Prejudice', '1813-01-28', 'Une satire sociale qui explore les thèmes de l''amour et des relations dans l''Angleterre du début du XIXe siècle.', 279),
    ('f890b5a3-a012-4de0-bb39-b042e8db0f09', 'Emma', '1815-12-23', 'Roman sur l''orgueil et les malentendus amoureux.', 474),
    ('a238d1bd-f98d-46d7-9a44-2b15c2fe81b0', 'The Great Gatsby', '1925-04-10', 'Une critique de l''American Dream à travers l''histoire tragique de Jay Gatsby et de son amour pour Daisy Buchanan.', 180),
    ('22706e31-e9bb-4c52-ba33-8b29255e0c46', 'One Hundred Years of Solitude', '1967-06-05', 'Un récit magique et réaliste de la famille Buendía sur plusieurs générations dans le village fictif de Macondo.', 417),
    ('48746aef-ebdb-4203-ab0e-4f6b06957e95', 'To Kill a Mockingbird', '1960-07-11', 'Une exploration des thèmes du racisme et de l''injustice dans le Sud des États-Unis à travers les yeux d''une jeune fille.', 281),
    ('f7367ebf-0917-48f8-9a09-e76dd5a393b5', 'Moby-Dick', '1851-10-18', 'L''obsession du capitaine Ahab pour la chasse à la baleine blanche symbolise la lutte humaine contre la nature et le destin.', 585),
    ('528a4d25-6c06-4d2f-bebf-bdb4f2c216b7', 'War and Peace', '1869-01-01', 'Une fresque historique qui explore la société russe, la guerre, l''amour et le destin à travers plusieurs familles.', 1225),
    ('7e684977-1dd6-4ea3-8747-79af79b64089', 'The Catcher in the Rye', '1951-07-16', 'Les aventures et la rébellion intérieure de Holden Caulfield face à l''hypocrisie de la société adulte.', 234),
    ('b6ef4dc3-3cae-459d-8650-b83b645d3189', 'The Hobbit', '1937-09-21', 'Les aventures de Bilbo Baggins dans un monde fantastique rempli de dragons, d''elfes et de quêtes épiques.', 310),
    ('8c2a02d5-b92b-4bd8-beb0-3db31619f673', 'Crime and Punishment', '1866-01-01', 'Une étude psychologique intense sur la culpabilité et la rédemption à travers le personnage de Raskolnikov.', 430),
    ('d0fde369-2d62-4200-be9f-3a03bff09851', 'Brave New World', '1932-08-18', 'Une société futuriste où le conditionnement et la consommation remplacent la liberté individuelle et l''émotion.', 311),
    ('dba9ed11-f334-4062-a008-c8eb20a9afc1', 'Don Quixote', '1605-01-16', 'Un roman satirique qui raconte les aventures de Don Quichotte, un noble obsédé par les idéaux chevaleresques.', 863);

-- Insert relations (book_authors)
INSERT INTO book_authors (book_id, author_id) VALUES
    ('e3f915f3-41ab-43a9-8799-d14101353289', 'f47ac10b-58cc-4372-a567-0e02b2c3d479'),
    ('a0595988-073c-464f-a38b-88d8de93adbc', 'f47ac10b-58cc-4372-a567-0e02b2c3d479'),
    ('bcd3dbcb-fca3-4f67-b6b3-c5b66b32f18c', '550e8400-e29b-41d4-a716-446655440000'),
    ('f890b5a3-a012-4de0-bb39-b042e8db0f09', '550e8400-e29b-41d4-a716-446655440000'),
    ('a238d1bd-f98d-46d7-9a44-2b15c2fe81b0', '6fa459ea-ee8a-3ca4-894e-db77e160355e'),
    ('22706e31-e9bb-4c52-ba33-8b29255e0c46', '123e4567-e89b-12d3-a456-426614174000'),
    ('48746aef-ebdb-4203-ab0e-4f6b06957e95', '987fbc97-4bed-5078-af07-9141ba07c9f3'),
    ('f7367ebf-0917-48f8-9a09-e76dd5a393b5', '1c6b147e-7f71-4a8c-8e6e-47e2d3e3c6b1'),
    ('528a4d25-6c06-4d2f-bebf-bdb4f2c216b7', '0f8fad5b-d9cb-469f-a165-70867728950e'),
    ('7e684977-1dd6-4ea3-8747-79af79b64089', '7c9e6679-7425-40de-944b-e07fc1f90ae7'),
    ('b6ef4dc3-3cae-459d-8650-b83b645d3189', '3c9a0c3d-5c2b-4d1a-bc8a-8f0d1d55f2d1'),
    ('8c2a02d5-b92b-4bd8-beb0-3db31619f673', '2c4b147e-1a71-4b8c-9e6f-57e2d3c3d7b2'),
    ('d0fde369-2d62-4200-be9f-3a03bff09851', '9f8b7c6d-5e4f-4a2b-9c0d-1e2f3a4b5c6d'),
    ('dba9ed11-f334-4062-a008-c8eb20a9afc1', 'abcd1234-ef56-7890-abcd-1234567890ab');
