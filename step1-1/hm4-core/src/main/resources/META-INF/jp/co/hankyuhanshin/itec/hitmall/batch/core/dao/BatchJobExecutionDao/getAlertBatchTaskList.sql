SELECT
    *,
    batchjobinstance.job_name batchName
FROM
    batch_job_execution batchjobexecution,
    batch_job_instance batchjobinstance
WHERE
      batchjobinstance.job_instance_id = batchjobexecution.job_instance_id
  AND batchjobexecution.status in ('STARTING', 'STARTED', 'UNKNOWN')
  AND batchjobexecution.create_time  + CAST(/*progressTime*/'60' || ' minutes' AS interval) < /*currentTime*/'2012/11/01 00:00:00'

order by batchjobexecution.job_execution_id
