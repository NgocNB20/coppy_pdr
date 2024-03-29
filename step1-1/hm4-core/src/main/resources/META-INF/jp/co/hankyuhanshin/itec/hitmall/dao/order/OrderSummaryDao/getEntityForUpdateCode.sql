SELECT 
    *
FROM
    ordersummary
WHERE
    ordersummary.ordercode = /*orderCode*/0
/*%if shopSeq != null*/
    and ordersummary.shopseq = /*shopSeq*/0
/*%end*/
/*%if orderVersionNo != null*/
    and ordersummary.orderversionno = /*orderVersionNo*/0
/*%end*/
FOR UPDATE NOWAIT 
