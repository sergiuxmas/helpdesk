DROP PROCEDURE IF EXISTS helpdesk.tiketList;
CREATE PROCEDURE helpdesk.`tiketList`(IN id_executor INT(100))
BEGIN
select s.id,s.statut as id_statut,DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y %H:%i:%s') as SolicitareDin,clu.id as id_solicitant,clu.nume as Solicitant
,cld.nume as Diviziune,IFNULL(clue.nume,'N/A') as Executor ,IFNULL(msg.Mesaje,0) as Mesaje from solicitare s
inner join cl_useri clu on s.id_user = clu.id
left join (SELECT id_solicitare,user_executor FROM executor_history n
WHERE id=(SELECT MAX(id) FROM executor_history WHERE id_solicitare=n.id_solicitare) order by id_solicitare desc) eh on eh.id_solicitare = s.id
left join cl_useri clue on clue.id = eh.user_executor
left join cl_diviziuni cld on cld.idDiviziune = clu.id_diviziune
left join (select id_solicitare,count(id_solicitare) as Mesaje from messages m
inner join solicitare s on m.id_solicitare = s.id
inner join tiket_open_history th on th.id_tiket = s.id
where s.statut <> 3 and th.date_opened < m.data_expedierii
group by id_solicitare union all
select id_solicitare,count(id_solicitare) as Mesaje from messages m
inner join solicitare s on m.id_solicitare = s.id
where s.statut <> 3 and s.id not in (select id_tiket from tiket_open_history )
group by id_solicitare
 ) as msg on msg.id_solicitare = s.id
where s.statut<>3 and ( eh.user_executor =@id_executor or eh.user_executor is null )
order by s.id desc;
END;
