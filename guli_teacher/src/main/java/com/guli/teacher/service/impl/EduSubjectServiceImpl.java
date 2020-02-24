package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.teacher.entity.EduSubject;
import com.guli.teacher.entity.vo.OneSubject;
import com.guli.teacher.entity.vo.TwoSubject;
import com.guli.teacher.mapper.EduSubjectMapper;
import com.guli.teacher.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lyl
 * @since 2020-02-12
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public List<String> importEXCL(MultipartFile file) {
        //存储错误信息的集合
        List<String> meg = new ArrayList<>();
         try {
             InputStream inputStream = file.getInputStream(); //获取文件流
             HSSFWorkbook workbook = new HSSFWorkbook(inputStream); //获取工作簿
             HSSFSheet sheet = workbook.getSheetAt(0);//获取第一个sheet
             int rowNum = sheet.getLastRowNum();//获取sheet的行数
             if(rowNum <= 1){
                 meg.add("请添加数据");
                 return meg;
             }
             for (int num = 1; num < rowNum; num++) {
                 Row row = sheet.getRow(num); //获取行
                 Cell cell = row.getCell(0);//获取这行的第一列的数据
                 if(cell==null){
                     meg.add("第"+num+"行第一列为空");
                     continue;
                 }
                 String cellValue = cell.getStringCellValue();
                 if(StringUtils.isEmpty(cellValue)){
                     meg.add("第"+num+"行第一列数据为空");
                     continue;
                 }
                 //判断列是否存在，在获取列的数据
                 EduSubject subject = this.selectSubjectByName(cellValue);
                 String pid = null;
                 //把这个第一列的数据保存到数据库中
                 if(subject==null){
                     EduSubject subject1 = new EduSubject();
                     subject1.setTitle(cellValue);
                     subject1.setParentId("0");
                     subject1.setSort(0);
                     baseMapper.insert(subject1);
                     pid = subject1.getId();
                 }else {
                     pid = subject.getId();
                 }
                //获取第二列
                 Cell cell1 = row.getCell(1);
                 if(cell1==null){
                     meg.add("第"+num+"行第2列为空");
                     continue;
                 }
                 String stringCellValue = cell1.getStringCellValue();
                 if(StringUtils.isEmpty(stringCellValue)){
                     meg.add("第"+num+"行第2列数据为空");
                     continue;
                 }
                 //判断此一级分类中是否存在二级分类
                 EduSubject subject2 = this.selectSubjectByNameAndParentId(stringCellValue,pid);
                 if(subject2==null){
                     EduSubject su = new EduSubject();
                     su.setTitle(stringCellValue);
                     su.setParentId(pid);
                     su.setSort(0);
                     baseMapper.insert(su);
                 }
             }
             return meg;
         } catch (Exception e) {
              e.printStackTrace();
              return meg;
         }
    }

    /**
     * 获取tree课程列表
     * @return
     */
    @Override
    public List<OneSubject> getTree() {

        List<OneSubject> oneSubjectList = new ArrayList<>();
        //获取一级分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0);
        List<EduSubject> subjectList = baseMapper.selectList(wrapper);
        //遍历一级分类
        for ( EduSubject eduSubject : subjectList) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            QueryWrapper<EduSubject> wrapper1= new QueryWrapper<>();
            wrapper1.eq("parent_id",oneSubject.getId());
            List<EduSubject> eduSubjects = baseMapper.selectList(wrapper1);
            for (EduSubject su : eduSubjects) {
                TwoSubject twoSubject = new TwoSubject();
                BeanUtils.copyProperties(su,twoSubject);
                oneSubject.getChildren().add(twoSubject);
            }
            oneSubjectList.add(oneSubject);
        }
        return oneSubjectList;
    }

    @Override
    public Boolean saveLevel1(EduSubject subject) {
        //根据title判断是否存在同名
        EduSubject eduSubject = this.selectSubjectByName(subject.getTitle());
        if(eduSubject!=null){
            return false;
        }
        subject.setSort(0);
        int i = baseMapper.insert(subject);
        return i==1;

    }

    @Override
    public Boolean saveLevelTwo(EduSubject subject) {

        EduSubject eduSubject = this.selectSubjectByNameAndParentId(subject.getTitle(), subject.getParentId());
        if(eduSubject!=null){
            return false;
        }else {
            int i = baseMapper.insert(subject);
            return i==1;
        }

    }

    private EduSubject selectSubjectByNameAndParentId(String stringCellValue,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",stringCellValue);
        wrapper.eq("parent_id",pid);
        EduSubject subject = baseMapper.selectOne(wrapper);
        return subject;
    }

    /**
     * 根据课程一级分类的名字查询是否存在
     * @param cellValue
     * @return
     */
    private EduSubject selectSubjectByName(String cellValue) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",cellValue);
        wrapper.eq("parent_id",0);
        EduSubject subject = baseMapper.selectOne(wrapper);
        return subject;

    }

}
