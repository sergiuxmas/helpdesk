use helpdesk;

-- lista mesajelor
select mess.id_solicitare from messages as mess
WHERE mess.data_expedierii > '2016-02-11 10:58:52'
  AND (mess.id_executor=51 OR mess.id_executor is null)
ORDER BY mess.data_expedierii DESC;

-- lista solicit
SELECT h.id_solicitare
FROM solicitare AS sol, executor_history AS h
WHERE sol.id = h.id_solicitare 
  AND h.user_executor = 51
  AND sol.statut<>3
ORDER BY h.data_decizie DESC


select * from 
(
SELECT h.id_solicitare
FROM solicitare AS sol, executor_history AS h
WHERE sol.id = h.id_solicitare 
  AND h.user_executor = 51
  AND sol.statut<>3
ORDER BY h.data_decizie DESC
) as solInExecutie
LEFT JOIN 








-- AND date(data_expedierii) > STR_TO_DATE('2016-04-18 10:58:52', '%Y-%m-%d %H:%i:%s')