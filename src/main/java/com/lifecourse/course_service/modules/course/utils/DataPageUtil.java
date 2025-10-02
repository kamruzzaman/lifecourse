package com.lifecourse.course_service.modules.course.utils;

import com.lifecourse.course_service.modules.course.config.DataPage;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class DataPageUtil {

   public static DataPage convertToDatapage(Page<?> convertToPageModel) {
        List<?> empty=new ArrayList<>();
        DataPage dataPage=new DataPage();
        if(convertToPageModel==null || convertToPageModel.getTotalElements()==0){
            dataPage.setContent(empty);
        }
        else{
            dataPage.setContent(convertToPageModel.getContent());
        }
        dataPage.setTotalPages(convertToPageModel.getTotalPages());
        dataPage.setLast(convertToPageModel.isLast());
        dataPage.setFirst(convertToPageModel.isFirst());
        dataPage.setSize(convertToPageModel.getSize());
        dataPage.setTotalElements(convertToPageModel.getTotalElements());
        dataPage.setPage(convertToPageModel.getPageable().getPageNumber());
        return dataPage;
    }
}
