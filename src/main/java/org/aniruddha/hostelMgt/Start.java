package org.aniruddha.hostelMgt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Start {
    public static final FirebaseController firebaseController =
            FirebaseController.getInstance();
    public static void main(String args[]) {
        JFrame main_frame = new JFrame("Hostel Management System");
        JTabbedPane tb = new JTabbedPane();
        JPanel panel1 = new JPanel();
        main_frame.setSize(1000,800);
        main_frame.setLayout(new BorderLayout());

        ImageIcon viewi = new ImageIcon("images\\view_icon.png");
        ImageIcon searchi = new ImageIcon("images\\search_icon.png");
        ImageIcon addi = new ImageIcon("images\\add_icon.png");
        ImageIcon modifyi = new ImageIcon("images\\modify_icon.png");
        panel1.setLayout(new BorderLayout());
        tb.addTab("View Data",viewi ,  new view_rec());
        tb.addTab("Search ",searchi ,  new search_rec());
        tb.addTab("Insert Data",addi ,  new insert_rec());
        tb.addTab("Modify Data", modifyi , new modify_rec());

        JPasswordField pass_f = new JPasswordField(10);
        panel1.add(tb);
        main_frame.add(panel1,BorderLayout.NORTH);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter a password:");
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        main_frame.setVisible(true);
        /*while(true)
        {
            int option = JOptionPane.showOptionDialog(null, panel, "Login",
                    JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[1]);
            if(option == 0)
            {
                String password = pass.getText();
                //main_frame.setVisible(true);
                if(password.equals("admin@123"))
                {
                    main_frame.setVisible(true);
                    break;
                }
                System.out.println(password);
            }
            if(option == 1)
                break;
        }*/
    }
}

class view_rec extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    Font t_font ,l_font;
    DefaultTableModel tm = new DefaultTableModel();
    JTable view_tabl = new JTable();
    JButton refresh = new JButton("Refresh");
    int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
    int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
    JScrollPane scroll = new JScrollPane(view_tabl , v ,h);
    private Image bg;

    public view_rec()
    {
        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        view_tabl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setPreferredSize(new Dimension(screenSize.width,(screenSize.height-135)));
        add(scroll);
        t_font=new Font("Times New Roman" , Font.PLAIN , 22);
        //l_font=new Font("Times New Roman" , Font.BOLD , 24 );
        String[] columnNames = {"ID", "NAME" , "ROOM NO." , "REGISTRATION NO." , "CONTACT NO."};
        tm.setColumnIdentifiers(columnNames);
        view_tabl.setFont(t_font);
        view_tabl.setRowHeight(40);
        refresh.setFont(t_font);
        refresh.addActionListener(this);
        add(refresh , BorderLayout.SOUTH);
        view_tabl.setModel(tm);


        TableColumn column1 = view_tabl.getColumn("ID");
        TableColumn column2 = view_tabl.getColumn("ROOM NO.");
        TableColumn column3 = view_tabl.getColumn("REGISTRATION NO.");
        //TableColumn column3 = view_tabl.getColumn("Contents");
        column1.setMinWidth(140);
        column1.setMaxWidth(140);
        column2.setMinWidth(140);
        column2.setMaxWidth(140);
        column3.setMinWidth(280);
        column3.setMaxWidth(280);
        view_tabl.setFocusable(false);
        view_tabl.setRowSelectionAllowed(true);
        view_tabl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        this.ref();
    }

    public void ref() {
        try {
            int cnt = 0;
            List<StudentData> studList = Start.firebaseController.retrieveData();
            for (StudentData stud : studList) {
                tm.addRow(new Object[]{stud.getStudId() , stud.getName() , stud.getRoomNo() ,
                        stud.getColRegNo() , stud.getContactNo()});
                cnt++;
            }
            //con.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        int rows = tm.getRowCount();
        for(int i = rows - 1; i >=0; i--)
        {
            tm.removeRow(i);
        }
        this.ref();
    }
}


class search_rec extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    JLabel l1 , l2;
    JTextField jtf;
    JTable search_tabl = new JTable();
    DefaultTableModel tm = new DefaultTableModel();
    JButton search = new JButton("Search");
    JPanel sp = new JPanel();
    int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
    int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
    JScrollPane scroll = new JScrollPane(search_tabl , v ,h);
    Font t_font ,l_font;
    int flag = 0;

    public search_rec()
    {
        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(screenSize.width,(screenSize.height-135)));
        l1 = new JLabel("ID search:=");
        l2 = new JLabel();
        jtf = new JTextField(10);
        t_font=new Font("Times New Roman" , Font.PLAIN , 22);
        sp.setLayout(new FlowLayout());
        String[] columnNames = {"ID", "NAME" , "ROOM NO." , "REGISTRATION NO." , "CONTACT NO."};
        tm.setColumnIdentifiers(columnNames);
        search_tabl.setFont(t_font);
        search_tabl.setRowHeight(40);
        sp.add(l1, BorderLayout.SOUTH);
        sp.add(jtf);
        sp.add(search);
        sp.add(l2);
        add(scroll);

        search.addActionListener(this);
        add(sp , BorderLayout.NORTH);
        search_tabl.setModel(tm);

        TableColumn column1 = search_tabl.getColumn("ID");
        TableColumn column2 = search_tabl.getColumn("ROOM NO.");
        TableColumn column3 = search_tabl.getColumn("CONTACT NO.");

        column1.setMinWidth(140);
        column1.setMaxWidth(140);
        column2.setMinWidth(140);
        column2.setMaxWidth(140);
        column3.setMinWidth(280);
        column3.setMaxWidth(280);
        search_tabl.setFont(t_font);
        search_tabl.setFocusable(false);
        search_tabl.setRowSelectionAllowed(true);
        search_tabl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
    public void actionPerformed(ActionEvent e)
    {
        flag = 0;
        int rows = tm.getRowCount();
        for(int i = rows - 1; i >=0; i--) {
            tm.removeRow(i);
        }
        if(jtf.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter Valid ID");
        }
        else
        {
            try
            {
                Map<String, Object> response = Start.firebaseController.retrieveQueryData(jtf.getText());
                if (response.get(FirebaseController.STATUS).equals(true)) {
                    flag = 1;
                    StudentData stud = (StudentData) response.get(FirebaseController.RESPONSE);
                    tm.addRow(new Object[]{stud.getStudId() , stud.getName() , stud.getRoomNo(),
                            stud.getColRegNo() , stud.getContactNo()});
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
            if(flag == 0) {
                JOptionPane.showMessageDialog(null, "No Data Found");
            }
        }
    }
}


class insert_rec extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    JLabel id_l ,name_l ,room_l , roll_l, contact_l, res;
    JTextField id_tf , name_tf , room_tf, roll_tf , contact_tf;

    JPanel p1 ,p2;
    JButton save_b, clear_b;
    int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
    int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
    JScrollPane scroll;
    int b;

    public insert_rec()
    {
        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(screenSize.width,(screenSize.height-135)));

        id_l = new JLabel("ID:");
        name_l = new JLabel("NAME:");
        room_l = new JLabel("ROOM NO.:");
        roll_l = new JLabel("REGISTRATION NO.:");
        contact_l = new JLabel("CONTACT NO.:");
        res = new JLabel("");

        id_tf = new JTextField("");
        name_tf = new JTextField("");
        roll_tf = new JTextField("");
        room_tf = new JTextField("");
        contact_tf = new JTextField("");

        save_b = new JButton("Save Record");
        clear_b = new JButton("Clear Fields");

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setPreferredSize(new Dimension(100,100));
        res.setBounds(400 , 600 , 600 , 30);
        id_l.setBounds(80 ,80 , 270 ,30);
        id_tf.setBounds(300 , 80 , 285 , 30);
        name_l.setBounds(80 , 140 , 250 , 30 );
        name_tf.setBounds(300 , 140 , 285 , 30);
        room_l.setBounds(80 , 200 , 270 , 30);
        room_tf.setBounds(300 , 200 , 285 , 30);
        roll_l.setBounds(80 , 260 , 270 , 30);
        roll_tf.setBounds(300, 260, 285, 30);
        contact_l.setBounds(80, 320, 270, 30);
        contact_tf.setBounds(300, 320, 285, 30);

        save_b.setBounds(470 , 460 , 200 ,30);
        clear_b.setBounds(700, 460, 200, 30);
        p1.add(id_l);
        p1.add(id_tf);
        p1.add(name_l);
        p1.add(name_tf);
        p1.add(room_l);
        p1.add(room_tf);
        p1.add(roll_l);
        p1.add(roll_tf);
        p1.add(contact_l);
        p1.add(contact_tf);

        p1.add(save_b);
        p1.add(clear_b);
        p1.add(res);
        add(p1);
        save_b.addActionListener(this);
        clear_b.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        String type;
        type = e.getActionCommand();
        if(type.equals("Save Record"))
        {
            b = 0;
            if (id_tf.getText().equals("") || name_tf.getText().equals("") || room_tf.getText().equals("")
                    || roll_tf.getText().equals("") || contact_tf.getText().equals("") ||
                    (contact_tf.getText()).length() != 10 ||
                    (contact_tf.getText()).matches("[0-9]+") == false ||
                    (roll_tf.getText()).matches("[a-zA-Z]+.[0-9]+") == false) {
                JOptionPane.showMessageDialog(null, "Please enter valid information");
            }
            else
            {
                try
                {
                    StudentData data = new StudentData();
                    data.setStudId(id_tf.getText());
                    data.setName(name_tf.getText());
                    data.setColRegNo(roll_tf.getText());
                    data.setRoomNo(Integer.parseInt(room_tf.getText()));
                    data.setContactNo(Long.valueOf(contact_tf.getText()));
                    Map<String, Object> resp = Start.firebaseController.saveData(data);

                    if (resp.get(FirebaseController.STATUS).equals(true)) {
                        JOptionPane.showMessageDialog(null, "Record Number " + id_tf.getText() + " is saved successfully at " + resp.get(FirebaseController.MESSAGE));
                        id_tf.setText("");
                        name_tf.setText("");
                        room_tf.setText("");
                        roll_tf.setText("");
                        contact_tf.setText("");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Unable to save record" + resp.get(FirebaseController.MESSAGE));
                    }
                } catch(Exception ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(null, "Unable to save record, Exception:"+ex);
                }

            }//else end
        }//save record end
        else {
            id_tf.setText("");
            name_tf.setText("");
            room_tf.setText("");
            roll_tf.setText("");
            contact_tf.setText("");
        }
    }
}

class modify_rec extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    String id_val = "";
    JLabel id_l ,name_l ,roll_l , save_l, res , search_l , serr_l, room_l, contact_l;
    JTextField id_tf , name_tf ,roll_tf, room_tf, contact_tf, search_tf ;
    JPanel p1 ,p3;
    JButton save_b , search_b , delete_b;
    int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
    int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
    JScrollPane scroll;
    int b = 0 , d = 0;
    public modify_rec()
    {
        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(screenSize.width,(screenSize.height-135)));

        id_l = new JLabel("ID:");
        name_l = new JLabel("Name:");
        room_l = new JLabel("Room No.:");
        roll_l = new JLabel("Registration No.:");
        contact_l = new JLabel("Contact No.:");
        search_l = new JLabel("Search ID:");
        res = new JLabel("");
        serr_l = new JLabel("");
        id_tf = new JTextField("");
        name_tf = new JTextField("");
        roll_tf = new JTextField("");
        room_tf = new JTextField("");
        contact_tf = new JTextField("");
        search_tf = new JTextField(10);
        search_b = new JButton("Search Record");
        save_b = new JButton("Modify Record");
        delete_b = new JButton("Delete Record");
        p1 = new JPanel();
        p3 = new JPanel();
        p1.setLayout(null);
        p3.setLayout(new FlowLayout());
        p1.setPreferredSize(new Dimension(100,100));
        res.setBounds(800 , 600 , 1050 , 60);
        res.setBounds(400 , 600 , 600 , 30);
        id_l.setBounds(80 ,80 , 270 ,30);
        id_tf.setBounds(300 , 80 , 285 , 30);
        name_l.setBounds(80 , 140 , 250 , 30 );
        name_tf.setBounds(300 , 140 , 285 , 30);
        room_l.setBounds(80 , 200 , 270 , 30);
        room_tf.setBounds(300 , 200 , 285 , 30);
        roll_l.setBounds(80 , 260 , 270 , 30);
        roll_tf.setBounds(300, 260, 285, 30);
        contact_l.setBounds(80, 320, 270, 30);
        contact_tf.setBounds(300, 320, 285, 30);

        save_b.setBounds(470 , 460 , 200 ,30);
        delete_b.setBounds(700, 460, 200, 30);
        p3.add(search_l);
        p3.add(search_tf);
        p3.add(search_b);
        p3.add(serr_l);
        p1.add(id_l);
        p1.add(id_tf);
        p1.add(name_l);
        p1.add(name_tf);
        p1.add(room_l);
        p1.add(room_tf);
        p1.add(roll_l);
        p1.add(roll_tf);
        p1.add(contact_l);
        p1.add(contact_tf);

        p1.add(save_b);
        p1.add(delete_b);
        p1.add(res);
        add(p3 , BorderLayout.NORTH);
        add(p1);

        search_b.addActionListener(this);
        save_b.addActionListener(this);
        delete_b.addActionListener(this);


    }
    public void actionPerformed(ActionEvent e)
    {
        String sel;
        sel = e.getActionCommand();
        if(sel.equals("Search Record"))
        {
            res.setText("");
            if(search_tf.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter Valid Data");
                id_tf.setText("");
                name_tf.setText("");
                room_tf.setText("");
                roll_tf.setText("");
                contact_tf.setText("");
            }
            else {
                try {
                    id_val = search_tf.getText();
                    Map<String, Object> response = Start.firebaseController.retrieveQueryData(id_val);
                    if(response.get(FirebaseController.STATUS).equals(true)) {
                        StudentData stud = (StudentData) response.get(FirebaseController.RESPONSE);
                        id_tf.setText(stud.getStudId());
                        name_tf.setText(stud.getName());
                        room_tf.setText(String.valueOf(stud.getRoomNo()));
                        roll_tf.setText(stud.getColRegNo());
                        contact_tf.setText(String.valueOf(stud.getContactNo()));
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "No Record Found");
                    }
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(null, "Please Enter Valid Data"+ex);
                }
            }
        }

        if(sel.equals("Modify Record"))
        {
            serr_l.setText("");
            if(id_tf.getText().equals("") || name_tf.getText().equals("") || room_tf.getText().equals("") ||
                    roll_tf.getText().equals("") || contact_tf.getText().equals("") ||
                    (contact_tf.getText()).length()!=10 ||
                    (contact_tf.getText()).matches("[0-9]+")==false ||
                    (roll_tf.getText()).matches("[a-zA-Z]+.[0-9]+")==false) {
                JOptionPane.showMessageDialog(null, "Please Enter Valid Data");
            }
            else {

                int n = JOptionPane.showConfirmDialog(null , "Are You Sure to Modify Record number "+id_val+"?" , "Modify "  , JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE);
                if(n == 0) {
                    try {
                        StudentData stud = new StudentData();
                        stud.setStudId(id_tf.getText());
                        stud.setName(name_tf.getText());
                        stud.setRoomNo(Integer.parseInt(room_tf.getText()));
                        stud.setColRegNo(roll_tf.getText());
                        stud.setContactNo(Long.valueOf(contact_tf.getText()));
                        Map<String, Object> response = Start.firebaseController.saveData(stud);

                        if(response.get(FirebaseController.STATUS).equals(true)) {
                            JOptionPane.showMessageDialog(null, "Record number "+id_val+" is updated successfully at : " + response.get(FirebaseController.MESSAGE));
                            id_tf.setText("");
                            name_tf.setText("");
                            room_tf.setText("");
                            roll_tf.setText("");
                            contact_tf.setText("");
                            search_tf.setText("");
                            id_val="";
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Unable to save record(Please Check record Details)");
                        }
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(null, "Unable to save record(Please Check record Details) "+ex);
                    }
                }
            }
        }
        if(sel.equals("Delete Record")) {
            serr_l.setText("");
            if(id_tf.getText().equals("") || name_tf.getText().equals("") || room_tf.getText().equals("") ||
                    roll_tf.getText().equals("") || contact_tf.getText().equals("") ||
                    (contact_tf.getText()).length()!=10 ||
                    (contact_tf.getText()).matches("[0-9]+")==false ||
                    (roll_tf.getText()).matches("[a-zA-Z]+.[0-9]+")==false) {
                JOptionPane.showMessageDialog(null, "Please Enter Valid Data");
            }
            else {
                int n = JOptionPane.showConfirmDialog(null , "Are You Sure to Delete Record number "+id_val+"?" , "Delete "  , JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE);
                if(n == 0) {
                    try {
                        Map<String, Object> response = Start.firebaseController.deleteQueryData(id_val);
                        if(response.get(FirebaseController.STATUS).equals(true)) {
                            JOptionPane.showMessageDialog(null, "Record number "+id_val+" is Deleted Successfully at: " + response.get(FirebaseController.MESSAGE));
                            id_tf.setText("");
                            name_tf.setText("");
                            room_tf.setText("");
                            roll_tf.setText("");
                            contact_tf.setText("");
                            search_tf.setText("");
                            id_val="";
                        }
                    } catch(Exception ex) {
                        System.out.println(ex);
                        JOptionPane.showMessageDialog(null, "Unable to Delete record  "+ex);
                    }
                }
            }
        }
    }
}
