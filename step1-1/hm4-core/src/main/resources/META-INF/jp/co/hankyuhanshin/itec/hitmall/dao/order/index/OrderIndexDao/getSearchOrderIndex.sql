SELECT
    orderindex.*
FROM orderindex,
    ordersummary
WHERE
    orderindex.orderseq = ordersummary.orderseq
/*%if conditionDto.orderCode != null */
AND ordersummary.ordercode = /*conditionDto.orderCode*/0
/*%end*/
/*%if conditionDto.shopSeq != null */
AND orderindex.shopseq = /*conditionDto.shopSeq*/0
/*%end*/

ORDER BY
/*%if conditionDto.pageInfo.orderField != null*/
    /*%if conditionDto.pageInfo.orderField == "orderVersionNo"*/
     orderindex.orderversionno /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/
/*%else*/
    1 ASC
/*%end*/
