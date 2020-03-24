package org.jdesktop.lg3d.apps.jmf23D;

import java.awt.event.*;

import javax.swing.*;

import org.jdesktop.lg3d.wg.*;


class InputPane extends SwingNode {

    JTextField text;
    JComboBox combo;
    JFileChooser jfc;
    Frame3D frame;
    boolean done = false;

    InputPane() {

        JPanel input = new JPanel();
        Object[] protocols = new Object[] {
            "file:", "alg3d:", "http://", "v4l:", "vfw:", "(specify)"
        };
        combo = new JComboBox(protocols);
        combo.setEditable(true);
        text = new JTextField(20);

        JButton browse = new JButton("browse");
        input.add(combo);
        input.add(text);
        input.add(browse);
        text.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                done = true;
            }
        });
        browse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
		    if(jfc==null)
			    createFileChooser();
            }
        });
        setPanel(input);
    }

    private void createFileChooser() {

        SwingNode sn = new SwingNode();
        jfc = new JFileChooser();
	jfc.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae){
			if(jfc.getSelectedFile()!=null)
				text.setText(jfc.getSelectedFile().getPath());
				done = true;
				frame.changeVisible(false);
				frame.changeEnabled(false);
		}
	});
        JPanel pane = new JPanel();
        pane.add(jfc);
        sn.setPanel(pane);

        frame = new Frame3D();
        frame.addChild(sn);
        frame.changeEnabled(true);
        frame.changeVisible(true);
    }

    public String getURI() {

        while (!done)

            try {
                Thread.sleep(20);
            } catch (Exception e) {
            }
	done = false;
        return combo.getSelectedItem().toString() + text.getText();
    }
}
