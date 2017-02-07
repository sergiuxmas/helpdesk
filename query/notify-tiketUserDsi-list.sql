USE helpdesk;-- SELECT-- s.id as id,-- d.nume as domeniu,-- s.id_prioritate, -- sol_id_prioritate-- p.nume as prioritate,-- u.nume as user,-- (SELECT nume FROM cl_useri WHERE id=s.user_executor) as user_executor,-- DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y %H:%i:%s') as data_solicitarii,-- dv.idDiviziune as id_diviziune,-- dv.nume as diviziune,-- s.opened as opened

SELECT *
  FROM (SELECT s.id AS id,
              -- statut.nume AS statutNume,
               s.statut AS statut,
              -- tip.nume AS tip,
              -- (SELECT ex.id_prioritate
              --    FROM executare AS ex
              --   WHERE ex.id_solicitare = s.id)
              --    AS ex_prioritate,
              -- (SELECT CASE
              --            WHEN    ex_prioritate IS NOT NULL
              --                 OR ex_prioritate = 0
              --                 OR ex_prioritate <> s.id_prioritate
              --            THEN
              --               ex_prioritate
              --            ELSE
              --               s.id_prioritate
              --         END)
              --    AS id_prioritate,
              -- (SELECT cl_prioritate.nume
              --    FROM cl_prioritate
              --   WHERE cl_prioritate.id = id_prioritate)
              --    AS prioritate,
              -- (SELECT ex.id_domeniu
              --    FROM executare AS ex
              --   WHERE ex.id_solicitare = s.id)
              --    AS ex_domeniu,
              -- (SELECT CASE
              --            WHEN    ex_domeniu IS NOT NULL
              --                 OR ex_domeniu = 0
              --                 OR ex_domeniu <> s.id_domeniu
              --            THEN
              --               ex_domeniu
              --            ELSE
              --               s.id_domeniu
              --         END)
              --    AS sol_id_domeniu,
              -- (SELECT cl_domeniu.nume
              --    FROM cl_domeniu
              --   WHERE cl_domeniu.id = sol_id_domeniu)
              --    AS domeniu,
               DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y %H:%i:%s') AS data_solicitarii,
               s.id_user AS id_user,
               cl_useri.nume AS user,
              -- cl_useri.id_diviziune AS id_diviziune,
               (SELECT di.nume
                  FROM cl_diviziuni AS di
                 WHERE di.idDiviziune = id_diviziune)
                  AS diviziune,
               (SELECT h.user_executor
                  FROM solicitare AS sol, executor_history AS h
                 WHERE sol.id = h.id_solicitare AND sol.id = s.id
                ORDER BY h.data_decizie DESC
                 LIMIT 1)
                  AS user_executor,
               (SELECT u.nume
                  FROM cl_useri AS u
                 WHERE u.id = user_executor)
                  AS user_executor_name,
              -- s.opened AS opened,
               "" as new_messages
          FROM solicitare AS s
               LEFT JOIN cl_solicitare_statut AS statut
                  ON statut.id = s.statut
               LEFT JOIN cl_domeniu AS d
                  ON s.id_domeniu = d.id
               LEFT JOIN cl_prioritate AS p
                  ON s.id_prioritate = p.id
               LEFT JOIN cl_useri
                  ON s.id_user = cl_useri.id
               LEFT JOIN cl_diviziuni AS di
                  ON cl_useri.id_diviziune = di.idDiviziune
               LEFT JOIN cl_solicitare_tip AS tip
                  ON s.tip = tip.id
       ) AS lista_solicitarilor
   WHERE statut<>3
   AND (user_executor=IFNULL(51,user_executor) OR user_executor is null)
   ORDER BY data_solicitarii