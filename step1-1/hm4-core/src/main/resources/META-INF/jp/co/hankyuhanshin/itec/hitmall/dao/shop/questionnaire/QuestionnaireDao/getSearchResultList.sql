SELECT questionnaire.questionnaireseq,
       questionnairekey,
       name,
       openstatuspc,
       openstarttime,
       openendtime,
       replycount,
       sitemapflag
FROM questionnaire
         LEFT OUTER JOIN
     questionnairereplytotal
     ON
             questionnaire.questionnaireseq = questionnairereplytotal.questionnaireseq
WHERE
    /*%if conditionDto.shopSeq != null*/
    shopseq = /*conditionDto.shopSeq*/0
    /*%end*/
    /*%if conditionDto.questionnaireSeq != null*/
  AND questionnaire.questionnaireseq = /*conditionDto.questionnaireSeq*/0
    /*%end*/
    /*%if conditionDto.questionnaireKey != null*/
  AND questionnairekey LIKE '%' || /*conditionDto.questionnaireKey*/0 || '%'
    /*%end*/
    /*%if conditionDto.name != null*/
  AND (name LIKE '%' || /*conditionDto.name*/0 || '%' OR namepc LIKE '%' || /*conditionDto.name*/0 || '%')
    /*%end*/
    /*%if conditionDto.replyCountFrom != null*/
  AND replycount >= /*conditionDto.replyCountFrom*/0
    /*%end*/
    /*%if conditionDto.replyCountTo != null*/
  AND replycount <= /*conditionDto.replyCountTo*/0
    /*%end*/
    /*%if conditionDto.memo != null*/
  AND memo LIKE '%' || /*conditionDto.memo*/0 || '%'
    /*%end*/
    /*%if conditionDto.openStatus != null*/
    /*%if conditionDto.site == null */
  AND (openstatuspc = /*conditionDto.openStatus.value*/0 )
  AND openstatuspc <> '9'
    /*%end*/
    /*%if conditionDto.site == "0"  */
  AND (openstatuspc = /*conditionDto.openStatus.value*/0 )
  AND openstatuspc <> '9'
    /*%end*/
    /*%if conditionDto.site == "1" */
  AND openstatuspc = /*conditionDto.openStatus.value*/0
    /*%end*/
    /*%if conditionDto.site == "2" */
  AND openstatuspc <> '9'
    /*%end*/
    /*%else*/
  AND openstatuspc <> '9'
    /*%end*/
    /*%if conditionDto.openStartTimeFrom != null*/
  AND openstarttime >= /*conditionDto.openStartTimeFrom*/0
    /*%end*/
    /*%if conditionDto.openStartTimeTo != null*/
  AND openstarttime <= /*conditionDto.openStartTimeTo*/0
    /*%end*/
    /*%if conditionDto.openEndTimeFrom != null*/
  AND openendtime >= /*conditionDto.openEndTimeFrom*/0
    /*%end*/
    /*%if conditionDto.openEndTimeTo != null*/
  AND openendtime <= /*conditionDto.openEndTimeTo*/0
    /*%end*/
    /*%if conditionDto.siteMapFlag != null*/
  AND sitemapflag = /*conditionDto.siteMapFlag.value*/0
    /*%end*/
ORDER BY
/*%if conditionDto.pageInfo != null */
    /*%if conditionDto.pageInfo.orderField == "questionnaireSeq"*/
    questionnaireseq /*%if conditionDto.pageInfo.orderAsc == true*/ ASC /*%else*/ DESC /*%end*/
    /*%elseif conditionDto.pageInfo.orderField == "questionnaireKey"*/
    questionnairekey /*%if conditionDto.pageInfo.orderAsc == true*/ ASC /*%else*/ DESC /*%end*/
    /*%elseif conditionDto.pageInfo.orderField == "name"*/
    name /*%if conditionDto.pageInfo.orderAsc == true*/ ASC /*%else*/ DESC /*%end*/
    /*%elseif conditionDto.pageInfo.orderField == "openStartTime"*/
    openstarttime /*%if conditionDto.pageInfo.orderAsc == true*/ ASC /*%else*/ DESC /*%end*/
    /*%elseif conditionDto.pageInfo.orderField == "openEndTime"*/
    openendtime /*%if conditionDto.pageInfo.orderAsc == true*/ ASC /*%else*/ DESC /*%end*/
    /*%elseif conditionDto.pageInfo.orderField == "replyCount"*/
    replycount /*%if conditionDto.pageInfo.orderAsc == true*/ ASC /*%else*/ DESC /*%end*/
    /*%elseif conditionDto.pageInfo.orderField == "siteMapFlag"*/
    sitemapflag /*%if conditionDto.pageInfo.orderAsc == true*/ ASC /*%else*/ DESC /*%end*/
    /*%else*/ 1 ASC /*%end*/
       /*%else*/ 1 ASC /*%end*/
