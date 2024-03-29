SELECT
    *
from
    reStockAnnounceMail
where
    goodsSeq = /*goodsSeq*/0
AND memberInfoSeq = /*memberInfoSeq*/0
ORDER BY versionNo DESC
LIMIT 1;
