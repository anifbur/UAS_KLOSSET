
# UAS Pemrograman Berorientasi Obyek 2

**Mata Kuliah**: Pemrograman Berorientasi Obyek 2
**Dosen Pengampu**: Muhammad Ikhwan Fathulloh

---

## Profil

**Kelompok**: <br>
Anif Burhanudin - 23552011075  <br>
Muhammad Rijal - 23552011138 <br>
Ikmal Khoerudin - 23552011373 <br>

---

## Studi Kasus: Sistem Manajemen Aset Fullstack (Spring Boot + Thymeleaf)

### **Judul Studi Kasus**

Sistem Manajemen Aset (Asset Management System)

---

## **Penjelasan Studi Kasus**

Universitas Teknologi Bandung (UTB) membutuhkan sebuah sistem berbasis web untuk mencatat, memonitor, dan mengelola data aset mereka. Sistem ini akan digunakan oleh staf inventaris untuk:

* Menambahkan data aset baru.
* Melakukan pengecekan stok fisik (stock opname) dan membandingkannya dengan data sistem.
* Mengekspor laporan stock opname ke PDF.
* Memastikan akurasi stok dan historinya.
* Memastikan jumlah pajak yang harus di bayar atau total jumlah pajak

Sistem dibangun menggunakan teknologi **Java Spring Boot** untuk backend dan **Thymeleaf** untuk frontend, mengikuti arsitektur **MVC (Model-View-Controller)**.

---

## **Penjelasan 4 Pilar OOP dalam Studi Kasus**

### **1. Inheritance (Pewarisan)**

Dalam sistem ini, inheritance digunakan melalui pewarisan interface `JpaRepository` dari Spring Data JPA.

Contoh:

```java
public interface AssetRepository extends JpaRepository<Asset, Long> {}
public interface StockOpnameRepository extends JpaRepository<StockOpname, Long> {}
```

Dengan mewarisi `JpaRepository`, repository secara otomatis memiliki fungsi CRUD (Create, Read, Update, Delete) tanpa perlu membuat method secara manual.

---

### **2. Encapsulation (Enkapsulasi)**

Setiap entity seperti `Asset` dan `StockOpname` memiliki atribut yang dibuat private dan hanya bisa diakses melalui getter dan setter.

Contoh:

```java
@Entity
public class Asset {
    @Id
    private Long id;

    private String namaAset;
    private Integer qty;

    // Getter dan Setter disediakan agar atribut tidak bisa diakses langsung dari luar
}
```

Hal ini menjaga integritas data dan membuat kode lebih aman serta modular.

---

### **3. Polymorphism (Polimorfisme)**

Polimorfisme diterapkan dalam sistem melalui interface dan dependency injection. Controller dan service bergantung pada interface alih-alih implementasi spesifik.

Contoh penggunaan:

```java
@Autowired
private StockOpnameRepository stockOpnameRepository;
```

Spring secara otomatis meng-inject implementasi yang sesuai, memungkinkan kita untuk mengganti implementasi (misal saat testing) tanpa mengubah kode utama.

---

### **4. Abstraction (Abstraksi)**

Abstraksi digunakan untuk menyembunyikan kompleksitas dari pengguna. Contohnya, kita hanya perlu memanggil method `findByTanggal` untuk mengambil data tertentu tanpa mengetahui query SQL di belakangnya.

Contoh:

```java
List<StockOpname> findByTanggal(LocalDate tanggal);
```

Kita tidak tahu detail query-nya, tapi cukup memanggil method ini dan Spring Data akan mengeksekusi query berdasarkan nama method tersebut.

---

## **Demo Proyek**

**GitHub**: https://github.com/anifbur/UAS_KLOSSET
**Google Drive**: https://drive.google.com/drive/folders/1dySVN014VzHSIFiOrEDYu5APXRSZ7VIX?usp=sharing

