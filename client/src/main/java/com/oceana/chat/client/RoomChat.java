package com.oceana.chat.client;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.omg.CORBA.DATA_CONVERSION;

import javax.print.attribute.standard.Severity;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class RoomChat {

	private JFrame frame;
	static DefaultListModel<String> data = new DefaultListModel<String>();
	DefaultListModel<String> data2 = new DefaultListModel<String>();
	final JList listUsers = new JList();
	

	private JTextField txtNameGourp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					for (String iterable_element : Read.userList) {
						data.addElement(iterable_element);
					}

					RoomChat window = new RoomChat();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RoomChat() {
		listUsers.setModel(data);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 509, 297);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 473, 236);
		frame.getContentPane().add(panel);

		JLabel lblGroupName = new JLabel("Group Name");

		txtNameGourp = new JTextField();
		txtNameGourp.setColumns(10);

		JButton btnCreateGroup = new JButton("Create Group");
		
		btnCreateGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashMap<String, DefaultListModel<String>> arrayList = new HashMap<String, DefaultListModel<String>>();
				arrayList.put(txtNameGourp.getText(), data2);
				
				frame.dispose();
				
			}
		});

		JScrollPane scrollPane = new JScrollPane();

		JPanel panel_1 = new JPanel();

		JPanel panel_2 = new JPanel();

		JLabel label = new JLabel(">>>>");

		JLabel label_1 = new JLabel("<<<<");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addContainerGap()
								.addComponent(lblGroupName, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(
										txtNameGourp, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
								.addGap(39).addComponent(btnCreateGroup))
						.addGroup(gl_panel.createSequentialGroup().addGap(31)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
								.addGap(37)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(label)
										.addComponent(label_1))
								.addGap(39)
								.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(33, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblGroupName, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtNameGourp, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnCreateGroup))
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(35)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup().addGap(84).addComponent(label).addGap(18)
								.addComponent(label_1)))
				.addContainerGap()));

		final JList listNewRoom = new JList();
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addComponent(listNewRoom,
				GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addComponent(listNewRoom,
				GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE));
		panel_2.setLayout(gl_panel_2);
		panel_1.setLayout(new BorderLayout(0, 0));

		panel_1.add(listUsers);
		panel.setLayout(gl_panel);

		listUsers.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent e) {
				if (listUsers.getSelectedIndex() >= 0) {
					data2.addElement((listUsers.getSelectedValue().toString()));
					data.removeElement(listUsers.getSelectedValue());
					listNewRoom.setModel(data2);
					listUsers.setSelectedIndex(0);
				}

			}
		});
		listNewRoom.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent e) {
				if (listNewRoom.getSelectedIndex() >= 0) {
					data.addElement((listNewRoom.getSelectedValue().toString())); // change the MODEL
					data2.removeElement(listNewRoom.getSelectedValue());
					listNewRoom.setSelectedIndex(0);
				}
			}
		});
		
		
	}
}
