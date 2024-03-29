SELECT
    *
FROM shop
WHERE
/*%if shopSeq != null*/
    shopseq = /*shopSeq*/0 AND
/*%end*/
/*%if siteType != null */
    /*%if siteType.value == "0" || siteType.value == "1"*/
    shopopenstatuspc = /*openStatus.value*/0
        AND
        (
            shopopenstarttimepc is null
            OR
            shopopenstarttimepc <= current_timestamp
        )
        AND
        (
            shopopenendtimepc is null
            OR
            shopopenendtimepc >= current_timestamp
        )
        AND
    /*%end*/
    /*%if siteType.value == "2"*/
    shopopenstatusmb = /*openStatus.value*/0
        AND
        (
            shopopenstarttimemb is null
            OR
            shopopenstarttimemb <= current_timestamp
        )
        AND
        (
            shopopenendtimemb is null
            OR
            shopopenendtimemb >= current_timestamp
        )
        AND
    /*%end*/
/*%end*/
    1 = 1
