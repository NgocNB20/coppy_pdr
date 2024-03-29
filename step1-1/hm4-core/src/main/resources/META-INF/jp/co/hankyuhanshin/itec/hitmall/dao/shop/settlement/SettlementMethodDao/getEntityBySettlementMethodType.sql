SELECT
    *
FROM
    settlementmethod
WHERE
    settlementmethod.settlementmethodtype = /*settlementMethodType*/0
ORDER BY
	updateTime DESC OFFSET 0 LIMIT 1
