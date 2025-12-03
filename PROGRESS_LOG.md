# DEVLOG: STUDENT GRADE MANAGEMENT (QLDS)
**Thời gian cập nhật cuối:** 03/12/2025 - Sprint 5 (UI Refinement & Integration)

## 1. TỔNG QUAN TRẠNG THÁI (PROJECT STATUS)
* **Chiến lược:** Snapshot -> Execute -> Commit.
* **Môi trường:** JDK 21 (LTS), JavaFX 21 SDK, SQLite.
* **Branch hiện tại:** `feature/ui-grade-management` (hoặc `main`).
* **Trạng thái Build:** Chạy được (Runnable), nhưng chức năng nhập điểm chưa hoàn thiện logic.

## 2. NHỮNG THAY ĐỔI ĐÃ THỰC HIỆN (SESSION COMPLETED)
### A. Giao diện (UI/UX) - Researcher B
- [x] **Update GradeManagementView.fxml**:
    - Thêm cột **"Điểm Thường Xuyên"** (`assignmentScoreColumn`) vào bảng điểm.
    - Thêm khu vực "Công cụ tính toán" ở cuối màn hình.
    - Thêm 3 nút chức năng: `Tính Đ.Thành Phần`, `Tính TB Môn`, `Tính GPA Tổng`.
    - Thêm 3 nhãn hiển thị kết quả (`Label`).

### B. Logic & Controller (Đã thực hiện trước đó)
- [x] Fix lỗi đường dẫn FXML và lỗi chính tả.
- [x] Fix lỗi Logout (thoát hoàn toàn khỏi màn hình Admin).
- [x] Fix lỗi NullPointer khi thêm sinh viên.

## 3. VẤN ĐỀ TỒN ĐỌNG (KNOWN ISSUES)
**Lưu ý cho Coder A:**
1.  **Logic hiển thị Bảng điểm (Critical):**
    - Hiện tại 1 học sinh đang hiện thành 3 dòng (do mỗi `Grade` là 1 dòng).
    - Cần gom nhóm (Group) để 1 học sinh chỉ hiện 1 dòng với đủ 3 cột điểm (Assignment, Midterm, Final).
2.  **Controller chưa map dữ liệu mới:**
    - Cột `assignmentScoreColumn` mới chỉ có trên FXML, chưa được xử lý trong `initialize()` của Controller.
    - Các nút tính toán mới chưa có code logic xử lý sự kiện (`onAction`).
3.  **Dữ liệu giả:** ComboBox chọn Lớp/Môn vẫn đang hardcode.

## 4. KẾ HOẠCH TIẾP THEO (NEXT ACTIONS - HANDOVER TO CODER A)
**Mục tiêu:** Làm cho giao diện Nhập điểm hoạt động đúng logic thực tế.

- [ ] **Task 1 (Quan trọng):** Tạo Class `StudentGradeViewModel` để gom 3 đầu điểm của 1 học sinh vào một object duy nhất -> Hiển thị lên bảng thành 1 dòng.
- [ ] **Task 2:** Cập nhật `GradeManagementController`:
    - Khai báo `@FXML` cho các nút và cột mới thêm.
    - Viết logic cho hàm `initialize` để map dữ liệu vào cột mới.
- [ ] **Task 3:** Viết hàm tính toán cho nút "Tính Đ.Thành Phần".
- [ ] **Task 4:** Thay thế ComboBox Lớp/Môn bằng dữ liệu thật từ `ClassroomDAO`.

## 5. GHI CHÚ (NOTES)
* **Researcher B:** Đã hoàn tất thiết kế UI cho màn hình nhập điểm. Giao diện đã đầy đủ các trường theo quy chế điểm.
* **Yêu cầu:** Coder A cần ưu tiên xử lý vấn đề hiển thị bảng điểm (Task 1) trước, nếu không dữ liệu nhập vào sẽ bị loạn.