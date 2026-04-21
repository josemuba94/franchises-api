INSERT INTO Franchise (id, name) VALUES (10000000, 'KFC');

INSERT INTO Branch (id, name, franchise_id) VALUES (10000002, 'KFC-S', 10000000);
INSERT INTO Product (id, name, branch_id, stock) VALUES (10000021, 'Burger', 10000002, 20);
INSERT INTO Product (id, name, branch_id, stock) VALUES (10000022, 'Combo1', 10000002, 7);
INSERT INTO Product (id, name, branch_id, stock) VALUES (10000023, 'Sundae', 10000002, 14);
