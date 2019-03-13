import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class GUI
{
    JTable nameTable = new JTable();
    JTable fieldTable = new JTable();
    JTextField[] propertiesTexts = new JTextField[19];


    private Model model = new Model();

    public GUI()
    {
        this.run();
    }

    private void run()
    {
        //Basic setting
        JFrame frame =  new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);

        //Menu
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        menubar.add(file);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        JMenuItem fileItem1 = new JMenuItem("Open CSV");
        JMenuItem fileItem2 = new JMenuItem("Save as JSON");
        JMenuItem fileItem3 = new JMenuItem("Open JSON");
        file.add(fileItem1);
        fileItem1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    try
                    {
                        model.readFile(selectedFile.getAbsolutePath());
                        updateNameTable();
                    } catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        });
        file.add(fileItem2);
        fileItem2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    model.saveAsJSON();
                } catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        file.add(fileItem3);
        fileItem3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    try {
                        model.readJSON(selectedFile.getAbsolutePath());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    updateNameTable();
                }
            }
        });
        frame.add(menubar, BorderLayout.PAGE_START);


        //nameTable
        updateNameTable();
        nameTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                updateProperty(nameTable.getValueAt(nameTable.getSelectedRow(), 0).toString(), nameTable.getValueAt(nameTable.getSelectedRow(), 1).toString());
               // System.out.println(nameTable.getValueAt(nameTable.getSelectedRow(), 0).toString());
            }
        });
        nameTable.setPreferredScrollableViewportSize(nameTable.getPreferredSize());
        nameTable.setFillsViewportHeight(true);
        JScrollPane tableSP = new JScrollPane(nameTable);
        frame.add(tableSP, BorderLayout.EAST);

       // search : allow substring search
        JPanel searchPanel = new JPanel();
        JButton search = new JButton("Search");
        JTextField name = new JTextField(10);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] fullName = model.getFullname(name.getText());
                if (fullName != null)
                {
                    updateProperty(fullName[0], fullName[1]);
                }
                else
                {
                    name.setText("Does not exist");
                }
            }
        });
        searchPanel.setLayout(new FlowLayout());
        searchPanel.add(name);
        searchPanel.add(search);

        //display information of patient
        JPanel properties = new JPanel();
        properties.setLayout(new GridLayout(0, 2));
        for (int i = 0; i < 19; i++)
        {
            properties.add(new JLabel(model.getFields()[i]));
            propertiesTexts[i] = new JTextField(20);
            propertiesTexts[i].setEditable(false);
            properties.add(propertiesTexts[i]);
        }


        JPanel information = new JPanel();
        information.setLayout(new BorderLayout());
        information.add(searchPanel, BorderLayout.NORTH);
        information.add(properties, BorderLayout.CENTER);
        frame.add(information, BorderLayout.CENTER);


        frame.setVisible(true);
    }

    private void updateNameTable()
    {
        String[] header = {"First Name", "Last Name"};
        DefaultTableModel tableModel = new DefaultTableModel(header, 0){
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        Map<String, String> names = model.getNames();
        for (String key : names.keySet())
        {
            String[] row = {key, names.get(key)};
            tableModel.addRow(row);
        }
        this.nameTable.setModel(tableModel);
    }


    private void updateProperty(String first, String last)
    {
        String[] value = model.getProperty(first, last);
        if (value != null)
        {
            for (int i = 0; i < 19; i++)
            {
                propertiesTexts[i].setText(value[i]);
            }
        }
    }

}
