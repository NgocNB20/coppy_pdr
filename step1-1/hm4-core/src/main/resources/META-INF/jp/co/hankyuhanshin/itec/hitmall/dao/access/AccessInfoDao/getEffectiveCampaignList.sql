SELECT *
FROM accessinfo
WHERE
shopSeq = /*shopSeq*/0
AND accessUid = /*accessUid*/0
AND accesstype = 'C001'
AND accessdate > (
            (
                CURRENT_DATE
            ) + CAST (
                '-' || /*specifiedDay*/0
                || ' days' AS INTERVAL
            )
        )
/*%if prefix != null */
AND campaigncode ~* /*prefix*/0
/*%end*/
ORDER BY updatetime DESC
OFFSET 0 LIMIT /*limit*/1