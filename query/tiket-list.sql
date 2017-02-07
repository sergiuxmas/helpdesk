use helpdesk;
    SELECT
    -- DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y') as h1,
    -- DATE_FORMAT(STR_TO_DATE('03.09.2015', '%d.%m.%Y'),'%d.%m.%Y') as h2,
    s.id as id,
    d.nume as domeniu,
    LEFT(s.descriere,
    100) as descriere,
    s.id_prioritate,
    p.nume as prioritate,
    u.nume as user,
    s.data_solicitarii as data_solicitarii,
    dv.idDiviziune as id_diviziune,
    dv.nume as diviziune FROM
        solicitare AS s,
        cl_domeniu AS d,
        cl_prioritate AS p,
        cl_useri AS u,
        cl_diviziuni as dv 
    WHERE
        s.id_domeniu=d.id 
        AND s.id_prioritate=p.id 
        AND s.id_user=u.id 
        AND dv.idDiviziune=u.id_diviziune 
        AND DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')=DATE_FORMAT(STR_TO_DATE('03.09.2015', '%d.%m.%Y'),'%d.%m.%Y')   
    order by
        data_solicitarii asc LIMIT 0,
        1000