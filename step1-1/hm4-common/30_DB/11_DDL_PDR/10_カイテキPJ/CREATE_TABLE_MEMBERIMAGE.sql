create table if not exists public.memberimage
(
    memberinfoseq       numeric(8)   not null,
    memberimageno       numeric(1)   not null,
    memberImageFileName varchar(100) not null,
    memberimagetype     varchar(1)   not null,
    registtime          timestamp(0) not null,
    updatetime          timestamp(0) not null,
    primary key (memberinfoseq, memberimageno)
);

comment on table public.memberimage is '会員画像';
comment on column public.memberimage.memberinfoseq is '会員SEQ';
comment on column public.memberimage.memberimageno is '会員画像連番';
comment on column public.memberimage.memberimagefilename is '会員画像ファイル名';
comment on column public.memberimage.memberimagetype is '会員画像種別';
comment on column public.memberimage.registtime is '登録日時';
comment on column public.memberimage.updatetime is '更新日時';


