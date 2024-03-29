SELECT ordersummary.*
FROM
	-- 受注サマリ（ポイントが未確定状態、かつ、キャンセル状態でない、かつ、保留状態でない、かつ、出荷完了）
	-- （売上日が指定日を経過（時分秒はみない）したもの）
	(SELECT * FROM ordersummary
		WHERE pointactivateflag = '0'
		AND cancelflag = '0'
		AND waitingflag = '0'
		AND orderstatus = '3'
		AND (CAST(salestime AS DATE)) <= /*checkeDate*/'2010/1/1'
		) AS ordersummary
	INNER JOIN
		-- 会員（会員状態＝入会）
		(SELECT memberinfoseq FROM memberinfo
			WHERE memberinfostatus = '0') AS memberinfo
	ON ordersummary.memberinfoseq = memberinfo.memberinfoseq
	INNER JOIN
		-- 受注インデックス 受注配送を結合するために必要
		(SELECT orderseq,orderversionno,orderdeliveryversionno,orderbillversionno FROM orderindex) AS orderindex
	ON ordersummary.orderseq = orderindex.orderseq AND ordersummary.orderversionno = orderindex.orderversionno
	INNER JOIN
		-- 受注請求 （請求決済エラーでない）
		(SELECT orderseq,orderbillversionno,emergencyflag  FROM orderbill
		 	WHERE emergencyflag = '0') AS orderbill
	ON orderindex.orderseq = orderbill.orderseq AND orderindex.orderbillversionno = orderbill.orderbillversionno

ORDER BY
	ordersummary.orderseq
