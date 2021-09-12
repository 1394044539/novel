package wpy.personal.novel.base.enums;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum  UtilsEnums {

    USER_ERROR("用户不存在或未登录"),

    FILE_UPLOAD_FAIL("文件上传失败"),
    FILE_DELETE_FAIL("文件删除失败"),

    REFLEX_FAIL("反射失败"),

    READ_EPUB_FAIL("获取epub文件失败"),

    REDIS_ERROR("REDIS服务异常")
    ;

    private String msg;

    public void UtilsEnums(String msg){
        this.msg=msg;
    }

    public String getMsg(){
        return msg;
    }
}
