SELECT
  *
FROM
  news
WHERE
  shopseq=/*conditionDto.shopSeq*/0
/*%if conditionDto.openStatus != null && conditionDto.siteType != null */
  /*%if conditionDto.siteType.value == "0" || conditionDto.siteType.value == "1"*/
AND
  (newsopenstarttimepc is null OR newsopenstarttimepc <= CURRENT_TIMESTAMP)
AND
  (newsopenendtimepc is null OR newsopenendtimepc >= CURRENT_TIMESTAMP)
AND
  newsopenstatuspc = /*conditionDto.openStatus.value*/0
  /*%end*/
  /*%if conditionDto.siteType.value == "2" */
AND
  (newsopenstarttimemb is null OR newsopenstarttimemb <= CURRENT_TIMESTAMP)
AND
  (newsopenendtimemb is null OR newsopenendtimemb >= CURRENT_TIMESTAMP)
AND
  newsopenstatusmb = /*conditionDto.openStatus.value*/0
  /*%end*/
/*%end*/
/*%if conditionDto.memberInfoSeq != null && conditionDto.memberInfoSeq != 0*/
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
        newsviewablemember.memberinfoseq = /*conditionDto.memberInfoSeq*/0
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
ORDER BY
	/*%if conditionDto.pageInfo.orderField == "newstime"*/
	 NEWS.NEWSTIME DESC, NEWS.NEWSSEQ DESC
	/*%else*/
	 1 ASC
	/*%end*/
