package com.thornBird.base;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description: System Test
 * @author: HymanHu
 * @date: 2019-01-14 10:24:22  
 */
public class SystemTest {

	/**
	 * System.getenv()
	 * USERPROFILE\USERDNSDOMAIN\PATHEXT\JAVA_HOME\TEMP\
	 * SystemDrive\ProgramFiles\USERDOMAIN\ALLUSERSPROFILE\
	 * SESSIONNAME\TMP\Path\CLASSPATH\PROCESSOR_ARCHITECTUR\
	 * OS\PROCESSOR_LEVEL\COMPUTERNAME\Windir\SystemRoot\USERNAME\ComSpec\
	 * 
	 * System.getProperty()
	 * java.version\java.vendor\java.vendor.url\java.home\java.vm.specification.version\
	 * java.vm.specification.vendor\java.vm.specification.name\java.vm.version\java.vm.vendor\
	 * java.vm.name\java.specification.version\java.specification.vendor\java.specification.name\
	 * java.class.version\java.class.path\java.library.path\java.io.tmpdir\java.compiler\java.ext.dirs\
	 * os.name\os.arch\os.version\file.separator\path.separator\line.separator\'user.name'\'user.home'\'user.dir'
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException {
		System.out.println(System.getenv("JAVA_HOME"));
		System.out.println(System.getenv("COMPUTERNAME"));
		System.out.println(System.getenv("HOSTNAME"));
		System.out.println(System.getProperty("java.version"));
		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("ENVIRONMENT"));
		
		System.out.println(InetAddress.getLocalHost());
		System.out.println(InetAddress.getLocalHost().getHostName());
		System.out.println(InetAddress.getLocalHost().getHostAddress());
	}
}
