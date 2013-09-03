package net.zomis.aiscores;

public class ParamAndField<Params, Field> {
	private Params	params;
	private Field	field;

	public ParamAndField(Params params, Field field) {
		this.params = params;
		this.field = field;
	}
	public Field getField() {
		return field;
	}
	public Params getParams() {
		return params;
	}
	@Override
	public String toString() {
		return field + " -- " + params;
	}
}
