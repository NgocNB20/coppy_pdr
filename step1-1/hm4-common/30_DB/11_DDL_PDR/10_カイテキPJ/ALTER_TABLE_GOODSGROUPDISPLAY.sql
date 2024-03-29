-- 2023-renew AddNo5 from here
ALTER TABLE public.goodsgroupdisplay ADD outleticonflag varchar(1) not null default 0;
COMMENT ON COLUMN public.goodsgroupdisplay.outleticonflag IS 'アウトレットアイコンフラグ';
-- 2023-renew AddNo5 to here

-- 2023-renew No19 from here
ALTER TABLE public.goodsgroupdisplay ALTER COLUMN goodsnote6 TYPE varchar(8000) USING goodsnote6::varchar(8000);
-- 2023-renew No19 to here

-- 2023-renew No11 from here
ALTER TABLE goodsgroupdisplay
ADD COLUMN goodsnote21 VARCHAR(4000);

COMMENT ON COLUMN goodsgroupdisplay.goodsnote21 IS '商品説明21';
-- 2023-renew No11 to here

-- 2023-renew No0 from here
ALTER TABLE public.goodsgroupdisplay ADD goodsnote22 varchar(4000) NULL;
COMMENT ON COLUMN public.goodsgroupdisplay.goodsnote22 IS '商品詳細アイコン（上位4件）';
-- 2023-renew No0 to here

-- 2023-renew No64 from here --
ALTER TABLE goodsgroupdisplay
ADD COLUMN goodsnote1openstarttime TIMESTAMP(0),
ADD COLUMN goodsnote1sub VARCHAR(4000),
ADD COLUMN goodsnote1subopenstarttime TIMESTAMP(0),
ADD COLUMN goodsnote2openstarttime TIMESTAMP(0),
ADD COLUMN goodsnote2sub VARCHAR(4000),
ADD COLUMN goodsnote2subopenstarttime TIMESTAMP(0),
ADD COLUMN goodsnote4openstarttime TIMESTAMP(0),
ADD COLUMN goodsnote4sub VARCHAR(4000),
ADD COLUMN goodsnote4subopenstarttime TIMESTAMP(0),
ADD COLUMN goodsnote10openstarttime TIMESTAMP(0),
ADD COLUMN goodsnote10sub VARCHAR(4000),
ADD COLUMN goodsnote10subopenstarttime TIMESTAMP(0);

COMMENT ON COLUMN goodsgroupdisplay.goodsnote1 IS '商品概要';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote1sub IS '商品概要2';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote2 IS '特徴';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote2sub IS '特徴2';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote4 IS '注意事項';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote4sub IS '注意事項2';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote10 IS 'シリーズPRコメントPC';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote10sub IS 'シリーズPRコメントPC2';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote1openstarttime IS '商品概要_公開開始日時';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote1subopenstarttime IS '商品概要2_公開開始日時';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote2openstarttime IS '特徴_公開開始日時';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote2subopenstarttime IS '特徴2_公開開始日時';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote4openstarttime IS '注意事項_公開開始日時';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote4subopenstarttime IS '注意事項2_公開開始日時';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote10openstarttime IS 'シリーズPRコメントPC_公開開始日時';
COMMENT ON COLUMN goodsgroupdisplay.goodsnote10subopenstarttime IS 'シリーズPRコメントPC2_公開開始日時';
-- 2023-renew No64 to here --
