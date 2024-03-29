SELECT
        COUNT (*)
    FROM
        accesssearchkeyword
    WHERE
        shopseq = /*shopSeq*/0
        AND accesstime < (
            (
                CURRENT_DATE + 1
            ) + CAST (
                /*specifiedDay*/0
                || ' days' AS INTERVAL
            )
        )
;
