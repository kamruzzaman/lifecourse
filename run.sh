mkdir -p src/main/java/com/lifecourse/course_service/modules/course/domain/exceptions
mkdir -p src/main/java/com/lifecourse/course_service/modules/course/application
mkdir -p src/main/java/com/lifecourse/course_service/modules/course/infrastructure/persistence
mkdir -p src/main/java/com/lifecourse/course_service/modules/course/infrastructure/messaging
mkdir -p src/main/java/com/lifecourse/course_service/modules/course/web
touch src/main/java/com/lifecourse/course_service/modules/course/package-info.java

# Student module
mkdir -p src/main/java/com/lifecourse/course_service/modules/student/domain/exceptions
mkdir -p src/main/java/com/lifecourse/course_service/modules/student/application
mkdir -p src/main/java/com/lifecourse/course_service/modules/student/infrastructure/persistence
mkdir -p src/main/java/com/lifecourse/course_service/modules/student/infrastructure/messaging
mkdir -p src/main/java/com/lifecourse/course_service/modules/student/web
touch src/main/java/com/lifecourse/course_service/modules/student/package-info.java

# Order module
mkdir -p src/main/java/com/lifecourse/course_service/modules/order/domain/exceptions
mkdir -p src/main/java/com/lifecourse/course_service/modules/order/application
mkdir -p src/main/java/com/lifecourse/course_service/modules/order/infrastructure/persistence
mkdir -p src/main/java/com/lifecourse/course_service/modules/order/infrastructure/messaging
mkdir -p src/main/java/com/lifecourse/course_service/modules/order/web
touch src/main/java/com/lifecourse/course_service/modules/order/package-info.java

# Payment module
mkdir -p src/main/java/com/lifecourse/course_service/modules/payment/domain/exceptions
mkdir -p src/main/java/com/lifecourse/course_service/modules/payment/application
mkdir -p src/main/java/com/lifecourse/course_service/modules/payment/infrastructure/persistence
mkdir -p src/main/java/com/lifecourse/course_service/modules/payment/infrastructure/messaging
mkdir -p src/main/java/com/lifecourse/course_service/modules/payment/web
touch src/main/java/com/lifecourse/course_service/modules/payment/package-info.java

# Root app classes
touch src/main/java/com/lifecourse/course_service/CourseManagementApplication.java
touch src/main/java/com/lifecourse/course_service/ModulithRuntimeHints.java