CREATE TABLE public.salegoods (
  goodsseq numeric(8) not null
  , salestatus character varying(1) default '0' not null
  , presalecd character varying(5)
  , salecd character varying(5)
  , salefrom timestamp(0) without time zone
  , saleto timestamp(0) without time zone
  , registtime timestamp(0) without time zone not null
  , updatetime timestamp(0) without time zone not null
  , primary key (goodsseq)
);

COMMENT ON TABLE salegoods IS 'セール商品';
COMMENT ON COLUMN salegoods.goodsseq IS '商品SEQ';
COMMENT ON COLUMN salegoods.salestatus IS 'セール状態';
COMMENT ON COLUMN salegoods.presalecd IS '前回セールコード';
COMMENT ON COLUMN salegoods.salecd IS 'セールコード';
COMMENT ON COLUMN salegoods.salefrom IS 'セール期間From';
COMMENT ON COLUMN salegoods.saleto IS 'セール期間To';
COMMENT ON COLUMN salegoods.registtime IS '登録日時';
COMMENT ON COLUMN salegoods.updatetime IS '更新日時';
