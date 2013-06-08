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
   VALUES (1000,
           "List1",
           1356998400000,
           1356998400001,
           NULL,
           0,
           0,
           -1,
           0,
           NULL,
           "10000");

INSERT INTO taskseries ( _id,
                         task_created,
                         task_modified,
                         task_name,
                         source,
                         url,
                         recurrence,
                         recurrence_every,
                         location_id,
                         list_id,
                         tags,
                         rtm_taskseries_id)
   VALUES (10,
           1356998400000,
           1356998400001,
           "Task1",
           "JUNIT",
           "http://abc.de",
           "every 2 days",
           1,
           2000,
           1000,
           "tag1,tag2",
           "1000");
        
INSERT INTO rawtasks ( _id,
                      due,
                      has_due_time,
                      added,
                      completed,
                      deleted,
                      priority,
                      postponed,
                      estimate,
                      estimateMillis,
                      rtm_rawtask_id,
                      taskseries_id)
   VALUES (1,
           ${today},
           1,
           1356998400003,
           1356998400004,
           1356998400005,
           'n',
           2,
           "1h",
           36000000,
           "5000",
           10);
           
INSERT INTO rawtasks ( _id,
                      due,
                      has_due_time,
                      added,
                      completed,
                      deleted,
                      priority,
                      postponed,
                      estimate,
                      estimateMillis,
                      rtm_rawtask_id,
                      taskseries_id)
   VALUES (2,
           ${today},
           1,
           1356998400003,
           null,
           null,
           'n',
           2,
           "1h",
           36000000,
           "5000",
           10);
           
INSERT INTO taskseries ( _id,
                         task_created,
                         task_modified,
                         task_name,
                         source,
                         url,
                         recurrence,
                         recurrence_every,
                         location_id,
                         list_id,
                         tags,
                         rtm_taskseries_id)
   VALUES (11,
           1356998400000,
           1356998400001,
           "MultiTask",
           "JUNIT",
           null,
           null,
           0,
           null,
           1000,
           null,
           "1001");
        
INSERT INTO rawtasks ( _id,
                      due,
                      has_due_time,
                      added,
                      completed,
                      deleted,
                      priority,
                      postponed,
                      estimate,
                      estimateMillis,
                      rtm_rawtask_id,
                      taskseries_id)
   VALUES (3,
           null,
           0,
           1356998400003,
           null,
           null,
           '1',
           0,
           null,
           -1,
           "5001",
           11);
           
INSERT INTO rawtasks ( _id,
                      due,
                      has_due_time,
                      added,
                      completed,
                      deleted,
                      priority,
                      postponed,
                      estimate,
                      estimateMillis,
                      rtm_rawtask_id,
                      taskseries_id)
   VALUES (4,
           ${tomorrow},
           0,
           1356998400004,
           1356998400005,
           null,
           '2',
           0,
           "1 day",
           86400000,
           "5002",
           11);
           
INSERT INTO rawtasks ( _id,
                      due,
                      has_due_time,
                      added,
                      completed,
                      deleted,
                      priority,
                      postponed,
                      estimate,
                      estimateMillis,
                      rtm_rawtask_id,
                      taskseries_id)
   VALUES (5,
           ${tomorrow},
           0,
           1356998400004,
           null,
           null,
           '2',
           0,
           "2 hours",
           7200000,
           "5002",
           11);
           
INSERT INTO rawtasks ( _id,
                      due,
                      has_due_time,
                      added,
                      completed,
                      deleted,
                      priority,
                      postponed,
                      estimate,
                      estimateMillis,
                      rtm_rawtask_id,
                      taskseries_id)
   VALUES (6,
           ${yesterday},
           0,
           1356998400004,
           null,
           null,
           '2',
           0,
           "10 minutes",
           600000,
           "5002",
           11);           

INSERT INTO rawtasks ( _id,
                      due,
                      has_due_time,
                      added,
                      completed,
                      deleted,
                      priority,
                      postponed,
                      estimate,
                      estimateMillis,
                      rtm_rawtask_id,
                      taskseries_id)
   VALUES (7,
           ${in2Days},
           0,
           1356998400004,
           null,
           null,
           '2',
           0,
           "10 minutes",
           600000,
           "5002",
           11);   