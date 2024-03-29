SELECT
	count (1)
FROM
	(SELECT
        ordertime
    FROM
        ordersummary
    WHERE
        ordersummary.memberInfoSeq = /*memberInfoSeq*/0
        AND TO_CHAR(current_date, 'YYYY-MM-DD') = TO_CHAR(ordertime, 'YYYY-MM-DD')
    GROUP BY
        ordertime) as uniqueOrder