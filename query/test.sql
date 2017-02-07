use helpdesk;

call tiketList(51);
call tiketListUserDSI(51);

call tiketListUser(61);

call tiketListUser(55);

select * from tiket_open_history where id_tiket=160 order by date_opened desc;

select * from messages where id_solicitare=160 order by data_expedierii desc;