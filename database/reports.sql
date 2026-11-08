USE cms;
DELIMITER $$
CREATE PROCEDURE GetDailyAppointmentReportByDoctor(IN reportDate DATE)
BEGIN
 SELECT d.id AS doctor_id,d.name AS doctor_name,DATE(a.appointment_time) AS appointment_date,COUNT(a.id) AS appointments
 FROM doctor d JOIN appointment a ON a.doctor_id=d.id
 WHERE DATE(a.appointment_time)=reportDate GROUP BY d.id,d.name,DATE(a.appointment_time) ORDER BY d.id;
END$$
CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(IN reportYear INT, IN reportMonth INT)
BEGIN
 SELECT d.id AS doctor_id,d.name AS doctor_name,COUNT(DISTINCT a.patient_id) AS patients
 FROM doctor d JOIN appointment a ON a.doctor_id=d.id
 WHERE YEAR(a.appointment_time)=reportYear AND MONTH(a.appointment_time)=reportMonth
 GROUP BY d.id,d.name ORDER BY patients DESC,d.id LIMIT 1;
END$$
CREATE PROCEDURE GetDoctorWithMostPatientsByYear(IN reportYear INT)
BEGIN
 SELECT d.id AS doctor_id,d.name AS doctor_name,COUNT(DISTINCT a.patient_id) AS patients
 FROM doctor d JOIN appointment a ON a.doctor_id=d.id WHERE YEAR(a.appointment_time)=reportYear
 GROUP BY d.id,d.name ORDER BY patients DESC,d.id LIMIT 1;
END$$
DELIMITER ;
