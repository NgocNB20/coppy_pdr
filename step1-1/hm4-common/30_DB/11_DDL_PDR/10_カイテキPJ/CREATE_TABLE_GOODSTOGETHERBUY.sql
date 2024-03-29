-- 2023-renew No21 from here
create table if not exists public.goodstogetherbuy
(
    goodsgroupseq            numeric(8)   not null,
    goodstogetherbuygroupseq numeric(8)   not null,
    boughtnumber             numeric(4),
    orderdisplay             numeric(4),
    registmethod             varchar(2)   not null,
    registtime               timestamp(0) not null,
    updatetime               timestamp(0) not null,
    primary key (goodstogetherbuygroupseq, goodsgroupseq)
);

comment on table public.goodstogetherbuy is 'よく一緒に購入される商品';

comment on column public.goodstogetherbuy.goodsgroupseq is '商品グループSEQ';

comment on column public.goodstogetherbuy.goodstogetherbuygroupseq is 'よく一緒に購入されている商品グループSEQ';

comment on column public.goodstogetherbuy.boughtnumber is '購入数';

comment on column public.goodstogetherbuy.orderdisplay is '表示順';

comment on column public.goodstogetherbuy.registmethod is '登録方法';

comment on column public.goodstogetherbuy.registtime is '登録日時';

comment on column public.goodstogetherbuy.updatetime is '更新日時';

create index if not exists idx_goodtogetherbuy_goodsgroupseq
    on public.goodstogetherbuy (goodsgroupseq);
-- 2023-renew No21 to here

