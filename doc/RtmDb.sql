CREATE TABLE Rtm.tags (
       "_id" INTEGER NOT NULL CONSTRAINT PK_TAGS PRIMARY KEY AUTOINCREMENT
     , tag TEXT NOT NULL
);

CREATE TABLE Rtm.notes (
       "_id" INTEGER NOT NULL
     , created INTEGER NOT NULL
     , modified INTEGER
     , title TEXT NOT NULL
     , text TEXT NOT NULL
     , CONSTRAINT PK_NOTES PRIMARY KEY ("_id")
);

CREATE TABLE Rtm.locations (
       "_id" INTEGER NOT NULL
     , name TEXT NOT NULL
     , longitude REAL NOT NULL
     , latitude REAL NOT NULL
     , address TEXT
     , viewable INTEGER NOT NULL DEFAULT 1
     , zoom INTEGER
     , CONSTRAINT PK_LOCATIONS PRIMARY KEY ("_id")
);

CREATE TABLE Rtm.tasks (
       "_id" INTEGER NOT NULL
     , due INTEGER
     , added INTEGER
     , completed INTEGER
     , deleted INTEGER
     , priority INTEGER
     , postponed INTEGER DEFAULT 0
     , estimate TEXT
     , CONSTRAINT PK_TASKS PRIMARY KEY ("_id")
);

CREATE TABLE Rtm.taskseries (
       "_id" INTEGER NOT NULL
     , created INTEGER NOT NULL
     , modified INTEGER
     , name TEXT
     , source TEXT
     , url TEXT
     , CONSTRAINT PK_TASKSERIES PRIMARY KEY ("_id")
     , CONSTRAINT notes FOREIGN KEY ("_id")
                  REFERENCES Rtm.notes ("_id")
     , CONSTRAINT tags FOREIGN KEY ("_id")
                  REFERENCES Rtm.tags ("_id")
     , CONSTRAINT task FOREIGN KEY ("_id")
                  REFERENCES Rtm.tasks ("_id")
     , CONSTRAINT location FOREIGN KEY ("_id")
                  REFERENCES Rtm.locations ("_id")
);

CREATE TABLE Rtm.lists (
       "_id" INTEGER NOT NULL
     , name TEXT NOT NULL
     , CONSTRAINT PK_LISTS PRIMARY KEY ("_id")
     , CONSTRAINT taskseries FOREIGN KEY ("_id")
                  REFERENCES Rtm.taskseries ("_id")
);

