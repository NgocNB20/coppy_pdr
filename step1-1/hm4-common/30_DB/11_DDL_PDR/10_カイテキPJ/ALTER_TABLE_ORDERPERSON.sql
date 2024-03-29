-- 2023-renew No85-1 from here
ALTER TABLE public.orderperson ALTER COLUMN orderfirstname TYPE varchar(15) USING orderfirstname::varchar(15);
ALTER TABLE public.orderperson ALTER COLUMN orderlastkana TYPE varchar(30) USING orderlastkana::varchar(30);
ALTER TABLE public.orderperson ALTER COLUMN ordertel TYPE varchar(14) USING ordertel::varchar(14);
ALTER TABLE public.orderperson ALTER COLUMN ordermail TYPE varchar(256) USING ordermail::varchar(256);
-- 2023-renew No85-1 to here