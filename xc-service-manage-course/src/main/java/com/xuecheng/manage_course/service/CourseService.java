package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.constant.CourseConstant;
import com.xuecheng.framework.domain.course.*;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Pace
 * @Data: 2020/2/25 22:04
 * @Version: v1.0
 */
@Service
public class CourseService {

    @Autowired
    private CourseBaseRepository courseBaseRepository;
    @Autowired
    private CoursePicRepository coursePicRepository;

    /**
     * 根据id查询课程基础信息
     * @param courseId
     * @return
     */
    public CourseBase getCourseBaseById(String courseId) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        return optional.get();
    }

    /**
     * 查询图片
     * @param courseId
     * @return
     */
    public CoursePic findCoursePic(String courseId) {
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);
        if(!optional.isPresent()){
            return  null;
        }
        return optional.get();
    }

}
