<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link type="text/css" rel="stylesheet" href="<c:url value='/styles/all.css'/>"/>
		<link type="text/css" rel="stylesheet" href="<c:url value='/styles/modifyOwerInfo.css'/>"/>
		<title>userInfo</title>
	</head>
	<body>
		<div class="allWrap">
			<%@include file="../common/head.jsp" %>
			<div class="bodyWrap">
				<table class="tb_all outLine" width="800" align="center" border="0" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td colspan="3" id="list_bt" height="22">
								<div class="divLinks">
									<a href="<c:url value='/securityLogin.do'/>">主页</a> &gt;
									<a href="<c:url value='/redirectMyRiverAndLake.do'/>">我的江湖</a> &gt;
									<a href="<c:url value='/account/showUserList.do'/>">用户列表</a> &gt;
									<a href="#">个人资料</a> &gt;
									<a href="#">相册</a> | <a href="#">日记</a>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="3" valign="top" align="center" height="10">
								<img src="<c:url value='/images/list_line.jpg'/>" width="751" height="1"/>
							</td>
						</tr>
						<tr>
							<td colspan="3" valign="middle" align="center" height="30">
								<span class="bt_font14cu">个人资料</span>
							</td>
						</tr>
						<tr>
							<td colspan="3" class="n_font" valign="top" align="center">
								<!-- 基本资料 -->
								<table class="table_blue" width="750" border="0" cellpadding="0" cellspacing="0">
									<tbody>
										<tr>
											<td colspan="2" class="name_line_font" valign="middle" align="left" height="30">
												<c:if test="${not empty accountVo.account.user.userName }">
													${accountVo.account.user.userName }
												</c:if>
												<c:if test="${empty accountVo.account.user.userName }">
													${accountVo.account.email }
												</c:if>
												<img src="<c:url value='/images/btn_modify.gif'/>">
											</td>
										</tr>
										<tr>
											<td colspan="2"height="10">
											</td>
										</tr>
										<tr>
											<td colspan="2" style="padding-top: 10px; padding-bottom: 0px;" >
												<span class="resume_bt1_font">基本资料</span>
												<span class="operationBt">
													<img src="<c:url value='/images/btn_modify.gif'/>">
												</span>
											</td>
										</tr>
										<tr>
											<td colspan="2" style="padding-top: 0px; padding-bottom: 0px;" valign="top" align="left">
												<img src="<c:url value='/images/reasum_line.jpg'/>" width="750" height="4" />
											</td>
										</tr>
										<tr>
											<td class="resume_n_font"  valign="top" width="600" align="left" >
												<table width=100% border="0">
													<tbody>
														<tr>
															<td class="leftTd" align="center">别名</td>
															<td class="rightTd">${accountVo.account.user.nickName }</td>
															<td class="leftTd" align="center">性别</td>
															<td class="rightTd">
																<c:if test="${accountVo.account.user.gender == true }">
																	男
																</c:if>
																<c:if test="${accountVo.account.user.gender == false }">
																	女
																</c:if>
															</td>
														</tr>
														<tr>
															<td class="leftTd" align="center">国籍</td>
															<td class="rightTd">${accountVo.account.user.nationality }</td>
															<td class="leftTd" align="center">民族</td>
															<td class="rightTd">${accountVo.account.user.nation }</td>
														</tr>
														<tr>
															<td class="leftTd" align="center">出生日期</td>
															<td class="rightTd"><fmt:formatDate value="${accountVo.account.user.birthday}" pattern="yyyy-MM-dd"/></td>
															<td class="leftTd" align="center">注册日期</td>
															<td class="rightTd"><fmt:formatDate value="${accountVo.account.user.registTime }" pattern="yyyy-MM-dd"/></td>
														</tr>
														<tr>
															<td class="leftTd" align="center">证件类型</td>
															<td class="rightTd">
																<c:if test="${accountVo.account.user.cardType == 1 }">
																	身份证
																</c:if>
																<c:if test="${accountVo.account.user.cardType == 2 }">
																	军官证
																</c:if>
																<c:if test="${accountVo.account.user.cardType == 2 }">
																	护照证
																</c:if>
															</td>
															<td class="leftTd" align="center">证件号</td>
															<td class="rightTd">
																<c:if test="${accountVo.account.user.cardType == 1 }">
																	${accountVo.account.user.nationalId }
																</c:if>
																<c:if test="${accountVo.account.user.cardType == 2 }">
																	${accountVo.account.user.militaryId }
																</c:if>
																<c:if test="${accountVo.account.user.cardType == 2 }">
																	${accountVo.account.user.passportId }
																</c:if>
															</td>
														</tr>
														<tr>
															<td class="leftTd" align="center">户籍</td>
															<td class="rightTd">${accountVo.account.user.householdRegister }</td>
															<td class="leftTd" align="center">现居住地</td>
															<td class="rightTd">${accountVo.account.user.residence }</td>
														</tr>
														<tr>
															<td class="leftTd" align="center">婚姻</td>
															<td class="rightTd">
																<c:if test="${accountVo.account.user.marriage == false }">
																	未婚
																</c:if>
																<c:if test="${accountVo.account.user.marriage == true }">
																	已婚
																</c:if>
															</td>
															<td class="leftTd" align="center">子女</td>
															<td class="rightTd">
																<c:if test="${accountVo.account.user.children == false }">
																	无
																</c:if>
																<c:if test="${accountVo.account.user.children == true }">
																	有
																</c:if>
															</td>
														</tr>
														<tr>
															<td class="leftTd" align="center">职业</td>
															<td class="rightTd">${accountVo.account.user.career }</td>
															<td class="leftTd" align="center">薪水</td>
															<td class="rightTd">${accountVo.account.user.salary }￥</td>
														</tr>
														<tr>
															<td class="leftTd" align="center">兴趣爱好</td>
															<td class="rightTd" colspan="3">${accountVo.account.user.hobby }</td>
														</tr>
													</tbody>
												</table>
											</td>
											<td valign="middle" width="150" align="center">
												<img src="<c:url value='${accountVo.account.user.image }'/>" onerror="this.src='<c:url value="/images/defaultPic.jpg"/>'" width="110" height="130" />
											</td>
										</tr>
									</tbody>
								</table>
								<!-- 联系方式 -->
								<table class="reasumtd" width="750" border="0" cellpadding="0" cellspacing="0">
									<tbody>
										<tr>
											<td style="padding-top: 10px; padding-bottom: 0px;" valign="top" align="left">
												<span class="resume_bt1_font">联系方式</span>
												<span class="operationBt">
													<img src="<c:url value='/images/btn_modify.gif'/>">
												</span>
											</td>
										</tr>
										<tr>
											<td style="padding-top: 0px; padding-bottom: 0px;" valign="top" align="left">
												<img src="<c:url value='/images/reasum_line.jpg'/>" width="750" height="4" />
											</td>
										</tr>
										<tr>
											<td valign="top" align="left">
												<table width=100%; border="0">
													<tbody>
														<tr>
															<td class="leftTdLong" align="center">地址</td>
															<td class="rightTdLong">${accountVo.account.user.address }</td>
															<td class="leftTdLong" align="center">邮编</td>
															<td class="rightTdLong">${accountVo.account.user.zipCode }</td>
														</tr>
														<tr>
															<td class="leftTdLong" align="center">座机</td>
															<td class="rightTdLong">${accountVo.account.user.phone }</td>
															<td class="leftTdLong" align="center">手机</td>
															<td class="rightTdLong">${accountVo.account.user.mobile }</td>
														</tr>
														<tr>
															<td class="leftTdLong" align="center">QQ</td>
															<td class="rightTdLong">${accountVo.account.user.QQ }</td>
															<td class="leftTdLong" align="center">MSN</td>
															<td class="rightTdLong">${accountVo.account.user.MSN }</td>
														</tr>
														<tr>
															<td class="leftTdLong" align="center">电子邮件</td>
															<td class="rightTdLong">${accountVo.account.email }</td>
															<td class="leftTdLong" align="center">个人主页</td>
															<td class="rightTdLong">${accountVo.account.user.homePage }</td>
														</tr>
													</tbody>
												</table>
											</td>
										</tr>
									</tbody>
								</table>
								<!-- 教育经历 -->
								<table class="reasumtd" width="750" border="0" cellpadding="0" cellspacing="0">
									<tbody>
										<tr>
											<td style="padding-top: 10px; padding-bottom: 0px;" valign="top" align="left">
												<span class="resume_bt1_font">教育经历</span>
											</td>
										</tr>
										<tr>
											<td style="padding-top: 0px; padding-bottom: 0px;" valign="top" align="left">
												<img src="<c:url value='/images/reasum_line.jpg'/>" width="750" height="4" />
											</td>
										</tr>
										<tr>
											<td valign="top" align="left">
												<c:if test="${not empty accountVo.account.user.educations }">
													<c:forEach var="education" items="${accountVo.account.user.educations }">
														<div class="reasumline">
															<table width=100% border="0">
																<tbody>
																	<tr>
																		<td class="leftTdLong" align="center">时间</td>
																		<td class="rightTdLong">
																			<fmt:formatDate value="${education.startDate}" pattern="yyyy.MM.dd"/> - 
																			<fmt:formatDate value="${education.endDate}" pattern="yyyy.MM.dd"/>
																		</td>
																		<td class="leftTdLong" align="center">学校</td>
																		<td class="rightTdLong">${education.school}</td>
																	</tr>
																	<tr>
																		<td class="leftTdLong" align="center">专业</td>
																		<td class="rightTdLong">${education.profession}</td>
																		<td class="leftTdLong" align="center">学历</td>
																		<td class="rightTdLong">${education.degree}</td>
																	</tr>
																	<tr>
																		<td class="leftTdLong" align="center">描述</td>
																		<td class="rightTdLong" colspan="3">${education.description}</td>
																	</tr>
																</tbody>
															</table>
														</div>
														<div class="operationDiv">
															<span class="operationBt">
																<img src="<c:url value='/images/btn_modify.gif'/>">
															</span>
														</div>
													</c:forEach>
												</c:if>
												<img src="<c:url value='/images/btn_addcontinue.gif'/>">
											</td>
										</tr>
									</tbody>
								</table>
								<!-- 工作经历 -->
								<table class="reasumtd" width="750" border="0" cellpadding="0" cellspacing="0">
									<tbody>
										<tr>
											<td style="padding-top: 10px; padding-bottom: 0px;" valign="top" align="left">
												<span class="resume_bt1_font">工作经历</span>
											</td>
										</tr>
										<tr>
											<td style="padding-top: 0px; padding-bottom: 0px;" valign="top" align="left">
												<img src="<c:url value='/images/reasum_line.jpg'/>" width="750" height="4" />
											</td>
										</tr>
										<tr>
											<td valign="top" align="left">
												<c:if test="${not empty accountVo.account.user.jobs }">
													<c:forEach var="job" items="${accountVo.account.user.jobs }">
														<div class="reasumline">
															<table width=100% border="0">
																<tbody>
																	<tr>
																		<td class="leftTdLong" align="center">时间</td>
																		<td class="rightTdLong">
																			<fmt:formatDate value="${job.startDate}" pattern="yyyy.MM.dd"/> - 
																			<fmt:formatDate value="${job.endDate}" pattern="yyyy.MM.dd"/>
																		</td>
																		<td class="leftTdLong" align="center">工作单位</td>
																		<td class="rightTdLong">${job.company }</td>
																	</tr>
																	<tr>
																		<td class="leftTdLong" align="center">公司性质</td>
																		<td class="rightTdLong">${job.property }</td>
																		<td class="leftTdLong" align="center">公司规模</td>
																		<td class="rightTdLong">${job.size }人</td>
																	</tr>
																	<tr>
																		<td class="leftTdLong" align="center">行业</td>
																		<td class="rightTdLong">${job.industry }</td>
																		<td class="leftTdLong" align="center">部门</td>
																		<td class="rightTdLong">${job.department }</td>
																	</tr>
																	<tr>
																		<td class="leftTdLong" align="center">职位</td>
																		<td class="rightTdLong">${job.position }</td>
																		<td class="leftTdLong" align="center">描述</td>
																		<td class="rightTdLong">${job.description }</td>
																	</tr>
																</tbody>
															</table>
														</div>
														<div class="operationDiv">
															<span class="operationBt">
																<img src="<c:url value='/images/btn_modify.gif'/>">
															</span>
														</div>
													</c:forEach>
												</c:if>
												<img src="<c:url value='/images/btn_addcontinue.gif'/>">
											</td>
										</tr>
									</tbody>
								</table>
								<!-- 项目经历 -->
								<table class="reasumtd" width="750" border="0" cellpadding="0" cellspacing="0">
									<tbody>
										<tr>
											<td style="padding-top: 10px; padding-bottom: 0px;" valign="top" align="left">
												<span class="resume_bt1_font">项目经历</span>
											</td>
										</tr>
										<tr>
											<td style="padding-top: 0px; padding-bottom: 0px;" valign="top" align="left">
												<img src="<c:url value='/images/reasum_line.jpg'/>" width="750" height="4" />
											</td>
										</tr>
										<tr>
											<td valign="top" align="left">
												<c:if test="${not empty accountVo.account.user.projects }">
													<c:forEach var="project" items="${accountVo.account.user.projects }">
														<div class="reasumline">
															<table width=100% border="0">
																<tbody>
																	<tr>
																		<td class="leftTdLong" align="center">时间</td>
																		<td class="rightTdLong">
																			<fmt:formatDate value="${project.startDate}" pattern="yyyy.MM.dd"/> - 
																			<fmt:formatDate value="${project.endDate}" pattern="yyyy.MM.dd"/>
																		</td>
																		<td class="leftTdLong" align="center">项目名称</td>
																		<td class="rightTdLong">${project.projectName }</td>
																	</tr>
																	<tr>
																		<td class="leftTdLong" align="center">使用工具</td>
																		<td class="rightTdLong">${project.tool }</td>
																		<td class="leftTdLong" align="center">项目环境</td>
																		<td class="rightTdLong">${project.environment }</td>
																	</tr>
																	<tr>
																		<td class="leftTdLong" align="center">项目描述</td>
																		<td class="rightTdLong" colspan="3">${prject.description }</td>
																	</tr>
																	<tr>
																		<td class="leftTdLong" align="center">责任描述</td>
																		<td class="rightTdLong" colspan="3">${project.liability }</td>
																	</tr>
																</tbody>
															</table>
														</div>
														<div class="operationDiv">
															<span class="operationBt">
																<img src="<c:url value='/images/btn_modify.gif'/>">
															</span>
														</div>
													</c:forEach>
												</c:if>
												<img src="<c:url value='/images/btn_addcontinue.gif'/>">
											</td>
										</tr>
									</tbody>
								</table>
								<!-- 大记事 -->
								<table class="reasumtd" width="750" border="0" cellpadding="0" cellspacing="0">
									<tbody>
										<tr>
											<td style="padding-top: 10px; padding-bottom: 0px;" valign="top" align="left">
												<span class="resume_bt1_font">大记事</span>
											</td>
										</tr>
										<tr>
											<td style="padding-top: 0px; padding-bottom: 0px;" valign="top" align="left">
												<img src="<c:url value='/images/reasum_line.jpg'/>" width="750" height="4" />
											</td>
										</tr>
										<tr>
											<td valign="top" align="left">
												<c:if test="${not empty accountVo.account.user.events }">
													<c:forEach var="event" items="${accountVo.account.user.events }">
														<div class="reasumline">
															<table width=100% border="0">
																<tbody>
																	<tr>
																		<td class="leftTdLong" align="center">时间</td>
																		<td class="rightTdLong">
																			<fmt:formatDate value="${event.startDate}" pattern="yyyy.MM.dd"/> - 
																			<fmt:formatDate value="${event.endDate}" pattern="yyyy.MM.dd"/>
																		</td>
																		<td class="leftTdLong" align="center">事件名称</td>
																		<td class="rightTdLong">${event.eventName }</td>
																	</tr>
																	<tr>
																		<td class="leftTdLong" align="center">事件描述</td>
																		<td class="rightTdLong" colspan="3">${event.description }</td>
																	</tr>
																	<tr>
																		<td class="leftTdLong" align="center">事件结果</td>
																		<td class="rightTdLong" colspan="3">${event.result }</td>
																	</tr>
																</tbody>
															</table>
														</div>
														<div class="operationDiv">
															<span class="operationBt">
																<img src="<c:url value='/images/btn_modify.gif'/>">
															</span>
														</div>
													</c:forEach>
												</c:if>
												<img src="<c:url value='/images/btn_addcontinue.gif'/>">
											</td>
										</tr>
									</tbody>
								</table>
								<!-- 梦想与评价 -->
								<table class="reasumtd" width="750" border="0" cellpadding="0" cellspacing="0">
									<tbody>
										<tr>
											<td style="padding-top: 10px; padding-bottom: 0px;" valign="top" align="left">
												<span class="resume_bt1_font">梦想与评价</span>
												<span class="operationBt">
													<img src="<c:url value='/images/btn_modify.gif'/>">
												</span>
											</td>
										</tr>
										<tr>
											<td style="padding-top: 0px; padding-bottom: 0px;" valign="top" align="left">
												<img src="<c:url value='/images/reasum_line.jpg'/>" width="750" height="4" />
											</td>
										</tr>
										<tr>
											<td valign="top" align="left">
												<table width=100% border="0">
													<tbody>
														<tr>
															<td class="leftTdLong" align="center">梦想</td>
															<td class="rightTdLong" colspan="3">${accountVo.account.user.dream }</td>
														</tr>
														<tr>
															<td class="leftTdLong" align="center">个人评价</td>
															<td class="rightTdLong" colspan="3">${accountVo.account.user.evaluation }</td>
														</tr>
													</tbody>
												</table>
											</td>
										</tr>
									</tbody>
								</table>
								<!-- 其他 -->
								<table class="reasumtd" width="750" border="0" cellpadding="0" cellspacing="0">
									<tbody>
										<tr>
											<td style="padding-top: 10px; padding-bottom: 0px;" valign="top" align="left">
												<span class="resume_bt1_font">其他</span>
											</td>
										</tr>
										<tr>
											<td style="padding-top: 0px; padding-bottom: 0px;" valign="top" align="left">
												<img src="<c:url value='/images/reasum_line.jpg'/>" width="750" height="4" />
											</td>
										</tr>
										<tr>
											<td valign="top" align="left">
												<c:if test="${not empty accountVo.account.user.others }">
													<c:forEach var="other" items="${accountVo.account.user.others }">
														<div class="reasumline">
															<table width=100% border="0">
																<tbody>
																	<tr>
																		<td class="leftTdLong" align="center">主题</td>
																		<td class="rightTdLong" colspan="3">${other.title }</td>
																	</tr>
																	<tr>
																		<td class="leftTdLong" align="center">内容</td>
																		<td class="rightTdLong" colspan="3">${other.content }</td>
																	</tr>
																</tbody>
															</table>
														</div>
														<div class="operationDiv">
															<span class="operationBt">
																<img src="<c:url value='/images/btn_modify.gif'/>">
															</span>
														</div>
													</c:forEach>
												</c:if>
												<img src="<c:url value='/images/btn_addcontinue.gif'/>">
											</td>
										</tr>
									</tbody>
								</table>
								<!-- 占位div -->
								<div class="nullDiv"></div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<%@include file="../common/foot.jsp" %>
		</div>
	</body>
</html>