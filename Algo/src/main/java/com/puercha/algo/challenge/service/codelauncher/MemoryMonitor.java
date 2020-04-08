package com.puercha.algo.challenge.service.codelauncher;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.ProcessBuilder.Redirect;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 * pid 별로 메모리사용량을 모니터링 함
 * @author Hyeonuk
 *
 */
//@Service
public class MemoryMonitor {
	public static final Logger logger = LoggerFactory.getLogger(MemoryMonitor.class);
	public static void main(String[] args) {
		new MemoryMonitor();
	}	
	private static Map<Integer,Long> memoryUsageInfo;// 사용 메모리
	private String parentProcessId; // 부모 프로세스 아이디
	private Process p;
	public MemoryMonitor() {
		//
		init();
		if(memoryUsageInfo!=null)
			logger.info("memoryUsageInfo:"+memoryUsageInfo.toString());
		else 
			logger.info("memoryUsageInfo: null");
		
	}
	
	
	
	public Process getP() {
		return p;
	}

	public String getParentProcessId() {
		return parentProcessId;
	}
	public void setParentProcessId(String parentProcessId) {
		this.parentProcessId = parentProcessId;

	}

	public Set<Integer> getKeySet(){
		return this.memoryUsageInfo.keySet();
	}
	


	/**
	 * 프로세스의 최대 메모리 사용량을 반환함
	 * @param pid 최대 사용량을 확인할 프로세스 id
	 * @return
	 */
	synchronized public long getMemoryUsage(int pid) {
		if(!memoryUsageInfo.containsKey(pid))
			return 0;
		return memoryUsageInfo.get(pid);
	}
	
	synchronized public void setMemoryUsage(int pid, long memoryUsage) {
		if(this.memoryUsageInfo.containsKey(pid) ){			
			long pre = (long)this.memoryUsageInfo.get(pid);
			this.memoryUsageInfo.replace(pid, Math.max(pre , memoryUsage) );
		}
		else  {
			this.memoryUsageInfo.put(pid, memoryUsage);
		}
	}
	
	public void init() {
		// 현재 프로세스의 pid 가져오기
		memoryUsageInfo = new HashMap<Integer,Long>();
		RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
		String name = rt.getName();
		parentProcessId = name.substring(0, name.indexOf("@"));
		// process builder로		
		List<String> command = new ArrayList<String>();
		command.add("cmd.exe");
		ProcessBuilder pb = new ProcessBuilder(command);
		pb.redirectInput(Redirect.PIPE);
		pb.redirectOutput(Redirect.PIPE);
		pb.redirectError(Redirect.PIPE);
		Process p = null;
		try {
			p = pb.start();
			WindowsMonitor monitor = new WindowsMonitor(this, p);
			Thread t = new Thread(monitor);
			t.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			p.waitFor();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	/**
	 * 윈도우 메모리 모니터
	 * @author Hyeonuk
	 *
	 */
	class WindowsMonitor implements Runnable{
		final String NAME_PROCESSID = "processid";
		final String NAME_PEAKWORKINGSETSIZE= "peakworkingsetsize";
		MemoryMonitor memoryMonitor;//
		boolean isRunnable = true;
		char[] inputBuf = new char[1024];
		byte[] errorBuf = new byte[1024];
		Process p;
		public WindowsMonitor( MemoryMonitor monitor, Process p) {
			this.memoryMonitor = monitor;
			this.p = p;
			
		}
		synchronized public void stop() {
			isRunnable = false;
		}
		
		@Override
		public void run() {
			System.out.println("run()");
//			Scanner scan = new Scanner(new BufferedInputStream(p.getInputStream()));
//			Scanner errerScaner = new Scanner(p.getErrorStream());
			InputStream inputStream = new BufferedInputStream(p.getInputStream());
			InputStreamReader inputReader = new InputStreamReader(inputStream);
			InputStream errorStream = p.getErrorStream();
			try {
				errorStream.close();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
//			p.getErrorStream().close();
			PrintStream print = new PrintStream(p.getOutputStream());
			String pid = memoryMonitor.getParentProcessId();
			
//			String cmd = "wmic process where(parentprocessid="+pidNumber+") get processid, peakworkingsetsize";
			String cmd = String.format("wmic process where(parentprocessid=%s) get %s, %s",pid, NAME_PROCESSID,NAME_PEAKWORKINGSETSIZE);
			Pattern dataPattern = Pattern.compile("^[0-9]{1,6}(\\s)+[0-9]{1,6}") ;
			Pattern namePattern = Pattern.compile("^[A-Za-z]+(\\s)+[A-Za-z]+") ;
			Pattern numberPattern = Pattern.compile("[0-9]+");
			StringBuilder inputBuilder = new StringBuilder();
			boolean isPidFirst = false;
			// 반복하기
			int cnt =0;
			int nRead ;
			try {
				nRead = inputReader.read(inputBuf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			print.println(cmd);
			print.flush();
			inputBuilder = new StringBuilder();
			
			while(isRunnable) {				
				try {					
					while(inputReader.ready()) {
						nRead = inputReader.read(inputBuf);						
						inputBuilder.append(inputBuf,0,nRead);
//						logger.info("message:"+new String(inputBuf));
						// 개행으로 자르기
						int preIdx = 0;
						int idx = inputBuilder.indexOf(System.lineSeparator());
						while(idx>=0) {
							// 한줄
//							System.out.printf("preIdx idx:(%d, %d)%n",preIdx,idx);
//							logger.info(String.format("preIdx idx:(%d, %d)%n",preIdx,idx));;
							String line = inputBuilder.substring(preIdx, idx);
							Matcher dataMatcher = dataPattern.matcher(line);
							Matcher nameMatcher = namePattern.matcher(line);
							if(nameMatcher.find()) { // 컬럼명일경우
//								System.out.println("line:"+line);
								String lower = line.toLowerCase();
//								System.out.printf("%s %s %s %d %d %n",lower,NAME_PEAKWORKINGSETSIZE,NAME_PROCESSID,
//										lower.indexOf(NAME_PEAKWORKINGSETSIZE) , 
//										lower.indexOf(NAME_PROCESSID));
								isPidFirst = lower.indexOf(NAME_PEAKWORKINGSETSIZE) > lower.indexOf(NAME_PROCESSID);
							}
							if(dataMatcher.find()) { // pid일 경우								
//								System.out.println("line:"+line);
								Matcher numberMatcher = numberPattern.matcher(line);
								String pidStr, memoryStr;
//								System.out.println("number matcher:"+numberMatcher.toString());
								if(isPidFirst) {
									numberMatcher.find();
									pidStr = numberMatcher.group();
									numberMatcher.find();
									memoryStr = numberMatcher.group();
								}else {
									numberMatcher.find();
									memoryStr = numberMatcher.group();
									numberMatcher.find();
									pidStr = numberMatcher.group();									
								}
//								logger.info(String.format("pid:%s , usage: %s", pidStr,memoryStr));
								memoryMonitor.setMemoryUsage(Integer.parseInt(pidStr), Long.parseLong(memoryStr));cnt++;
//								System.out.printf("pid:%s memory:%s%n",pidStr,memoryStr);
							}							
							preIdx  = idx + System.lineSeparator().length();//개행 다음
							idx = inputBuilder.indexOf(System.lineSeparator(),preIdx+1);							
						}
						String next = inputBuilder.substring(preIdx);
						inputBuilder= new StringBuilder(next );

						print.println(cmd);
						print.flush();
//						logger.info("command 입력");
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				} 
//				try {					
//					Thread.sleep(2000, 1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				
			}
		}
		
	}
}
