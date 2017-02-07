SELECT ex.id,
      ex.id_prioritate,
      (select p.nume from cl_prioritate as p where p.id=ex.id_prioritate) as prioritate,
      ex.tip_solicitare,
      (select tip.nume from cl_solicitare_tip as tip where tip.id=ex.tip_solicitare) as tip,
      ex.id_domeniu,
      (select d.nume from cl_domeniu as d where d.id=ex.id_domeniu) as domeniu,
      ex.id_executor,
      (select u.nume from cl_useri as u where u.id=ex.id_executor) as user,
      ex.descriere,
      ex.data_executarii 
FROM 
     executare as ex 
WHERE 
    ex.id_solicitare=56
