package orgprimeholding.service.downloaders;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SftpDownloader {
    private static final Logger LOGGER = Logger.getLogger(SftpDownloader.class.getName());

    private SftpDownloader() {
    }

    public static void downloadFiles(String username, String remoteHost, String password, String remoteDir, String localDir) {

        try {
            final String PATHSEPARATOR = "/";

            JSch jsch = new JSch();
            Session jschSession = jsch.getSession(username, remoteHost);
            jschSession.setPassword(password);
            jschSession.setConfig("StrictHostKeyChecking", "no");
            jschSession.connect();
            ChannelSftp channelSftp = (ChannelSftp) jschSession.openChannel("sftp");
            channelSftp.connect();

            List<ChannelSftp.LsEntry> fileAndFolderList = channelSftp.ls(remoteDir);
            for (ChannelSftp.LsEntry lsEntry : fileAndFolderList) {
                if (!lsEntry.getAttrs().isDir()) {
                    new File(localDir + PATHSEPARATOR + lsEntry.getFilename());
                    channelSftp.get(remoteDir + PATHSEPARATOR + lsEntry.getFilename(),
                            localDir + PATHSEPARATOR + lsEntry.getFilename());
                }
            }

            jschSession.disconnect();
            channelSftp.exit();
        } catch (JSchException | SftpException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
