package Common;

/**
 * Created by gaolei on 16/1/25.
 */
public class Cipher
{
    public static String f(byte[] keys,String text)
    {
        byte[] plainText=text.getBytes();
        StringBuffer sb=new StringBuffer("");
        for(int i=0;i<plainText.length;i+=3)
        {
            int temp=0;
            for(int j=0;j<3;j++)
            {
                temp<<=8;
                temp+=plainText[i+j];
            }
            for(int j=0;j<3;j++)
            {
                temp^=(keys[temp&3]<<8);
                temp=(temp<<7)|(temp>>17);
                temp&= ((1<<24)-1);
            }
            sb.append(String.format("%06x",temp));
        }
        return sb.toString();
    }
    public static void main(String[] args)
    {
        byte[] key=new byte[]{11,22,33,44};//key数组是byte数组,元素的取值范围是0-255
        String ans=Cipher.f(key,"hello,world!");
        System.out.println(ans);//5b0dcfc68c8d58e9c5680e4c
    }
}
