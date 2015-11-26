package ch.cern.pcanilho.jsonBuilder.arch;

import org.json.JSONObject;

public class DB_JS_Object extends JSONObject {

	private String unitName;
	private DB_Column column;

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public DB_Column getColumn() {
		return column;
	}

	public void setValue(DB_Column value) {
		this.column = value;
	}

}
