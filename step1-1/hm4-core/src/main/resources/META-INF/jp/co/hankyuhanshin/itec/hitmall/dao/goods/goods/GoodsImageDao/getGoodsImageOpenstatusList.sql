SELECT
    gg.goodsgroupseq
     , gg.goodsgroupcode
     , gg.goodsopenstatuspc
     , gi.*
FROM
    goodsimage gi
        INNER JOIN
    goodsgroup gg
    ON
            gi.goodsgroupseq = gg.goodsgroupseq
        INNER JOIN
    (SELECT g.goodsgroupseq, g.goodsseq
     FROM goods g
     WHERE g.salestatuspc != '9'
     GROUP BY g.goodsgroupseq, g.goodsseq) gs
    ON
                gi.goodsgroupseq = gs.goodsgroupseq
            AND gi.goodsseq = gs.goodsseq
ORDER BY
    gi.imagefilename
offset /*offset*/0 limit /*limit*/0
;
