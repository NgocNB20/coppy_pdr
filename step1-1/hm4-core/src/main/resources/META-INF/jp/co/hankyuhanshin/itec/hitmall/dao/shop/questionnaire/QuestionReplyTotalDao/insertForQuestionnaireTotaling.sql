INSERT INTO
    questionreplytotal(questionseq, replychoice, replychoicecount, registtime)
SELECT
    master.questionseq,
    master.choise        AS replychoice,
    COUNT(reply.reply)   AS replychoicecount,
    /*currentTime*/''      AS registtime
FROM
    (
    SELECT
    qn.questionnaireseq,
    q.questionseq,
    q.orderdisplay,
    q.choices,
    regexp_split_to_table(q.choices, '\r\n') AS choise
    FROM
    questionnaire qn
    INNER JOIN
    question q ON qn.questionnaireseq = q.questionnaireseq
    WHERE
    qn.openstatuspc <> '9'
    AND
    qn.openstarttime <= /*currentTime*/''
    AND
    qn.openendtime > /*totalingCompleteTime*/''
    AND
    q.openstatus <> '9'
    AND
    q.replytype NOT IN ('0', '1')
    ) master
    LEFT JOIN
    (
    SELECT
    qnr.questionnaireseq,
    qr.questionseq,
    qnr.questionnairereplyseq,
    regexp_split_to_table(qr.reply, '\n') AS reply
    FROM
    questionnairereply qnr
    INNER JOIN
    questionreply qr ON qnr.questionnairereplyseq = qr.questionnairereplyseq
    ) reply ON master.questionnaireseq = reply.questionnaireseq AND master.questionseq = reply.questionseq AND master.choise = reply.reply
GROUP BY
    master.questionnaireseq, master.questionseq, master.choise
