use helpdesk;

SELECT *
  FROM (SELECT s.id AS id,
               s.statut statut,

               DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y %H:%i:%s') AS data_solicitarii,
               s.id_user AS id_user,
               cl_useri.nume AS user,
               
               (SELECT h.user_executor
                  FROM solicitare AS sol, executor_history AS h
                 WHERE sol.id = h.id_solicitare AND sol.id = s.id
                ORDER BY h.data_decizie DESC
                 LIMIT 1) AS user_executor,
                  
               "" as new_messages
          FROM solicitare AS s
          INNER JOIN cl_useri ON s.id_user = cl_useri.id
          LEFT JOIN executor_history AS h ON s.id = h.id_solicitare
                  
       ) AS lista_solicitarilor
          where statut<>3 
          AND (user_executor=IFNULL(51,user_executor) OR user_executor is null)
          ORDER BY data_solicitarii DESC;


/*
 select u.id,u.nume,count(mess.id) from cl_useri as u 
 left join messages mess
 on mess.id_executor = u.id
 group by u.id
 order by u.id
*/









/*
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
*/