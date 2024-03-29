DELETE
    FROM
        cartgoods
    WHERE
        shopseq = /*shopSeq*/0
        AND cartgoods.updatetime < (
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
