package test.common.utils;

import java.io.InputStream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import lombok.extern.slf4j.Slf4j;
import test.common.config.ApplicationProperties;
import test.common.config.ApplicationProperties.ApplicationProperty;

@Slf4j
public class SSH_connection {
	
	public Session session = null;	
	public String user = ApplicationProperties.getString(ApplicationProperty.SSH_USER);
	public String password = ApplicationProperties.getString(ApplicationProperty.SSH_PASSWORD);
	public int port = Integer.parseInt(ApplicationProperties.getString(ApplicationProperty.SSH_PORT));
	public String host = ApplicationProperties.getString(ApplicationProperty.SSH_HOST);
	public String scenario_path = ApplicationProperties.getString(ApplicationProperty.SSH_SCENARIO_PATH);
	public String folder_name = ApplicationProperties.getString(ApplicationProperty.SSH_FOLDER_NAME);
	public String directory_path = ApplicationProperties.getString(ApplicationProperty.SSH_DIRECTORY_PATH);
	
	
	protected WebDriver driver;
	
	public SSH_connection(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void connectToNode()
	{
		
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		JSch jsch = new JSch();
		
		try {
			session = jsch.getSession(user, host, port);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
		} catch (JSchException e) {

			//log.info("Error While Connected to node "+host+ " through SSH");
		}
		
		//log.info("SSH connection extablished to node :"+host);
		
	}
	
	public void copyMessageTONode() {
		log.info("Creating SFTP Channel.");
		ChannelSftp sftpChannel = null;
		try {
			sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			sftpChannel.put("src//test//resources//message5.json","/home/bridgeadm/tests/manual/messages/scenarios/serviceTools");
		} catch (SftpException e) {
			log.info(e.getMessage());
		} catch (JSchException e) {
			log.info(e.getMessage());
		}

		log.info("SFTP Channel created.");
		sftpChannel.disconnect();

	}
	public void ingestData()
	 {
			String command1 = "python" + " " + scenario_path + " " + folder_name
					+ " --directory=" + directory_path;
			try {
				Channel channel = session.openChannel("exec");
				((ChannelExec) channel).setCommand(command1);
				channel.setInputStream(null);
				((ChannelExec) channel).setErrStream(System.err);

				InputStream in = channel.getInputStream();
				channel.connect();
				byte[] tmp = new byte[1024];
				while (true) {
					while (in.available() > 0) {
						int i = in.read(tmp, 0, 1024);
						if (i < 0)
							break;
						
					}
					if (channel.isClosed()) {
						log.info("exit-status: "+ channel.getExitStatus());
						
						break;
					}
					try {
						Thread.sleep(1000);
					} catch (Exception ee) {
					}
				}
				channel.disconnect();
				session.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}