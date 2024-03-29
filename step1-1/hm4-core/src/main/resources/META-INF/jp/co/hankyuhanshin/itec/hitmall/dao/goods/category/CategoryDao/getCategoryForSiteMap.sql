SELECT
  *
FROM
  category
WHERE
/*%if siteType == "0"*/
  (
    category.categoryopenstatuspc = '1'
  AND
     (category.categoryopenstarttimepc <= /*currentTime*/0 OR category.categoryopenstarttimepc IS NULL)
  AND
    (category.categoryopenendtimepc    >= /*currentTime*/0 OR category.categoryopenendtimepc   IS NULL)
  )
/*%end*/
/*%if siteType == "2"*/
  (
    category.categoryopenstatusmb = '1'
  AND
     (category.categoryopenstarttimemb <= /*currentTime*/0 OR category.categoryopenstarttimemb IS NULL)
  AND
    (category.categoryopenendtimemb    >= /*currentTime*/0 OR category.categoryopenendtimemb   IS NULL)
  )
/*%end*/
AND
  category.sitemapflag = '1'
ORDER BY
   updatetime DESC
  ,categoryseq DESC
;
