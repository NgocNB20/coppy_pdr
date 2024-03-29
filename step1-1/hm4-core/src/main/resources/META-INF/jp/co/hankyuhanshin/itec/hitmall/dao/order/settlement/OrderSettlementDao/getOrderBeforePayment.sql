-- 2023-renew No14 from here
SELECT
    ordersettlement.settlementmethodtype AS settlementmethodtype,
    mulpaybill.orderid AS orderid
From
    (
        SELECT
            orderseq,
            orderversionno
        FROM
            ordersummary
        WHERE
            orderseq = (
                SELECT
                    MIN(ordersummary.orderseq)
                FROM
                    (
                        SELECT
                            MAX(member.memberinfoseq) AS memberinfoseq,
                            MAX(ordertime) AS ordertime
                        FROM
                            (
                                SELECT
                                    memberinfoseq
                                from
                                    memberinfo
                                WHERE
                                    customerno = /*customerNo*/0
                            ) AS member
                            INNER JOIN ordersummary
                                ON member.memberinfoseq = ordersummary.memberinfoseq
                    ) AS latestOrder
                    INNER JOIN ordersummary
                        ON latestOrder.memberinfoseq = ordersummary.memberinfoseq
                        AND latestOrder.ordertime = ordersummary.ordertime
            )
    ) AS latestOrderSummary
    INNER JOIN orderindex
        ON latestOrderSummary.orderseq = orderindex.orderseq
        AND latestOrderSummary.orderversionno = orderindex.orderversionno
    INNER JOIN ordersettlement
        ON orderindex.orderseq = ordersettlement.orderseq
        AND orderindex.ordersettlementversionno = ordersettlement.ordersettlementversionno
    LEFT JOIN mulpaybill
        ON latestOrderSummary.orderseq = mulpaybill.orderseq
        AND mulpaybill.trantype = 'ExecTran'
-- 2023-renew No14 to here
