import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * <p></p>
 *
 MD5输出128bit  用16进制表示 32位
 SHA1输出160bit  用16进制表示 40位
 SHA256输出256bit  用16进制表示 64位
 另外还有SHA244,SHA512  用16进制表示 61位  用16进制表示 128位
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lvxz5
 * @version 1.0
 * @date 2017/11/16
 * @since 1.0
 */
public class Test_encrypt {

    public static void main(String[] args) throws Exception {
        String any = "some one";
        // 64base编码 特点: 可逆,位数不确定
        String base64Encoded = Base64.encodeToString(any.getBytes());
        System.out.println(base64Encoded);
        // 64base解码
        String anyDecode = Base64.decodeToString(base64Encoded.getBytes());
        System.out.println(anyDecode);

        // 16进制编码 特点: 可逆,位数不确定
        String str = "some one";
        System.out.println(str.getBytes());
        String encoded = Hex.encodeToString(str.getBytes());
        System.out.println(encoded);
        // 16进制解码
        String str2 = new String(Hex.decode(encoded));
        System.out.println(str2);

        // 散列算法 特点:位数固定,不可逆,不限制加密长度
        // MD5 特点:结果32位,不限制加密长度
        String strMD5 = "hello";
        String salt = "123";
        String md5 = new Md5Hash(strMD5, salt).toString();
        System.out.println(md5);
        // SHA256 特点:结果64位
        String strSHA256 = "hello";
        String sha = new SimpleHash("SHA-512", strSHA256, salt).toString();
        System.out.println(sha);

        System.out.println("=======对称加密");
        // AES对称加密   有一个公钥用来加密解密信息
        // 对称非对称参考地址: https://www.cnblogs.com/jfzhu/p/4020928.html
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);
        Key key = aesCipherService.generateNewKey();
        System.out.println(encryptBASE64(key.getEncoded()));
        String text = "hello";
        String encrptText = aesCipherService.encrypt(text.getBytes(),key.getEncoded()).toHex();
        System.out.println(encrptText);
        String text2 = new String(aesCipherService.decrypt(Hex.decode(encrptText),key.getEncoded()).getBytes());
        System.out.println(text2);
        // RSA非对称加密  使用公钥加密信息,使用私钥解密信息, 私钥不公开,不外传
        Test_encrypt.initKey();

    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static void initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance("RSA");
        keyPairGen.initialize(512);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        System.out.println("公钥: "+encryptBASE64(publicKey.getEncoded()));
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        System.out.println("私钥: "+encryptBASE64(privateKey.getEncoded()));

    }
    public static String encryptBASE64(byte[] key) throws Exception {
        return Base64.encodeToString(key);
    }

    /**
     * 总结:
     * 1, 编码解码用于传输,统一编码方式
     * 2, 散列算法用于生成摘要, 密码校验
     * 3, 加密解密用于数据加密,安全
     */

}
