package com.polenta.db.object.type;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.polenta.db.data.ResultSet;
import com.polenta.db.data.Row;
import com.polenta.db.exception.PolentaException;
import com.polenta.db.object.behavior.Insertable;
import com.polenta.db.object.behavior.Selectable;
import com.polenta.db.sorting.SortingExecutor;
import com.polenta.db.store.Storable;

public class Bag implements Insertable, Selectable, Storable {
	
	private Map<Integer, Row> rows = new LinkedHashMap<Integer, Row>();
	
	public ResultSet select(List<String> selectFields, Map<String, Object> whereConditions, List<String> orderByFields) throws PolentaException {
		//missing: validate if fields used on all clausules are valids to this bag
		
		ResultSet resultSet = new ResultSet();
		
		List<Row> filteredRows = filterRows(this.rows, whereConditions);
		
		for (Row row: filteredRows) {
			Row resultRow = new Row();
			for (String field: selectFields) {
				resultRow.set(field, row.get(field));
			}
			resultSet.add(resultRow);
		}
		
		if (orderByFields != null && !orderByFields.isEmpty()) {
			resultSet = SortingExecutor.sort(resultSet, orderByFields);
		}
		
		return resultSet;
	}
	
	protected List<Row> filterRows(Map<Integer, Row> allRows, Map<String, Object> whereConditions) {
		List<Row> filteredRows = new ArrayList<Row>();
		filteredRows.addAll(allRows.values());
		return filteredRows;
	}

	public synchronized void insert(Row row) {
		rows.put(rows.size() + 1, row);
	}

}
