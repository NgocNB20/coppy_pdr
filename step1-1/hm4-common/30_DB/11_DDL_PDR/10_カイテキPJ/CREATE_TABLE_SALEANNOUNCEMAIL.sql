create table public.saleannouncemail (
  goodsseq numeric(8) not null
  , memberinfoseq numeric(8) not null
  , deliveryid character varying(10)
  , deliverystatus character varying(1) default '0' not null
  , deliverytime timestamp(0) without time zone
  , registtime timestamp(0) without time zone not null
  , updatetime timestamp(0) without time zone not null
  , primary key (goodsseq, memberinfoseq)
);


COMMENT ON TABLE SALEANNOUNCEMAIL IS 'セールお知らせメール';
COMMENT ON COLUMN SALEANNOUNCEMAIL.goodsSeq IS '商品SEQ';
COMMENT ON COLUMN SALEANNOUNCEMAIL.memberInfoSeq IS '会員SEQ';
COMMENT ON COLUMN SALEANNOUNCEMAIL.deliveryId IS '配信ID';
COMMENT ON COLUMN SALEANNOUNCEMAIL.deliveryStatus IS '配信状況';
COMMENT ON COLUMN SALEANNOUNCEMAIL.deliveryTime IS '配信日時';
COMMENT ON COLUMN SALEANNOUNCEMAIL.registTime IS '登録日時';
COMMENT ON COLUMN SALEANNOUNCEMAIL.updateTime IS '更新日時';

