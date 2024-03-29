SELECT
    *
FROM
    settlementmethod
WHERE
    settlementmethod.settlementmethodname = /*settlementMethodName*/0
ORDER BY
	updateTime DESC OFFSET 0 LIMIT 1
