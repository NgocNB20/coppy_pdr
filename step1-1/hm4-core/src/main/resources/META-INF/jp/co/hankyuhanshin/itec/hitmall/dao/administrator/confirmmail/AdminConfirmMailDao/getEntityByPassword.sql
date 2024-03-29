select
    *
from
    adminConfirmMail
where
    adminConfirmMailPassword = /*password*/0
and
    effectiveTime >= current_timestamp
