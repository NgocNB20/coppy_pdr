SELECT
        COUNT (*)
    FROM
        accessinfo
    WHERE
        shopseq = /*shopSeq*/0
        AND accessdate < (
            (
                CURRENT_DATE + 1
            ) + CAST (
                /*specifiedDay*/0
                || ' days' AS INTERVAL
            )
        )
;
