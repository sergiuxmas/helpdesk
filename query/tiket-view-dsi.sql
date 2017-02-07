SELECT
        s.id as id,
        d.nume as domeniu,
        s.descriere as descriere,
        s.id_prioritate,
        s.statut as statut,
        st.nume as statutNume,
        p.nume as prioritate,
        u.nume as user,
        DATE_FORMAT(s.data_solicitarii,
        '%d.%m.%Y %H:%i:%s') as data_solicitarii,
        dv.idDiviziune as id_diviziune,
        dv.nume as diviziune,
        (select
            h.id 
        from
            solicitare as sol,
            executor_history as h      
        where
            sol.id=h.id_solicitare        
            and sol.id = s.id      
        order by
            h.data_decizie desc LIMIT 1) as id_executor_history,
        
        (
          select user_executor from executor_history where id=id_executor_history 
        ) as user_executor,
        (
          select executor_history.acceptat from executor_history where executor_history.id=78
        ) as acceptatp,
        (select
            u.nume 
        from
            cl_useri as u 
        where
            u.id=user_executor)as user_executor_name 
    FROM
        solicitare AS s,
        cl_domeniu AS d,
        cl_prioritate AS p,
        cl_useri AS u,
        cl_diviziuni as dv,
        cl_solicitare_statut as st 
    WHERE
        s.id_domeniu=d.id 
        AND s.id_prioritate=p.id 
        AND s.id_user=u.id 
        AND dv.idDiviziune=u.id_diviziune 
        AND st.id=s.statut 
        AND s.id=52