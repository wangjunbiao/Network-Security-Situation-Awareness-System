package nssa.uc.jfreechart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nssa.uc.dao.SecurityEventDao;
import nssa.uc.util.DateUtil;
import nssa.uc.vo.SecurityEvent;


public class BarChartDataAll {
	
	private String title;
	private List<Integer> dateList;
	private List<String> msgs;
	private int[][] values;
	
	public BarChartDataAll(String date) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("最近");
		if(date.equals("today")) {
			sb.append("一天");
		}
		if(date.equals("lastweek")) {
			sb.append("一周");
		}
		sb.append("各类安全事件情况统计");
		title = sb.toString();
		dateList = new ArrayList<Integer>();
		SecurityEventDao securityEventDao = new SecurityEventDao();
		msgs = securityEventDao.getMsgs();
		createData(date);
	}

	private void createData(String date) throws SQLException {
		//创建Dao
		SecurityEventDao securityEventDao = new SecurityEventDao();
		//创建securityEventList
		List<SecurityEvent> securityEventList = null;
		//最近一天
		if(date.equals("today")){
			//获取securityEventList
			securityEventList = securityEventDao.listTodayAll();
			//设施日期坐标
			for(int i = 0; i < securityEventList.size(); i++) {
				if(!dateList.contains(DateUtil.getHour(securityEventList.get(i).getTime()))) {
					dateList.add(DateUtil.getHour(securityEventList.get(i).getTime()));
				}
			}
			//初始化统计数目
			values = new int[dateList.size()][msgs.size()];
			//统计
			for(int n = 0; n < securityEventList.size(); n++) {
				for(int i = 0; i < dateList.size(); i++) {
					for(int j = 0; j < msgs.size(); j++) {
						if(DateUtil.getHour(securityEventList.get(n).getTime()) == dateList.get(i).intValue()) {
							if(securityEventList.get(n).getMsg().equals(msgs.get(j))) {
								values[i][j]++;
							}
						}
					}
				}
			}
		}
		//最近一周
		if(date.equals("lastweek")) {
			//获取securityEventList
			securityEventList = securityEventDao.listLastWeekAll();
			//设施日期坐标
			for(int i = 0; i < securityEventList.size(); i++) {
				if(!dateList.contains(DateUtil.getDay(securityEventList.get(i).getTime()))) {
					dateList.add(DateUtil.getDay(securityEventList.get(i).getTime()));
				}
			}
			//初始化统计数目
			values = new int[dateList.size()][msgs.size()];
			//统计
			for(int n = 0; n < securityEventList.size(); n++) {
				for(int i = 0; i < dateList.size(); i++) {
					for(int j = 0; j < msgs.size(); j++) {
						if(DateUtil.getDay(securityEventList.get(n).getTime()) == dateList.get(i).intValue()) {
							if(securityEventList.get(n).getMsg().equals(msgs.get(j))) {
								values[i][j]++;
							}
						}
					}
				}
			}
		}
		
		
	}

	public String getTitle() {
		return title;
	}

	public List<Integer> getDateList() {
		return dateList;
	}
	
	public String[] getMsgs() {
		return (String[])msgs.toArray(new String[msgs.size()]); 
	}

	public int[][] getValues() {
		return values;
	}
	
}
