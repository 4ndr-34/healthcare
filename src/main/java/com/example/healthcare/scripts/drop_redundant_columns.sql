ALTER TABLE prescription DROP COLUMN `dosage`, DROP COLUMN `frequency`, DROP COLUMN `duration`, DROP COLUMN `refills`, DROP COLUMN `status`;

ALTER TABLE billing DROP COLUMN due_date,
                    DROP COLUMN status,
                    DROP COLUMN insurance_claim_id,
                    DROP COLUMN insurance_coverage,
                    DROP COLUMN payment_date;