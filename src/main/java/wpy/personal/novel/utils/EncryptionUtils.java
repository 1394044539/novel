package wpy.personal.novel.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class EncryptionUtils {

    /**
     * md5加密
     * @param password
     * @param salt
     * @return
     */
    public static String md5Encryption(String password,String salt){
        Md5Hash md5Hash=new Md5Hash(password,salt,3);
        return md5Hash.toString();
    }
}
