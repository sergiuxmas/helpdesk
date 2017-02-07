use helpdesk;

SELECT h.id_solicitare
FROM solicitare AS sol, executor_history AS h
WHERE sol.id = h.id_solicitare AND h.user_executor = 51
ORDER BY h.data_decizie DESC


SELECT h.user_executor
FROM solicitare AS sol, executor_history AS h
WHERE sol.id = h.id_solicitare 
AND sol.id = 77
ORDER BY h.data_decizie DESC
LIMIT 1