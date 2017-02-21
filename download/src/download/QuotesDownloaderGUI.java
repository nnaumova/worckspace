
package download;

import java.awt.BorderLayout;

import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.io.BufferedReader;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import java.io.InputStreamReader;

import java.text.SimpleDateFormat;

import java.util.Calendar;



import javax.swing.JButton;

import javax.swing.JComboBox;

import javax.swing.JFileChooser;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JPanel;

import javax.swing.JTextField;

import javax.swing.SwingUtilities;



public class QuotesDownloaderGUI extends JFrame implements

        ActionListener {

    private final JTextField idField;

    private final JTextField fromDateField;

    private final JTextField toDateField;

    private final JComboBox<String> intervalComboBox;

    private final JTextField outputField;



    public QuotesDownloaderGUI() {

        super();



        setTitle("Historical Quotes Downloader");

        setSize(350, 200);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        JPanel pane = new JPanel();



        pane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;



        c.gridx = 0;

        c.gridy = 0;

        c.gridwidth = 1;

        c.weightx = 0.0;

        pane.add(new JLabel("ID list: "), c);



        idField = new JTextField();

        c.gridx = 1;

        c.gridy = 0;

        c.gridwidth = 1;

        c.weightx = 1.0;

        pane.add(idField, c);



        JButton idListButton = new JButton("...");

        idListButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                JFileChooser fc = new JFileChooser();

                if (!idField.getText().isEmpty())

                    fc.setSelectedFile(new File(idField.getText()));

                int returnVal = fc.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION)

                    idField.setText(fc.getSelectedFile().getAbsolutePath());

            }

        });



        c.gridx = 2;

        c.gridy = 0;

        c.gridwidth = 1;

        c.weightx = 0.0;

        pane.add(idListButton, c);



        c.gridx = 0;

        c.gridy = 1;

        c.gridwidth = 1;

        c.weightx = 0.0;

        pane.add(new JLabel("From Date: "), c);



        fromDateField = new JTextField("01.01.2001");

        c.gridx = 1;

        c.gridy = 1;

        c.gridwidth = 2;

        c.weightx = 1.0;

        pane.add(fromDateField, c);



        c.gridx = 0;

        c.gridy = 2;

        c.gridwidth = 1;

        c.weightx = 0.0;

        pane.add(new JLabel("To Date: "), c);



        toDateField = new JTextField("31.12.2014");

        c.gridx = 1;

        c.gridy = 2;

        c.gridwidth = 2;

        c.weightx = 1.0;

        pane.add(toDateField, c);



        c.gridx = 0;

        c.gridy = 3;

        c.gridwidth = 1;

        c.weightx = 0.0;

        pane.add(new JLabel("Interval: "), c);



        String[] intervals = { "daily", "weekly", "monthly" };

        intervalComboBox = new JComboBox<String>(intervals);

        c.gridx = 1;

        c.gridy = 3;

        c.gridwidth = 2;

        c.weightx = 1.0;

        pane.add(intervalComboBox, c);



        c.gridx = 0;

        c.gridy = 4;

        c.gridwidth = 1;

        c.weightx = 0.0;

        pane.add(new JLabel("Output Directory: "), c);



        JButton outputButton = new JButton("...");

        outputButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                JFileChooser fc = new JFileChooser();

                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                if (!outputField.getText().isEmpty())

                    fc.setSelectedFile(new File(outputField.getText()));

                int returnVal = fc.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION)

                    outputField.setText(

                            fc.getSelectedFile().getAbsolutePath());

            }

        });



        c.gridx = 2;

        c.gridy = 4;

        c.gridwidth = 1;

        c.weightx = 0.0;

        pane.add(outputButton, c);



        outputField = new JTextField();

        c.gridx = 1;

        c.gridy = 4;

        c.gridwidth = 1;

        c.weightx = 1.0;

        pane.add(outputField, c);



        add(pane);



        JButton startButton = new JButton("Start");

        startButton.addActionListener(this);

        pane = new JPanel();

        pane.add(startButton);

        add(pane, BorderLayout.SOUTH);



        setVisible(true);

    }



    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override

            public void run() {

                new QuotesDownloaderGUI();

            }

        });

    }



    private static String[] intervals = { "d", "w", "m" };



    @Override

    public void actionPerformed(ActionEvent arg0) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");

        try {

            Calendar fromDate = Calendar.getInstance();

            fromDate.setTime(sdf.parse(fromDateField.getText()));



            Calendar toDate = Calendar.getInstance();

            toDate.setTime(sdf.parse(toDateField.getText()));



            String interval = intervals[intervalComboBox.getSelectedIndex()];



            String dirPath = outputField.getText();



            String fileName = idField.getText();



            BufferedReader reader = new BufferedReader(

                    new InputStreamReader(

                            new FileInputStream(new File(fileName))));

            String id = "";

            while ((id = reader.readLine()) != null) {

                id = id.trim();

                if (id.isEmpty())

                    continue;



                File f = new File(dirPath + File.separator + id + ".csv");

                if (f.exists())

                    continue;



                try {

                    QuotesDownloader.download(id, fromDate, toDate, interval,

                            dirPath);

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

            reader.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }



}

