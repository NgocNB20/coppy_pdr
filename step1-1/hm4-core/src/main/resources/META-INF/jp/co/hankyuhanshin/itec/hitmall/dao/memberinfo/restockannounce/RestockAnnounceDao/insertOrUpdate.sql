INSERT INTO RESTOCKANNOUNCE
(memberinfoseq, goodsseq, registtime, updatetime)
VALUES
( /*restockAnnounceEntity.memberInfoSeq*/0
, /*restockAnnounceEntity.goodsSeq*/0
, /*restockAnnounceEntity.registTime*/0
, /*restockAnnounceEntity.updateTime*/0)
    ON CONFLICT (memberInfoSeq, goodsSeq)
DO UPDATE SET
    updatetime= /*restockAnnounceEntity.updateTime*/0
