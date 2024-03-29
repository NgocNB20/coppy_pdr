select
    addressbook.*
from
    addressbook
where
    addressbook.memberInfoSeq = /*conditionDto.memberInfoSeq*/0
order by
    addressbook.updateTime desc
