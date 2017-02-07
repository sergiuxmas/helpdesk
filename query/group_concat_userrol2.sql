SELECT * FROM
(
SELECT u.id,u.idnp,u.nume,u.prenume,u.id_diviziune, di.nume diviziune_nume, u.username,u.id_loginCnam,u.atasat,u.priority, group_concat(cat.id SEPARATOR ',') id_categoria, group_concat(cat.nume SEPARATOR ',') id_categoria_nume
FROM cl_useri u
LEFT JOIN atrib_user_rol ar
    ON u.id=ar.id_user
LEFT JOIN cl_categorii cat
    ON ar.id_rol=cat.id
LEFT JOIN cl_diviziuni di
    ON u.id_diviziune=di.idDiviziune
WHERE u.id_loginCnam=IFNULL(null,u.id_loginCnam)
GROUP BY u.id
LIMIT 0,1000
) AS it
WHERE (it.id_diviziune is null OR it.id_categoria is null);