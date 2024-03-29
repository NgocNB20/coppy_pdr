DELETE
    FROM
        adminconfirmmail
    WHERE
        shopseq = /*shopSeq*/0
        AND effectivetime < (
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
