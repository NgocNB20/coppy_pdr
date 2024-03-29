SELECT
  *
FROM
  news
WHERE
  shopseq = /*shopSeq*/0
AND
  newsseq = /*newsSeq*/0
/*%if memberInfoSeq != null && memberInfoSeq != 0*/
AND
  (
    EXISTS
      (
      SELECT
        1
      FROM
        newsviewablemember
      WHERE
        newsviewablemember.newsseq = news.newsseq
      AND
        newsviewablemember.memberinfoseq = /*memberInfoSeq*/0
      )
  OR
    NOT EXISTS
      (
      SELECT
        1
      FROM
        newsviewablemember
      WHERE
        newsviewablemember.newsseq = news.newsseq
      )
  )
  /*%else*/
AND
  NOT EXISTS
    (
    SELECT
      1
    FROM
      newsviewablemember
    WHERE
      newsviewablemember.newsseq = news.newsseq
    )
/*%end*/
