package ca.ethanelliott.upnext.database;

import java.util.HashMap;

class TableCreateStatements {

    static final HashMap<String, String> statements = new HashMap<String, String>() {{
        put(
                "create",
                "CREATE TABLE IF NOT EXISTS parties (" +
                        "id INTEGER PRIMARY KEY," +
                        "data BLOB NOT NULL" +
                        ");"
        );
    }};
}