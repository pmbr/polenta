package com.polenta.db.command.impl;

import com.polenta.db.command.Command;
import com.polenta.db.exception.InvalidStatementException;
import com.polenta.db.exception.PolentaException;
import com.polenta.db.object.behavior.Alterable;
import com.polenta.db.store.Store;

public class AlterCommand implements Command {

	@SuppressWarnings("unused")
	private String statement;
	
	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String execute() throws PolentaException {
		try {
			((Alterable)Store.getInstance().get(null)).alter(null);;
		} catch (ClassCastException e) {
			throw new InvalidStatementException("ALTER is not supported by this object type.");
		}
		
		return "OK";
	}
	
}