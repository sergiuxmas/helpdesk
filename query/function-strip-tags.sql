DROP FUNCTION IF EXISTS helpdesk.strip_tags;
CREATE FUNCTION helpdesk.`strip_tags`($str text) RETURNS text CHARSET utf8_unicode_ci
BEGIN
    DECLARE $start, $end INT DEFAULT 1;
    LOOP
        SET $start = LOCATE("<", $str, $start);
        IF (!$start) THEN RETURN $str; END IF;
        SET $end = LOCATE(">", $str, $start);
        IF (!$end) THEN SET $end = $start; END IF;
        SET $str = INSERT($str, $start, $end - $start + 1, "");
    END LOOP;
END;
