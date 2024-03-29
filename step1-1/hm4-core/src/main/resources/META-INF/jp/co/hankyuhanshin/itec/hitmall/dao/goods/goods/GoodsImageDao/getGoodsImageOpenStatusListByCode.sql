SELECT
    gg.goodsgroupseq
     , gs.goodsseq
     , gg.goodsgroupcode
     , gg.goodsopenstatuspc
     , gs.goodscode
     , gs.salestatuspc
     , gi.displayFlag
     , gi.imageFileName
     , gi.registTime
     , gi.updateTime
FROM
    (SELECT g.goodsgroupseq, g.goodsseq, g.salestatuspc, g.goodscode
     FROM goods g
     GROUP BY g.goodsgroupseq, g.goodsseq, g.salestatuspc, g.goodscode) gs
        LEFT OUTER JOIN
    goodsimage gi
    ON
                gs.goodsgroupseq = gi.goodsgroupseq
            AND gs.goodsseq = gi.goodsseq
        LEFT OUTER JOIN
    goodsgroup gg
    ON
            gs.goodsgroupseq = gg.goodsgroupseq
WHERE gg.goodsgroupcode = /*goodsGroupCode*/'0'
ORDER BY
    gs.goodsseq
;
