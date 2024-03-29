SELECT
    batch_job_instance.job_name as batchName,
    batch_job_execution.*
FROM
    batch_job_execution,
    batch_job_instance
WHERE
  batch_job_instance.job_instance_id = batch_job_execution.job_instance_id
  AND batch_job_instance.job_name != 'BATCH_JOB_MONITORING'
    /*%if searchDto.batchName != null*/
  AND batch_job_instance.job_name = /*searchDto.batchName*/'BATCH_NAME'
    /*%end*/
    /*%if searchDto.createTime != null*/
  AND TO_DATE(TO_CHAR(batch_job_execution.create_time, 'yyyy/MM/dd'), 'yyyy/MM/dd') >= TO_DATE(/*searchDto.createTime*/0, 'yyyy/MM/dd')
    /*%end*/
    /*%if searchDto.endTime != null*/
  AND TO_DATE(TO_CHAR(batch_job_execution.end_time, 'yyyy/MM/dd'), 'yyyy/MM/dd') <= TO_DATE(/*searchDto.endTime*/0, 'yyyy/MM/dd')
    /*%end*/
    /*%if searchDto.status != null*/
  AND batch_job_execution.status = /*searchDto.status*/'0'
    /*%end*/
order by start_time desc
