select 
h.id,
(select nume from cl_useri where id=h.user_executor) as user_executor,
IFNULL((select nume from cl_useri where id=h.user_decizie),"SI") as user_decizie,
DATE_FORMAT(h.data_decizie,'%d.%m.%Y %h:%i:%s') as data_decizie 
from executor_history as h LEFT JOIN cl_useri as u
on 
  u.id=h.user_executor AND
  h.id_solicitare = 52
order by h.data_decizie desc
