SELECT
	ordersummary.*
FROM
	ordersummary AS ordersummary
	INNER JOIN orderindex AS orderindex
	ON orderindex.orderseq = ordersummary.orderseq
	AND orderindex.orderVersionNo = ordersummary.orderversionno
	INNER JOIN orderperson AS orderperson
	ON orderperson.orderseq = orderindex.orderseq
	AND orderperson.orderpersonversionno = orderindex.orderpersonversionno
WHERE
	ordersummary.ordercode = /*orderCode*/0
	AND ordersummary.shopseq = /*shopSeq*/0
	AND orderperson.ordertel = /*orderTel*/0
