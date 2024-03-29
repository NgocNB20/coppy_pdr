SELECT
    *
FROM shop
WHERE
/*%if shopSeq != null*/
    shopseq = /*shopSeq*/0 AND
/*%end*/
/*%if siteType != null */
    /*%if siteType.value == "0" || siteType.value == "1"*/
        shopopenstatuspc = /*openStatus.value*/0 AND
    /*%end*/
    /*%if siteType.value == "2" */
        shopopenstatusmb = /*openStatus.value*/0 AND
    /*%end*/
/*%end*/
    1 = 1
