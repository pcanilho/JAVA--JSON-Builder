package ch.cern.pcanilho.jsonBuilder.system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.cern.pcanilho.jsonBuilder.arch.DB_JS_Manager;
import ch.cern.pcanilho.jsonBuilder.arch.DB_JS_Object;

public abstract class FileHandler {

	// OBJECTS
	private static JSONArray json_array;
	private static JSONObject json_display, json_root_display;
	private static List<JSONObject> json_objects;

	public static String query_to_JSON(List<List<DB_JS_Object>> data) throws JSONException {
		if (data == null || data.size() < 1)
			return "JSONBuilder -> INPUT NOT VALID";

		// INIT
		json_array = new JSONArray();
		json_display = new JSONObject();
		json_objects = new ArrayList<JSONObject>();

		json_root_display = new JSONObject();

		// DATASET
		for (List<DB_JS_Object> list : data) {
			for (DB_JS_Object d : list) {
				JSONObject json_object = new JSONObject();
				json_object.put("name", d.getColumn().getName());
				json_object.put("size", d.getColumn().getValue());
				json_array.put(json_object);

				json_objects.add(json_object);
				json_display.put(d.getUnitName(), json_array);
			}
			json_root_display.put("children", json_display);
		}
		return json_root_display.toString();
	}

	public static void saveJSONFile(DB_JS_Manager manager) {
		int r = JOptionPane.showConfirmDialog(null, "Do you want to save the result JSON file?", "Build JSON", JOptionPane.YES_NO_OPTION);
		if (r == JOptionPane.YES_OPTION) {
			JFileChooser jc = new JFileChooser();
			jc.showSaveDialog(null);
			File f = new File(jc.getSelectedFile().getAbsolutePath() + ".json");
			if (f.exists()) {
				int r1 = JOptionPane.showConfirmDialog(null, "File already exists!\nWould you like to replace it?", "File found", JOptionPane.YES_NO_OPTION);
				if (r1 == JOptionPane.YES_OPTION) {
					f.delete();
					try {
						f.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					FileWriter fw = null;
					BufferedWriter bw = null;
					try {
						if (!f.exists())
							f.createNewFile();
						fw = new FileWriter(f);
						bw = new BufferedWriter(fw);

						bw.write(manager.getDataToPrint());
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							if (bw != null)
								bw.close();
							if (fw != null)
								fw.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				FileWriter fw = null;
				BufferedWriter bw = null;
				try {
					if (!f.exists())
						f.createNewFile();
					fw = new FileWriter(f);
					bw = new BufferedWriter(fw);

					bw.write(manager.getDataToPrint());
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (bw != null)
							bw.close();
						if (fw != null)
							fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("FILE SAVED TO: " + f.getAbsolutePath() + " ... SUCCESS");
		}
	}

	public static String loadQuery(String path) {
		String query = "";
		File f = new File(path);
		if (f.exists()) {
			FileReader r = null;
			BufferedReader br = null;
			try {
				r = new FileReader(f);
				br = new BufferedReader(r);

				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = br.readLine()) != null)
					sb.append(line + "\n");

				query = sb.toString();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)
						br.close();
					if (r != null)
						r.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}else{
			System.out.println("ERROR -> Query file not found.");
			System.exit(1);
		}

		return query;
	}
}
