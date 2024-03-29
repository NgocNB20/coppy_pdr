SELECT
    questionnairereply.*
FROM
    questionnairereply
WHERE
    questionnairereply.memberinfoseq = /*memberInfoSeq*/0
AND
    questionnairereply.questionnairekey = /*questionnaireKey*/''