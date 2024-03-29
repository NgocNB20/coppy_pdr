SELECT categoryid
FROM category
WHERE categoryseq IN
    (
    SELECT
	    unnest(regexp_matches(c.categorySeqPath, '.{1,8}', 'g'))::numeric
	FROM category as c
	WHERE shopSeq = /*shopSeq*/0
	AND c.categoryId = /*categoryId*/0
    )
ORDER BY categoryseq asc
