SELECT *
FROM questionnaire
WHERE questionnairekey <> 'order'
  AND sitemapflag = '1'
    /*%if siteType == "0"*/
  AND openstatuspc = '1'
/*%end*/
  AND openstarttime <= /*currentTime*/''
  AND openendtime >= /*currentTime*/''
ORDER BY updatetime DESC
       , questionnaireseq DESC