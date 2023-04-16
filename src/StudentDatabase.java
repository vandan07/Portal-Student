import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentDatabase {
    private JPanel Main;
    private JTextField naam ;
    private JTextField regnumber;
    private JTextField semester;
    private JTextField fees;
    private JTextField DateOfBirth;
    private JTextField City;
    private JTable table1;
    private JButton saveButton;
    private JButton updateButton;
    private JButton searchButton;
    private JButton deleteButton;
    private JScrollPane DataTable;
    private JRadioButton radioButton1;
    private JTextField StudentID;

    public static void main(String[] args) {
        JFrame frame = new JFrame("StudentDatabase");
        frame.setContentPane(new StudentDatabase().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static class Lab6 {
    }
    Connection con;
    PreparedStatement pst;
    public void connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab6?autoReconnect=true&useSSL=false","root","root");
            System.out.println("Successs");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    void table_load()
    {
        try
        {
            pst = con.prepareStatement("select * from studentdata");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public StudentDatabase() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Name,RegistrationNumber,Semester,PhoneNo,DOB,StateCity;
                Name=naam.getText();
                RegistrationNumber= regnumber.getText();
                Semester=semester.getText();
                PhoneNo= fees.getText();
                DOB=DateOfBirth.getText();
                StateCity=City.getText();
                table_load();
                try {
                    pst = con.prepareStatement("insert into studentdata(Name, RegistrationNumber,Semester,PhoneNo,DOB,StateCity)values(?,?,?,?,?,?)");
                    pst.setString(1, Name);
                    pst.setString(2, RegistrationNumber);
                    pst.setString(3, Semester);
                    pst.setString(4, PhoneNo);
                    pst.setString(5, DOB);
                    pst.setString(6, StateCity);

                    pst.executeUpdate();
                    naam.setText("");
                    regnumber.setText("");
                    semester.setText("");
                    fees.setText("");
                    DateOfBirth.setText("");
                    City.setText("");
                    naam.requestFocus();
                    JOptionPane.showMessageDialog(null, "Data Saved !");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Some Error Occured ! No Data Saved!");
                }


            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String ID = StudentID.getText();

                    pst = con.prepareStatement("select Name, RegistrationNumber,Semester,PhoneNo,DOB,StateCity from studentdata where id = ?");
                    pst.setString(1, ID);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true) {
                        String name = rs.getString(1);
                        String regno = rs.getString(2);
                        String sem = rs.getString(3);
                        String phno = rs.getString(4);
                        String dob = rs.getString(5);
                        String city = rs.getString(6);

                        naam.setText(name);
                        regnumber.setText(regno);
                        semester.setText(sem);
                        fees.setText(phno);
                        DateOfBirth.setText(dob);
                        City.setText(city);}
                    else {
                        naam.setText("");
                        regnumber.setText("");
                        semester.setText("");
                        fees.setText("");
                        DateOfBirth.setText("");
                        City.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Student No");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Name,RegistrationNumber,Semester,PhoneNo,DOB,StateCity;
                Name=naam.getText();
                RegistrationNumber= regnumber.getText();
                Semester=semester.getText();
                PhoneNo= fees.getText();
                DOB=DateOfBirth.getText();
                StateCity=City.getText();

                try {
                    String ID = StudentID.getText();

                    pst = con.prepareStatement("update studentdata set Name = ?,RegistrationNumber = ?,Semester = ?,PhoneNo=?,DOB=?,StateCity=? where id = ?");
                    pst.setString(1, Name);
                    pst.setString(2,RegistrationNumber);
                    pst.setString(3, Semester);
                    pst.setString(4, PhoneNo);
                    pst.setString(5, DOB);
                    pst.setString(6, StateCity);
                    pst.setString(7, ID);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Update!");
                    table_load();
                    naam.setText("");
                    regnumber.setText("");
                    semester.setText("");
                    fees.setText("");
                    DateOfBirth.setText("");
                    City.setText("");
                } catch (SQLException e1) {
                    e1.printStackTrace();

                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID;
                ID = StudentID.getText();

                try {
                    pst = con.prepareStatement("delete from studentdata  where id = ?");

                    pst.setString(1, ID);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Delete!");
                    table_load();
                    naam.setText("");
                    regnumber.setText("");
                    semester.setText("");
                    fees.setText("");
                    DateOfBirth.setText("");
                    City.setText("");
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }

        });

        radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Invalid Student No");
            }
        });
    }
}


