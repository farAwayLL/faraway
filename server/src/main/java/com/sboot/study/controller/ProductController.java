package com.sboot.study.controller;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.sboot.study.entity.TProduct;
import com.sboot.study.enums.StatusCode;
import com.sboot.study.mybatisMapper.TProductMapper;
import com.sboot.study.response.BaseResponse;
import com.sboot.study.service.PoiService;
import com.sboot.study.service.ProductService;
import com.sboot.study.service.WebOperationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * create by faraway on 2018/12/28
 * description:用来测试excel导入导出
 */
@Controller
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private static final String PREFIX = "excel/product";

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private PoiService poiService;

    @Autowired
    private WebOperationService webOperationService;

    /**
     * 获取产品列表
     *
     * @param paramMap
     * @return
     */
    @PostMapping(value = PREFIX + "/getProductList")
    @ResponseBody
    public BaseResponse getProductList(@RequestParam Map<String, Object> paramMap) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            PageHelper.startPage(Integer.valueOf(paramMap.get("pageNum").toString()), Integer.valueOf(paramMap.get("pageSize").toString()));
            List<TProduct> productList = productMapper.selectProductList(paramMap);
            Map<String, Object> resultMap = Maps.newHashMap();
            resultMap.put("productList", productList);
            response.setData(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse(StatusCode.FAIL.getCode(), "查询商品列表失败！");
        }
        return response;
    }

    /**
     * 导出Excel
     */
    @RequestMapping(value = PREFIX + "/export", method = RequestMethod.GET)
    public @ResponseBody
    String export(@RequestParam Map<String, Object> paramMap, HttpServletResponse response) {
        final String[] headers = new String[]{"名称", "单位", "单价", "库存量", "备注", "采购日期"};
        List<TProduct> productList = productMapper.selectProductList(paramMap);
        try {
            if (productList != null && productList.size() > 0) {
                //将产品信息列表List<TProduct>---->List<Map>
                List<Map<Integer, Object>> listMap = productService.manageProductList(productList);

                //数据量不多的时候单sheet导出
                //Workbook wb=poiService.fillExcelSheetData(listMap,headers,"产品信息列表");

                //数据量多则分sheet导出
                Workbook wb = poiService.manageSheet(listMap, headers, "产品信息列表");


                //将excel实例以流的形式写回浏览器
                webOperationService.downloadExcel(response, wb, "产品信息列表.xlsx");
                return "产品信息列表.xlsx";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导入excel
     * @param request
     */
    @RequestMapping(value=PREFIX+"/upload",method=RequestMethod.POST,consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public BaseResponse uploadExcel(MultipartHttpServletRequest request){
        BaseResponse response=new BaseResponse(StatusCode.SUCCESS);
        try {
            MultipartFile file=request.getFile("file");
            if (file!=null){
                String fileName=file.getOriginalFilename();
                String suffix= StringUtils.substring(fileName,fileName.lastIndexOf(".")+1);

                //根据上传的excel文件构造workbook实例-注意区分xls与xlsx版本对应的实例
                Workbook wb=poiService.getWorkbook(file,suffix);

                //读取上传上来的excel的数据到List<Product>中
                List<TProduct> products=poiService.readExcelData(wb);
                log.debug("读取excel得到的数据：{} ",products);

                productMapper.insertBatch(products);

            }else{
                return new BaseResponse(StatusCode.INVALID_PARAMS);
            }
        } catch (Exception e) {
            log.error("上传excel导入数据 发生异常：",e.fillInStackTrace());
            return new BaseResponse(StatusCode.FAIL);
        }
        return response;
    }

    /**
     * 给运营人员一个excel空模板
     */
    @RequestMapping(value = PREFIX+"/export/template",method = RequestMethod.GET)
    public @ResponseBody String exportTemplate(HttpServletResponse response){
        final String[] headers=new String[]{"名称","单位","单价","库存量","备注","采购日期"};
        try {
            Workbook wb=poiService.fillExcelSheetData(null,headers,"产品信息列表");
            webOperationService.downloadExcel(response,wb,"产品信息列表.xlsx");
            return "产品信息列表.xlsx";
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
