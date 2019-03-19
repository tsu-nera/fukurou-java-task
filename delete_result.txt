/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Owner
 * Created: 2019/03/19
 */
CREATE OR REPLACE FUNCTION delete_result() AS $$
 
  DECLARE

  BEGIN
   
    DELETE FROM result
 
  END;
$$LANGUAGE plpgsql;

