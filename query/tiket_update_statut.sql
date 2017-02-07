use helpdesk;

-- select solicitare.id, solicitare.tip, solicitare.statut, solicitare.id_prioritate, solicitare.id_domeniu, solicitare.descriere, solicitare.id_user, solicitare.data_solicitarii, solicitare.data_stabilirii_prioritatii, solicitare.prioritate_DSI, solicitare.data_rezervarii, solicitare.opened, solicitare.data_inchiderii_solicitarii 
update solicitare
SET statut=2
where id=77