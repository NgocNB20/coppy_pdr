DELETE FROM
    questionreplytotal
WHERE
        questionseq IN
        (
            SELECT DISTINCT
                question.questionseq
            FROM
                questionnaire
                    INNER JOIN
                question ON questionnaire.questionnaireseq = question.questionnaireseq
            WHERE
                    questionnaire.openstatuspc <> '9'
              AND
                    questionnaire.openstarttime <= /*currentTime*/''
              AND
                    questionnaire.openendtime > /*totalingCompleteTime*/''
              AND
                    question.openstatus <> '9'
              AND
                    question.replytype NOT IN ('0', '1')
        )
