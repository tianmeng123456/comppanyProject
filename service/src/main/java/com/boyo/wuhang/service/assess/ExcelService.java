package com.boyo.wuhang.service.assess;

import com.boyo.wuhang.dao.assess.WhEvaluationMapper;
import com.boyo.wuhang.dao.assess.WhRankEvaluationMapper;
import com.boyo.wuhang.dao.base.BaseDepartureMapper;
import com.boyo.wuhang.entity.assess.WhEvaluation;
import com.boyo.wuhang.entity.base.BaseDeparture;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {
	@Autowired
	private WhEvaluationMapper evaluationMapper;
	@Autowired
	private WhRankEvaluationMapper rankEvaluationMapper;
	@Autowired
	private BaseDepartureMapper departureMapper;

	public XSSFWorkbook exportEvaluation(WhEvaluation evaluation){
		List<BaseDeparture> departureList = departureMapper.getAllDeparture(evaluation.getId());
		List<Map<String,Object>> list = rankEvaluationMapper.getRankRecordByEvaluationId(evaluation.getId(),
				rankEvaluationMapper.getDepartureString(evaluation.getId()));

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("级别评价表");
		int index =0;
		XSSFRow row = sheet.createRow(index);
		int length = 4 + departureList.size();
		for (int i = 0; i < length; i++){
			row.createCell(i);
		}
		index++;
		row.setHeightInPoints(20f);
		CellRangeAddress region = new CellRangeAddress(0,0,0,length-1);
		sheet.addMergedRegion(region);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short)14);
		titleStyle.setFont(font);

		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		XSSFCell cell = row.getCell(0);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("废钢级别评价表 （日期："+sdf1.format(evaluation.getExecDate())+"）");
		cell.setCellStyle(titleStyle);

		row = sheet.createRow(index);
		for (int i = 0; i < length; i++){
			row.createCell(i);
		}
		index++;

		region = new CellRangeAddress(1,1,0,length-2);
		sheet.addMergedRegion(region);
		cell = row.getCell(0);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("编号"+evaluation.getEvaluationNo());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		cell = row.getCell(length-1);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("执行时间"+sdf.format(evaluation.getExecDate()));

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setWrapText(true);

		CellStyle cellStyle2 = workbook.createCellStyle();
		cellStyle2.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		cellStyle2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cellStyle2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle2.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cellStyle2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle2.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle2.setWrapText(true);

		//表头
		for (int i = 0; i < 2; i++){
			row = sheet.createRow(index);
			for (int j = 0; j < length; j++){
				cell = row.createCell(j);
				cell.setCellStyle(cellStyle);
			}
			index++;
		}
		for (int i = 0; i < 3; i++){
			region = new CellRangeAddress(index-2, index-1, i, i);
			sheet.addMergedRegion(region);
		}
		region = new CellRangeAddress(index-2, index-1, length-1, length-1);
		sheet.addMergedRegion(region);

		row = sheet.getRow(index-2);
		cell = row.getCell(0);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("级别");
		cell = row.getCell(1);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("品级");
		cell.setCellStyle(cellStyle);
		cell = row.getCell(2);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("层次");
		cell.setCellStyle(cellStyle);

		region = new CellRangeAddress(index-2,index-2,3,length -2);
		sheet.addMergedRegion(region);

		cell = row.getCell(3);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("到场单价（元/吨）");
		cell.setCellStyle(cellStyle);
		cell = row.getCell(length -1);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("标准明细");
		cell.setCellStyle(cellStyle);

		row = sheet.getRow(index-1);
		for (int i = 0; i < departureList.size(); i++){
			cell = row.getCell(3+i);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(departureList.get(i).getdName());
			cell.setCellStyle(cellStyle);
		}

		//级别价格
		for (Map<String,Object> item : list){
			row = sheet.createRow(index);

			cell = row.createCell(0);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(item.get("wRank").toString());
			cell.setCellStyle(cellStyle);
			cell = row.createCell(1);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(item.get("grade").toString());
			cell.setCellStyle(cellStyle);
			cell = row.createCell(2);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(item.get("wLevel").toString());
			cell.setCellStyle(cellStyle);

			for (int i = 0; i < departureList.size(); i++){
				cell = row.createCell(3+i);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(item.get(departureList.get(i).getdName()).toString().replaceAll("\\.(0)+$|(0)+$", ""));
				cell.setCellStyle(cellStyle);
			}

			cell = row.createCell(length-1);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(item.get("detail").toString());
			cell.setCellStyle(cellStyle2);
			index++;
		}

		//注释
		String remark = evaluation.getRemark();
		if (remark != null){
			String[] remarkList = remark.split("\n");
			for (int i = 0; i < remarkList.length; i++){
				row = sheet.createRow(index);
				for (int j = 0; j < length; j++){
					cell = row.createCell(j);
					cell.setCellStyle(cellStyle2);
				}
				region = new CellRangeAddress(index,index,0,length-1);
				sheet.addMergedRegion(region);
				cell = row.getCell(0);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(remarkList[i]);

				index++;
			}
		}

		row = sheet.createRow(index);
		for (int i = 0; i < length; i++){
			row.createCell(i);
		}
		cell = row.getCell(0);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("审核：");
		cell = row.getCell(1);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(evaluation.getCheckPerson());
		cell = row.getCell(length-1);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("批准：");

		for (int i = 0; i < length -1; i++){
			sheet.setColumnWidth(i,8*256);
		}
		sheet.setColumnWidth(length-1,90*256);

		return workbook;
	}

}
