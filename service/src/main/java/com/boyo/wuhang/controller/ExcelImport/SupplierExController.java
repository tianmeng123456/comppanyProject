package com.boyo.wuhang.controller.ExcelImport;

import com.boyo.wuhang.dao.base.BaseSupplierMapper;
import com.boyo.wuhang.entity.assess.SupplierPay;
import com.boyo.wuhang.entity.base.BaseSupplier;
import com.boyo.wuhang.service.ExcelImport.SupplierExService;
import com.boyo.wuhang.ultity.ExcelUtil;
import com.boyo.wuhang.ultity.JsonBuilder;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/api/pay/")
public class SupplierExController {
    @Autowired
    private SupplierExService payService;
    @Autowired
    private BaseSupplierMapper supplierMapper;

    /**
     * 银行导入(前4值为空时会上传失败)
     * @param file
     * @param request
     * @return
     */
    @PostMapping("importexcel")
    public JSONObject SupplierImportExcel(@RequestParam("file") MultipartFile file , HttpServletRequest request){
        if (!file.isEmpty()){
            try {
                //获取原始文件名
                String filename = file.getOriginalFilename();
                String fileType = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
                //默认从第一行开始
                Integer startRow = 1;
                //获取输入流
                InputStream inputStream = file.getInputStream();
                List<SupplierPay> payList = new ArrayList<>();
                List<String[]> strings = ExcelUtil.readData(fileType, startRow, false, inputStream);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //遍历每一行数据
                for (String[] str : strings){
                    SupplierPay pay = new SupplierPay();
                    if (str.length <= 4){
                        continue;
                    }
                    pay.setBankName(str[0]);
                    Date payDate = sdf.parse(str[1]);
                    pay.setPayDate(payDate);
                    pay.setPayMoney(BigDecimal.valueOf(Double.valueOf(str[2])));
                    pay.setSerialNumber(str[3]);
                    BaseSupplier supplier = supplierMapper.selectByName(str[4]);
                    pay.setSupplierId(supplier.getId());
                    pay.setSupplierName(str[4]);
                    pay.setBankAccount(str[5]);
                    if (str.length != 7){
                        pay.setRemark(null);
                    }else {
                        pay.setRemark(str[6]);
                    }
                    payList.add(pay);
                }
                boolean supplierPay = payService.insertSupplierPay(payList);
                if (supplierPay){
                    return JsonBuilder.build(1,"上传成功",null);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return JsonBuilder.build(0,"上传失败",null);
    }
}
