package ch.cern.pcanilho.jsonBuilder.main;

import java.util.HashMap;
import java.util.Map;

import ch.cern.pcanilho.jsonBuilder.system.DatabaseConnection;
import ch.cern.pcanilho.jsonBuilder.system.FileHandler;
import ch.cern.pcanilho.jsonBuilder.system.StringHelper;

public abstract class JSONBuilder {

	public static void main(String[] args) {
		String def_query = "queries/query_rscp.sql";

		if (args != null & args.length > 0)
			if (args[0].equals("-q")){
				def_query = args[1];
				if(!def_query.endsWith(".sql"))
				{
					System.err.println("Selected file is not a valid .SQL");
					System.exit(1);
				}
			}

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "...");
		params.put("password", "...");
		params.put("database", "...");
		params.put("server", "...");

		// System.out.println(FileHandler.loadQuery("queries/query_rscp.sql"));

		DatabaseConnection.connectDatabase(params);
		DatabaseConnection.execQueryOnCurrentDB(StringHelper.escapeSQL(FileHandler.loadQuery(def_query)));

		// try {
		// System.out.println(FileHandler.buildJSONFile());
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
	}

}
