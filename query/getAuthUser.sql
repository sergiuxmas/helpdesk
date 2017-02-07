use helpdesk;

select u.id,u.idnp,u.nume as nume,u.prenume,u.id_categoria,u.username,u.id_loginCnam,u.atasat,
 (select cl_diviziuni.idDiviziune from cl_diviziuni WHERE idDiviziune=u.id_diviziune) as id_diviziune,
 (select cl_diviziuni.nume from cl_diviziuni WHERE idDiviziune=u.id_diviziune) as diviziune
from cl_useri as u
where  u.id_loginCnam=3359;


