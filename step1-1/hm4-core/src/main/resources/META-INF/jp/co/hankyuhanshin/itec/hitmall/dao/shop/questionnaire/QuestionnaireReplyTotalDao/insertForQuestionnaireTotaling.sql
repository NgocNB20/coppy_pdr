INSERT INTO
    questionnairereplytotal(questionnaireseq, replycount, registtime)
SELECT
    qn.questionnaireseq,
    COUNT(qnr.questionnairereplyseq) AS replycount,
    /*currentTime*/'' AS registtime
FROM
    questionnaire qn
    LEFT JOIN
    questionnairereply qnr ON qn.questionnaireseq = qnr.questionnaireseq
WHERE
    qn.openstatuspc <> '9'
  AND
    qn.openstarttime <= /*currentTime*/''
  AND
    qn.openendtime > /*totalingCompleteTime*/''
GROUP BY
    qn.questionnaireseq
