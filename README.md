# Java exercise
## Cấu trúc thư mục trong bài tập
## 1. Config

- 1.1 ApplicationConfig: config file tạo các bean.
- 1.2 FileUploadProperties:  config custom properties.
- 1.3 JwtAuthenticationFilter: filter file để thực hiện authenticate và authorize bằng *jsonwebtoken*.
- 1.4 PasswordEncoderTest: Spring Security yêu cầu một hàm encoder cho password. Tạo một custom password encoder class với mục đích giữ nguyên password và lưu vào database. Khuyến khích không nên sử dụng trong thực tế.
- 1.5 SecurityConfig: File config security.

## 2. Controller

- AuthController: Controller cho đăng nhập và đăng xuất.
- FileController: Controller cho upload và download file.
- UserController: Controller cho tính năng search của User.

## 3. Entity

- Student: ORM với bảng student trong database.
- StudentInfo: ORM với bảng student_info trong database.
- User: ORM với bảng user trong database.

## 4. DTO
- StudentDTO
- StudentDTOConverter: convert data từ 2 bảng student và student_info qua StudentDTO.

## 5. Repository

- 