SELECT
        *
    FROM
        mulPayBill
    WHERE
        mulPayBill.orderSeq = /*orderSeq*/0
        AND amazonPaymentConfirmStatus <> '9'
    ORDER BY
        mulPayBill.mulpaybillseq DESC OFFSET 0 LIMIT 1
