package com.chen.milliondataexport.export.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *    百万数据导出工具类
 * </p>
 *
 * @author：MaybeBin
 * @Date: 2022-07-22 16-37
 */
@Slf4j
public class MyExport<M extends BaseMapper<T>, T> {

    private HttpServletResponse response; // 最后一个sheet需要写入的次数
    private QueryWrapper<T> wrapper;
    private String fileName;
    private M mapper;

    private Page<T> page;
    private Class<T> aClass;
    private ExcelWriter excelWriter;
    private Integer totalCount;     // 总数
    private Integer sheetDataRows;  // sheet存放数据量
    private Integer writeDataRows;  // 每次写入行数
    private Integer sheetNum;       // Sheet个数
    private Integer oneSheetWriteCount;  // 每一个Sheet需要写入的次数
    private Integer lastSheetWriteCount; // 最后一个sheet需要写入的次数

    public MyExport(){
    }

    public MyExport(QueryWrapper<T> wrapper, String fileName, HttpServletResponse response, M mapper) throws IOException {
        this.wrapper = wrapper;
        this.fileName = fileName;
        this.response = response;
        this.mapper = mapper;

        this.initProperty();
        this.computeProperty();
        this.setExportConfigure();
        this.export();
    }

    /**
     * wrapper.select("checkid","idcard","name","sex");
     * MyExport.dataExport(wrapper,"文件名",response, personMapper);
     * @param wrapper
     * @param fileName
     * @param response
     * @param mapper
     * @throws IOException
     */
    public static void dataExport(QueryWrapper wrapper, String fileName, HttpServletResponse response, BaseMapper mapper) throws IOException {
        new MyExport(wrapper,fileName,response, mapper);
    }

    /**
     * 初始化属性（设置Sheet存放数量、每次写入数据量、获取总数和对象class）
     */
    private void initProperty(){
        // 每一个Sheet存放100w条数据
        this.sheetDataRows = 1000000;
        // 每次写入的数据量20w
        this.writeDataRows = 200000;

        // 获取此次导出的总数据量
        this.page = new Page<>(1,1);
        IPage<T> iPage = mapper.selectPage(page, wrapper);
        this.totalCount = Long.valueOf(iPage.getTotal()).intValue();
        // 获取对象的class
        this.aClass = (Class<T>)iPage.getRecords().get(0).getClass();
    }

    /**
     * 计算属性值(Sheet个数、每个Sheet写入次数、最后一个sheet写入次数)
     */
    private void computeProperty(){
        // 最后一个Sheet的行数
        Integer lastSheetNum = totalCount % sheetDataRows;
        // 计算Sheet数量
        this.sheetNum = lastSheetNum == 0 ? (totalCount / sheetDataRows) : (totalCount / sheetDataRows + 1);
        // 计算一般情况下每一个Sheet需要写入的次数(不包含最后一个sheet,因为最后一个sheet不确定会写入多少条数据)
        this.oneSheetWriteCount = sheetDataRows / writeDataRows;
        // 计算最后一个sheet需要写入的次数
        this.lastSheetWriteCount = lastSheetNum == 0 ? oneSheetWriteCount :
                (lastSheetNum % writeDataRows == 0 ? (lastSheetNum / writeDataRows) : (lastSheetNum / writeDataRows + 1));
    }

    /**
     * 设置导出配置
     * @throws IOException
     */
    private void setExportConfigure() throws IOException {
        this.response.setContentType("application/vnd.ms-excel");
        this.response.setCharacterEncoding("utf-8");
        // 防止中文乱码
        this.fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        this.response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        this.excelWriter = EasyExcel.write(response.getOutputStream()).build();
    }

    /**
     * 导出excel
     */
    private void export(){
        long startTime = System.currentTimeMillis();
        log.warn("导出开始时间:" + startTime);
        for (int i = 0; i < sheetNum; i++) {
            log.warn("第" + i + "个Sheet");
            //创建Sheet
            String sheet = "sheet" + (i + 1);
            //循环写入次数: j的自增条件是当不是最后一个Sheet的时候写入次数为正常的每个Sheet写入的次数,如果是最后一个就需要使用计算的次数lastSheetWriteCount
            for (int j = 0; j < (i != sheetNum - 1 ? oneSheetWriteCount : lastSheetWriteCount); j++) {
                //分页查询一次20w
                Integer current = oneSheetWriteCount * i + j + 1;
                page.setCurrent(current);
                page.setSize(writeDataRows);

                List<T> list = mapper.selectPage(page, wrapper).getRecords();
                WriteSheet writeSheet = EasyExcel.writerSheet(i, sheet)
                        .head(aClass).build();
                excelWriter.write(list, writeSheet);
            }
        }
        excelWriter.finish();
        log.warn("导出耗时："+(System.currentTimeMillis() - startTime));
    }

}
