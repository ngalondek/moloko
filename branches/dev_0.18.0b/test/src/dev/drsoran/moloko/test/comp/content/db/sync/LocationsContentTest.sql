INSERT INTO locations (  _id,
                         location_name,
                         longitude,
                         latitude,
                         address,
                         viewable,
                         zoom,                       
                         rtm_location_id)
   VALUES (1,
           "Location1",
           1.0,
           2.0,
           NULL,
           1,
           10,
           "1000");
        
INSERT INTO locations (  _id,
                         location_name,
                         longitude,
                         latitude,
                         address,
                         viewable,
                         zoom,                       
                         rtm_location_id)
   VALUES (2,
           "LocationWithAddr",
           3.0,
           4.0,
           "Main Street",
           1,
           20,
           "1001");

