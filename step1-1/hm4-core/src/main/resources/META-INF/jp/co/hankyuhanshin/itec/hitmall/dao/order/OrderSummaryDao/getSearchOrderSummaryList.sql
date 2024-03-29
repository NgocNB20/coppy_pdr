SELECT
    ordersummary.*
FROM
    ordersummary
WHERE
    ordersummary.memberInfoSeq = /*conditionDto.memberInfoSeq*/0
ORDER BY
    ordersummary.ordertime DESC
