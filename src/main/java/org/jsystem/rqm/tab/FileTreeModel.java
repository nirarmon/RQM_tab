package org.jsystem.rqm.tab;

import java.io.File;
import java.util.Arrays;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

public class FileTreeModel implements TreeModel {
	
	 private File root;

	    public FileTreeModel(File root) {
	        this.root = root;
	    }
	    
	    public int getIndexOfChild(Object parent, Object child) {
	        File par =  (File) ((DefaultMutableTreeNode) parent).getUserObject();
	        File ch =  (File) ((DefaultMutableTreeNode) child).getUserObject();

	        return Arrays.asList(par.listFiles()).indexOf(ch);
	    }
	    
	    public int getChildCount(Object parent) {
	    	 File f = (File) ((DefaultMutableTreeNode) parent).getUserObject();
	        if (!f.isDirectory()) {
	            return 0;
	        } else {
	            return f.list().length;
	        }
	    }

	    
	    public Object getRoot() {
	    	return new DefaultMutableTreeNode(root);
	        //return root;
	    }

	    
	    public boolean isLeaf(Object node) {
	    	
	    	 File f = (File) ((DefaultMutableTreeNode) node).getUserObject();
	        return !f.isDirectory();
	    }

	    public Object getChild(Object parent, int index) {
	    	 File f = (File) ((DefaultMutableTreeNode) parent).getUserObject();

	        return new DefaultMutableTreeNode( f.listFiles()[index]);
	        //return f.listFiles()[index];
	    }
		   
	    public void removeTreeModelListener(javax.swing.event.TreeModelListener l) {
	        //do nothing
	    }

	    
	    public void valueForPathChanged(javax.swing.tree.TreePath path, Object newValue) {
	        //do nothing
	    }

		public void addTreeModelListener(TreeModelListener l) {
			// TODO Auto-generated method stub
			
		}

	
}
