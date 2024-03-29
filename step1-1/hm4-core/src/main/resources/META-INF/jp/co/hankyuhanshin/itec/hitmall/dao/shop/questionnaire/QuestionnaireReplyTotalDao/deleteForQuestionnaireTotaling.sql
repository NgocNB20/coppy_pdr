DELETE FROM
    questionnairereplytotal
WHERE
        questionnaireseq IN
        (
            SELECT
                questionnaire.questionnaireseq
            FROM
                questionnaire
            WHERE
                    questionnaire.openstatuspc <> '9'
              AND
                    questionnaire.openstarttime <= /*currentTime*/''
              AND
                    openendtime > /*totalingCompleteTime*/''
        )
