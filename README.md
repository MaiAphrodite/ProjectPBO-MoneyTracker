# Money Tracker

> Aplikasi desktop Java Swing untuk mencatat pemasukan, pengeluaran, dan rencana bulanan

[![License](https://img.shields.io/badge/license-MIT-2ea44f?style=flat-square)](LICENSE)
[![Stack](https://img.shields.io/badge/stack-Java%20%7C%20Swing%20%7C%20Maven-000?style=flat-square)](#teknologi)

Money Tracker adalah final project mata kuliah Pemrograman Berorientasi Objek
(kelompok 3). Aplikasi ini berjalan sebagai jendela Swing dengan tiga halaman:
dashboard, input transaksi, dan riwayat bulanan. Data disimpan ke berkas serialisasi
Java, bukan ke database, dan laporan bulanan bisa diekspor ke PDF memakai iText.

Ini proyek kuliah, bukan produk. Cakupannya satu pengguna, satu rencana aktif, dan data
lokal di komputer yang menjalankannya.

## Fitur

- **Dashboard**: saldo saat ini, total pemasukan dan pengeluaran bulan berjalan, status
  rencana bulan ini, dan lima transaksi terakhir.
- **Tambah transaksi**: isi tanggal (format `YYYY-MM-DD`), tipe `Income` atau `Expense`,
  kategori, nominal, dan deskripsi.
- **Kategori**: pilihan bawaan Food, Transport, Utilities, Entertainment, dan Other,
  dan kolomnya bisa diketik untuk kategori lain.
- **Rencana bulanan**: simpan bulan (`YYYY-MM`) dan target nominal. Progres rencana
  bertambah dari setiap pengeluaran pada bulan yang sama, dan muncul peringatan saat
  pengeluaran melewati target.
- **Riwayat**: pilih bulan dari dropdown yang diisi dari tanggal transaksi, lalu lihat
  semua transaksi bulan itu.
- **Ekspor PDF**: tombol Export to PDF menulis `Transaction_History_<bulan>.pdf` berisi
  tabel ID, tanggal, tipe, kategori, nominal, dan deskripsi.
- **Penyimpanan**: transaksi dan rencana disimpan ke `moneytracker_data.ser` saat jendela
  ditutup, lalu dibaca lagi saat aplikasi dijalankan.

## Teknologi

| Lapisan | Pilihan |
|---------|---------|
| Bahasa | Java 22 (`maven.compiler.source` dan `target` di `moneytracker/pom.xml`) |
| Build | Maven, packaging `jar`, artifact `moneytracker:1.0-SNAPSHOT` |
| Antarmuka | Java Swing (`JFrame`, `CardLayout`, `JComboBox`, `JTextArea`) |
| Laporan | iTextPDF 5.5.13.4 |
| Data | Serialisasi Java (`ObjectOutputStream`) ke `moneytracker_data.ser` |

## Struktur proyek

```text
moneytracker/
  pom.xml
  src/main/java/com/lemai/moneytracker/
    MoneyTracker.java      main class, penyimpanan statis transaksi dan rencana
    MoneyTrackerGUI.java   jendela Swing, dashboard, form, riwayat, ekspor PDF
    Transaction.java       model transaksi
    Plan.java              model rencana bulanan
    Category.java          model kategori
    Report.java            laporan total lewat console
```

## Cara menjalankan

Prasyarat: JDK 22 atau lebih baru dan Maven 3.9 atau lebih baru.

```bash
cd moneytracker
mvn -q compile
mvn -q exec:java -Dexec.mainClass=com.lemai.moneytracker.MoneyTrackerGUI
```

`exec:java` memakai plugin Maven standar, jadi pada eksekusi pertama Maven akan
mengunduh plugin tersebut. Proyek ini juga bisa dibuka langsung di NetBeans atau
IntelliJ IDEA sebagai proyek Maven, lalu jalankan class `MoneyTrackerGUI`.

Catatan: `exec.mainClass` di `pom.xml` berisi `com.lemai.moneytracker.Moneytracker`,
sedangkan class yang ada bernama `MoneyTracker`. Karena itu class-nya disebut eksplisit
pada perintah di atas.

## Laporan

Dokumen laporan dan refleksi kelompok ada di root repositori:

| Dokumen | Isi |
|---------|-----|
| [LAPORAN PROJECT AKHIR PBO KELOMPOK 3.pdf](https://github.com/MaiAphrodite/ProjectPBO-MoneyTracker/blob/main/LAPORAN%20PROJECT%20AKHIR%20PBO%20KELOMPOK%203.pdf) | laporan akhir proyek |
| [Refleksi Ide Aplikasi Money Tracker Diaz.pdf](https://github.com/MaiAphrodite/ProjectPBO-MoneyTracker/blob/main/Refleksi%20Ide%20Aplikasi%20Money%20Tracker%20Diaz.pdf) | refleksi ide aplikasi |
| [Gema Rizqi Mulia Akbar_Refleksi diri projek PBO.pdf](https://github.com/MaiAphrodite/ProjectPBO-MoneyTracker/blob/main/Gema%20Rizqi%20Mulia%20Akbar_Refleksi%20diri%20projek%20PBO.pdf) | refleksi anggota |
| [Armando Firlian_Refleksi Final Project PBO.pdf](https://github.com/MaiAphrodite/ProjectPBO-MoneyTracker/blob/main/Armando%20Firlian_Refleksi%20Final%20Project%20PBO.pdf) | refleksi anggota |
| [Meily Adennia_2310506013_Refleksi Final Project PBO.pdf](https://github.com/MaiAphrodite/ProjectPBO-MoneyTracker/blob/main/Meily%20Adennia_2310506013_Refleksi%20Final%20Project%20PBO.pdf) | refleksi anggota |
| [Roekhan Dani Maulana_Refleksi Final Project PBO.pdf](https://github.com/MaiAphrodite/ProjectPBO-MoneyTracker/blob/main/Roekhan%20Dani%20Maulana_Refleksi%20Final%20Project%20PBO.pdf) | refleksi anggota |

## Anggota kelompok

- Gema Rizqi Mulia A. (2310506008)
- Meily Adennia (2310506013)
- Armando Firlian Ihza Y. (2310506016)
- Roekhan Dani Maulana (2320506020)
- Ridho Diaz Pranantyo (2320506024)

## Catatan dan batasan

- Input tidak divalidasi. Nominal yang kosong atau bukan angka akan melempar
  `NumberFormatException`, dan tanggal hanya diperiksa lewat `startsWith` pada bulan.
- Hanya ada satu rencana aktif pada satu waktu, dan rencana disimpan di berkas yang
  sama dengan transaksi.
- `Category` dan `Report` ada di kode, tetapi belum dipakai oleh GUI. Kategori transaksi
  disimpan sebagai teks.
- Data hanya ada di `moneytracker_data.ser` pada direktori kerja. Hapus berkas itu untuk
  mengosongkan data.
- Folder `target/` berisi hasil build Maven dan tidak dilacak git.

## Lisensi

MIT, lihat [LICENSE](LICENSE). Copyright (c) 2026 Mai.
