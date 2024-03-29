SELECT 
    *
FROM
    orderDelivery
WHERE
    orderDelivery.orderSeq = /*orderSeq*/0
AND orderDelivery.orderDeliveryVersionNo = /*orderDeliveryVersionNo*/0
ORDER BY orderDelivery.orderconsecutiveno
