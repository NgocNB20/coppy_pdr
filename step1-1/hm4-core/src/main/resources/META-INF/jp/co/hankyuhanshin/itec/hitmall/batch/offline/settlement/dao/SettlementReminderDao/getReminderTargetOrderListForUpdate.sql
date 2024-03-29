SELECT
        *
    FROM
        (
            ordersummary INNER JOIN orderindex
                ON ordersummary.orderseq = orderindex.orderseq
            AND ordersummary.orderversionno = orderindex.orderversionno
        ) INNER JOIN orderbill
            ON orderindex.orderseq = orderbill.orderseq
        AND orderindex.orderbillversionno = orderbill.orderbillversionno
    WHERE
        ordersummary.shopseq = /*shopSeq*/0
        AND ordersummary.cancelflag = '0'
        AND ordersummary.orderprice > 0
        AND ordersummary.receiptpricetotal = 0
        AND ordersummary.settlementmailrequired = '1'
        AND ordersummary.remindersentflag = '0'
        AND date_trunc('day', orderbill.paymenttimelimitdate) <= /*thresholdDate*/0
        AND date_trunc('day', orderbill.paymenttimelimitdate) > /*today*/0
    ORDER BY
        ordersummary.orderseq DESC FOR UPDATE
