DELETE
    FROM
        footprint
    WHERE
        shopseq = /*shopSeq*/0
        AND footprint.updatetime < (
            (
                (
                    CURRENT_DATE + 1
                ) + CAST (
                    /*specifiedDay*/0
                    || ' days' AS INTERVAL
                )
            )
        )
;
