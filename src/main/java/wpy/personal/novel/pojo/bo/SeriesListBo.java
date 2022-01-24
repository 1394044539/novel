package wpy.personal.novel.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SeriesListBo {

    private String novelId;

    private String seriesName;

    private String seriesAuthor;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publicTime;

    private String novelImg;

    private String novelDesc;

}
