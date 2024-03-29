SELECT
        *
    FROM
        mulPayBill
    WHERE
        mulPayBill.orderSeq = /*orderSeq*/0
        AND errcode is null
    ORDER BY
        mulPayBill.mulpaybillseq DESC OFFSET 0 LIMIT 1
