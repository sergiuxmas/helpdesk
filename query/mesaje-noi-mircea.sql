use helpdesk;
/*
select id, id_solicitare from messages as mes
where id_executor=51
  AND data_expedierii>'2016-04-01 15:32:01'
order by data_expedierii desc, id_solicitare asc
*/

select id_solicitare,count(id_solicitare) as MesageCnt from messages m
inner join solicitare s on m.id_solicitare = s.id
inner join tiket_open_history th on th.id_tiket = s.id
where s.id in (select id_solicitare from executor_history where user_executor = 51) and s.statut <> 3
and th.date_opened < m.data_expedierii
group by id_solicitare
union all
select id_solicitare,count(id_solicitare) as MesageCnt from messages m
inner join solicitare s on m.id_solicitare = s.id
where s.id in (select id_solicitare from executor_history where user_executor = 51) and s.statut <> 3
and s.id not in (select id_tiket from tiket_open_history where id_user = 51 )
group by id_solicitare

-- SELECT sol.id FROM solicitare AS sol, executor_history AS h 
-- WHERE sol.id = h.id_solicitare AND h.user_executor = 51 ORDER BY h.data_decizie DESC LIMIT 1