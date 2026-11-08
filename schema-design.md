# Relational schema and document schema

| Table | Fields | Keys and relations |
|---|---|---|
| admin | id BIGINT, username VARCHAR(255), password VARCHAR(255) | PK id |
| doctor | id BIGINT, name VARCHAR(100), specialty VARCHAR(50), email VARCHAR(255), password VARCHAR(255), phone VARCHAR(10) | PK id; unique email |
| patient | id BIGINT, name VARCHAR(100), email VARCHAR(255), password VARCHAR(255), phone VARCHAR(10), address VARCHAR(255) | PK id; unique email |
| appointment | id BIGINT, doctor_id BIGINT, patient_id BIGINT, appointment_time DATETIME(6), status INT | PK id; FK doctor_id → doctor.id; FK patient_id → patient.id; unique (doctor_id, appointment_time) |
| doctor_available_times | doctor_id BIGINT, available_times VARCHAR(255) | FK doctor_id → doctor.id; JPA element collection |

An appointment belongs to one doctor and one patient; each can have many appointments. Status 0 = scheduled, 1 = completed. Passwords are BCrypt hashes.

MongoDB `prescriptions`: `_id` ObjectId, `patientName` string, `appointmentId` long (logical reference to MySQL), `medication` string, `dosage` string, `doctorNotes` string. Application authorization verifies that the prescribing doctor owns the appointment. The two databases do not provide a distributed transaction; saving a prescription precedes the appointment status change.
