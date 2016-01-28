package Solution;

import Common.Gen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gaolei on 16/1/25.
 * 分桶方法求解，注意不要1000w个指纹中有重复的情况，
 */
public class TongTuSouSuo_2 {
    /**
     * 按照K位分桶
     */
    private static final int K = 20;

    /**
     * DIFF为常量数组， 下标为0,1，2，3，4分别代表20位数字中0,1，2，3，4个位置为1，其余为0的全部数字的集合，这个常量集合用于计算桶的相似桶编号
     */
    private static Set<Integer>[] DIFF = new Set[5];

    /**
     * 初始化的数据集,一共2^K个桶,因为数据有重复，要用List
     */
    private static List<Integer>[] fingerPrints = new List[1<<K];

    /**
     * 初始化生成计算用的常量Set数组DIFF
     */
    private static void initDIFF() {
        //生成常量Set DIFF
        for(int i = 0; i <= 4; ++i) {
            DIFF[i] = new HashSet<Integer>();
        }

        for(int i = 0; i < (1<<K); ++i) {
            int oneCnt = OneInBit(i);
            if(oneCnt >= 0 && oneCnt <= 4)
                DIFF[oneCnt].add(i);
        }
    }

    /**
     * 生成待计算的数据
     */
    private static void initFingerprint() {
        //初始化2^K个桶，让每个Set都不为NULL
        for(int i = 0; i < (1<<K); ++i) {
            fingerPrints[i] = new ArrayList<Integer>();
        }
        // 生成1000w个指纹数据，前20位换算成int，为桶的编号，后20位计算为Int存入对应桶中

        Gen rand = new Gen();
        for(int i = 0; i < 10000000; i++) {
            //前20个位是桶号
            StringBuilder indexSb = new StringBuilder();
            for(int j = 0; j < 20; j++) {
                indexSb.append(rand.next() % 2);
            }
            int index = Integer.parseInt(indexSb.toString(), 2);

            //后面20位是数字，放到相应桶号内
            StringBuilder numSb = new StringBuilder();
            for(int j = 0; j < 20; j++) {
                numSb.append(rand.next() % 2);
            }
            int num = Integer.parseInt(numSb.toString(), 2);

            //把num放到编号为index的桶中
            fingerPrints[index].add(num);
        }
    }

    /**
     * 计算数字的后K位有多少个1
     * @param num
     * @return
     */
    private static int OneInBit(int num) {
        int cnt = 0;
        for(int i = 0; i < K; ++i){
            if((num & 1) == 1)
                cnt ++;
            num >>= 1;
        }
        return cnt;
    }

    /**
     * 计算两个数字有多少个位不同，异或的结果中1的个数
     * @param a
     * @param b
     * @return
     */
    private static int diffBitCount(int a, int b) {
        int tmp = a ^ b;
        return OneInBit(tmp);
    }

    public static void main(String[] args) {
        initDIFF();
        initFingerprint();
        System.out.println("init finish");
        int maxSimilar = 0;
        int maxIndex = -1;
        int maxNum = 0;

        //循环fingerPrint，如果size是0则此桶为空，略过；否则根据DIFF，算出都有哪些桶需要和本桶做比较运算
        for(int index = 0; index < (1<<K); ++index) {
            if(index % 100 == 0)
                System.out.println("index:" + index);
            //这个桶内没有数据，直接continue；
            if(fingerPrints[index].size() == 0)
                continue;

            Set<Integer>[] similarSet = new Set[5];
            //根据常量集合DIFF，计算和桶编号不同位数分别为0（即本桶）,1，2，3，4的桶编号, 放到similar集合中
            for(int i = 0; i < 5; ++i) {
                similarSet[i] = new HashSet<Integer>();
                for(Integer mask : DIFF[i]) {
                    similarSet[i].add(index ^ mask);
                }
            }

            // 计算本桶中每个指纹的similar指纹个数，有个问题是把自己也算上了，不过我们找最大的，不影响结果
            for(Integer num : fingerPrints[index]) {
                int totSimilar = 0;
                for(int i = 0; i < 5; ++i) {
                    for(Integer compareIndex : similarSet[i]) {
                        for(Integer compareNum : fingerPrints[compareIndex]) {
                            if(diffBitCount(num, compareNum) + i <= 4) {
                                totSimilar++;
                            }
                        }
                    }
                }
                if(totSimilar > maxSimilar) {
                    maxSimilar = totSimilar;
                    maxIndex = index;
                    maxNum = num;
                    System.out.println("maxSimilar: " + maxSimilar + "maxIndex: " + maxIndex + "maxNum: " + maxNum);
                }
            }
        }
    }
}
