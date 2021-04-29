package vgc_Mubasher_Zeb_Khan_21694;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class StudentFee extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField tbSearchRecord;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentFee frame = new StudentFee();
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
	public StudentFee() {

		// Setting icon for window
		ImageIcon logo = new ImageIcon(Objects
				.requireNonNull(getClass().getClassLoader().getResource("virtual_global_campus_logo_small.png")));
		setIconImage(logo.getImage());
		setTitle("Student Fee");
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 754, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 728, 286);
		contentPane.add(scrollPane);

		table = new JTable(){
			private static final long serialVersionUID = 1L;

			// This will make a non editable JTabel.
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				// getting the selected row
				int rowIndex = table.getSelectedRow();
				TableModel model = table.getModel();

				String recordID = model.getValueAt(rowIndex, 0).toString();
				
				if(JavaWindowsFormUserInformers.showMsgWithConfirmJPane("Do you want to clear this fee?")==0)
				{
					//updating the fee status
					try {

						AppConfig.pst = AppConfig.con
								.prepareStatement("update fee set paymentStatus=?, updatedBy=? where id =?");
						AppConfig.pst.setBoolean(1, true);
						AppConfig.pst.setString(2, "System");
						AppConfig.pst.setString(3, recordID);
						AppConfig.pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Record is updated successfully!");
						loadStudentFeeRecords("List", "");
					} catch (SQLException exe) {
						exe.printStackTrace();
					}
				}
			}
		});
		scrollPane.setViewportView(table);
		
		tbSearchRecord = new JTextField();
		tbSearchRecord.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				loadStudentFeeRecords("Search", tbSearchRecord.getText());
			}
		});
		tbSearchRecord.setColumns(10);
		tbSearchRecord.setBounds(10, 41, 728, 20);
		contentPane.add(tbSearchRecord);
		
		JLabel lblNewLabel = new JLabel("Students Fee Status");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel.setBounds(292, 11, 200, 19);
		contentPane.add(lblNewLabel);

		// Start My function calls at the load time
		AppConfig.Connect();
		loadStudentFeeRecords("list", "");
		// End My function calls at the load time
	}

	public void loadStudentFeeRecords(String loadType, String searchKey) {
		try {
			String query = null;
			if (loadType.toLowerCase().equals("search") == true)
				query = "SELECT fee.ID,users.name as 'Student Name',courses.name as 'Course Name',fee.feeAmount as 'Fee($)',fee.studentID as 'Student ID' ,'Unclear' as 'Status' FROM fee INNER JOIN courses ON fee.enrollCourseID = courses.ID INNER JOIN student ON student.studentID = courses.studentID INNER JOIN users ON users.ID = student.ID where users.name like '"
						+ searchKey + "%' and fee.isActive=1 && fee.paymentStatus=0 order by users.name asc;";
			else if (loadType.toLowerCase().equals("list") == true)
				query = "SELECT fee.ID,users.name as 'Student Name',courses.name as 'Course Name',fee.feeAmount as 'Fee($)',fee.studentID as 'Student ID' ,'Unclear' as 'Status' FROM fee INNER JOIN courses ON fee.enrollCourseID = courses.ID INNER JOIN student ON student.studentID = courses.studentID INNER JOIN users ON users.ID = student.ID where fee.isActive=1 && fee.paymentStatus=0 order by users.name asc;";
				
			AppConfig.pst = AppConfig.con.prepareStatement(query);
			AppConfig.rs = AppConfig.pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(AppConfig.rs));
			JavaWindowsFormJControlsHandling.show_hide_column(0, "hide", table);// Hiding the ID column
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
