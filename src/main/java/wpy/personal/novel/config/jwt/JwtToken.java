package wpy.personal.novel.config.jwt;

import lombok.Data;

/**
 * jwtToken
 * @author pywang
 * @date 2020/11/29
 */
@Data
public class JwtToken {

    private String token;

    private String username;

    private String accountName;

    private String password;

    private String id;

    public JwtToken(){}

    public JwtToken(String token, String accountName, String password){
        this.token=token;
        this.accountName=accountName;
        this.password=password;
    }
}
