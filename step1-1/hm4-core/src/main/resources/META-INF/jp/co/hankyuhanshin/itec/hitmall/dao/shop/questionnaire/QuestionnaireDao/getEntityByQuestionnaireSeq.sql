SELECT
    *
FROM
    questionnaire

WHERE
	questionnaireseq = /*questionnaireSeq*/'12345678'
	AND
	shopseq = /*shopSeq*/'1001'
	AND
	openstatuspc <> '9'