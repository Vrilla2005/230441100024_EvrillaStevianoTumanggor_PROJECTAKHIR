/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author evril
 */
public class Frame1 extends javax.swing.JFrame {

    Connection conn;
    
    private DefaultTableModel anggaran;
    private DefaultTableModel pembelian;
    private DefaultTableModel gajikaryawan;
    private DefaultTableModel pemasukkan;
    private DefaultTableModel laporanpemasukkan;
    private DefaultTableModel laporanpembelian;
    private DefaultTableModel laporanhutang;
    
//    private int jumlahBarang = 0;
    
    /**
     * Creates new form Frame1
     */
    public Frame1() {
        initComponents();
        this.setLocationRelativeTo(null);
        conn = koneksi.getConnection(); 
        
        tabelPemasukkan();
        tabelAnggaran();
        tabelPembelian();
        tabelGaji();
        tabelLaporanPemasukkan();
        tabelLaporanPembelian();
        tabelLaporanHutang();
        
        loadDataLaporanPemasukkan();
        loadDataLaporanPembelian();
        loadDataLaporanHutang();
        loadDataPemasukkan();
        loadDataAnggaran();
        loadDataPembelian();
        loadDataGaji();
        
        KeyListener keyListener = new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkButtonAnggaran();
//                checkButtonPemasukkan();
            }
        };
        
        txtid.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); 
                    JOptionPane.showMessageDialog(null, "ID wajib berupa angka!");
                }
            }
        });
        txtketerangan.addKeyListener(keyListener);
        tglinput.getDateEditor().addPropertyChangeListener(evt -> checkButtonAnggaran());
        txtjumlahuang.addKeyListener(keyListener);
        
        txtid1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); 
                    JOptionPane.showMessageDialog(null, "ID wajib berupa angka!");
                }
            }
        });
        txtnamabarang.addKeyListener(keyListener);
//        tglpembelian.getDateEditor().addPropertyChangeListener(evt -> checkButtonPemasukkan());
        txtharga.addKeyListener(keyListener);
        
        txtidgaji.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); 
                    JOptionPane.showMessageDialog(null, "ID wajib berupa angka!");
                }
            }
        });
        txtgaji.addKeyListener(keyListener);
        tglgaji.addKeyListener(keyListener);
        txtjumlahgaji.addKeyListener(keyListener);
        
        checkButtonAnggaran();
//        checkButtonPemasukkan();
//        checkButtonGaji();

        cbkategori.setSelectedIndex(-1);
    }
    
    private void checkButtonAnggaran() {
    boolean isiID = !txtid.getText().trim().isEmpty();
    boolean isiKeterangan = !txtketerangan.getText().trim().isEmpty();
    boolean isiJumlah = !txtjumlahuang.getText().trim().isEmpty();
    boolean isiKategori = cbkategori.getSelectedItem() != null;
    boolean isiTanggal = tglinput.getDate() != null; 

        if (isiID && !isiKeterangan && !isiJumlah && !isiKategori) {
            deleteinput.setEnabled(true);
            updateinput.setEnabled(false);
            tambahinput.setEnabled(false);
        } else if (isiID && isiKeterangan && isiJumlah && isiKategori && isiTanggal) {
            deleteinput.setEnabled(false);
            updateinput.setEnabled(true);
            tambahinput.setEnabled(false);
        } else if (!isiID && isiKeterangan && isiJumlah && isiKategori && isiTanggal) {
            deleteinput.setEnabled(false);
            updateinput.setEnabled(false);
            tambahinput.setEnabled(true);
        } else {
            deleteinput.setEnabled(false);
            updateinput.setEnabled(false);
            tambahinput.setEnabled(false);
        }
    }
    
//    private void checkButtonPemasukkan() {
//    boolean isiIDPembelian = !txtid1.getText().trim().isEmpty();
//    boolean isiNamaBarang = !txtnamabarang.getText().trim().isEmpty();
//    boolean isiTgl = tglpembelian.getDate() != null;
//    boolean isiJumlahPembelian = !txtharga.getText().trim().isEmpty();
//    boolean isiJumlahBeli = !txtJumlahBarang.getText().trim().isEmpty();
//    
//        if (isiIDPembelian && isiNamaBarang && isiJumlahPembelian && isiTgl && isiJumlahBeli) {
//            deleteit.setEnabled(false);
//            updateit.setEnabled(true);
//            tambahit.setEnabled(false);
//        } else if (!isiIDPembelian && isiNamaBarang && isiJumlahPembelian && isiTgl && isiJumlahBeli) {
//            deleteit.setEnabled(false);
//            updateit.setEnabled(false);
//            tambahit.setEnabled(true);
//        } else if (isiIDPembelian && !isiNamaBarang && !isiTgl && !isiJumlahBeli) {
//            deleteit.setEnabled(true);
//            updateit.setEnabled(false);
//            tambahit.setEnabled(false);
//        } else {
//            deleteit.setEnabled(false);
//            updateit.setEnabled(false);
//            tambahit.setEnabled(false);
//        }
//    }
    
//    private void checkButtonGaji() {
//    boolean isiIDGaji = !txtidgaji.getText().trim().isEmpty();
//    boolean isiNamaKaryawan = !txtgaji.getText().trim().isEmpty();
//    boolean isiTglGaji = tglgaji.getDate() != null;
//    boolean isiJumlahGaji = !txtjumlahgaji.getText().trim().isEmpty();
//    
//        if (isiIDGaji && isiNamaKaryawan && isiJumlahGaji && isiTglGaji) {
//            btndeletegaji.setEnabled(false);
//            btnupdategaji.setEnabled(true);
//            btntambahgaji.setEnabled(false);
//        } else if (!isiIDGaji && isiNamaKaryawan && isiJumlahGaji && isiTglGaji) {
//            btndeletegaji.setEnabled(false);
//            btnupdategaji.setEnabled(false);
//            btntambahgaji.setEnabled(true);
//        } else if (isiIDGaji && !isiNamaKaryawan && !isiJumlahGaji && isiTglGaji) {
//            btndeletegaji.setEnabled(true);
//            btnupdategaji.setEnabled(false);
//            btntambahgaji.setEnabled(false);
//        } else {
//            btndeletegaji.setEnabled(false);
//            btnupdategaji.setEnabled(false);
//            btntambahgaji.setEnabled(false);
//        }
//    }

    private void tabelPemasukkan(){
        pemasukkan = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
//        pemasukkan = new DefaultTableModel();
        jTable2.setModel(pemasukkan);

        pemasukkan.addColumn("ID");
        pemasukkan.addColumn("Keterangan anggaran");
        pemasukkan.addColumn("Tgl input");
        pemasukkan.addColumn("Jumlah Uang");
        pemasukkan.addColumn("Kategori");
    }
    
    private void tabelLaporanPemasukkan(){
        laporanpemasukkan = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
//        laporanpemasukkan = new DefaultTableModel();
        jTable4.setModel(laporanpemasukkan);

        laporanpemasukkan.addColumn("ID");
        laporanpemasukkan.addColumn("Keterangan anggaran");
        laporanpemasukkan.addColumn("Tgl input");
        laporanpemasukkan.addColumn("Jumlah Uang");
        laporanpemasukkan.addColumn("Kategori");
    }
    
    private void tabelLaporanPembelian(){
        laporanpembelian = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
//        laporanpembelian = new DefaultTableModel();
        tablehutang.setModel(laporanpembelian);

        laporanpembelian.addColumn("ID");
        laporanpembelian.addColumn("Nama Barang");
        laporanpembelian.addColumn("Tgl Beli");
        laporanpembelian.addColumn("harga");
        laporanpembelian.addColumn("Jumlah/Status");
//        laporanpembelian.addColumn("Status");
    }
    
    private void tabelLaporanHutang(){
        laporanhutang = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
//        laporanhutang = new DefaultTableModel();
        jTable5.setModel(laporanhutang);

        laporanhutang.addColumn("ID");
        laporanhutang.addColumn("Nama Barang");
        laporanhutang.addColumn("Tgl Beli");
        laporanhutang.addColumn("harga");
        laporanhutang.addColumn("Jumlah/Status");
    }
    
    private void tabelAnggaran(){
        anggaran = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
//        anggaran = new DefaultTableModel();
        tableanggaran.setModel(anggaran);

        anggaran.addColumn("ID");
        anggaran.addColumn("Keterangan anggaran");
        anggaran.addColumn("Tgl input");
        anggaran.addColumn("Jumlah Uang");
        anggaran.addColumn("Kategori");
        
    }
    
    private void tabelPembelian(){
        pembelian = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
//        pembelian = new DefaultTableModel();
        tableit.setModel(pembelian);

        pembelian.addColumn("ID");
        pembelian.addColumn("Nama Barang");
        pembelian.addColumn("Tgl Beli");
        pembelian.addColumn("Harga");
        pembelian.addColumn("Jumlah per unit");
    }
    
    private void tabelGaji(){
        gajikaryawan = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
//        gajikaryawan = new DefaultTableModel();
        tablegaji.setModel(gajikaryawan);

        gajikaryawan.addColumn("ID");
        gajikaryawan.addColumn("Nama Karyawan");
        gajikaryawan.addColumn("Jumlah gaji");
        gajikaryawan.addColumn("Tgl gaji");
    }
    
    public void exportToPDF(DefaultTableModel tableModel, String filePath, String title) {
    Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Phrase(title + "\n\n")); 

            PdfPTable table = new PdfPTable(tableModel.getColumnCount());

            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                table.addCell(new PdfPCell(new Phrase(tableModel.getColumnName(i))));
            }

            for (int row = 0; row < tableModel.getRowCount(); row++) {
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    table.addCell(new PdfPCell(new Phrase(tableModel.getValueAt(row, col).toString())));
                }
            }
            document.add(table);
            JOptionPane.showMessageDialog(this, "PDF berhasil dibuat di : " + filePath);
        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(this, "Error dalam membuat PDF : " + e.getMessage());
        } finally {
            document.close();
        }
    }

    public void openPDF(String filePath) {
        try {
            File pdfFile = new File(filePath);
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    JOptionPane.showMessageDialog(this, "Desktop tidak didukung. Tidak bisa membuka PDF.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "File PDF tidak ditemukan di : " + filePath);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuka PDF : " + e.getMessage());
        }
    }
    
    private void loadDataAnggaran() {
      anggaran.setRowCount(0);

      try {
          String sql = "SELECT * FROM input_pemasukkan";
          PreparedStatement ps = conn.prepareStatement(sql);
          ResultSet rs = ps.executeQuery();
          while (rs.next()) {
             anggaran.addRow(new Object[]{
             rs.getInt("id_anggaran"),
             rs.getString("keterangan_anggaran"),
             rs.getString("tgl_input_anggaran"),
             rs.getString("jumlah_uang_anggaran"),
             rs.getString("kategori"),
           });
          }
        } catch (SQLException e) {
           System.out.println("Error Load Data Anggaran" + e.getMessage());
        }
    }
    
    private void loadDataPemasukkan() {
      pemasukkan.setRowCount(0);

      try {
          String sql = "SELECT * FROM input_pemasukkan";
          PreparedStatement ps = conn.prepareStatement(sql);
          ResultSet rs = ps.executeQuery();
          while (rs.next()) {
             pemasukkan.addRow(new Object[]{
             rs.getInt("id_anggaran"),
             rs.getString("keterangan_anggaran"),
             rs.getString("tgl_input_anggaran"),
             rs.getString("jumlah_uang_anggaran"),
             rs.getString("kategori"),
           });
          }
        } catch (SQLException e) {
           System.out.println("Error Load Data Anggaran" + e.getMessage());
        }
    }
    
    private void loadDataLaporanPemasukkan() {
      laporanpemasukkan.setRowCount(0);

      try {
          String sql = "SELECT * FROM input_pemasukkan";
          PreparedStatement ps = conn.prepareStatement(sql);
          ResultSet rs = ps.executeQuery();
          while (rs.next()) {
             laporanpemasukkan.addRow(new Object[]{
             rs.getInt("id_anggaran"),
             rs.getString("keterangan_anggaran"),
             rs.getString("tgl_input_anggaran"),
             rs.getString("jumlah_uang_anggaran"),
             rs.getString("kategori"),
           });
          }
        } catch (SQLException e) {
           System.out.println("Error Load Data Anggaran" + e.getMessage());
        }
    }
    
    private void loadDataLaporanPembelian() {
      laporanpembelian.setRowCount(0);

      try {
          String sql = "SELECT * FROM pembelian";
          PreparedStatement ps = conn.prepareStatement(sql);
          ResultSet rs = ps.executeQuery();
          while (rs.next()) {
             laporanpembelian.addRow(new Object[]{
             rs.getInt("id_pemasukkan"),
             rs.getString("nama_barang"),
             rs.getString("tgl_masuk"),
             rs.getString("jumlah"),
             rs.getString("jumlah_beli"),
           });
          }
        } catch (SQLException e) {
           System.out.println("Error Load Data Anggaran" + e.getMessage());
        }
    }
    
    private void loadDataLaporanHutang() {
      laporanhutang.setRowCount(0);
      try {
          String sql = "SELECT * FROM pembelian";
          PreparedStatement ps = conn.prepareStatement(sql);
          ResultSet rs = ps.executeQuery();
          while (rs.next()) {
             laporanhutang.addRow(new Object[]{
             rs.getInt("id_pemasukkan"),
             rs.getString("nama_barang"),
             rs.getString("tgl_masuk"),
             rs.getString("jumlah"),
             rs.getString("jumlah_beli")
           });
          }
        } catch (SQLException e) {
           System.out.println("Error Load Data Laporan Hutang" + e.getMessage());
        }
    }
    
    private void loadDataPembelian() {
        pembelian.setRowCount(0);

        try {
            String sql = "SELECT * FROM pembelian";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               pembelian.addRow(new Object[]{
               rs.getInt("id_pemasukkan"),
               rs.getString("nama_barang"),
               rs.getString("tgl_masuk"),
               rs.getString("jumlah"),
               rs.getString("jumlah_beli")
             });
            }
        } catch (SQLException e) {
           System.out.println("Error Load Data Pembelian" + e.getMessage());
        }
    }
    
    private void loadDataGaji() {
        gajikaryawan.setRowCount(0);

        try {
            String sql = "SELECT * FROM gaji_karyawan";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               gajikaryawan.addRow(new Object[]{
               rs.getInt("id"),
               rs.getString("pembayaran"),
               rs.getString("jumlah_gaji"),
               rs.getString("tgl_gaji")
             });
            }
        } catch (SQLException e) {
           System.out.println("Error Load Data Gaji" + e.getMessage());
        }
    }
    
    private void saveDataAnggaran() {
        String Kategori = cbkategori.getSelectedItem().toString();
        try {
            String sql = "INSERT INTO input_pemasukkan (keterangan_anggaran, tgl_input_anggaran, jumlah_uang_anggaran, kategori) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtketerangan.getText());
            ps.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(tglinput.getDate()));
            ps.setString(3, txtjumlahuang.getText());
            ps.setString(4, Kategori);
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data anggaran telah ditambah!");
            loadDataAnggaran();
            loadDataPemasukkan();
            loadDataLaporanPemasukkan();
        } catch (SQLException e) {
            System.out.println("Error Save Data Anggaran" + e.getMessage());
        }
    }
    
    private void updateDataAnggaran() {
        try {
            String sql = "UPDATE input_pemasukkan SET keterangan_anggaran = ?, tgl_input_anggaran = ?, jumlah_uang_anggaran = ?, kategori = ? WHERE id_anggaran = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtketerangan.getText());
            ps.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(tglinput.getDate()));
            ps.setString(3, txtjumlahuang.getText());
            ps.setString(4, cbkategori.getSelectedItem().toString());
            ps.setInt(5, Integer.parseInt(txtid.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data anggaran telah diupdate!");
            loadDataAnggaran();
            loadDataPemasukkan();
            loadDataLaporanPemasukkan();
        }  catch (SQLException e) {
            System.out.println("Error Update Data Anggaran" + e.getMessage());
        }
    }
    
    private void deleteDataAnggaran() {
        try {
            String sql = "DELETE FROM input_pemasukkan WHERE id_anggaran = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(txtid.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data anggaran telah dihapus!");
            loadDataAnggaran();
            loadDataPemasukkan();
            loadDataLaporanPemasukkan();
        } catch (SQLException e) {
            System.out.println("Error Delete Data" + e.getMessage());
        }
    }
    
    private void cariAnggaran() {
        anggaran.setRowCount(0);  
        try {
            String query;
            PreparedStatement ps;
            String searchTerm = txtcariinput.getText().trim();
            if (searchTerm.isEmpty()) {
                loadDataAnggaran();  
                return;  
            } else {
                query = "SELECT * FROM input_pemasukkan WHERE tgl_input_anggaran LIKE ? OR kategori LIKE ?";
                ps = conn.prepareStatement(query);
                searchTerm = "%" + searchTerm + "%";
                ps.setString(1, searchTerm);  
                ps.setString(2, searchTerm);  
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                anggaran.addRow(new Object[]{
                    rs.getInt("id_anggaran"),
                    rs.getString("keterangan_anggaran"),
                    rs.getDate("tgl_input_anggaran"),  
                    rs.getString("jumlah_uang_anggaran"),
                    rs.getString("kategori")
                });
            }
        } catch (SQLException e) {
            System.out.println("Fail: " + e.getMessage());
        }
    }
    
    private void cariGaji() {
        gajikaryawan.setRowCount(0);  
        try {
            String query;
            PreparedStatement ps;
            String searchTerm = carigaji.getText().trim();
            if (searchTerm.isEmpty()) {
                loadDataGaji();  
                return;  
            } else {
                query = "SELECT * FROM gaji_karyawan WHERE pembayaran LIKE ? OR tgl_gaji LIKE ?";
                ps = conn.prepareStatement(query);
                searchTerm = "%" + searchTerm + "%";
                ps.setString(1, searchTerm);  
                ps.setString(2, searchTerm);  
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                gajikaryawan.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("pembayaran"),
                    rs.getDouble("jumlah_gaji"),  
                    rs.getString("tgl_gaji")
                });
            }
        } catch (SQLException e) {
            System.out.println("Fail: " + e.getMessage());
        }
    }
    
    
    private void cariPembelian() {
        pembelian.setRowCount(0);  
        try {
            String query;
            PreparedStatement ps;
            String searchTerm = txtcari.getText().trim();
            if (searchTerm.isEmpty()) {
                loadDataPembelian();  
                return;  
            } else {
                query = "SELECT * FROM pembelian WHERE nama_barang LIKE ? OR tgl_masuk LIKE ?";
                ps = conn.prepareStatement(query);
                searchTerm = "%" + searchTerm + "%";
                ps.setString(1, searchTerm);  
                ps.setString(2, searchTerm);  
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pembelian.addRow(new Object[]{
                    rs.getInt("id_pemasukkan"),
                    rs.getString("nama_barang"),
                    rs.getDate("tgl_masuk"),  
                    rs.getString("jumlah"),
                    rs.getString("jumlah_beli")
                });
            }
        } catch (SQLException e) {
            System.out.println("Fail: " + e.getMessage());
        }
    }
    
    private void cariLaporanPemasukkan() {
        laporanpemasukkan.setRowCount(0);  
        try {
            String query;
            PreparedStatement ps;
            String searchTerm = txtcarilaporanpemasukkan.getText().trim();
            if (searchTerm.isEmpty()) {
                loadDataAnggaran();
                loadDataLaporanPemasukkan();
                return;  
            } else {
                query = "SELECT * FROM input_pemasukkan WHERE tgl_input_anggaran LIKE ? OR kategori LIKE ?";
                ps = conn.prepareStatement(query);
                searchTerm = "%" + searchTerm + "%";
                ps.setString(1, searchTerm);  
                ps.setString(2, searchTerm);  
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                laporanpemasukkan.addRow(new Object[]{
                    rs.getInt("id_anggaran"),
                    rs.getString("keterangan_anggaran"),
                    rs.getDate("tgl_input_anggaran"),  
                    rs.getString("jumlah_uang_anggaran"),
                    rs.getString("kategori")
                });
            }
        } catch (SQLException e) {
            System.out.println("Fail: " + e.getMessage());
        }
    }
    
    private void cariLaporanPengeluaran() {
        laporanhutang.setRowCount(0);  
        try {
            String query;
            PreparedStatement ps;
            String searchTerm = txtcarilaporanPengeluaran.getText().trim();
            if (searchTerm.isEmpty()) {
                loadDataPembelian();  
                loadDataLaporanHutang();
                return;  
            } else {
                query = "SELECT * FROM pembelian WHERE nama_barang LIKE ? OR tgl_masuk LIKE ?";
                ps = conn.prepareStatement(query);
                searchTerm = "%" + searchTerm + "%";
                ps.setString(1, searchTerm);  
                ps.setString(2, searchTerm);  
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                laporanhutang.addRow(new Object[]{
                    rs.getInt("id_pemasukkan"),
                    rs.getString("nama_barang"),
                    rs.getDate("tgl_masuk"),  
                    rs.getString("jumlah"),
                    rs.getString("jumlah_beli")
                });
            }
        } catch (SQLException e) {
            System.out.println("Fail: " + e.getMessage());
        }
    }

    
    
//    private void saveDataPembelian() {
//        try {
//            String sql = "INSERT INTO pembelian (nama_barang, tgl_masuk, jumlah, jumlah_beli) VALUES (?, ?, ?, ?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, txtnamabarang.getText());
//            ps.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(tglpembelian.getDate()));
//            ps.setString(3, txtharga.getText());
//            ps.setString(4, txtJumlahBarang.getText());
//            ps.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Data pemasukkan telah ditambah!");
//            loadDataPembelian();
//            loadDataLaporanHutang();
//            loadDataLaporanPembelian();
//        } catch (SQLException e) {
//            System.out.println("Error Save Data Pembelian" + e.getMessage());
//        }
//    }
    
        private void saveDataPembelian() {
        String namaBarang = txtnamabarang.getText().trim();
        String harga = txtharga.getText().trim();
        String jumlahBarang = txtJumlahBarang.getText().trim();

        // Validasi jika input kosong
        if (namaBarang.isEmpty() || harga.isEmpty() || jumlahBarang.isEmpty() || tglpembelian.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Harap isi semua kolom terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String tglPembelian = new SimpleDateFormat("yyyy-MM-dd").format(tglpembelian.getDate());

            String sql = "INSERT INTO pembelian (nama_barang, tgl_masuk, jumlah, jumlah_beli) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, namaBarang);
            pst.setString(2, tglPembelian);
            pst.setString(3, harga);
            pst.setString(4, jumlahBarang);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data pembelian berhasil ditambahkan.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            loadDataPembelian();
            loadDataLaporanPembelian();
            loadDataLaporanHutang();
            txtnamabarang.setText("");
            txtharga.setText("");
            txtJumlahBarang.setText("");
            tglpembelian.setDate(null);

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Harga dan Jumlah Barang harus berupa angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(this, "Kesalahan saat menyimpan data: " + sqlEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
//    private void updateDataPembelian() {
//        try {
//            String sql = "UPDATE pembelian SET nama_barang = ?, tgl_masuk = ?, jumlah = ?, jumlah_beli = ? WHERE id_pemasukkan = ?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, txtnamabarang.getText());
//            ps.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(tglpembelian.getDate()));
//            ps.setString(3, txtharga.getText());
//            ps.setString(4, txtJumlahBarang.getText());
//            ps.setString(5, txtid1.getText());
//            ps.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Data pemasukkan telah diupdate!");
//            loadDataPembelian();
//            loadDataLaporanHutang();
//            loadDataLaporanPembelian();
//        }  catch (SQLException e) {
//            System.out.println("Error Update Data Pembelian" + e.getMessage());
//        }
//    }
        
    private void updateDataPembelian() {
        String namaBarang = txtnamabarang.getText().trim();
        String harga = txtharga.getText().trim();
        String jumlahBarang = txtJumlahBarang.getText().trim();

        // Validasi jika input kosong
        if (namaBarang.isEmpty() || harga.isEmpty() || jumlahBarang.isEmpty() || tglpembelian.getDate() == null || txtid1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap isi semua kolom terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String tglPembelian = new SimpleDateFormat("yyyy-MM-dd").format(tglpembelian.getDate());

            String sql = "UPDATE pembelian SET nama_barang = ?, tgl_masuk = ?, jumlah = ?, jumlah_beli = ? WHERE id_pemasukkan = ?";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, namaBarang);
            pst.setString(2, tglPembelian);
            pst.setString(3, harga);
            pst.setString(4, jumlahBarang);
            pst.setString(5, txtid1.getText());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data pembelian berhasil diupdate.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            loadDataPembelian();
            loadDataLaporanPembelian();
            loadDataLaporanHutang();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Harga dan Jumlah Barang harus berupa angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(this, "Kesalahan saat mengupdate data: " + sqlEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
//    private void deleteDataPembelian() {
//        try {
//            String sql = "DELETE FROM pembelian WHERE id_pemasukkan = ?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, Integer.parseInt(txtid1.getText()));
//            ps.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Data pembelian telah dihapus!");
//            loadDataPembelian();
//            loadDataLaporanHutang();
//            loadDataLaporanPembelian();
//        } catch (SQLException e) {
//            System.out.println("Error Delete Data" + e.getMessage());
//        }
//    }
    
    private void deleteDataPembelian() {
    // Validasi jika ID kosong
        if (txtid1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap pilih atau masukkan ID sebelum menghapus data!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String sql = "DELETE FROM pembelian WHERE id_pemasukkan = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtid1.getText()));
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data pembelian berhasil dihapus.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            loadDataPembelian();
            loadDataLaporanPembelian();
            loadDataLaporanHutang();
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(this, "Kesalahan saat menghapus data: " + sqlEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void saveDataBayarHutang() {
        try {
            String sql = "INSERT INTO pembelian (nama_barang, tgl_masuk, jumlah, jumlah_beli) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtnamabarang.getText());
            ps.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(tglpembelian.getDate()));
            ps.setString(3, txtharga.getText());
            ps.setString(4, txtJumlahBarang.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data bayar hutang telah ditambah!");
            loadDataLaporanPembelian();
            loadDataPembelian();
        } catch (SQLException e) {
            System.out.println("Error Save Data Bayar Hutang" + e.getMessage());
        }
    }
    
//    private void saveDataBayarHutangBaru() {
//        try {
//            String sql = "INSERT INTO hutang (nama_pembayaran, tgl_pembayaran, jumlah_hutang, jumlah_unit, status) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, txtpembayaran.getText());
//            ps.setString(2, txtjumlahunit.getText());
//            ps.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(tglpembayaran.getDate()));
//            ps.setString(4, txtbayarhutang.getText());
//            ps.setString(5, txtjumlahhutang.getText());
//            ps.setString(6, txtkembalian.getText());
//            ps.setString(7, txtstatushutang.getText());
//            ps.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Data bayar hutang Baru telah ditambah!");
//            loadDataLaporanHutang();
////            loadDataPembelian();
//        } catch (SQLException e) {
//            System.out.println("Error Save Data Bayar Hutang Baru" + e.getMessage());
//        }
//    }
    
    
    private void saveDataGaji() {
        String pembayaran = txtgaji.getText().trim();
        String jumlahGaji = txtjumlahgaji.getText().trim();

        if (pembayaran.isEmpty() || jumlahGaji.isEmpty() || tglgaji.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Harap isi semua kolom terlebih dahulu!");
            return;
        }

        try {
            String tglGaji = new SimpleDateFormat("yyyy-MM-dd").format(tglgaji.getDate());

            String sql = "INSERT INTO gaji_karyawan (pembayaran, jumlah_gaji, tgl_gaji) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, pembayaran);
            pst.setDouble(2, Double.parseDouble(jumlahGaji)); 
            pst.setString(3, tglGaji);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data gaji berhasil ditambahkan.");
            loadDataGaji();
            txtgaji.setText("");
            txtjumlahgaji.setText("");
            tglgaji.setDate(null);

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Jumlah Gaji harus berupa angka!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }


    private void updateDataGaji() {
    String id = txtidgaji.getText().trim();
    String pembayaran = txtgaji.getText().trim();
    String jumlahGaji = txtjumlahgaji.getText().trim();
    Date tglGaji = tglgaji.getDate();
    
    if (id.isEmpty() || pembayaran.isEmpty() || jumlahGaji.isEmpty() || tglGaji == null) {
        JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tglGajiString = sdf.format(tglGaji);

        String sql = "UPDATE gaji_karyawan SET pembayaran = ?, jumlah_gaji = ?, tgl_gaji = ? WHERE id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, pembayaran);
        pst.setDouble(2, Double.parseDouble(jumlahGaji)); 
        pst.setString(3, tglGajiString);
        pst.setInt(4, Integer.parseInt(id)); 
        
        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui.");
            loadDataGaji(); 
        } else {
            JOptionPane.showMessageDialog(this, "Data dengan ID " + id + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah gaji atau ID tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void deleteDataGaji() {
        String id = txtidgaji.getText();

        try {
            String sql = "DELETE FROM gaji_karyawan WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(id));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
            loadDataGaji();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error Kak : " + ex.getMessage());
        }
    }
    
//    private void searchDatagaji() {
//    String keyword = carigaji.getText().trim(); 
//
//    if (keyword.isEmpty()) {
//        JOptionPane.showMessageDialog(this, "Pencarian tidak boleh kosong!");
//        return;
//    }
//
//    try {
//        String sql = "SELECT * FROM gaji_karyawan WHERE pembayaran LIKE ?";
//        PreparedStatement pst = conn.prepareStatement(sql);
//        pst.setString(1, "%" + keyword + "%"); 
//
//        ResultSet rs = pst.executeQuery();
//        DefaultTableModel model = (DefaultTableModel) tablegaji.getModel();
//        model.setRowCount(0);
//        while (rs.next()) {
//            model.addRow(new Object[]{
//                rs.getInt("id"), 
//                rs.getString("pembayaran"), 
//                rs.getDouble("jumlah_gaji"), 
//                rs.getString("tgl_gaji") 
//            });
//        }
//        if (model.getRowCount() == 0) {
//            JOptionPane.showMessageDialog(this, "Data tidak ditemukan : " + keyword);
//        }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
//        }
//    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        atas = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bawah = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tengah = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel17 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        logout = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        btnprintlaporanpengeluaran = new javax.swing.JButton();
        btnprintlaporanpemasukkan = new javax.swing.JButton();
        btncarilaporanpemasukkan = new javax.swing.JButton();
        txtcarilaporanpemasukkan = new javax.swing.JTextField();
        btncarilaporanpengeluaran = new javax.swing.JButton();
        txtcarilaporanPengeluaran = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tambahinput = new javax.swing.JButton();
        updateinput = new javax.swing.JButton();
        deleteinput = new javax.swing.JButton();
        btnresetinput = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableanggaran = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        txtjumlahuang = new javax.swing.JTextField();
        tglinput = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        txtketerangan = new javax.swing.JTextField();
        btncariinput = new javax.swing.JButton();
        txtcariinput = new javax.swing.JTextField();
        btnprintinput = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        cbkategori = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        tambahit = new javax.swing.JButton();
        updateit = new javax.swing.JButton();
        deleteit = new javax.swing.JButton();
        btnreset = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtid1 = new javax.swing.JTextField();
        txtnamabarang = new javax.swing.JTextField();
        txtharga = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableit = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        tglpembelian = new com.toedter.calendar.JDateChooser();
        btncari = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        btnprint = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtJumlahBarang = new javax.swing.JTextField();
        tambahpembelian = new javax.swing.JButton();
        kurangpembelian = new javax.swing.JButton();
        btnbayarpembelian = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        bayarhutang = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtpembayaran = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        tglpembayaran = new com.toedter.calendar.JDateChooser();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablehutang = new javax.swing.JTable();
        btnprintpembayaran = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        txtjumlahunit = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtbayarhutang = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtjumlahhutang = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtkembalian = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        btntambahgaji = new javax.swing.JButton();
        btnupdategaji = new javax.swing.JButton();
        btndeletegaji = new javax.swing.JButton();
        btnresetgaji = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        txtidgaji = new javax.swing.JTextField();
        txtgaji = new javax.swing.JTextField();
        txtjumlahgaji = new javax.swing.JTextField();
        tglgaji = new com.toedter.calendar.JDateChooser();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablegaji = new javax.swing.JTable();
        btncarigaji = new javax.swing.JButton();
        carigaji = new javax.swing.JTextField();
        btnprintgaji = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        atas.setBackground(new java.awt.Color(218, 242, 204));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel1.setText("KEUANGAN PERUSAHAAN");
        atas.add(jLabel1);

        getContentPane().add(atas, java.awt.BorderLayout.PAGE_START);

        bawah.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel2.setText("Design by Vrilla");
        bawah.add(jLabel2);

        getContentPane().add(bawah, java.awt.BorderLayout.PAGE_END);

        tengah.setBackground(new java.awt.Color(51, 51, 51));

        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.RIGHT);

        jPanel17.setLayout(new java.awt.BorderLayout());

        jPanel19.setBackground(new java.awt.Color(223, 242, 235));

        jLabel29.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel29.setText("LAPORAN");
        jPanel19.add(jLabel29);

        jPanel17.add(jPanel19, java.awt.BorderLayout.PAGE_START);

        jPanel20.setBackground(new java.awt.Color(223, 242, 235));

        logout.setBackground(new java.awt.Color(160, 213, 235));
        logout.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        logout.setText("LOGOUT");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        jPanel20.add(logout);

        jPanel17.add(jPanel20, java.awt.BorderLayout.PAGE_END);

        jPanel21.setBackground(new java.awt.Color(243, 243, 224));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable4);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(jTable5);

        jLabel30.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel30.setText("Table Pemasukkan");

        jLabel31.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel31.setText("Tabel Pengeluaran");

        btnprintlaporanpengeluaran.setBackground(new java.awt.Color(160, 213, 235));
        btnprintlaporanpengeluaran.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnprintlaporanpengeluaran.setText("PRINT");
        btnprintlaporanpengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintlaporanpengeluaranActionPerformed(evt);
            }
        });

        btnprintlaporanpemasukkan.setBackground(new java.awt.Color(160, 213, 235));
        btnprintlaporanpemasukkan.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnprintlaporanpemasukkan.setText("PRINT");
        btnprintlaporanpemasukkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintlaporanpemasukkanActionPerformed(evt);
            }
        });

        btncarilaporanpemasukkan.setBackground(new java.awt.Color(160, 213, 235));
        btncarilaporanpemasukkan.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btncarilaporanpemasukkan.setText("Cari");
        btncarilaporanpemasukkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncarilaporanpemasukkanActionPerformed(evt);
            }
        });

        btncarilaporanpengeluaran.setBackground(new java.awt.Color(160, 213, 235));
        btncarilaporanpengeluaran.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btncarilaporanpengeluaran.setText("Cari");
        btncarilaporanpengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncarilaporanpengeluaranActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                    .addComponent(jScrollPane6)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnprintlaporanpemasukkan))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnprintlaporanpengeluaran))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(btncarilaporanpemasukkan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcarilaporanpemasukkan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(btncarilaporanpengeluaran)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcarilaporanPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncarilaporanpemasukkan)
                    .addComponent(txtcarilaporanpemasukkan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnprintlaporanpemasukkan, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncarilaporanpengeluaran)
                    .addComponent(txtcarilaporanPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(btnprintlaporanpengeluaran))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel17.add(jPanel21, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Laporan", jPanel17);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(223, 242, 235));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel4.setText(" ANGGARAN ");
        jPanel3.add(jLabel4);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(223, 242, 235));

        tambahinput.setBackground(new java.awt.Color(160, 213, 235));
        tambahinput.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        tambahinput.setText("TAMBAH");
        tambahinput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahinputActionPerformed(evt);
            }
        });
        jPanel4.add(tambahinput);

        updateinput.setBackground(new java.awt.Color(160, 213, 235));
        updateinput.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        updateinput.setText("UPDATE");
        updateinput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateinputActionPerformed(evt);
            }
        });
        jPanel4.add(updateinput);

        deleteinput.setBackground(new java.awt.Color(160, 213, 235));
        deleteinput.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        deleteinput.setText("DELETE");
        deleteinput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteinputActionPerformed(evt);
            }
        });
        jPanel4.add(deleteinput);

        btnresetinput.setBackground(new java.awt.Color(160, 213, 235));
        btnresetinput.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnresetinput.setText("RESET");
        btnresetinput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetinputActionPerformed(evt);
            }
        });
        jPanel4.add(btnresetinput);

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel5.setBackground(new java.awt.Color(243, 243, 224));

        jLabel5.setText("ID");

        tableanggaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableanggaran);

        jLabel6.setText("Keterangan Input");

        jLabel8.setText("Tgl Input");

        jLabel9.setText("Jumlah Uang");

        btncariinput.setBackground(new java.awt.Color(160, 213, 235));
        btncariinput.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btncariinput.setText("Cari");
        btncariinput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariinputActionPerformed(evt);
            }
        });

        btnprintinput.setBackground(new java.awt.Color(160, 213, 235));
        btnprintinput.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnprintinput.setText("Print");
        btnprintinput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintinputActionPerformed(evt);
            }
        });

        jLabel15.setText("Kategori");

        cbkategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Leaf Spring", "Coil Spring", "Invenstasi", "Donasi", " " }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel15))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtid, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(txtketerangan)
                            .addComponent(cbkategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tglinput, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtjumlahuang, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btncariinput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcariinput, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnprintinput)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtketerangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(tglinput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtjumlahuang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbkategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(38, 38, 38)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncariinput)
                    .addComponent(txtcariinput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnprintinput))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(177, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Input", jPanel1);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(223, 242, 235));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel10.setText("Pembelian Sparepart");
        jPanel6.add(jLabel10);

        jPanel2.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel7.setBackground(new java.awt.Color(223, 242, 235));

        tambahit.setBackground(new java.awt.Color(160, 213, 235));
        tambahit.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        tambahit.setText("TAMBAH");
        tambahit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahitActionPerformed(evt);
            }
        });
        jPanel7.add(tambahit);

        updateit.setBackground(new java.awt.Color(160, 213, 235));
        updateit.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        updateit.setText("UPDATE");
        updateit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateitActionPerformed(evt);
            }
        });
        jPanel7.add(updateit);

        deleteit.setBackground(new java.awt.Color(160, 213, 235));
        deleteit.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        deleteit.setText("DELETE");
        deleteit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteitActionPerformed(evt);
            }
        });
        jPanel7.add(deleteit);

        btnreset.setBackground(new java.awt.Color(160, 213, 235));
        btnreset.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnreset.setText("RESET");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });
        jPanel7.add(btnreset);

        jPanel2.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        jPanel8.setBackground(new java.awt.Color(243, 243, 224));

        jLabel11.setText("ID");

        jLabel12.setText("Nama Barang");

        jLabel14.setText("Jumlah");

        txtharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txthargaActionPerformed(evt);
            }
        });

        tableit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tableit);

        jLabel19.setText("Tgl Beli");

        btncari.setBackground(new java.awt.Color(160, 213, 235));
        btncari.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btncari.setText("Cari");
        btncari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariActionPerformed(evt);
            }
        });

        btnprint.setBackground(new java.awt.Color(160, 213, 235));
        btnprint.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnprint.setText("Print");
        btnprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel7.setText("TOTAL PEMASUKKAN");

        jLabel32.setText("Jumlah per unit");

        txtJumlahBarang.setEditable(false);
        txtJumlahBarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtJumlahBarang.setText("0");
        txtJumlahBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahBarangActionPerformed(evt);
            }
        });

        tambahpembelian.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tambahpembelian.setText("+");
        tambahpembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahpembelianActionPerformed(evt);
            }
        });

        kurangpembelian.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kurangpembelian.setText("-");
        kurangpembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kurangpembelianActionPerformed(evt);
            }
        });

        btnbayarpembelian.setBackground(new java.awt.Color(160, 213, 235));
        btnbayarpembelian.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnbayarpembelian.setText("Bayar");
        btnbayarpembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbayarpembelianActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btncari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnbayarpembelian)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnprint)))
                .addContainerGap())
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addGap(20, 20, 20)
                                .addComponent(kurangpembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(txtJumlahBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tambahpembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtnamabarang, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(txtid1))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tglpembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 95, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(tglpembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtid1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtnamabarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kurangpembelian)
                    .addComponent(jLabel32)
                    .addComponent(txtJumlahBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tambahpembelian))
                .addGap(46, 46, 46)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncari)
                    .addComponent(btnprint)
                    .addComponent(btnbayarpembelian))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(134, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel8, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Pembelian", jPanel2);

        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel10.setBackground(new java.awt.Color(223, 242, 235));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel16.setText("PEMBAYARAN HUTANG");
        jPanel10.add(jLabel16);

        jPanel9.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        jPanel11.setBackground(new java.awt.Color(223, 242, 235));

        bayarhutang.setBackground(new java.awt.Color(160, 213, 235));
        bayarhutang.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        bayarhutang.setText("BAYAR");
        bayarhutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarhutangActionPerformed(evt);
            }
        });
        jPanel11.add(bayarhutang);

        jPanel9.add(jPanel11, java.awt.BorderLayout.PAGE_END);

        jPanel12.setBackground(new java.awt.Color(243, 243, 224));

        jLabel17.setText("Pembayaran");

        txtpembayaran.setEditable(false);
        txtpembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpembayaranActionPerformed(evt);
            }
        });

        jLabel18.setText("Tgl pembayaran");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel20.setText("TABEL PEMBAYARAN");

        tablehutang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablehutang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablehutangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablehutang);

        btnprintpembayaran.setBackground(new java.awt.Color(160, 213, 235));
        btnprintpembayaran.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnprintpembayaran.setText("PRINT");
        btnprintpembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintpembayaranActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel21.setText("TABEL PEMASUKKAN");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable2);

        jLabel22.setText("Jumlah Unit");

        txtjumlahunit.setEditable(false);
        txtjumlahunit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjumlahunitActionPerformed(evt);
            }
        });

        jPanel18.setBackground(new java.awt.Color(153, 153, 153));

        jLabel13.setText("Bayar");

        txtbayarhutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbayarhutangActionPerformed(evt);
            }
        });

        jLabel27.setText("Jumlah Hutang");

        txtjumlahhutang.setEditable(false);

        jLabel28.setText("Kembalian");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(txtbayarhutang, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(18, 18, 18)
                                .addComponent(txtjumlahhutang)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtkembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtbayarhutang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtjumlahhutang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtkembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtjumlahunit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtpembayaran, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tglpembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnprintpembayaran, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtpembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txtjumlahunit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tglpembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(78, 78, 78)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(btnprintpembayaran)))
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.add(jPanel12, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Hutang", jPanel9);

        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel14.setBackground(new java.awt.Color(223, 242, 235));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel3.setText("GAJI KARYAWAN");
        jPanel14.add(jLabel3);

        jPanel13.add(jPanel14, java.awt.BorderLayout.PAGE_START);

        jPanel15.setBackground(new java.awt.Color(223, 242, 235));

        btntambahgaji.setBackground(new java.awt.Color(160, 213, 235));
        btntambahgaji.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btntambahgaji.setText("TAMBAH");
        btntambahgaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahgajiActionPerformed(evt);
            }
        });
        jPanel15.add(btntambahgaji);

        btnupdategaji.setBackground(new java.awt.Color(160, 213, 235));
        btnupdategaji.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnupdategaji.setText("UPDATE");
        btnupdategaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdategajiActionPerformed(evt);
            }
        });
        jPanel15.add(btnupdategaji);

        btndeletegaji.setBackground(new java.awt.Color(160, 213, 235));
        btndeletegaji.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btndeletegaji.setText("DELETE");
        btndeletegaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeletegajiActionPerformed(evt);
            }
        });
        jPanel15.add(btndeletegaji);

        btnresetgaji.setBackground(new java.awt.Color(160, 213, 235));
        btnresetgaji.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnresetgaji.setText("RESET");
        btnresetgaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetgajiActionPerformed(evt);
            }
        });
        jPanel15.add(btnresetgaji);

        jPanel13.add(jPanel15, java.awt.BorderLayout.PAGE_END);

        jPanel16.setBackground(new java.awt.Color(243, 243, 224));

        txtidgaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidgajiActionPerformed(evt);
            }
        });

        txtgaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtgajiActionPerformed(evt);
            }
        });

        jLabel23.setText("Jumlah Gaji");

        jLabel24.setText("Tgl Gaji");

        jLabel25.setText("ID");

        jLabel26.setText("Nama Karyawan");

        tablegaji.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tablegaji);

        btncarigaji.setBackground(new java.awt.Color(160, 213, 235));
        btncarigaji.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btncarigaji.setText("Cari");
        btncarigaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncarigajiActionPerformed(evt);
            }
        });

        btnprintgaji.setBackground(new java.awt.Color(160, 213, 235));
        btnprintgaji.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnprintgaji.setText("Print");
        btnprintgaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintgajiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(txtidgaji, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel23))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(txtgaji, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel24)))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tglgaji, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtjumlahgaji, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 34, Short.MAX_VALUE))
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(btncarigaji)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(carigaji, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnprintgaji)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtidgaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23))
                    .addComponent(jLabel25)
                    .addComponent(txtjumlahgaji, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtgaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26)
                        .addComponent(jLabel24))
                    .addComponent(tglgaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncarigaji)
                    .addComponent(carigaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnprintgaji))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(203, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel16, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Gaji Karyawan", jPanel13);

        javax.swing.GroupLayout tengahLayout = new javax.swing.GroupLayout(tengah);
        tengah.setLayout(tengahLayout);
        tengahLayout.setHorizontalGroup(
            tengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tengahLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        tengahLayout.setVerticalGroup(
            tengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tengahLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        getContentPane().add(tengah, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btncariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariActionPerformed
        // TODO add your handling code here:
        cariPembelian();
    }//GEN-LAST:event_btncariActionPerformed

    private void txtbayarhutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbayarhutangActionPerformed
                
//        int selectedRow = tablehutang.getSelectedRow();
//        if (selectedRow < 0) {
//            JOptionPane.showMessageDialog(this, "Silakan pilih data hutang dari tabel terlebih dahulu.");
//            return;
//        }
//
//        // Ambil data dari tabel
//        String idHutangStr = tablehutang.getValueAt(selectedRow, 0).toString(); // Asumsi kolom 0 adalah ID hutang
//        String jumlahHutangStr = tablehutang.getValueAt(selectedRow, 2).toString(); // Kolom jumlah hutang
//        String pembayaranStr = txtbayarhutang.getText(); // Input pembayaran dari JTextField
//
//        try {
//            // Validasi input jumlah hutang
//            if (jumlahHutangStr == null || !jumlahHutangStr.matches("\\d+(\\.\\d+)?")) {
//                JOptionPane.showMessageDialog(this, "Data hutang di tabel tidak valid.");
//                return;
//            }
//
//            // Konversi nilai ke angka
//            double jumlahHutang = Double.parseDouble(jumlahHutangStr);
//            double pembayaran = Double.parseDouble(pembayaranStr);
//
//            Connection conn = koneksi.getConnection();
//            if (conn == null) {
//                JOptionPane.showMessageDialog(this, "Koneksi ke database gagal.");
//                return;
//            }
//
//            if (pembayaran >= jumlahHutang) {
//                // Pembayaran mencukupi
//                double kembalian = pembayaran - jumlahHutang; // Hitung kembalian
//                JOptionPane.showMessageDialog(this, "Anda terbayarkan. Kembalian Anda: " + kembalian);
//                txtkembalian.setText(String.valueOf(kembalian)); // Tampilkan kembalian di JTextField
//
//                // Update tabel GUI (set hutang menjadi 0)
//                tablehutang.setValueAt("0", selectedRow, 2);
//
//                // Update database (status lunas)
//                String updateQuery = "UPDATE hutang SET jumlah_hutang = 0, status = 'Lunas' WHERE id_hutang = ?";
//                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
//                pstmt.setInt(1, Integer.parseInt(idHutangStr));
//                pstmt.executeUpdate();
//
//            } else {
//                // Pembayaran kurang
//                double sisaHutang = jumlahHutang - pembayaran;
//                JOptionPane.showMessageDialog(this, 
//                    "Jumlah hutang Anda masih kurang. Sisa hutang: " + sisaHutang);
//
//                // Update tabel GUI dengan sisa hutang
//                tablehutang.setValueAt(String.valueOf(sisaHutang), selectedRow, 2);
//
//                // Update database dengan sisa hutang
//                String updateQuery = "UPDATE hutang SET jumlah_hutang = ? WHERE id_hutang = ?";
//                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
//                pstmt.setDouble(1, sisaHutang);
//                pstmt.setInt(2, Integer.parseInt(idHutangStr));
//                pstmt.executeUpdate();
//
//                txtkembalian.setText("0"); // Tidak ada kembalian jika pembayaran kurang
//            }
//
//            // Bersihkan input pembayaran
//            txtbayarhutang.setText("");
//
//            conn.close(); // Tutup koneksi
//
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid untuk pembayaran.");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengakses database.");
//            e.printStackTrace();
//        }
            
//            int selectedRow = tablehutang.getSelectedRow();
//    if (selectedRow < 0) {
//        JOptionPane.showMessageDialog(this, "Silakan pilih data hutang dari tabel terlebih dahulu.");
//        return;
//    }
//
//    // Ambil data jumlah hutang dari tabel
//    String idHutangStr = tablehutang.getValueAt(selectedRow, 0).toString(); // Asumsi kolom 0 adalah ID hutang
//    String jumlahHutangStr = tablehutang.getValueAt(selectedRow, 3).toString(); // Kolom jumlah hutang
//    String pembayaranStr = txtbayarhutang.getText(); // Input pembayaran dari JTextField
//
//    try {
//        // Validasi jumlah hutang
//        if (jumlahHutangStr == null || !jumlahHutangStr.matches("\\d+(\\.\\d+)?")) {
//            JOptionPane.showMessageDialog(this, "Data hutang di tabel tidak valid.");
//            return;
//        }
//
//        // Konversi nilai dari String ke angka
//        double jumlahHutang = Double.parseDouble(jumlahHutangStr);
//        double pembayaran = Double.parseDouble(pembayaranStr);
//
//        // Koneksi ke database
//        Connection conn = koneksi.getConnection(); // Pastikan sudah ada kelas DatabaseConnection seperti sebelumnya
//        if (conn == null) {
//            return;
//        }
//
//        // Proses pembayaran
//        if (pembayaran >= jumlahHutang) {
//            // Jika pembayaran mencukupi atau lebih
//            double kembalian = pembayaran - jumlahHutang; // Hitung kembalian
//            JOptionPane.showMessageDialog(this, "Anda terbayarkan. Kembalian Anda: " + kembalian);
//            txtkembalian.setText(String.valueOf(kembalian)); // Tampilkan kembalian
//
//            // Update tabel (hutang menjadi 0)
//            tablehutang.setValueAt("0", selectedRow, 3);
//
//            // Update database (set hutang menjadi 0 dan status lunas)
//            String updateQuery = "UPDATE hutang SET jumlah_hutang = 0, status = 'Lunas' WHERE id_hutang = ?";
//            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
//            pstmt.setInt(1, Integer.parseInt(idHutangStr));
//            pstmt.executeUpdate();
//            
//        } else {
//            // Jika pembayaran kurang
//            double sisaHutang = jumlahHutang - pembayaran;
//            JOptionPane.showMessageDialog(this, 
//                "Jumlah hutang Anda masih kurang. Sisa hutang: " + sisaHutang);
//
//            // Update tabel dengan sisa hutang
//            tablehutang.setValueAt(String.valueOf(sisaHutang), selectedRow, 3);
//
//            // Update database dengan sisa hutang
//            String updateQuery = "UPDATE hutang SET jumlah_hutang = ? WHERE id_hutang = ?";
//            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
//            pstmt.setDouble(1, sisaHutang);
//            pstmt.setInt(2, Integer.parseInt(idHutangStr));
//            pstmt.executeUpdate();
//
//            txtkembalian.setText("0"); // Tidak ada kembalian jika pembayaran kurang
//        }
//
//        // Bersihkan input pembayaran
//        txtbayarhutang.setText("");
//
//        // Menutup koneksi
//        conn.close();
//
//    } catch (NumberFormatException e) {
//        JOptionPane.showMessageDialog(this, "Masukkan angka yang valid untuk pembayaran.");
//        e.printStackTrace(); // Untuk debugging jika diperlukan
//    } catch (SQLException e) {
//        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengakses database.");
//        e.printStackTrace();
//    }
        int selectedRow = tablehutang.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Silakan pilih data hutang dari tabel terlebih dahulu.");
            return;
        }

        String idHutangStr = tablehutang.getValueAt(selectedRow, 0).toString(); 
        String jumlahHutangStr = tablehutang.getValueAt(selectedRow, 3).toString(); 
        String pembayaranStr = txtbayarhutang.getText(); 

        try {
            if (jumlahHutangStr == null || !jumlahHutangStr.matches("\\d+(\\.\\d+)?")) {
                JOptionPane.showMessageDialog(this, "Data hutang di tabel tidak valid.");
                return;
            }

            double jumlahHutang = Double.parseDouble(jumlahHutangStr);
            double pembayaran = Double.parseDouble(pembayaranStr);

            Connection conn = koneksi.getConnection(); 
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Gagal terhubung ke database.");
                return;
            }

            if (pembayaran >= jumlahHutang) {
                double kembalian = pembayaran - jumlahHutang; 
                JOptionPane.showMessageDialog(this, "Pembayaran berhasil Kembalian Anda : " + kembalian);
                txtkembalian.setText(String.valueOf(kembalian)); 

                tablehutang.setValueAt("0", selectedRow, 3); 
                tablehutang.setValueAt("Lunas", selectedRow, 4); 

                String updateQuery = "UPDATE hutang SET jumlah_hutang = 0, status = 'Lunas' WHERE id_hutang = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setInt(1, Integer.parseInt(idHutangStr));
                pstmt.executeUpdate();

            } else {
                double sisaHutang = jumlahHutang - pembayaran;
                JOptionPane.showMessageDialog(this, 
                    "Pembayaran kurang Sisa hutang Anda : " + sisaHutang);

                tablehutang.setValueAt(String.valueOf(sisaHutang), selectedRow, 3); 
                tablehutang.setValueAt("Belum Lunas", selectedRow, 4); 

                String updateQuery = "UPDATE hutang SET jumlah_hutang = ? WHERE id_hutang = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setDouble(1, sisaHutang);
                pstmt.setInt(2, Integer.parseInt(idHutangStr));
                pstmt.executeUpdate();

                txtkembalian.setText("0"); 
            }

            txtbayarhutang.setText("");

            conn.close();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah yang valid untuk pembayaran.");
            e.printStackTrace(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengakses database.");
            e.printStackTrace();
        }


//            int selectedRow = tablehutang.getSelectedRow();
//    if (selectedRow < 0) {
//        JOptionPane.showMessageDialog(this, "Silakan pilih data hutang dari tabel terlebih dahulu.");
//        return;
//    }
//
//    // Ambil data jumlah hutang dari tabel
//    String jumlahHutangStr = (String) tablehutang.getValueAt(selectedRow, 3); // Kolom jumlah hutang
//    String pembayaranStr = txtbayarhutang.getText(); // Input pembayaran dari JTextField
//
//    try {
//        // Validasi jumlah hutang
//        if (jumlahHutangStr == null || !jumlahHutangStr.matches("\\d+(\\.\\d+)?")) {
//            JOptionPane.showMessageDialog(this, "Data hutang di tabel tidak valid.");
//            return;
//        }
//
//        // Konversi nilai dari String ke angka
//        double jumlahHutang = Double.parseDouble(jumlahHutangStr);
//        double pembayaran = Double.parseDouble(pembayaranStr);
//
//        // Proses pembayaran
//        if (pembayaran >= jumlahHutang) {
//            // Jika pembayaran mencukupi atau lebih
//            double kembalian = pembayaran - jumlahHutang; // Hitung kembalian
//            JOptionPane.showMessageDialog(this, "Anda terbayarkan. Kembalian Anda: " + kembalian);
//
//            txtkembalian.setText(String.valueOf(kembalian)); // Tampilkan kembalian
//
//            // Update tabel (hutang menjadi 0)
//            tablehutang.setValueAt("0", selectedRow, 3);
//        } else {
//            // Jika pembayaran kurang
//            double sisaHutang = jumlahHutang - pembayaran;
//            JOptionPane.showMessageDialog(this, 
//                "Jumlah hutang Anda masih kurang. Sisa hutang: " + sisaHutang);
//
//            // Update tabel dengan sisa hutang
//            tablehutang.setValueAt(String.valueOf(sisaHutang), selectedRow, 3);
//
//            txtkembalian.setText("0"); // Tidak ada kembalian jika pembayaran kurang
//        }
//
//        // Bersihkan input pembayaran
//        txtbayarhutang.setText("");
//
//    } catch (NumberFormatException e) {
//        JOptionPane.showMessageDialog(this, "Masukkan angka yang valid untuk pembayaran.");
//        e.printStackTrace();
//    }
    }//GEN-LAST:event_txtbayarhutangActionPerformed

    private void txtpembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpembayaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpembayaranActionPerformed

    private void tambahinputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahinputActionPerformed
        // TODO add your handling code here:
        saveDataAnggaran();
    }//GEN-LAST:event_tambahinputActionPerformed

    private void updateinputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateinputActionPerformed
        // TODO add your handling code here:
        updateDataAnggaran();
    }//GEN-LAST:event_updateinputActionPerformed

    private void deleteinputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteinputActionPerformed
        // TODO add your handling code here:
        deleteDataAnggaran();
    }//GEN-LAST:event_deleteinputActionPerformed

    private void btnprintinputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintinputActionPerformed
        // TODO add your handling code here:
        String outputPath = "Pemasukkan.pdf";
        exportToPDF(anggaran, "Pemasukkan.pdf", "Pemasukkan");
        openPDF(outputPath);
    }//GEN-LAST:event_btnprintinputActionPerformed

    private void txtidgajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidgajiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidgajiActionPerformed

    private void txtgajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtgajiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtgajiActionPerformed

    private void tambahitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahitActionPerformed
        // TODO add your handling code here:
        saveDataPembelian();
    }//GEN-LAST:event_tambahitActionPerformed

    private void updateitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateitActionPerformed
        // TODO add your handling code here:
        updateDataPembelian();
    }//GEN-LAST:event_updateitActionPerformed

    private void deleteitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteitActionPerformed
        // TODO add your handling code here:
        deleteDataPembelian();
    }//GEN-LAST:event_deleteitActionPerformed

    private void btntambahgajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahgajiActionPerformed
        // TODO add your handling code here:
        saveDataGaji();
    }//GEN-LAST:event_btntambahgajiActionPerformed

    private void btnupdategajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdategajiActionPerformed
        // TODO add your handling code here:
        updateDataGaji();
    }//GEN-LAST:event_btnupdategajiActionPerformed

    private void btndeletegajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeletegajiActionPerformed
        // TODO add your handling code here:
        deleteDataGaji();
    }//GEN-LAST:event_btndeletegajiActionPerformed

    private void btncarigajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncarigajiActionPerformed
        // TODO add your handling code here:
        cariGaji();
    }//GEN-LAST:event_btncarigajiActionPerformed

    private void btncariinputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariinputActionPerformed
        // TODO add your handling code here:
        cariAnggaran();
    }//GEN-LAST:event_btncariinputActionPerformed

    private void btnprintgajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintgajiActionPerformed
        // TODO add your handling code here:
        String outputPath = "Gaji Karyawan.pdf";
        exportToPDF(gajikaryawan, "Gaji Karyawan.pdf", "Gaji Karyawan");
        openPDF(outputPath);
    }//GEN-LAST:event_btnprintgajiActionPerformed

    private void tambahpembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahpembelianActionPerformed
        // TODO add your handling code here:
        int Harga = 1000000;
        int Jumlah = Integer.parseInt(txtJumlahBarang.getText());

        try {
            Jumlah++;
            
            txtJumlahBarang.setText(String.valueOf(Jumlah));
            
        int Total = Harga * Jumlah;
        
        txtharga.setText(String.valueOf(Total));
        
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Masukkan harga yang valid (hanya angka)!", 
                "Kesalahan", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tambahpembelianActionPerformed

    private void txthargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txthargaActionPerformed

    private void kurangpembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kurangpembelianActionPerformed
        // TODO add your handling code here:                                                
        int Harga = 1000000;
        int Jumlah = Integer.parseInt(txtJumlahBarang.getText());

        try {
            if (Jumlah <= 0){
                JOptionPane.showMessageDialog(null, "Jumlah tidak boleh kurang dari 0");
            } else {
                Jumlah--;
            }
            
            txtJumlahBarang.setText(String.valueOf(Jumlah));
            
        int Total = Harga * Jumlah;
        
        txtharga.setText(String.valueOf(Total));
            
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Masukkan harga yang valid (hanya angka)!", 
                "Kesalahan", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_kurangpembelianActionPerformed

    private void txtJumlahBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahBarangActionPerformed
        // TODO add your handling code here:
//        String jumlahText = txtJumlahBarang.getText();
//
//        try {
//            // Konversi jumlah barang menjadi integer
//            jumlahBarang = Integer.parseInt(jumlahText);
//
//            if (jumlahBarang <= 0) {
//                javax.swing.JOptionPane.showMessageDialog(this,
//                    "Jumlah barang harus lebih dari 0!",
//                    "Peringatan",
//                    javax.swing.JOptionPane.WARNING_MESSAGE);
//                jumlahBarang = 0;
//            }
//        } catch (NumberFormatException e) {
//            // Tampilkan pesan jika jumlah barang tidak valid
//            javax.swing.JOptionPane.showMessageDialog(this,
//                "Masukkan jumlah barang yang valid (hanya angka)!",
//                "Kesalahan",
//                javax.swing.JOptionPane.ERROR_MESSAGE);
//        }

    }//GEN-LAST:event_txtJumlahBarangActionPerformed

    private void btnbayarpembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbayarpembelianActionPerformed
        // TODO add your handling code here:
        saveDataBayarHutang();
        
    }//GEN-LAST:event_btnbayarpembelianActionPerformed

    private void tablehutangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablehutangMouseClicked
        // TODO add your handling code here:
//        int selectedRow = tablehutang.getSelectedRow();
//    
//        String pembayaran = (String) tablehutang.getValueAt(selectedRow, 1); 
//        String tgl_pembayaran = (String) tablehutang.getValueAt(selectedRow, 2); 
//        String  jumlah_hutang= (String) tablehutang.getValueAt(selectedRow, 3); 
//        String  jumlah_hutang1= (String) tablehutang.getValueAt(selectedRow, 4); 
//
//        txtpembayaran.setText(pembayaran);
////        txtTanggal.setText(tgl_pembayaran);
//        txtjumlahunit.setText(jumlah_hutang1);
//        txtjumlahhutang.setText(jumlah_hutang);
//        
//
//        // Validasi apakah tgl_pembayaran valid
//        if (tgl_pembayaran != null && tgl_pembayaran.matches("\\d{4}-\\d{2}-\\d{2}")) {
//            try {
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                java.util.Date date = dateFormat.parse(tgl_pembayaran);
//                tglpembayaran.setDate(date);
//            } catch (ParseException e) {
//                e.printStackTrace(); // Log jika parsing gagal
//            }
//        } else {
//            System.out.println("Data tanggal tidak valid: " + tgl_pembayaran);
//            tglpembayaran.setDate(null); // Atur nilai tanggal ke null
//        }
        int selectedRow = tablehutang.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Silakan pilih baris di tabel terlebih dahulu.");
            return;
        }

        String pembayaran = (String) tablehutang.getValueAt(selectedRow, 1); 
        String tgl_pembayaran = (String) tablehutang.getValueAt(selectedRow, 2); 
        String jumlah_hutang = (String) tablehutang.getValueAt(selectedRow, 3); 
        String jumlah_unit = (String) tablehutang.getValueAt(selectedRow, 4);

        txtpembayaran.setText(pembayaran);
        txtjumlahhutang.setText(jumlah_hutang);
        txtjumlahunit.setText(jumlah_unit);

        if (tgl_pembayaran != null && tgl_pembayaran.matches("\\d{4}-\\d{2}-\\d{2}")) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = dateFormat.parse(tgl_pembayaran);
                tglpembayaran.setDate(date); 
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Format tanggal tidak valid.");
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tanggal tidak valid.");
            tglpembayaran.setDate(null); 
        }
    }//GEN-LAST:event_tablehutangMouseClicked

    private void txtjumlahunitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjumlahunitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjumlahunitActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
        loginn log = new loginn();
            log.setVisible(true);
            this.dispose();
    }//GEN-LAST:event_logoutActionPerformed

    private void bayarhutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarhutangActionPerformed
        // TODO add your handling code here:
//        saveDataBayarHutangBaru();
    }//GEN-LAST:event_bayarhutangActionPerformed

    private void btnresetinputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetinputActionPerformed
        // TODO add your handling code here:
        txtid.setText("");
        txtketerangan.setText("");
        tglinput.setDate(null);
        txtjumlahuang.setText("");
        cbkategori.setSelectedItem(null);
    }//GEN-LAST:event_btnresetinputActionPerformed

    private void btnprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintActionPerformed
        // TODO add your handling code here:
        String outputPath = "Laporan Pembelian.pdf";
        exportToPDF(laporanpembelian, "Laporan Pembelian.pdf", "Laporan Pembelian");
        openPDF(outputPath);
    }//GEN-LAST:event_btnprintActionPerformed

    private void btnprintpembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintpembayaranActionPerformed
        // TODO add your handling code here:
        String outputPath = "Laporan Hutang.pdf";
        exportToPDF(laporanhutang, "Laporan Hutang.pdf", "Laporan Hutang");
        openPDF(outputPath);
    }//GEN-LAST:event_btnprintpembayaranActionPerformed

    private void btnprintlaporanpemasukkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintlaporanpemasukkanActionPerformed
        // TODO add your handling code here:
        String outputPath = "Tabel Pemasukkan.pdf";
        exportToPDF(anggaran, "Tabel Pemasukkan.pdf", "Tabel Pemasukkan");
        openPDF(outputPath);
    }//GEN-LAST:event_btnprintlaporanpemasukkanActionPerformed

    private void btnprintlaporanpengeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintlaporanpengeluaranActionPerformed
        // TODO add your handling code here:
        String outputPath = "Laporan Hutang.pdf";
        exportToPDF(laporanhutang, "Laporan Hutang.pdf", "Laporan Hutang");
        openPDF(outputPath);
    }//GEN-LAST:event_btnprintlaporanpengeluaranActionPerformed

    private void btncarilaporanpemasukkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncarilaporanpemasukkanActionPerformed
        // TODO add your handling code here:
        cariLaporanPemasukkan();
    }//GEN-LAST:event_btncarilaporanpemasukkanActionPerformed

    private void btncarilaporanpengeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncarilaporanpengeluaranActionPerformed
        // TODO add your handling code here:
        cariLaporanPengeluaran();
    }//GEN-LAST:event_btncarilaporanpengeluaranActionPerformed

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        // TODO add your handling code here:
        txtid1.setText("");
        txtnamabarang.setText("");
        txtJumlahBarang.setText("0");
        tglpembelian.setDate(null);
        txtharga.setText("");
    }//GEN-LAST:event_btnresetActionPerformed

    private void btnresetgajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetgajiActionPerformed
        // TODO add your handling code here:
        txtidgaji.setText("");
        txtgaji.setText("");
        txtjumlahgaji.setText("");
        tglgaji.setDate(null);
    }//GEN-LAST:event_btnresetgajiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel atas;
    private javax.swing.JPanel bawah;
    private javax.swing.JButton bayarhutang;
    private javax.swing.JButton btnbayarpembelian;
    private javax.swing.JButton btncari;
    private javax.swing.JButton btncarigaji;
    private javax.swing.JButton btncariinput;
    private javax.swing.JButton btncarilaporanpemasukkan;
    private javax.swing.JButton btncarilaporanpengeluaran;
    private javax.swing.JButton btndeletegaji;
    private javax.swing.JButton btnprint;
    private javax.swing.JButton btnprintgaji;
    private javax.swing.JButton btnprintinput;
    private javax.swing.JButton btnprintlaporanpemasukkan;
    private javax.swing.JButton btnprintlaporanpengeluaran;
    private javax.swing.JButton btnprintpembayaran;
    private javax.swing.JButton btnreset;
    private javax.swing.JButton btnresetgaji;
    private javax.swing.JButton btnresetinput;
    private javax.swing.JButton btntambahgaji;
    private javax.swing.JButton btnupdategaji;
    private javax.swing.JTextField carigaji;
    private javax.swing.JComboBox<String> cbkategori;
    private javax.swing.JButton deleteinput;
    private javax.swing.JButton deleteit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JButton kurangpembelian;
    private javax.swing.JButton logout;
    private javax.swing.JTable tableanggaran;
    private javax.swing.JTable tablegaji;
    private javax.swing.JTable tablehutang;
    private javax.swing.JTable tableit;
    private javax.swing.JButton tambahinput;
    private javax.swing.JButton tambahit;
    private javax.swing.JButton tambahpembelian;
    private javax.swing.JPanel tengah;
    private com.toedter.calendar.JDateChooser tglgaji;
    private com.toedter.calendar.JDateChooser tglinput;
    private com.toedter.calendar.JDateChooser tglpembayaran;
    private com.toedter.calendar.JDateChooser tglpembelian;
    private javax.swing.JTextField txtJumlahBarang;
    private javax.swing.JTextField txtbayarhutang;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtcariinput;
    private javax.swing.JTextField txtcarilaporanPengeluaran;
    private javax.swing.JTextField txtcarilaporanpemasukkan;
    private javax.swing.JTextField txtgaji;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtid1;
    private javax.swing.JTextField txtidgaji;
    private javax.swing.JTextField txtjumlahgaji;
    private javax.swing.JTextField txtjumlahhutang;
    private javax.swing.JTextField txtjumlahuang;
    private javax.swing.JTextField txtjumlahunit;
    private javax.swing.JTextField txtkembalian;
    private javax.swing.JTextField txtketerangan;
    private javax.swing.JTextField txtnamabarang;
    private javax.swing.JTextField txtpembayaran;
    private javax.swing.JButton updateinput;
    private javax.swing.JButton updateit;
    // End of variables declaration//GEN-END:variables
}
