SELECT
    news.*
FROM
    news
LEFT JOIN
    newsviewablemember
ON
    news.newsseq = newsviewablemember.newsseq
WHERE
/*%if siteType == "0"*/
  (
    newsopenstatuspc = '1'
  AND
    (newsopenstarttimepc <= /*currentTime*/0 OR newsopenstarttimepc IS NULL)
  AND
    (newsopenendtimepc   >= /*currentTime*/0 OR newsopenendtimepc   IS NULL)
  )
  AND
    newsnotepc IS NOT NULL
/*%end*/
/*%if siteType == "2"*/
  (
    newsopenstatusmb = '1'
  AND
    (newsopenstarttimemb <= /*currentTime*/0 OR newsopenstarttimemb IS NULL)
  AND
    (newsopenendtimemb   >= /*currentTime*/0 OR newsopenendtimemb   IS NULL)
  )
  AND
    newsnotemb IS NOT NULL
/*%end*/
AND
    newsviewablemember.newsseq IS NULL
ORDER BY
   updatetime DESC
  ,newsseq DESC
;
