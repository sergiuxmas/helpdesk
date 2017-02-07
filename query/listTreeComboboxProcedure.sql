DROP PROCEDURE IF EXISTS helpdesk.listTreeCombobox;
CREATE PROCEDURE helpdesk.`listTreeCombobox`()
BEGIN
  DECLARE _tbl VARCHAR(100);
  DECLARE cur CURSOR FOR SELECT nume FROM cl_diviziuni;
  
  SET @b = 0;
  OPEN cur;
  REPEAT
  FETCH cur INTO _tbl;
    select _tbl;
  SET @b := @b+1;
  UNTIL @b = 4 END REPEAT;
  CLOSE cur;
  
  
	-- SET @myvar := (SELECT nume FROM cl_diviziuni WHERE idDiviziune=1);
  -- SELECT @myvar;
  
END;
