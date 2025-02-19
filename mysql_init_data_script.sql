-- INSERT ROLES
insert into role values
(UUID_TO_BIN(UUID()),'ROLE_USER');

-- INSERT CATEGORIES
insert into income_category values
(UUID_TO_BIN(UUID()), 'Salary'),
(UUID_TO_BIN(UUID()), 'Bonus'),
(UUID_TO_BIN(UUID()), 'Investments'),
(UUID_TO_BIN(UUID()), 'Part-Time'),
(UUID_TO_BIN(UUID()), 'Others');

insert into expense_category values
(UUID_TO_BIN(UUID()), 'Food'),
(UUID_TO_BIN(UUID()), 'Closing'),
(UUID_TO_BIN(UUID()), 'Car'),
(UUID_TO_BIN(UUID()), 'Health'),
(UUID_TO_BIN(UUID()), 'Sports'),
(UUID_TO_BIN(UUID()), 'Utilities'),
(UUID_TO_BIN(UUID()), 'Housing'),
(UUID_TO_BIN(UUID()), 'Education'),
(UUID_TO_BIN(UUID()), 'Transportation'),
(UUID_TO_BIN(UUID()), 'Phone'),
(UUID_TO_BIN(UUID()), 'Electronics'),
(UUID_TO_BIN(UUID()), 'Beauty'),
(UUID_TO_BIN(UUID()), 'Restaurants'),
(UUID_TO_BIN(UUID()), 'Travel'),
(UUID_TO_BIN(UUID()), 'Pets'),
(UUID_TO_BIN(UUID()), 'Repairs'),
(UUID_TO_BIN(UUID()), 'Lottery'),
(UUID_TO_BIN(UUID()), 'Gifts'),
(UUID_TO_BIN(UUID()), 'Donations');