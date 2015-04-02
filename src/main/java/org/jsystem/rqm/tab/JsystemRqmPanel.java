package org.jsystem.rqm.tab;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import jsystem.framework.FrameworkOptions;
import jsystem.framework.JSystemProperties;
import jsystem.treeui.images.ImageCenter;
import jsystem.treeui.teststable.TestsTableController;

import org.apache.commons.io.FilenameUtils;
import javax.swing.JButton;

public class JsystemRqmPanel extends JPanel implements
		jsystem.treeui.interfaces.JSystemTab {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tcName;

	public static void main(String[] args) {
		JFrame frame = new JFrame("JFrame Source Demo");
		// Add a window listner for close button
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(new JsystemRqmPanel().init());
		// This is an empty content area in the frame

		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Create the panel.
	 */
	public JsystemRqmPanel() {

		 TreeModel model = new FileTreeModel(new File(JSystemProperties
		 .getInstance().getPreference(
		 FrameworkOptions.RESOURCES_SOURCE_FOLDER)
		 + "/scenarios"));

//		TreeModel model = new FileTreeModel(new File("c:\\test"));

		setLayout(new BorderLayout(0, 0));

		final JTree jTree = initTree(model);
		JScrollPane jScrollPane = new JScrollPane(jTree);
		JPanel panel = initRightPane();
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				jScrollPane, panel);
		jSplitPane.setResizeWeight(0.3);
		jSplitPane.setDividerLocation(0.5);

		add(jSplitPane);

	}

	private JPanel initRightPane() {
		JPanel panel = new JPanel();
	
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblTestCaseName = new JLabel("Test Case Name");
		GridBagConstraints gbc_lblTestCaseName = new GridBagConstraints();
		gbc_lblTestCaseName.insets = new Insets(0, 0, 5, 5);
		gbc_lblTestCaseName.gridx = 1;
		gbc_lblTestCaseName.gridy = 1;
		panel.add(lblTestCaseName, gbc_lblTestCaseName);

		tcName = new JTextField();
		GridBagConstraints gbc_tcName = new GridBagConstraints();
		gbc_tcName.insets = new Insets(0, 0, 5, 0);
		gbc_tcName.gridwidth = 2;
		gbc_tcName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tcName.gridx = 3;
		gbc_tcName.gridy = 1;
		panel.add(tcName, gbc_tcName);
		tcName.setColumns(10);
		
		JButton btnUpload = new JButton("Upload");
		GridBagConstraints gbc_btnUpload = new GridBagConstraints();
		gbc_btnUpload.anchor = GridBagConstraints.EAST;
		gbc_btnUpload.gridx = 4;
		gbc_btnUpload.gridy = 9;
		panel.add(btnUpload, gbc_btnUpload);
		return panel;
	}

	private JTree initTree(TreeModel model) {
		final JTree jTree = new JTree(model) {
			public boolean isPathEditable(TreePath path) {
				Object comp = path.getLastPathComponent();
				if (comp instanceof DefaultMutableTreeNode) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) comp;
					Object userObject = node.getUserObject();
					if (userObject instanceof TrueFalseTreeNodeData) {
						return true;
					}
				}
				return false;
			}
		};
		MouseListener ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selRow = jTree.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = jTree.getPathForLocation(e.getX(), e.getY());
				if (selRow != -1) {
					if (e.getClickCount() == 1) {
						// mySingleClick(selRow, selPath);
					} else if (e.getClickCount() == 2) {
						Object selectedNode = ((DefaultMutableTreeNode) selPath
								.getLastPathComponent()).getUserObject();
						if (!((File) selectedNode).isDirectory()) {
							tcName.setText(FilenameUtils
									.removeExtension(((File) selectedNode)
											.getName()));
						}

					}
				}
			}
		};

		jTree.addMouseListener(ml);
		jTree.setCellRenderer(new DefaultTreeCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTreeCellRendererComponent(JTree tree,
					Object value, boolean sel, boolean expanded, boolean leaf,
					int row, boolean hasFocus) {
				// TODO Auto-generated method stub
				super.getTreeCellRendererComponent(tree, value, selected,
						false, false, 0, hasFocus);

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				Object userObject = node.getUserObject();

				if (!((File) userObject).isDirectory()) {
					setIcon(ImageCenter.getInstance().getImage(
							ImageCenter.ICON_SCENARIO));
				} else {
					setIcon(ImageCenter.getInstance().getImage(
							ImageCenter.ICON_PATH));

				}
				setText(((File) userObject).getName());

				return this;
			}
		});
		return jTree;
	}

	public JPanel init() {
		return new JsystemRqmPanel();

	}

	public String getTabName() {
		return "TAB";
	}

	public void setTestsTableController(
			TestsTableController testsTableController) {
		// TODO Auto-generated method stub

	}
}
