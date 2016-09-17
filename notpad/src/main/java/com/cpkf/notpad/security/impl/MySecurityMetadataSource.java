package com.cpkf.notpad.security.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

import com.cpkf.notpad.commons.constants.PublicResourceEnum;
import com.cpkf.notpad.entity.Resource;
import com.cpkf.notpad.entity.Role;
import com.cpkf.notpad.server.IResourceService;
import com.cpkf.notpad.server.IRoleService;

/**  
 * Filename:    MySecurityMetadataSource.java
 * Description: 资源源数据定义，决定某一资源能被什么角色访问
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   May 26, 2011 5:04:50 PM
 * modified:    
 */
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	private static final Logger logger = Logger.getLogger(MySecurityMetadataSource.class);
	@Autowired
	private IResourceService resourceService;
	@Autowired
	private IRoleService roleService;
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	private UrlMatcher urlMatcher;
	public MySecurityMetadataSource(){
		urlMatcher = new AntUrlPathMatcher();
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		//可在构造方法中调用初始化方法，也可在配置文件中指定初始化方法
//		loadResourceDefine();
	}
	/* 
	 * method name   : loadPublicResources
	 * description   : 为每个角色添加公共资源
	 * @author       : Jiang.Hu
	 * @param        : 
	 * @return       : void
	 * Create at     : May 30, 2011 11:12:35 AM
	 * modified      : 
	 */      
	public void loadPublicResources(){
		List<Role> roles = roleService.getAllRoles();
		for(PublicResourceEnum publicResourceEnum : PublicResourceEnum.values()){
			Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
			for(Role role : roles){
				ConfigAttribute configAttribute = new SecurityConfig(role.getRoleName());
				configAttributes.add(configAttribute);
			}
			resourceMap.put(publicResourceEnum.getPublicResourcePath(), configAttributes);
		}
	}
	/* 
	 * method name   : loadResourceDefine
	 * description   : 初始化方法，得到所有资源以及每个资源对应的角色
	 * @author       : Jiang.Hu
	 * @param        : 
	 * @return       : void
	 * Create at     : May 27, 2011 2:38:26 PM
	 * modified      : 
	 */      
	public void loadResourceDefine(){
		List<Resource> resources = resourceService.getAllResources();
		if(resources != null && !resources.isEmpty()){
			for(Resource resource : resources){
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				Iterator<Role> roleIterator = resource.getRoles().iterator();
				while(roleIterator.hasNext()){
					Role role = roleIterator.next();
					ConfigAttribute configAttribute = new SecurityConfig(role.getRoleName());
					configAttributes.add(configAttribute);
				}
				resourceMap.put(resource.getUrl(), configAttributes);
			}
		}
		loadPublicResources();
	}
	/* 
	 * method name   : getAttributes
	 * description   : 将请求url和资源库比较，符合则将资源库的角色定义赋值给当前请求url
	 * @author       : Jiang.Hu
	 * modified      : leo ,  May 27, 2011
	 * @see          : @see org.springframework.security.access.SecurityMetadataSource#getAttributes(java.lang.Object)
	 */    
	public Collection<ConfigAttribute> getAttributes(Object object) 
		throws IllegalArgumentException {
		if((object == null) || !this.supports(object.getClass())){
			logger.info("Object must be a FilterInvocation");
			throw new IllegalArgumentException("Object must be a FilterInvocation");
		}
		Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
		if(object instanceof FilterInvocation){
			String requestUrl = ((FilterInvocation)object).getRequestUrl();
//			System.out.println("请求url:" + requestUrl);
			//比较请求url和资源库url，匹配则赋予对应的角色权限
			if(resourceMap != null && !resourceMap.isEmpty()){
				Iterator<String> iterator = resourceMap.keySet().iterator();
				while(iterator.hasNext()){
					String resourceUrl = iterator.next();
					if(urlMatcher.pathMatchesUrl(resourceUrl, requestUrl)){
						configAttributes.addAll(resourceMap.get(resourceUrl));
					}
				}
				if(configAttributes.isEmpty()){
					configAttributes.add(new SecurityConfig("nobody"));
				}
//				for(ConfigAttribute configAttribute : configAttributes){
//					System.out.println("请求url匹配角色：" + configAttribute.toString());
//				}
				return configAttributes;
			}
		}
		return null;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return new ArrayList<ConfigAttribute>();
	}

	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
