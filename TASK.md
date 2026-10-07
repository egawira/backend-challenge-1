# Tugas Live Coding

Lengkapi semua bagian yang ditandai `// TODO Part X` agar aplikasi banking sederhana ini bisa berjalan sesuai ekspektasi.

## Acceptance Criteria Umum

- Aplikasi bisa dijalankan dengan `./mvnw spring-boot:run` tanpa error.
- Semua endpoint mengembalikan response dalam format `ApiResponse<T>`.
- Validasi input aktif menggunakan Jakarta Validation (`jakarta.validation.constraints`).
- Error ditangani oleh `GlobalExceptionHandler` dan dibungkus dalam `ApiResponse`.
- Semua perhitungan uang menggunakan `BigDecimal`.
- Database H2 sudah terisi sample data saat aplikasi start.

---

## Part 1: Model, DTO, dan Repository

### Model (`model/Account.java`)

Ubah `Account` menjadi entity JPA yang memetakan ke tabel `accounts`:

- `accountNumber`: primary key, `String`, maksimal 20 karakter.
- `accountHolderName`: `String`, maksimal 100 karakter, tidak boleh null.
- `balance`: `BigDecimal`, `precision = 19, scale = 2`, tidak boleh null.
- `accountType`: `String`, maksimal 20 karakter, tidak boleh null.

Tambahkan default constructor, constructor dengan semua field, getter, dan setter.

### Repository (`repository/AccountRepository.java`)

Ubah `AccountRepository` dari class biasa menjadi interface Spring Data JPA:

```java
public interface AccountRepository extends JpaRepository<Account, String> {
}
```

Dengan begitu, Spring Boot akan otomatis menyediakan method seperti `save`, `findById`, dan `existsById`.

### DTO

Lengkapi file-file berikut dengan field, validasi, getter, dan setter:

**`dto/AccountRequest.java`**
- `accountNumber`: tidak boleh blank, panjang 10-20 karakter.
- `accountHolderName`: tidak boleh blank, panjang 3-100 karakter.
- `initialBalance`: tidak boleh null, nilai minimal 0.
- `accountType`: tidak boleh blank.

**`dto/DepositRequest.java`**
- `amount`: tidak boleh null, nilai harus lebih besar dari 0.

**`dto/TransferRequest.java`**
- `toAccountNumber`: tidak boleh blank.
- `amount`: tidak boleh null, nilai harus lebih besar dari 0.

**`dto/ApiResponse.java`**
- Generic class dengan field `success` (boolean), `message` (String), dan `data` (T).
- Sediakan constructor untuk semua field.
- Sediakan static helper methods `success(String message, T data)` dan `error(String message)`.

---

## Part 2: Service Layer — Create, Get, Deposit, Withdraw

File: `service/AccountService.java`

### `createAccount(AccountRequest request)`

- Buat objek `Account` dari `AccountRequest`.
- Gunakan `BigDecimal.ZERO` jika `initialBalance` null.
- Log pembuatan akun.
- Simpan ke database melalui `accountRepository.save`.
- Kembalikan objek `Account` yang tersimpan.

### `getAccount(String accountNumber)`

- Cari akun berdasarkan `accountNumber` menggunakan `accountRepository.findById`.
- Jika tidak ditemukan, lempar `AccountNotFoundException`.
- Kembalikan objek `Account`.

### `deposit(String accountNumber, BigDecimal amount)`

- Validasi `amount` tidak null dan lebih besar dari 0. Jika tidak, lempar `IllegalArgumentException`.
- Ambil akun via `getAccount`.
- Tambahkan `amount` ke `balance` menggunakan `BigDecimal.add`.
- Simpan dan kembalikan akun terbaru.

### `withdraw(String accountNumber, BigDecimal amount)`

- Validasi `amount` tidak null dan lebih besar dari 0. Jika tidak, lempar `IllegalArgumentException`.
- Ambil akun via `getAccount`.
- Jika `balance` kurang dari `amount`, lempar `InsufficientBalanceException`.
- Kurangi `amount` dari `balance` menggunakan `BigDecimal.subtract`.
- Simpan dan kembalikan akun terbaru.

---

## Part 3: Service Layer — Transfer

File: `service/AccountService.java`

### `transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount)`

- Validasi `fromAccountNumber` dan `toAccountNumber` tidak sama. Jika sama, lempar `IllegalArgumentException`.
- Panggil `withdraw` dari rekening sumber.
- Panggil `deposit` ke rekening tujuan.
- Log transfer.

---

## Part 4: Controller Layer

File: `controller/AccountController.java`

Semua method harus mengembalikan `ResponseEntity<ApiResponse<T>>`.

### `POST /api/accounts`

- Panggil `accountService.createAccount(request)`.
- Kembalikan HTTP 201 Created dengan `ApiResponse.success("Account created successfully", account)`.

### `GET /api/accounts/{accountNumber}`

- Panggil `accountService.getAccount(accountNumber)`.
- Kembalikan HTTP 200 OK dengan `ApiResponse.success("Account retrieved successfully", account)`.

### `POST /api/accounts/{accountNumber}/deposit`

- Panggil `accountService.deposit(accountNumber, request.getAmount())`.
- Kembalikan HTTP 200 OK dengan `ApiResponse.success("Deposit successful", account)`.

### `POST /api/accounts/{accountNumber}/withdraw`

- Panggil `accountService.withdraw(accountNumber, request.getAmount())`.
- Kembalikan HTTP 200 OK dengan `ApiResponse.success("Withdrawal successful", account)`.

### `POST /api/accounts/{accountNumber}/transfer`

- Panggil `accountService.transfer(accountNumber, request.getToAccountNumber(), request.getAmount())`.
- Kembalikan HTTP 200 OK dengan `ApiResponse.success("Transfer successful", null)`.

---

## Part 5: Exception Handling

File: `exception/GlobalExceptionHandler.java`

Tangani exception berikut dan kembalikan response dalam format `ApiResponse`:

- `AccountNotFoundException` → HTTP 404 Not Found, pesan dari exception.
- `InsufficientBalanceException` → HTTP 400 Bad Request, pesan dari exception.
- `IllegalArgumentException` → HTTP 400 Bad Request, pesan dari exception.
- `MethodArgumentNotValidException` → HTTP 400 Bad Request, pesan error validasi pertama.
- `Exception` (fallback) → HTTP 500 Internal Server Error, pesan generic.

---

## Part 6: Unit Test (Bonus)

File: `test/.../AccountServiceTest.java`

Lengkapi test yang sudah disediakan:

- `createAccount_shouldReturnSavedAccount`: verifikasi akun berhasil dibuat dengan data yang sesuai.
- `getAccount_whenNotFound_shouldThrow`: verifikasi `AccountNotFoundException` dilempar jika akun tidak ada.
- `withdraw_whenInsufficientBalance_shouldThrow`: verifikasi `InsufficientBalanceException` dilempar jika saldo tidak cukup.

---

## Ekspektasi Akhir

Setelah semua part selesai, jalankan:

```bash
./mvnw test
./mvnw spring-boot:run
```

Kemudian uji endpoint dengan curl sesuai contoh di `README.md`. Semua harus mengembalikan response yang benar.
