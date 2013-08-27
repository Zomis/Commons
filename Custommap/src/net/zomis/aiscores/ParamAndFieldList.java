package net.zomis.aiscores;

import java.util.List;

public class ParamAndFieldList<Params, Field> {
	private List<Field>	list;
	private Params	param;

	public ParamAndFieldList(Params param, List<Field> fieldList) {
		this.param = param;
		this.list = fieldList;
	}
	
	public List<Field> getList() {
		return list;
	}
	public Params getParam() {
		return param;
	}
}
