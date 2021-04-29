package vgc_Mubasher_Zeb_Khan_21694;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class StudentAttendance extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> cbCourseName;
	private JTable table;

    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentAttendance frame = new StudentAttendance();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StudentAttendance() {
		// Setting icon for window
		ImageIcon logo = new ImageIcon(Objects
				.requireNonNull(getClass().getClassLoader().getResource("virtual_global_campus_logo_small.png")));
		setIconImage(logo.getImage());
		setTitle("Student Attendance");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 664, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
        
        table = new JTable() {

            private static final long serialVersionUID = 1L;

            //Setting the data types of columns after loading data in it.
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 61, 638, 199);
        getContentPane().add(scrollPane);
        
        cbCourseName = new JComboBox<String>();
        cbCourseName.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		if (cbCourseName.getItemCount() > 0 && cbCourseName.getSelectedItem() != null && isVisible()==true) {
        		loadStudentFeeRecords("Combobox", cbCourseName.getSelectedItem().toString());
        		}
        	}
        });
        cbCourseName.setSelectedIndex(-1);
        cbCourseName.setBounds(10, 29, 158, 22);
        contentPane.add(cbCourseName);
        
        JLabel lblNewLabel = new JLabel("Course Name");
        lblNewLabel.setBounds(10, 11, 85, 14);
        contentPane.add(lblNewLabel);
        
        JButton btnSaveAttendance = new JButton("Save Attendance");
        btnSaveAttendance.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
				TableModel model = table.getModel();
				String query="";
				for(int i=0;i<table.getRowCount();i++)
				{					
					try {
						// Start saving records in attendance table
						if(model.getValueAt(i, 2).toString().equals("true"))
							query="insert into attendance(studentID,courseName,attendanceStatus,createdBy,isActive)values('"+model.getValueAt(i, 1).toString()+"','"+cbCourseName.getSelectedItem().toString()+"','1','System','1')";
						else
							query="insert into attendance(studentID,courseName,attendanceStatus,createdBy,isActive)values('"+model.getValueAt(i, 1).toString()+"','"+cbCourseName.getSelectedItem().toString()+"','0','System','1')";
						
						AppConfig.pst = AppConfig.con.prepareStatement(query);
						AppConfig.pst.executeUpdate();
						// End saving records in attendance table
					}catch (SQLException exe) {
						exe.printStackTrace();
						break;
					}
					
				}
				JOptionPane.showMessageDialog(null, "Attendance is added successfully!");
				clearAllControls();
        	}
        });
        btnSaveAttendance.setBounds(496, 29, 140, 23);
        contentPane.add(btnSaveAttendance);
        
		// Start My function calls at the load time
		AppConfig.Connect();
		addCoursesInComboBox();
		// End My function calls at the load time
	}

	public void addCoursesInComboBox() {
		try {

			String query = "select * from room where isActive=1";
			AppConfig.pst = AppConfig.con.prepareStatement(query);
			AppConfig.rs = AppConfig.pst.executeQuery();

			while (AppConfig.rs.next()) {

				cbCourseName.addItem(AppConfig.rs.getString("name"));
			}

			cbCourseName.setSelectedIndex(-1);
		} catch (SQLException exe) {
			exe.printStackTrace();
		}
	}
	private void clearAllControls() {

		//Deleting all the rows from table
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		cbCourseName.setSelectedIndex(-1);
	}
	
	public void loadStudentFeeRecords(String loadType, String searchKey) {
		try {
			String query = null;
			if (loadType.toLowerCase().equals("search") == true)
				query = "SELECT users.name as 'Student Name',fee.studentID as 'Student ID' FROM fee INNER JOIN courses ON fee.enrollCourseID = courses.ID INNER JOIN student ON student.studentID = courses.studentID INNER JOIN users ON users.ID = student.ID where users.name like '"
						+ searchKey + "%' and courses.isActive=1 order by users.name asc;";
			else if (loadType.toLowerCase().equals("list") == true)
				query = "SELECT users.name as 'Student Name',fee.studentID as 'Student ID' FROM fee INNER JOIN courses ON fee.enrollCourseID = courses.ID INNER JOIN student ON student.studentID = courses.studentID INNER JOIN users ON users.ID = student.ID where courses.isActive=1 order by users.name asc;";
			else if (loadType.toLowerCase().equals("combobox") == true)
				query = "SELECT users.name as 'Student Name',fee.studentID as 'Student ID' FROM fee INNER JOIN courses ON fee.enrollCourseID = courses.ID INNER JOIN student ON student.studentID = courses.studentID INNER JOIN users ON users.ID = student.ID where courses.isActive=1 && courses.name='"+searchKey+"' order by users.name asc;";
					
			AppConfig.pst = AppConfig.con.prepareStatement(query);
			
	        Object[] columnNames = {"Student Name", "Student ID", "Attendance"};
	        Object[][] data = null;
	        
	        DefaultTableModel model = new DefaultTableModel(data, columnNames);
	        
	        AppConfig.rs = AppConfig.pst.executeQuery();
            while (AppConfig.rs.next()) {

                model.addRow(new Object[]{AppConfig.rs.getString("Student Name"), AppConfig.rs.getString("Student ID"), true});
            }
            
			table.setModel(model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
