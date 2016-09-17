package com.thornBird.think.zookeeper.controller;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.thornBird.think.zookeeper.common.util.JSonUtil;
import com.thornBird.think.zookeeper.model.PageResult;
import com.thornBird.think.zookeeper.server.impl.ZkConsoleServerImpl;

@Controller
public class ConsoleController {

	private static final Log log = LogFactory.getLog(ConsoleController.class);

	@RequestMapping(value = "getChildrenNodesJson", method = { RequestMethod.GET, RequestMethod.POST })
	public void getChildrenNodesJson(HttpServletRequest request, HttpServletResponse response) {
		String identifier = request.getParameter("id");
		log.debug("get tree node: " + identifier);
		ZkConsoleServerImpl zkConsoleServer = ZkConsoleServerImpl.getZkConsoleServer();
		String nodeJson = "";
		PrintWriter out = null;

		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			if (zkConsoleServer == null) {
				out.print("Connection failure.");
				return;
			}

			if (identifier.equals("root")) {
				nodeJson = zkConsoleServer.getCurrentNodesJson("/");
			} else {
				nodeJson = zkConsoleServer.getChildrenNodesJson(identifier);
			}

			String zkHost = zkConsoleServer.getZkHostString();
			request.getSession().setAttribute("zkHost", zkHost);

			out.print(nodeJson);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.toString());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@RequestMapping(value = "createNode", method = { RequestMethod.GET, RequestMethod.POST })
	public void createNode(HttpServletRequest request, HttpServletResponse response) {
		String identifier = request.getParameter("identifier");
		String nodeKey = request.getParameter("nodeKey");
		String nodeValue = request.getParameter("nodeValue");
		log.debug("Create new node...");
		log.debug("identifier" + identifier);
		log.debug("nodeKey" + nodeKey);
		log.debug("nodeValue" + nodeValue);
		ZkConsoleServerImpl zkConsoleServer = ZkConsoleServerImpl.getZkConsoleServer();
		PrintWriter out = null;
		PageResult result = new PageResult();

		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			if (zkConsoleServer == null) {
				result.setSuccess(false);
				result.setMsg("Connection failure.");
				out.print(JSonUtil.transformToJSon(result));
				return;
			}

			boolean success = zkConsoleServer.addNode(identifier, nodeKey, nodeValue);

			if (success) {
				result.setSuccess(true);
				result.setMsg("Create node success.");
			} else {
				result.setSuccess(false);
				result.setMsg("Create node failure.");
			}
			out.print(JSonUtil.transformToJSon(result));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage() == null ? "Create node failure." : e.getMessage());
			out.print(JSonUtil.transformToJSon(result));

			e.printStackTrace();
			log.debug(e.toString());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@RequestMapping(value = "deleteNode", method = { RequestMethod.POST, RequestMethod.GET })
	public void deleteNode(HttpServletRequest request, HttpServletResponse response) {
		String identifiersString = request.getParameter("identifiers");
		log.debug("Delete new node...");
		log.debug("identifiers" + identifiersString);
		ZkConsoleServerImpl zkConsoleServer = ZkConsoleServerImpl.getZkConsoleServer();
		PrintWriter out = null;
		PageResult result = new PageResult();

		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			if (zkConsoleServer == null) {
				result.setSuccess(false);
				result.setMsg("Connection failure.");
				out.print(JSonUtil.transformToJSon(result));
				return;
			}

			String[] identifiers = identifiersString.split(";");
			Set<String> identifierSet = new HashSet<String>();
			StringBuffer sb = new StringBuffer();
			for (String identifier : identifiers) {
				identifierSet.add(identifier);
			}
			for (String identifier : identifierSet) {
				if (!zkConsoleServer.deleteNode(identifier)) {
					sb.append(identifier + ";");
				}
			}

			if (sb.length() > 0) {
				result.setSuccess(false);
				result.setMsg(String.format("%s%s%s", "Delete '", sb.substring(0, sb.length() - 1),
						"' failure."));
			} else {
				result.setSuccess(true);
				result.setMsg("Delete nodes success.");
			}
			out.print(JSonUtil.transformToJSon(result));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage() == null ? "Delete node failure." : e.getMessage());
			out.print(JSonUtil.transformToJSon(result));

			e.printStackTrace();
			log.debug(e.toString());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@RequestMapping(value = "updateNode", method = { RequestMethod.POST, RequestMethod.GET })
	public void updateNode(HttpServletRequest request, HttpServletResponse response) {
		String identifier = request.getParameter("identifier");
		String nodeValue = request.getParameter("nodeValue");
		log.debug("Update new node...");
		log.debug("identifier" + identifier);
		log.debug("nodeValue" + nodeValue);
		ZkConsoleServerImpl zkConsoleServer = ZkConsoleServerImpl.getZkConsoleServer();
		PrintWriter out = null;
		PageResult result = new PageResult();

		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			if (zkConsoleServer == null) {
				result.setSuccess(false);
				result.setMsg("Connection failure.");
				out.print(JSonUtil.transformToJSon(result));
				return;
			}

			if (zkConsoleServer.updateNode(identifier, nodeValue)) {
				result.setSuccess(true);
				result.setMsg("Update node success.");
			} else {
				result.setSuccess(false);
				result.setMsg("Update node failure.");
			}
			out.print(JSonUtil.transformToJSon(result));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage() == null ? "Update node failure." : e.getMessage());
			out.print(JSonUtil.transformToJSon(result));

			e.printStackTrace();
			log.debug(e.toString());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@RequestMapping(value = "copyNode", method = { RequestMethod.GET, RequestMethod.POST })
	public void copyNode(HttpServletRequest request, HttpServletResponse response) {
		String fromIdentifier = request.getParameter("fromIdentifier");
		String toIdentifier = request.getParameter("toIdentifier");
		log.debug("Copy node...");
		log.debug("fromIdentifier" + fromIdentifier);
		log.debug("toIdentifier" + toIdentifier);
		ZkConsoleServerImpl zkConsoleServer = ZkConsoleServerImpl.getZkConsoleServer();
		PrintWriter out = null;
		PageResult result = new PageResult();

		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			if (zkConsoleServer == null) {
				result.setSuccess(false);
				result.setMsg("Connection failure.");
				out.print(JSonUtil.transformToJSon(result));
				return;
			}

			if (zkConsoleServer.copyNode(fromIdentifier, toIdentifier)) {
				result.setSuccess(true);
				result.setMsg("Copy node success.");
			} else {
				result.setSuccess(false);
				result.setMsg("Copy node failure.");
			}
			out.print(JSonUtil.transformToJSon(result));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage() == null ? "Copy node failure." : e.getMessage());
			out.print(JSonUtil.transformToJSon(result));

			e.printStackTrace();
			log.debug(e.toString());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@RequestMapping(value = "exportNode", method = { RequestMethod.POST, RequestMethod.GET })
	public void exportNode(HttpServletRequest request, HttpServletResponse response) {
		String identifiers = request.getParameter("identifiers");
		log.debug("Export node...");
		log.debug("identifiers" + identifiers);
		ZkConsoleServerImpl zkConsoleServer = ZkConsoleServerImpl.getZkConsoleServer();
		PrintWriter out = null;

		try {
			out = response.getWriter();
			if (zkConsoleServer == null) {
				response.setContentType("text/html;charset=UTF-8");
				out.print("Connection failure.");
				return;
			} else {
				String nodesString = zkConsoleServer.getAllNodeJson(identifiers);
				response.setContentType("application/octet-stream;charset=UTF-8");
				response.addHeader("Content-Disposition", "attachment;filename=zookeeperData.txt");
				response.addHeader("Content-Length", "" + nodesString.getBytes("UTF-8").length);
				out.write(nodesString);
			}
		} catch (Exception e) {
			response.setContentType("text/html;charset=UTF-8");
			out.print(e.getMessage() == null ? "Export node failure." : e.getMessage());
			e.printStackTrace();
			log.debug(e.toString());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@RequestMapping(value = "importNode", method = { RequestMethod.POST, RequestMethod.GET })
	public void importNode(@RequestParam(value = "importFile", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		String identifier = request.getParameter("identifier");
		log.debug("Import node...");
		log.debug("identifier" + identifier);
		ZkConsoleServerImpl zkConsoleServer = ZkConsoleServerImpl.getZkConsoleServer();
		PrintWriter out = null;
		PageResult result = new PageResult();

		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			if (zkConsoleServer == null) {
				result.setSuccess(false);
				result.setMsg("Connection failure.");
				out.print(JSonUtil.transformToJSon(result));
				return;
			}

			String fileName = file == null ? null : file.getOriginalFilename();
			if (fileName == null || fileName.length() == 0) {
				result.setSuccess(false);
				result.setMsg("Please select upload file.");
				out.print(JSonUtil.transformToJSon(result));
				return;
			}
			String suffixfileName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (!suffixfileName.equals("txt")) {
				result.setSuccess(false);
				result.setMsg("Only upload txt file!");
				out.print(JSonUtil.transformToJSon(result));
				return;
			}

			byte[] bytes = file.getBytes();
			String data = new String(bytes, "UTF-8");
			if (zkConsoleServer.importNode(identifier, data)) {
				result.setSuccess(true);
				result.setMsg("Upload file success.");
			} else {
				result.setSuccess(false);
				result.setMsg("Upload file failure.");
			}

			out.print(JSonUtil.transformToJSon(result));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage() == null ? "Upload file failure." : e.getMessage());
			out.print(JSonUtil.transformToJSon(result));

			e.printStackTrace();
			log.debug(e.toString());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	@RequestMapping(value = "getZkHost", method = {RequestMethod.POST, RequestMethod.GET})
	public void getZkHost(HttpServletRequest request, HttpServletResponse response) {
		ZkConsoleServerImpl zkConsoleServer = ZkConsoleServerImpl.getZkConsoleServer();
		PrintWriter out = null;
		
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			if (zkConsoleServer == null) {
				out.print("Connection failure.");
				return;
			}

			String zkHost = zkConsoleServer.getZkHostString();

			log.debug("get zookeeper host:" + zkHost);
			out.print(zkHost);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.toString());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
