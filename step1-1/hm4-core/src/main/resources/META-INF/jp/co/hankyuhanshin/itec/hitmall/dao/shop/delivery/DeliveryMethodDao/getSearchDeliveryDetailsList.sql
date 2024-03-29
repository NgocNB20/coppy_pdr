SELECT
    deliveryMethod.*,
    deliveryMethodTypeCarriage.prefectureType,
    deliveryMethodTypeCarriage.maxPrice,
    deliveryMethodTypeCarriage.carriage
FROM
    deliveryMethod
LEFT OUTER JOIN
    deliveryMethodTypeCarriage
ON
    deliveryMethod.deliveryMethodSeq = deliveryMethodTypeCarriage.deliveryMethodSeq
    AND
    (
        (
        deliveryMethod.deliveryMethodType = '1'
        AND deliveryMethodTypeCarriage.prefectureType = /*conditionDto.prefectureType.value*/0
        )
        OR
        (
        deliveryMethod.deliveryMethodType = '2'
        AND deliveryMethodTypeCarriage.maxPrice >= /*conditionDto.totalGoodsPrice*/0
        )
    )

WHERE 1 = 1
/*%if conditionDto.shopSeq != null*/
    AND deliveryMethod.shopSeq = /*conditionDto.shopSeq*/0
/*%end*/
/*%if conditionDto.openStatusPC != null*/
    AND deliveryMethod.openStatusPC = /*conditionDto.openStatusPC.value*/0
/*%end*/
/*%if conditionDto.openStatusMB != null*/
    AND deliveryMethod.openStatusMB = /*conditionDto.openStatusMB.value*/0
/*%end*/
/*%if conditionDto.openStatusPC == null && conditionDto.openStatusMB == null*/
and
    deliverymethod.openstatuspc <> '9'
and
    deliverymethod.openstatusmb <> '9'
/*%end*/
ORDER BY
    deliveryMethod.orderDisplay,
    deliveryMethod.deliveryMethodSeq,
    deliveryMethodTypeCarriage.maxPrice
