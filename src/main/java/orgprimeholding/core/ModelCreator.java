package orgprimeholding.core;

import orgprimeholding.models.Company;
import orgprimeholding.service.downloaders.SftpDownloader;
import orgprimeholding.service.parsers.XmlParser;
import orgprimeholding.service.validators.XmlValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModelCreator {
    private static final Logger LOGGER = Logger.getLogger(ModelCreator.class.getName());

    private List<Company> companies;

    public ModelCreator() {
        companies = new ArrayList<>();
    }

    public void downloadAndCreate(Properties properties) {

        String userName = properties.getProperty("sftp.user");
        String remoteHost = properties.getProperty("remoteHost");
        String password = properties.getProperty("sftp.password");
        String remoteDir = properties.getProperty("remoteDir");
        String localDir = properties.getProperty("localDir");
        String schemaPath = properties.getProperty("schemaPath");

        SftpDownloader.downloadFiles(userName, remoteHost, password, remoteDir, localDir);

        File dir = new File(localDir);
        File[] directoryListing = dir.listFiles();
        if (directoryListing == null) {
            throw new NullPointerException();
        }

        for (File file : directoryListing) {

            try (InputStream schemaStream = new FileInputStream(schemaPath); InputStream fileStream = new FileInputStream(file)) {
                if (XmlValidator.validate(schemaStream, fileStream)) {
                    Company company = XmlParser.parse(file, Company.class);
                    companies.add(company);
                } else {
                    LOGGER.log(Level.INFO, file.getName() + " is not valid");
                }

            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public List<Company> getCompanies() {
        return this.companies;
    }
}
