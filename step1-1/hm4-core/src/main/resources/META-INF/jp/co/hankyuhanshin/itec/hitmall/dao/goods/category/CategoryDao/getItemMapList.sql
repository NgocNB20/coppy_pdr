SELECT
    categoryseq,
    categoryid,
    categoryname,
    categorypath
FROM
    category
WHERE
    shopseq = /*shopSeq*/0
ORDER BY
    categorypath
