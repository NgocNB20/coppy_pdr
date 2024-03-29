CREATE TABLE RESTOCKANNOUNCE
(
    memberInfoSeq               NUMERIC(8) NOT NULL,
    goodsSeq                    NUMERIC(8) NOT NULL,
    registTime                  TIMESTAMP(0) NOT NULL,
    updateTime                  TIMESTAMP(0) NOT NULL
)
;

ALTER TABLE RESTOCKANNOUNCE
    ADD CONSTRAINT RESTOCKANNOUNCE_PKEY PRIMARY KEY (memberInfoSeq,goodsSeq)
;

COMMENT ON TABLE RESTOCKANNOUNCE IS '入荷お知らせ';
COMMENT ON COLUMN RESTOCKANNOUNCE.memberInfoSeq IS '会員SEQ';
COMMENT ON COLUMN RESTOCKANNOUNCE.goodsSeq IS '商品SEQ';
COMMENT ON COLUMN RESTOCKANNOUNCE.registTime IS '登録日時';
COMMENT ON COLUMN RESTOCKANNOUNCE.updateTime IS '更新日時';

CREATE UNIQUE INDEX idx_reStockAnnounce ON RESTOCKANNOUNCE (memberInfoSeq,goodsSeq);

