INSERT INTO companies (name, phone_number, address, email, username, password) VALUES
('cybernet','0705001020','xetai street','cyb@gmail.com','admincybernet','cybernet123'),
('ABB','0706001540','nizami street','ABB@gmail.com','adminabb','abb123'),
('azericard','0773002345','bulbul street','azeri@gmail.com','adminazericard','azericard123');

INSERT INTO employees (name, surname, email, password, company_id) VALUES
('rovshan','huseynov','rovshan@gmail.com','rovshan123',1),
('ali','alizade','ali@gmail.com','ali123',2),
('semed','semedov','semed@gmail.com','semed123',1);

INSERT INTO tasks (company_id, title, description, deadline, status) VALUES
(1,'email automation', 'automate all the emails from customer', '2022-06-01', 0),
(2,'parse log', 'parse and categorize all the logs into database', '2022-06-02', 1),
(1,'listen complaints', 'listen and categorize all the complaints from customers', '2022-06-03', 2);

INSERT INTO employee_task (employee_id, task_id) VALUES
(1,1),
(2,2),
(3,1);

SELECT * FROM companies;
SELECT * FROM employees;
SELECT * FROM tasks;
SELECT * FROM employee_task;