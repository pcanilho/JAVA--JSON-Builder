package ch.cern.pcanilho.jsonBuilder.arch;

import java.util.Comparator;

public class DB_Column_Comparator implements Comparator<DB_Column> {

	@Override
	public int compare(DB_Column o1, DB_Column o2) {
		if (Double.parseDouble(o1.getValue()) > Double.parseDouble(o2.getValue()))
			return 1;
		return (Double.parseDouble(o1.getValue()) == Double.parseDouble(o2.getValue())) ? -1 : 0;
	}

}
