package com.xuecheng.manage_course.controller;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.utils.XcOauth2Util;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Pace
 * @Data: 2020/2/25 22:06
 * @Version: v1.0
 */
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController{

    @Autowired
    private CourseService courseService;

    @GetMapping("/coursebase/get/{courseId}")
    public CourseBase getCourseBaseById(@PathVariable String courseId) {
        return courseService.getCourseBaseById(courseId);
    }

    @GetMapping("/coursepic/get/{courseId}")
    public CoursePic findCoursePic(@PathVariable String courseId) {
        return courseService.findCoursePic(courseId);
    }

}
