# Smart Clinic Management System

Spring Boot 3, Java 17, MySQL, MongoDB, Thymeleaf and browser JavaScript.

## Source attribution
This educational implementation adapts IBM Developer Skills Network's Apache-2.0 [starter](https://github.com/ibm-developer-skills-network/okssu-java-database-capstone-template) and [reference implementation](https://github.com/ibm-developer-skills-network/pgcjz-java-database-capstone-final). Original notices and LICENSE are retained. Additional work includes environment-based configuration, BCrypt password hashing, authorization checks, data constraints, demo seeding, SQL reports, CI and regression tests. This is an assisted learning project; review and understand it before submitting any authorship declaration.

## Run
Set `DB_PASSWORD` and `JWT_SECRET` (32+ UTF-8 bytes), optionally `DB_URL`, `DB_USER`, `MONGODB_URI`. Run MySQL 8 with database `cms` and MongoDB 7. Then `cd app && mvn spring-boot:run -Dspring-boot.run.profiles=demo`.

Demo accounts: `admin`, `doctor1@example.com`, `patient1@example.com`; demo password `ClinicDemo2026!`. Demo users are fictional and created only with the explicit `demo` profile.

## Validation
`cd app && mvn clean verify`. SQL definitions in `database/reports.sql`. Evidence must be generated from a running instance, not substituted with expected output.

## Architecture
The browser UI calls Spring MVC controllers; services enforce business logic and identity checks; JPA repositories persist admins/doctors/patients/appointments in MySQL, Spring Data MongoDB stores prescriptions. This teaching application uses the course's path-token routes for frontend compatibility. A production version should use authorization headers and secure cookies instead.
