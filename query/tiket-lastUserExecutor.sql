use helpdesk;

select h.id,h.id_solicitare,h.user_executor, h.data_decizie from solicitare as s, executor_history as h
where s.id=h.id_solicitare
  and s.id = 52
order by h.data_decizie desc LIMIT 1;