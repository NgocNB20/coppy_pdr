SELECT count(*)
FROM questionnaire

WHERE shopseq = /*shopSeq*/'1001'
  AND questionnairekey = /*questionnaireKey*/'order201505'
  AND openstatuspc <> '9'
  AND openstarttime <= /*openEndTime*/'2015/05/30 23:59:59'
  AND openendtime >= /*openStartTime*/'2015/5/01 00:00:00'
