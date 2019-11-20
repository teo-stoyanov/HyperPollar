package orgprimeholding.service.parsers;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateAdapter extends XmlAdapter<String, LocalDateTime> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v,formatter);
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return v.toString();
    }
}
