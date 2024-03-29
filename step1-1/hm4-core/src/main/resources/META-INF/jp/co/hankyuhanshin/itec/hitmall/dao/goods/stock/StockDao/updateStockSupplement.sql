UPDATE stock
SET realStock = realStock + /*supplementCount*/0,
    updatetime = CURRENT_TIMESTAMP
WHERE
    shopSeq = /*shopSeq*/0
AND
    goodsseq =
    (select goodsseq from goods where shopSeq = /*shopSeq*/0 AND goodscode = /*goodscode*/0)
AND
	realStock + /*supplementCount*/0 >= orderreservestock
