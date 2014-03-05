INSERT INTO modifications ( _id,
                            entity_uri,
                            property,
                            new_value,
                            synced_value,
                            timestamp)
   VALUES (1,
           "content://dev.drsoran.provider.Rtm/lists/1",
           "list_name",
           "RenamedList",
           "List",
           1356998400000);

INSERT INTO modifications ( _id,
                            entity_uri,
                            property,
                            new_value,
                            synced_value,
                            timestamp)
   VALUES (2,
           "content://dev.drsoran.provider.Rtm/tasks/10",
           "postponed",
           "1",
           null,
           1356998400000);
           
 INSERT INTO modifications ( _id,
                             entity_uri,
                             property,
                             new_value,
                             synced_value,
                             timestamp)
   VALUES (3,
           "content://dev.drsoran.provider.Rtm/tasks/10",
           "url",
           null,
           "http://www.google.de",
           1356998400000);
