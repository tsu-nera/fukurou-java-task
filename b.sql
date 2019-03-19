CREATE OR REPLACE FUNCTION save_result(
i_salary     IN integer,
i_age        IN integer,
i_pref_id    IN integer,
i_business   IN integer,
i_health     IN integer,
i_pension    IN integer,
i_employment IN integer,
i_total      IN integer,
o_num  OUT integer  
) AS $$
 
  DECLARE
    num integer;

  BEGIN
    --numのMAX+1を取得し（なければ1）、それと引数を元にresultへINSERT
    SELECT COALESCE(MAX(input_num)+1,1) INTO num FROM result;

    --IF num IS NULL THEN
    --  num := 1;
    --ELSE
    --  num := num + 1;
    --END IF; 

    o_num := num;

    INSERT INTO result
    (input_num, salary, age, pref_id, business, health, pension,
    employment, total, input_time)
    VALUES (num,i_salary,i_age,i_pref_id,i_business,i_health,i_pension,
    i_employment,i_total, now());
  
  END;
$$LANGUAGE plpgsql;