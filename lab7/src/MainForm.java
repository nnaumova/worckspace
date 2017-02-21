

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;




public class MainForm  extends JFrame implements TreeSelectionListener{
	
	private static final long serialVersionUID = 1L;

	private Model model = new Model();
	private JTree theTree;
	
	private JPanel panel = new JPanel();
	private JButton save;
	private JButton open;
	private JButton insertButton;
	private JButton deleteButton;
	private JButton changeLookFeelButton;
	private JPanel panel1 = new JPanel();
	private EmployeeForm curView;

	private JTextField searchF;
	private JComboBox<String> searchCmb;
	private JButton searchNextBtn;
	private static MainForm instance;

	public Searcher seacher = new Searcher();

	private UIManager.LookAndFeelInfo installedLF[];

	private int current;
	
	public static MainForm getInstance() {
		return instance;
	}
	
	public MainForm() {
		instance = this;
		setTitle("База сотрудников");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildGUI();
		installedLF = UIManager.getInstalledLookAndFeels();
		current = 0;
		setVisible(true);
		try {
			UIManager.setLookAndFeel(installedLF[current].getClassName());
		} catch (Exception ex) {
			System.out.println("Exception 1");
		}
		addListeners();
	}
	protected void buildGUI() {
		theTree = new JTree(model);
		expandAll();
		theTree.addTreeSelectionListener(this);
		int mode = TreeSelectionModel.SINGLE_TREE_SELECTION;
		theTree.getSelectionModel().setSelectionMode(mode);

		panel1.setLayout(new GridLayout(1, 4));
		panel.setLayout(new GridLayout(1, 2));
		panel.add(new JScrollPane(theTree));
		
		setLayout(new GridLayout(2, 2));

		add(panel);
		add(panel1);

		open = new JButton("Open");
		save = new JButton("Save as");
		

		insertButton = new JButton("Insert Person");

		deleteButton = new JButton("Delete Selected");

		changeLookFeelButton = new JButton("Change Look & Feel");

		

		panel1.add(open);
		panel1.add(save);
		panel1.add(insertButton);
		panel1.add(deleteButton);
		panel1.add(changeLookFeelButton);
		

		
		Box searchPanel = Box.createHorizontalBox();
		searchPanel.add(new JLabel("Искать:  "));
		searchF = new JTextField();
		searchPanel.add(searchF);
		String items[] = { "By name", "By surname", "By patronimic",
				"By birthday",  "By tabel number" };
		searchCmb = new JComboBox<>(new DefaultComboBoxModel<>(items));
		searchPanel.add(searchCmb);
		searchNextBtn = new JButton("Find next");
		searchPanel.add(searchNextBtn);
		add(searchPanel, "South");
	}
	public void addListeners() {
		final MainForm form = this;

		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.showOpenDialog(form);
				if (jfc.getSelectedFile() != null)
					model.readFile(jfc.getSelectedFile());
			}
		});

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.showSaveDialog(form);
				if (jfc.getSelectedFile() != null)
					model.writeFile(jfc.getSelectedFile());
			}
		});

		

		insertButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Employee entry = new Employee();
				TreePath path = model.insertPerson(entry);
				if (path != null) {
					theTree.scrollPathToVisible(path);
				}
				MainForm.getInstance().getModel().fireDataChange();
				theTree.setSelectionPath(path);
			}
		});

		searchF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				searchNextBtn.doClick();
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) theTree
						.getLastSelectedPathComponent();
				if (selectedNode != null && selectedNode.getParent() != null)
					model.deletePerson(selectedNode);
				if (curView != null)
					panel.remove(curView);
				form.revalidate();
			}
		});

		searchNextBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Employee entry;
				TreePath originalPath = theTree.getSelectionPath();
				switch (searchCmb.getSelectedItem().toString()) {
				case "By name":
					entry = new Employee( searchF.getText(),"", "",
							 "", "", "");
					break;

				case "By surname":
					entry = new Employee( "",searchF.getText(), "",
							 "", "", "");
					break;
				case "By patronimic":
					entry = new Employee("", "", searchF.getText(),
							 "", "", "");
					break;
				
				case "By id":
					entry = new Employee("", "", "",  searchF
							.getText(), "", "");
					break;

				case "By birth":
					entry = new Employee("", "", "",  "", searchF
							.getText(), "");
					break;
				default:
					entry = null;
				}
				TreePath path = seacher.findNext(entry);
				if (path != null) {
					theTree.setSelectionPath(path);
					theTree.expandPath(path);
				} else {
					theTree.setSelectionPath(originalPath);
					theTree.expandPath(originalPath);
					JOptionPane.showMessageDialog(form, "Employee not found!");
				}
			}
		});

		changeLookFeelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				current++;
				if (current > installedLF.length - 1) {
					current = 0;
				}

				System.out.println("New Current Look&Feel:" + current);
				System.out.println("New Current Look&Feel Class:"
						+ installedLF[current].getClassName());

				try {
					UIManager.setLookAndFeel(installedLF[current]
							.getClassName());
					SwingUtilities.updateComponentTreeUI(form);
				} catch (Exception ex) {
					System.out.println("exception");
				}

			}
		});
	}

	public void valueChanged(TreeSelectionEvent event) {
		TreePath path = theTree.getSelectionPath();
		if (path == null)
			return;

		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path
				.getLastPathComponent();
		if (selectedNode != null
				&& selectedNode.getUserObject() instanceof Employee) {
			if (curView != null)
				panel.remove(curView);
			curView = ((Employee) selectedNode.getUserObject())
					.getView();
			panel.add(curView);
			this.revalidate();
		} else {
			if (curView != null)
				panel.remove(curView);
			this.revalidate();
		}
	}

	public Model getModel() {
		return model;
	}

	public DefaultMutableTreeNode getSelectedNode() {
		return (DefaultMutableTreeNode) theTree.getLastSelectedPathComponent();
	}

	public void expandPath(TreePath path) {
		theTree.expandPath(path);
	}

	public void selectPath(TreePath path) {
		theTree.setSelectionPath(path);
	}

	public void expandAll() {
		for (int i = 0; i < theTree.getRowCount(); i++) {
			theTree.expandRow(i);
		}
	}
}


