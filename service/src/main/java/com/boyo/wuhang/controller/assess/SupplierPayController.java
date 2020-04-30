package com.boyo.wuhang.controller.assess;

import com.boyo.wuhang.entity.SupplierPayForm;
import com.boyo.wuhang.entity.assess.SupplierPay;
import com.boyo.wuhang.service.assess.SupplierPayService;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/supplier/pay")
public class SupplierPayController {
	@Autowired
	private SupplierPayService supplierPayService;

	/**
	 * 银行汇款导入(按模板)
	 * @param req
	 * @param file
	 * @return
	 */
	@PostMapping("import")
	public JSONObject upload(HttpServletRequest req, @RequestParam MultipartFile file){
		try {
			if (file.isEmpty()){
				return JsonBuilder.build(1,"文件不存在",null);
			}
			List<SupplierPay> payArrayList = new ArrayList<>();
			XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			for (int row = 1;row < rows;row++){
				SupplierPay data = new SupplierPay();
				if (StringUtils.isBlank(sheet.getRow(row).getCell(4).toString())){
					continue;
				}
				data.setBankName(sheet.getRow(row).getCell(0).toString());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date parse = sdf.parse(sheet.getRow(row).getCell(1).toString());
				data.setPayDate(parse);
				data.setPayMoney(BigDecimal.valueOf(Double.valueOf(String.valueOf(sheet.getRow(row).getCell(2)).trim())));
				data.setSerialNumber(sheet.getRow(row).getCell(3).toString());
				data.setSupplierName(sheet.getRow(row).getCell(4).toString());
				data.setBankAccount(sheet.getRow(row).getCell(5).toString());
				if (StringUtils.isBlank(String.valueOf(sheet.getRow(row).getCell(6)))){
					data.setRemark(null);
				}else{
					data.setRemark(String.valueOf(sheet.getRow(row).getCell(6)));
				}
				payArrayList.add(data);
			}
			boolean dataList = supplierPayService.insertSupplierPay(payArrayList);
			return JsonBuilder.build(0,"上传成功",dataList);
		}catch (Exception e){
			e.printStackTrace();
		}
		return JsonBuilder.build(1,"上传失败",null);
	}


	@PostMapping("list")
	public JSONObject getSupplierPayList(@RequestBody SupplierPayForm payForm){
		Pager page = new Pager();
		page.setPageNumber(payForm.getPageNumber());
		page.setPageSize(page.getPageSize());
		page = supplierPayService.getSupplierPayList(payForm,page);
		return JsonBuilder.build(1,"",page);
	}
}
