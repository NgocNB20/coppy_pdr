SELECT
  freearea.*
FROM
  freearea
LEFT JOIN
    freeareaviewablemember
ON
    freearea.freeareaseq = freeareaviewablemember.freeareaseq
WHERE
  sitemapflag = '1'
/*%if urlType == "5"*/
AND
  targetgoods IS NULL
/*%end*/
/*%if urlType == "6"*/
AND
  targetgoods IS NOT NULL
/*%end*/
AND
(
    (openstarttime <= /*currentTime*/0)

)
AND
    freeareaviewablemember.freeareaseq IS NULL
AND
NOT EXISTS
(
SELECT freeareakey
FROM freearea AS MAX
WHERE
freearea.freeareakey = MAX.freeareakey
AND
freearea.openstarttime < MAX.openstarttime
AND
(
    (openstarttime <= /*currentTime*/0)

)
)
ORDER BY
   updatetime  DESC
  ,freeareaseq DESC
;
