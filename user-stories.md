# User stories

1. As an admin, I want to sign in so that doctor management is restricted.
2. As an admin, I want to add a doctor with specialty and slots so that patients can find care.
3. As an admin, I want to update or remove a doctor so that the directory stays current.
4. As a patient, I want to register and sign in so that I can manage my appointments.
5. As a patient, I want to search by doctor name, specialty and time so that I find suitable care.
6. As a patient, I want to book an available slot so that conflicting appointments are rejected.
7. As a patient, I want to view and cancel my own appointments so that I can manage my schedule.
8. As a doctor, I want to sign in and see appointments for a date so that I can plan consultations.
9. As a doctor, I want to prescribe medication for my own appointment so that treatment is recorded.

Acceptance criteria: invalid credentials fail; protected routes require the correct identity and role; searches return matching doctors; occupied slots are excluded; one doctor cannot prescribe for another doctor's appointment; patient records never expose password hashes.
