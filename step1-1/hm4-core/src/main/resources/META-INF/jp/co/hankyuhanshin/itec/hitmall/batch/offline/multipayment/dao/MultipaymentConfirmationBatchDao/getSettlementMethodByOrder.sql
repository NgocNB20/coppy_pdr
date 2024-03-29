SELECT
        settlementmethod.*
    FROM
        orderindex
        INNER JOIN orderbill
          ON orderindex.orderseq = orderbill.orderseq
          AND orderindex.orderbillversionno = orderbill.orderbillversionno
        INNER JOIN settlementmethod
          ON orderbill.settlementmethodseq = settlementmethod.settlementmethodseq
    WHERE
        orderindex.orderseq = /*orderSeq*/0
        AND orderindex.orderversionno = /*orderVersionNo*/0
