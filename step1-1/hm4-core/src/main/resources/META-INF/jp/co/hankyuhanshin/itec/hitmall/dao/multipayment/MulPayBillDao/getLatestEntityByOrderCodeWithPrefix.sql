SELECT
        *
    FROM
        mulPayBill
    WHERE
        mulPayBill.orderId LIKE /*orderCodeWithPrefix*/0 || '%'
        AND errcode is null
    ORDER BY
        mulPayBill.orderId DESC OFFSET 0 LIMIT 1
