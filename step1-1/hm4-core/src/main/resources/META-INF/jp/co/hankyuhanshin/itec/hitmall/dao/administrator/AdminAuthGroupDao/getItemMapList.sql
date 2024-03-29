SELECT
    auth.*
FROM
    adminAuthGroup auth
WHERE
    auth.shopseq = /*shopSeq*/1001
ORDER BY
    auth.adminAuthGroupSeq
