SELECT
    coalesce(MAX(orderdisplay) + 1, 1) AS orderdisplay
FROM
    category
WHERE
    shopseq = /*shopSeq*/0
    AND categoryseqpath LIKE /*categorySeqPath*/0 || '%'
    AND categorylevel = /*categoryLevel*/0
