package orgprimeholding.utils.parsers;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateAdapter extends XmlAdapter<String, LocalDateTime> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd'T'HH:mm:ss][yyyy-MM-dd HH:mm:ss]");

    @Override
    public LocalDateTime unmarshal(String v) {
        return LocalDateTime.parse(v, formatter);
    }

    @Override
    /* Not in use */
    public String marshal(LocalDateTime v) {
        return v.toString();
    }
}
