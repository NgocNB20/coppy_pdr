CREATE TABLE SALESADVISABILITY
(
    salesAdvisabilitySeq           NUMERIC(8) NOT NULL,
    loginType                         VARCHAR(1) NOT NULL,
    dentalMonopolySalesFlg            VARCHAR(1) ,
    businessType                      VARCHAR(2) ,
    confDocumentType                  VARCHAR(2) ,
    goodsClassType                    VARCHAR(2) ,
    salableGoodsType                  VARCHAR(1) NOT NULL
)
;


ALTER TABLE SALESADVISABILITY
    ADD CONSTRAINT SALESADVISABILITY_PKEY PRIMARY KEY (salesAdvisabilitySeq)
;

COMMENT ON TABLE SALESADVISABILITY IS '販売可否判定';

COMMENT ON COLUMN SALESADVISABILITY.salesAdvisabilitySeq IS '販売可否判定SEQ';
COMMENT ON COLUMN SALESADVISABILITY.loginType IS 'ログイン状態';
COMMENT ON COLUMN SALESADVISABILITY.dentalMonopolySalesFlg IS '歯科専売可否フラグ';
COMMENT ON COLUMN SALESADVISABILITY.businessType IS '顧客区分';
COMMENT ON COLUMN SALESADVISABILITY.confDocumentType IS '確認書類';
COMMENT ON COLUMN SALESADVISABILITY.goodsClassType IS '薬品区分';
COMMENT ON COLUMN SALESADVISABILITY.salableGoodsType IS '販売可能商品区分';

