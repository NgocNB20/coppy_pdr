SELECT
    count(*)
FROM
    questionnaire
WHERE
        shopseq = /*shopSeq*/0
  AND
        questionnaireseq = /*questionnaireSeq*/0
  AND
        openstatuspc = '1'
  AND
        openstarttime <= /*currentTime*/0
  AND
        openendtime >= /*currentTime*/0
  AND
        (
            SELECT
                count(*)
            FROM
                question
            WHERE
                    shopseq = /*shopSeq*/0
              AND
                    questionnaireseq = /*questionnaireSeq*/0
              AND
                    openstatus = '1'
        ) > 0
