-- INSERT ROLES
insert into role values
(gen_random_uuid(),'ROLE_USER');

-- INSERT CATEGORIES
insert into income_category values
(gen_random_uuid(), 'Salary'),
(gen_random_uuid(), 'Bonus'),
(gen_random_uuid(), 'Investments'),
(gen_random_uuid(), 'Part-Time'),
(gen_random_uuid(), 'Others');

insert into expense_category values
(gen_random_uuid(), 'Food'),
(gen_random_uuid(), 'Closing'),
(gen_random_uuid(), 'Car'),
(gen_random_uuid(), 'Health'),
(gen_random_uuid(), 'Sports'),
(gen_random_uuid(), 'Utilities'),
(gen_random_uuid(), 'Housing'),
(gen_random_uuid(), 'Education'),
(gen_random_uuid(), 'Transportation'),
(gen_random_uuid(), 'Phone'),
(gen_random_uuid(), 'Electronics'),
(gen_random_uuid(), 'Beauty'),
(gen_random_uuid(), 'Restaurants'),
(gen_random_uuid(), 'Travel'),
(gen_random_uuid(), 'Pets'),
(gen_random_uuid(), 'Repairs'),
(gen_random_uuid(), 'Lottery'),
(gen_random_uuid(), 'Gifts'),
(gen_random_uuid(), 'Donations');