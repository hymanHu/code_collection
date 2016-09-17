package com.thornBird.think.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thornBird.think.model.PollerType;
import com.thornBird.think.model.jsTree.JsTreeObject;
import com.thornBird.think.model.mysqlModel.PollerGroup;
import com.thornBird.think.model.mysqlModel.PollerItem;
import com.thornBird.think.model.mysqlModel.PollerReport;
import com.thornBird.think.server.daoServer.IPollerGroupServer;
import com.thornBird.think.server.daoServer.IPollerItemServer;
import com.thornBird.think.server.daoServer.IPollerReportServer;
import com.thornBird.think.util.JSonUtil;

/**
 * 监控历史数据控制器，加载tree，点击item获得历史数据
 * @author hyman
 *
 */
@Controller("historyStatsController")
public class HistoryStatsController {

	private static final Logger logger = LoggerFactory.getLogger(HistoryStatsController.class);
	
	private static final int UNIT_YEAR = 1;
	private static final int UNIT_MONTH = 2;
	private static final int UNIT_DAY = 3;
	private static final int UNIT_HOUR = 4;
	private static final int UNIT_HALF_HOUR = 5;
	
	@Resource(name = "pollerGroupServer")
	private IPollerGroupServer pollerGroupServer;
	@Resource(name = "pollerItemServer")
	private IPollerItemServer pollerItemServer;
	@Resource(name = "pollerReportServer")
	private IPollerReportServer pollerReportServer;
	
	@RequestMapping(value = "history/items", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String loadStatsItems(@RequestParam(required = false) String callback) {
		String response = null;
		
		try {
			List<PollerGroup> pollerGroups = pollerGroupServer.loadAll();
			if (pollerGroups == null || pollerGroups.isEmpty()) {
				return response;
			}
			
			List<JsTreeObject> trees = new ArrayList<JsTreeObject>();
			for (PollerGroup pg : pollerGroups) {
				List<PollerItem> pollerItems = pollerItemServer.loadByGroupId(pg.getId());
				if (pollerItems == null || pollerItems.isEmpty()) {
					continue;
				}
				
				JsTreeObject tree = new JsTreeObject();
				tree.setData(pg.getGroupName());
				tree.setAttr(null);
				for (PollerItem pi : pollerItems) {
					JsTreeObject childTree = new JsTreeObject();
					childTree.setData(pi.getPollerName());
					childTree.setChildren(null);
					childTree.getAttr().put("id", String.valueOf(pi.getId()));
					childTree.getAttr().put("nodeKey", pi.getPollerCommand());
					childTree.getAttr().put("nodeValue", String.format("history/stats?pollerItemId=%d", pi.getId()));
					tree.getChildren().add(childTree);
				}
				
				trees.add(tree);
			}
			
			response = JSonUtil.transformToJSon(trees);
			if (!StringUtils.isBlank(callback)) {
				response = String.format("%s%s%s%s", callback, "(", response, ")");
			}
		} catch (Exception e) {
			logger.error("Load stats items error.", e);
			return response;
		}
		
		logger.debug("Stats items: " + response);
		return response;
	}
	
	@RequestMapping(value = "history/stats", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String loadHistoryStats(
			@RequestParam int pollerItemId, 
			@RequestParam String startTime, 
			@RequestParam String endTime, 
			@RequestParam int timeUnit, 
			@RequestParam(required = false) String callback) {
		String response = null;

		try {
			List<PollerReport> pollerReports = new ArrayList<PollerReport>();
			switch (timeUnit) {
			case UNIT_YEAR: {
				pollerReports = pollerReportServer.loadReport(pollerItemId, PollerType.YEAR, startTime, endTime);
			}
				break;
			case UNIT_MONTH: {
				pollerReports = pollerReportServer.loadReport(pollerItemId, PollerType.MONTH, startTime, endTime);
			}
				break;
			case UNIT_DAY: {
				pollerReports = pollerReportServer.loadReport(pollerItemId, PollerType.DAY, startTime, endTime);
			}
				break;
			case UNIT_HOUR: {
				pollerReports = pollerReportServer.loadReport(pollerItemId, PollerType.HOUR, startTime, endTime);
			}
				break;
			case UNIT_HALF_HOUR: {
				pollerReports = pollerReportServer.loadReport(pollerItemId, PollerType.HALF_HOUR, startTime, endTime);
			}
				break;
			default:
				break;
			}
			if (pollerReports == null || pollerReports.isEmpty()) {
				return response;
			}
			
			// 根据pollerReport的stats_key，存入map中，map的value是一个list，每个元素为endTime和statsValue组成的字符串数组
			// returnMap{statsKey={[endTime,statsValue],...},...}
			Map<String, List<String[]>> reportMap = new HashMap<String, List<String[]>>();
			for (PollerReport pr : pollerReports) {
				if (!reportMap.containsKey(pr.getStatsKey())) {
					reportMap.put(pr.getStatsKey(), new ArrayList<String[]>());
				}
				reportMap.get(pr.getStatsKey()).add(
						new String[]{String.valueOf(pr.getEndTime().getTime()), pr.getStatsValue()});
			}
			
			response = JSonUtil.transformToJSon(reportMap);
			if (!StringUtils.isBlank(callback)) {
				response = String.format("%s%s%s%s", callback, "(", response, ")");
			}
		} catch (Exception e) {
			logger.error("Load history stats error: ", e);
		}
		
		logger.debug("Load real time stats: {}", response);
		return response;
	}
}
