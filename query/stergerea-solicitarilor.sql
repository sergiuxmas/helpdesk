/*
select m.id from messages m
WHERE m.id_solicitare in (110,109,117,118,119);

select fis.id from files fis
where fis.id_tiket in (110,109,117,118,119);

select ex.id from executare ex
where ex.id_solicitare in (110,109,117,118,119);

select s.id from solicitare s
where s.id in (110,109,117,118,119);
*/
delete from messages 
WHERE id_solicitare in (198);

delete from files
where id_tiket in (198);

delete from executare
where id_solicitare in (198);

delete from solicitare
where id in (198);
