SELECT
    qn.questionnaireseq                                    AS questionnaireSeq
     , qnr.questionnairereplyseq                            AS questionnaireReplySeq
     , MIN(qnr.registTime)                                  AS registTime
     , MIN(qnr.siteType)                                    AS siteType
     , MIN(qnr.memberInfoSeq)                               AS memberInfoSeq
     , MIN(m.memberInfoId)                                  AS memberInfoId
     , MIN(qnr.orderCode)                                   AS orderCode
     , MIN(qn.questionnairekey)                             AS questionnaireKey
     , MIN(qn.name)                                         AS name
     , MIN(CASE q.orderdisplay WHEN 1 THEN q.question END)  AS question1
     , MIN(CASE q.orderdisplay WHEN 1 THEN qr.reply END)    AS reply1
     , MIN(CASE q.orderdisplay WHEN 2 THEN q.question END)  AS question2
     , MIN(CASE q.orderdisplay WHEN 2 THEN qr.reply END)    AS reply2
     , MIN(CASE q.orderdisplay WHEN 3 THEN q.question END)  AS question3
     , MIN(CASE q.orderdisplay WHEN 3 THEN qr.reply END)    AS reply3
     , MIN(CASE q.orderdisplay WHEN 4 THEN q.question END)  AS question4
     , MIN(CASE q.orderdisplay WHEN 4 THEN qr.reply END)    AS reply4
     , MIN(CASE q.orderdisplay WHEN 5 THEN q.question END)  AS question5
     , MIN(CASE q.orderdisplay WHEN 5 THEN qr.reply END)    AS reply5
     , MIN(CASE q.orderdisplay WHEN 6 THEN q.question END)  AS question6
     , MIN(CASE q.orderdisplay WHEN 6 THEN qr.reply END)    AS reply6
     , MIN(CASE q.orderdisplay WHEN 7 THEN q.question END)  AS question7
     , MIN(CASE q.orderdisplay WHEN 7 THEN qr.reply END)    AS reply7
     , MIN(CASE q.orderdisplay WHEN 8 THEN q.question END)  AS question8
     , MIN(CASE q.orderdisplay WHEN 8 THEN qr.reply END)    AS reply8
     , MIN(CASE q.orderdisplay WHEN 9 THEN q.question END)  AS question9
     , MIN(CASE q.orderdisplay WHEN 9 THEN qr.reply END)    AS reply9
     , MIN(CASE q.orderdisplay WHEN 10 THEN q.question END) AS question10
     , MIN(CASE q.orderdisplay WHEN 10 THEN qr.reply END)   AS reply10
     , MIN (CASE q.orderdisplay WHEN 11 THEN q.question END ) AS question11
     , MIN (CASE q.orderdisplay WHEN 11 THEN qr.reply END ) AS reply11
     , MIN (CASE q.orderdisplay WHEN 12 THEN q.question END ) AS question12
     , MIN (CASE q.orderdisplay WHEN 12 THEN qr.reply END ) AS reply12
     , MIN (CASE q.orderdisplay WHEN 13 THEN q.question END ) AS question13
     , MIN (CASE q.orderdisplay WHEN 13 THEN qr.reply END ) AS reply13
     , MIN (CASE q.orderdisplay WHEN 14 THEN q.question END ) AS question14
     , MIN (CASE q.orderdisplay WHEN 14 THEN qr.reply END ) AS reply14
     , MIN (CASE q.orderdisplay WHEN 15 THEN q.question END ) AS question15
     , MIN (CASE q.orderdisplay WHEN 15 THEN qr.reply END ) AS reply15
     , MIN (CASE q.orderdisplay WHEN 16 THEN q.question END ) AS question16
     , MIN (CASE q.orderdisplay WHEN 16 THEN qr.reply END ) AS reply16
     , MIN (CASE q.orderdisplay WHEN 17 THEN q.question END ) AS question17
     , MIN (CASE q.orderdisplay WHEN 17 THEN qr.reply END ) AS reply17
     , MIN (CASE q.orderdisplay WHEN 18 THEN q.question END ) AS question18
     , MIN (CASE q.orderdisplay WHEN 18 THEN qr.reply END ) AS reply18
     , MIN (CASE q.orderdisplay WHEN 19 THEN q.question END ) AS question19
     , MIN (CASE q.orderdisplay WHEN 19 THEN qr.reply END ) AS reply19
     , MIN (CASE q.orderdisplay WHEN 20 THEN q.question END ) AS question20
     , MIN (CASE q.orderdisplay WHEN 20 THEN qr.reply END ) AS reply20
     , MIN (CASE q.orderdisplay WHEN 21 THEN q.question END ) AS question21
     , MIN (CASE q.orderdisplay WHEN 21 THEN qr.reply END ) AS reply21
     , MIN (CASE q.orderdisplay WHEN 22 THEN q.question END ) AS question22
     , MIN (CASE q.orderdisplay WHEN 22 THEN qr.reply END ) AS reply22
     , MIN (CASE q.orderdisplay WHEN 23 THEN q.question END ) AS question23
     , MIN (CASE q.orderdisplay WHEN 23 THEN qr.reply END ) AS reply23
     , MIN (CASE q.orderdisplay WHEN 24 THEN q.question END ) AS question24
     , MIN (CASE q.orderdisplay WHEN 24 THEN qr.reply END ) AS reply24
     , MIN (CASE q.orderdisplay WHEN 25 THEN q.question END ) AS question25
     , MIN (CASE q.orderdisplay WHEN 25 THEN qr.reply END ) AS reply25
     , MIN (CASE q.orderdisplay WHEN 26 THEN q.question END ) AS question26
     , MIN (CASE q.orderdisplay WHEN 26 THEN qr.reply END ) AS reply26
     , MIN (CASE q.orderdisplay WHEN 27 THEN q.question END ) AS question27
     , MIN (CASE q.orderdisplay WHEN 27 THEN qr.reply END ) AS reply27
     , MIN (CASE q.orderdisplay WHEN 28 THEN q.question END ) AS question28
     , MIN (CASE q.orderdisplay WHEN 28 THEN qr.reply END ) AS reply28
     , MIN (CASE q.orderdisplay WHEN 29 THEN q.question END ) AS question29
     , MIN (CASE q.orderdisplay WHEN 29 THEN qr.reply END ) AS reply29
     , MIN (CASE q.orderdisplay WHEN 30 THEN q.question END ) AS question30
     , MIN (CASE q.orderdisplay WHEN 30 THEN qr.reply END ) AS reply30
FROM
    questionnaire qn
        INNER JOIN
    question q ON qn.questionnaireseq = q.questionnaireseq
        INNER JOIN
    questionnairereply qnr ON qn.questionnaireseq = qnr.questionnaireseq
        INNER JOIN
    questionreply qr ON qnr.questionnairereplyseq = qr.questionnairereplyseq AND q.questionseq = qr.questionseq
        LEFT JOIN
    memberinfo m ON qnr.memberinfoseq = m.memberinfoSeq
WHERE
    /*%if conditionDto.shopSeq != null*/
        qn.shopseq = /*conditionDto.shopSeq*/0
    /*%end*/
    /*%if conditionDto.questionnaireSeq != null*/
  AND qn.questionnaireseq = /*conditionDto.questionnaireSeq*/0
    /*%end*/
    /*%if conditionDto.questionnaireKey != null*/
  AND  qn.questionnairekey LIKE '%' || /*conditionDto.questionnaireKey*/0 || '%'
    /*%end*/
    /*%if conditionDto.name != null*/
  AND(qn.name LIKE '%' || /*conditionDto.name*/0 || '%' OR qn.namepc LIKE '%' || /*conditionDto.name*/0 )
    /*%end*/
    /*%if conditionDto.openStatus != null*/
    /*%if conditionDto.site == null*/
  AND ( qn.openstatuspc = /*conditionDto.openStatus.value*/0)
  AND qn.openstatuspc <> '9'
    /*%end*/
    /*%if conditionDto.site == "0"*/
  AND ( qn.openstatuspc = /*conditionDto.openStatus.value*/0)
  AND qn.openstatuspc <> '9'
    /*%end*/
    /*%if conditionDto.site == "1" */
  AND qn.openstatuspc = /*conditionDto.openStatus.value*/0
    /*%end*/
    /*%if conditionDto.site == "2" */
  AND qn.openstatuspc <> '9'
    /*%end*/
    /*%else*/
  AND qn.openstatuspc <> '9'
    /*%end*/
    /*%if conditionDto.openStartTimeFrom != null*/
  AND qn.openstarttime >= /*conditionDto.openStartTimeFrom*/0
    /*%end*/
    /*%if conditionDto.openStartTimeTo != null*/
  AND qn.openstarttime <= /*conditionDto.openStartTimeTo*/0
    /*%end*/
    /*%if conditionDto.openEndTimeFrom != null*/
  AND qn.openendtime >= /*conditionDto.openEndTimeFrom*/0
    /*%end*/
    /*%if conditionDto.openEndTimeTo != null*/
  AND qn.openendtime <= /*conditionDto.openEndTimeTo*/0
    /*%end*/
    /*%if conditionDto.siteTypeList != null*/
  AND qnr.sitetype IN /*conditionDto.siteTypeList*/(1,2,3)
    /*%end*/
    /*%if conditionDto.deviceTypeList != null*/
  AND qnr.devicetype IN /*conditionDto.deviceTypeList*/(1,2,3)
    /*%end*/
    /*%if conditionDto.registTimeFrom != null*/
  AND qnr.registTime >= /*conditionDto.registTimeFrom*/0
    /*%end*/
    /*%if conditionDto.registTimeTo != null*/
  AND qnr.registTime <= /*conditionDto.registTimeTo*/0
    /*%end*/
    /*%if conditionDto.orderCode != null*/
  AND qnr.ordercode = /*conditionDto.orderCode*/0
    /*%end*/
    /*%if conditionDto.memberInfoSeq != null*/
  AND qnr.memberinfoseq = /*conditionDto.memberInfoSeq*/0
    /*%end*/
    /*%if conditionDto.memberInfoId != null*/
  AND m.memberinfoid LIKE '%' || /*conditionDto.memberInfoId*/0 || '%'
    /*%end*/
GROUP BY
    qn.questionnaireseq, qnr.questionnairereplyseq
ORDER BY
    qn.questionnairekey ASC, qn.openStartTime DESC, qnr.registTime DESC
