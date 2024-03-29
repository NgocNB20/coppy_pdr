SELECT
    os.ordercode AS orderCode, mbs.authoryLimitDate
FROM
    ordersummary os
    INNER JOIN orderindex oi
      ON os.orderseq = oi.orderseq
      AND os.orderversionno = oi.orderversionno
    INNER JOIN orderbill ob
      ON oi.orderseq = ob.orderseq
      AND oi.orderbillversionno = ob.orderbillversionno
    INNER JOIN
      (SELECT mbm.orderseq, (to_date(mbm.trandate,'YYYYMMDDHH24MISS') + cast(/*authoryHoldPeriod*/0 || ' days' AS interval)) AS authoryLimitDate
        FROM mulpaybill mbm
        WHERE mulpaybillseq IN
          (SELECT MAX(mb.mulpaybillseq)
            FROM mulpaybill mb
            WHERE mb.jobcd = 'AUTH'
              AND mb.trandate IS NOT NULL
            GROUP BY mb.orderseq
          )
      ) mbs
      ON oi.orderseq = mbs.orderseq

WHERE
    -- ①受注状態：キャンセル以外
    os.cancelflag != '1'
    -- ②決済方法：クレジット
    -- ③請求種別：後請求
    AND os.settlementmethodseq IN
      (SELECT s.settlementmethodseq
       FROM settlementmethod s
       WHERE s.settlementmethodtype = '0'
         AND s.billtype = '1')
    -- ④売上状態：未売上
    AND os.salesflag = '0'
    -- ⑤バッチ実行日がオーソリ保持期限日（決済日付＋オーソリ保持期間）- メール送信開始期間以降
    AND /*currentDate*/0 >= (mbs.authoryLimitDate - cast(/*mailSendStartPeriod*/0 || ' days' AS interval))
    -- ⑥請求決済エラーが発生していない
    AND ob.emergencyflag = '0'
    -- ⑦オーソリ保持期限（決済日付＋オーソリ保持期間）がバッチ実行日以降
    AND /*currentDate*/0 <= mbs.authoryLimitDate
ORDER BY mbs.authoryLimitDate,os.ordercode
