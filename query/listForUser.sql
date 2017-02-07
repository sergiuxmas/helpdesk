use helpdesk;
select * from (
    SELECT
    -- DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y') as h1,
    -- DATE_FORMAT(STR_TO_DATE('03.09.2015', '%d.%m.%Y'),'%d.%m.%Y') as h2,
    s.id as id,
    s.statut as statut,
    s.tip as id_tip,
    -- s.id_prioritate as s_id_prioritate,
    (select ex.id_prioritate from executare as ex where ex.id_solicitare=s.id) as ex_prioritate,
    (select case 
                when ex_prioritate is not null OR ex_prioritate=0 OR ex_prioritate<>s.id_prioritate
                then ex_prioritate
                else s.id_prioritate
            end
    ) as sol_id_prioritate,
    (select cl_prioritate.nume from cl_prioritate where cl_prioritate.id=sol_id_prioritate) as prioritate,
    (select ex.id_domeniu from executare as ex where ex.id_solicitare=s.id) as ex_domeniu,
    (select case 
                when ex_domeniu is not null OR ex_domeniu=0 OR ex_domeniu<>s.id_domeniu
                then ex_domeniu
                else s.id_domeniu
            end
    ) as sol_id_domeniu,
  --  s.id_domeniu as id_domeniu,
    (select cl_domeniu.nume from cl_domeniu where cl_domeniu.id=sol_id_domeniu) as domeniu,
    s.data_solicitarii as data_solicitarii,
    s.id_user as id_user,
    cl_useri.nume as user,
    cl_useri.id_diviziune as id_diviziune,
    di.nume as diviziune_nume,
    (
      select h.user_executor from solicitare as sol, executor_history as h
      where sol.id=h.id_solicitare
        and sol.id = s.id
      order by h.data_decizie desc LIMIT 1
    ) as user_executor_id,
    (
      select u.nume from cl_useri as u where u.id=user_executor_id
    )as user_executor_name,
    LEFT(strip_tags(s.descriere),100) as descriere,
    s.opened as opened
    FROM solicitare AS s 
    -- LEFT JOIN cl_domeniu AS d on s.id_domeniu=d.id 
    -- LEFT JOIN cl_prioritate AS p on s.id_prioritate=p.id    
    LEFT JOIN cl_useri on s.id_user=cl_useri.id
    LEFT JOIN cl_diviziuni as di on cl_useri.id_diviziune=di.idDiviziune
  --  LEFT JOIN executor_history as h on s.id=h.id_solicitare
  --  WHERE user_executor_id=24
    
    order by data_solicitarii asc LIMIT 0,1000
    ) as lista_solicitarilor
   -- LEFT JOIN cl_prioritate AS p on sol_id_prioritate=p.id AND lista_solicitarilor.sol_id_prioritate=IFNULL(null,lista_solicitarilor.sol_id_prioritate)
   WHERE sol_id_prioritate=IFNULL(null,sol_id_prioritate)
     AND sol_id_domeniu=IFNULL(null,sol_id_domeniu)
   -- WHERE (user_executor_id=25 OR user_executor_id is null)