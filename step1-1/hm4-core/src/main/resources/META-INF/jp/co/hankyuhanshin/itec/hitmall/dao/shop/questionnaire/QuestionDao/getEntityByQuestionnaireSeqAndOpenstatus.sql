SELECT *
FROM question
WHERE questionnaireseq = /*questionnaireSeq*/0
    /*%if siteType.value != "3"*/
  AND openstatus = '1'
    /*%end*/
ORDER BY orderdisplay ASC
