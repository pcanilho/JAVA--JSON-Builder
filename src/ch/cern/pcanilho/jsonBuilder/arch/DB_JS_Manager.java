package ch.cern.pcanilho.jsonBuilder.arch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;

import ch.cern.pcanilho.jsonBuilder.system.FileHandler;

public class DB_JS_Manager {

	private Map<String, List<DB_JS_Object>> data_groups = new HashMap<String, List<DB_JS_Object>>();

	public void addChildren(DB_JS_Object object) {
		/**
		 * ADD NEW LIST IF UNIT NAME IS NOT FOUND, ELSE ADD TO ALREADY CREATED
		 * LIST IF UNIT NAME IS FOUND
		 */

		if (!checkInsideMap(object))
			addToListMap(object);
	}

	private void addToListMap(DB_JS_Object object) {
		List<DB_JS_Object> data_entry = data_groups.get(object.getUnitName());
		data_entry.add(object);
	}

	private boolean checkInsideMap(DB_JS_Object object) {
		if (!data_groups.containsKey(object.getUnitName())) {
			List<DB_JS_Object> object_list = new ArrayList<DB_JS_Object>();
			object_list.add(object);
			data_groups.put(object.getUnitName(), object_list);
			return true;
		}
		return false;
	}

	public String getDataToPrint() {
		List<List<DB_JS_Object>> list = new ArrayList<List<DB_JS_Object>>();
		try {
			for (Entry<String, List<DB_JS_Object>> entry : data_groups.entrySet())
				list.add(entry.getValue());
			return FileHandler.query_to_JSON(list);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void printJSON() {

		try {
			List<List<DB_JS_Object>> list = new ArrayList<List<DB_JS_Object>>();
			for (Entry<String, List<DB_JS_Object>> entry : data_groups.entrySet())
				list.add(entry.getValue());

			System.out.println(FileHandler.query_to_JSON(list));
		} catch (JSONException e) {
			System.err.println(this.getClass().getName() + " -> printJSON: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
