package nguyentrinhan70.example.com.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CDDVDUI extends JFrame {
	
	JTextField txtMa, txtTen, txtLoai, txtNamXuatBan;
	JButton btnLuu, btnTimKiem, btnXoa;
	
	DefaultTableModel dtmCd;
	JTable tblCd;
	
	Connection conn= null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	public CDDVDUI(String title) {
		super(title);
		addControls();
		addEvents();
		ketNoiCoSoDuLieu();
		hienThiToanBoCDDVD();
	}

	private void hienThiToanBoCDDVD() {
		// TODO Auto-generated method stub
		try{
			
			String sql = "Select * from CDDVDCollection";
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			dtmCd.setRowCount(0);
			while(resultSet.next()){
				Vector<Object> vec = new Vector<>();
				vec.add(resultSet.getString("Ma"));
				vec.add(resultSet.getString("TieuDe"));
				vec.add(resultSet.getString("LoaiDia"));
				vec.add(resultSet.getString("NamXuatBan"));
				dtmCd.addRow(vec);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	private void ketNoiCoSoDuLieu() {
		// TODO Auto-generated method stub
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl = 
					//jdbc:sqlserver://DESKTOP-71M73KO\\SQLEXPRESS:1433
					//"jdbc:sqlserver://DESKTOP-J3H92G0\\SQLEXPRESS:1433; databaseName=dbQlCDDVD; integratedSecurity = true;";
					"jdbc:sqlserver://TRINHANNGUYEN\\SQLEXPRESS:1433; databaseName=dbQlCDDVD; integratedSecurity = true;";
			conn = DriverManager.getConnection(connectionUrl);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		btnTimKiem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				TimKiemCDDVDUI ui = new TimKiemCDDVDUI("Tìm kiếm CD và DVD	");
				ui.showWindow();
			}
		});
		btnLuu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				xuLyLuuCDDVD();
			}
		});
		
	}

	protected void xuLyLuuCDDVD() {
		// TODO Auto-generated method stub
		try
		{
			String sql = "insert into CDDVDCollection values(?, ?,?,?)";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, txtMa.getText());
			preparedStatement.setString(2, txtTen.getText());
			preparedStatement.setString(3, txtLoai.getText());
			preparedStatement.setInt(4, Integer.parseInt(txtNamXuatBan.getText()));
			int x = preparedStatement.executeUpdate();
			if (x>0){
				hienThiToanBoCDDVD();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void addControls() {
		// TODO Auto-generated method stub
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		
		JPanel pnThongTin = new JPanel();
		pnThongTin.setLayout(new BoxLayout(pnThongTin, BoxLayout.Y_AXIS));
		con.add(pnThongTin, BorderLayout.NORTH);
		
		JPanel pnMa = new JPanel();
		pnMa.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblMa = new JLabel("Mã");
		txtMa = new JTextField(20);
		pnMa.add(lblMa);
		pnMa.add(txtMa);
		pnThongTin.add(pnMa);
		
		JPanel pnTen = new JPanel();
		pnTen.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTen = new JLabel("Tên");
		txtTen = new JTextField(20);
		pnTen.add(lblTen);
		pnTen.add(txtTen);
		pnThongTin.add(pnTen);
		
		JPanel pnLoai = new JPanel();
		pnLoai.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblLoai = new JLabel("Loại");
		txtLoai = new JTextField(20);
		pnLoai.add(lblLoai);
		pnLoai.add(txtLoai);
		pnThongTin.add(pnLoai);
		
		JPanel pnNxb = new JPanel();
		pnNxb.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNxb = new JLabel("Năm xuất bản");
		txtNamXuatBan = new JTextField(20);
		pnNxb.add(lblNxb);
		pnNxb.add(txtNamXuatBan);
		pnThongTin.add(pnNxb);
		
		lblMa.setPreferredSize(lblNxb.getPreferredSize());

		lblTen.setPreferredSize(lblNxb.getPreferredSize());
		lblLoai.setPreferredSize(lblNxb.getPreferredSize());
		
		JPanel pnButton = new JPanel();
		pnButton.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnLuu = new JButton("Lưu");
		btnXoa = new JButton("Xóa");
		btnTimKiem = new JButton("Tìm kiếm");
		
		pnButton.add(btnLuu);
		pnButton.add(btnXoa);
		pnButton.add(btnTimKiem);
		
		pnThongTin.add(pnButton);
		
		dtmCd = new DefaultTableModel();
		dtmCd.addColumn("Mã ");
		dtmCd.addColumn("Tên");
		dtmCd.addColumn("Loại");
		dtmCd.addColumn("Năm xuất bản");
		tblCd = new JTable(dtmCd);
		JScrollPane sc = new JScrollPane(tblCd,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		con.add(sc, BorderLayout.CENTER);
		
	}
	public void showWindow(){
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
