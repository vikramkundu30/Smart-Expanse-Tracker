import javax.swing.*;
import javax.swing.table.*;

public class SearchUI extends JFrame {

    public SearchUI(){

        setSize(600,400);
        setLayout(null);

        JTextField date = new JTextField();
        date.setBounds(50,30,150,30);

        JComboBox<String> cat = new JComboBox<>(new String[]{"","Food","Travel","Bills"});
        cat.setBounds(220,30,150,30);

        JButton btn = new JButton("Search");
        btn.setBounds(400,30,100,30);

        DefaultTableModel m = new DefaultTableModel(
                new String[]{"Amt","Cat","Date","Desc"},0
        );

        JTable t = new JTable(m);
        JScrollPane sp = new JScrollPane(t);
        sp.setBounds(50,100,500,250);

        btn.addActionListener(e -> {
            m.setRowCount(0);
            for(String[] r : new ExpenseDAO().filter(
                    (String)cat.getSelectedItem(),
                    date.getText())){
                m.addRow(r);
            }
        });

        add(date); add(cat); add(btn); add(sp);
        setVisible(true);
    }
}