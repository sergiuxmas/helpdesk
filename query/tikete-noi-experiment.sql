
select * from solicitare where solicitare.id=143;

select * from tiket_open_history where id_tiket=143; 

select * from messages where messages.id_solicitare=143 order by data_expedierii DESC;

select toh.id_tiket,toh.id_user,m.id, m.data_expedierii from tiket_open_history toh, messages m
where m.id_solicitare=toh.id_tiket 
and toh.id_tiket=143 and toh.id_user=55
and toh.date_opened<m.data_expedierii
order by m.data_expedierii desc;