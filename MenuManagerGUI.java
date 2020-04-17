package rfk20MenuManager;

/**
 * Class MenuManagerGUI
 * author : Rye Keating
 * created: 11/20/2019
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MenuManagerGUI {

	/* Global variables representing SWING controls for the parent frame */
	private static MenuManager menuMgr;
	private JFrame parentFrame;
	private JLabel lblEntree;
	private JLabel lblSide;
	private JLabel lblApp;
	private JLabel lblDessert;
	private JLabel lblMenus;
	private JTextField txtTextBoxExample;
	private JComboBox cboComboBoxEntree;
	private JComboBox cboComboBoxSide;
	private JComboBox cboComboBoxApp;
	private JComboBox cboComboBoxDessert;
	private JButton btnCreate;
	private JButton btnGenRandom;
	private JButton btnMinimumCal;
	private JButton btnMaximumCal;
	private JButton btnDetails;
	private JButton btnDeleteAll;
	private JButton btnSaveToFile;
	private JList listMenus = null;
	private JFrame childFrameMenuName;
	private JFrame childFrameDetails;
	private JButton btnButtonExample1;
	private static int nWidth = 90;
	private int menuSelected = -1;
	private String menuNameStr;
	private ArrayList<Entree> entreesLst;
	private ArrayList<Side> sidesLst;
	private ArrayList<Salad> appsLst;
	private ArrayList<Dessert> dessertsLst;

	private enum menuType {
		CREATED, RANDOM, MINIMUM, MAXIMUM
	}
	menuType mType;

	public static void main(String[] args) {
		menuMgr = new MenuManager("data/dishes.txt");

		MenuManagerGUI window = new MenuManagerGUI();
		window.parentFrame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public MenuManagerGUI() {
		initParentFrame();
		initChildMenuName();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initParentFrame() {
		parentFrame = new JFrame("Menu Manager");
		parentFrame.setBounds(100, 100, 700, 500);
		parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parentFrame.getContentPane().setLayout(null);

		lblEntree = new JLabel("Entree:");
		lblEntree.setBounds(20, 20, nWidth, 20);
		parentFrame.getContentPane().add(lblEntree);
		entreesLst = menuMgr.getEntreeItems();
		cboComboBoxEntree = new JComboBox();
		cboComboBoxEntree.setBounds(lblEntree.getX() + lblEntree.getWidth() + 10, lblEntree.getY(), nWidth, 30);
		int index = 0;
		for (index = 0; index < entreesLst.size(); index++) {
			cboComboBoxEntree.addItem(entreesLst.get(index).getName());
		}
		parentFrame.getContentPane().add(cboComboBoxEntree);

		lblSide = new JLabel("Side:");
		lblSide.setBounds(lblEntree.getX(), lblEntree.getY() + 40, nWidth, 20);
		parentFrame.getContentPane().add(lblSide);
		sidesLst = menuMgr.getSideItems();
		cboComboBoxSide = new JComboBox();
		cboComboBoxSide.setBounds(lblSide.getX() + lblSide.getWidth() + 10, lblSide.getY(), nWidth, 30);
		for (index = 0; index < sidesLst.size(); index++) {
			cboComboBoxSide.addItem(sidesLst.get(index).getName());
		}
		parentFrame.getContentPane().add(cboComboBoxSide);

		lblApp = new JLabel("Salad:");
		lblApp.setBounds(lblSide.getX(), lblSide.getY() + 40, nWidth, 20);
		parentFrame.getContentPane().add(lblApp);
		appsLst = menuMgr.getSaladItems();
		cboComboBoxApp = new JComboBox();
		cboComboBoxApp.setBounds(lblApp.getX() + lblApp.getWidth() + 10, lblApp.getY(), nWidth, 30);
		for (index = 0; index < appsLst.size(); index++) {
			cboComboBoxApp.addItem(appsLst.get(index).getName());
		}
		parentFrame.getContentPane().add(cboComboBoxApp);

		lblDessert = new JLabel("Dessert:");
		lblDessert.setBounds(lblApp.getX(), lblApp.getY() + 40, nWidth, 20);
		parentFrame.getContentPane().add(lblDessert);
		dessertsLst = menuMgr.getDessertItems();
		cboComboBoxDessert = new JComboBox();
		cboComboBoxDessert.setBounds(lblDessert.getX() + lblDessert.getWidth() + 10, lblDessert.getY(), nWidth, 30);
		for (index = 0; index < dessertsLst.size(); index++) {
			cboComboBoxDessert.addItem(dessertsLst.get(index).getName());
		}
		parentFrame.getContentPane().add(cboComboBoxDessert);

		cboComboBoxEntree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		btnCreate = new JButton("Create Menu with these dishes");
		btnCreate.setBounds(lblDessert.getX(), lblDessert.getY() + 50, (nWidth * 2) + 65, 30);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				childFrameMenuName.setVisible(true);
				mType = menuType.CREATED;
			}
		});
		parentFrame.getContentPane().add(btnCreate);

		btnGenRandom = new JButton("Generate a Random Menu");
		btnGenRandom.setBounds(btnCreate.getX(), btnCreate.getY() + 100, (nWidth * 2) + 65, 30);
		btnGenRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				childFrameMenuName.setVisible(true);
				mType = menuType.RANDOM;
			}
		});
		parentFrame.getContentPane().add(btnGenRandom);

		btnMinimumCal = new JButton("Generate a Minimum Calories Menu");
		btnMinimumCal.setBounds(btnGenRandom.getX(), btnGenRandom.getY() + 50, (nWidth * 2) + 65, 30);
		btnMinimumCal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				childFrameMenuName.setVisible(true);
				mType = menuType.MINIMUM;
			}
		});
		parentFrame.getContentPane().add(btnMinimumCal);

		btnMaximumCal = new JButton("Generate a Maximum Calories Menu");
		btnMaximumCal.setBounds(btnMinimumCal.getX(), btnMinimumCal.getY() + 50, (nWidth * 2) + 65, 30);
		btnMaximumCal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				childFrameMenuName.setVisible(true);
				mType = menuType.MAXIMUM;
			}
		});
		parentFrame.getContentPane().add(btnMaximumCal);

		showMenus();
		
		btnDetails = new JButton("Details");
		btnDetails.setBounds(listMenus.getX(), listMenus.getHeight() + 50, nWidth + 8, 30);
		btnDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSelected = listMenus.getSelectedIndex();
				
				initChildDetails();
				
				childFrameDetails.setVisible(true);
			}
		});
		parentFrame.getContentPane().add(btnDetails);

		btnDeleteAll = new JButton("Delete all");
		btnDeleteAll.setBounds(listMenus.getX() + nWidth + 13, listMenus.getHeight() + 50, nWidth + 8, 30);
		btnDeleteAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuMgr.getMenus().clear();
				showMenus();
			}
		});
		parentFrame.getContentPane().add(btnDeleteAll);
		
		btnSaveToFile = new JButton("Save to file");
		btnSaveToFile.setBounds(listMenus.getX() + (nWidth + 13) * 2, listMenus.getHeight() + 50, nWidth + 8, 30);
		btnSaveToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileManager.writeMenu("data/menus.txt", menuMgr.getMenus());
			}
		});
		parentFrame.getContentPane().add(btnSaveToFile);
	} // initParentFrame

	private void initChildMenuName() {
		JLabel lblMenuName;
		childFrameMenuName = new JFrame("Menu Name");
		childFrameMenuName.setBounds(100, 100, 400, 200);
		childFrameMenuName.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		childFrameMenuName.getContentPane().setLayout(null);
		childFrameMenuName.setVisible(false);

		lblMenuName = new JLabel("Menu Name:");
		lblMenuName.setBounds(20, 20, nWidth, 20);
		childFrameMenuName.getContentPane().add(lblMenuName);

		JTextArea menuNameText = new JTextArea(1, nWidth);
		menuNameText.setBounds(nWidth + 20, 20, nWidth * 2, 20);
		childFrameMenuName.getContentPane().add(menuNameText);

		menuNameStr = "";
		JButton btnOk = new JButton("Ok");
		btnOk.setBounds((childFrameMenuName.getWidth() / 2) - (nWidth / 2), childFrameMenuName.getHeight() - 100,
				nWidth, 30);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = 0;
				int mIndex = 0;
				childFrameMenuName.setVisible(false);
				menuNameStr = menuNameText.getText();
				// Menu name entered?
				if (menuNameStr.equals("")) { // no - nothing to create
					return;
				}

				Menu menuCreated = new Menu(menuNameStr);

				if (mType == menuType.CREATED) {
					// Set Entree name to user's selection.
					for (index = 0; index < entreesLst.size(); index++) {
						if (entreesLst.get(index).getName().equals(cboComboBoxEntree.getSelectedItem())) {
							menuCreated.setEntree(entreesLst.get(index));
						}
					}
					// Set Side name to user's selection.
					for (index = 0; index < sidesLst.size(); index++) {
						if (sidesLst.get(index).getName().equals(cboComboBoxSide.getSelectedItem())) {
							menuCreated.setSide(sidesLst.get(index));
						}
					}
					// Set Salad name to user's selection.
					for (index = 0; index < appsLst.size(); index++) {
						if (appsLst.get(index).getName().equals(cboComboBoxApp.getSelectedItem())) {
							menuCreated.setSalad(appsLst.get(index));
						}
					}
					// Set Dessert name to user's selection.
					for (index = 0; index < dessertsLst.size(); index++) {
						if (dessertsLst.get(index).getName().equals(cboComboBoxDessert.getSelectedItem())) {
							menuCreated.setDessert(dessertsLst.get(index));
						}
					}
				} else if (mType == menuType.RANDOM) {
					Random r = new Random();

					// Select random salad.
					int randomInt = r.nextInt(appsLst.size());
					menuCreated.setSalad(appsLst.get(randomInt));

					// Select random entree.
					randomInt = r.nextInt(entreesLst.size());
					menuCreated.setEntree(entreesLst.get(randomInt));

					// Select random side.
					randomInt = r.nextInt(sidesLst.size());
					menuCreated.setSide(sidesLst.get(randomInt));

					// Select random dessert.
					randomInt = r.nextInt(dessertsLst.size());
					menuCreated.setDessert(dessertsLst.get(randomInt));
				} else if (mType == menuType.MINIMUM) {
					for (index = 0; index < appsLst.size(); index++) {
						if (appsLst.get(index).getCalories() < appsLst.get(mIndex).getCalories()) {
							mIndex = index;
						}
					}
					menuCreated.setSalad(appsLst.get(mIndex));

					for (index = 0; index < entreesLst.size(); index++) {
						if (entreesLst.get(index).getCalories() < entreesLst.get(mIndex).getCalories()) {
							mIndex = index;
						}
					}
					menuCreated.setEntree(entreesLst.get(mIndex));

					for (index = 0; index < sidesLst.size(); index++) {
						if (sidesLst.get(index).getCalories() < sidesLst.get(mIndex).getCalories()) {
							mIndex = index;
						}
					}
					menuCreated.setSide(sidesLst.get(mIndex));

					for (index = 0; index < dessertsLst.size(); index++) {
						if (dessertsLst.get(index).getCalories() < dessertsLst.get(mIndex).getCalories()) {
							mIndex = index;
						}
					}
					menuCreated.setDessert(dessertsLst.get(mIndex));
				} else if (mType == menuType.MAXIMUM) {
					for (index = 0; index < appsLst.size(); index++) {
						if (appsLst.get(index).getCalories() > appsLst.get(mIndex).getCalories()) {
							mIndex = index;
						}
					}
					menuCreated.setSalad(appsLst.get(mIndex));

					for (index = 0; index < entreesLst.size(); index++) {
						if (entreesLst.get(index).getCalories() > entreesLst.get(mIndex).getCalories()) {
							mIndex = index;
						}
					}
					menuCreated.setEntree(entreesLst.get(mIndex));

					for (index = 0; index < sidesLst.size(); index++) {
						if (sidesLst.get(index).getCalories() > sidesLst.get(mIndex).getCalories()) {
							mIndex = index;
						}
					}
					menuCreated.setSide(sidesLst.get(mIndex));

					for (index = 0; index < dessertsLst.size(); index++) {
						if (dessertsLst.get(index).getCalories() > dessertsLst.get(mIndex).getCalories()) {
							mIndex = index;
						}
					}
					menuCreated.setDessert(dessertsLst.get(mIndex));
				}

				// Add created menu to list.
				menuMgr.getMenus().add(menuCreated);
				showMenus();
			}
		});
		childFrameMenuName.getContentPane().add(btnOk);
	} // initChildMenuName

	private void showMenus() {
		lblMenus = new JLabel("Created Menus:");
		lblMenus.setBounds((nWidth * 2) + 130, lblEntree.getY() - 5, lblEntree.getWidth(), lblEntree.getHeight());
		parentFrame.getContentPane().add(lblMenus);

		if (listMenus != null) {
			parentFrame.getContentPane().remove(listMenus);
		}

		DefaultListModel listModel = new DefaultListModel();

		for (int index = 0; index < menuMgr.getMenus().size(); index++) {
			listModel.addElement(menuMgr.getMenus().get(index).getName());
		}
		listMenus = new JList(listModel);
		listMenus.setModel(listModel); 
		listMenus.setBounds(lblMenus.getX(), lblMenus.getY() + 25, (parentFrame.getWidth() / 2) - 50,
				parentFrame.getHeight() - 190);
		parentFrame.getContentPane().add(listMenus);
		parentFrame.setVisible(true);
		listMenus.repaint();
	} // showMenus

	private void initChildDetails() {
		String menuNameStr;
		String strPrice;
		int sumCalories = 0;
		double sumPrice = 0;
		// Menu selected?
		if (menuSelected < 0) {
			menuNameStr = " ";
		}
		else {
			menuNameStr = menuMgr.getMenus().get(menuSelected).getName();
		}
		childFrameDetails = new JFrame("Menu: " + menuNameStr);
		childFrameDetails.setBounds(100, 100, 700, 500);
		childFrameDetails.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		childFrameDetails.getContentPane().setLayout(null);
		childFrameDetails.setVisible(false);

		lblEntree = new JLabel("Entree");
		lblEntree.setBounds(20, 20, nWidth, 20);
		childFrameDetails.getContentPane().add(lblEntree);

		JTextArea txtEntree = new JTextArea(2, (nWidth * 2) - 10);
		txtEntree.setBounds(lblEntree.getX() + lblEntree.getWidth() + 10, lblEntree.getY(),
				(int) (childFrameDetails.getWidth() * 0.7), 60);
		txtEntree.setLineWrap(true);		// enable line wrapping
		txtEntree.setWrapStyleWord(true);	// wrap lines at word boundaries rather than at character boundaries
		txtEntree.setEditable(false);		// make field read-only

		if (menuSelected >= 0) {
			strPrice = String.format("%.2f", menuMgr.getMenus().get(menuSelected).getEntree().getPrice());
			txtEntree.setText(menuMgr.getMenus().get(menuSelected).getEntree().getName() + "\n"
					+ menuMgr.getMenus().get(menuSelected).getEntree().getDescription() + ". Calories: "
					+ menuMgr.getMenus().get(menuSelected).getEntree().getCalories() + ". Price: $"
					+ strPrice);
			sumCalories = menuMgr.getMenus().get(menuSelected).getEntree().getCalories();
			sumPrice = menuMgr.getMenus().get(menuSelected).getEntree().getPrice();
		}
		childFrameDetails.getContentPane().add(txtEntree);
		
		childFrameDetails.repaint();
		
		lblSide = new JLabel("Side");
		lblSide.setBounds(lblEntree.getX(), lblEntree.getY() + txtEntree.getHeight() + 10, nWidth, 20);
		childFrameDetails.getContentPane().add(lblSide);

		JTextArea txtSide = new JTextArea(2, (nWidth * 2) - 10);
		txtSide.setBounds(lblSide.getX() + lblSide.getWidth() + 10, lblSide.getY(),
				(int) (childFrameDetails.getWidth() * 0.7), 60);
		txtSide.setLineWrap(true);		// enable line wrapping
		txtSide.setWrapStyleWord(true);	// wrap lines at word boundaries rather than at character boundaries
		txtSide.setEditable(false);		// make field read-only

		if (menuSelected >= 0) {
			strPrice = String.format("%.2f", menuMgr.getMenus().get(menuSelected).getSide().getPrice());
			txtSide.setText(menuMgr.getMenus().get(menuSelected).getSide().getName() + "\n"
					+ menuMgr.getMenus().get(menuSelected).getSide().getDescription() + ". Calories: "
					+ menuMgr.getMenus().get(menuSelected).getSide().getCalories() + ". Price: $"
					+ strPrice);
			sumCalories +=  menuMgr.getMenus().get(menuSelected).getSide().getCalories();
			sumPrice += menuMgr.getMenus().get(menuSelected).getSide().getPrice();
		}
		childFrameDetails.getContentPane().add(txtSide);

		lblApp = new JLabel("Salad");
		lblApp.setBounds(lblSide.getX(), lblSide.getY() + txtSide.getHeight() + 10, nWidth, 20);
		childFrameDetails.getContentPane().add(lblApp);

		JTextArea txtApp = new JTextArea(2, (nWidth * 2) - 10);
		txtApp.setBounds(lblApp.getX() + lblApp.getWidth() + 10, lblApp.getY(),
				(int) (childFrameDetails.getWidth() * 0.7), 60);
		txtApp.setLineWrap(true);		// enable line wrapping
		txtApp.setWrapStyleWord(true);	// wrap lines at word boundaries rather than at character boundaries
		txtApp.setEditable(false);		// make field read-only

		if (menuSelected >= 0) {
			strPrice = String.format("%.2f", menuMgr.getMenus().get(menuSelected).getSalad().getPrice());
			txtApp.setText(menuMgr.getMenus().get(menuSelected).getSalad().getName() + "\n"
					+ menuMgr.getMenus().get(menuSelected).getSalad().getDescription() + ". Calories: "
					+ menuMgr.getMenus().get(menuSelected).getSalad().getCalories() + ". Price: $"
					+ strPrice);
			sumCalories += menuMgr.getMenus().get(menuSelected).getSalad().getCalories();
			sumPrice += menuMgr.getMenus().get(menuSelected).getSalad().getPrice();
		}
		childFrameDetails.getContentPane().add(txtApp);

		lblDessert = new JLabel("Dessert");
		lblDessert.setBounds(lblApp.getX(), lblApp.getY() + txtApp.getHeight() + 10, nWidth, 20);
		childFrameDetails.getContentPane().add(lblDessert);

		JTextArea txtDessert = new JTextArea(2, (nWidth * 2) - 10);
		txtDessert.setBounds(lblDessert.getX() + lblDessert.getWidth() + 10, lblDessert.getY(),
				(int) (childFrameDetails.getWidth() * 0.7), 60);
		txtDessert.setLineWrap(true);		// enable line wrapping
		txtDessert.setWrapStyleWord(true);	// wrap lines at word boundaries rather than at character boundaries
		txtDessert.setEditable(false);		// make field read-only

		if (menuSelected >= 0) {
			strPrice = String.format("%.2f", menuMgr.getMenus().get(menuSelected).getDessert().getPrice());
			txtDessert.setText(menuMgr.getMenus().get(menuSelected).getDessert().getName() + "\n"
					+ menuMgr.getMenus().get(menuSelected).getDessert().getDescription() + ". Calories: "
					+ menuMgr.getMenus().get(menuSelected).getDessert().getCalories() + ". Price: $"
					+ strPrice);
			sumCalories += menuMgr.getMenus().get(menuSelected).getDessert().getCalories();
			sumPrice += menuMgr.getMenus().get(menuSelected).getDessert().getPrice();
		}
		childFrameDetails.getContentPane().add(txtDessert);

		JLabel lbltCal = new JLabel("Total calories:");
		lbltCal.setBounds(lblDessert.getX(), lblDessert.getY() + txtDessert.getHeight() + 20, nWidth, 20);
		childFrameDetails.getContentPane().add(lbltCal);
		JTextArea txtCal = new JTextArea(1, nWidth / 4);
		txtCal.setBounds(lbltCal.getX() + lbltCal.getWidth() + 10, lbltCal.getY(), nWidth / 2, 20);
		txtCal.setText(Integer.toString(sumCalories));
		txtCal.setEditable(false);		// make field read-only
		childFrameDetails.getContentPane().add(txtCal);
		
		JLabel lbltPrice = new JLabel("Total price: $");
		lbltPrice.setBounds(lbltCal.getX(), lbltCal.getY() + txtCal.getHeight() + 20, nWidth, 20);
		childFrameDetails.getContentPane().add(lbltPrice);
		JTextArea txtPrice = new JTextArea(1, nWidth / 4);
		txtPrice.setBounds(lbltCal.getX() + lbltCal.getWidth() + 10, lbltPrice.getY(), nWidth / 2, 20);
		txtPrice.setText(String.format("%.2f", sumPrice));
		txtPrice.setEditable(false);		// make field read-only
		childFrameDetails.getContentPane().add(txtPrice);
	} // initChildDetails
}
