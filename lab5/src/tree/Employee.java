package tree;

import java.io.Serializable;




public class Employee extends DictionaryElem implements Serializable {

	private static final long serialVersionUID = -1268094734864998615L;
	String fam, name, father, tab, birth, path;
	

	public Employee() {
		this.fam = "New";
		this.name = "";
		this.father = "";
		this.tab = "";
		this.birth = "";
		this.path = "";
	}

	public Employee(String fam, String name, String father, 
			String tab, String birth, String path) {
		this.fam = fam;
		this.name = name;
		this.father = father;
		
		this.tab = tab;
		this.birth = birth;
		this.path = path;
	}

	public String getType() {
		return "Entry";
	}

	public String getValue() {
		return toString();
	}

	public String toString() {
		return fam + " " + name + " " + father;
	}

	public boolean equals(Object ob) {
		if (!(ob instanceof Employee))
			return false;
		Employee o = (Employee) ob;
		return o.fam.equals(fam) && o.name.equals(name)
				&& o.father.equals(father) 
				&& o.tab.equals(tab) && o.birth.equals(birth);
	}



}
