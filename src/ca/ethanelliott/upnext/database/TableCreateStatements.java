package ca.ethanelliott.upnext.database;

class TableCreateStatements {
    static String[] statements = {
            // parties table
            "CREATE TABLE IF NOT EXISTS parties (" +
                    "id INTEGER PRIMARY KEY," +
                    "data BLOB NOT NULL" +
                    ");",
    };
}
