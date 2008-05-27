package reporterpack;

/**
 * 
 * TODO BU YAZDIRMA SECENKELERI GELISTIRILECEK, DAHA PROFESYONELCE YAPILACAK
 * 
 */
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

import java.lang.String;
import java.lang.String;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import net.sf.fjreport.tableprint.PageTablePrint;
import net.sf.fjreport.util.GridBagUtil;
import net.sf.fjreport.util.CommonUtil;

public class TablePrintSample extends JFrame implements global.global_variables {

    private JTable tb;
    private PageTablePrint pageTable;
    public static String alignments[] = {"Left", "Center", "Right"};
    private TablePrintSample instance;
    private JCheckBox ckPrintFirstHeader = new JCheckBox("Print header on first page");
    JTextArea txtTitle = new JTextArea(3, 8);
    JComboBox cbTitleFont = new JComboBox(CommonUtil.fontNames);
    JComboBox cbTitleSize = new JComboBox(CommonUtil.fontSizes);
    JComboBox cbTitleAlign = new JComboBox(alignments);
    JTextArea txtHeader = new JTextArea(3, 8);
    JComboBox cbHeaderFont = new JComboBox(CommonUtil.fontNames);
    JComboBox cbHeaderSize = new JComboBox(CommonUtil.fontSizes);
    JComboBox cbHeaderAlign = new JComboBox(alignments);
    JTextArea txtFooter = new JTextArea(3, 8);
    JComboBox cbFooterFont = new JComboBox(CommonUtil.fontNames);
    JComboBox cbFooterSize = new JComboBox(CommonUtil.fontSizes);
    JComboBox cbFooterAlign = new JComboBox(alignments);

    public TablePrintSample() {
        super("Baskı yapılandırma");
        instance = this;
        createUI();
        setSize(800, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createUI() {
        tb = new JTable(getTableModel());
        JScrollPane sc = new JScrollPane(tb);

        pageTable = new PageTablePrint();
        pageTable.packTable(this, "Sayfa Önizleme", tb.getModel(), null);

        JPanel bottomPane = new JPanel(new GridLayout(1, 3));
        int preferredW = 0;
        bottomPane.add(getTitleChooserPane());
        bottomPane.add(getHeaderChooserPane());
        bottomPane.add(getFooterChooserPane());


        JToolBar toolbar = new JToolBar();
        JButton btnPrint = new JButton("Yazdır");
        btnPrint.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                configReports();
                try {
                    tb.print();
                } catch (PrinterException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
//				pageTable.print();
            }
        });
        JButton btnPreview = new JButton("Önizleme");
        btnPreview.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                configReports();
                pageTable.preview();

            }
        });
        JButton btnSetup = new JButton("Sayfa ayarı");
        btnSetup.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                pageTable.printSetup();
            }
        });
        toolbar.add(btnPrint);
        ;
        toolbar.add(btnPreview);
        toolbar.add(ckPrintFirstHeader);
        add(toolbar, "North");
        add(sc, "Center");
        add(bottomPane, "South");
        setSize((int) (bottomPane.getPreferredSize().getWidth() + 10), 500);
    }
/**
 * TEKLIF MUHATTAP BILGISI
 */
    public  String teklif_detayi = "Teklif Kodu:" + ana.TABLO3.getValueAt(0, 0).toString() + "\n" +
                "Muhattap Kuruluş:" + ana.TABLO3.getValueAt(0, 1).toString() + "\n" +
                "Son geçerlilik tarihi:" + ana.TABLO3.getValueAt(0, 2).toString() + "\n" +
                "Yetkili:" + ana.TABLO3.getValueAt(0, 3).toString() + "\n" +
                "Telefon:" + ana.TABLO3.getValueAt(0, 4).toString() + "\n" +
                "Fax:" + ana.TABLO3.getValueAt(0, 5).toString() + "\n" +
                "Telif tarihi:" + ana.TABLO3.getValueAt(0, 6).toString() + "\n" +
                "";
    
    protected void configReports() {
         
         
        
        cbTitleFont.setEnabled(false);
        pageTable.setTitle("MOCAWA APPLICATION REPORT\n" +
                "TEKLİF MEKTUBU\n\n\n\n\n"+txtTitle.getText());
//		pageTable.setTitleFont(new Font((String) cbTitleFont
//				.getSelectedItem(), 0, Integer
//				.parseInt((String) cbTitleSize.getSelectedItem())));

        Font f=new Font("TAHOMA",1,12);
        pageTable.setTitleFont(f);
        
        
        
		pageTable.setTitleAlignment(cbTitleAlign.getSelectedIndex());
//		
        pageTable.setHeader(txtHeader.getText());
		pageTable.setHeaderFont(new Font((String) cbHeaderFont
				.getSelectedItem(), 0, Integer
				.parseInt((String) cbHeaderSize.getSelectedItem())));
		pageTable.setHeaderAlignment(cbHeaderAlign.getSelectedIndex());
//		

        pageTable.setFooter(txtFooter.getText()+"\n\n*Bu teklif mektubu MOCAWA programıyla oluşturulmuştur.\n Detaylı bilgi için: www.suatatan.com");
		pageTable.setFooterFont(new Font((String) cbFooterFont
				.getSelectedItem(), 0, Integer
				.parseInt((String) cbFooterSize.getSelectedItem())));
		pageTable.setFooterAlignment(cbFooterAlign.getSelectedIndex());

        pageTable.setPrintHeaderOnFirstPage(ckPrintFirstHeader.isSelected());
    }

    private JPanel getTitleChooserPane() {
        JPanel pane = new JPanel(new GridBagLayout());

       

        txtTitle.setText("Buraya başlığa eklemek istediğiniz metni yazabilirsiniz.");
        JPanel p = new JPanel(new BorderLayout());
        p.add(cbTitleFont, "North");
        p.add(cbTitleSize, "South");
        cbTitleSize.setSelectedIndex(5);
        cbTitleAlign.setSelectedIndex(1);
        JPanel p2 = new JPanel(new BorderLayout());
        p2.add(new JLabel("Align: "), "West");
        p2.add(cbTitleAlign, "Center");
        GridBagUtil.addf2(pane, new JScrollPane(txtTitle), 0, 0, 1, 1, GridBagUtil.BOTH);
        GridBagUtil.addf2(pane, p, 0, 1, 1, 0, GridBagUtil.HORIZONTAL);
        GridBagUtil.addf2(pane, p2, 0, 2, 1, 0, GridBagUtil.HORIZONTAL);
        pane.setBorder(new TitledBorder("Başlık,başlık-altı ve stili"));
        return pane;
    }

    private JPanel getHeaderChooserPane() {
        JPanel pane = new JPanel(new GridBagLayout());
        txtHeader.setText("Sayfa: %p / %allpage");
        JPanel p = new JPanel(new BorderLayout());
        p.add(cbHeaderFont, "North");
        p.add(cbHeaderSize, "South");
        cbHeaderSize.setSelectedIndex(4);
        JPanel p2 = new JPanel(new BorderLayout());
        p2.add(new JLabel("Align: "), "West");
        p2.add(cbHeaderAlign, "Center");
        cbHeaderAlign.setSelectedIndex(2);
        GridBagUtil.addf2(pane, new JScrollPane(txtHeader), 0, 0, 1, 1, GridBagUtil.BOTH);
        GridBagUtil.addf2(pane, p, 0, 1, 1, 0, GridBagUtil.HORIZONTAL);
        GridBagUtil.addf2(pane, p2, 0, 2, 1, 0, GridBagUtil.HORIZONTAL);
        pane.setBorder(new TitledBorder("Choose Header style"));
        return pane;
    }

    private JPanel getFooterChooserPane() {
        
         
        
        JPanel pane = new JPanel(new GridBagLayout());
        txtFooter.setText(teklif_detayi+"\nwww.suatatan.com \n%date");
        JPanel p = new JPanel(new BorderLayout());
        Font font=new Font("TAHOMA",1,5);
        
        cbFooterFont.setSelectedItem(font);
        p.add(cbFooterFont, "North");
        p.add(cbFooterSize, "South");
        cbFooterSize.setSelectedIndex(4);
        cbFooterAlign.setSelectedIndex(0);
        JPanel p2 = new JPanel(new BorderLayout());
        p2.add(new JLabel("Align: "), "West");
        p2.add(cbFooterAlign, "Center");
        GridBagUtil.addf2(pane, new JScrollPane(txtFooter), 0, 0, 1, 1, GridBagUtil.BOTH);
        GridBagUtil.addf2(pane, p, 0, 1, 1, 0, GridBagUtil.HORIZONTAL);
        GridBagUtil.addf2(pane, p2, 0, 2, 1, 0, GridBagUtil.HORIZONTAL);
        pane.setBorder(new TitledBorder("Choose footer style"));
        return pane;
    }
    private DefaultTableModel rs;

    private DefaultTableModel getTableModel() {
        final Object[] columnNames = new String[]{"Teklif No", "Boy", "En", "Yükseklik", "Dalga", "Kalite", "Safiaeni,", "Fiyat S.Eni", "SafiaBoyu", "BirimFiyat"};
        DefaultTableModel rsx = new DefaultTableModel(columnNames, op.OBJ.length);
//	 
        int i = 0;

        while (i < op.OBJ.length) {


            rsx.setValueAt((op.OBJ[i][0]), i, 0);
            rsx.setValueAt((op.OBJ[i][1]), i, 1);
            rsx.setValueAt((op.OBJ[i][2]), i, 2);
            rsx.setValueAt((op.OBJ[i][3]), i, 3);
            rsx.setValueAt((op.OBJ[i][4]), i, 4);
            rsx.setValueAt((op.OBJ[i][5]), i, 5);
            rsx.setValueAt((op.OBJ[i][6]), i, 6);
            rsx.setValueAt((op.OBJ[i][7]), i, 7);
            rsx.setValueAt((op.OBJ[i][8]), i, 8);
            rsx.setValueAt((op.OBJ[i][9]), i, 9);

            i++;

        }



        return rsx;
    }

    public static void main(String[] args) {
        new TablePrintSample();
    }
}
