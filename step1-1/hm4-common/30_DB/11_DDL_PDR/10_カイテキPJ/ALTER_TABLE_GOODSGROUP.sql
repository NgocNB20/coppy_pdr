-- 2023-renew No64 from here
ALTER TABLE public.goodsgroup ADD goodsgroupnameadmin VARCHAR(120);
COMMENT ON COLUMN public.goodsgroup.goodsgroupnameadmin IS '商品名（管理用）';
-- 2023-renew No64 to here

-- 2023-renew No64 from here
ALTER TABLE goodsgroup
ADD COLUMN goodsgroupname1 VARCHAR(120),
ADD COLUMN goodsgroupname1openstarttime TIMESTAMP(0),
ADD COLUMN goodsgroupname2 VARCHAR(120),
ADD COLUMN goodsgroupname2openstarttime TIMESTAMP(0);

COMMENT ON COLUMN goodsgroup.goodsgroupname IS '商品名';
COMMENT ON COLUMN goodsgroup.goodsgroupname1 IS '商品名1';
COMMENT ON COLUMN goodsgroup.goodsgroupname1openstarttime IS '商品名1_公開開始日時';
COMMENT ON COLUMN goodsgroup.goodsgroupname2 IS '商品名2';
COMMENT ON COLUMN goodsgroup.goodsgroupname2openstarttime IS '商品名2_公開開始日時';
-- 2023-renew No64 to here

-- 2023-renew AddNo5 from here
ALTER TABLE goodsgroup ADD COLUMN goodsGroupMaxPriceMb NUMERIC(8) NOT NULL DEFAULT 0;
ALTER TABLE goodsgroup ADD COLUMN goodsGroupMinPriceMb NUMERIC(8) NOT NULL DEFAULT 0;

COMMENT ON COLUMN goodsgroup.goodsGroupMaxPriceMb IS '商品グループ最高値携帯';
COMMENT ON COLUMN goodsgroup.goodsGroupMinPriceMb IS '商品グループ最安値携帯';
-- 2023-renew AddNo5 to here

-- 2023-renew No21 from here
alter table goodsgroup add excludingflag varchar(1) default 0 not null;
comment on column goodsgroup.excludingflag is '除外フラグ';
-- 2023-renew No21 to here
