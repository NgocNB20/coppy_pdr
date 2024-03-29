-- 2023-renew No85-1 from here
ALTER TABLE public.orderdelivery ALTER COLUMN receiverfirstname TYPE varchar(15) USING receiverfirstname::varchar(15);
ALTER TABLE public.orderdelivery ALTER COLUMN receiverlastkana TYPE varchar(30) USING receiverlastkana::varchar(30);
ALTER TABLE public.orderdelivery ALTER COLUMN receivertel TYPE varchar(14) USING receivertel::varchar(14);
-- 2023-renew No85-1 to here