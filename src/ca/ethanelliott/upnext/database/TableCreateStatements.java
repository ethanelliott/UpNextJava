package ca.ethanelliott.upnext.database;

class TableCreateStatements {
    static String[] statements = {
            // parties table
            "CREATE TABLE IF NOT EXISTS parties (" +
                    "id integer PRIMARY KEY," +
                    "name text NOT NULL" +
                    ");",

            // users table
            "CREATE TABLE IF NOT EXISTS users (" +
                    "id integer PRIMARY KEY," +
                    "name text NOT NULL," +
                    "partyID integer NOT NULL," +
                    "FOREIGN KEY(partyID) REFERENCES parties(id)" +
                    ");",

            //playlist entries table
            "CREATE TABLE IF NOT EXISTS playlistEntries (" +
                    "id integer PRIMARY KEY," +
                    "name text NOT NULL," +
                    "partyID integer NOT NULL," +
                    "addedBy integer NOT NULL," +
                    "FOREIGN KEY(partyID) REFERENCES parties(id)," +
                    "FOREIGN KEY(addedBy) REFERENCES users(id)" +
                    ");",

            // playlist added table
            "CREATE TABLE IF NOT EXISTS parties (" +
                    "id integer PRIMARY KEY," +
                    "name text NOT NULL" +
                    ");",
    };
}
