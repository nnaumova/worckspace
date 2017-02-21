


public class Employee {
	
	String  name,sur, patr, id,  path;
	String birth;
	private EmployeeForm view;

	public Employee() {
		name = "New";
		sur = "";
		patr = "";
		id = "000";
		birth = "00.00.0000";
		path = "";
	}

	public Employee( String n,String fam, String father,
			 String tab, String bd, String p) {
		sur = fam;
		name = n;
		patr = father;
		
		id = tab;
		birth = bd;
		path = p;
	}

	public EmployeeForm getView() {
		return view = new EmployeeForm(name,sur, patr,id, birth, 
				 path);
	}

	public void update() {
		sur = view.getFamVal();
		name = view.getNameVal();
		patr = view.getFatherVal();
		
		id = view.getTabVal();
		birth = view.getBirthVal();
		path = view.getPathVal();
	}

	public Employee getData() {
		return new Employee( name,sur, patr, id, birth, path);
	}

	public String getValue() {
		return toString();
	}
}
