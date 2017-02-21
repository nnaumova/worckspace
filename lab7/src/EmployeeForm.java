

import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;







public class EmployeeForm extends JPanel{

	private static final long serialVersionUID = 1L;
	
	JTextField name, surname, patronymic, photoPath;
	PicturePanel picture;
	private JFormattedTextField id;
	private JFormattedTextField birth;
	private JButton sButton;
	public  EmployeeForm(String nam, String sur, String patron,String mid,String bd,
			  String path) {
		sButton = new JButton("Save");
		Container pane;
		setLayout(new GridLayout(7,2));
		
		name = new JTextField(nam);
		surname = new JTextField(sur);
		
		patronymic = new JTextField(patron);
		id = new JFormattedTextField(mid);
		birth = new JFormattedTextField(bd);
		photoPath = new JTextField(path);
		
		
		add(new JLabel("Имя"));
		add(name, "pushx,growx,wrap");
		add(new JLabel("Фамилия"));
		add(surname, "pushx,growx,wrap");
		
		add(new JLabel("Отчество"));
		add(patronymic, "pushx,growx,wrap");
		add(new JLabel("Дата рождения"));
		add(birth, "pushx,growx,wrap");
		add(new JLabel("Табельный номер"));
		add(id, "pushx,growx,wrap");
		add(new JLabel("Путь к фотографии"));
		add(photoPath, "pushx,growx,wrap");
		add(sButton, "span 2, w 150!,al center,wrap");
		add(picture, "span 2,al center,push,grow");
		
		try {
			id.setFormatterFactory(new DefaultFormatterFactory(
					new MaskFormatter("###")));
		} catch (ParseException e) {
		
			e.printStackTrace();
		}
		
		try {
			birth.setFormatterFactory(new DefaultFormatterFactory(
					new MaskFormatter("##.##.####")));
		} catch (ParseException e) {
		
			e.printStackTrace();
		}
		
		
		final EmployeeForm form = this;
		
		sButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (form.surname.getText().isEmpty()) {
					JOptionPane.showMessageDialog(form, "Fam cannot be empty");
					return;
				}
				if (form.name.getText().isEmpty()) {
					JOptionPane.showMessageDialog(form, "Name cannot be empty");
					return;
				}
				if (form.patronymic.getText().isEmpty()) {
					JOptionPane.showMessageDialog(form,
							"Father name cannot be empty");
					return;
				}
			
				
				if (form.id.getText().isEmpty()) {
					JOptionPane.showMessageDialog(form,
							"Tab number cannot be empty");
					return;
				}
				if (form.birth.getText().isEmpty()) {
					JOptionPane.showMessageDialog(form,
							"Address cannot be empty");
					return;
				}
				if (!Character.isLetter(form.surname.getText().charAt(0))) {
					form.surname.setText("");
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
				if (!Character.isLetter(form.patronymic.getText().charAt(0))) {
					form.patronymic.setText("");
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
									(Employee) selectedNode
											.getUserObject());
				}

				if (newPath != null) {
					MainForm.getInstance().getModel().fireDataChange();
					MainForm.getInstance().expandPath(newPath.getParentPath());
					MainForm.getInstance().selectPath(newPath);
				}
				picture.init();
			}
		});
	}
	public String getFamVal() {
		return surname.getText().trim();
	}

	public String getNameVal() {
		return name.getText().trim();
	}

	public String getFatherVal() {
		return patronymic.getText().trim();
	}



	public String getTabVal() {
		return id.getText().trim();
	}

	public String getBirthVal() {
		return birth.getText().trim();
	}

	public String getPathVal() {
		return photoPath.getText().trim();
	}

	class PicturePanel extends JPanel {
		/**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		Image img;

		public PicturePanel() {
			init();
			this.addComponentListener(new ComponentListener() {

				@Override
				public void componentShown(ComponentEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void componentResized(ComponentEvent e) {
					init();

				}

				@Override
				public void componentMoved(ComponentEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void componentHidden(ComponentEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}

		public void init() {

			Toolkit kit = Toolkit.getDefaultToolkit();
			if (!getPathVal().isEmpty())
				img = kit.getImage(getPathVal());
			else
				img = kit.getImage("default.jpg");
			if (!(getWidth() == 0 || getHeight() == 0))
				img = img.getScaledInstance(getWidth(), getHeight(),
						Image.SCALE_SMOOTH);
			this.repaint();
		}

		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, this);
		}

	}
}
		
		

