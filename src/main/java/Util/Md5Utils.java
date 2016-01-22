
/**
 * Created by gaolei on 15/9/22.
 */
import java.security.MessageDigest;
public class Md5Utils {

    // 十六进制的各字符
    public static char[] hexDigits={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};


    public final static String toMd5(String src) {
        try {
            //输入字符串转为byte数组
            byte[] btInputs = src.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            messageDigest.update(btInputs);
            //获得密文
            byte[] md5 = messageDigest.digest();
            //把byte密文转为16进制的结果，len是字符数组的长度，因为一个byte 8位，4位组成一个hex数字
            int len = md5.length * 2;
            char[] charStr = new char[len];

            for(int i = 0; i < md5.length; ++i) {
                byte b = md5[i];
                charStr[i * 2] = hexDigits[b>>4 & 0xf];
                charStr[i * 2 + 1] = hexDigits[b & 0xf];
            }

            String res = new String(charStr);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(toMd5("a"));
        String str = "0CC175B9C0F1B6A831C399E269772661";
        int len = str.length();
        System.out.println(len);
    }
}
