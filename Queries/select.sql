-- Gets the information of the last 7 days
SELECT id, consumption
FROM NetComputing.ncProduction
WHERE DATEDIFF(NOW(), date_added) <= 7;

-- Gets the information of the last 30 days
SELECT id, consumption
FROM NetComputing.ncProduction
WHERE DATEDIFF(NOW(), date_added) <= 30;

-- Gets the information of the last 365 days
SELECT id, consumption
FROM NetComputing.ncProduction
WHERE DATEDIFF(NOW(), date_added) <= 365;