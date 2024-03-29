SELECT *
FROM questionnaire
WHERE shopseq = /*shopSeq*/'1001'
  AND questionnairekey = /*questionnaireKey*/'order'
    /*%if siteType.value == "0" || siteType.value == "1"*/
  AND openstatuspc = '1'
    /*%end*/
    /*%if siteType.value == "3"*/
  AND openstatuspc <> '9'
    /*%end*/
  AND openstarttime <= /*currentTime*/'2015/05/30 23:59:59'
  AND openendtime >= /*currentTime*/'2015/5/01 00:00:00'