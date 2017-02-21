package tree;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.miginfocom.swing.MigLayout;


public class AdditionalForm extends JPanel {

	private static final long serialVersionUID = 1L;

	JTextField fam, name, father, path, tab,  birth;

	//PicturePanel picture;
	//private JFormattedTextField tab,  birth;
	private JButton editButton;
	
	public AdditionalForm(String fam, String name, String father,
			 String tab, String birth, String path) {
		editButton = new JButton("Save");
		setLayout(new GridLayout(7,2));
		//
		this.fam = new JTextField(fam);
		this.name = new JTextField(name);
		this.father = new JTextField(father);
		this.tab = new JTextField(tab);
		this.birth = new JTextField(birth);
		//this.tab = new JFormattedTextField(tab);
		//this.birth = new JFormattedTextField(birth);
		this.path = new JTextField(path);
		//picture = new PicturePanel();
		
		add(new JLabel("Фамилия"));
		add(this.fam);
		add(new JLabel("Имя"));
		add(this.name);
		
		
		add(new JLabel("Отчество"));
		add(this.father);
		add(new JLabel("Табельный номер"));
		add(this.tab );
		add(new JLabel("Дата рождения"));
		add(this.birth);
		
		add(new JLabel("Путь к фотографии"));
		add(this.path);
		add(editButton);
		//add(picture);
/*
		try {
			this.tab.setFormatterFactory(new DefaultFormatterFactory(
					new MaskFormatter("###")));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		try {
			this.birth.setFormatterFactory(new DefaultFormatterFactory(
					new MaskFormatter("##.##.####")));
		} catch (ParseException e) {
		
			e.printStackTrace();
		}
*/
		final AdditionalForm form = this;

		this.fam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				form.name.requestFocus();
			}
		});
		this.name.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				form.father.requestFocus();
			}
		});
		this.father.addActionListener(new ActionListener() {

			

			@Override
			public void actionPerformed(ActionEvent e) {
				form.tab.requestFocus();
			}
		});
		this.tab.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				form.birth.requestFocus();
			}
		});
		this.birth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				form.path.requestFocus();
			}
		});
		

		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				if (form.fam.getText().isEmpty()) {
					JOptionPane.showMessageDialog(form, "Fam cannot be empty");
					return;
				}
				if (form.name.getText().isEmpty()) {
					JOptionPane.showMessageDialog(form, "Name cannot be empty");
					return;
				}
				if (form.father.getText().isEmpty()) {
					JOptionPane.showMessageDialog(form,
							"Father name cannot be empty");
					return;
				}
			
				
				if (form.tab.getText().isEmpty()) {
					JOptionPane.showMessageDialog(form,
							"Tab number cannot be empty");
					return;
				}
				if (form.birth.getText().isEmpty()) {
					JOptionPane.showMessageDialog(form,
							"birth cannot be empty");
					return;
				}
				if (!Character.isLetter(form.fam.getText().charAt(0))) {
					form.fam.setText("");
					JOptionPane.showMessageDialog(form,
							"Fam must be started from letter");
					return;
				}
				if (!Character.isLetter(form.name.getText().charAt(0))) {
					form.name.setText("");
					JOptionPane.showMessageDialog(form,
							"Name must be started from letter");
					return;
				}
				if (!Character.isLetter(form.father.getText().charAt(0))) {
					form.father.setText("");
					JOptionPane.showMessageDialog(form,
							"Father must be started from letter");
					return;
				}

				TreePath newPath = null;
				DefaultMutableTreeNode selectedNode = MainForm.getInstance()
						.getSelectedNode();
				if (selectedNode != null
						&& selectedNode.getUserObject() instanceof Employee) {
					newPath = MainForm
							.getInstance()
							.getModel()
							.updatePerson(
									(EmployeeAdapter) selectedNode
											.getUserObject());
				}

				if (newPath != null) {
					MainForm.getInstance().getModel().fireDataChange();
					MainForm.getInstance().expandPath(newPath.getParentPath());
					MainForm.getInstance().selectPath(newPath);
				}
				
			}
		});
	}

	

	public String getFamVal() {
		return fam.getText().trim();
	}

	public String getNameVal() {
		return name.getText().trim();
	}

	public String getFatherVal() {
		return father.getText().trim();
	}



	public String getTabVal() {
		return tab.getText().trim();
	}

	public String getbirthVal() {
		return birth.getText().trim();
	}

	public String getPathVal() {
		return path.getText().trim();
	}

	class PicturePanel extends JPanel {
		
		
		/////////////
		

	

		

	}

}
