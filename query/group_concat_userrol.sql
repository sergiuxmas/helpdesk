/*
select u.username ,group_concat(ar.id_rol separator ',') roluri 
from cl_useri u left join atrib_user_rol ar
on 
 u.id=ar.id_user
 group by u.id;
*/
 
select u.username,group_concat(cat.nume separator ',') roluri 
from cl_useri u 
left join atrib_user_rol ar
on 
u.id=ar.id_user
left join cl_categorii cat
on
ar.id_rol=cat.id
group by u.id
order by u.id;