# 💸 Simulasi Transaksi **Manhwa Store**
Audina Jelita - F1D02310039
---

## 🧩 **Summary**

Project ini merupakan latihan untuk mata kuliah **Pemrograman Berorientasi Objek (PBO)** dengan topik **Thread dan JDBC (Database Transaction)**.  
Pada project ini, saya membuat *simulasi transaksi pembelian manhwa* yang memanfaatkan dua komponen utama:

- 🧠 **`Transaction`** → merepresentasikan proses pembelian (setiap transaksi berjalan di thread terpisah).  
- 💾 **`DatabaseConnection`** → menyediakan koneksi ke database **MySQL** menggunakan **JDBC**.

> Tujuan utama dari project ini adalah untuk memperlihatkan bagaimana *konkurensi dan integritas data* dapat dijaga menggunakan **thread di Java** serta **manajemen transaksi (commit & rollback)** dalam database.

---

## 📂 **Struktur Proyek**

manhwa-store/
│
├─ lib/                         # JDBC driver MySQL
├─ src/
│   ├─ DatabaseConnection.java   # Menyediakan koneksi ke database MySQL
│   ├─ Transaction.java          # Menangani proses transaksi (Producer-like task)
│   ├─ Main.java                 # Menjalankan simulasi multithread transaksi
│   └─ TestConnection.java       # (Opsional) Untuk mengetes koneksi ke database
└─ out/


---

## ⚙️ **Alur Kerja Program**

### 1️⃣ Inisialisasi
Program dimulai dari `Main`.  
Beberapa objek **Transaction** dibuat dengan ID dan jumlah pembelian berbeda.  
Setiap transaksi dijalankan di **thread** terpisah menggunakan `Thread.start()`.

---

### 2️⃣ Proses Transaksi
Setiap thread `Transaction` akan:
1. Membuka koneksi ke database melalui `DatabaseConnection`.
2. Mengatur *transaction isolation level* menjadi:
   ```java
   connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

melakukan query:
SELECT stock FROM manhwa WHERE id = ? FOR UPDATE
untuk mengunci baris data manhwa yang akan diperbarui.

Validasi dan pembaruan stok
Jika stok mencukupi → stok dikurangi dan transaksi di-commit.
Jika stok tidak cukup → transaksi di-rollback agar data tetap konsisten.

DatabaseConnection
Menghubungkan Java dengan MySQL melalui JDBC.

Menggunakan URL:
jdbc:mysql://localhost:3306/manhwa_store?useSSL=false&allowPublicKeyRetrieval=true

Output terminal
Menampilkan hasil setiap transaksi:
Transaksi berhasil untuk manhwa 1: 3 dibeli.
Transaksi gagal untuk manhwa 1: stok tidak cukup.

Program selesai

Setelah semua thread Transaction selesai dijalankan, program berakhir.

🧰 Cara Menjalankan Program
1️⃣ Siapkan Database di MySQL (phpMyAdmin)

Jalankan query berikut untuk membuat database dan tabel:

CREATE DATABASE IF NOT EXISTS manhwa_store;
USE manhwa_store;

CREATE TABLE IF NOT EXISTS manhwa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100),
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL
);

INSERT INTO manhwa (title, author, price, stock) VALUES
('Solo Leveling', 'Chugong', 75000, 10),
('Tower of God', 'SIU', 80000, 8),
('Noblesse', 'Son Jeho', 70000, 5),
('The Beginning After The End', 'TurtleMe', 90000, 12);

2️⃣ Compile source code
javac -cp ".;lib/mysql-connector-j-9.5.0.jar" -d out src/*.java

3️⃣ Jalankan program
java -cp "out;lib/mysql-connector-j-9.5.0.jar" src.Main

🧠 Contoh Output
Transaksi berhasil untuk manhwa 1: 3 dibeli.
Transaksi gagal untuk manhwa 1: stok tidak cukup.
Transaksi berhasil untuk manhwa 2: 2 dibeli.

💡 Konsep yang Dipelajari

Threading di Java (Runnable, Thread)
JDBC Connection & PreparedStatement
Transaction Management (commit, rollback)
Isolation Level (READ_COMMITTED)
Concurrency Control dengan FOR UPDATE
