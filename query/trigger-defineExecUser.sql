use helpdesk;

INSERT INTO executor_history (id_solicitare, user_decizie, user_executor,data_decizie)
select s.id,null,d.userDSI, now()
  from solicitare as s,cl_diviziuni as d, cl_useri as u
  WHERE 
    u.id = s.id_user
    AND d.idDiviziune = u.id_diviziune
    AND d.userDSI is not null
    AND s.id not in (
      select s.id
        from solicitare as s,cl_diviziuni as d, cl_useri as u, executor_history as h
        WHERE 
          u.id = s.id_user
          AND d.idDiviziune = u.id_diviziune
          AND h.id_solicitare=s.id
    );

/*
select id,id_solicitare,user_decizie,user_executor, data_decizie 
  from executor_history
  where id_solicitare =48
*/

/*
select s.id,s.id_user,u.id_diviziune, d.userDSI as responsabilDSI
  from solicitare as s,cl_diviziuni as d, cl_useri as u, executor_history as h
  WHERE 
    u.id = s.id_user
    AND d.idDiviziune = u.id_diviziune
    AND h.id_solicitare=s.id 
*/    
/*

INSERT INTO executor_history (id_solicitare, user_decizie, user_executor,data_decizie)
VALUES (48, null, 25, NOW())
*/
