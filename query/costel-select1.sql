set @iduser=51;

select s.id, s.data_solicitarii, s.id_user, 0 as nr_mesaje, count(*)
from solicitare s, messages m, executor_history e, tiket_open_history o 
where s.id = m.id_solicitare
and s.id=e.id_solicitare
and s.id=o.id_tiket
and s.statut<>3
and m.id_executor<>@iduser
and m.data_expedierii>o.date_opened
and o.id_user=@iduser
group by s.id, s.data_solicitarii, s.id_user