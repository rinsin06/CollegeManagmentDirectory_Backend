package com.collegedirectory.controller;

//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.collegedirectory.services.CourseService;
//import com.collegedirectory.services.EnrollmentService;
//import com.collegedirectory.services.FacultyService;
//import com.collegedirectory.services.StudentService;
//
//@RestController
//@RequestMapping("/api/admin/dashboard")
//public class AdminDashBoardController {
//
//    @Autowired
//    private EnrollmentService enrollmentService;
//
//    @Autowired
//    private FacultyService facultyService;
//
//    @Autowired
//    private CourseService courseService;
//
//    @Autowired
//    private StudentService studentService;
//
//    // 1.1 Get Enrollment Trends Over Time
//    @GetMapping("/enrollment-trends")
//    public ResponseEntity<?> getEnrollmentTrends(@RequestParam Optional<Integer> startYear,
//                                                 @RequestParam Optional<Integer> endYear,
//                                                 @RequestParam Optional<Long> departmentId) {
//        List<EnrollmentTrendDTO> trends = enrollmentService.getEnrollmentTrends(startYear, endYear, departmentId);
//        return ResponseEntity.ok(trends);
//    }
//
//    // 1.2 Get Enrollment Trends by Month
//    @GetMapping("/enrollment-trends/monthly")
//    public ResponseEntity<?> getMonthlyEnrollmentTrends(@RequestParam int year,
//                                                        @RequestParam Optional<Long> departmentId) {
//        List<MonthlyEnrollmentDTO> monthlyTrends = enrollmentService.getMonthlyEnrollmentTrends(year, departmentId);
//        return ResponseEntity.ok(monthlyTrends);
//    }
//
//    // 2.1 Get Faculty Course Load
//    @GetMapping("/faculty-course-load")
//    public ResponseEntity<?> getFacultyCourseLoad(@RequestParam Optional<Long> departmentId,
//                                                  @RequestParam Optional<String> semester) {
//        List<FacultyCourseLoadDTO> courseLoads = facultyService.getFacultyCourseLoad(departmentId, semester);
//        return ResponseEntity.ok(courseLoads);
//    }
//
//    // 2.2 Get Specific Faculty Course Load
//    @GetMapping("/faculty-course-load/{facultyId}")
//    public ResponseEntity<?> getFacultyCourseLoadById(@PathVariable Long facultyId,
//                                                      @RequestParam Optional<String> semester) {
//        FacultyCourseLoadDetailDTO courseLoadDetail = facultyService.getFacultyCourseLoadById(facultyId, semester);
//        return ResponseEntity.ok(courseLoadDetail);
//    }
//
//    // 3.1 Get Course Enrollment Data
//    @GetMapping("/course-enrollments")
//    public ResponseEntity<?> getCourseEnrollments(@RequestParam Optional<Long> courseId,
//                                                  @RequestParam Optional<Long> departmentId,
//                                                  @RequestParam Optional<String> semester) {
//        List<CourseEnrollmentDTO> enrollments = courseService.getCourseEnrollments(courseId, departmentId, semester);
//        return ResponseEntity.ok(enrollments);
//    }
//
//    // 4.1 Get Student Details by Department
//    @GetMapping("/students-by-department")
//    public ResponseEntity<?> getStudentsByDepartment(@RequestParam Long departmentId) {
//        List<StudentDTO> students = studentService.getStudentsByDepartment(departmentId);
//        return ResponseEntity.ok(students);
//    }
//
//    // 5.1 Get Faculty Details by Department
//    @GetMapping("/faculty-by-department")
//    public ResponseEntity<?> getFacultyByDepartment(@RequestParam Long departmentId) {
//        List<FacultyDTO> faculty = facultyService.getFacultyByDepartment(departmentId);
//        return ResponseEntity.ok(faculty);
//    }
//
//    // 6.1 Get General Dashboard Data
//    @GetMapping("/data")
//    public ResponseEntity<?> getDashboardData() {
//        DashboardDataDTO dashboardData = new DashboardDataDTO();
//        dashboardData.setEnrollmentTrends(enrollmentService.getAllEnrollmentTrends());
//        dashboardData.setFacultyCourseLoad(facultyService.getAllFacultyCourseLoads());
//        dashboardData.setCourseEnrollments(courseService.getAllCourseEnrollments());
//
//        return ResponseEntity.ok(dashboardData);
//    }
//}
//
