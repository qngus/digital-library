-- Insert authors
INSERT INTO author (id, name) VALUES
    ('11111111-1111-1111-1111-111111111111', 'J.K. Rowling'),
    ('22222222-2222-2222-2222-222222222222', 'George R.R. Martin'),
    ('33333333-3333-3333-3333-333333333333', 'J.R.R. Tolkien'),
    ('44444444-4444-4444-4444-444444444444', 'Jane Austen'),
    ('55555555-5555-5555-5555-555555555555', 'Stephen King');

-- Insert books
INSERT INTO book (id, title, publication_date, summary, page_count) VALUES
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Harry Potter and the Philosopher''s Stone', '1997-06-26', 'First book in the Harry Potter series.', 223),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'Harry Potter and the Chamber of Secrets', '1998-07-02', 'Second book in the Harry Potter series.', 251),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3', 'A Game of Thrones', '1996-08-06', 'First book in A Song of Ice and Fire series.', 694),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4', 'A Clash of Kings', '1998-11-16', 'Second book in A Song of Ice and Fire series.', 768),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5', 'The Fellowship of the Ring', '1954-07-29', 'First book of The Lord of the Rings.', 423),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa6', 'The Two Towers', '1954-11-11', 'Second book of The Lord of the Rings.', 352),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa7', 'Pride and Prejudice', '1813-01-28', 'Romantic novel of manners.', 432),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa8', 'Emma', '1815-12-23', 'Novel about youthful hubris and romantic misunderstandings.', 474),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa9', 'The Shining', '1977-01-28', 'Horror novel set in an isolated hotel.', 447),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa10', 'It', '1986-09-15', 'Horror novel about a shapeshifting entity.', 1138);

-- Insert relations (book_authors)
INSERT INTO book_authors (book_id, author_id) VALUES
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '11111111-1111-1111-1111-111111111111'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3', '22222222-2222-2222-2222-222222222222'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4', '22222222-2222-2222-2222-222222222222'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5', '33333333-3333-3333-3333-333333333333'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa6', '33333333-3333-3333-3333-333333333333'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa7', '44444444-4444-4444-4444-444444444444'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa8', '44444444-4444-4444-4444-444444444444'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa9', '55555555-5555-5555-5555-555555555555'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa10', '55555555-5555-5555-5555-555555555555');
