-- PDR Migrate Customization from here
select
  memberinfo.customerNo 
from
  freeareaviewablemember
inner join
  memberinfo 
on
  freeareaviewablemember.memberinfoseq = memberinfo.memberinfoseq 
where
  freeareaviewablemember.freeareaseq = /*freeAreaSeq*/0
order by
  freeareaviewablemember.memberinfoseq asc
-- PDR Migrate Customization to here
