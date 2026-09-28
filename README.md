# Community Smart Compost & Organic Waste Circular Fertilizer Hub

A beginner-friendly end-to-end Java Spring Boot + MySQL project implementing the supplied problem statement.

## Included
- Admin/F​​acility operations dashboard
- Contact Master: Commercial Farm, Waste Collection Contractor, Bio-Lab
- Product Master: fertilizer, waste ingestion, testing
- Chart of Accounts
- Bio-digester temperature parameters and safety threshold
- Organic waste intake
- Compost/fertilizer batch processing and stock tracking
- Purchase Orders
- Vendor Bills and open liabilities
- Sales Orders
- Customer Invoices
- Bank/Cash Payments
- Journal Entries
- Analytic Account + Budget
- Balance Sheet, P&L and Budget report APIs
- Thymeleaf HTML dashboard
- REST APIs
- MySQL + JPA/Hibernate
- Seed/sample data

## Requirements
- Java 21
- Maven 3.9+
- MySQL 8+

## Database
Create the database:
```sql
CREATE DATABASE compost_hub;
```
Or run `database/compost_hub.sql`.

Then edit:
`src/main/resources/application.properties`

Set:
spring.datasource.username=${MYSQL_USERNAME:root}
spring.datasource.password=${MYSQL_PASSWORD}

Before starting the application in PowerShell, set the credentials for your local MySQL installation:

```powershell
$env:MYSQL_USERNAME = "root"
$env:MYSQL_PASSWORD = "your-mysql-password"
```

## Run in VS Code
Open the project folder (the folder containing pom.xml), then:
```powershell
mvn spring-boot:run
```

Open:
http://localhost:8080

## Main REST APIs
POST /api/compost/digesters
POST /api/compost/intake
POST /api/compost/batches
POST /api/compost/contacts
POST /api/compost/products
POST /api/compost/accounts
POST /api/compost/purchase-orders
POST /api/compost/vendor-bills
POST /api/compost/sales-orders
POST /api/compost/customer-invoices
POST /api/compost/payments
POST /api/compost/budgets
POST /api/compost/journal-entries

Reports:
GET /api/compost/reports/pl
GET /api/compost/reports/budget
GET /api/compost/reports/balance-sheet

## Transaction flow
1. Create Bio-Lab / sensor calibration Purchase Order.
2. Convert it into Vendor Bill.
3. Record Bank payment.
4. Create fertilizer Sales Order for commercial farm.
5. Create Customer Invoice.
6. Record customer Bank payment.
7. Record journal entries for revenue, stock, electricity and testing costs.

## Important
This is a college-project implementation of the business requirements. It is designed to be easy to understand and extend. Authentication/authorization and production-grade accounting controls can be added later.
