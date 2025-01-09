# FinTracker

**FinTracker** is a web application that helps users track their income and expenses, categorize financial data, and generate reports for better financial management.

## Features

- **Account Management**: Create accounts.
- **Income/Expense Records**: Add, edit, and delete income/expense records.
- **Data Display**: View financial records in a clear, organized format.
- **Filtering**: Filter transaction records by different time periods.
- **Reports**: Generate summary reports for the month, week, 3 months, 6 months, year.

## Technologies

- **Backend**: Java, Spring Boot, Hibernate
- **Data Storage**: MySQL (Relational Database), H2 (For Testing)
- **Security**: Spring Security for authentication and role management
- **Frontend**: HTML, CSS, JavaScript, Thymeleaf
- **Testing**: JUnit5, Mockito

## Installation

1. Download or clone the repository.
2. Change datasource config, then run FinTrackerApplication.
3. Run init_data_script.sql file to init user role and default categories for transactions.
4. Open `http://localhost:8080/` in a web browser. 
5. Create account or login with existed one. 
6. Use the navbar or sidebar to navigate through the pages. 
7. Press the "Logout" button to be redirected to the login page.
