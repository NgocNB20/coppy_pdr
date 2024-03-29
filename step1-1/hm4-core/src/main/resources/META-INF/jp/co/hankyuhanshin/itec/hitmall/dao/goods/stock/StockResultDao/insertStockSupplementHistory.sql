insert into stockresult
(stockresultseq, goodsseq, supplementtime, supplementcount, realstock, processpersonname, note, registtime, updatetime, stockManagementFlag)
values(
 /*stockResultEntity.stockResultSeq*/0,
 (select goodsseq from goods where shopSeq = /*shopSeq*/0 AND goodscode = /*goodsCode*/0),
 CURRENT_TIMESTAMP,
 /*stockResultEntity.supplementCount*/0,
 (select realstock
     from stock
     where
         shopSeq = /*shopSeq*/0
     AND
         goodsseq =
         (select goodsseq from goods where shopSeq = /*shopSeq*/0 AND goodscode = /*goodsCode*/0)),
 /*stockResultEntity.processPersonName*/0,
 /*stockResultEntity.note*/0,
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP,
 /*stockResultEntity.stockManagementFlag.value*/0
 );
