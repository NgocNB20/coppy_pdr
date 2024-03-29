SELECT
    open_fa.freeareakey
    , open_fa.freeareaTitle
    , open_fa.uktransitionurl
    , open_fa.ukcontentimageurl
    , open_fa.uksearchkeyword as searchKeyword
FROM
    (
        SELECT
            freeareakey
            , freeareaTitle
            , uktransitionurl
            , ukcontentimageurl
            , uksearchkeyword
            , ukfeedinfosendflag
        FROM
            freearea
        WHERE
            (freeareakey, openstarttime) IN (
                SELECT
                    freeareakey
                    , MAX(openstarttime)
                FROM
                    freearea
                WHERE
                    openstarttime <= /*currentTime*/0
                GROUP BY
                    freeareakey
            )
    ) open_fa
WHERE
    open_fa.ukfeedinfosendflag = '1'
AND
    open_fa.freeAreaTitle IS NOT NULL
AND
    open_fa.uktransitionurl IS NOT NULL
ORDER BY
    open_fa.freeareakey ASC
