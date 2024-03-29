SELECT
    CASE
    WHEN MAX(orderdisplay) IS NULL THEN
        1
    ELSE
        MAX(orderdisplay) + 1
    END AS maxOrderDisplay
FROM
    settlementmethod
WHERE
    shopseq = /*shopSeq*/0
