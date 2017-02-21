package tree;



public class EmployeeAdapter extends Employee {

	private static final long serialVersionUID = 8574279049831135128L;
	private AdditionalForm view;

	public EmployeeAdapter() {
		super();
	}

	public EmployeeAdapter(String fam, String name, String father,
			 String tab, String birth, String path) {
		super(fam, name, father,  tab, birth, path);
	}

	public AdditionalForm getView() {
		return view = new AdditionalForm(fam, name, father, tab,birth,
				 path);
	}

	public void update() {
		fam = view.getFamVal();
		name = view.getNameVal();
		father = view.getFatherVal();
		
		tab = view.getTabVal();
		birth = view.getbirthVal();
		path = view.getPathVal();
	}

	public Employee getData() {
		return new Employee(fam, name, father, tab, birth, path);
	}

}
