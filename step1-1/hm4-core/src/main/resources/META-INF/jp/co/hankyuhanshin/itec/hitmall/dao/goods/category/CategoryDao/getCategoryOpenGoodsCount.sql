WITH goodsInfoPC AS (
SELECT
	c.categoryseqpath, g.goodsgroupseq
FROM
	categorygoods AS cg
	INNER JOIN category AS c
		ON c.categoryseq = cg.categoryseq
	INNER JOIN goodsgroup AS g
		ON g.goodsgroupseq = cg.goodsgroupseq
WHERE
	(g.openstarttimepc <= CURRENT_TIMESTAMP OR g.openstarttimepc IS NULL)
	AND (g.openendtimepc >= CURRENT_TIMESTAMP OR g.openendtimepc IS NULL)
	AND g.goodsopenstatuspc = '1'
)

SELECT
	c2.categorySeq
	,c2.categoryname
 	,(
		SELECT COUNT(DISTINCT goodsInfoPC.goodsgroupseq)
		FROM
			goodsInfoPC
		WHERE
			goodsInfoPC.categoryseqpath LIKE c2.categoryseqpath || '%'
	) AS openGoodsCountPC
FROM
	category AS c2
WHERE c2.categoryseq IN /*categorySeqList*/(1,2,3)
