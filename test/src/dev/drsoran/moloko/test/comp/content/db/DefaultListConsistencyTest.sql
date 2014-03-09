INSERT INTO lists (_id,
                   list_name,
                   list_created,
                   list_modified,
                   list_deleted,
                   locked,
                   archived,
                   position,
                   smart,
                   filter,
                   rtm_list_id)
   VALUES (1,
           "NonSmartList",
           1356998400000,
           1356998400001,
           NULL,
           0,
           0,
           -1,
           0,
           NULL,
           "1000");
           
INSERT INTO lists (_id,
                   list_name,
                   list_created,
                   list_modified,
                   list_deleted,
                   locked,
                   archived,
                   position,
                   smart,
                   filter,
                   rtm_list_id)
   VALUES (2,
           "NonSmartList1",
           1356998400000,
           1356998400001,
           NULL,
           0,
           0,
           -1,
           0,
           NULL,
           "1001");
        
UPDATE settings SET
   defaultlist_id = 1,
   rtm_defaultlist_id = "1000";