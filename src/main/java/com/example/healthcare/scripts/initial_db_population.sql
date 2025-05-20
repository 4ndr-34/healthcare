USE healthcare;

-- Insert into patient table with all fields (including those inherited from User)
REPLACE INTO patient (id, first_name, last_name, birth_date, gender, address, city, country, zip_code, phone_number, email, password,
                     insurance_provider, role, insurance_policy_number, emergency_contact_name, emergency_contact_phone)
VALUES
    (1, 'John', 'Smith', '1985-06-12', 'M', '123 Main St', 'Boston', 'USA', '02115', '555-123-4567', 'john.smith@example.com', 'password123',
     'Blue Cross', 'PATIENT', 'BC1234567', 'Sarah Smith', '555-111-2222'),

    (2, 'Emily', 'Johnson', '1992-03-24', 'F', '456 Oak Ave', 'Chicago', 'USA', '60007', '555-987-6543', 'emily.johnson@example.com', 'securepass',
     'Aetna', 'PATIENT', 'AE7654321', 'Robert Johnson', '555-333-4444'),

    (3, 'Michael', 'Wong', '1978-11-08', 'M', '789 Pine Blvd', 'San Francisco', 'USA', '94107', '555-456-7890', 'michael.wong@example.com', 'simpletext',
     'Kaiser Permanente', 'PATIENT', 'KP9876543', 'Lisa Wong', '555-555-6666');


-- Insert into staff table with all fields (including those inherited from User)
REPLACE INTO staff (id, first_name, last_name, birth_date, gender, address, city, country, zip_code, phone_number, email, password,
                   specialty, license_number, department, role, active)
VALUES
    (1, 'David', 'Williams', '1975-08-15', 'M', '234 Cedar Dr', 'New York', 'USA', '10001', '555-222-3333', 'david.williams@example.com', 'doctor123',
     'Cardiology', 'MD12345', 'Cardiac Care', 'DOCTOR', true),

    (2, 'Sarah', 'Chen', '1982-04-20', 'F', '567 Maple Ave', 'Los Angeles', 'USA', '90001', '555-444-5555', 'sarah.chen@example.com', 'nurse567',
     'Pediatrics', 'RN78901', 'Children Ward', 'NURSE', true),

    (3, 'James', 'Rodriguez', '1979-12-10', 'M', '890 Elm St', 'Miami', 'USA', '33101', '555-666-7777', 'james.rodriguez@example.com', 'staff999',
     'Orthopedics', 'MD24680', 'Surgery', 'DOCTOR', true);

ALTER TABLE appointment DROP CONSTRAINT appointment_chk_1;

-- Insert into appointment table with patientID=1 and staff_id=1
REPLACE INTO appointment (id, appointment_date_and_time, appointment_notes, appointment_status, created_at, last_updated, patient_id, staff_id, medical_record_id, billing_id)
VALUES
(null, '2024-10-15 09:30:00', 'Initial consultation for heart condition', 'REQUESTED', '2024-10-01', '2024-10-01', 1, 1, NULL, NULL),

(null, '2024-10-22 10:00:00', 'Follow-up appointment to review test results', 'REQUESTED', '2024-10-01', '2024-10-01', 1, 1, NULL, NULL),

(null, '2024-11-05 14:45:00', 'Quarterly checkup for ongoing treatment', 'REQUESTED', '2024-10-02', '2024-10-02', 1, 1, NULL, NULL);

