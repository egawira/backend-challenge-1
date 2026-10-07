# Banking Live Coding Interview

Projek Spring Boot 3.2 + Java 17.

Database menggunakan **H2 in-memory** dengan sample data yang sudah disediakan.

## Prasyarat

- Java 17 atau lebih baru
- Terminal / command line
- (Opsional) Maven 3.8+ jika tidak ingin menggunakan Maven Wrapper

## Cara Menjalankan Aplikasi

Projek ini sudah dilengkapi Maven Wrapper. Jalankan perintah berikut dari root folder ini:

```bash
./mvnw spring-boot:run
```

Di Windows (Command Prompt / PowerShell):

```bash
mvnw.cmd spring-boot:run
```

Jika kamu ingin menggunakan Maven yang sudah terinstall:

```bash
mvn spring-boot:run
```

Setelah berjalan, aplikasi tersedia di:

```
http://localhost:8081
```

## H2 Console

H2 Console bisa diakses di:

```
http://localhost:8081/h2-console
```

Gunakan JDBC URL berikut untuk login:

```
jdbc:h2:mem:bankingdb
```

Username: `sa`  
Password: (kosong)

## Sample Data

Saat aplikasi berjalan, tabel `accounts` sudah terisi dengan 3 rekening:

| account_number | account_holder_name | balance   | account_type |
|----------------|---------------------|-----------|--------------|
| 1111111111     | Alice Wijaya        | 200000.00 | CURRENT      |
| 2222222222     | Bob Santoso         | 50000.00  | SAVINGS      |
| 3333333333     | Charlie Tan         | 100000.00 | SAVINGS      |

## Endpoint yang Harus Bekerja

Setelah semua TODO diselesaikan, endpoint berikut harus bisa digunakan:

### 1. Membuat rekening baru

```bash
curl -X POST http://localhost:8081/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "accountNumber": "4444444444",
    "accountHolderName": "Diana Putri",
    "initialBalance": 75000,
    "accountType": "SAVINGS"
  }'
```

Response yang diharapkan: HTTP 201 Created dengan body `ApiResponse<Account>`.

### 2. Mengambil data rekening

```bash
curl http://localhost:8081/api/accounts/1111111111
```

Response yang diharapkan: HTTP 200 OK dengan data rekening.

### 3. Deposit

```bash
curl -X POST http://localhost:8081/api/accounts/2222222222/deposit \
  -H "Content-Type: application/json" \
  -d '{"amount": 25000}'
```

Response yang diharapkan: HTTP 200 OK dengan saldo baru 75000.

### 4. Withdraw

```bash
curl -X POST http://localhost:8081/api/accounts/1111111111/withdraw \
  -H "Content-Type: application/json" \
  -d '{"amount": 50000}'
```

Response yang diharapkan: HTTP 200 OK dengan saldo baru 150000.

### 5. Transfer antar rekening

```bash
curl -X POST http://localhost:8081/api/accounts/1111111111/transfer \
  -H "Content-Type: application/json" \
  -d '{
    "toAccountNumber": "2222222222",
    "amount": 30000
  }'
```

Response yang diharapkan: HTTP 200 OK dengan pesan sukses.

## Cara Menjalankan Test

```bash
./mvnw test
```

## Instruksi Pengerjaan

Buka file `TASK.md` untuk melihat daftar lengkap part yang harus diselesaikan.
